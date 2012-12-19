package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.CreateTopicModel;

/* *
 * @author 叨叨雨
 * @version 0.0.1
 * @since 2009-12-02
 * 
 */
@SuppressWarnings("serial")
public class DraftBoxAction extends AbstractBaseAction {

	private static final Log log = LogFactory.getLog(DraftBoxAction.class);

	private CreateTopicModel model = new CreateTopicModel();

	private ForumService forumService;

	public String saveDraftBox() throws Exception {
		try {
			signOnlineUser("新主题保存到草稿箱中...");
			MessageList m = new MessageList();
			model.setUser(super.getUser());
			super.setOnlineStatusForumId(model.getForumId());
			forumService.saveDraftBox(model);

			m.setUrl("/forum.yws?forumId=${forumId}", MessageList.P(
					"${forumId}", model.getForumId()));
			m.addMessage("<b>新主题保存成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			Util.putMessageList(m, getSession());
			log.debug("新主题保存成功！");
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}

	}

	public String saveAgainDraftBox() throws Exception {
		try {
			signOnlineUser("重新保存新主题到草稿箱中...");
			MessageList m = new MessageList();
			model.setUser(super.getUser());
			super.setOnlineStatusForumId(model.getForumId());
			forumService.saveAgainDraftBox(model, 0);
			m.setUrl("/getdraftboxes.yws");
			m.addMessage("<b>新主题再次保存成功！</b>");
			m.addMessage("<a href='/getdraftboxes.yws'>返回草稿列表</a>");
			Util.putMessageList(m, getSession());
			log.debug("新主题再次保存成功！");
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}

	}

	public String getDraftBoxes() throws Exception {
		try {
			signOnlineUser("读取所有草稿箱...");
			//MessageList m = new MessageList();
			model.setUser(super.getUser());
			super.setOnlineStatusForumId(model.getForumId());
			forumService.getDraftBoxes(model);
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}

	}

	public String readDraftBox() throws Exception {
		try {
			signOnlineUser("读取草稿箱...");
			//MessageList m = new MessageList();
			model.setUser(super.getUser());
			super.setOnlineStatusForumId(model.getForumId());
			model = forumService.getDraftBoxModel(model.getId());
			//Forum forum = model.getForum();
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String createTpFromDb() throws Exception {
		signOnlineUser("新主题提交中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
//			Topic topic = forumService.createTopicDo(model);
			forumService.saveAgainDraftBox(model, 1);
			m.setUrl("/forum.yws?forumId=${forumId}", MessageList.P(
					"${forumId}", model.getForumId()));
			m.addMessage("<b>新主题发表成功！</b>");
			m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
					MessageList.P("${forumId}", model.getForumId()));
			m.addMessage(
					"<a href='/topic.yws?forumId=${forumId}}'>查看我刚才发表的主题</a>",
					MessageList.P("${forumId}", model.getForumId()));
			Util.putMessageList(m, getSession());
			log.debug("新主题发表成功！");
			return SUCCESS;
		} catch (BBSException e) {
			e.printStackTrace();
			return ERROR;
		}

	}

	public String delDraftBoxes() throws Exception {
		signOnlineUser("删除草稿...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			forumService.delDraftBoxes(model);
			m.setUrl("/getdraftboxes.yws");
			m.addMessage("<b>删除成功！</b>");
			log.debug("删除草稿成功！");
			Util.putMessageList(m, getSession());
			return SUCCESS;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	@Override
	public String execute() throws Exception {
		signOnlineUser("新主题保存...");
		model.setUser(super.getUser());
		super.setOnlineStatusForumId(model.getForumId());
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

}
