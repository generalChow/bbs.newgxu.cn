package cn.newgxu.bbs.domain;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
//import org.hibernate.validator.Email;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.filter.FilterUtil;
import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemUsedLog;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.domain.vote.Vote;
import cn.newgxu.bbs.domain.vote.VoteOption;
import cn.newgxu.bbs.web.activity.Activity;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "topic")
public class Topic extends JPAEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_topic")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "title", length = 255)
	private String title;

	private int face;

	// 由于是多对一的关系，应网管部要求，首页获取帖子时显示用户id，所以这里改为了Eager 2012-04-19
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private User topicUser;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "reply_id")
	private User replyUser;

	@Column(name = "reply_time")
	private Date replyTime;

	@Column(name = "reply_times")
	private int replyTimes;

	@Column(name = "click_times")
	private int clickTimes;

	@Column(name = "top_type")
	private int topType;

	@Column(name = "pub_type")
	private int pubType;

	private boolean invalid;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "forum_id")
	private Forum forum;

	@Column(name = "lock_token")
	private boolean lock;

	@Column(name = "creation_time")
	private Date creationTime;

	@Column(name = "good_topic")
	private boolean goodTopic;

	@Column(name = "light_topic")
	private boolean lightTopic;

	@Column(name = "lucky_id")
	private int luckyId;

	@Column(name = "activity_type")
	private String activityType;

	@Column(name = "vote_topic")
	private boolean voteTopic;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "vote_id")
	private Vote vote;
	
	//............
//	@Column(name = "activity_decoration")
//	private String activityDecoration;
//
//	public String getActivityDecoration() {
//		return activityDecoration;
//	}
//
//	public void setActivityDecoration(String activityDecoration) {
//		this.activityDecoration = activityDecoration;
//	}
	//.............
	public boolean isLightTopic() {
		return lightTopic;
	}

	public void setLightTopic(boolean lightTopic) {
		this.lightTopic = lightTopic;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public boolean isVoteTopic() {
		return voteTopic;
	}

	public int getPubType() {
		return pubType;
	}

	public void setPubType(int pubType) {
		this.pubType = pubType;
	}
	
	public void setVoteTopic(boolean voteTopic) {
		this.voteTopic = voteTopic;
	}

	public int getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(int clickTimes) {
		this.clickTimes = clickTimes;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
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

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public int getReplyTimes() {
		return replyTimes;
	}

	public void setReplyTimes(int replyTimes) {
		this.replyTimes = replyTimes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTopType() {
		return topType;
	}

	public void setTopType(int topType) {
		this.topType = topType;
	}

	public User getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(User replyUser) {
		this.replyUser = replyUser;
	}

	public User getTopicUser() {
		if (topicUser == null)
			return getUser();
		return topicUser;
	}
	
	/**
	 * 获取帖子的作者，用于10周年的改版
	 * @since 2012-04-19
	 * @author ivy
	 * @return
	 */
	public User getUser() {
		try {
			return User.get(this.topicUser.getId());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setTopicUser(User topicUser) {
		this.topicUser = topicUser;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean isGoodTopic() {
		return goodTopic;
	}

	public void setGoodTopic(boolean goodTopic) {
		this.goodTopic = goodTopic;
	}

	public int getLuckyId() {
		return luckyId;
	}

	public void setLuckyId(int luckyId) {
		this.luckyId = luckyId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * 道具使用后在相应的topic出现效果
	 * 
	 * @return
	 */
	// public String getIsUseItem(){
	// int count;
	// Item item = new Item();
	// item.setId(44);
	// String img ="";
	// try {
	// long nowdate = TimerUtils.getTime("2010-12-25 00:00:00" ,
	// "yyyy-MM-dd HH:mm:ss") ;
	// long ydate = new Date(nowdate + 24*3600*1000).getTime();
	// long cdate = this.creationTime.getTime();
	// if(cdate >nowdate && cdate <ydate){
	// count = ItemUsedLog.getCountUseItemByUser(topicUser , item);
	// if(count >0){
	// Random random = new Random();
	// img = "<img src=\"/images/2010/"
	// +(Math.abs(random.nextInt())%10)+".gif\" style=\"text-align:center;\"/>";
	// }
	// }
	// } catch (ObjectNotFoundException e) {
	// return "";
	// }
	//		
	// return img;
	// }
	// ------------------------------------------------

	public static Topic get(int topicId) throws ObjectNotFoundException {
		return (Topic) getById(Topic.class, topicId);
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopics(Pagination p, int type) {
		if (type == 0) {
			return (List<Topic>) Q(
					"from Topic t where invalid = 0 order by creationTime desc",
					p).getResultList();
		} else {
			return (List<Topic>) Q(
					"from Topic t where invalid = 0 order by replyTime desc", p)
					.getResultList();
		}

	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopics(Forum forum, Pagination p, int type) {
		if (type == 0) {
			return (List<Topic>) Q(
					"from Topic t where forum = ?1 and invalid = 0 order by creationTime desc",
					P(1, forum), p).getResultList();
		} else {
			return (List<Topic>) Q(
					"from Topic t where forum = ?1 and invalid = 0 order by replyTime desc",
					P(1, forum), p).getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopics(Forum forum, Pagination p, String title) {
		return (List<Topic>) Q(
				"from Topic t where forum = ?1 and invalid = 0 and t.title like '%"
						+ title
						+ "%' and forum.secrecy=0 and t.forum.confrere=0 order by creationTime desc",
				P(1, forum), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopics(Forum forum, Pagination p, User user) {
		return (List<Topic>) Q(
				"from Topic t where forum = ?1 and invalid = 0 and topicUser = ?2 and forum.secrecy=0 and t.forum.confrere=0 order by creationTime desc",
				P(1, forum), P(2, user), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopics(User user, Pagination p) {
		return (List<Topic>) Q(
				"from Topic t where topicUser = ?1 and invalid = 0 and forum.secrecy=0 and t.forum.confrere=0  order by id desc",
				P(1, user), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopicsByReplyUser(User user, Pagination p) {
		return (List<Topic>) Q(
				"select distinct r.topic from Reply r where r.postUser = ?1 and r.invalid = 0 order by r.topic.id desc",
				P(1, user), p).getResultList();
	}

	//
	@SuppressWarnings("unchecked")
	public static List<Topic> getTopicsByReplyUserAndByReplyTime(User user,
			Pagination p) {
		return (List<Topic>) Q(
				"select distinct r.topic from Reply r where r.postUser = ?1 and r.invalid = 0 order by r.topic.replyTime desc",
				P(1, user), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopicsByReplyUserAndByReplyTime(User user,
			Forum forum, Pagination p) {
		return (List<Topic>) Q(
				"select distinct r.topic from Reply r where r.postUser = ?1 and r.topic.forum=?2 and r.invalid = 0 order by r.topic.replyTime desc",
				P(1, user), P(2, forum), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopicsByReplyUserAndByCreationTime(User user,
			Pagination p) {
		return (List<Topic>) Q(
				"select distinct r.topic from Reply r where r.postUser = ?1 and r.invalid = 0 order by r.topic.creationTime desc",
				P(1, user), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopicsByReplyUserAndByCreationTime(User user,
			Forum forum, Pagination p) {
		return (List<Topic>) Q(
				"select distinct r.topic from Reply r where r.postUser = ?1 and r.topic.forum=?2 and r.invalid = 0 order by r.topic.creationTime desc",
				P(1, user), P(2, forum), p).getResultList();
	}

	//
	@SuppressWarnings("unchecked")
	public static List<Topic> getTopicsByReplyUser(User user, Forum forum,
			Pagination p) {
		return (List<Topic>) Q(
				"select distinct r.topic from Reply r where r.postUser = ?1 and r.topic.forum=?2 and r.invalid = 0 order by r.topic.id desc",
				P(1, user), P(2, forum), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getTopicsByTitle(String title, Pagination p) {
		return (List<Topic>) Q(
				"from Topic t where t.title like '%"
						+ title
						+ "%' and invalid = 0 and t.forum.secrecy=0 order by id desc",
				p).getResultList();
	}

	public static Reply getReplyThis(int replyContentId, Pagination p) {
		return (Reply) Q("from Reply r where r.contentBean.id=?1",
				P(1, replyContentId)).getSingleResult();
	}

	// 2010-07-01 搜索全局出错，原因因为编码问题，java文件原来为gbk，保持了utf8所以出错,liutao
	public static int getTopicsSizeByTitle(String title) {
		try {
			return ((Long) SQ("select count(*) from Topic t where t.title like '%"
					+ title
					+ "%' and invalid = 0 and t.forum.secrecy=0 and t.forum.confrere=0 order by id desc"))
					.intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getTopicsSize(Forum forum, Date date) {
		try {
			return ((Long) SQ(
					"select count(*) from Topic t where forum = ?1 and creationTime > ?2 ",
					P(1, forum), P(2, date))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getTopicsSize(Forum forum, String title) {
		try {
			return ((Long) SQ(
					"select count(*) from Topic t where t.title like '%"
							+ title
							+ "%' and forum = ?1 and invalid = 0 and forum.secrecy=0 and t.forum.confrere=0  order by id desc",
					P(1, forum))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getTopicsSize(Forum forum) {
		try {
			return ((Long) SQ("select count(*) from Topic t where forum = ?1 ",
					P(1, forum))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getTopicsSize() {
		try {
			return ((Long) SQ("select count(*) from Topic t where invalid = 0 "))
					.intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getTopicsSize(Forum forum, User user) {
		try {
			return ((Long) SQ(
					"select  count(*) from Topic t where topicUser = ?1 and invalid = 0 and forum = ?2 and forum.secrecy=0 and t.forum.confrere=0",
					P(1, user), P(2, forum))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getTopicsSize(User user) {
		try {
			return ((Long) SQ(
					"select count(*) from Topic t where topicUser = ?1 and invalid = 0 and forum.secrecy=0 and t.forum.confrere=0",
					P(1, user))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getTopicsSize(User user, Date startTime, Date endTime) {
		try {
			return ((Long) SQ(
					"select count(t) from Topic t where t.topicUser = ?1 and t.invalid = 0 and t.creationTime>=?2 and t.creationTime<=?3",
					P(1, user), P(2, startTime), P(3, endTime))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	/**
	 * 根据时间段查询回帖数目
	 * 
	 * @param user
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getTopicsSizeByReplyUser(User user, Date startTime,
			Date endTime) {
		try {
			return ((Long) SQ(
					"select count(r) from Reply r where r.postUser = ?1 and r.invalid = 0 and r.postTime>=?2 and r.postTime<=?3 ",
					P(1, user), P(2, startTime), P(3, endTime))).intValue();
		} catch (ObjectNotFoundException e) {
			System.out.println(e);
			return 0;
		}
	}

	/*
	 * 2010-11-5 由于sql语句错误，导致无法分页： 原sql语句：select count(*) distinct r.topic from
	 * Reply r where r.postUser = ?1 and r.invalid = 0
	 */
	public static int getTopicsSizeByReplyUser(User user) {
		try {
			return ((Long) SQ(
					"select count(distinct r.topic) from Reply r where r.postUser = ?1 and r.invalid = 0 ",
					P(1, user))).intValue();
		} catch (ObjectNotFoundException e) {
			System.out.println(e);
			return 0;
		}
	}

	public static int getTopicsSizeByReplyUser(User user, Forum forum) {
		try {
			return ((Long) SQ(
					"select count(distinct r.topic) from Reply r where postUser = ?1 and r.topic.forum=?2 and invalid = 0 ",
					P(1, user), P(2, forum))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getTopicsSize(String title) {
		try {
			return ((Long) SQ("select count(*) from Topic t where t.title like '%"
					+ title + "%' and invalid = 0 ")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Topic> getPubTopics(int pubType) {
		return (List<Topic>) Q(
				"from Topic t where pubType = ?1 and invalid = 0 order by id desc",
				P(1, pubType)).setFirstResult(0).setMaxResults(7)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Reply> getReplies(Pagination p, boolean invalid) {
		return (List<Reply>) Q(
				"from Reply r where r.topic = ?1 and r.invalid = ?2"
						+ " order by r.postTime asc", P(1, this),
				P(2, invalid), p).getResultList();
	}

	public int getRepliesCount(boolean invalid) {

		try {
			return ((Long) SQ(
					"select count(*) from Reply r where r.topic = ?1 and r.invalid = ?2 ",
					P(1, this), P(2, invalid))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}

		// return
		// (Integer)Q("from Reply r where r.topic.id = ?1 and r.invalid = ?2"+
		// " order by r.postTime asc", P(1, this.getId()),P(2, invalid),
		// p).getSingleResult();
	}

	public List<ReplyLine> getReplieLines(Pagination p, boolean invalid) {
		List<Reply> replies = getReplies(p, invalid);
		// p.setRecordSize(replies.size());
		List<ReplyLine> result = new LinkedList<ReplyLine>();
		int i = 1;
		for (Reply reply : replies) {
			Reply replyThis = null;
			result.add(new ReplyLine(reply, replyThis, p.getPage(), p
					.getPageSize(), i));
			i++;
		}
		return result;
	}

	public static Long getTopicsPerTodayByUser(User user, Forum forum)
			throws ObjectNotFoundException, ParseException {
		return (Long) SQ(
				"select count(*) from Topic where topicUser=?1 and forum =?2 and creationTime>?3",
				P(3, TimerUtils.getDate()), P(2, forum), P(1, user));
	}

	/**
	 * @方法名称 :getHolidayString
	 * @功能描述 : 当节日来临，网友使用了指定的物品后，就可以在这里得到相应的节日图片<br />
	 *       在帖子标题前可以看到这个图片嘛<br />
	 *       从ItemUsedLog实体中获取用户是否使用过指定的节日物品<br />
	 *       跟getTopString（）的原理是一样的啦<br />
	 * 
	 *       这个图片显示的时候要判断对应的帖子是不是在这个节日中发表的,不然是不能添加这个图片的<br />
	 * 
	 * @返回值类型 :String
	 * @return
	 * 
	 * @创建日期 :2011-4-3 by 集成显卡
	 * @修改记录 :
	 */
	public String getHolidayString() {
		String result = "";
		try {
			// ItemUsedLog log=ItemUsedLog.getUsedLog(topicUser, 50);
			// (ItemUsedLog.getCountUseItemByUser(topicUser, Item.get(50))>0
			if ((ItemUsedLog.getCountUseItemByUser(topicUser, Item.get(52)) > 0)
					&& this.getHolidayCheck()) {
				// 清除这个记录
				result = "<img src=\"/images/holiday/yutu.gif\" /><script>$j('#title_"
						+ this.getId() + "').css('color','#222222');</script>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 检查是不是满足节日条件
	 * 
	 * @return
	 */
	public boolean getHolidayCheck() {
		Calendar cen = Calendar.getInstance();
		cen.setTime(creationTime);
		// 4月的话，返回的值是3
		// 2011年的七夕是8.6
		return cen.get(Calendar.MONTH) == 8
				&& cen.get(Calendar.YEAR) == 2011
				&& (cen.get(Calendar.DAY_OF_MONTH) >= 12 && cen
						.get(Calendar.DAY_OF_MONTH) <= 13);
	}

	public String getTopString() {
		if (getTopType() == 0) {
			return "";
		} else if (getTopType() == 1) {
			return "<img src=\"/images/setTop.gif\" />";
		} else if (getTopType() == 2) {
			return "<img src=\"/images/setTopArea.gif\" />";
		} else if (getTopType() == 3) {
			return "<img src=\"/images/setTopAll.gif\" />";
		} else {
			return "";
		}
	}

	public String getLockString() {
		if (isLock()) {
			return "<img src=\"/images/setLock.gif\" />";
		} else {
			return "";
		}
	}

	public String getGoodString() {
		if (isGoodTopic()) {
			return "<img src=\"/images/setGood.gif\" />";
		} else {
			return "";
		}
	}

	public String getVoteString() {
		if (isVoteTopic()) {
			return "<img src=\"/images/setVote.gif\" />";
		} else {
			return "";
		}
	}

	public String getNewTopicString() {
		if (Util.getDateAfterDay(-1).before(getCreationTime())) {
			return "<img src=\"/images/newTopic.gif\" />";
		} else {
			return "";
		}
	}

	public int getReplyPages() {

		// int pages = this.replyTimes / Constants.NUMBER_OF_REPLIES + 1;
		int count = this.getRepliesCount(false);
		int pages = count / Constants.NUMBER_OF_REPLIES;

		if (count % Constants.NUMBER_OF_REPLIES > 0) {
			pages++;
		}
		return pages;
	}

	public String getGuide() {
		int pages = getReplyPages();
		if (pages < 2) {
			return "";
		}
		String front = "/topic.yws?forumId=" + forum.getId() + "&topicId="
				+ this.getId() + "&page=";
		StringBuffer sb = new StringBuffer("[");
		if (pages <= Constants.TOPIC_GUILD_LENGTH * 2 + 1) {
			for (int i = 1; i <= pages; i++) {
				if (i != 1) {
					sb.append(" ");
				}
				sb.append("<a href=\"").append(front).append(i).append("\">");
				if (i == 1) {
					sb
							.append("<img src=\"/images/pagelist.gif\" border=\"0\" />");
				} else {
					sb.append(i);
				}
				sb.append("</a>");
			}
		} else {
			for (int i = 1; i <= Constants.TOPIC_GUILD_LENGTH; i++) {
				if (i != 1) {
					sb.append(" ");
				}
				sb.append("<a href=\"").append(front).append(i).append("\">");
				if (i == 1) {
					sb
							.append("<img src=\"/images/pagelist.gif\" border=\"0\" />");
				} else {
					sb.append(i);
				}
				sb.append("</a>");
			}
			sb.append("...");
			int start = pages + 1 - Constants.TOPIC_GUILD_LENGTH;
			for (int i = start; i <= pages; i++) {
				if (i != start) {
					sb.append(" ");
				}
				sb.append("<a href=\"").append(front).append(i).append("\">");
				sb.append(i);
				sb.append("</a>");
			}
		}

		sb.append("]");

		return sb.toString();
	}

	public void addReplyTimes(int number) {
		this.replyTimes += number;
	}

	public void addClickTimes(int number) {
		this.clickTimes += number;
	}

	public void newReply(Reply reply) {
		addReplyTimes(1);
		setReplyTime(reply.getPostTime());
		setReplyUser(reply.getPostUser());
	}

	public void display() {
		addClickTimes(1);
	}

	public void unSetTop() {
		setTopType(0);
	}

	public void setTop() {
		setTopType(1);
	}

	public void setAreaTop() {
		setTopType(2);
	}

	public void setAllTop() {
		setTopType(3);
	}

	public String getReplyTimeDisplay() {
		return Util.formatTime(Util.DATE_TIME_FORMAT, getReplyTime());
	}

	public String getCreateTimeDisplay() {
		return Util.formatTime(Util.DATE_TIME_FORMAT, getCreationTime());
	}

	public String getTitleFilter() {
		String title = FilterUtil.title(getTitle());
		if (isLightTopic()) {
			title = StringUtils.replace(
					"<font class=\"light\">${title}</font>", "${title}", title);
		}
		return title;
	}

	/**
	 * 应用于论坛html标题
	 * 
	 * @return
	 */
	public String getHtmlTitle() {
		return FilterUtil.title(getTitle());
	}

	public Vote getVote() {
		if (!isVoteTopic()) {
			throw new RuntimeException("不是投票主题，怎么可能不出错？！");
		}
		if (vote == null) {
			throw new RuntimeException("严重错误：投票主题找不到投票对象！建议删除该主题...");
		}
		return vote;
	}

	public boolean isVoted(User user) {
		Vote vote;
		try {
			vote = getVote();
		} catch (Exception e) {
			return true;
		}
		return vote.isVoted(user);
	}

	public boolean isVoteable(User user) {
		if (log.isDebugEnabled()) {
			log.debug("isVoteTopic=" + isVoteTopic());
			log.debug("isVoted=" + isVoted(user));
		}
		if (!isVoteTopic()) {
			return false;
		}
		return !isVoted(user);
	}

	public void doVote(int[] optionIndexs, User user) throws BBSException {
		if (log.isDebugEnabled()) {
			log.debug("voteable=" + isVoteable(user));
		}
		if (!isVoteable(user)) {
			throw new BBSException(BBSExceptionMessage.UNVOTEABLE);
		}
		Vote vote = getVote();
		Object[] options = vote.getOptions().toArray();
		for (int index : optionIndexs) {
			VoteOption option = (VoteOption) options[index];
			option.addScore(user);
		}
		vote.addVoteUser(user);
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "topic" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("title", title);
				put("face", face);
				put("topicUser", topicUser);
				put("replyUser", replyUser);
				put("replyTime", replyTime);
				put("replyTimes", replyTimes);
				put("clickTimes", clickTimes);
				put("topType", topType);
				put("invalid", invalid);
				put("forum", forum);
			}
		}.toString();
	}

	// public boolean isTop() {
	// return this.getTopType()==1?true:false;
	// }
	//	
	// public boolean isAreaTop() {
	// return this.getTopType()==2?true:false;
	// }
	//	
	// public boolean isAllTop() {
	// return this.getTopType()==3?true:false;
	// }

	/**
	 * 获取一个信息，是对应这次活动的说明
	 */
	public String getActivityInfo() {
		return Activity.getActivityInfo(getLuckyId());
	}

	/**
	 * 判断topic中是否含有图片
	 */
	// public String getImageString() {
	// Reply reply = getReplies(new Pagination(), false).get(0);
	// String content = reply.getContent();
	// Pattern p = Pattern.compile("[img]");
	// Matcher m = p.matcher(content);
	// if (m.find()) {
	// FileTest.print(m.group(), "d:/log.txt");
	// return "<img src=\"image/image.gif\" />";
	// }
	// return "";
	// }

	/**
	 * 判断topic中是否含有图片
	 */
	public String getImageString() {
		Reply reply = getReplies(new Pagination(), false).get(0);
		String content = reply.getContentFilter();
		String img = "<img style='border: 0;' src='image/image.gif' />";

		Pattern p = Pattern.compile("<img.+src=\"/upload/");
		Matcher m = p.matcher(content);
		if (m.find()) {
			return img;
		}
		
		m.reset();
		
		p = Pattern.compile("<img.+src=\"images/upload_files/");
		m = p.matcher(content);
		if (m.find()) {
			return img;
		}

		return "";
	}
	
	/**
	 * 获取活动的图片信息。
	 * @since 2012-12-19
	 */
	public String getActivityImage() {
		if (this.getActivityType() == null)
			return "";
		if (this.getActivityType().equals("activity_2012#!")) {
			return "<img src='/resources/images/topic/activity_2012.jpg' alt='末日！' title='2012末日年末日月活动贴~' />";
		}
		return "";
	}
	
	public String getImageString(String content) {
//		Reply reply = getReplies(new Pagination(), false).get(0);
//		String content = reply.getContentFilter();

		Pattern p = Pattern.compile("<img.+src=.*/upload/");
		Matcher m = p.matcher(content);
		if (m.find()) {
			return "<img src=\"image/image.gif\" />";
		}
		
		m.reset();
		
		p = Pattern.compile("<img.+src=.*images/upload_files/");
		m = p.matcher(content);
		if (m.find()) {
			return "<img src=\"image/image.gif\" />";
		}

		return "nothing!";
	}

	
	public String getImageStringTest(String content) {
//		System.out.println(content);
		String result = "";
//		Pattern p = Pattern.compile("upload/mp3/.+\\.mp3");
		Pattern p = Pattern.compile("upload/mp3/");
		Matcher m = p.matcher(content);
		
		if (m.find()) {
			System.out.println(1);
			result += "<img src=\"image/music.gif\" />";
		}
		
		m.reset();
		
		p = Pattern.compile("<img.+src=.*/upload/");
		m = p.matcher(content);
		if (m.find()) {
			System.out.println(2);
			result += "<img src=\"image/image.gif\" />";
			return result;
		}
		
		m.reset();
		
		p = Pattern.compile("<img.+src=.*images/upload_files/");
		m = p.matcher(content);
		if (m.find()) {
			System.out.println(3);
			result += "<img src=\"image/image.gif\" />";
			return result;
		}
		return "nothing!";
	}
	
	/**
	 * 获取帖子中既是加亮主题，又是推荐主题，也是好贴（good_topic = 1, light_topic = 1, pub_type = 1)的帖子，用于主页的文章推荐版块
	 * @since 2012-04-17
	 * @author ivy
	 */
	@SuppressWarnings("unchecked")
	public static List<Topic> getRecomendedTopic(int number, int pubType, boolean goodTopic, boolean lightTopic) {
		return (List<Topic>) Q(
				"from Topic t where t.pubType = ?1 and t.goodTopic = ?2 and t.lightTopic = ?3 and t.invalid = 0 order by t.id desc",
				P(1, pubType), P(2, goodTopic), P(3, lightTopic)).setFirstResult(0).setMaxResults(number)
				.getResultList();
	}
	
	/**
	 * 获取最新的帖子的id，用于判断是否有新帖被发表,处于效率和语句的灵活性原因，使用了mysql方言。
	 * @since 2012-05-10
	 * @author ivy
	 * @return id 最新的帖子的id
	 */
	public static int getLatestTopicId() {
		int id = 0;
		try {
			id = (Integer) Q("select t.id from Topic t order by t.id desc").setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			id = 0;
		}
		return id;
	}
	
	/**
	 * 获取最新的帖子, 暂时用于wap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Topic> getLatesTopics(int number) {
		return Q("from Topic as t order by t.creationTime desc").setMaxResults(number).getResultList();
	}
	
	/**
	 * 获取帖子后续（more）的回复。
	 * @param topicId
	 * @param offset
	 * @param number
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Reply> getReples(int topicId, int offset, int number) {
		return getEntityManager()
				.createQuery("FROM Reply r WHERE r.topic.id = :tid and r.id > :rid and r.invalid = false order by r.id asc")
				.setParameter("tid", topicId)
				.setParameter("rid", offset)
				.setMaxResults(number)
				.getResultList();
	}
	
}