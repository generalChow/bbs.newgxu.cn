package cn.newgxu.bbs.common.filter.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.Filter;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class QuoteReluctantFilter extends AbstractBaseFilter {

	private static final String QUOTE_PATTERN_STRING_RELUCTANT = "(\\[quote\\])(.*?)(\\[\\/quote\\])";

	private static final String QUOTE_APPEND_1 = "<DIV class=\"quote\">";

	private static final String QUOTE_APPEND_2 = "</DIV>";

	public QuoteReluctantFilter(Filter filter) {
		super(filter);
	}

	private boolean haveQuote(String content) {
		Matcher matcher = Pattern.compile(QUOTE_PATTERN_STRING_RELUCTANT)
				.matcher(content);
		while (matcher.find()) {
			return true;
		}
		return false;
	}

	public String convert(String input) {
		try {
			return InnerQuoteCoverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String InnerQuoteCoverter(String input) throws Exception {
		if (haveQuote(input)) {
			Pattern pattern = Pattern
					.compile(QUOTE_PATTERN_STRING_RELUCTANT, 2);
			Matcher matcher = pattern.matcher(input);
			StringBuffer stringbuffer = new StringBuffer();

			for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
				matcher
						.appendReplacement(stringbuffer, QUOTE_APPEND_1
								+ InnerQuoteCoverter(matcher.group(2))
								+ QUOTE_APPEND_2);
			}

			matcher.appendTail(stringbuffer);
			return stringbuffer.toString();
		}
		return input;
	}

}
