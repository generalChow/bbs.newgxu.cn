package cn.newgxu.bbs.common.filter;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HTMLEscapeFilter extends AbstractBaseFilter {

	public HTMLEscapeFilter(Filter filter) {
		super(filter);
	}

	/**
	 * 因为可以支持直接写入html标签，也可以写入 ubb
	 * 现在只屏蔽了 javascript 标签
	 */
	protected String convert(String s) {
		/*
		String str = StringUtils.replace(s, "<", "&lt;");
		       str = StringUtils.replace(str, "javascript", "java_script");
		return StringUtils.replace(str, ">", "&gt;");
		*/
		String str = StringUtils.replace(s, "<script>", "&lt;script&gt;");
		str = StringUtils.replace(str, "</script>", "&lt;/script&gt;");
		str = StringUtils.replace(str, "<script language=\"javascript\">", "&lt;script&gt;");
		return str;
		
	}

}
