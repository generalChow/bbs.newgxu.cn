package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.VoteModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class VoteAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2614570106442478326L;

	private static final Log log = LogFactory.getLog(VoteAction.class);

	private VoteModel model = new VoteModel();

	private ForumService forumService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("投票提交中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			Topic topic = forumService.vote(model);
			m.setUrl("/topic.yws?forumId=${forumId}&topicId=${topicId}",
					MessageList.P("${forumId}", model.getForumId()),
					MessageList.P("${topicId}", model.getTopicId()));
			m.addMessage("<b>投票成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			m.addMessage(
							"<a href='/topic.yws?forumId=${forumId}&topicId=${topicId}'>查看我刚才投票的主题</a>",
							MessageList.P("${forumId}", model.getForumId()),
							MessageList.P("${topicId}", topic.getId()));
			Util.putMessageList(m, getSession());
			log.debug("投票成功！");
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
