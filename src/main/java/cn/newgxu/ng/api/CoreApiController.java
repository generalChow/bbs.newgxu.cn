/*
 * Copyright (c) 2001-2013 newgxu.cn <the original author or authors>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package cn.newgxu.ng.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.ObjectNotFoundException;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
import cn.newgxu.ng.core.mvc.annotation.MVCParamMapping;

/**
 * 
 * @author longkai
 * @email im.longkai@gmail.com
 * @since 2013-5-14
 * @version 0.1
 */
@MVCHandler(value = "api", namespace = "/ng/api/")
public class CoreApiController {
	
	private static final Logger L = LoggerFactory.getLogger(CoreApiController.class);
	
	public static final String JSON_OK = "{\"msg\":\"ok\"}";
	public static final String JSON_NO = "{\"msg\":\"no\"}";

	@MVCMapping("exists")
	public String exists(@MVCParamMapping("username") String username, @MVCParamMapping("pwd") String pwd) {
		L.debug("username: {}, pwd: {}", username, pwd);
		User u = null;
		try {
			u = User.getByUsername(username);
		} catch (ObjectNotFoundException e) {
			L.error("验证存在用户时出错！", e);
			u = null;
			return JSON_NO;
		}
		if (u == null) {
			return JSON_NO;
		}
		
		if (!u.getPassword().equals(Util.hash(pwd))) {
			return JSON_NO;
		} else {
			return JSON_OK;
		}
	}
	
	
}
