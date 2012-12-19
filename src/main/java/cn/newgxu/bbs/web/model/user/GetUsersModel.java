package cn.newgxu.bbs.web.model.user;

import java.util.List;

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class GetUsersModel extends PaginationBaseModel {

	private int type;

	private String keywords;

	private List<User> users;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
