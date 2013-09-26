package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.service.MessageService;
import cn.newgxu.bbs.web.model.ReplyModel;
import cn.newgxu.bbs.web.model.message.SendMessageModel;
import cn.newgxu.bbs.web.webservice.util.Twitter;

import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ReplyAction extends AbstractBaseAction {

	private static final long serialVersionUID = -8204797530439905156L;

	private static final Log log = LogFactory.getLog(ReplyAction.class);

	private ReplyModel model = new ReplyModel();

	private ForumService forumService;

	private MessageService messageService;
	
	

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public String execute() throws Exception {
		signOnlineUser("回复主题");
		model.setUser(getUser());
		try {
			model.setIfPhone(getIsPhone());
			forumService.reply(model);
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
		signOnlineUser("回复主题提交中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			// add by ivy, 2012-04-14, 解决阿管反应刚注册的用户就可以回复帖子
			if ((System.currentTimeMillis()
					- model.getUser().getRegisterTime().getTime() < 24 * 3600 * 1000))
				throw new BBSException("对不起，您的注册时间不超过1天，无法回复帖子，请一天后再试");
			// add end...

			model.setIfPhone(getIsPhone());
			Topic topic = forumService.createReply(model);
			SendMessageModel sendModel = new SendMessageModel();
			sendModel.setTitle("我回复了你的主题，去看看吧");
			sendModel
					.setContent("<a target=\"_blank\" href=\"/topic.yws?forumId="
							+ topic.getForum().getId()
							+ "&topicId="
							+ topic.getId() + "\">去看看吧</a>");
			sendModel.setUser(getUser());
			sendModel.setUsers(model.getTopic().getUser().getNick());
			messageService.sendMessageDo(sendModel);
			// System.out.println("昵称是："+model.getTopic().getUser().getNick());
			// m.setUrl("/topic.yws?forumId=${forumId}&topicId=${topicId}&page=${page}",
			// MessageList.P("${forumId}", model.getForumId()),
			// MessageList.P("${topicId}", model.getTopicId()),
			// MessageList.P("${page}", topic.getReplyPages()));
			m.setUrl("/forum.yws?forumId=${forumId}",
					MessageList.P("${forumId}", model.getForumId()));
			m.addMessage("<b>新回复发表成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			m.addMessage(
					"<a href='/topic.yws?forumId=${forumId}&topicId=${topicId}&page=${page}'>查看我刚才回复的主题</a>",
					MessageList.P("${forumId}", model.getForumId()),
					MessageList.P("${topicId}", model.getTopicId()),
					MessageList.P("${page}", topic.getReplyPages()));
			Util.putMessageList(m, getSession());
			log.debug("新回复发表成功！");

			if (model.getSynchronousTwitter() == 2) {
				synchronousTwitter();
			}

			return SUCCESS;
		} catch (BBSException e) {
			e.printStackTrace();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	/**
	 * 快速回复
	 * 
	 * @return
	 * @throws Exception
	 */
	public String replyFast() throws Exception {
		model.setUser(getUser());
		try {
			forumService.replyFast(model);
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String replyFastDo() throws Exception {
		model.setUser(getUser());
		try {
			forumService.replyFastDo(model);
			ServletActionContext.getResponse().getWriter()
					.write("<script>parent.result('1');</script>");
			if (model.getSynchronousTwitter() == 2) {
				synchronousTwitter();
			}

			return null;
		} catch (BBSException e) {
			MessageList m = new MessageList();
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

	private void synchronousTwitter() {
		Twitter twitter = new Twitter();
		twitter.setUrl("http://bbs.newgxu.cn/topic.yws?topicId="
				+ model.getTopicId() + "&forumId=" + model.getForumId());
		twitter.setTitle(model.getTopic().getTitle());
		twitter.synchronousTwitter(model.getUser().getUsername(), model
				.getUser().getPassword(), 2);
		System.out.println(twitter.getUrl());
	}

}
