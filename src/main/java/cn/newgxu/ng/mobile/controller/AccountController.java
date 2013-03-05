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

import static cn.newgxu.bbs.common.Constants.ACCOUNT_STATUS_FORBID;
import static cn.newgxu.bbs.common.Constants.ACCOUNT_STATUS_NORMAL;
import static cn.newgxu.bbs.common.Constants.ACCOUNT_STATUS_UNPAST;
import static cn.newgxu.bbs.common.Constants.ACCOUNT_STATUS_WAIT;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.IndexModel;
import cn.newgxu.bbs.web.model.accounts.LoginModel;
import cn.newgxu.ng.core.mvc.Model;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.View;
import cn.newgxu.ng.core.mvc.annotation.MVCExceptionpHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
import cn.newgxu.ng.util.InfoLevel;
import cn.newgxu.ng.util.Pigeon;
import cn.newgxu.ng.util.ViewType;

/**
 * 
 * @author longkai
 * @since 2013-3-4
 * @version 1.0
 */
@MVCHandler(value = "mAccountController", namespace = "/ng/m/")
@Scope("prototype")
public class AccountController {
	
	private static final Logger L = LoggerFactory.getLogger(AccountController.class);
	
	@Inject
	private UserService userService;
	
	@MVCExceptionpHandler({BBSException.class})
	public ModelAndView exception(BBSException e, ModelAndView mav) {
		mav.addModel("msg", e.getMessage()).setViewName("mobile/index.jsp");
		return mav;
	}
	
	@MVCMapping("login")
	public String loginPage() {
		return "mobile/login.jsp";
	}
	
	@MVCMapping("do_login")
	public ModelAndView login(LoginModel model, ModelAndView mav, HttpServletRequest request, HttpServletResponse response) throws BBSException {
		L.info("用户：{} 试图从mobile登录论坛！", model.getUsername());
		model.setIp(request.getRemoteAddr());
		User user =  userService.loginWithoutValidCode(model);
		switch (user.getAccountStatus()) {
		case ACCOUNT_STATUS_NORMAL:
			userService.deleteOnlineUser(AuthorizationManager.getAuthorization(request.getSession()));
			AuthorizationManager.saveAuthorization(request.getSession(), user);
			Util.saveCookie(response, user.getUsername(), user.getPassword(), true);
//			交给首页处理！
			mav = this.index(mav);
			mav.addModel("u", user);
			break;
		case ACCOUNT_STATUS_WAIT:
			mav.setView(new View("mobile/login.jsp")).addModel("msg", "您的注册资料在等待审核中，请耐心等待！");
			break;
		case ACCOUNT_STATUS_UNPAST:
//			msg.setLevel(InfoLevel.ERROR).setReason("您的注册资料未能通过验证，请重新填写！")
//				.setSolutions("<a href='/accounts/re_input.yws' data-role='button'>重新填写我的资料</a>");
			mav.setView(new View("mobile/login.jsp")).addModel("msg", "的注册资料未能通过验证，请重新填写！");
			break;
		case ACCOUNT_STATUS_FORBID:
			mav.setView(new View()).addModel("msg", "抱歉，您的帐号已被禁止登录！");
			break;
		default:
			throw new BBSException("出错啦！请稍后再试！");
		}
		return mav;
	}
	
	@MVCMapping({"index", "home"})
	public ModelAndView index(ModelAndView mav) {
		IndexModel index = new IndexModel();
		index.setAreas(Area.getAreas());
		index.setLatestTopics(Topic.getLatesTopics(10));
		mav.addModel("index", index).setViewName("index.jsp");
		return mav;
	}

}
