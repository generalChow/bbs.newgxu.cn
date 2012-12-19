package cn.newgxu.bbs.web.action.admin;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.AdministratorService;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.accounts.LoginAction;
import cn.newgxu.bbs.web.model.accounts.LoginModel;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ModelDriven;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AdminLoginDoAction extends AbstractBaseAction implements SessionAware,
		ModelDriven {

	private static final long serialVersionUID = 8530233371159027615L;

	private static final Log log = LogFactory.getLog(LoginAction.class);
	
	private Map<String, String> session;

	private LoginModel model = new LoginModel();

	private AdministratorService administratorService;
	
	private String originalUrl = null;
	

	public void setAdministratorService(
			AdministratorService administratorService) {
		this.administratorService = administratorService;
	}

	public String execute() throws Exception {
		//MessageList m = new MessageList();
		try {
			model.setRightCode(Util.getValidCode(getSession()));
			model.setIp(getRequest().getRemoteAddr());
			User user=administratorService.login(model);
			session.put("admin", "ok");
			
			userService.deleteOnlineUser(getAuthorization());
			AuthorizationManager.saveAdminAuthorization(getSession(), user);
			return SUCCESS;
		} catch (BBSException e) {
			model.setRightCode(e.getMessage());
			//e.printStackTrace();
			//m.addMessage(e.getMessage());
			//Util.putMessageList(m, getSession());
			log.debug(e);
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setSession(Map session) {
		this.session = session;
	}

	public Object getModel() {
		return this.model;
	}
	
	public void setUserService(UserService userService){
		super.userService=userService;
	}
	
	public String getOriginalUrl() {
		if (log.isDebugEnabled()) {
			log.debug("originalUrl = " + originalUrl);
		}
		return originalUrl;
	}
	
	public String getRedirect() {
		return getOriginalUrl();
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
		if (StringUtils.isEmpty(this.originalUrl)) {
			this.originalUrl = "/index.yws";
		}
	}

	

}
