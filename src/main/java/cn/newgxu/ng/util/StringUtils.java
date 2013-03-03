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

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some utility of string operations.
 * 
 * @author longkai
 * @since 2013-2-8
 * @version 1.0
 */
public class StringUtils {
	
	private static final Logger L = LoggerFactory.getLogger(StringUtils.class);
	
	/**
	 * wrap the given string with the given pattern.
	 * @param str what do you want to wrap?
	 * @param wrapTo wrap with what?
	 * @return the wrapped string.
	 */
	public static String wrap(String str, CharSequence wrapTo) {
		StringBuilder sb = new StringBuilder(wrapTo);
		return sb.append(str).append(wrapTo).toString();
	}
	
	/**
	 * provide a way to convert a string to a desired type
	 * (Primitive and their wrappers, String, Date(also include the three type in java.util.sql package).
	 * if the type doesn' t match, return null.
	 * @param type desired type.
	 * @param str string to be converted.
	 * @return the converted object, or, you know, null.
	 */
	public static Object parse(Class<?> type, String str) {
//		now, let' s jduge the type and converted!
		Object value = null;
		
//		number...
		if (real(str)) {
			value = parseNumber(Integer.class, str);
		} else if (RegexUtils.matches(str, ".")) {
			if (type.equals(char.class) || type.equals(Character.class)) {
				value = str.charAt(0);
			}
		}
		
//		if it' s been convert! go back!
		if (value != null) {
			return value;
		}
		
//		bool
//		(?i) ignore case
		if (RegexUtils.matches(str, "(?i)(true|false|1|0)")) {
			if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				if (str.length() == 1) {
					value = str.equals("1");
				} else {
					value = str.equalsIgnoreCase("true");
				}
			}
		} 
		
		if (value != null) {
			return value;
		}
		
		if (DateTime.parsable(str)) {
//			date
			value = parseDateTime(type, str);
		} else if (type.equals(String.class)) {
			L.warn("转换类型是字符串：{}", str);
//			if the type is String and can' t be converted to date and time, do nothing!
			value = str;
		}
		return value;
	}
	
	/**
	 * parse the string to the specific numeric type.
	 * warn: for there' re primitive type' s sake, if cannot convert well, the default value will be rendered.
	 * @param type
	 * @param str
	 * @return if success, the parsed object, or null will be returned.
	 */
	public static Object parseNumber(Class<?> type, String str) {
		Object value = null;
		if (type.equals(int.class) || type.equals(Integer.class)) {
			try {
				value = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				value = new Integer(0);
			}
		} else if (type.equals(long.class) || type.equals(Long.class)) {
			try {
				value = Long.parseLong(str);
			} catch (NumberFormatException e) {
				value = new Long(0L);
			}
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			try {
				value = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				value = new Double(0F);
			}
		} else if (type.equals(float.class) || type.equals(Float.class)) {
			try {
				value = Float.parseFloat(str);
			} catch (NumberFormatException e) {
				value = new Float(0F);
			}
		} else if (type.equals(short.class) || type.equals(Short.class)) {
			try {
				value = Short.parseShort(str);
			} catch (NumberFormatException e) {
				value = new Short((short) 0);
			}
		} else if (type.equals(byte.class) || type.equals(Byte.class)) {
			try {
				value = Byte.parseByte(str);
			} catch (NumberFormatException e) {
				value = new Byte((byte) 0);
			}
		}
		return value;
	}
	
	/**
	 * parse the four types of the date and time.
	 * @see Date
	 * @see java.sql.Date
	 * @see Time
	 * @see Timestamp
	 * @param type which date type do you want?
	 * @param str the given string
	 * @return if successes, the given type of the formated object, or null will be rendered.
	 */
	public static Object parseDateTime(Class<?> type, String str) {
		Object value = null;
		if (type.equals(Date.class)) {
			try {
				value =  DateTime.parse(str);
			} catch (ParseException e) {
				L.error("parse date error! request string: {}", str);
			}
		} else if (type.equals(Timestamp.class)) {
			value = Timestamp.valueOf(str);
		} else if (type.equals(java.sql.Date.class)) {
			value = java.sql.Date.valueOf(str);
		} else if (type.equals(Time.class)) {
			value = Time.valueOf(str);
		}
		return value;
	}
	
	/**
	 * get the <b>setter</b> method name of the given name.
	 * @param name
	 */
	public static String setter(String name) {
		L.debug("name: {}", name);
		return "set" + RegexUtils.fetchFirst(name, "^.").toUpperCase() 
				+ name.substring(1, name.length());
	}

	/**
	 * get the <b>getter</b> method name of the given name.
	 * @param name
	 */
	public static String getter(String name) {
		return "get" + RegexUtils.fetchFirst(name, "^.").toUpperCase() 
				+ name.substring(1, name.length());
	}
	
	/**
	 * does the string can be converted to a natural number? (-1, 0, 1 etc.)
	 * warn: does check the max/min value of the Long or Integer, (just check the bits)
	 * @param str
	 * @return true or false
	 */
	public static boolean natural(String str) {
		if (RegexUtils.matches(str, "(\\+|-)?\\d{1,19}")) {
			return true;
		}
		return false;
	}
	
	/**
	 * does the string can be converted to a natural number? (-1, 0, 1 etc.)
	 * warn: no checks of the range of real number.
	 * @param str
	 * @return true or false
	 */
	public static boolean real(String str) {
		if (natural(str)) {
//			natrual number also is real number.
			return true;
		}
		if (RegexUtils.matches(str, "(\\+|-)?(\\d+)?\\.\\d+")) {
			return true;
		}
		return false;
	}
	
}
