package cn.newgxu.bbs.domain.group.impl;

import java.util.List;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AreaWebmasterPermission extends AdministratorPermission {

	private boolean isManagingArea(List<Area> areas, Area currentArea) {
		for (Area area : areas) {
			if (area.equals(currentArea)) {
				return true;
			}
		}
		return false;
	}

	public boolean canSetAllTop(Topic topic, User user) {
		return false;
	}

	public boolean canSetAreaTop(Topic topic, User user) {
		if (isManagingArea(user.getManagingAreas(), topic.getForum().getArea())) {
			return true;
		}
		return false;
	}

	public boolean canSetTop(Topic topic, User user) {
		if (isManagingArea(user.getManagingAreas(), topic.getForum().getArea())) {
			return true;
		}
		return false;
	}

	public boolean canUnSetTop(Topic topic, User user) {
		if (isManagingArea(user.getManagingAreas(), topic.getForum().getArea())) {
			return true;
		}
		return false;
	}

	public boolean isCanViewTrueName() {
		return false;
	}
	

}
