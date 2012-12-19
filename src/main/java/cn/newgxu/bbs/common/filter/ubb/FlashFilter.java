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
public class FlashFilter extends AbstractBaseFilter {

	private static final String FLASH_PATTERN_STRING = "\\[flash=(.+?),(.+?)\\](.+?)\\[\\/flash\\]";

	private static final String FLASH_APPEND_1 = "<br><object classid=\"clsid:"
			+ "D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\" http://download"
			+ ".macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version="
			+ "6,0,29,0\" width=\"";

	private static final String FLASH_APPEND_2 = "\" height=\"";

	private static final String FLASH_APPEND_3 = "\"><param name=\"movie\" "
			+ "value=\"";

	private static final String FLASH_APPEND_4 = "\"><param name=\"wmode\" value=\"transparent\" /><param name=\"quality\" "
			+ "value=\"high\"><embed src=\"";

	private static final String FLASH_APPEND_5 = "\" width=\"";

	private static final String FLASH_APPEND_6 = "\" height=\"";

	private static final String FLASH_APPEND_7 = "\" quality=\"high\" "
			+ "pluginspage=\"http://www.macromedia.com/go/getflashplayer\" "
			+ "type=\"application/x-shockwave-flash\"></embed></object><br>";

	public FlashFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return flashConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String flashConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(FLASH_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(
					stringbuffer,
					FLASH_APPEND_1 + matcher.group(1) + FLASH_APPEND_2
							+ matcher.group(2) + FLASH_APPEND_3
							+ matcher.group(3) + FLASH_APPEND_4
							+ matcher.group(3) + FLASH_APPEND_5
							+ matcher.group(1) + FLASH_APPEND_6
							+ matcher.group(2) + FLASH_APPEND_7);
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
