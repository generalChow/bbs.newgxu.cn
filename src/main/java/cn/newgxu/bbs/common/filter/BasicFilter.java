package cn.newgxu.bbs.common.filter;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BasicFilter implements Filter {

	public String doFilter(String str) {
		return StringUtils.defaultString(str);
	}
	
	

}
