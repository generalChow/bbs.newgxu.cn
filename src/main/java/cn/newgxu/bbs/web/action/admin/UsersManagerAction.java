package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.UsersManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class UsersManagerAction extends AbstractBaseAction {

	private static final long serialVersionUID = -1047332308829561598L;

	private UsersManageModel model = new UsersManageModel();
	
	@Override
	public String execute() throws Exception {
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		userService.getUsers(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
