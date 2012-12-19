package cn.newgxu.bbs.web.action;

import org.apache.log4j.Logger;

import cn.newgxu.bbs.web.model.TopThreeModel;


public class TopThreeAction extends AbstractBaseAction {

	private static final long	serialVersionUID	= 1L;
	private static Logger l = Logger.getLogger(TopThreeAction.class);
	
	private TopThreeModel model = new TopThreeModel();
	
	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		l.info("Top three!");
		l.debug("Debug!");
		model.setTopThrees(userService.getTopThree(1));
		return SUCCESS;
	}

}
