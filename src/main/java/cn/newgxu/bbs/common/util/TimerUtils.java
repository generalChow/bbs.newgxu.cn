package cn.newgxu.bbs.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author hjc
 * 
 *         处理与时间日期有关的东东。
 */
public class TimerUtils {

	public static int getHour() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMin() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MINUTE);
	}

	public static int getSec() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.SECOND);
	}

	public static int getDay() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getYear() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.YEAR);
	}

	public static Date getDate() throws ParseException {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.parse(sf.format(date));
	}

	public static Date getTomorrow() throws ParseException {
		Date date = getDate();
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return new Date(c.getTimeInMillis() + 24 * 3600 * 1000);
	}

	public static Date getYesterday() throws ParseException {
		Date date = getDate();
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return new Date(c.getTimeInMillis() - 24 * 3600 * 1000);
	}
	
	public static int getDayOfWeek() {
		Date date = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int mydate = cd.get(Calendar.DAY_OF_WEEK);
		return mydate;
	}
	/**
	 * 获取星期
	 * @param theDate
	 * @return
	 */
//	public static String getDayOfWeek() {
//		Date date = new Date();
//		Calendar cd = Calendar.getInstance();
//		cd.setTime(date);
//		int mydate = cd.get(Calendar.DAY_OF_WEEK);
//		String showDate = "";
//		switch (mydate) {
//		case 1:
//			showDate = "星期日";
//			break;
//		case 2:
//			showDate = "星期一";
//			break;
//		case 3:
//			showDate = "星期二";
//			break;
//		case 4:
//			showDate = "星期三";
//			break;
//		case 5:
//			showDate = "星期四";
//			break;
//		case 6:
//			showDate = "星期五";
//			break;
//		default:
//			showDate = "星期六";
//			break;
//		}
//		return showDate;
//	}

	public static Date getDate(String theDate,String dateFormat) {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = format.parse(theDate);
		} catch (Exception e) {
		}
		return date;
	}
	
	public static long getTime(String theDate,String dateFormat) {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = format.parse(theDate);
		} catch (Exception e) {
		}
		return date.getTime();
	}
	
	public static String getDateByFormat(Date date , String dateFormat){
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			return format.format(date);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String getNowYMD(String format){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = null;
		try {
			date = dateFormat.format(new Date());
		} catch (Exception e) {
		}
		return date;
	}
	
	/**
	 * 检查给定的日期字符串是否过期
	 * @param time 字符串必须是 yyyy-MM-dd HH:mm:ss#yyyy-MM-dd HH:mm:ss 此种形式，若果参数为空或者空串，那么是为永久有效
	 * @return 过期，true, 否则 false
	 * @author ivy
	 * @since 2012-03-13
	 */
	public static boolean isOverdue(String time) {
		// 是否永久有效
		if (time == null || time.equals("")) {
			return false;
		}
		
		String[] temp;
		try {
			temp = time.split("#");
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		Date now = new Date();
		Date start = Timestamp.valueOf(temp[0]);
		Date end = Timestamp.valueOf(temp[1]);
		return !(now.before(end) && now.after(start));
	}
	
	/**
	 * 判断闰年
	 * @param year
	 * @return
	 * @since 2012-12-18
	 */
	public static boolean isLeap(int year) {
		if (year % 4 == 0 && year % 100 != 0)
			return true;
		else if (year % 400 == 0)
			return true;
		return false;
	}
	
	/**
	 * 判断当前的时间是否在截至日期之前
	 * @param deadline 
	 * @return
	 * @since 2012-12-19
	 */
	public static boolean before(Calendar deadline) {
		return System.currentTimeMillis() - deadline.getTimeInMillis() < 0 ? true : false;
	}
	
	/**
	 * 判断当前的时间是否在某个日期之后
	 * @param initial
	 * @return
	 * @since 2012-12-19
	 */
	public static boolean after(Calendar initial) {
		return !before(initial);
	}
	
	/**
	 * 判断现在是否在某个时间区间中
	 * @param initial
	 * @param deadline
	 * @return
	 * @since 2012-12-19
	 */
	public static boolean between(Calendar initial, Calendar deadline) {
		return before(deadline) && after(initial);
	}
	
}
