package cn.newgxu.bbs.service.proxy;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.ValidationUtil;
import cn.newgxu.bbs.service.MessageService;
import cn.newgxu.bbs.web.model.message.DelMessageModel;
import cn.newgxu.bbs.web.model.message.MessageModel;
import cn.newgxu.bbs.web.model.message.SendMessageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MessageServiceProxy implements MessageService {

	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void sendMessage(SendMessageModel model) throws BBSException {
		messageService.sendMessage(model);
	}

	public void sendMessageDo(SendMessageModel model) throws BBSException,
			ValidationException {
		ValidationUtil.sendMessage(model.getUsers(), model.getTitle(), model
				.getContent());
		messageService.sendMessageDo(model);
	}

	public void message(MessageModel model) throws BBSException {
		messageService.message(model);
	}

	public void messageList(MessageModel model) throws BBSException {
		messageService.messageList(model);
	}

	public void delMessage(DelMessageModel model) throws BBSException {
		messageService.delMessage(model);
	}

}
