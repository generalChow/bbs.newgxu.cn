package cn.newgxu.bbs.domain;

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
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "short_message")
public class ShortMessage extends JPAEntity {

	private static final long serialVersionUID = -7064156094652847410L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_short_message")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	private User receiver;

	private int senderId;

	private int face;

	private boolean readed;

	private String title;

	private String content;

	private Date postTime;

	private boolean friendQuery;

	private boolean saved;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public boolean isFriendQuery() {
		return friendQuery;
	}

	public void setFriendQuery(boolean friendQuery) {
		this.friendQuery = friendQuery;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// ------------------------------------------------

	public void setSender(User sender) {
		this.senderId = sender.getId();
	}

	public User getSender() {
		if (this.senderId == 0) {
			return new User();
		}
		return User.getCertainExist(this.senderId);
	}

	private void saveMessage(String title, String content, int face,
			User receiver, User sender, boolean friendQuery) {
		ShortMessage msg = new ShortMessage();
		msg.setReceiver(receiver);
		msg.setSenderId(sender == null ? 0 : sender.getId());
		msg.setFace(face);
		msg.setReaded(false);
		msg.setTitle(title);
		msg.setContent(content);
		msg.setPostTime(new Date());
		msg.setFriendQuery(friendQuery);
		msg.setSaved(false);

		msg.save();
	}

	public void sendMessage(String title, String content, int face,
			List<User> receivers, User sender, boolean friendQuery) {
		for (User receiver : receivers) {
			saveMessage(title, content, face, receiver, sender, friendQuery);
		}
	}

	public void sendSystemMessage(String title, String content, int face,
			List<User> receivers, boolean friendQuery) {
		for (User receiver : receivers) {
			saveMessage(title, content, face, receiver, null, friendQuery);
		}
	}

	public void sendMessage(String title, String content, int face,
			User receiver, User sender, boolean friendQuery) {
		saveMessage(title, content, face, receiver, sender, friendQuery);
	}

	public void sendSystemMessage(String title, String content, int face,
			User receiver, boolean friendQuery) {
		saveMessage(title, content, face, receiver, null, friendQuery);
	}

	@SuppressWarnings("unchecked")
	public List<ShortMessage> getReceivedMessages(User receiver, Pagination p) {
		return (List<ShortMessage>) Q(
				"from ShortMessage msg where msg.receiver = ?1 order by msg,readed desc, msg.postTime desc",
				P(1, receiver), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ShortMessage> getSendedMessages(User sender, Pagination p) {
		return (List<ShortMessage>) Q(
				"from ShortMessage msg where msg.sender = ?1 order by msg.postTime desc",
				P(1, receiver), p).getResultList();
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "shortMessage" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("receiver", receiver);
				put("senderId", senderId);
				put("face", face);
				put("readed", readed);
				put("title", title);
				put("content", content);
				put("postTime", postTime);
				put("friendQuery", friendQuery);
				put("saved", saved);
			}
		}.toString();
	}

}
