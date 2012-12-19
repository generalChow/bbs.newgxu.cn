package cn.newgxu.bbs.common.filter;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class QuotationMarkFilter extends AbstractBaseFilter {

	public QuotationMarkFilter(Filter filter) {
		super(filter);
	}

	protected String convert(String str) {
		String s = StringUtils.replaceChars(str, "'", "");
		return StringUtils.replaceChars(s, "\"", "");
	}
	

}
