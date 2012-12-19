package cn.newgxu.bbs.web.action.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.accounts.RegisterAction;
import cn.newgxu.bbs.web.model.user.EidtTitleModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditTitleAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2963413112287145520L;

	private static final Log log = LogFactory.getLog(RegisterAction.class);

	private EidtTitleModel model = new EidtTitleModel();

	public String execute() throws Exception {
		signOnlineUser("修改头衔中...");
		model.setUser(getUser());
		model.setTitle(model.getUser().getTitle());
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("修改头衔中...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			userService.editTitle(model);
			m.setUrl("/user/edit_title.yws");
			m.addMessage("<b>修改头衔成功！</b>");
			m.addMessage("<a href='/index.yws'>返回论坛首页</a>");
			Util.putMessageList(m, getSession());
			log.debug("修改头衔成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
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
