package cn.newgxu.bbs.web.wap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.ForumModel;

@Controller
@Scope("prototype")
public class ForumController extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;

	private ForumModel model = new ForumModel();
	private ForumService forumService;
	private String message = "";
	
	public ForumService getForumService() {
		return forumService;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		model.setViewer(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		try { 
			forumService.forum(model);
		} catch (Exception e) {
			setMessage(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
