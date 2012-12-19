package cn.newgxu.bbs.web.model;

import java.util.Date;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OnlineUserModel {

	private int userId;

	private Date lastAliveTime;

	private int forumId;

	private String os;
	
	private String ip;

	private String location;
	
	private String sessionid;

	public OnlineUserModel(int userId, int forumId, String os,String ip,String location,String sessionid) {
		this.userId = userId;
		this.lastAliveTime = new Date();
		this.forumId = forumId;
		this.os = os;
		this.ip=ip;
		this.location = location;
		this.sessionid=sessionid;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public Date getLastAliveTime() {
		return lastAliveTime;
	}

	public void setLastAliveTime(Date lastAliveTime) {
		this.lastAliveTime = lastAliveTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	

}
