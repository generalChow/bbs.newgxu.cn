package cn.newgxu.bbs.domain;

import java.util.Date;
import java.util.List;

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

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "managelog")
public class ManageLog extends JPAEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "logid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user;

	@Column(name = "typeid")
	private int typeid;

	@Column(name = "dt")
	private Date dt;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "topicid")
	private Topic topic;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "replyid")
	private Reply reply;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "forumid")
	private Forum forum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Reply getReply() {
		return reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	@SuppressWarnings("unchecked")
	public static List<ManageLog> getLogs(Pagination p) {
		p.setRecordSize(ManageLog.getNumberOfLogs());
		return (List<ManageLog>) Q("from ManageLog m order by dt desc", p).getResultList();
	}

	private static int getNumberOfLogs() {
		try {
			return ((Long) SQ("select count(*) from ManageLog m")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<ManageLog> getLogsByID(Pagination p, String id) {
		int num = 0;
		try {
			num = Integer.valueOf(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		p.setRecordSize(ManageLog.getNumberOfLogsByID(id));
		return (List<ManageLog>) Q(
				"from ManageLog m where m.user.id=?1 or m.topic.topicUser.id=?1 or m.reply.postUser.id=?1 order by dt desc",
				P(1, num)).getResultList();
	}

	private static int getNumberOfLogsByID(String id) {
		try {
			int num = Integer.getInteger(id);
			return ((Long) SQ(
					"select count(*) from ManageLog m where m.user.id=?1 or m.topic.topicUser.id=?1 or m.reply.postUser.id=?1",
					P(1, num))).intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<ManageLog> getLogsByForum(Pagination p, String name) {
		p.setRecordSize(ManageLog.getNumberOfLogsByForum(name));
		return (List<ManageLog>) Q("from ManageLog m where m.forum.name like'%"+name+"%' order by dt desc",p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<ManageLog> getLogsByUsername(Pagination p, String name) {
		p.setRecordSize(ManageLog.getNumberOfLogsByUsername(name));
		return (List<ManageLog>) Q("from ManageLog m where m.user.username like'%"+name+"%' order by dt desc",p).getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public static List<ManageLog> getLogsByStudentid(Pagination p, String name) {
		p.setRecordSize(ManageLog.getNumberOfLogsByStudentid(name));
		return (List<ManageLog>) Q("from ManageLog m where m.user.studentid like'%"+name+"%'").getResultList();

	}

	private static int getNumberOfLogsByStudentid(String name) {
		try {
			return ((Long) SQ(
					"select count(*) from ManageLog m where m.user.studentid like'%"+name+"%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	private static int getNumberOfLogsByUsername(String name) {
		try {
			return ((Long) SQ(
					"select count(*) from ManageLog m where m.user.username like'%"+name+"%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	private static int getNumberOfLogsByForum(String name) {
		try {
			return ((Long) SQ(
					"select count(*) from ManageLog m where m.forum.name like'%"+name+"%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getLogNumberOfUser(User user) {
		try {
			return ((Long) SQ(
					"select count(*) from ManageLog m where m.user.id=?1",
					P(1, user.getId()))).intValue();
		} catch (Exception e) {
			return 0;
		}
	}

}
