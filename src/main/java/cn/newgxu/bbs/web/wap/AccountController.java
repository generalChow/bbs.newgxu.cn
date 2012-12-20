package cn.newgxu.bbs.web.wap;

import static cn.newgxu.bbs.common.Constants.ACCOUNT_STATUS_FORBID;
import static cn.newgxu.bbs.common.Constants.ACCOUNT_STATUS_NORMAL;
import static cn.newgxu.bbs.common.Constants.ACCOUNT_STATUS_UNPAST;
import static cn.newgxu.bbs.common.Constants.ACCOUNT_STATUS_WAIT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.Guest;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.accounts.LoginModel;

import com.opensymphony.webwork.ServletActionContext;

@Controller
@Scope("prototype")
public class AccountController extends AbstractBaseAction {

	private static final long serialVersionUID = 8989419100072388374L;
	
	private static final Logger l = LoggerFactory.getLogger(AccountController.class);
	
	private LoginModel model = new LoginModel();
	private String message = "";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

//	public void setModel(LoginModel model) {
//		this.model = model;
//	}

	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String loginProcess() throws IllegalArgumentException, IllegalAccessException {
		l.debug("用户手机登录！ username: {}, password: {}", model.getUsername(), model.getPassword());
		try {
			model.setIp(getRequest().getRemoteAddr());
			User user = super.userService.loginWithoutValidCode(model);
			switch (user.getAccountStatus()) {
			case ACCOUNT_STATUS_NORMAL:
				userService.deleteOnlineUser(getAuthorization());
				AuthorizationManager.saveAuthorization(getSession(), user);
				Util.saveCookie(getResponse(), user.getUsername(),
						user.getPassword(), true);
				return SUCCESS;
			case ACCOUNT_STATUS_WAIT:
				message = "您的注册资料在等待审核中，请耐心等待！";
				return INPUT;
			case ACCOUNT_STATUS_UNPAST:
				message = "您的注册资料未能通过验证，请重新填写！"
						+ "<br /><a href='/accounts/re_input.yws'>重新填写我的资料</a>";
				return INPUT;
			case ACCOUNT_STATUS_FORBID:
				message = "抱歉，您的帐号已被禁止登录！";
				return INPUT;
			default:
				message = "出错啦！请稍后再试。";
				return ERROR;
			}
		} catch (BBSException e) {
			message = e.getMessage();
			return ERROR;
		}
	}

	public String logout() {
		userService.deleteOnlineUser(getAuthorization());
		AuthorizationManager.saveAuthorization(getSession(), new Guest());
		AuthorizationManager.saveAdminAuthorization(getSession(), new Guest());
		Util.saveCookie(ServletActionContext.getResponse(), "", "", false);
		return SUCCESS;
	}

}
