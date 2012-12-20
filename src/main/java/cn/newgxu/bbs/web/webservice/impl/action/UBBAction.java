package cn.newgxu.bbs.web.webservice.impl.action;

import cn.newgxu.bbs.common.filter.FilterUtil;
import cn.newgxu.bbs.web.webservice.impl.BaseWebAction;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.webservice.impl.action.UBBAction.java
 * 
 * @author 集成显卡
 * @since 4.0.0
 * @version $Revision 1.1$
 * @date 2011-7-2
 * @describe 用于发帖预览 从浏览器口post带有UBB标签的内容，返回ubb转换成Html后的内容
 * 
 */
public class UBBAction extends BaseWebAction {
	
	private static final long serialVersionUID = 32934872864354334L;

	private String input = "";

	/**
	 * 默认的方法就是UBB标签到Html标签的转换
	 */
	public String execute() {
		try {
			// 使用FilterUtil过滤
			input = FilterUtil.ubb(input);
		} catch (Exception e) {
			l.error("UBB标签转换时出错" + e.getMessage());
			input = e.getMessage();
		}
		return SUCCESS;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
}
