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
public class DirFilter extends AbstractBaseFilter {

	private static final String DIR_PATTERN_STRING = "\\[dir=(.+?),(.+?)\\](.+?)\\[\\/dir\\]";

	private static final String DIR_APPEND_1 = "<br><OBJECT codeBase=http://"
			+ "download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version="
			+ "7,0,2,0 height=\"";

	private static final String DIR_APPEND_2 = "\" width=\"";

	private static final String DIR_APPEND_3 = "\" classid=clsid:166B1BCA-3F9C"
			+ "-11CF-8075-444553540000><PARAM NAME=\"src\" VALUE=\"";

	private static final String DIR_APPEND_4 = "\"><embed src=\"";

	private static final String DIR_APPEND_5 = "\" pluginspage=http://"
			+ "www.macromedia.com/shockwave/download/ width=\"";

	private static final String DIR_APPEND_6 = "\" height=\"";

	private static final String DIR_APPEND_7 = "\"></embed></OBJECT><br>";

	public DirFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return dirConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String dirConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(DIR_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(stringbuffer, DIR_APPEND_1
					+ matcher.group(2) + DIR_APPEND_2 + matcher.group(1)
					+ DIR_APPEND_3 + matcher.group(3) + DIR_APPEND_4
					+ matcher.group(3) + DIR_APPEND_5 + matcher.group(1)
					+ DIR_APPEND_6 + matcher.group(2) + DIR_APPEND_7);
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
