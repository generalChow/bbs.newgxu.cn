/*
 *
 * Copyright (C) 2012 longkai im.longkai@gmail.com
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
 *
 */
package cn.newgxu.bbs.common.interceptor;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.service.UserService;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

/**
 * 这个拦截器是临时为了网站和移动谈赞助用的（它们想要知道用户访问论坛手机版的点击量）
 * @author longkai
 * @version 1.0
 * @since 2012-10-21
 */
public class HitsInterceptor implements Interceptor {

	private static final long	serialVersionUID	= 1L;
	
	private static final Logger l = LoggerFactory.getLogger(HitsInterceptor.class);
	
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		l.info("访问wap页面计数器+1");

		HttpSession session = ServletActionContext.getRequest().getSession();
		Authorization auth = AuthorizationManager.getAuthorization(session);

		boolean isLogin = AuthorizationManager.isLogin(auth); 
		
//		Hits.addHits(isLogin);
		userService.addHits(isLogin);
		
		return invocation.invoke();
	}

}
