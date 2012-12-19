package cn.newgxu.bbs.web.action;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.web.model.MoreResultsModel;

public class MoreResultsAction extends AbstractBaseAction {
	private static final long serialVersionUID = 4698152689002447217L;

	private MoreResultsModel moreResultsModel = new MoreResultsModel();
	
	public Object getModel() {
		return moreResultsModel;
	}

	@Override
	public String execute() throws Exception {
		Area area = Area.get(moreResultsModel.getId());
		moreResultsModel.setArea(area);
		moreResultsModel.setMoreTopics(area.getLatestTopics(30));
		return SUCCESS;
	}
}
