package cn.newgxu.bbs.web.model.ycalendar;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.ycalendar.CalendarModel.java 
 * 
 * @author 集成显卡
 * @since 4.0.0
 * @version $Revision 1.1$
 * @date 2011-9-20
 * @describe  
 *
 */
public class CalendarModel {
	private int year;
	private int month;
	private int day;
	
	private String message;//错误或者提示信息
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
