package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.ModifyModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ModifyAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1969751312825171867L;

	private static final Log log = LogFactory.getLog(ModifyAction.class);

	private ModifyModel model = new ModifyModel();

	private ForumService forumService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("修改文章内容");
		model.setUser(getUser());
		try {
			forumService.modify(model);
			super.setOnlineStatusForumId(model.getForumId());
			return model.getReply().isFirstReply() ? "topic" : "reply";
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String submit() throws Exception {
		signOnlineUser("修改提交中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			forumService.modifyDo(model);
			m
					.setUrl(
							"/topic.yws?forumId=${forumId}&topicId=${topicId}&page=${page}#${replyId}",
							MessageList.P("${forumId}", model.getForumId()),
							MessageList.P("${topicId}", model.getTopicId()),
							MessageList.P("${page}", model.getTopic()
									.getReplyPages()), MessageList.P(
									"${replyId}", model.getReplyId()));
			m.addMessage("<b>修改成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			m
					.addMessage(
							"<a href='/topic.yws?forumId=${forumId}&topicId=${topicId}&page=${page}#${replyId}'>查看我刚才的修改</a>",
							MessageList.P("${forumId}", model.getForumId()),
							MessageList.P("${topicId}", model.getTopicId()),
							MessageList.P("${page}", model.getTopic()
									.getReplyPages()), MessageList.P(
									"${replyId}", model.getReplyId()));
			Util.putMessageList(m, getSession());
			log.debug("修改成功！");
			return SUCCESS;
		} catch (BBSException e) {
			e.printStackTrace();
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
