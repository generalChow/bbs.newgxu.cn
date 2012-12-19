package cn.newgxu.bbs.domain.group.impl;

import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.group.Permission;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AdministratorPermission implements Permission {

	public boolean canViewForum(Forum forum, User user) {
		return true;
	}

	public boolean canLock(Topic topic, User user) {
		return true;
	}

	public boolean canModify(Topic topic, User user) {
		return true;
	}

	public boolean canModify(Reply reply, User user) {
		return true;
	}

	public boolean canRepair(Topic topic, User user) {
		return true;
	}

	public boolean canScreen(Reply reply, User user) {
		return true;
	}

	public boolean canSetAllTop(Topic topic, User user) {
		return true;
	}

	public boolean canSetAreaTop(Topic topic, User user) {
		return true;
	}

	public boolean canSetTop(Topic topic, User user) {
		return true;
	}

	public boolean canUnSetTop(Topic topic, User user) {
		return true;
	}
	
	public boolean canSetGood(Topic topic, User user) {
		return true;
	}

	public boolean canSetLight(Topic topic, User user) {
		return true;
	}

	public boolean canPub(Topic topic, User user) {
		return true;
	}

	public boolean canDelete(Topic topic, User user) {
		return true;
	}

	public boolean canMoveTopic(Topic topic, User user) {
		return true;
	}

	public boolean canDelReply(Reply reply, User user) {
		return true;
	}

	public boolean canDelSmallNews(SmallNews smallNews, User user) {
		return true;
	}

	public boolean canViewLastLogTime() {
		return true;
	}

	public boolean canViewTrueName() {
		return true;
	}
	
	public boolean canViewUserIp() {
		return true;
	}

	public boolean isInVirtualTime() {
		return true;
	}

	public boolean canLeavelUp(User user) {
		return false;
	}

	public boolean isEditTitleFree() {
		return true;
	}

}
