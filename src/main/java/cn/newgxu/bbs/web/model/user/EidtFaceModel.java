package cn.newgxu.bbs.web.model.user;

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EidtFaceModel extends PaginationBaseModel {

	private User user;

	private String face;

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
