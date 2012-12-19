package cn.newgxu.bbs.web.action.accounts;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.util.SessionUtil;
import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.accounts.LoginModel;

import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class LoginAction extends AbstractBaseAction {

	private static final long serialVersionUID = -1718172310043924389L;

	private static final Log log = LogFactory.getLog(LoginAction.class);

	private LoginModel model = new LoginModel();

	private String originalUrl = null;

	public String execute() throws Exception {
		userService.updateLastWeekExp();
		signOnlineUser("正在登录中...");
		MessageList m = new MessageList();
		try {
			model.setRightCode(Util.getValidCode(getSession()));
			model.setIp(getRequest().getRemoteAddr());
			User user = super.userService.login(model);

			switch (user.getAccountStatus()) {
			case 1: // 可正常使用
				userService.deleteOnlineUser(getAuthorization());
				AuthorizationManager.saveAuthorization(getSession(), user);

				if (StringUtils.isEmpty(model.getOriginalUrl())) {
					setOriginalUrl((String) SessionUtil.getAttribute(super
							.getSession(), Constants.ORIGINAL_URL));
				} else {
					setOriginalUrl(model.getOriginalUrl());
				}

				Util.saveCookie(ServletActionContext.getResponse(), model
						.getUsername(), model.getPassword(), model
						.isAutoLogin());
				
				//如果是”到微博“，就转到微雨无声
				String t=getRequest().getParameter("weibo");
				if(t!=null&&t.equals("1"))
					this.originalUrl="/accounts/myTwitter.yws";
				
				return SUCCESS;
			case 2: // 禁止登录
				m.addMessage("抱歉，您的帐号已被禁止登录！");
				Util.putMessageList(m, getSession());
				log.debug("禁止登录！");
				return ERROR;
			case 3: // 注册资料待审
				m.addMessage("您的注册资料在等待审核中，请耐心等待！");
				Util.putMessageList(m, getSession());
				log.debug("注册资料待审！");
				return ERROR;
			case 4: // 注册请求未通过
				SessionUtil.setAttribute(getSession(), Constants.RE_INPUT_USER,
						user);
				m.addMessage("您的注册资料未能通过验证，请重新填写！");
				m.addMessage("<a href='/accounts/re_input.yws'>重新填写我的资料</a>");
				Util.putMessageList(m, getSession());
				log.debug("注册请求未通过！");
				return ERROR;
			default:
				m.addMessage(BBSExceptionMessage.PARAMETER_ERROR);
				Util.putMessageList(m, getSession());
				return ERROR;
			}
		} catch (BBSException e) {
			addValidateMsg(e.getMessage());
			return INPUT;
		}
	}

	public String homelogin() throws Exception {
		signOnlineUser("从主页登入...正在登录中...");
		MessageList m = new MessageList();
		try {
			model.setIp(getRequest().getRemoteAddr());
			checkTwitterLogin(model);
			User user = userService.loginWithoutValidCode(model);
			switch (user.getAccountStatus()) {
			case 1: // 可正常使用
				userService.deleteOnlineUser(getAuthorization());
				AuthorizationManager.saveAuthorization(getSession(), user);

				if (StringUtils.isEmpty(model.getOriginalUrl())) {
					setOriginalUrl((String) SessionUtil.getAttribute(super
							.getSession(), Constants.ORIGINAL_URL));
				} else {
					setOriginalUrl(model.getOriginalUrl());
				}

				Util.saveCookie(ServletActionContext.getResponse(), model
						.getUsername(), model.getPassword(), model
						.isAutoLogin());
				m.setUrl("/index.yws");
				m.addMessage("登入成功！");
				m.addMessage("<a href='/index.yws'>进入论坛逛逛</a>");
				Util.putMessageList(m, getSession());
				return SUCCESS;
			case 2: // 禁止登录
				m.addMessage("抱歉，您的帐号已被禁止登录！");
				m.addMessage("<a href='http://bbs.newgxu.cn'>进入论坛逛逛</a>");
				Util.putMessageList(m, getSession());
				log.debug("禁止登录！");
				return ERROR;
			case 3: // 注册资料待审
				m.addMessage("您的注册资料在等待审核中，请耐心等待！");
				m.addMessage("<a href='http://bbs.newgxu.cn'>进入论坛逛逛</a>");
				Util.putMessageList(m, getSession());
				log.debug("注册资料待审！");
				return ERROR;
			case 4: // 注册请求未通过
				SessionUtil.setAttribute(getSession(), Constants.RE_INPUT_USER,
						user);
				m.addMessage("您的注册资料未能通过验证，请重新填写！");
				m.addMessage("<a href='http://bbs.newgxu.cn'>进入论坛逛逛</a>");
				m
						.addMessage("<a href='http://bbs.newgxu.cn/accounts/re_input.yws'>重新填写我的资料</a>");
				Util.putMessageList(m, getSession());
				log.debug("注册请求未通过！");
				return ERROR;
			default:
				m.addMessage(BBSExceptionMessage.PARAMETER_ERROR);
				Util.putMessageList(m, getSession());
				return ERROR;
			}
		} catch (BBSException e) {
			addValidateMsg(e.getMessage());
			return INPUT;
		}
	}

	/**
	 * 检查是否是微博登录，这里，使用了LoginModel 中的 rightCode 字段作为标识。
	 * 因为微博登录时，发送过来的是md5 后的密码
	 * 
	 * @param model
	 */
	private void checkTwitterLogin(LoginModel model){
		String twitter=ServletActionContext.getRequest().getParameter("rightCode");
		if(twitter!=null&&twitter.equalsIgnoreCase("twitter")){
			model.setRightCode(twitter);
		}
		else{
			model.setRightCode(null);
		}
	}
	
	/**
	 * 转到我的微博
	 * @return
	 */
	public String toMyTwitter(){
		return SUCCESS;
	}
	
	/**
	 * 模拟web service 一下，<br />
	 * 其实就是一个ajax访问<br />
	 * 如果可以登录，返回相关信息<br />
	 * 
	 * by 集成显卡 2011 4 21
	 * 
	 * @return
	 */
	public String webService_login() {
		System.out.println(model.getUsername());
		try {
			model.setUsername(new String(model.getUsername().getBytes("ISO8859_1"), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		/*
		 * url中应该记录了username和password两个基本参数
		 * 论坛上面的登录需要validCode，那么这里就添加系统时间作为validCode
		 */
		try {
			if (model.getUsername() == null
					|| model.getUsername().length() == 0
					|| model.getPassword() == null
					|| model.getPassword().length() == 0) {
				ServletActionContext.getResponse().getWriter().print("error");
				return null;
			}
			// 设置相同的 validCode和 rightCode
			model.setRightCode(System.currentTimeMillis() + "");
			model.setValidCode(model.getRightCode());
			model.setIp(this.getRequest().getRemoteAddr());
			User user = super.userService.login(model);
			System.out.println(model.getUsername() + "  返回可以登录的信息"
					+ user.getUsername());
			// user.getAccountStatus 为1时可以登录
			if (user.getAccountStatus() == 1) {
				log.debug(model.getUsername() + "  返回可以登录的信息");
				ServletActionContext.getResponse().getWriter().print(
						"success!!success"
								+ TimerUtils.getDateByFormat(user
										.getRegisterTime(), "yyyy-MM-dd"));
				return null;
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Judge login and put the user's nick
	 * By Xjc 11-5-29
	 * @return
	 */
	public String webServiceLoginGetNick() {
		System.out.println(model.getUsername());
		System.out.println(model.getPassword());
		try {
			if (model.getUsername() == null
					|| model.getUsername().length() == 0
					|| model.getPassword() == null
					|| model.getPassword().length() == 0) {
				ServletActionContext.getResponse().getWriter().print("error");
				return null;
			}
			model.setRightCode(System.currentTimeMillis() + "");
			model.setValidCode(model.getRightCode());
			model.setIp(this.getRequest().getRemoteAddr());
			User user = super.userService.login(model);
			if (user.getAccountStatus() == 1) {
				log.debug(model.getUsername() + "  返回可以登录的信息");
				ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
				ServletActionContext.getResponse().getWriter().print(
						"success!!success"
								+ user.getNick());
				return null;
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String getOriginalUrl() {
		if (log.isDebugEnabled()) {
			log.debug("originalUrl = " + originalUrl);
		}
		return originalUrl;
	}

	public String getRedirect() {
		return getOriginalUrl();
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
		if (StringUtils.isEmpty(this.originalUrl)) {
			this.originalUrl = "/index.yws";
		}
	}

	public Object getModel() {
		return model;
	}

}
