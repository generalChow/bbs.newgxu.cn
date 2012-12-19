package cn.newgxu.bbs.web.model.admin;

import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class WebMasterModel {
	private int numberOfTopic;
	private int numberOfReply;
	private int numberOfGood;
	private int numberOfManage;
	private User user;
	
	private int id;
	
	private int userid;
	private String nick;
	private String forum_name;
	private int score;
	
	private String randomBar="bar"+((int)(10*Math.random())+1);
	
    public int getNumberOfTopic() {
		return numberOfTopic;
	}
	public void setNumberOfTopic(int numberOfTopic) {
		this.numberOfTopic = numberOfTopic;
	}
	public int getNumberOfReply() {
		return numberOfReply;
	}
	public void setNumberOfReply(int numberOfReply) {
		this.numberOfReply = numberOfReply;
	}
	public int getNumberOfGood() {
		return numberOfGood;
	}
	public void setNumberOfGood(int numberOfGood) {
		this.numberOfGood = numberOfGood;
	}
	public int getNumberOfManage() {
		return numberOfManage;
	}
	public void setNumberOfManage(int numberOfManage) {
		this.numberOfManage = numberOfManage;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getForum_name() {
		return forum_name;
	}
	public void setForum_name(String forumName) {
		forum_name = forumName;
	}
	public String getRandomBar() {
		return randomBar;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	
}
