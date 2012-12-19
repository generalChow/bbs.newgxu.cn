package cn.newgxu.bbs.service;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.web.model.message.DelMessageModel;
import cn.newgxu.bbs.web.model.message.MessageModel;
import cn.newgxu.bbs.web.model.message.SendMessageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface MessageService {

	void sendMessage(SendMessageModel model) throws BBSException;

	void sendMessageDo(SendMessageModel model) throws BBSException,
			ValidationException;

	void message(MessageModel model) throws BBSException;

	void messageList(MessageModel model) throws BBSException;

	void delMessage(DelMessageModel model) throws BBSException;

}
