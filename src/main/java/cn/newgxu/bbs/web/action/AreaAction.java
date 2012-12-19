package cn.newgxu.bbs.web.action;

import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.AreaModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AreaAction extends AbstractBaseAction {

	private static final long serialVersionUID = -618934209333695632L;

	private AreaModel model = new AreaModel();

	private ForumService forumService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("查看分区论坛列表");
		forumService.area(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

}
