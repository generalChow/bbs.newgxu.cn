package cn.newgxu.bbs.domain;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.util.Util;
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
@Table(name = "forum")
public class Forum extends JPAEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_forum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "name", length = 20)
	private String name;

	@Column(name = "description", length = 500)
	private String description;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private Area area;

	@Column(name = "comp_id")
	private int compositorId;

	private boolean hot;

	private boolean secrecy;

	private boolean confrere;

	@Column(name = "topic_money")
	private int topicMoney;

	@Column(name = "reply_money")
	private int replyMoney;

	@Column(name = "topic_exp")
	private int topicExp;

	@Column(name = "reply_exp")
	private int replyExp;

	@Column(name = "good_money")
	private int goodMoney;

	@Column(name = "good_exp")
	private int goodExp;

	@Column(name = "del_money")
	private int delMoney;

	@Column(name = "del_exp")
	private int delExp;

	@Column(name = "light_money")
	private int lightMoney;

	@Column(name = "light_exp")
	private int lightExp;

	@Column(name = "limit_topics_perday")
	private int limit_topics_perday;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "webmaster_forum", joinColumns = @JoinColumn(name = "forum_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> webmasters;

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public boolean isConfrere() {
		return confrere;
	}

	public void setConfrere(boolean confrere) {
		this.confrere = confrere;
	}

	public int getLightExp() {
		return lightExp;
	}

	public void setLightExp(int lightExp) {
		this.lightExp = lightExp;
	}

	public int getLightMoney() {
		return lightMoney;
	}

	public void setLightMoney(int lightMoney) {
		this.lightMoney = lightMoney;
	}

	public boolean isSecrecy() {
		return secrecy;
	}

	public void setSecrecy(boolean secrecy) {
		this.secrecy = secrecy;
	}

	public List<User> getWebmasters() {
		return webmasters;
	}

	public void setWebmasters(List<User> webmasters) {
		this.webmasters = webmasters;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public int getCompositorId() {
		return compositorId;
	}

	public void setCompositorId(int compositorId) {
		this.compositorId = compositorId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDelExp() {
		return delExp;
	}

	public void setDelExp(int delExp) {
		this.delExp = delExp;
	}

	public int getDelMoney() {
		return delMoney;
	}

	public void setDelMoney(int delMoney) {
		this.delMoney = delMoney;
	}

	public int getGoodExp() {
		return goodExp;
	}

	public void setGoodExp(int goodExp) {
		this.goodExp = goodExp;
	}

	public int getGoodMoney() {
		return goodMoney;
	}

	public void setGoodMoney(int goodMoney) {
		this.goodMoney = goodMoney;
	}

	public int getReplyExp() {
		return replyExp;
	}

	public void setReplyExp(int replyExp) {
		this.replyExp = replyExp;
	}

	public int getReplyMoney() {
		return replyMoney;
	}

	public void setReplyMoney(int replyMoney) {
		this.replyMoney = replyMoney;
	}

	public int getTopicExp() {
		return topicExp;
	}

	public void setTopicExp(int topicExp) {
		this.topicExp = topicExp;
	}

	public int getTopicMoney() {
		return topicMoney;
	}

	public void setTopicMoney(int topicMoney) {
		this.topicMoney = topicMoney;
	}

	// -----------------------------------------------

	public int getLimit_topics_perday() {
		return limit_topics_perday;
	}

	public void setLimit_topics_perday(int limitTopicsPerday) {
		limit_topics_perday = limitTopicsPerday;
	}

	public static Forum get(int forumId) throws ObjectNotFoundException {
		return (Forum) getById(Forum.class, forumId);
	}

	@SuppressWarnings("unchecked")
	public static List<Forum> getForumsByAreaId(int areaId) {
		return (List<Forum>) Q("from Forum f where f.areaId = ?1", P(1, areaId))
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Topic> getTopAllTopics() {
		return (List<Topic>) Q(
				"from Topic t where t.topType = 3 and t.invalid = 0"
						+ " order by t.replyTime desc").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Topic> getTopAreaTopics() {
		return (List<Topic>) Q(
				"from Topic t where t.topType = 2 and t.forum.area = ?1 and t.invalid = 0"
						+ " order by t.replyTime desc", P(1, this.area))
				.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public List<Topic> getTopTopics() {
		return (List<Topic>) Q(
				"from Topic t where t.topType = 1 and t.forum = ?1 and t.invalid = 0"
						+ " order by t.replyTime desc", P(1, this))
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Topic> getTopics(String sort, Pagination p) {
		try {
			p
					.setRecordSize(((Long) SQ(
							"select count(*) from Topic t where t.forum = ?1 and t.invalid = 0 and t.topType = 0 and t.forum.id>0",
							P(1, this))).intValue());
		} catch (ObjectNotFoundException e) {
			p.setRecordSize(0);
		}

		List<Topic> result = null;

		if (sort.equals(Constants.SORT_BY_CREATETIME)) {
			result = (List<Topic>) Q(
					"from Topic t where t.forum = ?1 and t.topType = 0 and t.invalid = 0"
							+ " order by t.id desc", P(1, this), p)
					.getResultList();

		} else if (sort.equals(Constants.SORT_BY_REPLYTIME)) {
			result = (List<Topic>) Q(
					"from Topic t where t.forum = ?1 and t.topType = 0 and t.invalid = 0"
							+ " order by t.replyTime desc", P(1, this), p)
					.getResultList();
		}

		// 如果是第一页，则需要显示置顶
		if (p.isFirstPage()) {
			List<Topic> topicList = new LinkedList<Topic>();
			topicList.addAll(getTopAllTopics());
			topicList.addAll(getTopAreaTopics());
			topicList.addAll(getTopTopics());
			topicList.addAll(result);

			return topicList;
		} else {
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Topic> getGoodTopics(Pagination p) {
		try {
			p
					.setRecordSize(((Long) SQ(
							"select count(*) from Topic t where t.forum = ?1 and t.invalid = 0 and t.goodTopic = true",
							P(1, this))).intValue());
		} catch (ObjectNotFoundException e) {
			p.setRecordSize(0);
		}
		return (List<Topic>) Q(
				"from Topic t where t.forum = ?1 and t.goodTopic = true and t.invalid = 0"
						+ " order by t.replyTime desc", P(1, this), p)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SmallNews> getSmallNewsList() {
		return (List<SmallNews>) Q(
				"from SmallNews s where s.invalid = 0 and s.createTime > ?2"
						+ " order by s.createTime desc",
				P(2, Util.getDateAfterDay(-2))).getResultList();
	}

	public Long getTopicsByForum() {
		try {
			return (Long) SQ("select count(*) from Topic where forum=?1", P(1,
					this));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取某个版块最新的帖子
	 * @param offset 偏移
	 * @param forumId 板块的id
	 * @param number 每次获取的数量
	 * @author longkai
	 * @since 2013-03-10
	 * @return list
	 */
	public static List<Topic> getLatestTopis(int offset, int forumId, int number) {
//		SQ("FROM Topic t where t.forum.id = ?1 and t.id < ?2 order by t.id desc")
		return getEntityManager()
				.createQuery("FROM Topic t where t.forum.id = :forumId and t.id < :offfset order by t.id desc", Topic.class)
				.setParameter("offfset", offset)
				.setParameter("forumId", forumId)
				.setMaxResults(number)
				.getResultList();
	}

	public String getHotString() {
		if (isHot()) {
			return "<FONT color=red>是</FONT>";
		} else {
			return "否";
		}
	}

	public String getSecrecyString() {
		if (isSecrecy()) {
			return "<FONT color=red>需</FONT>";
		} else {
			return "否";
		}
	}

	public String getConfrereString() {
		if (isConfrere()) {
			return "<FONT color=red>是</FONT>";
		} else {
			return "否";
		}
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "forum" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("name", name);
				put("description", description);
				put("area", area);
				put("compositorId", compositorId);
			}
		}.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Forum) {
			return ((Forum) obj).getId() == this.getId();
		}
		return false;
	}

}