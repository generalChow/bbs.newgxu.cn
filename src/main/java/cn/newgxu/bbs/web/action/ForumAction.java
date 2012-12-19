package cn.newgxu.bbs.web.action;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.activity.Christmas;
import cn.newgxu.bbs.domain.activity.Doomsday;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.ForumModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ForumAction extends AbstractBaseAction {

	private static final long serialVersionUID = -24365334259326306L;

	private static final Log log = LogFactory.getLog(ForumAction.class);

	private ForumModel model = new ForumModel();

	private ForumService forumService;
	
	@Override
	public String execute() throws Exception {
		signOnlineUser("<a href=\"/forum.yws?forumId=" + model.getForumId()
				+ "\">查看论坛主题列表</a>");
		model.setViewer(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		try {
			forumService.forum(model);
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

	public Object getModel() {
		return model;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}
	
}
