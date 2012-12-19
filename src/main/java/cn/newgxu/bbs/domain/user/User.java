package cn.newgxu.bbs.domain.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.filter.FilterUtil;
import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.FootBallTeam;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Honor;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.group.GroupManager;
import cn.newgxu.bbs.domain.group.UserGroup;
import cn.newgxu.bbs.domain.market.FreeMarketItem;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.market.ItemWorkManager;
import cn.newgxu.bbs.domain.message.Message;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 * 
 *          修改记录： 1.添加state属性，这个是用户的状态值。用户可以修改是否显示状态信息
 * 
 *          ALTER TABLE `user` ADD COLUMN `can_state` tinyint(1) NULL DEFAULT 1
 *          AFTER `replyMessage`; ALTER TABLE `user` ADD COLUMN `state`
 *          varchar(255) NULL AFTER `can_state`;
 */
@Entity
@Table(name = "user")
public class User extends JPAEntity implements Browser {

	private static final Log	log					= LogFactory.getLog(User.class);

	private static final long	serialVersionUID	= 5166233887545932757L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_users")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int					id					= -1;

	@Column(name = "username", length = 32)
	private String				username;

	@Column(name = "password", length = 32)
	private String				password;

	@Column(name = "nick", length = 20)
	private String				nick;

	@Column(name = "title", length = 20)
	private String				title;

	@Column(name = "face", length = 255)
	private String				face;

	@Column(name = "idiograph", length = 511)
	private String				idiograph;

	@Column(name = "login_times")
	private int					loginTimes;

	@Column(name = "last_login_time")
	private Date				lastLoginTime;

	@Column(name = "true_name", length = 20)
	private String				trueName;

	@Column(name = "sex")
	private boolean				sex;

	private Date				birthday;

	@Column(name = "email", length = 255)
	private String				email;

	@Column(name = "homepage", length = 255)
	private String				homepage;

	@Column(name = "qq", length = 16)
	private String				qq;

	@Column(name = "idcode", length = 18)
	private String				idcode;

	@Column(name = "tel", length = 20)
	private String				tel;

	@Column(name = "current_power")
	private int					currentPower;

	@Column(name = "number_of_topic")
	private int					numberOfTopic;

	@Column(name = "number_of_reply")
	private int					numberOfReply;

	@Column(name = "number_of_good")
	private int					numberOfGood;

	/** 发表的帖子被快速回复是否通知 */
	@Column(name = "replyMessage")
	private Integer				replyMessage;

	private int					exp;
	private int					money;
	private int					gold;
	private int					badboy;

	@Column(name = "honor", length = 255)
	private String				honor;
	// 代替旧的荣誉
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_honor", joinColumns = @JoinColumn(
			name = "user_id",
			referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(
			name = "honor_id",
			referencedColumnName = "id"))
	@OrderBy("id")
	private List<Honor>			honors;
	// cup of life
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "myfootball_team", joinColumns = @JoinColumn(
			name = "user_id",
			referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(
			name = "team_id",
			referencedColumnName = "id"))
	private List<FootBallTeam>	footBallTeams;

	@Column(name = "confrere")
	private boolean				confrere;

	@Column(name = "group_id")
	private int					groupId;

	@Column(name = "group_type_id")
	private int					groupTypeId;

	@Column(name = "question", length = 100)
	private String				question;

	@Column(name = "answer", length = 100)
	private String				answer;

	@Column(name = "register_time")
	private Date				registerTime;

	@Column(name = "studentid", length = 20)
	private String				studentid;

	@Column(name = "units", length = 255)
	private String				units;

	@Column(name = "register_type")
	private byte				registerType;

	@Column(name = "account_status")
	private byte				accountStatus;

	private long				loginmt;

	private String				remark;

	private String				state;
	@Column(name = "can_state")
	private boolean				allowState;

	@Column(name = "last_week_exp")
	private int					lastWeekExp;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "webmaster_area", joinColumns = @JoinColumn(
			name = "user_id",
			referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(
			name = "area_id",
			referencedColumnName = "id"))
	private List<Area>			managingAreas;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "webmaster_forum", joinColumns = @JoinColumn(
			name = "user_id",
			referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(
			name = "forum_id",
			referencedColumnName = "id"))
	private List<Forum>			managingForums;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_favorite", joinColumns = @JoinColumn(
			name = "user_id",
			referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(
			name = "topic_id",
			referencedColumnName = "id"))
	private List<Topic>			favoriteTopics;

	@Transient
	private FootBallTeam		footBallTeam;

	// -----------------------------------------------

	public byte getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(byte accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getBadboy() {
		return badboy;
	}

	public void setBadboy(int badboy) {
		this.badboy = badboy;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getCurrentPower() {
		return currentPower;
	}

	public void setCurrentPower(int currentPower) {
		this.currentPower = currentPower;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public boolean isConfrere() {
		return confrere;
	}

	public void setConfrere(boolean confrere) {
		this.confrere = confrere;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public List<Honor> getHonors() {
		return honors;
	}

	public void setHonors(List<Honor> honors) {
		this.honors = honors;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public String getIdiograph() {
		return idiograph;
	}

	public void setIdiograph(String idiograph) {
		this.idiograph = idiograph;
	}

	public long getLoginmt() {
		return loginmt;
	}

	public void setLoginmt(long loginmt) {
		this.loginmt = loginmt;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getGroupTypeId() {
		return groupTypeId;
	}

	public void setGroupTypeId(int groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	public List<Area> getManagingAreas() {
		return managingAreas;
	}

	public void setManagingAreas(List<Area> managingAreas) {
		this.managingAreas = managingAreas;
	}

	public List<Forum> getManagingForums() {
		return managingForums;
	}

	public void setManagingForums(List<Forum> managingForums) {
		this.managingForums = managingForums;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		System.out.println(password);
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumberOfGood() {
		return numberOfGood;
	}

	public void setNumberOfGood(int numberOfGood) {
		this.numberOfGood = numberOfGood;
	}

	public int getNumberOfReply() {
		return numberOfReply;
	}

	public void setNumberOfReply(int numberOfReply) {
		this.numberOfReply = numberOfReply;
	}

	public int getNumberOfTopic() {
		return numberOfTopic;
	}

	public void setNumberOfTopic(int numberOfTopic) {
		this.numberOfTopic = numberOfTopic;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public byte getRegisterType() {
		return registerType;
	}

	public void setRegisterType(byte registerType) {
		this.registerType = registerType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 发表的帖子被快速回复是否通知<br />
	 * 0 为需要<br />
	 * 1 为不需要
	 * 
	 * @return
	 */
	public Integer getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(Integer replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAllowState() {
		return allowState;
	}

	public void setAllowState(boolean allowState) {
		this.allowState = allowState;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<FootBallTeam> getFootBallTeams() {
		return footBallTeams;
	}

	public void setFootBallTeams(List<FootBallTeam> footBallTeams) {
		this.footBallTeams = footBallTeams;
	}

	public FootBallTeam getFootBallTeam() {
		if (footBallTeams.size() > 0) {
			this.footBallTeam = footBallTeams.get(0);
		}
		return footBallTeam;
	}

	public void setFootBallTeam(FootBallTeam footBallTeam) {
		this.footBallTeam = footBallTeam;
	}

	public List<Topic> getFavoriteTopics() {
		return favoriteTopics;
	}

	public void setFavoriteTopics(List<Topic> favoriteTopics) {
		this.favoriteTopics = favoriteTopics;
	}

	public int getLastWeekExp() {
		return lastWeekExp;
	}

	public void setLastWeekExp(int lastWeekExp) {
		this.lastWeekExp = lastWeekExp;
	}

	// -----------------------------------------------------

	public static User getByUsername(String username)
			throws ObjectNotFoundException {
		return (User) SQ("from User u where u.username = ?1", P(1, username));
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByUsername(String username, Pagination p)
			throws ObjectNotFoundException {
		p.setRecordSize(getNumberOfUsersByUsername(username));
		System.out.println(p.getRecordSize() + " ---------------");
		return (List<User>) Q("from User u where u.username like '%" + username
				+ "%'", p).getResultList();
	}

	private static int getNumberOfUsersByUsername(String username) {
		try {
			return ((Long) SQ("select count(*) from User u where u.username like '%"
					+ username + "%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	/**
	 * 2010-10-7 由于查询不区分大小写
	 * 
	 * @param nick
	 * @return
	 * @throws ObjectNotFoundException
	 */
	@SuppressWarnings("all")
	public static User getByNick(String nick) throws ObjectNotFoundException {

		List<User> users = (List<User>) Q("from User u where u.nick = ?1", P(1, nick)).getResultList();
		User user = null;
		for (User u : users) {
			if (nick.equals(u.getNick())) {
				user = u;
			}
		}
		if (user == null)
			throw new ObjectNotFoundException();
		return user;
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByNick(String nick, Pagination p)
			throws ObjectNotFoundException {
		p.setRecordSize(getNumberOfUsersByNick(nick));
		return (List<User>) Q("from User u where u.nick like '%" + nick + "%'", p).getResultList();
	}

	private static int getNumberOfUsersByNick(String nick) {
		try {
			return ((Long) SQ("select count(*) from User u where u.nick like '%"
					+ nick + "%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static User getByStudentid(String studentid)
			throws ObjectNotFoundException {
		return (User) SQ("from User u where studentid = ?1", P(1, studentid));
	}

	@SuppressWarnings("unchecked")
	public static
			List<User> getUsersByStudentid(String studentid, Pagination p)
					throws ObjectNotFoundException {
		p.setRecordSize(getNumberOfUsersByStudentid(studentid));
		return (List<User>) Q("from User u where studentid like '%" + studentid
				+ "%'", p).getResultList();
	}

	private static int getNumberOfUsersByStudentid(String studentid) {
		try {
			return ((Long) SQ("select count(*) from User u where studentid like '%"
					+ studentid + "%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static User get(int userId) throws ObjectNotFoundException {
		return (User) getById(User.class, userId);
	}

	public static User getCertainExist(int userId) {
		try {
			return User.get(userId);
		} catch (ObjectNotFoundException e) {
			log.error(e);
			log.error("严重错误：user 没有找到。 id=" + userId);
			throw new RuntimeException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsers(int[] ids, Pagination p) {
		return (List<User>) Q("from User u where id in ("
				+ Util.intsToString(ids) + ")", p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsers(int[] ids) {
		return (List<User>) Q("from User u where id in ("
				+ Util.intsToString(ids) + ")").getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByHonorType(int type) {
		return (List<User>) Q("from User u where u.honors.honor.id = ?1", P(1, type));
	}

	/**
	 * not param 找出用户未有的荣誉
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Honor> getLeaveHonor() {
		String param = "";
		if (getHonors().size() > 0) {
			for (int i = 0; i < getHonors().size(); i++) {
				param = getHonors().get(i).getId() + ",";
			}
			return (List<Honor>) Q("from Honor h where h.id not in("
					+ param.substring(0, param.length() - 1) + ")").getResultList();
		}

		return (List<Honor>) Q("from Honor h ").getResultList();
	}

	/**
	 * 获取用户组类型 普通用户 GroupTypeId=1 斑竹 GroupTypeId=2 区管理员 GroupTypeId=3 超级管理员
	 * GroupTypeId=4
	 * 
	 * @return UserGroup
	 */
	public UserGroup getUserGroup() {
		return GroupManager.getUserGroup(this.groupTypeId, this.groupId);
	}

	// ------------------------------------------------------------

	public static int getNumberOfUsers() {
		try {
			return ((Long) SQ("select count(*) from User u")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	/**
	 * 传入指定的hql语句，得到相关结果集的记录数 by：集成显卡 2011.4.22
	 * 
	 * @param hql
	 * @return
	 */
	public static int getNumberOfUsersByLogintime(Date date) {
		try {
			return ((Long) (Q("select count(*) from User u where u.lastLoginTime>:date").setParameter("date", date)).getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersOrderByTime(Pagination p) {
		return (List<User>) Q("from User u order by registerTime asc", p).getResultList();
	}

	/**
	 * 获取 id 和list
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Integer> getIdsByLasttime(Date date) {
		return (List<Integer>) Q("select u.id from User u where u.lastLoginTime>:date order by u.lastLoginTime desc").setParameter("date", date).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersOrderByExp(Pagination p) {
		return (List<User>) Q("from User u order by exp desc", p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersOrderByMoney(Pagination p) {
		return (List<User>) Q("from User u order by money desc", p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersOrderByTopic(Pagination p) {
		return (List<User>) Q("from User u order by numberOfTopic desc", p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersOrderByReply(Pagination p) {
		return (List<User>) Q("from User u order by numberOfReply desc", p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersOrderByGood(Pagination p) {
		return (List<User>) Q("from User u order by numberOfGood desc", p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsers(Pagination p) {
		p.setRecordSize(User.getNumberOfUsers());
		return (List<User>) Q("from User u ", p).getResultList();
	}

	// ---------------------user register manager
	@SuppressWarnings("unchecked")
	public static
			List<User> getUsersByAccoutStatusType(byte type, Pagination p) {
		p.setRecordSize(User.getNumberOfUsersByAccoutStatusType(type));
		return (List<User>) Q("from User u where u.accountStatus=?1", P(1, type), p).getResultList();
	}

	private static int getNumberOfUsersByAccoutStatusType(byte type) {
		try {
			return ((Long) SQ("select count(*) from User u where u.accountStatus=?1", P(1, type))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByIdcodeAndStudentid(
			String sid, String icode) {
		return (List<User>) Q("from User u where u.studentid=?1 and u.idcode=?2", P(1, sid), P(2, icode)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getRegisterUsersToday(Pagination p) {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		p.setRecordSize(User.getNumberRegisterUsersToday());
		return (List<User>) Q("from User u where u.registerTime like '%"
				+ sdf.format(today) + "%'", p).getResultList();
	}

	private static int getNumberRegisterUsersToday() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return ((Long) SQ("select count(*) from User u where u.registerTime like  '%"
					+ sdf.format(today) + "%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<User> getLastRegisterUsers(
			String start, String end, Pagination p) {
		p.setRecordSize(User.getNumberLastRegisterUsers(start, end));
		return (List<User>) Q("from User u where u.registerTime between '"
				+ Util.getDisignDate(start, -1) + "' and '"
				+ Util.getDisignDate(end, 1) + "'  order by u.registerTime", p).getResultList();
	}

	private static int getNumberLastRegisterUsers(String start, String end) {
		try {
			return ((Long) SQ("select count(*) from User u where u.registerTime between '"
					+ Util.getDisignDate(start, -1)
					+ "' and '"
					+ Util.getDisignDate(end, 1) + "'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	// -------------------------------------------------------------

	/**
	 * 用户当前money是否足以付账。
	 * 
	 * @param cost
	 *            花费。
	 * @return
	 */
	public boolean canPayFor(int cost) {
		return this.money - cost >= 0;
	}

	/**
	 * 用户当前体力是否足够。
	 * 
	 * @param power
	 *            需要体力。
	 * @return
	 */
	public boolean hasEnoughPower(int power) {
		return this.getCurrentPower() - power >= 0;
	}

	/**
	 * 消耗体力。
	 * 
	 * @param power
	 *            消耗的体力值。
	 * @throws OutOfPowerException
	 *             如果体力值不足。
	 */
	public void usePower(int power) throws BBSException {
		if (!hasEnoughPower(power)) {
			throw new BBSException(BBSExceptionMessage.OUT_OF_POWER);
		}

		this.currentPower -= power;
	}

	/**
	 * 消耗体力，忽略体力不足的情况。
	 * 
	 * @param power
	 *            消耗的体力值。
	 * @throws OutOfPowerException
	 */
	public void usePowerIngore(int power) {
		this.currentPower -= power;
		this.currentPower = this.currentPower < 0 ? 0 : this.currentPower;
	}

	public int getAgio() {
		return this.getUserGroup().getAgio();
	}

	/**
	 * 计算用户为此上传项目所须付的金额。
	 * 
	 * @param item
	 *            上传项目
	 * @return
	 */
	public int reckonPriceOfUploadItemPrice(UploadItem item) {
		return Util.reckonCost(item.reckonPrice(), getAgio());
	}

	/**
	 * 付账。扣除用户的money。
	 * 
	 * @param cost
	 *            花费的money。
	 * @throws OutOfMoneyException
	 *             如果当前money不足以付账。
	 */
	public void payFor(int cost) throws BBSException {
		if (!canPayFor(cost)) {
			throw new BBSException(BBSExceptionMessage.OUT_OF_MONEY);
		}

		this.money -= cost;
	}

	/**
	 * 补充体力(如果是第二天登录)
	 * 
	 * 增加每天中午补充
	 */
	public void addPower() {
		if (!DateUtils.isSameDay(getLastLoginTime(), Util.getCurrentTime())) {
			setCurrentPower(getMaxPower());
			return;
		}
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(TimerUtils.getDate());
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			if (getLastLoginTime().before(calendar.getTime())
					&& Util.getCurrentTime().after(calendar.getTime())) {
				setCurrentPower(getMaxPower());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void addPower(int power) {
		this.currentPower += power;
		this.currentPower = this.currentPower < 0 ? 0 : this.currentPower;
	}

	/**
	 * 增加登录次数。
	 * 
	 */
	public void addLoginTimes() {
		setLoginTimes(getLoginTimes() + 1);
	}

	/**
	 * 取得用户的最大体力值。
	 * 
	 * @return
	 */
	public int getMaxPower() {
		return this.getUserGroup().getMaxPower();
	}

	/**
	 * 增加用户的money。
	 * 
	 * @param money
	 *            增加的money。
	 */
	public void addMoney(int money) {
		this.money += money;
		this.money = this.money < 0 ? 0 : this.money;
	}

	/**
	 * 增加用户经验值。
	 * 
	 * @param exp
	 *            增加的经验值。
	 */
	public void addExp(int exp) {
		this.exp += exp;
		this.exp = this.exp < 0 ? 0 : this.exp;
	}

	/**
	 * 增加用户发表的主题数。
	 * 
	 * @param number
	 *            增加的主题数。
	 */
	public void addNumberOfTopic(int number) {
		this.numberOfTopic += number;
	}

	/**
	 * 增加用户发表的回复数。
	 * 
	 * @param number
	 *            增加的回复数。
	 * 
	 */
	public void addNumberOfReply(int number) {
		this.numberOfReply += number;
	}

	/**
	 * 增加用户发表的精华数。
	 * 
	 * @param number
	 *            增加的精华数。
	 * 
	 */
	public void addNumberOfGood(int number) {
		this.numberOfGood += number;
	}

	public void addBadboy(int badboy) {
		this.badboy += badboy;
		this.badboy = this.badboy < 0 ? 0 : this.badboy;
	}

	private void canUseItem(ItemLine itemLine) throws BBSException {
		// 不能使用别人的物品
		if (!isSelf(itemLine.getUser())) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
	}

	public void useItem(ItemLine itemLine, User object) throws BBSException {
		canUseItem(itemLine);
		itemLine.use(object);
	}

	public void useItem(ItemLine itemLine, Topic topic) throws BBSException {
		canUseItem(itemLine);
		itemLine.use(topic);
	}

	public void complimentAwayItem(ItemLine itemLine, User object, String wish)
			throws BBSException {
		canUseItem(itemLine);

		itemLine.changeOwner(object, wish);
	}

	public boolean isSelf(User user) {
		return this.getId() == user.getId();
	}

	public void sellItem(ItemLine itemLine, int price) throws BBSException {
		canUseItem(itemLine);

		FreeMarketItem.addItemSell(itemLine.getItem(), this, price, itemLine.getMakerId());
		itemLine.delete();
	}

	public Authorization getAuthorization() {
		return new Authorization(this.getId());
	}

	public boolean isOwnTopic(Topic topic) {
		return topic.getTopicUser().equals(this);
	}

	public boolean isOwnReply(Reply reply) {
		return reply.getPostUser().equals(this);
	}

	public boolean isOwnSmallNews(SmallNews smallNews) {
		return smallNews.getUser().equals(this);
	}

	public boolean isOwnMessage(Message message) {
		return message.getUser().equals(this);
	}

	public void reply(Forum forum, Bank bank) throws BBSException {
		addExp(forum.getReplyExp());
		addMoney(forum.getReplyMoney());
		addNumberOfReply(1);
		usePower(Constants.REPLY_POWER);
		bank.payout(forum.getReplyMoney());
	}

	public void newTopic(Forum forum, Bank bank) throws BBSException {
		addMoney(forum.getTopicMoney());
		addExp(forum.getTopicExp());
		addNumberOfTopic(1);
		usePower(Constants.TOPIC_POWER);
		bank.payout(forum.getTopicMoney());
	}

	public void deleteTopic(Forum forum, Bank bank) throws BBSException {
		// forum.getDelMoney() 应为负值
		addMoney(forum.getDelMoney());
		addExp(forum.getDelExp());
		bank.income(0 - forum.getDelMoney());
	}

	public void deleteReply(Bank bank) throws BBSException {
		addMoney(0 - 3);
		addExp(0 - 3);
		bank.income(3);
	}

	public void gainGoodTopic(Forum forum, Bank bank) throws BBSException {
		addMoney(forum.getGoodMoney());
		addExp(forum.getGoodExp());
		addNumberOfGood(1);
		bank.payout(forum.getGoodMoney());
	}

	public void decreaseGoodTopic(Forum forum, Bank bank) {
		addMoney(0 - forum.getGoodMoney());
		addExp(0 - forum.getGoodExp());
		addNumberOfGood(0 - 1);
		bank.income(forum.getGoodMoney());
	}

	public void gainLightTopic(Forum forum, Bank bank) throws BBSException {
		addMoney(forum.getLightMoney());
		addExp(forum.getLightExp());
		bank.payout(forum.getLightMoney());
	}

	public void decreaseLightTopic(Forum forum, Bank bank) {
		addMoney(0 - forum.getLightMoney());
		addExp(0 - forum.getLightExp());
		bank.income(forum.getLightMoney());
	}

	public void newSmallNews(Bank bank) throws BBSException {
		if (canPayFor(1000)) {
			payFor(1000);
		} else {
			throw new BBSException(BBSExceptionMessage.OUT_OF_MONEY);
		}
		bank.payout(1000);
	}

	public boolean isOnline() {
		try {
			OnlineUser.getByUserId(this.getId());
			return true;
		} catch (ObjectNotFoundException e) {
			return false;
		}
	}

	public String getTitleDisplay() {
		if (StringUtils.isEmpty(this.title)) {
			return Constants.DEFAULT_TITLE;
		}
		return StringUtils.replace("<font color='#FF0000'>${title}</font>", "${title}", this.title);
	}

	public int getTotalPostDisplay() {
		return this.numberOfTopic + this.numberOfReply;
	}

	public String getRegisterDateDisplay() {
		return Util.formatTime(Util.DATE_FORMAT, this.registerTime);
	}

	public String getSexDisplay() {
		StringBuffer sb = new StringBuffer();
		sb.append("<img src='/images/sex_").append(isSex() ? "1" : "0").append(isOnline() ? "1"
				: "0").append(".gif' alt='${alt}' />");
		String alt = null;
		if (isOnline()) {
			if (isSex()) {
				alt = "美女，在线！";
			} else {
				alt = "帅哥，在线！";
			}
		} else {
			if (isSex()) {
				alt = "美女，不在线！";
			} else {
				alt = "帅哥，不在线！";
			}
		}
		return StringUtils.replace(sb.toString(), "${alt}", alt);
	}

	public String getFaceDisplay() {
		String propsFace = ItemWorkManager.getPropsFace(this);
		if (propsFace != null) {
			return propsFace;
		}
		if (StringUtils.isEmpty(getFace())
				|| "null".equalsIgnoreCase(getFace())) {
			return Constants.DEFAULT_USER_FACE;
		}
		return getFace();
	}

	public String getGroupNameDisplay() {
		String result = "<font color='${color}'>${name}</font>";
		result = StringUtils.replace(result, "${color}", getUserGroup().getDisplayColor());
		return StringUtils.replace(result, "${name}", getUserGroup().getGroupName());
	}

	public String getIdiographFilter() {
		return FilterUtil.idiograph(getIdiograph());
	}

	public int getLoginmtDisplay() {
		return ((Long) (loginmt / 8640000)).intValue();// 1000*60*60*24 =
		// 8640000 一天时间
	}

	public void changeAuthority(int groupid, int groupTypeId) {
		this.setGroupId(groupid);
		this.setGroupTypeId(groupTypeId);
	}

	// 改变图文的排列方式，文字的在上，勋章图片下面，好排版 daodaoyu
	public String getHonorDisplay() {
		String result = "";
		for (int i = 0; i < getHonors().size(); i++) {
			if (getHonors().get(i).getType() != 2) {
				result += "<font color='red'><li class='honorli'><img src=/images/honor1.gif width=16 height=16 />";
				result += getHonors().get(i).getName();
				result += "</li></font>";
			}
		}
		// for (int i = 0; i < getHonors().size(); i++) {
		// if (getHonors().get(i).getType() == 2) {
		// result += "<li class='honorli'><img src='/images/honor/honor" +
		// getHonors().get(i).getId() +
		// ".gif' alt='"+getHonors().get(i).getName()+"' class='honorimg'/></li>";
		// }
		// }
		return result;
	}

	// 列出包含用户已经有的荣誉和未有的荣誉供选择 add daodaoyu
	public String getUserHonor() {
		String result = "";
		if (getHonors().size() > 0) {
			for (Honor h : getHonors()) {

				result += "<input name='honor' type='checkbox' id='honor' value='"
						+ h.getId() + "'  checked='checked'>";
				result += h.getName();
			}
		}
		List<Honor> leaveHonors = getLeaveHonor();
		if (leaveHonors.size() > 0) {
			for (Honor h : leaveHonors) {
				result += "<input name='honor' type='checkbox' id='honor' value='"
						+ h.getId() + "'>";
				result += h.getName();
			}
		}
		return result;
	}

	// 新用户注册一天后才可以发贴，请你熟悉一下论坛的发贴规则；
	public boolean isNewCome() {
		Date systime = new Date();
		if (systime.getTime() - this.registerTime.getTime() < Util.ONE_DAY) {
			return true;
		}
		return false;
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "user" + new LinkedHashMap<String, Object>() {

			{
				put("id", id);
				put("username", username);
				put("password", password);
				put("money", money);
				put("badboy", badboy);
				put("email", email);
				put("sex", sex);
				put("confrere", confrere);
				put("idcode", idcode);
				put("birthday", birthday);
				put("nick", nick);
				put("trueName", trueName);
				put("homepage", homepage);
				put("qq", qq);
				put("idiograph", idiograph);
				put("registerTime", registerTime);
				put("groupId", groupId);
				put("groupTypeId", groupTypeId);
			}
		}.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof User) {
			return ((User) obj).getId() == this.getId();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByGroupType(int GroupType) {
		return (List<User>) Q("from User u where u.groupTypeId=?1", P(1, GroupType)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByGroupType(int GroupType, Pagination p) {
		try {
			p.setRecordSize(((Long) SQ("select count(*) from User u where u.groupTypeId=?1", P(1, GroupType))).intValue());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		return (List<User>) Q("from User u where u.groupTypeId=?1", P(1, GroupType), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByGroupTypeSortByExp(int GroupType) {
		return (List<User>) Q("from User u where u.groupTypeId=?1 order by u.exp desc", P(1, GroupManager.FORUM_WEBMASTER_GROUP)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByGroupTypeSortByMoney(int GroupType) {
		return (List<User>) Q("from User u where u.groupTypeId=?1 order by u.money desc", P(1, GroupManager.FORUM_WEBMASTER_GROUP)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByGroupTypeSortByTopics(int GroupType) {
		return (List<User>) Q("from User u where u.groupTypeId=?1 order by u.numberOfTopic desc", P(1, GroupManager.FORUM_WEBMASTER_GROUP)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByGroupTypeSortByReplys(int GroupType) {
		return (List<User>) Q("from User u where u.groupTypeId=?1 order by u.numberOfReply desc", P(1, GroupManager.FORUM_WEBMASTER_GROUP)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByGroupTypeSortBygoods(int GroupType) {
		return (List<User>) Q("from User u where u.groupTypeId=?1 order by u.numberOfGood desc", P(1, GroupManager.FORUM_WEBMASTER_GROUP)).getResultList();
	}

	public static int getNumberOfGoods(String span) {
		return 0;
	}

	public static int getNumberOfTopics(String span) {
		return 0;
	}

	public static int getNumberOfReplys(String span) {
		return 0;
	}

	/**
	 * 获取用户的状态信息，这个是由html元素展示的<br />
	 * 分析state字段中的信息，这个字段的信息应该是这样子的： \images/item/error.gif#描述信息#-#｛另外一个｝
	 * 以#-#分隔， 以#分隔内容
	 * 
	 * @return
	 */
	public String getHtmlState() {
		if (getState() == null)
			return "";
		String temp[] = state.split("#-#");
		StringBuffer sb = new StringBuffer();
		for (String t : temp) {
			String item[] = t.split("#");
			sb.append("<img src='" + item[0] + "' class='USER_STATE' title=\""
					+ item[1] + "\"/>");
		}
		return sb.toString();
	}

	/**
	 * 获取用户的全部名称，包括状态信息
	 * 
	 * @return
	 */
	public String getFullNick() {
		return getNick() + (isAllowState() ? getHtmlState() : "");
	}

	/**
	 * 有时需要对一些用户的昵称进行加色处理，这时就需要判断了 state 字段中对颜色跟时间进行了定义 格式：color|#92cf28
	 * 
	 */
	public String getColorNick() {
		if (state != null) {
			if (state.trim().startsWith("color"))
				return "<font color='" + state.replace("color|", "") + "'>"
						+ nick + "</font>";
			/*
			 * 添加图标 格式：class@类名@提示中是否显示昵称@提示内容
			 */
			if (state.trim().startsWith("class")) {
				String a[] = state.split("@");
				boolean name = Boolean.valueOf(a[2]);
				return nick + "<img border='0' src='" + a[1] + "' title='"
						+ (name ? nick : "") + a[3] + "'/>";
			}
		}
		return nick;
	}

	/**
	 * 获取用户的头像的实际地址，用于新版主页显示
	 * 
	 * @author ivy
	 * @since 2012-04-07
	 */
	public String getFaceSource() {
		String src = "";
		src = getFace();
		Pattern pattern = Pattern.compile("images.+\\.gif");
		Matcher matcher = pattern.matcher(src);
		if (matcher.find()) {
			return matcher.group();
		}

		pattern = Pattern.compile("images.+\\.jpg");
		matcher = pattern.matcher(src);
		if (matcher.find()) {
			return matcher.group();
		}

		pattern = Pattern.compile("images.+\\.png");
		matcher = pattern.matcher(src);
		if (matcher.find()) {
			return matcher.group();
		}

		return "";
	}

	/*
	 * public static Paging<User> getLastWeekMostActiveUsers(int pageNO, int
	 * howMany) { Paging<User> paging = new Paging<User>(pageNO, howMany,
	 * size("User")); EntityManager entityManager = getEntityManager();
	 * List<User> lastWeekMostActiveUsers = null; lastWeekMostActiveUsers =
	 * entityManager.
	 * createQuery("from User u order by (u.exp - u.lastWeekExp) desc",
	 * User.class)
	 * .setFirstResult(paging.getBeginRow()).setMaxResults(howMany).getResultList
	 * (); return paging.setList(lastWeekMostActiveUsers); }
	 */
	/**
	 * 获取最近一周最活跃的用户。
	 * 
	 * @author longkai
	 * @since 2012-09-20
	 */
	@SuppressWarnings("unchecked")
	public static List<User> getLastWeekMostActiveUsers(Pagination p) {
		return (List<User>) Q("from User u where u.exp >= u.lastWeekExp order by (u.exp - u.lastWeekExp) desc", p).getResultList();
	}

//	/**
//	 * 获取活跃度最高的用户。
//	 * 
//	 * @return 活跃度最高的用户。
//	 * @author longkai
//	 * @since 2012-09-21
//	 */
//	public static User getLastWeekMostActivedUser() {
//		return (User) Q("from User u where (u.exp - u.lastWeekExp) = select max(u2.exp - u2.lastWeekExp) from User u2)").getSingleResult();
//	}

	@SuppressWarnings("unchecked")
	public static List<User> getLastWeekMostActiveUsers(int count) {
		return (List<User>) Q("from User u where u.exp >= u.lastWeekExp order by (u.exp - u.lastWeekExp) desc").setFirstResult(0).setMaxResults(count).getResultList();
	}

	/**
	 * 用于每周的一个时刻更新用户上周的经验。
	 */
	@Transactional
	public static void updateLastWeekExp() {
		EntityManager entityManager = getEntityManager();
		// if (entityManager != null) {
		// System.out.println("123");
		// //
		// entityManager.createQuery("update User u set u.lastWeekExp = u.exp").executeUpdate();
		//
		// Q("update User u set u.lastWeekExp = u.exp").executeUpdate();
		// } else {
		// System.out.println(321);
		// }
//		entityManager.getTransaction().begin();
		entityManager.createQuery("update User u set u.lastWeekExp = u.exp").executeUpdate();
//		entityManager.getTransaction().commit();
	}

}