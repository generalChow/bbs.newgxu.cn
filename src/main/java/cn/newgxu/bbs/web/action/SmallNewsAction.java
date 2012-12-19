package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.SmallNewsModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SmallNewsAction extends AbstractBaseAction {

	private static final long serialVersionUID = -8538755273711109508L;

	private static final Log log = LogFactory.getLog(SmallNewsAction.class);

	private SmallNewsModel model = new SmallNewsModel();

	private ForumService forumService;

	public String execute() throws Exception {
		signOnlineUser("查看小字报");
		model.setUser(getUser());
		try {
			model.setSmallNews(forumService.smallNews(model.getId()));
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

	public String list() throws Exception {
		signOnlineUser("浏览小字报");
		model.setUser(getUser());
		try {
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			forumService.getSmallNewsList(model);
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String del() throws Exception {
		signOnlineUser("浏览小字报");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			forumService.delSmallNews(model);
			m.setUrl("#");
			m.addMessage("<b>删除成功！</b>");
			log.debug("删除小字报成功！");
			Util.putMessageList(m, getSession());
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
