package cn.newgxu.bbs.web.model;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class UserStatus {

	private boolean login = false;

	private int messageNotRead = 0;

	private String nick = "游客";
	
	// add start by ivy 2012年10周年论坛主页改版
	
	// 经验
	private int experience;
	// 灌水数
	private int publishTopicsCount;
	// 当前体力值
	private int currentPower;
	// 最大体力值
	private int maxPower;
	// 职务
	private String userGroup;
	// 用户头像
	private String userFace;
	// 登陆账号
	private String username = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getPublishTopicsCount() {
		return publishTopicsCount;
	}

	public void setPublishTopicsCount(int publishTopicsCount) {
		this.publishTopicsCount = publishTopicsCount;
	}

	public int getCurrentPower() {
		return currentPower;
	}

	public void setCurrentPower(int currentPower) {
		this.currentPower = currentPower;
	}

	public int getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	
	public String getUserFace() {
		return userFace;
	}

	public void setUserFace(String userFace) {
		this.userFace = userFace;
	}
	
	// add end by ivy @2012-04-07
	
	



	private int userId = 0;

	private String originalUrl;

	// -------add by 叨叨雨
	private int draftboxSize;
	// ----add by hjc
	private static int totalHicount;

	private static int todayHicount;

	public int getTotalHicount() {
		return totalHicount;
	}

	public void setTotalHicount(int totalHicount) {
		UserStatus.totalHicount = totalHicount;
	}

	public int getTodayHicount() {
		return todayHicount;
	}

	public void setTodayHicount(int todayHicount) {
		UserStatus.todayHicount = todayHicount;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public int getMessageNotRead() {
		return messageNotRead;
	}

	public void setMessageNotRead(int messageNotRead) {
		this.messageNotRead = messageNotRead;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDraftboxSize() {
		return draftboxSize;
	}

	public void setDraftboxSize(int draftboxSize) {
		this.draftboxSize = draftboxSize;
	}

}
