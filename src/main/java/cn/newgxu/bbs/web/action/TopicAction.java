package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.TopicModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

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
	private String id ;
	
	
	
	



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
	
	public String getTopicById() throws ObjectNotFoundException{
		//System.out.println("it is here");
		//System.out.println(id);
		Topic topic =forumService.getTopicById(Integer.parseInt(id));
		System.out.println(topic.toString());
		return "success";
	}
	
	public String deleteTopicById() throws BBSException, ObjectNotFoundException{
		System.out.println("it is here");
		System.out.println(id);
		//Topic topic =forumService.getTopicById(Integer.parseInt(id));
		//System.out.println(topic.toString());
		//topic.delete();
		forumService.deleteTopic(Integer.parseInt(id));
		return "success";
	}

}
