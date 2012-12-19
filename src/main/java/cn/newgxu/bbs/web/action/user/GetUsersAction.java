package cn.newgxu.bbs.web.action.user;

import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.GetUsersModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class GetUsersAction extends AbstractBaseAction {

	private static final long serialVersionUID = 8895141830126528310L;

	private GetUsersModel model = new GetUsersModel();

	public String execute() throws Exception {
		signOnlineUser("查看用户列表");
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		model.setUsers(userService.getUsers(model.getType(), model
				.getPagination()));
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
