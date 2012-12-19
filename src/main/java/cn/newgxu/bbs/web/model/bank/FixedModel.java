package cn.newgxu.bbs.web.model.bank;

import java.util.List;

import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.bank.Fixed;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class FixedModel {

	private int id;
	private int money;
	private int days;
	private String bankpwd;
	private Bank bank;
	private User user;
	private List<Fixed> myFixeds;
	
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public List<Fixed> getMyFixeds() {
		return myFixeds;
	}
	public void setMyFixeds(List<Fixed> myFixeds) {
		this.myFixeds = myFixeds;
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
	
}
