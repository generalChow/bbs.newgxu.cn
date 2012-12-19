package cn.newgxu.bbs.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 管理非法字符串的工具类
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class IllegalWordUtil {
	public static String ILLEGAL_USERNAME = "管理|江泽民|胡锦涛|朱容基|无限网";

	public static String ILLEGAL_TITLE = "强奸|轮奸|法轮|李洪志|ln-chache|qld5159|pass168|icpcn";

	public static String ILLEGAL_WORD = "管理|江泽民|胡锦涛|朱容基|无限网|强奸|轮奸|法轮|李洪志|ln-chache|qld5159|pass168|icpcn";

	private static String getReplace(String input) {
		int count = input.length();
		StringBuffer sb = new StringBuffer(count);
		for (int i = 0; i < count; i++) {
			sb.append("*");
		}
		return sb.toString();
	}

	private static String WordConverter(String input, String type)
			throws Exception {
		Pattern pattern = null;
		if (type.equals("name")) {
			pattern = Pattern.compile(ILLEGAL_USERNAME, 2);
		} else if (type.equals("title")) {
			pattern = Pattern.compile(ILLEGAL_TITLE, 2);
		}
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag && matcher.group(0) != null; flag = matcher
				.find()) {
			matcher.appendReplacement(stringbuffer,
					getReplace(matcher.group(0)));
		}
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	public static String getFilterString(String input, String type) {
		try {
			return WordConverter(input, type);
		} catch (Exception e) {
			e.printStackTrace();
			return input;
		}
	}
}
