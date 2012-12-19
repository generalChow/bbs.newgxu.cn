package cn.newgxu.bbs.domain.group;

import java.util.List;

import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class GroupManager {

	public static final int BASIC_GROUP = 1;

	public static final int FORUM_WEBMASTER_GROUP = 2;

	public static final int AREA_WEBMASTER_GROUP = 3;

	public static final int ADMINISTRATOR_GROUP = 4;

	public static final int AUTHORIZATION_GROUP = 5;

	/**
	 * 获取用户组类别 普通用户 GroupTypeId=1 斑竹 GroupTypeId=2 区管理员 GroupTypeId=3 超级管理员
	 * GroupTypeId=4 return UserGroup
	 */
	public static UserGroup getUserGroup(int groupTypeId) {
		if (groupTypeId == BASIC_GROUP) {
			return new BasicGroup();
		} else if (groupTypeId == FORUM_WEBMASTER_GROUP) {
			return new ForumWebmasterGroup();
		} else if (groupTypeId == AREA_WEBMASTER_GROUP) {
			return new AreaWebmasterGroup();
		} else if (groupTypeId == ADMINISTRATOR_GROUP) {
			return new AdministratorGroup();
		} else if (groupTypeId == AUTHORIZATION_GROUP) {
			return new AuthorizationGroup();
		} else {
			throw new RuntimeException("无法判断的用户组类型!");
		}
	}

	public static UserGroup getUserGroup(int groupTypeId, int groupId) {
		try {
			return (UserGroup) getUserGroup(groupTypeId).getClass().getMethod(
					"getCertainExist", new Class[] { int.class }).invoke(null,
					groupId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void changeGroup(User user, int groupTypeId, int groupId) {
		UserGroup to = null;
		// try {
		// to = getUserGroup(groupTypeId, groupId);
		// } catch (Exception e) {
		// to = getUserGroup(groupTypeId).getGroupByExp(user.getExp());
		// }
		to = getUserGroup(groupTypeId).getGroupByExp(user.getExp());

		if (to == null) {
			throw new RuntimeException("无法根据用户经验值匹配正确的用户组，请选择！");
		}

		user.setGroupTypeId(to.getTypeId());
		user.setGroupId(to.getId());
	}

	@SuppressWarnings("unchecked")
	public static List<UserGroup> getGroups(int groupTypeId) {
		try {
			return (List<UserGroup>) getUserGroup(groupTypeId).getClass()
					.getMethod("getGroups").invoke(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
