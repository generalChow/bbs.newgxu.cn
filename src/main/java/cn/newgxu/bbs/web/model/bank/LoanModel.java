package cn.newgxu.bbs.web.model.bank;

import java.util.List;

import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.bank.Loan;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class LoanModel {

	private int id;
	private int money;
	private int days;
	private String bankpwd;
	private int action;
	private Bank bank;
	private User user;
	private List<Loan> myLoans;
	private List<Loan> myApplyLoans;
	
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public List<Loan> getMyApplyLoans() {
		return myApplyLoans;
	}
	public void setMyApplyLoans(List<Loan> myApplyLoans) {
		this.myApplyLoans = myApplyLoans;
	}
	public List<Loan> getMyLoans() {
		return myLoans;
	}
	public void setMyLoans(List<Loan> myLoans) {
		this.myLoans = myLoans;
	}
	
}
