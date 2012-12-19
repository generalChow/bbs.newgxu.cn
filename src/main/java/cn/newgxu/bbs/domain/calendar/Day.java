package cn.newgxu.bbs.domain.calendar;

import java.io.Serializable;
import java.util.List;

/**
 * @项目名称 :meeting
 * @文件名称 :Day.java
 * @所在包 :cn.newgxu.bbs.domain.calendar
 * @功能描述 :日历中的天。可以说是最小的单位了。
 * 		一天可以是多个节日的集中，当然也可能无节日问津。
 * 		可以判断是过否是今天.
 *
 * @创建者 :集成显卡	1053214511@qq.com
 * @创建日期 :2011-9-15
 * @修改记录 :
 */
@SuppressWarnings("serial")
public class Day implements Serializable{
	private int year;
	private int month;
	private int day;//一个月中的第几天
	
	/*链表*/
	private List<FixHoliday> fixHolidays;
	private List<UserHoliday> userHolidays;
	
	/**
	 * @构造方法:默认是2011年1月1日
	 * @类名:Day.java
	 */
	public Day(){
		  this(2011,1,1);
	}
	public Day(int year,int month,int day){
		this.year=year;
		if(month<13&&month>0)
			this.month=month;
		if(day<323&&day>0)
			this.day=day;
	}
	
	/**
	 * 获取这天的完整字符串，使用的是YWSCalendar中的格式字符串
	 *	@return
	 *  @date :2011-9-15
	 */
	public String getFullDay(){
		return this.year+"-"+(this.month<10?"0"+this.month:this.month)+"-"+(this.day<10?"0"+this.day:this.day);
	}
	
	/**
	 * 这天是不是今天？？？？？
	 *	@return
	 *  @date :2011-9-15
	 */
	public boolean isToday(){
		return YWSCalendar.formatDate(null).equals(this.getFullDay());
	}
	/**
	 * 这个方法跟toDay是一样的，只是为了迎合freemarker而产生的
	 *	@return
	 *  @date :2011-9-15
	 */
	public boolean getToday(){
		return this.isToday();
	}
	
	/**
	 * 获取今天的节日信息
	 * @return
	 */
	public String getHolidayInfo(){
		StringBuffer sb=new StringBuffer("<div class='fix_day'>");
		for(FixHoliday h:this.getFixHolidays()){
			sb.append("<span style='color:"+h.getColor()+";'>"+h.getName()+"</span>&nbsp;&nbsp;");
		}
		sb.append("</div>");
		sb.append(this.getUserHolidays().size()>0?"<span class='user_day'>有&nbsp;"+this.getUserHolidays().size()+"&nbsp;个用户节日</span>":"");
		return sb.toString();
	}
	
	public List<FixHoliday> getFixHolidays() {
		if(fixHolidays==null){
			fixHolidays=FixHoliday.getFixHoliday(year,month, day);
		}
		return fixHolidays;
	}
	public void setFixHolidays(List<FixHoliday> fixHolidays) {
		this.fixHolidays = fixHolidays;
	}
	public List<UserHoliday> getUserHolidays() {
		if(userHolidays==null){
			userHolidays=UserHoliday.getUserHoliday(year, month, day);
		}
		return userHolidays;
	}
	public void setUserHolidays(List<UserHoliday> userHolidays) {
		this.userHolidays = userHolidays;
	}
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
}
