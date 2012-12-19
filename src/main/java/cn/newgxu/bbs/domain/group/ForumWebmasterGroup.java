package cn.newgxu.bbs.domain.group;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.domain.group.impl.ForumWebmasterPermission;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "forum_webmaster_group")
public class ForumWebmasterGroup extends UserGroup {

	private static final long serialVersionUID = -2894731136577661265L;

	private static final Log log = LogFactory.getLog(ForumWebmasterGroup.class);

	public ForumWebmasterGroup() {
		permission = new ForumWebmasterPermission();
	}

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq",
	// sequenceName="seq_forum_webmaster_group")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "max_power")
	private int maxPower;

	@Column(name = "leavel_up_exp")
	private int leavelUpExp;

	private int encouragement;

	private int agio;

	@Column(name = "display_color")
	private String displayColor;

	@Override
	public int getAgio() {
		return agio;
	}

	@Override
	public String getDisplayColor() {
		return displayColor;
	}

	@Override
	public String getGroupName() {
		return groupName;
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}

	public int getEncouragement() {
		return encouragement;
	}

	public void setEncouragement(int encouragement) {
		this.encouragement = encouragement;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeavelUpExp() {
		return leavelUpExp;
	}

	public void setLeavelUpExp(int leavelUpExp) {
		this.leavelUpExp = leavelUpExp;
	}

	public void setAgio(int agio) {
		this.agio = agio;
	}

	public void setDisplayColor(String displayColor) {
		this.displayColor = displayColor;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	// ------------------------------------------------
	public static ForumWebmasterGroup get(int groupId)
			throws ObjectNotFoundException {
		return (ForumWebmasterGroup) getById(ForumWebmasterGroup.class, groupId);
	}

	public static ForumWebmasterGroup getCertainExist(int groupId) {
		try {
			return get(groupId);
		} catch (ObjectNotFoundException e) {
			log.error(e);
			log.error("严重错误：forumWebmasterGroup 没有找到。 id=" + groupId);
			throw new RuntimeException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserGroup getGroupByExp(int exp) {
		List<UserGroup> groups = Q(
				"from ForumWebmasterGroup where leavelUpExp <= ?1 order by leavelUpExp desc",
				P(1, exp)).getResultList();
		if (groups.size() >= 1) {
			try {
				return groups.get(0);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserGroup getNextGroup() {
		List<UserGroup> groups = Q(
				"from ForumWebmasterGroup where leavelUpExp > ?1 order by leavelUpExp asc",
				P(1, this.leavelUpExp)).getResultList();
		if (groups.size() >= 1) {
			try {
				return groups.get(0);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public int getTypeId() {
		return GroupManager.FORUM_WEBMASTER_GROUP;
	}

	@SuppressWarnings("unchecked")
	public static List<UserGroup> getGroups() {
		return (List<UserGroup>) Q("from ForumWebmasterGroup order by id asc")
				.getResultList();
	}

	public void leavelUp(User user) {
		ForumWebmasterGroup group = (ForumWebmasterGroup) getNextGroup();
		if (group == null) {
			throw new RuntimeException();
		}
		if (!isAuthLeavelUp(user)) {
			return;
		}

		user.setGroupId(group.getId());
		user.addMoney(group.getEncouragement());
		user.update();
	}

	@Override
	public boolean isAuthLeavelUp(User user) {
		if (user.getExp() >= getLeavelUpExp())
			return true;
		else
			return false;
	}

}
