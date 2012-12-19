package cn.newgxu.bbs.web.action.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.MessageService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.CreateSmallNewsAction;
import cn.newgxu.bbs.web.model.message.DelMessageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DelMessageAction extends AbstractBaseAction {

	private static final long serialVersionUID = 7969107121869497794L;

	private static final Log log = LogFactory
			.getLog(CreateSmallNewsAction.class);

	private DelMessageModel model = new DelMessageModel();

	private MessageService messageService;

	public String execute() throws Exception {
		signOnlineUser("删除短消息...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			messageService.delMessage(model);
			m.setUrl("message/message_list.yws?folderId=1");
			m.addMessage("<b>删除成功！</b>");
			log.debug("删除短消息成功！");
			Util.putMessageList(m, getSession());
			return SUCCESS;
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
