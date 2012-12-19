package cn.newgxu.bbs.common.filter;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class PlayerCodeFilter {

	private static final String ZIP_ICON = "[img]images/zip_icon.gif[/img]";

	public static String doFilter(String str, String uri) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}

		String s = StringUtils.replace(str, "{uri}", uri);
		s = s.replaceAll("\\{zip\\}", ZIP_ICON);
		return s;
	}

}
