package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.util.HttpUtil;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.model.user.GetUsersModel;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.ModelDriven;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SearchUserAction extends ActionSupport implements ModelDriven {

	private static final long serialVersionUID = -1047332308829561598L;

	private GetUsersModel model = new GetUsersModel();

	private UserService userService;

	public String execute() {
		try {
			model.getPagination().setActionName(HttpUtil.getRequestURI(ServletActionContext.getRequest()));
			userService.searchUsers(model);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
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
