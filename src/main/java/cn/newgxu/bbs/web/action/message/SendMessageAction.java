package cn.newgxu.bbs.web.action.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.MessageService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.CreateSmallNewsAction;
import cn.newgxu.bbs.web.model.message.SendMessageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SendMessageAction extends AbstractBaseAction {

	private static final long serialVersionUID = -8538755273711109508L;

	private static final Log log = LogFactory
			.getLog(CreateSmallNewsAction.class);

	private SendMessageModel model = new SendMessageModel();

	private MessageService messageService;

	public String execute() throws Exception {
		signOnlineUser("发送短消息");
		model.setUser(getUser());
		try {
			messageService.sendMessage(model);
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String submit() throws Exception {
		signOnlineUser("发送短消息中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			messageService.sendMessageDo(model);
			m.setUrl("/message/message_list.yws");
			m.addMessage("<b>操作成功！</b>");
			m.addMessage("<a href='/message/message_list.yws'>到收件箱看看</a>");
			m.addMessage("<a href='/index.yws'>逛论坛去</a>");
			Util.putMessageList(m, getSession());
			log.debug("短消发送成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			log.debug(e);
			addValidateMsg(e.getMessage());
			return execute().equals(SUCCESS) ? INPUT : ERROR;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

}
