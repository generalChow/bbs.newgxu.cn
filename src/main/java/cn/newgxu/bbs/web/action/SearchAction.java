package cn.newgxu.bbs.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.SearchModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SearchAction extends AbstractBaseAction {

	private static final long serialVersionUID = -9490143097665510L;

	private static final Log log = LogFactory.getLog(TopicAction.class);

	private ForumService forumService;

	private SearchModel model = new SearchModel();

	public String execute() throws Exception {
		signOnlineUser("检索中心");
		this.model.setAreas(this.forumService.getAreas());
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("检索中心");
		MessageList m = new MessageList();
		try {
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			forumService.search(model);
			log.debug("检索完成！");
			return SUCCESS;
		} catch (ValidationException e) {
			addValidateMsg(e.getMessage());
			String result = execute();
			return SUCCESS.equals(result) ? INPUT : result;
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
