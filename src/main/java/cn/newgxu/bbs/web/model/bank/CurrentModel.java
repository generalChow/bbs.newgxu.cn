package cn.newgxu.bbs.web.model.bank;

import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.bank.Accounts;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CurrentModel {

	private int money;
	private int accrual;
	private int action;
	private String bankpwd;
	private Bank bank;
	private User user;
	private Accounts current;
	
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Accounts getCurrent() {
		return current;
	}
	public void setCurrent(Accounts current) {
		this.current = current;
	}
	public String getBankpwd() {
		return bankpwd;
	}
	public void setBankpwd(String bankpwd) {
		this.bankpwd = bankpwd;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getAccrual() {
		return accrual;
	}
	public void setAccrual(int accrual) {
		this.accrual = accrual;
	}
	
}
