package cn.newgxu.bbs.common;

import javax.servlet.http.HttpSession;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.util.SessionUtil;
import cn.newgxu.bbs.domain.user.Browser;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AuthorizationManager {

	public static boolean isLogin(Authorization auth) {
		if (auth == null) {
			return false;
		}
		return auth.isLogin();
	}

	public static void saveAuthorization(HttpSession session, Browser brower) {
		SessionUtil.setAttribute(session, Constants.USER_SESSION, brower
				.getAuthorization());
	}

	public static Authorization getAuthorization(HttpSession session) {
		return (Authorization) SessionUtil.getAttribute(session,
				Constants.USER_SESSION);
	}

	public static User getUser(Authorization auth) throws BBSException {
		if (auth.isLogin()) {
			return User.getCertainExist(auth.getId());
		} else {
			throw new BBSException(BBSExceptionMessage.NOT_LOGIN);
		}
	}
	
	public static void saveAdminAuthorization(HttpSession session, Browser brower) {
		SessionUtil.setAttribute(session, Constants.ADMIN_SESSION, brower
				.getAuthorization());
	}
	public static Authorization getAdminAuthorization(HttpSession session) {
		return (Authorization) SessionUtil.getAttribute(session,
				Constants.ADMIN_SESSION);
	}
}
