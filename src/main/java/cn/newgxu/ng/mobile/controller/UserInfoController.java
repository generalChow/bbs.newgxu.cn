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
package cn.newgxu.ng.mobile.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.model.user.GetUserInfoModel;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;

/**
 * 用户信息相关的控制器。
 * 
 * @author longkai
 * @since 2013-3-8
 * @version 1.0
 */
@MVCHandler(value = "mUserInfoController", namespace = "/ng/m/user_info/")
@Scope("prototype")
public class UserInfoController {

	private static final Logger L = LoggerFactory.getLogger(UserInfoController.class);
	
	@Inject
	private UserService userService;
	
	@MVCMapping("view")
	public String view(GetUserInfoModel model, ModelAndView mav) throws BBSException {
		if (model.getId() > 0) {
			model.setPageUser(userService.getUser(model.getId()));
		} else {
			model.setPageUser(userService.getUser(model.getNick()));
		}
		mav.addModel("u", model.getPageUser());
		return "mobile/user_info.jsp";
	}
	
}
