package cn.newgxu.bbs.web.model.user;

import java.util.List;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.group.UserGroup;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class UpgradeModel {

	private User user;

	private UserGroup userGroup;

	private UserGroup nextUserGroup;
	
	private List<Area> areas;

	public float getExpStatus() {
		if (nextUserGroup == null) {
			return 0;
		}

		float expStatus = (float) user.getExp()
				/ nextUserGroup.getLeavelUpExp();
		return expStatus > 1 ? 1 : expStatus;
	}

	public UserGroup getNextUserGroup() {
		return nextUserGroup;
	}

	public void setNextUserGroup(UserGroup nextUserGroup) {
		this.nextUserGroup = nextUserGroup;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	
}
