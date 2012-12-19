package cn.newgxu.bbs.web.action.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.accounts.RegisterAction;
import cn.newgxu.bbs.web.model.user.EidtFaceModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditFaceAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2963413112287145520L;

	private static final Log log = LogFactory.getLog(RegisterAction.class);

	private EidtFaceModel model = new EidtFaceModel();

	public String execute() throws Exception {
		signOnlineUser("更换头像中...");
		model.setUser(getUser());
		model.setFace(model.getUser().getFace());
		model.getPagination().setRecordSize(10);
		model.getPagination().setPageSize(1);
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("更换头像中...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			userService.editFace(model);
			m.setUrl("/user/edit_face.yws");
			m.addMessage("<b>更新成功！</b>");
			m.addMessage("<a href='/index.yws'>返回论坛首页</a>");
			Util.putMessageList(m, getSession());
			log.debug("交易成功！");
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
