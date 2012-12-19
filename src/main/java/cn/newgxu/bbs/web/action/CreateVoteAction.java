package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.CreateVoteModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateVoteAction extends AbstractBaseAction {

	private static final long serialVersionUID = -8204797530439905156L;

	private static final Log log = LogFactory.getLog(CreateVoteAction.class);

	private CreateVoteModel model = new CreateVoteModel();

	private ForumService forumService;

	public String execute() throws Exception {
		signOnlineUser("发表投票主题");
		model.setUser(getUser());
		try {
			forumService.createVote(model);
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
		signOnlineUser("投票主题提交中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			Topic topic = forumService.createVoteDo(model);
			m.setUrl("/topic.yws?forumId=${forumId}&topicId=${topicId}",
					MessageList.P("${forumId}", model.getForumId()),
					MessageList.P("${topicId}", topic.getId()));
			m.addMessage("<b>投票主题发表成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			m
					.addMessage(
							"<a href='/topic.yws?forumId=${forumId}&topicId=${topicId}'>查看我刚才发表的主题</a>",
							MessageList.P("${forumId}", model.getForumId()),
							MessageList.P("${topicId}", topic.getId()));
			Util.putMessageList(m, getSession());
			log.debug("投票主题发表成功！");
			return SUCCESS;
		} catch (BBSException e) {
			addValidateMsg(e.getMessage());
			String result = execute();
			return SUCCESS.equals(result) ? INPUT : result;
		}
	}

	public Object getModel() {
		return model;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

}
