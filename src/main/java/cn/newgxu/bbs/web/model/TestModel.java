package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.user.User;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.TestModel.java
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2012-4-1
 * @describe
 * 
 */
public class TestModel extends PaginationBaseModel {
	private User user;
	private List<User> activeUsers;

	public List<User> getActiveUsers() {
		return activeUsers;
	}

	
	public void setActiveUsers(List<User> activeUsers) {
		this.activeUsers = activeUsers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
