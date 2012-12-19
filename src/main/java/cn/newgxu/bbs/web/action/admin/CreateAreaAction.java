package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.AreaManageModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateAreaAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private AreaManageModel model = new AreaManageModel();

	private ForumService forumService;

	public String execute() throws Exception {
		return SUCCESS;
	}

	public String createAreaDo() throws Exception {
		try {
			forumService.createArea(model);
			response("区域论坛添加成功，请刷新列表。当前窗口可以关闭了");
			//response.getWriter().write("{\"statusCode\":\"200\", \"message\":\"区域论坛创建成功\", \"navTabId\":\"forum_info\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"areas.yws\"}");
			return null;
			/*
			MessageList m = new MessageList();
			m.setUrl("#");
			m.addMessage("<b>创建成功！</b>");
			Util.putMessageList(m, getSession());
			return SUCCESS;
			*/
		} catch (BBSException e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public Object getModel() {
		return model;
	}

}
