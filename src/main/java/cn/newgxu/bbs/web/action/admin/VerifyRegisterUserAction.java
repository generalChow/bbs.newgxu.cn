package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.UsersManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class VerifyRegisterUserAction extends AbstractBaseAction {

	private static final long serialVersionUID = -1047332308829561598L;

	private UsersManageModel model = new UsersManageModel();
	
	@Override
	public String execute() throws Exception {
		userService.getUser(model);
		return SUCCESS;
	}
	
	public String doVerifyRegisterUser() throws Exception {
		userService.verifyUser(model);
		response("{\"statusCode\":\"200\", \"message\":\"用户信息保存成功\", \"navTabId\":\"register_user\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"registerUsers.yws\"}");
		return null;
		//return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
