package cn.newgxu.bbs.web.model.message;

import java.util.List;

import cn.newgxu.bbs.domain.message.Message;
import cn.newgxu.bbs.domain.message.MessageFolder;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MessageModel extends PaginationBaseModel {

	private int id;

	private int folderId = 1;

	private User user;

	private Message message;

	private MessageFolder folder;

	private List<Message> messages;

	private List<MessageFolder> folders;

	public MessageFolder getFolder() {
		return folder;
	}

	public void setFolder(MessageFolder folder) {
		this.folder = folder;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public List<MessageFolder> getFolders() {
		return folders;
	}

	public void setFolders(List<MessageFolder> folders) {
		this.folders = folders;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
