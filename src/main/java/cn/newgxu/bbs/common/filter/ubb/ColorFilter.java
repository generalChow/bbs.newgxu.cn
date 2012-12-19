package cn.newgxu.bbs.common.filter.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.Filter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ColorFilter extends AbstractBaseFilter {

	private static final String COLOR_PATTERN_STRING = "\\[color=(#?[a-z0-9]*)\\](.+?)\\[\\/color\\]";

	private static final String COLOR_APPEND_1 = "<font color=\"";

	private static final String COLOR_APPEND_2 = "\">";

	private static final String COLOR_APPEND_3 = "</font>";

	public ColorFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return colorConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String colorConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(COLOR_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();

		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(sb, "" + COLOR_APPEND_1
					+ matcher.group(1) + COLOR_APPEND_2 + matcher.group(2)
					+ COLOR_APPEND_3);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
