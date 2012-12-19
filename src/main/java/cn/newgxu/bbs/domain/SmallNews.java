package cn.newgxu.bbs.domain;

import java.util.Date;
import java.util.LinkedHashMap;
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
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "small_news")
public class SmallNews extends JPAEntity {

	private static final long serialVersionUID = 9155200262255518973L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_small_news")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "title", length = 30)
	private String title;

	@Column(name = "content", length = 500)
	private String content;

	// 由于是many2one，级联获取，用于解决首页有时出现获取不到用户nick的bug，@2012-05-22
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private User user;

	@Column(name = "create_time")
	private Date createTime;

	private int hit;

	private boolean invalid;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "forum_id")
	private Forum forum;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void display() {
		addhit(1);
	}

	// ------------------------------------------------

	public static SmallNews get(int newsId) throws ObjectNotFoundException {
		return (SmallNews) getById(SmallNews.class, newsId);
	}

	/* sql语句写错导致发不了小字报*/
	@SuppressWarnings("unchecked")
	public static SmallNews getSmallNewsListByUser(int userId) {
		List<SmallNews> smalls = (List<SmallNews>) Q("from SmallNews s where user.id = ?1 and invalid = 0 order by createTime desc", P(1, userId)).getResultList();
//		if(smalls!=null)
//			return smalls.get(0);
		for(SmallNews sm : smalls){
			return sm;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<SmallNews> getSmallNewsList(Forum forum, Pagination p) {
		return (List<SmallNews>) Q(
				"from SmallNews s where forum = ?1 and invalid = 0 order by id desc",
				P(1, forum), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<SmallNews> getSmallNewsList(Pagination p) {
		return (List<SmallNews>) Q(
				"from SmallNews s where invalid = 0 order by id desc", p)
				.getResultList();
	}

	public static int getSmallNewsSize(Forum forum) {
		try {
			return ((Long) SQ(
					"select count(*) from SmallNews s where forum = ?1 and invalid = 0",
					P(1, forum))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getSmallNewsSize() {
		try {
			return ((Long) SQ("select count(*) from SmallNews s where invalid = 0"))
					.intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}
	
	/**
	 * 获取小字报，用于十周年主页的改版
	 * @since 2012-04-19
	 * @author ivy
	 * @param number 需要获取的数量
	 */
	@SuppressWarnings("unchecked")
	public static List<SmallNews> getSmallNews(int number) {
		return (List<SmallNews>) Q(
				"from SmallNews where invalid = 0 order by createTime desc").setFirstResult(0).setMaxResults(number).getResultList(); 
	}

	public void addhit(int number) {
		this.hit += number;
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "topic" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("title", title);
				put("user", user);
				put("content", content);
				put("createTime", createTime);
				put("hit", hit);
				put("invalid", invalid);
				put("forum", forum);
			}
		}.toString();
	}

}