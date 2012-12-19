package cn.newgxu.bbs.domain.group.impl;

import java.util.List;

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
public class ForumWebmasterPermission implements Permission {

	public boolean canViewForum(Forum forum, User user) {
		if (forum.isConfrere() && !user.isConfrere()) {
			// 站员交流版
			return false;
		}
		return true;
	}

	private boolean isManagingForum(List<Forum> forums, Forum currentForum) {
		//一个版块被删除后，会影响参与此版块的用户修改其他帖子的数据2010-11-23
		try{
			for (Forum forum : forums) {
				if (forum.equals(currentForum)) {
					return true;
				}
			}
		}catch (Exception e) {
		}
		
		return false;
	}

	public boolean canLock(Topic topic, User user) {
		if (isManagingForum(user.getManagingForums(), topic.getForum())) {
			return true;
		}
		if (user.isOwnTopic(topic)) {
			return true;
		}
		return false;
	}

	public boolean canModify(Topic topic, User user) {
		return canLock(topic, user);
	}

	public boolean canModify(Reply reply, User user) {
		if (isManagingForum(user.getManagingForums(), reply.getTopic()
				.getForum())) {
			return true;
		}
		if (user.isOwnReply(reply)) {
			return true;
		}

		return false;
	}

	public boolean canRepair(Topic topic, User user) {
		return canLock(topic, user);
	}

	public boolean canScreen(Reply reply, User user) {
		return canModify(reply, user);
	}

	public boolean canSetAllTop(Topic topic, User user) {
		return false;
	}

	public boolean canSetAreaTop(Topic topic, User user) {
		return false;
	}

	public boolean canSetTop(Topic topic, User user) {
		if (isManagingForum(user.getManagingForums(), topic.getForum())) {
			return true;
		}
		return false;
	}

	public boolean canUnSetTop(Topic topic, User user) {
		if (topic.getTopType() > 1) {
			return false;
		}
		if (isManagingForum(user.getManagingForums(), topic.getForum())) {
			return true;
		}
		return false;
	}

	public boolean canPub(Topic topic, User user) {
		if (isManagingForum(user.getManagingForums(), topic.getForum())) {
			return true;
		}
		return false;
	}

	public boolean canSetGood(Topic topic, User user) {
		if (isManagingForum(user.getManagingForums(), topic.getForum())) {
			return true;
		}
		return false;
	}

	public boolean canSetLight(Topic topic, User user) {
		if (isManagingForum(user.getManagingForums(), topic.getForum())) {
			return true;
		}
		return false;
	}

	public boolean canDelete(Topic topic, User user) {
		if (isManagingForum(user.getManagingForums(), topic.getForum())) {
			return true;
		}
		return false;
	}

	public boolean canMoveTopic(Topic topic, User user) {
		if (isManagingForum(user.getManagingForums(), topic.getForum())) {
			return true;
		}
		return false;
	}

	public boolean canDelReply(Reply reply, User user) {
		if (isManagingForum(user.getManagingForums(), reply.getTopic()
				.getForum())) {
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

	public boolean canViewUserIp() {
		return false;
	}

	public boolean isInVirtualTime() {
		return true;
	}

	public boolean canLeavelUp(User user) {
		return true;
	}

	public boolean isEditTitleFree() {
		return true;
	}

}
