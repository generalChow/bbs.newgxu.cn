package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.ForumManageModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditForumAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private ForumManageModel model = new ForumManageModel();

	private ForumService forumService;

	public String execute() throws Exception {
		MessageList m = new MessageList();
		try {
			model.setAreas(forumService.getAllAreas());
			Forum forum = forumService.getForum(model.getForumId());
			model.setAreaId(forum.getArea().getId());
			model.setName(forum.getName());
			model.setCompositorId(forum.getCompositorId());
			model.setHot(forum.isHot() ? 1 : 0);
			model.setSecrecy(forum.isSecrecy() ? 1 : 0);
			model.setConfrere(forum.isConfrere() ? 1 : 0);
			model.setLimit_topics_perday(forum.getLimit_topics_perday());
			model.setTopicMoney(forum.getTopicMoney());
			model.setTopicExp(forum.getTopicExp());
			model.setReplyMoney(forum.getReplyMoney());
			model.setReplyExp(forum.getReplyExp());
			model.setGoodMoney(forum.getGoodMoney());
			model.setGoodExp(forum.getGoodExp());
			model.setLightMoney(forum.getLightMoney());
			model.setLightExp(forum.getLightExp());
			model.setDelMoney(forum.getDelMoney());
			model.setDelExp(forum.getDelExp());
			model.setDescription(forum.getDescription());
			String nicks = "";
			for (User user : forum.getWebmasters()) {
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

	public String editForumDo() throws Exception {
		MessageList m = new MessageList();
		try {
			forumService.editForum(model);
			response("论坛修改成功，请刷新列表。当前窗口可以关闭了");
			//response("{\"statusCode\":\"200\", \"message\":\"论坛编辑成功，请刷新相应的区域论坛以查看！\", \"navTabId\":\"\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"\"}");
			return null;
			/*
			m.setUrl("#");
			m.addMessage("<b>更改成功！</b>");
			Util.putMessageList(m, getSession());
			return SUCCESS;*/
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
