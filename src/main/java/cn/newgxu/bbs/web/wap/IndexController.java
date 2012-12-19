package cn.newgxu.bbs.web.wap;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.IndexModel;

@Controller
@Scope("prototype")
public class IndexController extends AbstractBaseAction {

	private static final long serialVersionUID = 7993653718783286621L;
	
	private static Logger l = Logger.getLogger(IndexController.class);

	private IndexModel model = new IndexModel();
	
	public void setModel(IndexModel model) {
		this.model = model;
	}

	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		l.info("This is a info test!");
		l.info("A user coming!");
		model.setAreas(Area.getAreas());
		model.setLatestTopics(Topic.getLatesTopics(10));
		return SUCCESS;
	}

}
