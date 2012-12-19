package cn.newgxu.bbs.web.action.accounts;

import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.Guest;
import cn.newgxu.bbs.web.action.AbstractBaseAction;

import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class LogoutAction extends AbstractBaseAction {

	private static final long serialVersionUID = 6367992091539143356L;

	@Override
	public String execute() throws Exception {
		userService.deleteOnlineUser(getAuthorization());

		AuthorizationManager.saveAuthorization(getSession(), new Guest());
		AuthorizationManager.saveAdminAuthorization(getSession(), new Guest());
		
		Util.saveCookie(ServletActionContext.getResponse(), "", "", false);

		return SUCCESS;
	}

	public Object getModel() {
		return null;
	}

}
