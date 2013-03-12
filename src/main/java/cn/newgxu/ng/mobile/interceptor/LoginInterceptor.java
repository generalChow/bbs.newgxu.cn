/*
 * Copyright im.longkai@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.newgxu.ng.mobile.interceptor;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.ng.core.mvc.Interceptor;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.View;
import cn.newgxu.ng.core.mvc.annotation.MVCInterceptor;
import cn.newgxu.ng.util.ViewType;

/**
 * 适用于移动版的拦截器。
 * 
 * @author longkai
 * @since 2013-3-8
 * @version 1.0
 */
@MVCInterceptor
public class LoginInterceptor implements Interceptor {

	private static final Logger L = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean before(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mav) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("_user");
		L.info("拦截器拦截用户是否登陆！用户：{}", u);
		if (session != null && u != null) {
			return true;
		}
//		Enumeration<String> e = request.getHeaderNames();
//		while (e.hasMoreElements()) {
//			System.out.println(e.nextElement());
//		}
//		System.out.println(request.getHeader("Accept"));
		if (request.getHeader("Accept").contains("json") || request.getHeader("Accept").contains("java")) {
			mav.setView(new View().setType(ViewType.JSON).setContent("{\"info\":\"#\"}".replace("#", "请您登陆后再操作！")));
		} else {
			mav.setView(new View().setViewName("mobile/login.jsp")).addModel("msg", "请您登陆后再操作！");
		}
		return false;
	}

	@Override
	public boolean after(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mav)
			throws ServletException, IOException {
		return true;
	}

}
