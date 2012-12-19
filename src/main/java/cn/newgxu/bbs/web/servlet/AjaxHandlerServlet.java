package cn.newgxu.bbs.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.UserService;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@SuppressWarnings("serial")
public class AjaxHandlerServlet extends HttpServlet {

	private UserService userService = (UserService) Util.getBean("userService");

	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		path = path.substring(0, path.lastIndexOf("."));
		String handler = getHandlerMapping().get(path);
		if (handler == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"Unknown path:" + path);
			return;
		}

		try {
			response.setHeader("Cache-Control", "no-cache");
			Class<?>[] parameterTypes = new Class[] { HttpServletRequest.class,
					HttpServletResponse.class };
			Method method = this.getClass().getDeclaredMethod(handler,
					parameterTypes);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected Map<String, String> getHandlerMapping() {
		Map<String, String> handlers = new HashMap<String, String>();
		handlers.put("/accounts/validateNickName", "validateNickName");
		handlers.put("/accounts/validateLoginName", "validateLoginName");
		handlers.put("/request/get", "handleGetParameters");
		handlers.put("/request/post", "handlePostParameters");
		return handlers;
	}

	protected void validateLoginName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		String userName = request.getParameter("userName");

		boolean exist = false;
		try {
			exist = userService.isUserNameInUser(userName);
		} catch (Exception e) {
			e.printStackTrace();
			// throw new ServletException(e);
		}

		if (exist) {
			out.print("inuse");
		} else {
			out.print("good name");
		}

		out.close();
	}

	protected void validateNickName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		String nick = request.getParameter("nick");

		boolean exist = false;
		try {
			exist = userService.isNickNameInUser(nick);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("good name");
			out.close();
		}

		if (exist) {
			out.print("inuse");
		} else {
			out.print("good name");
		}
		out.close();
	}

	protected void handleGetParameters(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String greetings = "尊敬的" + name;
		if (gender.equals("m")) {
			greetings += "先生";
		} else {
			greetings += "女士";
		}
		out.print(greetings);
		out.print(",您好!");

		out.close();
	}

	protected void handlePostParameters(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();

		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String greetings = "尊敬的" + name;
		if (gender.equals("m")) {
			greetings += "先生";
		} else {
			greetings += "女士";
		}
		out.print(greetings);
		out.print(",您好!");

		out.close();
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}
}
