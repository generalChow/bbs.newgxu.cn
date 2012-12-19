package cn.newgxu.bbs.web.action.ycalendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.newgxu.bbs.domain.calendar.FixHoliday;
import cn.newgxu.bbs.domain.calendar.UserHoliday;
import cn.newgxu.bbs.service.YCalendarService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.ycalendar.IndexCalendarModel;

import com.opensymphony.webwork.ServletActionContext;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.action.ycalendar.Test.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-18
 * @describe  
 *
 */
public class YCalendarAction extends AbstractBaseAction {
	private static final long serialVersionUID=9345849758962L;
	
	private IndexCalendarModel model=new IndexCalendarModel();
	
	@Resource(name="yCalendarService")
	private YCalendarService yCalendarService;

	public String execute() throws Exception {
		//Holiday h=Holiday.getById(1);
		//System.out.println(h.getName());
		//Day d=new Day(2011,9,18);
		//System.out.println(d.getHolidayInfo());
		long start=System.currentTimeMillis();
		this.yCalendarService.getMonthUnit(model);
		model.setComingHoliday(yCalendarService.getComingFixHoliday());
		model.setNearbyHoliday(yCalendarService.getNearbyUserHoliday());
		System.out.println(System.currentTimeMillis()-start+"ms-----------------------------用户节日："+model.getNearbyHoliday().size());
		return SUCCESS;
	}

	public String getMonth() throws Exception {
		HttpServletResponse response=ServletActionContext.getResponse();
		try{
			this.yCalendarService.getMonthUnit(model);
			response.setCharacterEncoding("UTF-8");
			//response.setContentType("text/html");
			response.getWriter().print(model.getMonthUnit().getTableInfo());
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().print("加载日历出错.");
		}
		return null;
	}
	
	/**
	 * 获取这一天的节日信息<br />
	 * ajax获取<br />
	 * 
	 * @return
	 */
	public String getDay() {
		try{
			model.setComingHoliday(FixHoliday.getFixHoliday(model.getYear(), model.getMonth(), model.getDay()));
			model.setNearbyHoliday(UserHoliday.getUserHoliday(model.getYear(), model.getMonth(), model.getDay()));
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public Object getModel() {
		return model;
	}
}