package cn.newgxu.bbs.common.filter.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.Filter;

/**
 * 
 * @author ddy
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SizeFilter extends AbstractBaseFilter {

	private static final String SIZE_PATTERN_STRING = "\\[size=([1-5])\\](.*?)(\\[\\/size\\])";

	private static final String SIZE_APPEND_1 = "<font size=";

	private static final String SIZE_APPEND_2 = ">";

	private static final String SIZE_APPEND_3 = "</font>";

	// 2010-06-28修改5号字体显示问题，如果是5号字体就添加<span块控制
	private static final String SPAN_LINE_HEIGHT_BEFORE = "<span style=\"line-height:100%;\">";

	private static final String SPAN_LINE_HEIGHT_AFTER = "</span>";

	public SizeFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return sizeConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String sizeConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(SIZE_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();

		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher
					.appendReplacement(sb, "" + SIZE_APPEND_1
							+ matcher.group(1) + SIZE_APPEND_2
							+ sizeChoiceLine(matcher.group(1))
							+ matcher.group(2) + SPAN_LINE_HEIGHT_AFTER
							+ SIZE_APPEND_3);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String sizeChoiceLine(String s) {
		int i = Integer.parseInt(s);
		String str = "";
		switch (i) {
		case 5:
			str = SPAN_LINE_HEIGHT_BEFORE;
			break;
		default:
			break;
		}
		return str;
	}
}
