package cn.newgxu.bbs.common.filter.ubb;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.Filter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BrFilter extends AbstractBaseFilter {

	private static String BR_PATTERN_STRING = "\r\n";

	private static final String BR_APPEND_1 = "<br>";
	
	public BrFilter(Filter filter) {
		super(filter);
	}

	@Override
	protected String convert(String str) {
		//return str;
		/*
		 * 旧版中使用的 <br /> 转换。现在不用先
		 * by:集成显卡  2011-12-5
		 */
		return str.replaceAll(BR_PATTERN_STRING, BR_APPEND_1);
	}

}
