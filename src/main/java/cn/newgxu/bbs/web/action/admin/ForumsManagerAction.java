package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.ForumManageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ForumsManagerAction extends AbstractBaseAction {

	private static final long serialVersionUID = -6615848397182081396L;

	private ForumManageModel model = new ForumManageModel();

	private ForumService forumService;

	public String execute() throws Exception {
		model.setArea(this.forumService.getArea(model.getAreaId()));
		return SUCCESS;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public Object getModel() {
		return model;
	}

}
