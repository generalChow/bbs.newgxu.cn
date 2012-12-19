package cn.newgxu.bbs.web.model.bank;

import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OpenAccountsModel {

	private int money;
	
//	private String password;
//	
//	private String confirmPassword;
	
	private User user;
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
