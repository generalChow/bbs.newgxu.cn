package cn.newgxu.bbs.domain.bank;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * Bank generated by MyEclipse - Hibernate Tools
 */

@Entity
@Table(name = "bank")
public class Bank extends JPAEntity {

	private static final long serialVersionUID = -8199261655207027555L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_bank_operate_log")
	private int id = -1;

	private String name;
	private String admins;

	// 投资总额
	private int investment;
	// 实际资本
	private int bankroll;
	// 银行存款
	private int deposit;
	// 存款中定期总额
	private int fixed;
	// 银行贷出总额
	private int loan;

	@Column(name = "user_number")
	private int userNumber;

	@Column(name = "open_cost")
	private int openCost;

	@Column(name = "current_rate", precision = 4)
	private float currentRate;

	@Column(name = "fixed_rate", precision = 4)
	private float fixedRate;

	@Column(name = "loan_rate", precision = 4)
	private float loanRate;

	@Column(name = "virement_rate", precision = 4)
	private float virementRate;

	@Column(name = "fix_min_days")
	private int fixMinDays;

	@Column(name = "virement_min")
	private int virementMin;

	@Column(name = "virement_max")
	private int virementMax;

	// Property accessors

	public int getVirementMax() {
		return virementMax;
	}

	public void setVirementMax(int virementMax) {
		this.virementMax = virementMax;
	}

	public int getVirementMin() {
		return virementMin;
	}

	public void setVirementMin(int virementMin) {
		this.virementMin = virementMin;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdmins() {
		return this.admins;
	}

	public void setAdmins(String admins) {
		this.admins = admins;
	}

	public int getInvestment() {
		return this.investment;
	}

	public void setInvestment(int investment) {
		this.investment = investment;
	}

	public int getBankroll() {
		return this.bankroll;
	}

	public void setBankroll(int bankroll) {
		this.bankroll = bankroll;
	}

	public int getDeposit() {
		return this.deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public int getUserNumber() {
		return this.userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public int getOpenCost() {
		return this.openCost;
	}

	public void setOpenCost(int openCost) {
		this.openCost = openCost;
	}

	public float getCurrentRate() {
		return this.currentRate;
	}

	public void setCurrentRate(float currentRate) {
		this.currentRate = currentRate;
	}

	public float getFixedRate() {
		return this.fixedRate;
	}

	public void setFixedRate(float fixedRate) {
		this.fixedRate = fixedRate;
	}

	public float getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(float loanRate) {
		this.loanRate = loanRate;
	}

	public float getVirementRate() {
		return this.virementRate;
	}

	public void setVirementRate(float virementRate) {
		this.virementRate = virementRate;
	}

	public int getFixMinDays() {
		return fixMinDays;
	}

	public void setFixMinDays(int fixMinDays) {
		this.fixMinDays = fixMinDays;
	}

	public int getFixed() {
		return fixed;
	}

	public void setFixed(int fixed) {
		this.fixed = fixed;
	}

	public int getLoan() {
		return loan;
	}

	public void setLoan(int loan) {
		this.loan = loan;
	}

	// -----------------------

	public static synchronized Bank get(int id) throws ObjectNotFoundException {
		return (Bank) getById(Bank.class, id);
	}

	public static Bank get() throws ObjectNotFoundException {
		return get(1);
	}

	public int[] getBankAdmins() {
		return Util.splitIds(this.admins);
	}

	public boolean isBankAdmin(User user) {
		int[] ids = getBankAdmins();
		for (int id : ids) {
			if (id == user.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 银行存取款。
	 * 
	 * @param money
	 *            操作金额。
	 */
	public void addDeposit(int money) {
		this.deposit += money;
		this.deposit = this.deposit < 0 ? 0 : this.deposit;
	}

	/**
	 * 银行实际资本进出。
	 * 
	 * @param money
	 *            操作金额。
	 */
	public void addBankroll(int money) {
		this.bankroll += money;
		this.bankroll = this.bankroll < 0 ? 0 : this.bankroll;
	}

	/**
	 * 银行定期资金进出。
	 * 
	 * @param money
	 *            操作金额。
	 */
	public void addFixed(int money) {
		this.fixed += money;
		this.fixed = this.fixed < 0 ? 0 : this.fixed;
	}

	/**
	 * 银行贷款资金进出。
	 * 
	 * @param money
	 *            操作金额。
	 */
	public void addLoan(int money) {
		this.loan += money;
		this.loan = this.loan < 0 ? 0 : this.loan;
	}

	/**
	 * 增加银行用户数量。
	 */
	public void addUserNumber(int num) {
		this.userNumber += num;
	}

	/**
	 * 社区基金支出。
	 * 
	 * @param money
	 *            操作金额。
	 */
	public void payout(int money) {
		addBankroll(0 - money);
	}

	/**
	 * 社区基金收入。
	 * 
	 * @param money
	 *            操作金额。
	 */
	public void income(int money) {
		addBankroll(money);
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "bank" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("name", name);
				put("admins", admins);
				put("userNumber", userNumber);
				put("openCost", openCost);
				put("currentRate", currentRate);
				put("fixedRate", fixedRate);
				put("loanRate", loanRate);
				put("virementRate", virementRate);
				put("investment", investment);
				put("bankroll", bankroll);
				put("deposit", deposit);
			}
		}.toString();
	}

}