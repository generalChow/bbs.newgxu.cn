package cn.newgxu.bbs.web.model.admin;

import java.util.List;

import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class WebMastersManageModel {
	private List<WebMasterModel> webmasters;

	private int id;

	private User user;

	private String span;

	private String type;
	// 参与投票人数
	private Long totalNum;
	// 被投票的斑竹id数组
	private int[] options;
	// 是否已经投过票
	private boolean voteTarget = false;

	private String forum_name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public List<WebMasterModel> getWebmasters() {
		return webmasters;
	}

	public void setWebmasters(List<WebMasterModel> webmasters) {
		this.webmasters = webmasters;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public int[] getOptions() {
		return options;
	}

	public void setOptions(int[] options) {
		this.options = options;
	}

	public boolean isVoteTarget() {
		return voteTarget;
	}

	public void setVoteTarget(boolean voteTarget) {
		this.voteTarget = voteTarget;
	}

	public String getForum_name() {
		return forum_name;
	}

	public void setForum_name(String forumName) {
		forum_name = forumName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
