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
public class EmailFilter extends AbstractBaseFilter {

	private static final String EMAIL_PATTERN_STRING = "\\[email=(.*)\\](.*)(\\[\\/email\\])"
			+ "|\\[email\\](.*)(\\[\\/email\\])";

	private static final String EMAIL_APPEND_1 = "<a href=\"mailto:";

	private static final String EMAIL_APPEND_2 = "\">";

	private static final String EMAIL_APPEND_3 = "</a>";

	public EmailFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return emailConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String emailConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag && matcher.group(0) != null; flag = matcher
				.find()) {
			if (matcher.group(1) != null) {
				matcher.appendReplacement(stringbuffer, EMAIL_APPEND_1
						+ matcher.group(1) + EMAIL_APPEND_2 + matcher.group(2)
						+ EMAIL_APPEND_3);
				continue;
			}
			if (matcher.group(4) != null) {
				matcher.appendReplacement(stringbuffer, EMAIL_APPEND_1
						+ matcher.group(4) + EMAIL_APPEND_2 + matcher.group(4)
						+ EMAIL_APPEND_3);
			}
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
