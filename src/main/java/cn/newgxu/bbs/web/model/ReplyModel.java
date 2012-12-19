package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ReplyModel {
	private boolean ifPhone;//是否手机访问
	
	private String content;

	private int forumId;

	private int topicId;

	private int replyId;
	
	private int userId;
	
	private int replyContentId;
	
	private User user;

	private Forum forum;

	private Topic topic;
	
	private int page = 1;

	private List<Area> areas;

	private String action;
	
	// add @2012-06-12
	private Reply reply;
	private int synchronousTwitter;

	public Reply getReply() {
		return reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}

	
	public int getSynchronousTwitter() {
		return synchronousTwitter;
	}

	
	public void setSynchronousTwitter(int synchronousTwitter) {
		this.synchronousTwitter = synchronousTwitter;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getReplyContentId() {
		return replyContentId;
	}

	public void setReplyContentId(int replyContentId) {
		this.replyContentId = replyContentId;
	}

	public boolean isIfPhone() {
		return ifPhone;
	}

	public void setIfPhone(boolean ifPhone) {
		this.ifPhone = ifPhone;
	}
}
