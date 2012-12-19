package cn.newgxu.bbs.common.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public abstract class AbstractBaseFilter implements Filter {
	
	protected Log log = LogFactory.getLog(getClass()); 

	protected Filter filter;

	public AbstractBaseFilter(Filter filter) {
		this.filter = filter;
	}

	public String doFilter(String str) {
		String s = convert(filter.doFilter(str));
//		输出的日记过多，且意义不大，屏蔽掉了 longkai@2012-10-11
//		if (log.isDebugEnabled()) {
//			log.debug(s);
//		}
		return s;
	}

	protected abstract String convert(String str);

}
