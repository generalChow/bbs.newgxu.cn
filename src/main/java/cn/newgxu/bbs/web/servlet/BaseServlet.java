package cn.newgxu.bbs.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.util.SessionUtil;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 7904700617134643537L;

	protected static Authorization getAuth(HttpServletRequest request) throws IOException {
		return (Authorization) SessionUtil.getAttribute(request.getSession(),
				Constants.USER_SESSION);
	}

}
