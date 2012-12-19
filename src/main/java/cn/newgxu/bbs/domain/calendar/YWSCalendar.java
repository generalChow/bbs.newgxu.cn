package cn.newgxu.bbs.domain.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @项目名称 :meeting
 * @文件名称 :YWSCalendar.java
 * @所在包 :cn.newgxu.bbs.domain.calendar
 * @功能描述 :
 *	雨无声日历<br />
 *	这个类中定义了一些常量<br />
 *
 * @创建者 :集成显卡	1053214511@qq.com
 * @创建日期 :2011-9-15
 * @修改记录 :
 */
public class YWSCalendar {

	public static final String NORMAL_DATE_FORMAT="yyyy-MM-dd";
	public static final String NORMAL_DATETIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	public static final int MONTH_STEP=1;//默认月份时间差
	
	/**获取今天的信息时的格式*/
	public static final int YEAR_MONTH_DAY=3;
	public static final int YEAR_MONTH=2;
	
	/**
	 * 格式化日期<br />
	 * 如果日期为NULL，则使用当前日期
	 *	@param date
	 *	@return
	 *  @date :2011-9-15
	 */
	public static String formatDate(Date date){
		SimpleDateFormat df=new SimpleDateFormat(YWSCalendar.NORMAL_DATE_FORMAT);
		if(date==null)
			return df.format(new Date());
		else
			return df.format(date);
	}
	
	/**
	 * 格式化日期及时间<br />
	 * 如果日期为NULL，则使用当前日期
	 *	@param date
	 *	@return
	 *  @date :2011-9-15
	 */
	public static String formatDateTime(Date date){
		SimpleDateFormat df=new SimpleDateFormat(YWSCalendar.NORMAL_DATETIME_FORMAT);
		if(date==null)
			return df.format(new Date());
		else
			return df.format(date);
	}
	
	/**
	 * 获取  2011-09-18样式的格式
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getDateInfo(int year,int month,int day){
		return (year>0?year+"-":"")+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day);
	}
	
	/**
	 * 重新设置这个日期信息，参数必须是正确的格式: yyyy-MM-dd
	 * vkjs vkjs如果出错了，直接返回原信息
	 * @param date
	 * @return
	 */
	public static String resetDateInfo(String date){
		try{
			String item[]=date.split("-");
			return getDateInfo(Integer.valueOf(item[0]), Integer.valueOf(item[1]), Integer.valueOf(item[2]));
		}catch(Exception e){
			e.printStackTrace();
			return date;
		}
	}
	
	/**
	 * 获取今天对应的 年，月，日 数组
	 * @param type  根据这个参数会返回不同长度的数组
	 * @return
	 */
	public static int[] getToday(int type){
		return getDay(0,0,0,type);
	}
	
	/**
	 * 获取某一天对应的 年，月，日 数组<br />
	 * 这个某一天是由今天开始，根据参数的提升量而得到的
	 * 
	 * @param yearUp
	 * @param monthUp
	 * @param dayUp
	 * @return
	 */
	public static int[] getDay(int yearUp,int monthUp,int dayUp,int type){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.YEAR, yearUp);
		c.add(Calendar.MONTH, monthUp);
		c.add(Calendar.DAY_OF_MONTH, dayUp);
		int day[]=new int[type];
		day[0]=c.get(Calendar.YEAR);
		day[1]=c.get(Calendar.MONTH)+1;
		if(type==YEAR_MONTH_DAY)
			day[2]=c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
}
