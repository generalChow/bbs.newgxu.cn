package cn.newgxu.bbs.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.activity.Christmas;
import cn.newgxu.bbs.domain.activity.Doomsday;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.cache.BBSCache;
import cn.newgxu.bbs.web.model.CreateTopicModel;
import cn.newgxu.bbs.web.webservice.util.Twitter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateTopicAction extends AbstractBaseAction {

	private static final long serialVersionUID = -8204797530439905156L;

	private static final Log log = LogFactory.getLog(CreateTopicAction.class);

	private CreateTopicModel model = new CreateTopicModel();

	private ForumService forumService;
	

	public String execute() throws Exception {
		signOnlineUser("发表新主题");
		model.setUser(getUser());
		try {
			forumService.createTopic(model);
			super.setOnlineStatusForumId(model.getForumId());
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		
		activity();
		
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("新主题提交中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		
		log.debug(model.getActivity());
		
		try {
			Topic topic = forumService.createTopicDo(model);
			m.setUrl("/topic.yws?forumId=${forumId}&topicId=${topicId}",
					MessageList.P("${forumId}", model.getForumId()),
					MessageList.P("${topicId}", topic.getId()));
			m.addMessage("<b>新主题发表成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			m
					.addMessage(
							"<a href='/topic.yws?forumId=${forumId}&topicId=${topicId}'>查看我刚才发表的主题</a>",
							MessageList.P("${forumId}", model.getForumId()),
							MessageList.P("${topicId}", topic.getId()));
			Util.putMessageList(m, getSession());
			log.debug("新主题发表成功！");
			
			BBSCache.topicCount2++;
			
			if (model.getSynchronousTwitter() == 1) {
				synchronousTwitter(topic);
			}
			
			return SUCCESS;
		} catch (Exception e) {
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
	
	private void synchronousTwitter(Topic topic) {
		Twitter twitter = new Twitter();
		twitter.setUrl("http://bbs.newgxu.cn/topic.yws?forumId=" + topic.getForum().getId() + "&topicId=" + topic.getId());
		twitter.setTitle(topic.getTitle());
		System.out.println(model.getUser().getUsername());
		twitter.synchronousTwitter(model.getUser().getUsername(), model.getUser().getPassword(), 1);
		twitter = null;
	}
	
}
