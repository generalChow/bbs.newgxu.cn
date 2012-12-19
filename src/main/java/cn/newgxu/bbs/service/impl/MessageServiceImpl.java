package cn.newgxu.bbs.service.impl;

import java.util.LinkedList;
import java.util.List;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.message.Message;
import cn.newgxu.bbs.domain.message.MessageFolder;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.MessageService;
import cn.newgxu.bbs.web.model.message.DelMessageModel;
import cn.newgxu.bbs.web.model.message.MessageModel;
import cn.newgxu.bbs.web.model.message.SendMessageModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MessageServiceImpl implements MessageService {

	public MessageFolder getFolder(User user, int fid) throws BBSException {
		try {

			MessageFolder folder = MessageFolder.get(fid);
			if (fid > 3 && !folder.getUser().equals(user)) {
				throw new BBSException("您无权打开此信箱");
			}
			return folder;
		} catch (ObjectNotFoundException e) {
			throw new BBSException("您无权打开此信箱");
		}
	}

	public void message(MessageModel model) throws BBSException {
		Message message;
		try {
			message = Message.get(model.getId());
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		if (!message.getUser().equals(model.getUser())) {
			throw new BBSException("您无权浏览此信息");
		}
		message.setReaded(true);
		message.save();
		model.setMessage(message);
	}

	public void messageList(MessageModel model) throws BBSException {
		User user = model.getUser();
		MessageFolder folder = getFolder(user, model.getFolderId());
		int size = Message.getMessageSize(user, folder);
		if (size > folder.getSize()) {
			size = folder.getSize();
		}
		model.setFolder(folder);
		model.getPagination().setRecordSize(size);
		model.setMessages(Message.getMessages(user, folder, model
				.getPagination()));
	}

	public void sendMessage(SendMessageModel model) throws BBSException {
		if (model.getUserId() > 0) {
			try {
				model.setUsers(User.get(model.getUserId()).getNick());
				// 在消息模型中增加Topic,为管理员操作(发送系统信息做准备)2010-6-10 12:39
				model.setTopic(Topic.get(model.getTopicId()));
			} catch (ObjectNotFoundException e) {
			}
		}
		if (model.getSmid() > 0) {
			Message message;
			try {
				message = Message.get(model.getSmid());
				if (message.getFolder().getId() == 2
						&& model.getUser().isOwnMessage(message)) {
					model.setUsers(message.getReceivers());
					model.setTitle(message.getTitle());
					model.setContent(message.getContent());
				}
			} catch (ObjectNotFoundException e) {
			}
		}

		String action = model.getAction();
		if (action != null) {
			String msg = null;
			// 如果是删帖操作,则设置默认消息
			if (action.equals("delete")) {
				msg = "你好，你在论坛【" + model.getTopic().getForum().getName()
						+ "】 版块发布（回复）的帖子 〖" + model.getTopic().getTitle()
						+ "〗    因违反了论坛规定，现已删除，请仔细阅读论坛发帖规定";
			} else if (action.equals("lock")) {
				msg = "你好，你在论坛【" + model.getTopic().getForum().getName()
						+ "】 版块发布（回复）的帖子 〖" + model.getTopic().getTitle()
						+ "〗    因违反了论坛规定，现已锁定，请仔细阅读论坛发帖规定";
			}
			model.setContent(msg);
		}
	}

	public void sendMessageDo(SendMessageModel model) throws BBSException {
		User user = model.getUser();
		if ("1".equals(model.getDraft())) {
			// 保存到草稿箱
			if (model.getSmid() > 0) {
				try {
					Message message = Message.get(model.getSmid());
					if (user.isOwnMessage(message)) {
						message.setReceivers(model.getUsers());
						message.setTitle(model.getTitle());
						message.setContent(model.getContent());
						message.save();
						return;
					}
				} catch (ObjectNotFoundException e) {
				}
			}
			Message.saveDraftMessage(model.getTitle(), model.getContent(),
					model.getFace(), user, model.getUsers());
			return;
		}
		List<String> nicks = Util.splitNicks(model.getUsers());
		// 接收者
		List<User> receivers = new LinkedList<User>();
		// 错误昵称，用户不存在
		String notExistUsers = "";

		for (int i = 0; i < nicks.size(); i++) {
			String nick = (String) nicks.get(i);
			try {
				User receiver = User.getByNick(nick);
				receivers.add(receiver);
			} catch (ObjectNotFoundException e) {
				notExistUsers += nick + "，";
			}
		}

		if (notExistUsers.length() > 0) {
			throw new BBSException("以下用户昵称不存在：" + notExistUsers + "请重新填写！");
		}

		// 发送
		Message.sendMessage(model.getTitle(), model.getContent(), model
				.getFace(), receivers, user, model.getUsers());
		// 保存到已发送
		if ("1".equals(model.getTrack())) {
			Message.saveTrackMessage(model.getTitle(), model.getContent(),
					model.getFace(), user, model.getUsers());
			return;
		}
	}

	public void delMessage(DelMessageModel model) throws BBSException {
		User user = model.getUser();
		int[] smids = model.getSmid();

		for (int i = 0; i < smids.length; i++) {
			Message message;
			try {
				message = Message.get(smids[i]);
				if (user.isOwnMessage(message)) {
					message.setReaded(true);
					message.setDel(true);
					message.save();
				}
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

}
