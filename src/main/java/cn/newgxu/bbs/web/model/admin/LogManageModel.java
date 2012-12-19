package cn.newgxu.bbs.web.model.admin;

import java.util.List;

import cn.newgxu.bbs.domain.ManageLog;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class LogManageModel extends PaginationBaseModel {

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
	public static final int LIGHT_TOPIC_LOGID = 16; // 加亮主题
	public static final int UNLIGHT_TOPIC_LOGID = 17; // 取消加亮

	private int forumId;

	private int topicId;

	private int replyId;

	private String action;

	private User user;

	private int typeid;

	private List<ManageLog> logs;

	private String searchType;
	private String searchValue;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getForumId() {
		return forumId;
	}

	public int getTopicId() {
		return topicId;
	}

	public int getReplyId() {
		return replyId;
	}

	public String getAction() {
		return action;
	}

	public User getUser() {
		return user;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public List<ManageLog> getLogs() {
		return logs;
	}

	public void setLogs(List<ManageLog> logs) {
		this.logs = logs;
	}

}
