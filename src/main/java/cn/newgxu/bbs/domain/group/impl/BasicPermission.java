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
public class BasicPermission implements Permission {

	public boolean canViewForum(Forum forum, User user) {
		if (!forum.isSecrecy() && !forum.isConfrere()) {
			// 非限制版块
			return true;
		}
		if (forum.isConfrere() && user.isConfrere()) {
			// 站员交流版
			return true;
		}
		return false;
	}

	public boolean canLock(Topic topic, User user) {
		return false;
	}

	public boolean canModify(Topic topic, User user) {
		if (user.isOwnTopic(topic)) {
			return true;
		}
		return false;
	}

	public boolean canModify(Reply reply, User user) {
		if (user.isOwnReply(reply)) {
			return true;
		}
		return false;
	}

	public boolean canRepair(Topic topic, User user) {
		if (user.isOwnTopic(topic)) {
			return true;
		}
		return false;
	}

	public boolean canScreen(Reply reply, User user) {
		if (user.isOwnReply(reply)) {
			return true;
		}
		return false;
	}

	public boolean canSetAllTop(Topic topic, User user) {
		return false;
	}

	public boolean canSetAreaTop(Topic topic, User user) {
		return false;
	}

	public boolean canSetTop(Topic topic, User user) {
		return false;
	}

	public boolean canUnSetTop(Topic topic, User user) {
		return false;
	}

	public boolean canPub(Topic topic, User user) {
		return false;
	}

	public boolean canSetGood(Topic topic, User user) {
		return false;
	}

	public boolean canSetLight(Topic topic, User user) {
		return false;
	}

	public boolean canDelete(Topic topic, User user) {
		if (user.isOwnTopic(topic))
			return true;
		return false;
	}

	public boolean canMoveTopic(Topic topic, User user) {
		return false;
	}

	public boolean canDelReply(Reply reply, User user) {
		if (user.isOwnReply(reply)) {
			return true;
		}
		return false;
	}

	public boolean canDelSmallNews(SmallNews smallNews, User user) {
		if (user.isOwnSmallNews(smallNews)) {
			return true;
		}
		return false;
	}

	public boolean canViewLastLogTime() {
		return false;
	}

	public boolean canViewTrueName() {
		return false;
	}

	public boolean isInVirtualTime() {
		return true;
	}

	public boolean canLeavelUp(User user) {
		return true;
	}

	public boolean isEditTitleFree() {
		return false;
	}

	public boolean canViewUserIp() {
		return false;
	}

}
