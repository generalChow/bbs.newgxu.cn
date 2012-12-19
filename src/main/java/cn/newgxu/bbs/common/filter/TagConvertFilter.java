package cn.newgxu.bbs.common.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class TagConvertFilter  {
	
	private static final String TAG_PATTERN_STRING = "(\\$)|(\\{)|(\\})";

	private static final String RE_TAG_PATTERN_STRING = "(####36####)|(####123####)|(####125####)";

	private static final String REGULAR_STOP_1 = "\\$";

	private static final String REGULAR_STOP_2 = "\\{";

	private static final String REGULAR_STOP_3 = "\\}";

	private static final String RE_REGULAR_STOP_1 = "####36####";

	private static final String RE_REGULAR_STOP_2 = "####123####";

	private static final String RE_REGULAR_STOP_3 = "####125####";

	public static String before(String input) {
//		log.debug("before...");
		try {
			return tagConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	public static String after(String input) {
//		log.debug("after...");
		try {
			return ReTagConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private static String tagConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(TAG_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			if (matcher.group(1) != null) {
				matcher.appendReplacement(stringbuffer, RE_REGULAR_STOP_1);
			}

			else if (matcher.group(2) != null) {
				matcher.appendReplacement(stringbuffer, RE_REGULAR_STOP_2);
			}

			else if (matcher.group(3) != null) {
				matcher.appendReplacement(stringbuffer, RE_REGULAR_STOP_3);
			}
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	private static String ReTagConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(RE_TAG_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();

		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			if (matcher.group(1) != null) {
				matcher.appendReplacement(stringbuffer, REGULAR_STOP_1);
			}

			else if (matcher.group(2) != null) {
				matcher.appendReplacement(stringbuffer, REGULAR_STOP_2);
			}

			else if (matcher.group(3) != null) {
				matcher.appendReplacement(stringbuffer, REGULAR_STOP_3);
			}
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
