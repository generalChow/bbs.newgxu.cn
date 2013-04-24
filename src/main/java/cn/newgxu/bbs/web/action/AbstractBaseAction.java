package cn.newgxu.bbs.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.staticHtml.StaticService;
import cn.newgxu.bbs.common.util.HttpUtil;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.DraftBox;
import cn.newgxu.bbs.domain.activity.Christmas;
import cn.newgxu.bbs.domain.activity.Doomsday;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.HolidayService;
import cn.newgxu.bbs.service.StatisticService;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.model.OnlineStatus;
import cn.newgxu.bbs.web.model.OnlineUserModel;
import cn.newgxu.bbs.web.model.UserStatus;
import cn.newgxu.jpamodel.ObjectNotFoundException;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.ModelDriven;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@SuppressWarnings("serial")
public abstract class AbstractBaseAction extends ActionSupport implements
		ModelDriven, UserStatsAware {

	private static final Log log = LogFactory.getLog(AbstractBaseAction.class);

	protected UserService userService;
	
	protected StatisticService statisticService;

	private List<String> validateMsg = new ArrayList<String>();

	private OnlineStatus onlineStatus;

	private UserStatus stats;
	
	public void setOnlineStatus(OnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public abstract String execute() throws Exception;

	/**
	 * 取得 session
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 取得 request
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获取response
	 * @return
	 */
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	/**
	 * 直接返回字符串，这个方法常用于 json/ajax 信息的返回。
	 * add 集成显卡
	 * 
	 * @param info
	 * @throws Exception
	 */
	public void response(String info) throws Exception{
		HttpServletResponse response=getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(info);
	}
	
	protected void addValidateMsg(String msg) {
		validateMsg.add(msg);
	}

	public Authorization getAuthorization() {
		// 因为有AutoLogin存在，所以返回不可能为null
		return AuthorizationManager.getAuthorization(getSession());
	}

	public UserStatus getStatus() {
		if (stats == null) {
			stats = new UserStatus();

			stats.setOriginalUrl(HttpUtil.buildOriginalURL(getRequest()));
			if (this.userService == null) {
				if (log.isFatalEnabled()) {
					log.fatal("userService 不能为空!");
				}
				throw new RuntimeException("userService 不能为空!");
			}

			Authorization auth = getAuthorization();

			if (AuthorizationManager.isLogin(auth)) {
				User user;
				try {
					user = AuthorizationManager.getUser(auth);
					stats.setUserId(user.getId());
					stats.setLogin(true);
					
					
					// add by ivy 2012论坛10周年改版
					
					stats.setExperience(user.getExp());
					stats.setPublishTopicsCount(user.getNumberOfTopic());
					stats.setCurrentPower(user.getCurrentPower());
					stats.setMaxPower(user.getMaxPower());
					stats.setUserGroup(user.getUserGroup().getGroupName());
					stats.setUserFace(user.getFaceSource());
					stats.setUsername(user.getUsername());
					
					// add end @2012-04-07
					
					
					// TODO 未读短信
					stats.setMessageNotRead(userService
							.getMessageSizeNotRead(user));
					stats.setNick(user.getColorNick());
					//
					Pagination p = new Pagination();
					stats.setDraftboxSize(DraftBox.getDraftBoxes(user, p)
							.size());
				} catch (BBSException e) {
					log.error(e);
				} catch (ObjectNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		return stats;
	}

	protected void signOnlineUser(String location) {
		Authorization auth = getAuthorization();
		if (auth == null) {
			log.error("authorization为空，请检查！");
			return;
		}

		OnlineUserModel model = new OnlineUserModel(auth.getId(), HttpUtil
				.getIntParameter(getRequest(), "forumId"), getRequest()
				.getHeader("User-Agent") == null/*
												 * 某些手机浏览器无法识别，为null插入数据库出错2010-
												 * 12-3
												 */? "" : getRequest()
				.getHeader("User-Agent"), getRequest().getRemoteAddr(),
				location, getSession().getId());
		userService.signOnlineUser(model);
	}

	public User getUser() throws BBSException {
		if (!AuthorizationManager.isLogin(getAuthorization())) {
			return null;
		}
		return AuthorizationManager.getUser(getAuthorization());
	}

	/**
	 * 只获取第一条信息
	 * @return
	 */
	public String getFirstMsg() {
		if (validateMsg.isEmpty())
			return "";
		return validateMsg.get(0);
	}
	public String getValidMsg() {
		return formatValidMsg();
	}

	public String getTopClass() {
		int hour = Util.getHour(new Date());
		return (hour > 6 && hour < 19) ? "header_day" : "header_night";
	}

	private String formatValidMsg() {
		if (validateMsg.isEmpty()) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("<div class=\"validError\">\r\n");
			sb.append("<b>您的输入未通过验证：</b>\r\n");
			for (String msg : validateMsg) {
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<li>").append(msg).append(
						"</li>\r\n");
			}
			sb.append("</div>\r\n");
			return sb.toString();
		}
	}

	public void setOnlineStatusForumId(int forumId) {
		getOnlineStatus().setForumId(forumId);
	}

	public OnlineStatus getOnlineStatus() {
		if (onlineStatus == null) {
			onlineStatus = new OnlineStatus();
			onlineStatus.setForumId(HttpUtil.getIntParameter(getRequest(),
					"forumId"));
			userService.onlineStatus(onlineStatus);
		}
		return onlineStatus;
	}

	public int getViewerId() {
		return getStatus().getUserId();
	}

	public String getViewerNick() {
		return getStatus().getNick();
	}

	public boolean getViewerIsLogin() {
		return getStatus().isLogin();
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getActionName() {
		return HttpUtil.getRequestURI(ServletActionContext.getRequest());
	}

	public void setStats(UserStatus stats) {
		this.stats = stats;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String[]> getParameterMap() {
		return (Map<String, String[]>) HttpUtil
				.getParameterMap(ServletActionContext.getRequest());
	}

	private static final String[] MOBILE_SPECIFIC_SUBSTRING = { "iPad", "iPhone",
		  "Android", "MIDP", "Opera Mobi", "Opera Mini", "BlackBerry",
		  "HP iPAQ", "IEMobile", "MSIEMobile", "Windows Phone", "HTC", "LG",
		  "MOT", "Nokia", "Symbian", "Fennec", "Maemo", "Tear", "Midori",
		  "armv", "Windows CE", "WindowsCE", "Smartphone", "240x320",
		  "176x220", "320x320", "160x160", "webOS", "Palm", "Sagem",
		  "Samsung", "SGH", "Siemens", "SonyEricsson", "MMP", "UCWEB" };
	/**
	 * 判断是否由手机登录的，如果使用的是手机登录，那么就会加载旧版的编辑器<br >
	 * add by：集成显卡
	 * @return
	 */
	public boolean getIsPhone(){
		
		boolean ifWap = false;
		String userAgent = ServletActionContext.getRequest().getHeader("user-agent");
		
		for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
			if (userAgent.contains(mobile) || userAgent.contains(mobile.toUpperCase()) || userAgent.contains(mobile.toLowerCase())) {
				ifWap = true;
			}
		}
		return ifWap;
	}
	
	/**
	 * 现在更新到全部的 action 都可以获取这个 字符串
	 * 2011-12-16
	 * @return
	 */
	public String getHoliday(){
		try{
			HolidayService holidayService=(HolidayService)Util.getBean("holidayService");
			String result=holidayService.getHoliday(getSession());
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return "出错了啦："+e.getMessage();
		}
	}
	
	protected void activity() {
		if (getRequest().getRequestURI().contains("forum.yws")) {
			setAttrbute();
			return;
		}
		
		String activity = getRequest().getParameter("activity");
		if (activity == null || activity.trim().equals("")) {
			return;
		}
		setAttrbute();
	}
	
	private void setAttrbute() {
		if (Doomsday.rightnow()) {
			getRequest().setAttribute("activity", Doomsday.getName());
		} else if (Christmas.rightnow()) {
			getRequest().setAttribute("activity", Christmas.getName());
		}
	}

	public StatisticService getStatisticService() {
		return statisticService;
	}

	public void setStatisticService(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

}
