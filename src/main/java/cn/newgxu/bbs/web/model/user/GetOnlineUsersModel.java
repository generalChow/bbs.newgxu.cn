package cn.newgxu.bbs.web.model.user;

import java.util.List;

import cn.newgxu.bbs.domain.user.OnlineUser;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class GetOnlineUsersModel extends PaginationBaseModel {

	private int type = 0;

	private int forumId = 0;

	private List<OnlineUser> onlineUsers;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public List<OnlineUser> getOnlineUsers() {
		return onlineUsers;
	}

	public void setOnlineUsers(List<OnlineUser> onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

}
