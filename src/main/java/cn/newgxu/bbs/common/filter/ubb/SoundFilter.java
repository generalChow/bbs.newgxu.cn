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
public class SoundFilter extends AbstractBaseFilter {

	public SoundFilter(Filter filter) {
		super(filter);
	}

	private static final String SOUND_PATTERN_STRING = "\\[sound\\](.+?)\\[\\/sound\\]";

	private static final String SOUND_APPEND_1 = "<br><IMG alt=背景音乐 src=\""
			+ "./images/mid.gif\" border=0><BGSOUND src=\"";

	private static final String SOUND_APPEND_2 = "\" loop=infinite><br>";

	public String convert(String input) {
		try {
			return soundConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String soundConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(SOUND_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(stringbuffer, SOUND_APPEND_1
					+ matcher.group(1) + SOUND_APPEND_2);
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
