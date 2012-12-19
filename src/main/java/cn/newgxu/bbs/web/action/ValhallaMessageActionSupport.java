package cn.newgxu.bbs.web.action;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ValhallaMessageActionSupport extends ValhallaActionSupport {

	private static final long serialVersionUID = 8915725884935527123L;

	private MessageList messageList = new MessageList();

	public String execute() throws Exception {
		signOnlineUser("论坛跳转中...");
		
		messageList = Util.getMessageList(getSession());
		return SUCCESS;
	}

	public Object getModel() {
		System.out.println("modelaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		return messageList;
	}

}
