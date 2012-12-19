package cn.newgxu.bbs.domain.message;

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
import cn.newgxu.bbs.common.filter.FilterUtil;
import cn.newgxu.bbs.domain.market.ItemLine;
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
@Table(name = "message")
public class Message extends JPAEntity {

	private static final long serialVersionUID = -687927691438455641L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_message")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "folder_id")
	private MessageFolder folder;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private User sender;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private User receiver;

	private String receivers;

	private int face;

	private String title;

	private String content;

	@Column(name = "create_time")
	private Date time;

	@Column(name = "readed")
	private boolean readed;

	@Column(name = "is_del")
	private boolean del;

	public String getContent() {
		return FilterUtil.ubb(content);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public MessageFolder getFolder() {
		return folder;
	}

	public void setFolder(MessageFolder folder) {
		this.folder = folder;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTitle() {
		return FilterUtil.title(title);
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

	// --------------------------------------

	public static Message get(int id) throws ObjectNotFoundException {
		return (Message) getById(Message.class, id);
	}

	private static void saveMessage(String title, String content, int face,
			MessageFolder folder, User receiver, User sender, String userNicks) {
		Message msg = new Message();
		msg.setUser(receiver);
		msg.setReceiver(receiver);
		msg.setSender((sender == null) ? new User() : sender);
		msg.setFace(face);
		msg.setReaded(false);
		msg.setDel(false);
		msg.setTitle(title);
		msg.setContent(content);
		msg.setTime(new Date());
		msg.setReceivers(userNicks);
		msg.setFolder(folder);
		msg.save();
	}

	/**
	 * 当使用物品时，满足一定条件时，就会对用户发送提示信息。
	 * @param il
	 */
	public static void sendItemLineMessage(ItemLine il){
		saveMessage(il.getItem().getMessage_title(),
				il.getItem().getMessage(),
				1,MessageFolder.getDefaultFolder(),
				il.getObject(),il.getUser(),il.getObject().getNick());
	}
	
	public static void saveDraftMessage(String title, String content, int face,
			User user, String userNicks) {
		saveMessage(title, content, face, MessageFolder.getDraftFolder(), user,
				user, userNicks);
	}

	public static void saveTrackMessage(String title, String content, int face,
			User user, String userNicks) {
		saveMessage(title, content, face, MessageFolder.getTrackFolder(), user,
				user, userNicks);
	}

	public static void sendMessage(String title, String content, int face,
			List<User> receivers, User sender, String userNicks) {
		for (User receiver : receivers) {
			saveMessage(title, content, face, MessageFolder.getDefaultFolder(),
					receiver, sender, userNicks);
		}
	}

	public static void sendMessage(String title, String content, int face,
			User receiver, User sender, String userNicks) {
		saveMessage(title, content, face, MessageFolder.getDefaultFolder(),
				receiver, sender, userNicks);
	}

	public static void sendSystemMessage(String title, String content,
			int face, List<User> receivers, String userNicks) {
		for (User receiver : receivers) {
			saveMessage(title, content, face, MessageFolder.getDefaultFolder(),
					receiver, null, userNicks);
		}
	}

	public static void sendSystemMessage(String title, String content,
			int face, User receiver, String userNicks) {
		saveMessage(title, content, face, MessageFolder.getDefaultFolder(),
				receiver, null, userNicks);
	}

	@SuppressWarnings("unchecked")
	public static List<Message> getMessages(User user, MessageFolder folder,
			Pagination p) {
		return (List<Message>) Q(
				"from Message msg where user = ?1 and folder = ?2 and del = 0 order by readed asc, time desc",
				P(1, user), P(2, folder), p).getResultList();
	}

	public static int getMessageSize(User user, MessageFolder folder) {
		try {
			return ((Long) SQ(
					"select count(*) from Message msg where user = ?1 and folder = ?2 and del = 0",
					P(1, user), P(2, folder))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getMessageSizeNotRead(User user) {
		try {
			return ((Long) SQ(
					"select count(*) from Message msg where user = ?1 and folder.id = 1 and readed = 0",
					P(1, user))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public String getReadedString() {
		if (isReaded()) {
			return "<img src=\"/images/shortMessage/true.gif\" border=\"0\" />";
		} else {
			return "<img src=\"/images/shortMessage/false.gif\" border=\"0\" />";
		}
	}

}
