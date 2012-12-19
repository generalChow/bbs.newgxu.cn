package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.SelectUserInfoModel;

/**
 * 
 * @author tao
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SelectUserInfoAction extends AbstractBaseAction{

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private SelectUserInfoModel model = new SelectUserInfoModel();
	
	public String execute() {
		MessageList m = new MessageList();
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		try {
			userService.selectUserLoginInfo(model);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
