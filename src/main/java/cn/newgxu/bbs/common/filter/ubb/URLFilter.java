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
public class URLFilter extends AbstractBaseFilter {

	private static final String URL_PATTERN_STRING = "(\\[(url)((=(((http://)|(ftp://))?)([^\\[]*))?)\\])"
			+ "((((http://)|(ftp://))?)([^\\[]*))(\\[(\\/url)\\])";

	private static final String URL_APPEND_1 = "<a target=\"_blank\" href=\"";

	private static final String URL_APPEND_2 = "";

	private static final String URL_APPEND_3 = "\">";

	private static final String URL_APPEND_4 = "</a>";

	public URLFilter(Filter filter) {
		super(filter);
	}

	@Override
	protected String convert(String str) {
		Pattern pattern = Pattern.compile(URL_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(str);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(stringbuffer, URL_APPEND_1);
			if (!matcher.group(3).equals("")) {
				if (matcher.group(5).equals("")) {
					if (matcher.group(10) == null) {
						stringbuffer.append(URL_APPEND_2 + matcher.group(9)
								+ URL_APPEND_3 + matcher.group(9)
								+ URL_APPEND_4);
					} else {
						stringbuffer.append(URL_APPEND_2 + matcher.group(9)
								+ URL_APPEND_3 + matcher.group(10)
								+ URL_APPEND_4);
					}
					continue;
				}
				if (matcher.group(10) == null) {
					stringbuffer.append(matcher.group(5) + matcher.group(9)
							+ URL_APPEND_3 + matcher.group(5)
							+ matcher.group(9) + URL_APPEND_4);
				} else {
					stringbuffer.append(matcher.group(5) + matcher.group(9)
							+ URL_APPEND_3 + matcher.group(10) + URL_APPEND_4);
				}
				continue;
			}
			if ("".equals(matcher.group(11))) {
				stringbuffer.append(URL_APPEND_2 + matcher.group(10)
						+ URL_APPEND_3 + matcher.group(10) + URL_APPEND_4);
			} else {
				stringbuffer.append(matcher.group(10) + URL_APPEND_3
						+ matcher.group(10) + URL_APPEND_4);
			}
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
