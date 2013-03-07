package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.TopicModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class TopicAction extends AbstractBaseAction {

	private static final long serialVersionUID = -9490143097665510L;

	private static final Log log = LogFactory.getLog(TopicAction.class);

	private ForumService forumService;

	private TopicModel model = new TopicModel();

	public String execute() throws Exception {
		model.setUser(getUser());
		MessageList m = new MessageList();
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		try {
			forumService.topic(model);
			super.setOnlineStatusForumId(model.getForumId());
			signOnlineUser("查看主题：<a href=\"/topic.yws?forumId="
					+ model.getForumId() + "&amp;topicId=" + model.getTopicId()
					+ "\">" + model.getTopic().getTitle() + "</a>");
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

}
