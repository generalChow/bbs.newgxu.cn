package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.domain.group.GroupManager;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.GroupsManageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class GroupsManagerAction extends AbstractBaseAction {

	private static final long serialVersionUID = -1047332308829561598L;

	private GroupsManageModel model = new GroupsManageModel();

	@Override
	public String execute() throws Exception {
		model.setAdministratorGroups(userService
				.getGroups(GroupManager.ADMINISTRATOR_GROUP));
		model.setAreaWebmasterGroups(userService
				.getGroups(GroupManager.AREA_WEBMASTER_GROUP));
		model.setForumWebmasterGroups(userService
				.getGroups(GroupManager.FORUM_WEBMASTER_GROUP));
		model.setBasicGroups(userService.getGroups(GroupManager.BASIC_GROUP));
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
