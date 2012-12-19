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
@Table(name = "message_folder")
public class MessageFolder extends JPAEntity {

	private static final long serialVersionUID = 7472550896174839027L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_message_folder")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "name", length = 8)
	private String name;

	private int size;

	@Column(name = "create_time")
	private Date time;

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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// ------------------------------------------------

	public static MessageFolder get(int fid) throws ObjectNotFoundException {
		return (MessageFolder) getById(MessageFolder.class, fid);
	}

	public static MessageFolder getDefaultFolder() {
		MessageFolder folder;
		try {
			folder = get(1);
		} catch (ObjectNotFoundException e) {
			folder = new MessageFolder();
			folder.setId(1);
			folder.setSize(30);
		}
		return folder;
	}

	public static MessageFolder getDraftFolder() {
		MessageFolder folder;
		try {
			folder = get(2);
		} catch (ObjectNotFoundException e) {
			folder = new MessageFolder();
			folder.setId(2);
			folder.setSize(30);
		}
		return folder;
	}

	public static MessageFolder getTrackFolder() {
		MessageFolder folder;
		try {
			folder = get(3);
		} catch (ObjectNotFoundException e) {
			folder = new MessageFolder();
			folder.setId(3);
			folder.setSize(30);
		}
		return folder;
	}

	@SuppressWarnings("unchecked")
	public List<MessageFolder> getReceivedMessages(User user) {
		return (List<MessageFolder>) Q(
				"from MessageFolder f where f.user = ?1 order id asc",
				P(1, user)).getResultList();
	}

}
