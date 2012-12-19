package cn.newgxu.bbs.web.wap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.ReplyModel;

@Controller
@Scope("prototype")
public class ReplyController extends AbstractBaseAction {

	private static final long serialVersionUID = 7695215466078438200L;

	private ReplyModel model = new ReplyModel();
	private String message = "";
	private ForumService forumService;

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
		// System.out.println(getRequest().getRequestURI());
		if (getUser() == null) {
			setMessage("请登录后再回复！");
			return "login";
		}
		model.setUser(getUser());
		// model.setIfPhone(true);
		try {
			forumService.reply(model);
		} catch (Exception e) {
			e.printStackTrace();
			setMessage(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String feedbackProcess() {
		try {
			if (getUser() == null) {
				setMessage("请登录后再回复！");
				return "login";
			}
		} catch (BBSException e1) {
			e1.printStackTrace();
			setMessage(e1.getMessage());
			return ERROR;
		}
		Topic topic = null;
		try {
			model.setUser(getUser());
			topic = forumService.createReply(model);
		} catch (BBSException e) {
			e.printStackTrace();
			setMessage(e.getMessage());
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			setMessage(e.getMessage());
			return ERROR;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("恭喜您发表回复成功！<br />");
		sb.append("您可以：<br />");
		sb.append("<a href='/wap/topic.yws?topicId=").append(topic.getId())
				.append("&forumId=").append(topic.getForum().getId())
				.append("'>返回查看刚才回复的帖子</a><br />");
		sb.append("<a href='/wap/forum.yws?forumId=")
				.append(topic.getForum().getId()).append("'>回到主题列表</a>");
		setMessage(sb.toString());
		return SUCCESS;
	}
	
	public String reply() {
		try {
			if (getUser() == null) {
				setMessage("请您登录后再回复！");
				return "login";
			}
			model.setUser(getUser());
			System.out.println(model.getForumId());
			forumService.replyFast(model);
		} catch (Exception e) {
			e.printStackTrace();
			setMessage(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String replyProcess() {
		try {
			if (getUser() == null) {
				setMessage("请您登录后再回复！");
				return "login";
			}
			model.setUser(getUser());
			forumService.replyFastDo(model);
		} catch (Exception e) {
			setMessage(e.getMessage());
			return ERROR;
		}
		Topic topic = model.getTopic();
		StringBuilder sb = new StringBuilder();
		sb.append("恭喜您发表回复成功！<br />");
		sb.append("您可以：<br />");
		sb.append("<a href='/wap/topic.yws?topicId=").append(topic.getId())
				.append("&forumId=").append(topic.getForum().getId())
				.append("'>返回查看刚才回复的帖子</a><br />");
		sb.append("<a href='/wap/forum.yws?forumId=")
				.append(topic.getForum().getId()).append("'>回到主题列表</a>");
		setMessage(sb.toString());
		return SUCCESS;
	}

}
