package cn.newgxu.bbs.web.action.ycalendar;

import javax.annotation.Resource;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.YCalendarService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.ycalendar.ListHolidayModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.action.ycalendar.ViewCalendarAction.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-25
 * @describe  
 * 查看节日，包括用户查看自己的节日列表，查看节日的详细信息
 */
@SuppressWarnings("serial")
public class ViewCalendarAction extends AbstractBaseAction{

	@Resource(name="yCalendarService")
	private YCalendarService yCalendarService;
	private ListHolidayModel model=new ListHolidayModel();
	
	/**
	 * 查看一个节日的具体信息
	 */
	public String execute() throws Exception {
		MessageList m=new MessageList();
		try{
			return yCalendarService.viewHoliday(model);
		}catch(Exception e){
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	/**
	 * 查看用户自己的节日信息<br />
	 * 是带操作的<br />
	 * @return
	 */
	public String myHoliday(){
		MessageList m=new MessageList();
		try{
			initPagination();
			yCalendarService.viewMyHoliday(model);
			return "uList";
		}catch(Exception e){
			e.printStackTrace();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	/**
	 * 这个是老版的Pagination 中一个不爽的地方，很次都要注明一下这两个属性
	 */
	private void initPagination(){
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
	}
	
	public Object getModel() {
		return model;
	}
}
