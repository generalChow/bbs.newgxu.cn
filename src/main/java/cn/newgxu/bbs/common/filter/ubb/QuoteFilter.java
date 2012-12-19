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
public class QuoteFilter extends AbstractBaseFilter {

	private static final String QUOTE_PATTERN_STRING = "(\\[quote\\])(.*)(\\[\\/quote\\])";

	private static final String QUOTE_APPEND_1 = "<DIV class=\"quote\">";

	private static final String QUOTE_APPEND_2 = "</DIV>";

	public QuoteFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return QuoteConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String QuoteConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(QUOTE_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();

		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(stringbuffer, QUOTE_APPEND_1
					+ QuoteConverter(matcher.group(2)) + QUOTE_APPEND_2);
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
