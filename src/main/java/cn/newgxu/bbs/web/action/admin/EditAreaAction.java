package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.AreaManageModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditAreaAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private AreaManageModel model = new AreaManageModel();

	private ForumService forumService;

	public String execute() throws Exception {
		MessageList m = new MessageList();
		try {
			Area area = forumService.getArea(model.getAreaId());
			model.setName(area.getName());
			model.setDescription(area.getDescription());
			model.setTaxis(area.getTaxis());
			model.setHidden(area.isHidden() ? 1 : 0);
			model.setHot(area.isHot() ? 1 : 0);
			String nicks = "";
			for (User user : area.getWebmasters()) {
				nicks += user.getNick() + "\r\n";
			}
			model.setNicks(nicks);
			return SUCCESS;
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String editAreaDo() throws Exception {
		MessageList m = new MessageList();
		try {
			forumService.editArea(model);
			response("区域论坛修改成功，请刷新列表。当前窗口可以关闭了");
			//response.getWriter().write("{\"statusCode\":\"200\", \"message\":\"区域论坛修改成功\", \"navTabId\":\"forum_info\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"areas.yws\"}");
			return null;
			/*
			m.setUrl("#");
			m.addMessage("<b>更改成功！</b>");
			Util.putMessageList(m, getSession());
			return SUCCESS;
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
