package cn.newgxu.bbs.web.model.ycalendar;

import cn.newgxu.bbs.domain.calendar.UserHoliday;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.ycalendar.EditCalendarModel.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-20
 * @describe  
 *
 */
public class EditCalendarModel extends CalendarModel{
	private UserHoliday holiday=null;
	private String method;
	
	public UserHoliday getHoliday() {
		return holiday;
	}
	public void setHoliday(UserHoliday holiday) {
		this.holiday = holiday;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}
