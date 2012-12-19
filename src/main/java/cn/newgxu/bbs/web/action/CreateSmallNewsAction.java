package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.CreateSmallNewsModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateSmallNewsAction extends AbstractBaseAction {

	private static final long serialVersionUID = -8538755273711109508L;

	private static final Log log = LogFactory
			.getLog(CreateSmallNewsAction.class);

	private CreateSmallNewsModel model = new CreateSmallNewsModel();

	private ForumService forumService;

	public String execute() throws Exception {
		signOnlineUser("发布小字报");
		model.setUser(getUser());
		try {
			forumService.createSmallNews(model);
			super.setOnlineStatusForumId(model.getForumId());
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String submit() throws Exception {
		signOnlineUser("发布小字报中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			forumService.createSmallNewsDo(model);
			m.setUrl("/forum.yws?forumId=${forumId}", MessageList.P(
					"${forumId}", model.getForumId()));
			m.addMessage("<b>新主题发表成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			Util.putMessageList(m, getSession());
			log.debug("小字报发布成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			log.debug(e);
			addValidateMsg(e.getMessage());
			return execute().equals(SUCCESS) ? INPUT : ERROR;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

}
