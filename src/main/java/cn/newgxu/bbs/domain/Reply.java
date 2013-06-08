package cn.newgxu.bbs.domain;

import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;
import cn.newgxu.ng.util.DateTime;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 * 
 * 修改日志：
 * 1.添加了  editTime 字段，记录了最后修改的时间，用于判断新旧编辑器
 */
@Entity
@Table(name = "reply")
public class Reply extends JPAEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_reply")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "topicid")
	private Topic topic;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User postUser;

	@Column(name = "post_time")
	private Date postTime;
	@Column(name="last_edit_time")
	private Date editTime;
	
	private boolean invalid;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id")
	private Content contentBean;

	@Column(name = "first_reply_token")
	private boolean firstReply;

	private boolean screen;
	
//	@Column(name="reply_content_id")
//	private int replyContentId;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isScreen() {
		return screen;
	}

	public void setScreen(boolean screen) {
		this.screen = screen;
	}

	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public User getPostUser() {
		return postUser;
	}

	public void setPostUser(User postUser) {
		this.postUser = postUser;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public boolean isFirstReply() {
		return firstReply;
	}

	public void setFirstReply(boolean firstReply) {
		this.firstReply = firstReply;
	}
	
	// ------------------------------------------------

	public static Reply get(int id) throws ObjectNotFoundException {
		return (Reply) getById(Reply.class, id);
	}
	
	public static Reply getByContentId(int replyContentId){
		return (Reply)Q("from Reply r where r.contentBean.id=?1",P(1,replyContentId)).getSingleResult();
	}
	
	public Content getContentBean() {
		if (this.contentBean == null) {
			this.contentBean = new Content();
		}
		return this.contentBean;
	}

	public String getContent() {
		if (isScreen()) {
			return Constants.SCREEN_CONTENT;
		} else {
			return getContentBean().getContent();
		}
	}

	public String getContentFilter(){
		if (isScreen()) {
			return Constants.SCREEN_CONTENT;
		} else {
			//System.out.println("这里的content:"+getContentBean().getContent());
			//return getContentBean().getContent();
			//如果是旧的帖子，返回的是filter，新的就是直接的内容
			/*
			if(!ForumConfig.isBeforeNewEfitor(getEditTime())){
				Content content=getContentBean();
				MpFilter mpf=new MpFilter(null);
				String result=content.getContent();
				result=mpf.newConvert(result);
				return result;
			}*/
			return getContentBean().getContentFilter();
		}
	}

	public void setContent(String content) {
		Content c = getContentBean();
		c.setContent(content);
		this.contentBean = c;
	}
	
	public static int getReplysSize(Forum forum) {
		try {
			return ((Long) SQ("select count(*) from Reply r where topic.forum = ?1 ", P(1, forum))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}
	
	public static int getSizeByContent(String key){
		try {
			return ((Long) SQ("select count(*) from Reply t where t.firstReply=1 and invalid = 0 and t.contentBean.content like '%"
					+ key
					//+ "%' and invalid = 0 and t.forum.secrecy=0 and t.forum.confrere=0 order by id desc"))
					+ "%' order by id desc"))
					.intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}
	@SuppressWarnings("unchecked")
	public static List<Reply> getReplysByContent(String key,Pagination p){
		return (List<Reply>) Q(
				"from Reply t where t.firstReply=1 and invalid = 0 and t.contentBean.content like '%"
						+ key
						+ "%' order by id desc",
				p).getResultList();
	}
	public static List<Topic> getTopicsByContent(String key,Pagination p){
		List<Reply> list=getReplysByContent(key, p);
		List<Topic> topics=new ArrayList<Topic>();
		for(Reply r:list)
			topics.add(r.getTopic());
		return topics;
	}
	

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "reply" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("topic", topic);
				put("postUser", postUser);
				put("postTime", postTime);
				put("invalid", invalid);
				put("content", contentBean);
			}
		}.toString();
	}
	
	public String getRelativeTime() {
		return DateTime.getRelativeTime(this.postTime.getTime());
	}
	
	/*****************************************************************************
		REST API 接口的实现 2013 longkai
	/*****************************************************************************/

	/** 目前只是单向的 */
	public static List<Reply> fetchReplies(int tid, int lastReplyId, int count) {
		String hql = null;
		if (lastReplyId == 0) {
			hql = String.format("FROM Reply r WHERE r.topic.id = %d AND r.firstReply IS FALSE AND r.invalid IS FALSE ORDER BY r.id DESC", tid);
		} else {
			hql = String.format("FROM Reply r WHERE r.topic.id = %d AND r.id < %d AND r.firstReply IS FALSE AND r.invalid IS FALSE ORDER BY r.id DESC", tid, lastReplyId);
		}
		return getEntityManager().createQuery(hql).setFirstResult(0).setMaxResults(count).getResultList();
	}

	/*****************************************************************************
		REST API 接口的实现 2013 longkai
	/*****************************************************************************/
}		