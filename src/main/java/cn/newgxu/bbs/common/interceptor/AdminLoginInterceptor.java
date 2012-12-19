package cn.newgxu.bbs.common.interceptor;

import javax.servlet.http.HttpSession;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.util.HttpUtil;
import cn.newgxu.bbs.common.util.SessionUtil;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AdminLoginInterceptor implements Interceptor {

	private static final long serialVersionUID = -4444811987476404767L;

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Authorization auth = AuthorizationManager.getAdminAuthorization(session);

		if (!AuthorizationManager.isLogin(auth)) {
			SessionUtil.setAttribute(session, Constants.ORIGINAL_URL, HttpUtil
					.buildOriginalURL(ServletActionContext.getRequest()));
			return "adminLogin";
		}
		return invocation.invoke();
	}

	public void destroy() {

	}

	public void init() {

	}

}
