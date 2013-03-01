/*
 * Copyright im.longkaii@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.newgxu.ng.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A common utility deals with date and time.
 * 
 * @author longkai
 * @since 2013-01-13
 * @version 0.1
 */
public class DateTime {

	/** the ISO datetime pattern: yyyy-MM-dd HH:mm:ss */
	public static final String			ISO_DATE_TIME_PATTERN	= "yyyy-MM-dd HH:mm:ss";

	/** the ISO date pattern: yyyy-MM-dd */
	public static final String			ISO_DATE_PATTERN		= "yyyy-MM-dd";

	/** the ISO time pattern: HH:mm:ss */
	public static final String			ISO_TIME_PATTERN		= "HH:mm:ss";
	
	/** the regex of the ISO date pattern */
	public static final String 			ISO_TIME_REGEX 			= "(\\d{2}|\\d):(\\d{2}|\\d):(\\d{2}|\\d)";

	/** the regex of the ISO time pattern */
	public static final String 			ISO_DATE_REGEX 			= "\\d{4}-(\\d{2}|\\d)-(\\d{2}|\\d)";
	
	/** the regex of the ISO datetime pattern */
	public static final String			ISO_DATE_TIME_REGEX		= ISO_DATE_REGEX + " " + ISO_TIME_REGEX;

	/** a thread local object that bind a simple data format */
	private static ThreadSafeDateFormat threadLocal;
	
	/** a thead-safe date format */
	private static SimpleDateFormat	dateFormat;

	/**
	 * set up the date format if need format method.
	 */
	private static void setup() {
		if (threadLocal == null) {
			threadLocal = new ThreadSafeDateFormat();
		}
		dateFormat = threadLocal.get();
	}

	/**
	 * format the given date to the ISO data-time pattern (yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		if (dateFormat == null) {
			setup();
		}
		return dateFormat.format(date);
	}

	/**
	 * parse the given date string as a Date object.
	 * @param date
	 * @return Date object.
	 * @see java.util.Date
	 * @throws ParseException
	 */
	public static Date parse(String date) throws ParseException {
		if (dateFormat == null) {
			setup();
		}
		if (RegexUtils.matches(date, ISO_TIME_REGEX)) {
//			if it only has time, it' ll append the current date.
			Date now = new Date();
			date = format(now, ISO_DATE_PATTERN) + " " + date;
		} else if (RegexUtils.matches(date, ISO_DATE_REGEX)) {
//			if it only has date, it' ll append the 00:00:00 time. 
			date += " 00:00:00";
		}
		return dateFormat.parse(date);
	}
	
	/**
	 * Does the string can be parsable to a type of string?
	 * @param str
	 * @return parsable?
	 */
	public static boolean parsable(String str) {
		if (RegexUtils.contains(str, ISO_DATE_TIME_REGEX)) {
			return true;
		} else if (RegexUtils.contains(str, ISO_DATE_REGEX)) {
			return true;
		} else if (RegexUtils.contains(str, ISO_TIME_REGEX)) {
			return true;
		}
		return false;
	}

	/**
	 * format the given date to the given pattern
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (dateFormat == null) {
			setup();
		}
		dateFormat.applyPattern(pattern);
		try {
			return dateFormat.format(date);
		} finally {
//			we need to remain the default pattern
			dateFormat.applyPattern(ISO_DATE_TIME_PATTERN);
		}
	}

	/**
	 * judge the current system time whether is before or not.
	 * 
	 * @param calendar the given calendar
	 * @return if before, true, or false
	 */
	public static boolean before(Calendar calendar) {
		return System.currentTimeMillis() < calendar.getTimeInMillis();
	}

	/**
	 * judge the current system time whether is after or not.
	 * 
	 * @param calendar the given calendar
	 * @return if before, true, or false
	 */
	public static boolean after(Calendar calendar) {
		return !before(calendar);
	}

	/**
	 * judge the current system time whether is before or not.
	 * 
	 * @param date the given date
	 * @return if before, true, or false
	 */
	public static boolean before(Date date) {
		return System.currentTimeMillis() < date.getTime();
	}

	/**
	 * judge the current system time whether is after or not.
	 * 
	 * @param date the given date
	 * @return if before, true, or false
	 */
	public static boolean after(Date date) {
		return !before(date);
	}

	/**
	 * judge the current system's time is between the beginline and deadline.
	 * 
	 * @param beginline
	 * @param deadline
	 * @return if in true, or false
	 */
	public static boolean between(Calendar beginline, Calendar deadline) {
		return before(deadline) && after(beginline);
	}

	/**
	 * judge the current system's time is between the beginline and deadline.
	 * 
	 * @param beginline
	 * @param deadline
	 * @return if in true, or false
	 */
	public static boolean between(Date beginline, Date deadline) {
		return before(deadline) && after(beginline);
	}

	/**
	 * obtain the millis time gap between current system's time, always return positive!
	 * @param date the given date
	 * @return the positive millis gap
	 */
	public static long millisGap(Date date) {
		long now = System.currentTimeMillis();
		long gap = now - date.getTime();
		return gap > 0 ? gap : -gap;
	}

	/**
	 * A thread-safe datatime format.
	 * 
	 * @author longkai
	 * @since 2013-01-13
	 * @version 1.0
	 */
	private static class ThreadSafeDateFormat extends
			ThreadLocal<SimpleDateFormat> {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(ISO_DATE_TIME_PATTERN);
		}

	}

}