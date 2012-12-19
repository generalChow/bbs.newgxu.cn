package cn.newgxu.bbs.common.filter.ubb;

import org.apache.commons.lang.StringUtils;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.Filter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class FormatFilter extends AbstractBaseFilter {

	private static String FORMAT_PATTERN_STRING = "  ";
	
	private static String FORMAT_PATTERN_STRING_1 = "\t";

	private static final String FORMAT_APPEND_1 = "&nbsp;&nbsp;&nbsp;";
	
	private static final String FORMAT_APPEND_2 = "&nbsp;&nbsp;&nbsp;&nbsp;";

	public FormatFilter(Filter filter) {
		super(filter);
	}

	public String convert(String input) {
		return FormatConverter(input);
	}

	private String FormatConverter(String input) {
		input = StringUtils.replace(input, FORMAT_PATTERN_STRING, FORMAT_APPEND_1);
		input = StringUtils.replace(input, FORMAT_PATTERN_STRING_1, FORMAT_APPEND_2);
		return input;
	}

}
