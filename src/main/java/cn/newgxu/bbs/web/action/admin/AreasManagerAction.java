package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.AreaManageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AreasManagerAction extends AbstractBaseAction {

	private static final long serialVersionUID = 3040850707528477571L;

	private AreaManageModel model = new AreaManageModel();

	private ForumService forumService;

	public String execute() throws Exception {
		model.setAreas(this.forumService.getAllAreas());
		return SUCCESS;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public Object getModel() {
		return model;
	}

}
