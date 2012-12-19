package cn.newgxu.bbs.web.model;

import java.util.LinkedList;
import java.util.List;

import cn.newgxu.bbs.common.util.ForumControlUtil;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.ReplyLine;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class TopicModel extends PaginationBaseModel {

	private User user;

	private int forumId;

	private int topicId;

	private Forum forum;

	private Topic topic;
	
	private boolean voteable;

	private List<ReplyLine> replieLines = new LinkedList<ReplyLine>();
	
	private List<Area> areas = new LinkedList<Area>();

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ReplyLine> getReplieLines() {
		return replieLines;
	}

	public void setReplieLines(List<ReplyLine> replieLines) {
		this.replieLines = replieLines;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public boolean isVoteable() {
		return voteable;
	}

	public void setVoteable(boolean voteable) {
		this.voteable = voteable;
	}

	public boolean getIsClose(){
		return ForumControlUtil.isForumClose();
	}
}
