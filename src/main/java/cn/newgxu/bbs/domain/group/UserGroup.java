package cn.newgxu.bbs.domain.group;

import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@SuppressWarnings("serial")
public abstract class UserGroup extends JPAEntity {

	protected Permission permission;

	public boolean isAuthViewForum(Forum forum, User user) {
		return permission.canViewForum(forum, user);
	}

	public boolean isAuthLock(Topic topic, User user) {
		return permission.canLock(topic, user);
	}

	public boolean isAuthModify(Topic topic, User user) {
		return permission.canModify(topic, user);
	}

	public boolean isAuthModify(Reply reply, User user) {
		return permission.canModify(reply, user);
	}

	public boolean isAuthRepair(Topic topic, User user) {
		return permission.canRepair(topic, user);
	}

	public boolean isAuthDelReply(Reply reply, User user) {
		return permission.canDelReply(reply, user);
	}

	public boolean isAuthScreen(Reply reply, User user) {
		return permission.canScreen(reply, user);
	}

	public boolean isAuthSetAllTop(Topic topic, User user) {
		return permission.canSetAllTop(topic, user);
	}

	public boolean isAuthSetAreaTop(Topic topic, User user) {
		return permission.canSetAreaTop(topic, user);
	}

	public boolean isAuthSetTop(Topic topic, User user) {
		return permission.canSetTop(topic, user);
	}

	public boolean isAuthUnSetTop(Topic topic, User user) {
		return permission.canUnSetTop(topic, user);
	}

	public boolean isAuthSetGood(Topic topic, User user) {
		return permission.canSetGood(topic, user);
	}

	public boolean isAuthSetLight(Topic topic, User user) {
		return permission.canSetLight(topic, user);
	}

	public boolean isAuthPub(Topic topic, User user) {
		return permission.canPub(topic, user);
	}
	
	public boolean isAuthDelete(Topic topic, User user) {
		return permission.canDelete(topic, user);
	}

	public boolean isAuthMoveTopic(Topic topic, User user) {
		return permission.canMoveTopic(topic, user);
	}

	public boolean isAuthDelSmallNews(SmallNews smallNews, User user) {
		return permission.canDelSmallNews(smallNews, user);
	}

	public boolean isAuthViewLastLogTime() {
		return permission.canViewLastLogTime();
	}
	
	public boolean isAuthViewTrueName() {
		return permission.canViewTrueName();
	}

	public boolean isAuthViewUserIp() {
		return permission.canViewUserIp();
	}

	public boolean isInVirtualTime() {
		return permission.isInVirtualTime();
	}

	public boolean isAuthCreateTopic() {
		return isInVirtualTime();
	}

	public boolean isAuthCreateVote() {
		return isInVirtualTime();
	}

	public boolean isAuthReply() {
		return isInVirtualTime();
	}

	public boolean isAuthLeavelUp(User user) {
		return permission.canLeavelUp(user);
	}

	public boolean isEditTitleFree() {
		return permission.isEditTitleFree();
	}
	
	public  boolean canViewUserIp(){
		return permission.canViewUserIp();
	}
	
	public abstract int getId();
	
	public abstract int getTypeId();
	
	public abstract int getAgio();

	public abstract int getMaxPower();

	public abstract String getGroupName();

	public abstract String getDisplayColor();

	public abstract void leavelUp(User user);

	public abstract UserGroup getGroupByExp(int exp);

	public abstract UserGroup getNextGroup();

	public abstract int getLeavelUpExp();
	
	

}
