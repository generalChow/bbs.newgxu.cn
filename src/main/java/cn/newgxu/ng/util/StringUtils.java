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
	 * @deprecated for some reason(complixity and safety), do not use this, try the enumerated one.
	 */
	@Deprecated
	public static Object parse(Class<?> type, String str) {
		L.debug("convert type: {}, value: {}", type, str);
		if (str == null) {
			
		}
//		now, let' s jduge the type and converted!
		Object value = null;
		
//		number...
		if (real(str)) {
			value = parseNumber(type, str);
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
		L.debug("return value: {}", value);
		return value;
	}
	
	/**
	 * convert the str to the desired type.
	 * @param clazz
	 * @param str
	 * @return success, the obj, or null.
	 */
	public static Object convert(Class<?> clazz, String str) {
		L.info("请求转换开始！type：{}，value：{}", clazz, str);
		Object obj = null;
		if (str != null) {
			if (clazz.equals(String.class)) {
				obj =  str;
			} else if (clazz.isPrimitive()) {
				obj = parsePremitive(clazz, str);
			} else if (WrapperUtils.isWrapper(clazz)) {
				obj = parseWrapper(clazz, str);
			} else {
				obj = parseDateTime(clazz, str);
			}
		} else if (clazz.isPrimitive()) {
			obj = parsePremitive(clazz, str);
		}
		L.info("请求转换结束！结果：{}", obj);
		return obj;
	}
	
	/**
	 * parse premitive, if not success, the default value will' be rendered.
	 * @param clazz
	 * @param str
	 */
	public static Object parsePremitive(Class<?> clazz, String str) {
		Object value = null;
		if (clazz.equals(int.class)) {
			try {
				value = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				value = 0;
			}
		} else if (clazz.equals(long.class)) {
			try {
				value = Long.parseLong(str);
			} catch (NumberFormatException e) {
				value = 0L;
			}
		} else if (clazz.equals(boolean.class)) {
			if (str.length() > 1) {
				value = str.equalsIgnoreCase("true");
			} else {
				value = str.equals("1");
			}
		} else if (clazz.equals(float.class)) {
			try {
				value = Float.parseFloat(str);
			} catch (NumberFormatException e) {
				value = 0F;
			}
		} else if (clazz.equals(double.class)) {
			try {
				value = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				value = 0F;
			}
		} else if (clazz.equals(char.class)) {
			try {
				value = str.charAt(0);
			} catch (Exception e) {
				value = '\0';
			}
		} else if (clazz.equals(short.class)) {
			try {
				value = Short.parseShort(str);
			} catch (NumberFormatException e) {
				value = 0;
			}
		} else if (clazz.equals(byte.class)) {
			try {
				value = Byte.parseByte(str);
			} catch (NumberFormatException e) {
				value = 0;
			}
		}
		return value;
	}
	
	/**
	 * 解析包装类，注意，解析不了返回null，不会像基本类型那样有默认值。
	 * @param clazz
	 * @param str
	 * @return 解析后的值，解析不了返回null
	 */
	private static Object parseWrapper(Class<?> clazz, String str) {
		Object value = null;
		if (clazz.equals(Integer.class)) {
			value = Integer.parseInt(str);
		} else if (clazz.equals(Long.class)) {
			value = Long.parseLong(str);
		} else if (clazz.equals(Boolean.class)) {
			if (str.length() > 1) {
				value = str.equalsIgnoreCase("true");
			} else {
				value = str.equals("1");
			}
		} else if (clazz.equals(Float.class)) {
			value = Float.parseFloat(str);
		} else if (clazz.equals(Double.class)) {
			value = Double.parseDouble(str);
		} else if (clazz.equals(Character.class)) {
			value = str.charAt(0);
		} else if (clazz.equals(Short.class)) {
			value = Short.parseShort(str);
		} else if (clazz.equals(Byte.class)) {
			value = Byte.parseByte(str);
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
				L.debug("hear");
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
