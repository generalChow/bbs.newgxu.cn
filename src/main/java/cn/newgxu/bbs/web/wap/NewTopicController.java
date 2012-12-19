package cn.newgxu.bbs.web.wap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.CreateTopicModel;

@Controller
@Scope("prototype")
public class NewTopicController extends AbstractBaseAction {

	private static final long serialVersionUID = 729000955461695495L;

	private CreateTopicModel model = new CreateTopicModel();
	private ForumService forumService;
	private String message = "";

	public ForumService getForumService() {
		return forumService;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		if (getUser() == null) {
			setMessage("请登录后再发表帖子！");
			return "login";
		}
		model.setFace(1); // 默认是1
		model.setUser(getUser());
		try {
			forumService.createTopic(model);
		} catch (Exception e) {
			e.printStackTrace();
			setMessage(e.getMessage());
			return ERROR;
		}
		super.setOnlineStatusForumId(model.getForumId());
		return SUCCESS;
	}

	public String newTopic() {
		Topic topic = null;
		try {
			if (getUser() == null) {
				setMessage("请登录后再发帖！");
				return "login";
			}
			model.setUser(getUser());
			topic = forumService.createTopicDo(model);
		} catch (Exception e) {
			setMessage(e.getMessage());
			return ERROR;
		}
		message = "<p style='color: red;'>发表主题成功！</p>"
				+ "<a href='/wap/topic.yws?topicId=" + topic.getId()
				+ "&forumId=" + topic.getForum().getId()
				+ "'>查看我刚才发表的主题</a> <br />"
				+ "<a href='/wap/forum.yws?forumId=" + topic.getForum().getId()
				+ "'>返回板块列表</a>";
		System.out.println(message);
		return SUCCESS;
	}

}
