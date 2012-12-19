package cn.newgxu.bbs.web.model.admin;

import java.util.List;

import cn.newgxu.bbs.domain.group.UserGroup;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class GroupsManageModel {

	private List<UserGroup> administratorGroups;
	private List<UserGroup> areaWebmasterGroups;
	private List<UserGroup> forumWebmasterGroups;
	private List<UserGroup> basicGroups;

	public List<UserGroup> getAdministratorGroups() {
		return administratorGroups;
	}

	public void setAdministratorGroups(List<UserGroup> administratorGroups) {
		this.administratorGroups = administratorGroups;
	}

	public List<UserGroup> getAreaWebmasterGroups() {
		return areaWebmasterGroups;
	}

	public void setAreaWebmasterGroups(List<UserGroup> areaWebmasterGroups) {
		this.areaWebmasterGroups = areaWebmasterGroups;
	}

	public List<UserGroup> getBasicGroups() {
		return basicGroups;
	}

	public void setBasicGroups(List<UserGroup> basicGroups) {
		this.basicGroups = basicGroups;
	}

	public List<UserGroup> getForumWebmasterGroups() {
		return forumWebmasterGroups;
	}

	public void setForumWebmasterGroups(List<UserGroup> forumWebmasterGroups) {
		this.forumWebmasterGroups = forumWebmasterGroups;
	}

}
