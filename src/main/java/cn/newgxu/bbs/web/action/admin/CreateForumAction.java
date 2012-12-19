package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.ForumManageModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateForumAction extends AbstractBaseAction {

	private static final long serialVersionUID = 8139927647426477364L;

	private ForumManageModel model = new ForumManageModel();

	private ForumService forumService;

	public String execute() throws Exception {
		model.setAreas(forumService.getAllAreas());
		return SUCCESS;
	}

	public String createForumDo() throws Exception {
		MessageList m = new MessageList();
		try {
			forumService.createForum(model);
			response("论坛添加成功，请刷新列表。当前窗口可以关闭了");
			//response("{\"statusCode\":\"200\", \"message\":\"论坛创建成功，请刷新相应的区域论坛以查看！\", \"navTabId\":\"\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"\"}");
			return null;
			/*
			m.setUrl("#");
			m.addMessage("<b>创建成功！</b>");
			Util.putMessageList(m, getSession());
			*/
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public Object getModel() {
		return model;
	}

}
