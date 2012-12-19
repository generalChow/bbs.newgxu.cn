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
import javax.persistence.Transient;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "hottopic")
public class HotTopic extends JPAEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "title", length = 255)
	private String title;

	@Column(name = "pub_type")
	private int pubType;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "forum_id")
	private Forum forum;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_id")
	private Topic topic;

	@Column(name = "creation_time")
	private Date creationTime;

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public int getPubType() {
		return pubType;
	}

	public void setPubType(int pubType) {
		this.pubType = pubType;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	// ------------------------------------------------

	public static HotTopic get(int topicId) throws ObjectNotFoundException {
		return (HotTopic) getById(HotTopic.class, topicId);
	}

	@SuppressWarnings("unchecked")
	public static List<HotTopic> getPubTopics(int pubType, int num) {
		// return (List<HotTopic>)
		// Q("from HotTopic t where pubType = ?1 order by id desc", P(1,
		// pubType)).setFirstResult(0).setMaxResults(num).getResultList();
		List<HotTopic> hotTopics = (List<HotTopic>) Q(
				"from HotTopic t where pubType = ?1 order by id desc",
				P(1, pubType)).setFirstResult(0).setMaxResults(num)
				.getResultList();
		for (HotTopic hotTopic : hotTopics) {
			String content = hotTopic.getTopic()
					.getReplies(new Pagination(), false).get(0)
					.getContentFilter();
			splitAndFilterString(content, 20);
			// hotTopic.getTopic().getReplies(new Pagination(),
			// false).get(0).setContent(content);
			hotTopic.setContent(content);
		}
		return hotTopics;
	}

	@SuppressWarnings("unchecked")
	public static List<HotTopic> getAllHotTopic() {
		return (List<HotTopic>) Q("from HotTopic t").setFirstResult(0)
				.setMaxResults(10).getResultList();
	}

	public static HotTopic getByTopicId(int topicid)
			throws ObjectNotFoundException {
		return (HotTopic) SQ("from HotTopic t where topic.id=?1", P(1, topicid));
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "topic" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("title", title);
				put("forum", forum);
			}
		}.toString();
	}

	@SuppressWarnings("unchecked")
	public static void deleteAll() {
		List<HotTopic> list = Q("from HotTopic t").getResultList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).delete();
		}
	}

	@Transient
	private String content;

	/**
	 * 获取帖子的内容, 使之易用于10周年论坛改版(freemarker)
	 */
	public String getContent() {
		this.content = splitAndFilterString(
				this.getTopic().getReplies(new Pagination(), false).get(0)
						.getContentFilter(), 321);
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 添加用户过滤掉html标签的方法，用于首页的精彩分享推荐
	 * 
	 * @param input
	 *            读取的字符串
	 * @param length
	 *            抽取的摘要的长度
	 * @author ivy
	 */
	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length - 1);
			str += "......";
		}
		return str;
	}
}
