package cn.newgxu.bbs.domain.user;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "online_user")
public class OnlineUser extends JPAEntity {

	private static final long serialVersionUID = -2231731211179213181L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_online_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "last_alive_time")
	private Date lastAliveTime;

	@Column(name = "forum_id")
	private int forumId;

	private String os;

	private String ip;

	private String location;

	private String sessionid;

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	// ------------------------------------------------

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static OnlineUser getByUserId(int userId)
			throws ObjectNotFoundException {
		return (OnlineUser) SQ("from OnlineUser u where u.userId = ?1", P(1,
				userId));
	}

	public static int getNumberByIp(String ip) {
		try {
			return ((Long) SQ("select count(*) from OnlineUser where ip = ?1",
					P(1, ip))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static void clean() {
		Q("delete from OnlineUser where lastAliveTime <= ?1",
				P(1, Util.getDateAfterMinute(-40))).executeUpdate();
	}

	public static void sign(int userId, Date current, int forumId, String os,
			String ip, String location, String sessionid) {
		OnlineUser onlineUser = null;
		try {
			onlineUser = getByUserId(userId);
		} catch (ObjectNotFoundException e) {
			try {
				onlineUser = getBySessionId(sessionid);
			} catch (ObjectNotFoundException e1) {
				onlineUser = new OnlineUser();
			}
		}
		onlineUser.setUserId(userId);
		onlineUser.setLastAliveTime(current);
		onlineUser.setForumId(forumId);
		onlineUser.setOs(os);
		onlineUser.setIp(ip);
		onlineUser.setLocation(location);
		onlineUser.setSessionid(sessionid);

		onlineUser.save();
	}

	private static OnlineUser getBySessionId(String sessionid)
			throws ObjectNotFoundException {
		return (OnlineUser) SQ("from OnlineUser u where u.sessionid = ?1", P(1,
				sessionid));
	}

	public static int getTotal() {
		try {
			return ((Long) SQ("select count(*) from OnlineUser")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getNumberOfForum(int forumId) {
		try {
			return ((Long) SQ(
					"select count(*) from OnlineUser where forumId = ?1", P(1,
							forumId))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getUserNumberOfForum(int forumId) {
		try {
			return ((Long) SQ(
					"select count(*) from OnlineUser where userId >0 and forumId = ?1",
					P(1, forumId))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<OnlineUser> getOnlineUsers(Pagination p) {
		try {
			p.setRecordSize(((Long) SQ("select count(*) from OnlineUser"))
					.intValue());
		} catch (ObjectNotFoundException e) {
			p.setRecordSize(0);
		}
		return (List<OnlineUser>) Q("from OnlineUser o", p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<OnlineUser> getOnlineForumUsers(int forum_Id,
			Pagination p) {
		try {
			p.setRecordSize(((Long) SQ(
					"select count(*) from OnlineUser where forum_Id = ?1", P(1,
							forum_Id))).intValue());
		} catch (ObjectNotFoundException e) {
			p.setRecordSize(0);
		}
		return (List<OnlineUser>) Q("from OnlineUser o where forum_Id = ?1",
				P(1, forum_Id), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<OnlineUser> getOnlineUsers() {
		return (List<OnlineUser>) Q("from OnlineUser o where userId >0 ")
				.getResultList();
	}

	public String getNick() {
		if (this.userId > 0) {
			try {
				return "<a href=\"/user/user_info.yws?id=" + this.userId
						+ "\">" + User.get(this.userId).getNick() + "</a>";
			} catch (ObjectNotFoundException e) {
				return "游客";
			}
		}
		return "游客";
	}

	public String getAction() {
		if (this.userId > 0) {
			return "<a href=\"/message/send_message.yws?userId=" + this.userId
					+ "\">发送</a>";
		}
		return "";
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "user" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("userId", userId);
				put("lastAliveTime", lastAliveTime);
				put("forumId", forumId);
				put("os", os);
				put("location", location);
			}
		}.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof OnlineUser) {
			return ((OnlineUser) obj).userId == this.userId;
		}
		return false;
	}

}
