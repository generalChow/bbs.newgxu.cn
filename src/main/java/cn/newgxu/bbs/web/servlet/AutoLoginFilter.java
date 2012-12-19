package cn.newgxu.bbs.web.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.Guest;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.model.accounts.LoginModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AutoLoginFilter implements Filter {

	private static final Log log = LogFactory.getLog(AutoLoginFilter.class);

	protected FilterConfig filterConfig;

	UserService userService;

	public void destroy() {
		filterConfig = null;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession();

		Authorization auth = AuthorizationManager.getAuthorization(session);
		if (log.isDebugEnabled()) {
			log.debug("session auth=" + auth);
		}

		// 如果从session中无法取得，则从cookie中取
		if (auth == null) {
			Cookie cookie = Util.getCookie(request, Constants.AUTH_USER_COOKIE);
			Cookie autoLogin = Util.getCookie(request,
					Constants.AUTOLOGIN_COOKIE);
			if (log.isDebugEnabled()) {
				log.debug("cookie=" + cookie);
				log.debug("autoLogin=" + autoLogin);
			}
			if (cookie != null) {
				String[] username_password = Util
						.decodePasswordCookie(URLDecoder.decode(cookie
								.getValue(), "utf8"));
				LoginModel model = new LoginModel();
				model.setAutoLogin(Boolean.parseBoolean(autoLogin.getValue()));
				model.setUsername(username_password[0]);
				model.setPassword(username_password[1]);
				model.setIp(request.getRemoteAddr());
				try {
					AuthorizationManager.saveAuthorization(session, userService
							.loginWithoutValidCode(model));
				} catch (BBSException e) {
					guestLogin(session);
					log.warn(e);
				}
			} else {
				guestLogin(session);
			}
		}

		chain.doFilter(servletRequest, servletResponse);
	}

	private void guestLogin(HttpSession session) {
		AuthorizationManager.saveAuthorization(session, new Guest());
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		userService = (UserService) Util.getBean("userService");
	}
}
