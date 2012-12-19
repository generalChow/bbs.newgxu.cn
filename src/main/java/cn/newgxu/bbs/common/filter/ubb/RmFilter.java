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
public class RmFilter extends AbstractBaseFilter {

	private static final String RM_PATTERN_STRING = "\\[rm=(.+?),(.+?)\\](.+?)\\[\\/rm\\]";

	private static final String RM_APPEND_1 = "<br>影音文件，单击播放按钮播放。"
			+ "<br><OBJECT class=OBJECT " + "id=RAOCX height=\"";

	private static final String RM_APPEND_2 = "\" width=\"";

	private static final String RM_APPEND_3 = "\" classid=clsid:CFCDAA03-8BE4-"
			+ "11cf-B84B-0020AFBBCCFA><PARAM NAME=\"SRC\" VALUE=\"";

	private static final String RM_APPEND_4 = "\"><PARAM NAME=\"CONSOLE\" "
			+ "VALUE=\"Clip1\"><PARAM NAME=\"CONTROLS\" VALUE=\"imagewindow\">"
			+ "<PARAM NAME=\"AUTOSTART\" VALUE=\"false\"></OBJECT><BR><OBJECT "
			+ "id=video2 height=32 width=\"";

	private static final String RM_APPEND_5 = "\" classid=CLSID:CFCDAA03-8BE4-11CF"
			+ "-B84B-0020AFBBCCFA><PARAM NAME=\"SRC\" VALUE=\"";

	private static final String RM_APPEND_6 = "\"><PARAM NAME=\"AUTOSTART\" "
			+ "VALUE=\"0\"><PARAM NAME=\"CONTROLS\" VALUE=\"controlpanel\">"
			+ "<PARAM NAME=\"CONSOLE\" VALUE=\"Clip1\"></OBJECT><br>";

	public RmFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		try {
			return rmConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String rmConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(RM_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(stringbuffer, RM_APPEND_1
					+ matcher.group(2) + RM_APPEND_2 + matcher.group(1)
					+ RM_APPEND_3 + matcher.group(3) + RM_APPEND_4
					+ matcher.group(1) + RM_APPEND_5 + matcher.group(3)
					+ RM_APPEND_6);
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
