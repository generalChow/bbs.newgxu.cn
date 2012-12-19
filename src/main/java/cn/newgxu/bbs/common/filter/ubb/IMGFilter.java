package cn.newgxu.bbs.common.filter.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.Filter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class IMGFilter extends AbstractBaseFilter {

	private static final String IMG_PATTERN_STRING = "(\\[img\\])(.[^\\[]*)(\\[\\/img\\])";

	private static final String IMG_APPEND_1 = "<br><a href=\"";

	private static final String IMG_APPEND_2 = "\" target=\"_blank\"><img src=\"";

	private static final String IMG_APPEND_3 = "\" border=0 alt=\"点击看大图\" "
			+ "onload=\"javascript:if (this.width>600) this.width="
			+ "600;\" /></a><br>";

	public IMGFilter(Filter filter) {
		super(filter);
	}

	@Override
	protected String convert(String str) {
		Pattern pattern = Pattern.compile(IMG_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(str);
		StringBuffer stringbuffer = new StringBuffer();

		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(stringbuffer, IMG_APPEND_1
					+ StringUtils.replace(matcher.group(2), ".yws", " ")
					+ IMG_APPEND_2 + matcher.group(2) + IMG_APPEND_3);
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
