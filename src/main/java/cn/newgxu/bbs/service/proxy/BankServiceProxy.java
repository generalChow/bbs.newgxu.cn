package cn.newgxu.bbs.service.proxy;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.ValidationUtil;
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
public class BankServiceProxy implements BankService {

	private BankService bankService;

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void bank(BankModel model) throws BBSException {
		this.bankService.bank(model);
	}

	public void openAccounts(OpenAccountsModel model) throws BBSException, ValidationException {
//		ValidationUtil.password(model.getPassword(), model.getConfirmPassword());
		ValidationUtil.openMoney(model.getMoney());
		this.bankService.openAccounts(model);
	}

	public void current(CurrentModel model) throws BBSException {
		this.bankService.current(model);
	}

	public void access(CurrentModel model) throws ValidationException, BBSException {
		ValidationUtil.money(model.getMoney());
		this.bankService.access(model);
	}

	public void manual(CurrentModel model) throws BBSException {
		this.bankService.manual(model);
	}

	public void fixed(FixedModel model) throws BBSException {
		this.bankService.fixed(model);
	}

	public void newFixed(FixedModel model) throws BBSException, ValidationException {
		ValidationUtil.money(model.getMoney());
		ValidationUtil.days(model.getDays());
		this.bankService.newFixed(model);
	}

	public void drawFixed(FixedModel model) throws BBSException {
		this.bankService.drawFixed(model);
	}

	public void loan(LoanModel model) throws BBSException {
		this.bankService.loan(model);
	}
	public void newLoan(LoanModel model) throws BBSException, ValidationException {
		ValidationUtil.money(model.getMoney());
		ValidationUtil.days(model.getDays());
		this.bankService.newLoan(model);
	}

	public void dealLoan(LoanModel model) throws BBSException {
		this.bankService.dealLoan(model);
	}

	public void virement(VirementModel model) throws BBSException {
		this.bankService.virement(model);
	}

	public void virementDo(VirementModel model) throws BBSException, ValidationException {
		ValidationUtil.money(model.getMoney());
		ValidationUtil.nicks(model.getUserNicks());
		this.bankService.virementDo(model);
	}

	public void editBankPasswordDo(EditPasswordModel model) throws BBSException, ValidationException {
		ValidationUtil.password(model.getPassword(), model.getConfirmPassword());
		this.bankService.editBankPasswordDo(model);
	}

	public void operateLog(OperateLogModel model) throws BBSException {
		this.bankService.operateLog(model);
	}

	public void bankManage(BankManageModel model) throws BBSException {
		this.bankService.bankManage(model);
	}

	public void bankModify(BankManageModel model) throws BBSException {
		this.bankService.bankModify(model);
	}

	public void approachLoan(LoanModel model) throws ObjectNotFoundException {
		this.bankService.approachLoan(model);
	}

	public void denyLoan(LoanModel model) throws ObjectNotFoundException {
		this.bankService.denyLoan(model);
	}


}
