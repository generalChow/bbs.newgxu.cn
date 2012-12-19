package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.UsersManageModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class RegisterUsersLastManagerAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2481159807323331421L;

	private UsersManageModel model = new UsersManageModel();

	@Override
	public String execute() throws Exception {
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		userService.getRegisterUsersToday(model);
		return SUCCESS;
	}

	public String registerUserLast() throws BBSException {
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		userService.getLastRegisterUsers(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
