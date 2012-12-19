package cn.newgxu.bbs.web.model.bank;

import java.util.List;

import cn.newgxu.bbs.domain.bank.Accounts;
import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class VirementModel {

	private int action;
	private int money;
	private String bankpwd;
	private String userNicks;
	private Bank bank;
	private User user;
	private Accounts current;
	// 转帐成功用户
	private List<String> doneUsers;
	// 帐户余额不足，转帐失败用户
	private List<String> unPayUsers;
	// 错误昵称，用户不存在
	private List<String> notExistUsers;

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getBankpwd() {
		return bankpwd;
	}

	public void setBankpwd(String bankpwd) {
		this.bankpwd = bankpwd;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserNicks() {
		return userNicks;
	}

	public void setUserNicks(String userNicks) {
		this.userNicks = userNicks;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Accounts getCurrent() {
		return current;
	}

	public void setCurrent(Accounts current) {
		this.current = current;
	}

	public List<String> getDoneUsers() {
		return doneUsers;
	}

	public void setDoneUsers(List<String> doneUsers) {
		this.doneUsers = doneUsers;
	}

	public List<String> getNotExistUsers() {
		return notExistUsers;
	}

	public void setNotExistUsers(List<String> notExistUsers) {
		this.notExistUsers = notExistUsers;
	}

	public List<String> getUnPayUsers() {
		return unPayUsers;
	}

	public void setUnPayUsers(List<String> unPayUsers) {
		this.unPayUsers = unPayUsers;
	}
}
