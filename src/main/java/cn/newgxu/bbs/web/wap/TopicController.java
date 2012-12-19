package cn.newgxu.bbs.web.wap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.TopicModel;

@Controller
@Scope("prototype")
public class TopicController extends AbstractBaseAction {
	private static final long serialVersionUID = -8708369307361742237L;

	private TopicModel model = new TopicModel();
	private String message = "";
	private ForumService forumService;
	
	public ForumService getForumService() {
		return forumService;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		model.setUser(getUser());
		model.getPagination().setParamMap(getParameterMap());
		model.getPagination().setActionName(getActionName());
		try {
			forumService.topic(model);
		} catch (Exception e) {
			setMessage(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

}
