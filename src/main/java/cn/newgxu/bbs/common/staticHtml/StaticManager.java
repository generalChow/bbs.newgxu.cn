package cn.newgxu.bbs.common.staticHtml;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.HitsCounter;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.IndexAction;
import cn.newgxu.bbs.web.model.OnlineStatus;
import cn.newgxu.bbs.web.model.UserStatus;

/**
 * @path valhalla_hx----cn.newgxu.bbs.common.staticHtml.StaticManager.java 
 * 
 * @author 集成显卡
 * @since 4.0.0
 * @version $Revision 1.1$
 * @date 2011-6-4
 * @describe  静态化管理类
 * 	静态web页面时,需要 ServletContext的支持,那么从哪里可以获取这个 ServletContext呢.<br />
 * 在action中我们可以轻松获取 他，然后用他作为我们 的参数传进来。<br />
 * 可是  没有在action中获取呢，比如 在filter中，莫非也要让filter 集成 HttpServlet》？？<br />
 * 这个先不说行不行，光是这样想就觉得很别扭了<br />
 * 最后，选用的方案是， 在pojo中实现 Spring 的ServletContextAware 接口，然后由spring给我们自己加上ServletCOntext <br />
 * 要实现这个目的，我们的pojo必须是 Spring 里面的一个 bean，不然你的数据从哪里来？ <br />
 * 
 * 
 *
 */
public class StaticManager implements ServletContextAware{

	private ForumService forumService;
	private UserService userService;
	private ServletContext servletContext;
	
	/**
	 * 如果 forumService 为空，那么就从 spring 的context中获取 对应的bean
	 */
	public void init(){
		if(this.forumService==null)
			this.forumService=(ForumService)Util.getBean("forumService");
		if(this.userService==null)
			this.userService=(UserService)Util.getBean("userService");
	}
	
	/**
	 * 静态化首页
	 */
	public void staticIndex(ServletContext sc){
		this.init();
		UserStatus userStatus = new UserStatus();
		userStatus.setTotalHicount(HitsCounter.getTotalHitsCounter());
		userStatus.setTodayHicount(HitsCounter.getTodayHitsCounter());
		this.setUserStatus(userStatus);
		
		OnlineStatus onlineStatus = new OnlineStatus();
		//onlineStatus.setForumId(HttpUtil.getIntParameter(getRequest(),"forumId"));
		userService.onlineStatus(onlineStatus);
		
		String templateName="template/html/ftl/index.ftl";
		String saveName="template/html/index.htm";
		Map<String,Object> data=new HashMap<String ,Object>();

		data.put("areas", this.forumService.getAreas());
		data.put("topMoneyUsers", userService.getUsers(2, null) );
		data.put("topTopicUsers", userService.getUsers(3, null));
		data.put("topReplyUsers",  userService.getUsers(4, null));
		data.put("topGoodUsers", userService.getUsers(5, null) );
		data.put("pubGoodTopics",forumService.getTopics(2, 7) );
		data.put("pubHotTopics",forumService.getTopics(1, 7) );
		data.put("topExpUsers", userService.getUsers(1, null));
		data.put("userMessage", "${status.messageNotRead}");
		data.put("userModel", this.getUserHtml());
		data.put("status",userStatus);
		data.put("onlineStatus", onlineStatus);
		
		if(sc==null)
			sc=this.servletContext;
		StaticUtil.creatHtml(sc, templateName, saveName, data);
	}
	
	public void setUserStatus(UserStatus userStatus){
		AbstractBaseAction aba=(IndexAction)Util.getBean("index");
		aba.setStats(userStatus);
	}
	/**
	 * 获取首页上用户的模版代码，首页上的用户模块是没有静态化的，直接输入  freemarker 模版的东西
	 * @return
	 */
	private String getUserHtml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<div class=\"${topClass?default(\"header_night\")}\">");
		sb.append("<div class='header_user'>");
		sb.append("<span class='left'>嘿，欢迎您， <#if status.login>${status.nick}<#else>游客&nbsp;&nbsp;</#if></span>");
		sb.append("<span class='right'>");
		sb.append("<#if status.login>");
		sb.append("<a href='/user/upgrade.yws' >个人服务区</a>&nbsp;&nbsp;");
		sb.append("<a href='/getdraftboxes.yws' target='_blank'>草稿箱(${status.draftboxSize})</a>&nbsp;&nbsp;");
		sb.append("<a href='/diary/index.yws' target='_blank'>心情日记</a>&nbsp;&nbsp;");
		sb.append("<a href='/user/log.yws' target='_blank'>新手帮助</a>&nbsp;&nbsp;");
		sb.append("<a href='/accounts/logout.yws'>退出</a>&nbsp;&nbsp;");
		sb.append("<a href='#' onClick=\"messageList();\">新消息：${status.messageNotRead}条</a></#if></span></div>");
		sb.append("<#if status.login == false>");
		sb.append("<div class='header_login'>");
		sb.append(" <form action='/accounts/login_do.yws' method='post'>");
		sb.append("<div style='height:12px;'></div>");
		sb.append("<div style='height:25px; padding-left:55px;padding-right:25px;'><input name='username' type='text' class='input2' id='username' size='8' /></div>");
		sb.append("<div style='height:25px; padding-left:55px;'><input name='password' type='password' class='input2' id='password' size='8'/></div>");
		sb.append("<div style='height:25px; padding-left:55px;'><input onFocus=\"javascript:$('validImage').src='/validImage_servlet';\" name='validCode' type='text' class='input_rand' id='validCode' autocomplete='off' size='4' />&nbsp;<img src='/images/default_valid_img.png' align='top' id='validImage' /></div>");
		sb.append("<div style='height:31px; padding-left:40px;'>");
		sb.append("<input name=\"originalUrl\" type=\"hidden\" id=\"originalUrl\" value=\"${status.originalUrl?default('/')}\" />");
		sb.append("<input type=image src='/images/dl1.gif' name='Image1' width='37' height='16'  id='Image1' onclick=\"{target='_parent';}\" />");
		sb.append("<img src='/images/zc1.gif' name='Image2' width='37' height='16' border='0' id='Image2' onclick=\"window.location.href='/accounts/register.yws'\"/>");
		sb.append("</div></form></div></#if></div>");
		return sb.toString();
	}

	public void setServletContext(ServletContext arg0) {
		this.servletContext=arg0;
	}
}
