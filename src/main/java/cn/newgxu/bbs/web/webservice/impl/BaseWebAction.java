package cn.newgxu.bbs.web.webservice.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;

public abstract class BaseWebAction  extends ActionSupport {
	private static final long serialVersionUID=21827121L;
	protected Logger log=Logger.getLogger(BaseWebAction.class);
	
	//子类action需要实现这个方法
	public abstract String execute();
	
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	public HttpSession getSession(){
		return this.getRequest().getSession();
	}
	
	public void request_add(String name,Object obj){
		this.getRequest().setAttribute(name, obj);
	}
	
	/**
	 * @方法名称 :session_add
	 * @功能描述 :添加session的属性
	 * @返回值类型 :boolean
	 *	@param name
	 *	@param obj
	 *	@return
	 *
	 * @创建日期 :2011-5-16
	 * @修改记录 :
	 */
	public boolean session_add(String name,Object obj){
		try{
			getSession().setAttribute(name, obj);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	public Object session_get(String name){
		return getSession().getAttribute(name);
	}
	
	/**
	 * 使用 Ajax 返回相应的值
	 * @param info
	 */
	public void ajaxReturn(Object info){
		try{
			this.getResponse().getWriter().print(info);
		}
		catch(Exception e){
			log.error("ajax返回值时出错 "+e.toString());
		}
	}
}
