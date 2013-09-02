/*
 * Copyright im.longkai@gmail.com
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式的一个工具类。
 * 
 * @author longkai
 * @since 2013-2-28
 * @version 1.0
 */
public class RegexUtils {
	
//	private static Pattern pattern;
//	private static Matcher matcher; 
	
	/**
	 * 初始化，稍微考虑到了一点效率。
	 * @param regex
	 */
//	private static void init(String regex, String text) {
//		if (pattern == null || !pattern.pattern().equals(regex)) {
//			pattern = Pattern.compile(regex);
//		}
//		matcher = pattern.matcher(text);
//	}
	
	/**
	 * 是否包含此串。
	 * @param text
	 * @param regex
	 * @return true or false.
	 */
	public static boolean contains(String text, String regex) {
//		init(regex, text);
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(text);
		return matcher.find();
	}
	
	/**
	 * does the text matches the pattern.
	 * @param text
	 * @param regex
	 * @return true or false.
	 */
	public static boolean matches(String text, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(text);
		return matcher.matches();
	}
	
	/**
	 * 抓取第一个匹配的串。
	 * @param text
	 * @param regex
	 * @return 捕捉到的第一个匹配串，否则返回null。
	 */
	public static String fetchFirst(String text, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(text);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
}
