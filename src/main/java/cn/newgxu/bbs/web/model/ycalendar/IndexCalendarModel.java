package cn.newgxu.bbs.web.model.ycalendar;

import java.util.List;

import cn.newgxu.bbs.domain.calendar.FixHoliday;
import cn.newgxu.bbs.domain.calendar.MonthUnit;
import cn.newgxu.bbs.domain.calendar.UserHoliday;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.ycalendar.ViewCalendarModel.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-18
 * @describe  
 *	这个是查看日历的model。
 *	由于没有分页效果，可以不继承PaginationBaseModel
 */
public class IndexCalendarModel extends CalendarModel{
	private MonthUnit monthUnit;

	private List<FixHoliday> comingHoliday=null;
	private List<UserHoliday> nearbyHoliday=null;
	
	public MonthUnit getMonthUnit() {
		return monthUnit;
	}
	public void setMonthUnit(MonthUnit monthUnit) {
		this.monthUnit = monthUnit;
	}
	public List<FixHoliday> getComingHoliday() {
		return comingHoliday;
	}
	public void setComingHoliday(List<FixHoliday> comingHoliday) {
		this.comingHoliday = comingHoliday;
	}
	public List<UserHoliday> getNearbyHoliday() {
		return nearbyHoliday;
	}
	public void setNearbyHoliday(List<UserHoliday> nearbyHoliday) {
		this.nearbyHoliday = nearbyHoliday;
	}
}