package cn.newgxu.bbs.common.filter;

import cn.newgxu.bbs.common.util.IllegalWordUtil;

/**
 * 名字非法字符串过滤器
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class NameWordFilter extends AbstractBaseFilter {

	public NameWordFilter(Filter filter) {
		super(filter);
	}

	@Override
	protected String convert(String str) {
		return IllegalWordUtil.getFilterString(str, "name");
	}

}
