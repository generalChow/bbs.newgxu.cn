package cn.newgxu.bbs.web.model.bank;

import java.util.List;

import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BankModel {
	
	private Bank bank;
	private User user;
	private List<User> bankAdmins;
	private boolean hasAccounts = false;

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

	public List<User> getBankAdmins() {
		return bankAdmins;
	}

	public void setBankAdmins(List<User> bankAdmins) {
		this.bankAdmins = bankAdmins;
	}

	public boolean isHasAccounts() {
		return hasAccounts;
	}

	public void setHasAccounts(boolean hasAccounts) {
		this.hasAccounts = hasAccounts;
	}
}
