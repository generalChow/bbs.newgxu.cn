package cn.newgxu.bbs.domain;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "user_logon_log")
public class UserLogonLog extends JPAEntity {

	private static final long serialVersionUID = -1048081023282584550L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_user_logon_log")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "logon_time")
	private Date logonTime;

	private String ip;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLogonTime() {
		return logonTime;
	}

	public void setLogonTime(Date logonTime) {
		this.logonTime = logonTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// ------------------------------------------------
	public static void log(User user, Date logonTime, String ip) {
		UserLogonLog log = new UserLogonLog();
		log.setUser(user);
		log.setLogonTime(logonTime);
		log.setIp(ip);

		log.save();
	}

	/**
	 * 获取用户在一个时间段的登录次数
	 * @param user
	 * @param startTime 
	 * @param endTime
	 * @return
	 */
	public static int getLoginCountByUser(User user ,Date startTime , Date endTime) {
		try {
			return ((Long) SQ(
					"select count(u) from UserLogonLog u where u.user = ?1 and u.logonTime>=?2 and u.logonTime<=?3",
					P(1, user),P(2, startTime),P(3, endTime))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}
	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "userLogonLog" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("user", user);
				put("logonTime", logonTime);
				put("ip", ip);
			}
		}.toString();
	}

}
