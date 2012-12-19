package cn.newgxu.bbs.common.filter;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class WhitespaceEscapeFilter extends AbstractBaseFilter {

	public WhitespaceEscapeFilter(Filter filter) {
		super(filter);
	}

	protected String convert(String s) {
		String str = StringUtils.remove(s, (char)127);
		return StringUtils.deleteWhitespace(str);
	}
	
	

}
