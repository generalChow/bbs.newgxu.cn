package cn.newgxu.bbs.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.bank.Accounts;
import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.bank.Fixed;
import cn.newgxu.bbs.domain.bank.Loan;
import cn.newgxu.bbs.domain.bank.OperateLog;
import cn.newgxu.bbs.domain.group.GroupManager;
import cn.newgxu.bbs.domain.message.Message;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.model.admin.BankManageModel;
import cn.newgxu.bbs.web.model.bank.BankModel;
import cn.newgxu.bbs.web.model.bank.CurrentModel;
import cn.newgxu.bbs.web.model.bank.EditPasswordModel;
import cn.newgxu.bbs.web.model.bank.FixedModel;
import cn.newgxu.bbs.web.model.bank.LoanModel;
import cn.newgxu.bbs.web.model.bank.OpenAccountsModel;
import cn.newgxu.bbs.web.model.bank.OperateLogModel;
import cn.newgxu.bbs.web.model.bank.VirementModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BankServiceImpl implements BankService {

	public void bank(BankModel model) throws BBSException {
		try {
			getAccounts(model.getUser());
			model.setHasAccounts(true);
		} catch (BBSException e1) {
			model.setHasAccounts(false);
		}
		model.setBank(getBank());
		model.setBankAdmins(User.getUsers(model.getBank().getBankAdmins()));
	}

	/**
	 * 用户开户。
	 */
	public void openAccounts(OpenAccountsModel model) throws BBSException {
		Bank bank = getBank();
		User user = model.getUser();
		int cost = model.getMoney() + bank.getOpenCost();
		if (!user.canPayFor(cost)) {
			throw new BBSException(BBSExceptionMessage.OUT_OF_MONEY);
		}
		Accounts acc = new Accounts();
		acc.setUser(user);
		acc.setOpenTime(new Date());
		acc.setBeginTime(new Date());
		// acc.setPassword(Util.hash(model.getPassword()));
		acc.setBalance(model.getMoney());
		acc.save();

		bank.addDeposit(cost);
		bank.addUserNumber(1);
		bank.save();

		user.payFor(cost);
		user.save();
		OperateLog.saveOperateLog(acc.getUser(), 0, 0, 0, "用户开户(id:"
				+ acc.getId() + ")，开户金额：" + model.getMoney(),
				acc.getOpenTime(), Constants.BANK_OP_TYPE_OTHER, null);
	}

	/**
	 * 查看活期存款。
	 */
	public void current(CurrentModel model) throws BBSException {
		model.setBank(getBank());
		model.setCurrent(getAccounts(model.getUser()));
		model.setAccrual(accountAccrual(model.getCurrent().getBeginTime(),
				model.getCurrent().getBalance()));
	}

	/**
	 * 活期存取。
	 */
	public void access(CurrentModel model) throws BBSException {
		// if (!bankPassword(getAccounts(model.getUser()), model.getBankpwd()))
		// {
		// throw new BBSException(BBSExceptionMessage.BANK_PASSWORD_INVALID);
		// }
		User user = model.getUser();
		Accounts acc = getAccounts(user);
		switch (model.getAction()) {
		case 1: // 存款
			if (!user.canPayFor(model.getMoney())) {
				throw new BBSException(BBSExceptionMessage.OUT_OF_MONEY);
			}
			squareAccrual(acc);// 结算利息
			access(user, acc, model.getMoney());
			OperateLog.saveOperateLog(user, model.getMoney(), 0, 0, "存款",
					new Date(), Constants.BANK_OP_TYPE_CURR, null);
			break;
		case 2: // 取款
			if (!acc.canPayout(model.getMoney())) {
				throw new BBSException(BBSExceptionMessage.OUT_OF_BALANCE);
			}
			squareAccrual(acc);// 结算利息
			access(user, acc, 0 - model.getMoney());
			OperateLog.saveOperateLog(user, model.getMoney(), 0, 0, "取款",
					new Date(), Constants.BANK_OP_TYPE_CURR, null);
			break;
		default:
			throw new BBSException("操作类型错误！");
		}
	}

	/**
	 * 手动结息。
	 * 
	 * @throws BBSException
	 */
	public void manual(CurrentModel model) throws BBSException {
		// if (!bankPassword(getAccounts(model.getUser()), model.getBankpwd()))
		// {
		// throw new BBSException(BBSExceptionMessage.BANK_PASSWORD_INVALID);
		// }
		squareAccrual(getAccounts(model.getUser()));// 结算利息
	}

	private Bank getBank() throws BBSException {
		try {
			return Bank.get();
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.BANK_NOT_FOUND);
		}
	}

	/**
	 * 获取用户帐户。
	 * 
	 * @param user
	 *            用户。
	 */
	private Accounts getAccounts(User user) throws BBSException {
		try {
			return Accounts.getByUser(user);
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.BANK_ACCOUNTS_NOT_FOUND);
		}
	}

	/**
	 * 检查用户输入的银行密码是否有效。
	 * 
	 * @param accounts
	 *            帐户
	 * @param pwd
	 *            密码
	 * @return true 如果有效；false 如果无效。
	 */
	// private boolean bankPassword(Accounts acc, String pwd) {
	// return acc.getPassword().equals(Util.hash(pwd));
	// }

	/**
	 * 计算活期利息。
	 * 
	 */
	private int accountAccrual(Date beginTime, int price) throws BBSException {
		int total = (int) (price * getBank().getCurrentRate());
		int accrual = (int) (Util.days(beginTime, new Date()) * payTaxes(total));
		// 更新开始计算时间
		return (accrual < 0) ? 0 : accrual;
	}

	/**
	 * 计算定期利息。
	 * 
	 */
	public int fixedAccrual(Date beginTime, int price) throws BBSException {
		int total = (int) (price * getBank().getFixedRate());
		int accrual = (int) (Util.days(beginTime, new Date()) * payTaxes(total));
		return (accrual < 0) ? 0 : accrual;
	}

	/***
	 * 扣除利息税后的利息 级数 日原本所得利息 税率% 速算扣除数 1 0-10 5 0 2 10-40 10 0. 5 3 40-100 15 2.5
	 * 4 100-400 20 7.5 5 400-800 30 27.5 6 800-1200 50 107.5 7 1200-1600 60
	 * 227.5 8 1600-2000 70 387.5 9 2000以上 80 587.5
	 */
	private float payTaxes(int total) {
		float accrual = 0;
		if (total > 2000) {
			accrual += (total - 2000) * 0.2;
			total = 2000;
		}
		if (total > 1600) {
			accrual += (total - 1600) * 0.3;
			total = 1600;
		}
		if (total > 1200) {
			accrual += (total - 1200) * 0.4;
			total = 1200;
		}
		if (total > 800) {
			accrual += (total - 800) * 0.5;
			total = 800;
		}
		if (total > 400) {
			accrual += (total - 400) * 0.7;
			total = 400;
		}
		if (total > 100) {
			accrual += (total - 100) * 0.8;
			total = 100;
		}
		if (total > 40) {
			accrual += (total - 40) * 0.85;
			total = 40;
		}
		if (total > 10) {
			accrual += (total - 10) * 0.9;
			total = 10;
		}
		accrual += total * 0.95;
		return accrual;
	}

	/**
	 * 结算活期利息。
	 * 
	 * @param acc
	 *            操作帐户
	 */
	private void squareAccrual(Accounts acc) throws BBSException {
		Bank bank = getBank();
		int accrual = accountAccrual(acc.getBeginTime(), acc.getBalance());
		// 更新开始计算时间
		acc.setBeginTime(new Date());
		// 利息为0就停止业务
		if (accrual == 0)
			return;
		bank.addBankroll(0 - accrual);
		acc.addBalance(accrual);
		bank.save();
		acc.save();
		OperateLog.saveOperateLog(acc.getUser(), 0, 0, 0, "结算活期利息,取得利息："
				+ accrual, new Date(), Constants.BANK_OP_TYPE_OTHER, null);
	}

	/**
	 * 帐户存取款。
	 * 
	 * @param user
	 *            用户。
	 * @param acc
	 *            操作帐户。
	 * @param money
	 *            操作金额 +存款 -取款。
	 */
	private void access(User user, Accounts acc, int money) throws BBSException {
		Bank bank = getBank();
		bank.addDeposit(money);
		user.addMoney(0 - money);
		acc.addBalance(money);
		bank.save();
		user.save();
		acc.save();
	}

	/**
	 * 计算转帐费用。
	 * 
	 * @param user
	 *            用户。 管理员、版主免转帐费用
	 */
	private int accountVirementCost(User user, int money) throws BBSException {
		if (user.getGroupTypeId() != GroupManager.BASIC_GROUP) {
			return 0;
		}
		int cost = (int) (money * getBank().getVirementRate());
		return (cost < 0) ? 0 : cost;
	}

	public void fixed(FixedModel model) throws BBSException {
		model.setBank(getBank());
		model.setMyFixeds(Fixed.getByUser(model.getUser()));
	}

	/**
	 * 新定期存款。
	 */
	public void newFixed(FixedModel model) throws BBSException {
		// if (!bankPassword(getAccounts(model.getUser()), model.getBankpwd()))
		// {
		// throw new BBSException(BBSExceptionMessage.BANK_PASSWORD_INVALID);
		// }
		if (model.getDays() < getBank().getFixMinDays()) {
			throw new BBSException("定期存款的天数不得小于 " + getBank().getFixMinDays()
					+ " 天！");
		}
		if (!model.getUser().canPayFor(model.getMoney())) {
			throw new BBSException(BBSExceptionMessage.OUT_OF_MONEY);
		}
		Bank bank = getBank();
		Fixed fixed = new Fixed();
		User user = model.getUser();
		int money = model.getMoney();
		fixed.setBeginTime(new Date());
		fixed.setDays(model.getDays());
		fixed.setUser(user);
		fixed.setMoney(money);
		fixed.setFixedRate(bank.getFixedRate());
		fixed.setDrawing(false);

		bank.addDeposit(money);
		bank.addFixed(money);
		user.addMoney(0 - money);

		fixed.save();
		bank.save();
		user.save();
		OperateLog.saveOperateLog(user, money, fixed.getFixedRate(), fixed
				.getDays(), "存入定期(id:" + fixed.getId() + ")", fixed
				.getBeginTime(), Constants.BANK_OP_TYPE_FIXED, null);
	}

	/**
	 * 支取定期存款。
	 */
	public void drawFixed(FixedModel model) throws BBSException {
		Fixed fixed;
		try {
			fixed = Fixed.get(model.getId());
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		if (!fixed.getUser().equals(model.getUser())) {
			throw new BBSException(BBSExceptionMessage.CANNOT_DRAW_FIXED);
		}
		if (fixed.getDrawing() == true) {
			throw new BBSException(BBSExceptionMessage.FIXED_HAS_DRAW);
		}

		Bank bank = getBank();
		User user = fixed.getUser();
		int money = fixed.getMoney();
		int accrual = 0;

		// 如果提前取就按活期算
		if (Util.days(fixed.getBeginTime(), new Date()) < fixed.getDays()) {
			accrual = accountAccrual(fixed.getBeginTime(), fixed.getMoney());
		}
		// 否则按定期算
		else {
			accrual = fixedAccrual(fixed.getBeginTime(), fixed.getMoney());
		}

		bank.addDeposit(0 - money);
		bank.addBankroll(0 - accrual); // 支付利息
		bank.addFixed(0 - money);
		user.addMoney(money + accrual);
		fixed.setDrawing(true);
		fixed.setDrawTime(new Date());

		user.save();
		fixed.save();
		bank.save();
		OperateLog.saveOperateLog(user, money, fixed.getFixedRate(), fixed
				.getDays(), "支取定期(id:" + fixed.getId() + "),取得本金和利息：" + money
				+ accrual, new Date(), Constants.BANK_OP_TYPE_FIXED, null);
	}

	public void loan(LoanModel model) throws BBSException {
		model.setBank(getBank());
		model.setMyLoans(Loan.getByUser(model.getUser(),
				Constants.LOAN_STATUS_PAST));
		model.setMyApplyLoans(Loan.getByUser(model.getUser(),
				Constants.LOAN_STATUS_WAIT));
	}

	/**
	 * 贷款申请。
	 */
	public void newLoan(LoanModel model) throws BBSException {
		// if (!bankPassword(getAccounts(model.getUser()), model.getBankpwd()))
		// {
		// throw new BBSException(BBSExceptionMessage.BANK_PASSWORD_INVALID);
		// }
		Loan loan = new Loan();
		loan.setUser(model.getUser());
		loan.setMoney(model.getMoney());
		loan.setDays(model.getDays());
		loan.setApplyTime(new Date());
		loan.setLoanRate(getBank().getLoanRate());
		loan.setStatus(Constants.LOAN_STATUS_WAIT);
		loan.setBeginTime(new Date());

		loan.save();
	}

	public void dealLoan(LoanModel model) throws BBSException {
		User user = model.getUser();
		Loan loan;
		try {
			loan = Loan.get(model.getId());
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		if (!loan.getUser().equals(user)) {
			throw new BBSException(BBSExceptionMessage.CANNOT_DEAL_LOAN);
		}
		switch (model.getAction()) {
		case 1: // 取消贷款申请
			loan.setStatus(Constants.LOAN_STATUS_CANCEL);
			loan.save();
			break;
		case 2: // 偿还贷款
			int money = loan.getMoney();
			int accrual = loan.getAccrual();
			if (!user.canPayFor(money + accrual)) {
				throw new BBSException(BBSExceptionMessage.OUT_OF_MONEY);
			}
			Bank bank = getBank();
			bank.addDeposit(money);
			bank.addLoan(0 - money);
			bank.addBankroll(accrual);
			loan.setApplyTime(new Date());
			loan.setStatus(Constants.LOAN_STATUS_REPAY);
			user.payFor(money + accrual);
			user.save();
			loan.save();
			bank.save();
			OperateLog.saveOperateLog(user, loan.getMoney(),
					loan.getLoanRate(), loan.getDays(), "偿还贷款(id:"
							+ loan.getId() + "),支付利息：" + accrual, new Date(),
					Constants.BANK_OP_TYPE_LOAN, null);
			break;
		default:
			throw new BBSException("操作类型错误！");
		}
	}

	public void virement(VirementModel model) throws BBSException {
		model.setBank(getBank());
		model.setCurrent(getAccounts(model.getUser()));
	}

	public void virementDo(VirementModel model) throws BBSException,
			ValidationException {
		// if (!bankPassword(getAccounts(model.getUser()), model.getBankpwd()))
		// {
		// throw new ValidationException(
		// BBSExceptionMessage.BANK_PASSWORD_INVALID);
		// }
		if (model.getMoney() < getBank().getVirementMin()) {
			throw new ValidationException("转帐金额最少不得小于："
					+ getBank().getVirementMin());
		}
		if (model.getMoney() > getBank().getVirementMax()) {
			throw new ValidationException("转帐金额最多不得大于："
					+ getBank().getVirementMax());
		}
		Accounts acc = getAccounts(model.getUser());
		List<String> nicks = Util.splitNicks(model.getUserNicks());
		// 转帐成功用户
		List<String> doneUsers = new LinkedList<String>();
		// 帐户余额不足，转帐失败用户
		List<String> unPayUsers = new LinkedList<String>();
		// 错误昵称，用户不存在
		List<String> notExistUsers = new LinkedList<String>();

		int money = model.getMoney();
		int virementCost = accountVirementCost(model.getUser(), money);

		squareAccrual(acc);// 结算利息
		for (int i = 0; i < nicks.size(); i++) {
			String nick = (String) nicks.get(i);
			try {
				User user = User.getByNick(nick);
				if (acc.canPayout(money + virementCost)) {
					Bank bank = getBank();
					acc.payout(money + virementCost);
					bank.addDeposit(0 - money - virementCost);
					bank.addBankroll(virementCost);
					user.addMoney(money);
					user.save();
					bank.save();
					acc.save();
					doneUsers.add(nick);
					OperateLog.saveOperateLog(model.getUser(), money, bank
							.getVirementRate(), 0, "转出(手续费:" + virementCost
							+ ")", new Date(), Constants.BANK_OP_TYPE_VIRE_OUT,
							user);
					OperateLog.saveOperateLog(user, money, bank
							.getVirementRate(), 0, "转入", new Date(),
							Constants.BANK_OP_TYPE_VIRE_IN, model.getUser());

					String str = model.getUser().getNick() + "成功转入给你" + money
							+ "西大币，详情请查看理财日志。";
					// 转账成功发送消息
					Message.sendMessage("转账信息", str, 1, user, model.getUser(),
							nick);
				} else {
					unPayUsers.add(nick);
				}
			} catch (ObjectNotFoundException e) {
				notExistUsers.add(nick);
			}
		}
		model.setDoneUsers(doneUsers);
		model.setUnPayUsers(unPayUsers);
		model.setNotExistUsers(notExistUsers);
	}

	public void editBankPasswordDo(EditPasswordModel model) throws BBSException {
		// Accounts acc = getAccounts(model.getUser());
		// if (!bankPassword(acc, model.getOldPassword())) {
		// throw new BBSException(BBSExceptionMessage.BANK_PASSWORD_INVALID);
		// }
		// acc.setPassword(Util.hash(model.getPassword()));
		// acc.save();
	}

	public void operateLog(OperateLogModel model) throws BBSException {
		User user = model.getUser();
		model.getPagination().setPageSize(15);
		switch (model.getType()) {
		case 0: // 其他记录
			model.setLogs(OperateLog.getByUser(user,
					Constants.BANK_OP_TYPE_OTHER, model.getPagination()));
			break;
		case 1: // 活期记录
			model.setLogs(OperateLog.getByUser(user,
					Constants.BANK_OP_TYPE_CURR, model.getPagination()));
			break;
		case 2: // 定期记录
			model.setLogs(OperateLog.getByUser(user,
					Constants.BANK_OP_TYPE_FIXED, model.getPagination()));
			break;
		case 3: // 贷款记录
			model.setLogs(OperateLog.getByUser(user,
					Constants.BANK_OP_TYPE_LOAN, model.getPagination()));
			break;
		case 4: // 转出记录
			model.setLogs(OperateLog.getByUser(user,
					Constants.BANK_OP_TYPE_VIRE_OUT, model.getPagination()));
			break;
		case 5: // 转入记录
			model.setLogs(OperateLog.getByUser(user,
					Constants.BANK_OP_TYPE_VIRE_IN, model.getPagination()));
			break;
		default:
			throw new BBSException("操作类型错误！");
		}
	}

	public void bankManage(BankManageModel model) throws BBSException {
		model.setBank(getBank());
		model.setBankAdmins(User.getUsers(model.getBank().getBankAdmins()));
		model
				.setApplyLoans(Loan
						.getApplyLoans(model.getType() == 0 ? Constants.LOAN_STATUS_WAIT
								: model.getType()));
	}

	public void bankModify(BankManageModel model) throws BBSException {
		Bank bank = getBank();
		bank.setOpenCost(model.getOpenCost());
		bank.setCurrentRate(model.getCurrentRate() / 1000);
		bank.setLoanRate(model.getLoanRate() / 1000);
		bank.setFixedRate(model.getFixedRate() / 1000);
		bank.setVirementRate(model.getVirementRate() / 1000);
		bank.save();
	}

	public void approachLoan(LoanModel model) throws ObjectNotFoundException {
		Loan loan = Loan.get(model.getId());
		loan.setStatus(Constants.LOAN_STATUS_PAST);
		User user = loan.getUser();
		user.addMoney(loan.getMoney());
		loan.save();
		user.save();
	}

	public void denyLoan(LoanModel model) throws ObjectNotFoundException {
		Loan loan = Loan.get(model.getId());
		loan.setStatus(Constants.LOAN_STATUS_UNPAST);
		loan.save();
	}

	public static void main(String[] args) throws BBSException{
		Calendar c=Calendar.getInstance();
		c.set(2011, 5, 12, 13, 21, 43);
		int price=1951;
		int total = (int) (price * 0.001);
		System.out.println("total:"+total);
		int days=Util.days(c.getTime(), new Date());
		int accrual = (int) (days * new BankServiceImpl().payTaxes(total));
		System.out.println(days+"天"+accrual);
		//System.out.println(new BankServiceImpl().accountAccrual(c.getTime(),1951));
	}
}
