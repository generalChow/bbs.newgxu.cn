package cn.newgxu.bbs.common.filter.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.BasicFilter;
import cn.newgxu.bbs.common.filter.Filter;
import cn.newgxu.bbs.common.filter.FilterUtil;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class FontFilter extends AbstractBaseFilter {

	public FontFilter(Filter filter) {
		super(filter);
	}

	private static final String FONT_PATTERN_STRING = "\\[size=([1-4])\\](.*?)(\\[\\/size\\])|"
			+ "\\[face=(.*)\\](.*?)(\\[\\/face\\])|"
			+ "\\[align=(.*)\\](.*?)(\\[\\/align\\])|"
			+ "\\[em([0-9]*)\\]|\\[face([0-9]*)\\]|\\[B\\](.*?)(\\[\\/B\\])|"
			+ "\\[I\\](.*?)(\\[\\/I\\])|\\[U\\](.*?)(\\[\\/U\\])|"
			+ "\\[center\\](.*?)(\\[\\/center\\])|"
			+ "\\[glow=(.+),(.+),(.+)\\]([^\\[]+)\\[/glow\\]|"
			+ "\\[SHADOW=(.+),(.+),(.+)\\]([^\\[]+)\\[/SHADOW\\]";

	private static final String FONT_APPEND_1 = "<font size=";

	private static final String FONT_APPEND_2 = ">";

	private static final String FONT_APPEND_3 = "</font>";

	private static final String FONT_APPEND_4 = "<font face=";

	private static final String FONT_APPEND_5 = "<div align=";

	private static final String FONT_APPEND_6 = "<img src=\"/images/newTopic/em";

	private static final String FONT_APPEND_7 = "<img src=\"/images/face/face";

	private static final String FONT_APPEND_8 = ".gif\" />";

	private static final String FONT_APPEDN_9 = "</div>";

	private static final String FONT_APPEND_10 = "<b>";

	private static final String FONT_APPEND_11 = "</b>";

	private static final String FONT_APPEND_12 = "<i>";

	private static final String FONT_APPEND_13 = "</i>";

	private static final String FONT_APPEND_14 = "<u>";

	private static final String FONT_APPEND_15 = "</u>";

	private static final String FONT_APPEND_16 = "<center>";

	private static final String FONT_APPEND_17 = "</center>";

	private static final String FONT_APPEND_18 = "<TABLE style=\"FILTER: glow(color=";

	private static final String FONT_APPEND_19 = ", strength=";

	private static final String FONT_APPEND_20 = ")\" width=";

	private static final String FONT_APPEND_21 = "><TBODY><TR><TD>";

	private static final String FONT_APPEND_22 = "</TD></TR></TBODY></TABLE>";

	private static final String FONT_APPEND_23 = "<DIV style=\"FILTER: shadow(color=";

	private static final String FONT_APPEND_24 = ", strength=";

	private static final String FONT_APPEND_25 = "); WIDTH: ";

	private static final String FONT_APPEND_26 = "px\">";

	private static final String FONT_APPEND_27 = "</DIV>";

	/*-------------------------------------------*/
//	private static final String COLOR_PATTERN_STRING = "\\[color=(#?[a-z0-9]*)\\](.+?)\\[\\/color\\]";
//
//	private static final String COLOR_APPEND_1 = "<font color=\"";
//
//	private static final String COLOR_APPEND_2 = "\">";
//
//	private static final String COLOR_APPEND_3 = "</font>";

	public String convert(String input) {
		try {
			return fontConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String fontConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(FONT_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();

		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			if (matcher.group(1) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_1
						+ fontConverter(matcher.group(1)) + FONT_APPEND_2
						+ matcher.group(2) + FONT_APPEND_3);
				continue;
			}

			else if (matcher.group(4) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_4
						+ fontConverter(matcher.group(4)) + FONT_APPEND_2
						+ matcher.group(5) + FONT_APPEND_3);
				continue;
			}

			else if (matcher.group(7) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_5
						+ fontConverter(matcher.group(7)) + FONT_APPEND_2
						+ matcher.group(8) + FONT_APPEDN_9);
				continue;
			}

			else if (matcher.group(10) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_6
						+ fontConverter(matcher.group(10)) + FONT_APPEND_8);
			}

			else if (matcher.group(11) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_7
						+ fontConverter(matcher.group(11)) + FONT_APPEND_8);
			}

			else if (matcher.group(12) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_10
						+ fontConverter(matcher.group(12)) + FONT_APPEND_11);
			}

			else if (matcher.group(14) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_12
						+ fontConverter(matcher.group(14)) + FONT_APPEND_13);
			}

			else if (matcher.group(16) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_14
						+ fontConverter(matcher.group(16)) + FONT_APPEND_15);
			}

			else if (matcher.group(18) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_16
						+ fontConverter(matcher.group(18)) + FONT_APPEND_17);
			}

			else if (matcher.group(23) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_18
						+ matcher.group(21) + FONT_APPEND_19
						+ matcher.group(22) + FONT_APPEND_20
						+ matcher.group(20) + FONT_APPEND_21
						+ matcher.group(23) + FONT_APPEND_22);
			}

			else if (matcher.group(27) != null) {
				matcher.appendReplacement(stringbuffer, FONT_APPEND_23
						+ matcher.group(25) + FONT_APPEND_24
						+ matcher.group(26) + FONT_APPEND_25
						+ matcher.group(24) + FONT_APPEND_26
						+ matcher.group(27) + FONT_APPEND_27);
			}
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	public static void main(String[] args) {
		System.out
				.println(new FontFilter(new BasicFilter())
						.convert("原文是[color=#B3B34D][face=新宋体][size=2]不会吧，~~~~~~这样么，我也来试试[/size][/face][/color]"));
		System.out
				.println(FilterUtil
						.replyContent("原文是[color=#B3B34D][face=新宋体][size=2]不会吧，~~~~~~这样么，我也来试试[/size][/face][/color]"));
	}
}
