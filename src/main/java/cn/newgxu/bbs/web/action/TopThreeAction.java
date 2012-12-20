package cn.newgxu.bbs.web.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.newgxu.bbs.web.model.TopThreeModel;


public class TopThreeAction extends AbstractBaseAction {

	private static final long	serialVersionUID	= 1L;
	private static final Logger l = LoggerFactory.getLogger(TopThreeAction.class);
	
	private TopThreeModel model = new TopThreeModel();
	
	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		l.info("获取榜单前三名！！！");
		model.setTopThrees(userService.getTopThree(1));
		return SUCCESS;
	}

}
