package cn.newgxu.bbs.web.action.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.MessageService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.CreateSmallNewsAction;
import cn.newgxu.bbs.web.model.message.MessageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MessageAction extends AbstractBaseAction {

	private static final long serialVersionUID = 7969107121869497794L;

	private static final Log log = LogFactory
			.getLog(CreateSmallNewsAction.class);

	private MessageModel model = new MessageModel();

	private MessageService messageService;

	public String execute() throws Exception {
		signOnlineUser("查看短消息...");
		model.setUser(getUser());
		try {
			messageService.message(model);
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String list() throws Exception {
		signOnlineUser("查看短消息...");
		model.setUser(getUser());
		try {
			model.getPagination().setActionName(getActionName());
			messageService.messageList(model);
			return (model.getFolderId() == 2 || model.getFolderId() == 3) ? "list_2"
					: SUCCESS;
		} catch (BBSException e) {
			e.printStackTrace();
			MessageList m = new MessageList();
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
