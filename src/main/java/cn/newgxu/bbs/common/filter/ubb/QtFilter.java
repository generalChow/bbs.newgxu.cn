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
public class QtFilter extends AbstractBaseFilter {

	private static final String QT_PATTERN_STRING = "\\[qt=(.+?),(.+?)\\](.+?)\\[\\/qt\\]";

	private static final String QT_APPEND_1 = "<embed src=\"";

	private static final String QT_APPEND_2 = "\" width=\"";

	private static final String QT_APPEND_3 = "\" height=\"";

	private static final String QT_APPEND_4 = "\" autoplay=true loop=false "
			+ "controller=true playeveryframe=false cache=false scale=TOFIT "
			+ "bgcolor=#000000 kioskmode=false targetcache=false pluginspage="
			+ "http://www.apple.com/quicktime/>";

	public QtFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return qtConverter(input);
		} catch (Exception e) {
			e.printStackTrace();
			return input;
		}
	}

	private String qtConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(QT_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(stringbuffer, QT_APPEND_1
					+ matcher.group(3) + QT_APPEND_2 + matcher.group(1)
					+ QT_APPEND_3 + matcher.group(2) + QT_APPEND_4);
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
