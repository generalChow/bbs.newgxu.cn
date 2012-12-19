package cn.newgxu.bbs.domain.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @项目名称 :meeting
 * @文件名称 :MonthUnit.java
 * @所在包 :cn.newgxu.bbs.domain.calendar
 * @功能描述 :
 *	年月日中的月基本单位。具有dayList	
 *
 * @创建者 :集成显卡	1053214511@qq.com
 * @创建日期 :2011-9-15
 * @修改记录 :
 */
@SuppressWarnings("serial")
public class MonthUnit implements Serializable {
	private int year;
	private int month;
	private int space_before;
	private int space_after;
	
	private List<Day> dayList=null;

	/**
	 * @构造方法:默认是1月
	 * @类名:MonthUnit.java
	 */
	public MonthUnit(){
		this(2011,1);
	}
	public MonthUnit(int year,int month){
		this.year=year;
		if(month<13&&month>0)
			this.month=month;
		initDays();
	}
	
	/**
	 * 实例这个月的天表<br />
	 * 先计数出一共有多少天.这里是以35为全部，减去1号前的空白天数，还有月尾后的空白天数。<br />
	 * 使用Calendar.get(Calendar.DAY_OF_WEEK);得到指定号的空白时间。<br />
	 *	
	 *  @date :2011-9-15
	 */
	private void initDays(){
		Calendar c=Calendar.getInstance();
		c.set(this.year,this.month-1, 1);
		space_before=c.get(Calendar.DAY_OF_WEEK)-1;
		int start=c.get(Calendar.DAY_OF_YEAR);
		c.roll(Calendar.MONTH, true);//月份+1
		int end=c.get(Calendar.DAY_OF_YEAR);
		space_after=8-c.get(Calendar.DAY_OF_WEEK);
		//当end为1时，就说明是另外一年了，就要回加上年的天数
		if(end==1){
			//如果刚好又是星期六，要处理一下
			if(space_after==1)
				space_after=0;
			c.add(Calendar.DAY_OF_MONTH, -1);
			end=c.get(Calendar.DAY_OF_YEAR)+1;
		}
		//得到天数
		int size=end-start;
		dayList=new ArrayList<Day>();
		for(int i=0;i<size;i++){
			dayList.add(new Day(this.year,this.month,i+1));
		}
	}
	
	/**
	 * 获取这个月的信息
	 *	@return
	 *  @date :2011-9-15
	 */
	public String getMothInfo(){
		return this.year+"年"+this.month+"月";
	}
	/**
	 * 获取天数
	 *	@return
	 *  @date :2011-9-15
	 */
	public int getDayCount(){
		return this.dayList.size();
	}
	
	/**
	 * 获取这个月份的以Table形式显示的html代码
	 *	@return
	 *  @date :2011-9-15
	 */
	public String getTableInfo(){
		StringBuffer sb=new StringBuffer("<tr>");
		if(this.space_before>0)
			sb.append("<td colspan='"+this.space_before+"' class='space'>&nbsp;</td>");
		for(int i=0;i<this.dayList.size();i++){
			String dd=this.dayList.get(i).isToday()?" class='current_day'":"";
			if(i>0&&(i+space_before)%7==0){
				sb.append("</tr><tr><td"+dd+" day='"+this.dayList.get(i).getDay()+"'>"+this.dayList.get(i).getDay()+"<br />"+this.dayList.get(i).getHolidayInfo()+"</td>");
			}else{
				sb.append("<td"+dd+" day='"+this.dayList.get(i).getDay()+"'>"+this.dayList.get(i).getDay()+"<br />"+this.dayList.get(i).getHolidayInfo()+"</td>");
			}
		}
		if(this.space_after==0)
			sb.append("</tr>");
		else
			sb.append("<td colspan='"+this.space_after+"' class='space'>&nbsp;</td></tr>");
		return sb.toString();
	}
	
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSpace_before() {
		return space_before;
	}
	public void setSpace_before(int spaceBefore) {
		space_before = spaceBefore;
	}
	public int getSpace_after() {
		return space_after;
	}
	public void setSpace_after(int spaceAfter) {
		space_after = spaceAfter;
	}
	public List<Day> getDayList() {
		return dayList;
	}
	public void setDayList(List<Day> dayList) {
		this.dayList = dayList;
	}
}
