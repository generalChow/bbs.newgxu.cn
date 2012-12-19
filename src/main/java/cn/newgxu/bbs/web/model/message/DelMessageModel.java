package cn.newgxu.bbs.web.model.message;

import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DelMessageModel {

	private int[] smid;

	private User user;

	public int[] getSmid() {
		return smid;
	}

	public void setSmid(int[] smid) {
		this.smid = smid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
