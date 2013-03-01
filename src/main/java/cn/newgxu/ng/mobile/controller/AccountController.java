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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.accounts.LoginModel;
import cn.newgxu.ng.util.InfoLevel;
import cn.newgxu.ng.util.Pigeon;

/**
 * 
 * @author longkai
 * @since 2013-2-27
 * @version 1.0
 */
@Controller("mAccountController")
@Scope("prototype")
public class AccountController extends AbstractBaseAction {

	private static final long	serialVersionUID	= 1L;
	private static final Logger L = LoggerFactory.getLogger(AccountController.class);
	
	private LoginModel model = new LoginModel();
	private Pigeon msg;
	
	@Inject
	private UserService userService;

	public String loginPage() {
		return SUCCESS;
	}
	
	public String login() {
		L.info("有用户:{} 尝试从触控版登陆!", model.getUsername());
		msg = new Pigeon();
		try {
			model.setIp(getRequest().getRemoteAddr());
			L.debug((userService == null) + "");
			User user = userService.loginWithoutValidCode(model);
			switch (user.getAccountStatus()) {
			case ACCOUNT_STATUS_NORMAL:
				userService.deleteOnlineUser(getAuthorization());
				AuthorizationManager.saveAuthorization(getSession(), user);
				Util.saveCookie(getResponse(), user.getUsername(),
						user.getPassword(), true);
				return SUCCESS;
			case ACCOUNT_STATUS_WAIT:
				msg.setLevel(InfoLevel.WARN).setReason("您的注册资料在等待审核中，请耐心等待！");
				return INPUT;
			case ACCOUNT_STATUS_UNPAST:
				msg.setLevel(InfoLevel.ERROR).setReason("您的注册资料未能通过验证，请重新填写！")
					.setSolutions("<a href='/accounts/re_input.yws' data-role='button'>重新填写我的资料</a>");
				return INPUT;
			case ACCOUNT_STATUS_FORBID:
				msg.setLevel(InfoLevel.ERROR).setReason("抱歉，您的帐号已被禁止登录！");
				return INPUT;
			default:
				msg.setLevel(InfoLevel.ERROR).setReason("出错啦！").setSolutions("请稍后再试。");
				return ERROR;
			}
		} catch (BBSException e) {
			L.info("error");
			msg.setLevel(InfoLevel.ERROR).setReason(e.getMessage());
			return ERROR;
		}
	}

	@Override
	public Object getModel() {
		return this.model;
	}

	@Override
	public String execute() throws Exception {
		throw new UnsupportedOperationException("sorry, not supported!");
	}
	
	public Pigeon getMsg() {
		return msg;
	}

}
