package cn.newgxu.bbs.web.action.admin;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;

import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * @author ddy
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@SuppressWarnings("serial")
public class AdminLogOutAction extends AbstractBaseAction {

	@Override
	public String execute() throws Exception {
		MessageList m = new MessageList();
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpSession session = super.getSession();
			session.removeAttribute(Constants.ADMIN_SESSION);
			session.removeAttribute(Constants.ORIGINAL_URL);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			Util.saveCookie(ServletActionContext.getResponse(), "", "", false);
			m.setUrl("/index.yws");
			m.addMessage("<b>注销成功！</b>");
			Util.putMessageList(m, getSession());
			return SUCCESS;
		} catch (Exception e) {
			m.setUrl("/index.yws");
			m.addMessage("<b>注销失败！</b>");
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public Object getModel() {
		return null;
	}

}
