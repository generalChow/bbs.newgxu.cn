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
public class FaceFilter extends AbstractBaseFilter {

	private static final String FACE_PATTERN_STRING = "\\[face=(新宋体|宋体|楷体_GB2312|黑体|隶书|Andale Mono|Arial|Arial Black|Book Antiqua|Century Gothic|Comic Sans MS|Courier New|Impact|Georgia|Tahoma|Times New Roman|Trebuchet MS|Script MT Bold|Stencil|Verdana|Lucida Console)\\](.*?)(\\[\\/face\\])";

	private static final String FACE_APPEND_1 = "<font face=";

	private static final String FACE_APPEND_2 = ">";

	private static final String FACE_APPEND_3 = "</font>";

	public FaceFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return faceConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String faceConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(FACE_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();

		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(sb, "" + FACE_APPEND_1 + matcher.group(1)
					+ FACE_APPEND_2 + matcher.group(2) + FACE_APPEND_3);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
