package cn.newgxu.bbs.web.activity;

import javax.servlet.http.HttpServletResponse;

import cn.newgxu.bbs.web.action.AbstractBaseAction;

import com.opensymphony.webwork.ServletActionContext;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.activity.Activity.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-31
 * @describe  
 *
 */
@SuppressWarnings("serial")
public abstract class ActivityAction extends AbstractBaseAction {
	public static final String TOPIC="topic";//发帖页面
	
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	public void response(String info) throws Exception{
		HttpServletResponse response=getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(info);
	}
}
