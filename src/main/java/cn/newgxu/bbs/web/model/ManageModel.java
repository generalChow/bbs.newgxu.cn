package cn.newgxu.bbs.web.model;

import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ManageModel {

	public static final int DELETE_TOPIC_LOGID = 1; // 删除
	public static final int REPAIR_TOPIC_LOGID = 2; // 修复
	public static final int SETTOP_LOGID = 3; // 置顶
	public static final int SETTOP_GROUP_LOGID = 4; // 区置顶
	public static final int SETTOP_ALL_LOGID = 5; // 总置顶
	public static final int SET_GOOD_LOGID = 6; // 精华
	public static final int LOCK_LOGID = 7; // 锁定
	public static final int PUB_LOGID = 8; // 推荐
	public static final int UN_SETTOP_LOGID = 9; // 取消置顶
	public static final int UN_SET_GOOD_LOGID = 10; // 取消精华
	public static final int UN_PUB_LOGID = 11; // 取消推荐
	public static final int SHIELD_LOGID = 12; // 屏蔽发言
	public static final int DELETE_RE_LOGID = 13; // 删除回复
	public static final int UN_SET_LOCK_LOGID = 14; // 撤销锁定
	public static final int MOVE_TOPIC_LOGID = 15; // 转移主题
	public static final int LIGHT_TOPIC = 16; // 加亮主题
	public static final int UNLIGHT_TOPIC = 17; // 取消加亮
	
	public static final int SEARCH_TREASURE = 18; // 设置为寻宝帖

	private int forumId;

	private int topicId;

	private int replyId;

	private int pubId;

	private int toForumId;

	private String action;

	private User user;

	private int typeid;

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}

	public int getToForumId() {
		return toForumId;
	}

	public void setToForumId(int toForumId) {
		this.toForumId = toForumId;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

}
