package cn.newgxu.bbs.web.action.user;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.SaveCupOfLifeModel;

/**
 * 
 * @author daodaoyu
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SaveCupOfLifeAction extends AbstractBaseAction {

	private static final long serialVersionUID = -481667924342826558L;

	private SaveCupOfLifeModel model = new SaveCupOfLifeModel();

	private ForumService forumService;

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public String execute() throws Exception {
		signOnlineUser("查看世界杯专题...");
		model.setAreas(this.forumService.getAreas());
		model.setUser(getUser());
		userService.getCupOfLife(model);
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("添加世界杯忠实球队...");
		MessageList m = new MessageList();
		try {
			model.setAreas(this.forumService.getAreas());
			model.setUser(getUser());
			userService.saveCupOfLife(model);
			m.setUrl("/index.yws");
			m.addMessage("<b>操作成功！</b>");
			m.addMessage("<a href='/index.yws'>返回主页</a>");
			Util.putMessageList(m, getSession());
			return SUCCESS;
		} catch (BBSException e) {
			e.printStackTrace();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

}
