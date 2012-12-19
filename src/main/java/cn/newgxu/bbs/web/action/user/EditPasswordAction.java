package cn.newgxu.bbs.web.action.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.accounts.RegisterAction;
import cn.newgxu.bbs.web.model.user.EidtPasswordModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditPasswordAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2963413112287145520L;

	private static final Log log = LogFactory.getLog(RegisterAction.class);

	private EidtPasswordModel model = new EidtPasswordModel();

	public String execute() throws Exception {
		signOnlineUser("更改密码中...");
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("更改密码中...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			userService.editPassword(model);
			m.setUrl("/user/edit_password.yws");
			m.addMessage("<b>更改成功！</b>");
			m.addMessage("<a href='/index.yws'>返回论坛首页</a>");
			Util.putMessageList(m, getSession());
			log.debug("更改密码成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			addValidateMsg(e.getMessage());
			return INPUT;
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

}
