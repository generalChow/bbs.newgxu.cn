package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.ManageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ManageAction extends AbstractBaseAction {

	private static final long serialVersionUID = -9490143097665510L;

	private static final Log log = LogFactory.getLog(TopicAction.class);

	private ForumService forumService;

	private ManageModel model = new ManageModel();

	public String execute() throws Exception {
		signOnlineUser("操作管理");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			Topic topic = forumService.manage(model);
			m.setUrl("/forum.yws?forumId=${forumId}", MessageList.P(
					"${forumId}", model.getForumId()));
			m.addMessage("<b>操作成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			m
					.addMessage(
							"<a href='/topic.yws?forumId=${forumId}&topicId=${topicId}'>查看我刚才操作的主题</a>",
							MessageList.P("${forumId}", topic.getForum()
									.getId()), MessageList.P("${topicId}",
									topic.getId()));
			Util.putMessageList(m, getSession());
			log.debug("操作成功！");
			return SUCCESS;
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
