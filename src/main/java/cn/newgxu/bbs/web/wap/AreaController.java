package cn.newgxu.bbs.web.wap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.MoreResultsModel;

@Controller
@Scope("prototype")
public class AreaController extends AbstractBaseAction {

	private static final long serialVersionUID = 6383145720517814880L;

	MoreResultsModel model = new MoreResultsModel();
	
	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		Area area = Area.get(model.getId());
		model.setArea(area);
		model.setMoreTopics(area.getLatestTopics(10));
		return SUCCESS;
	}

}
