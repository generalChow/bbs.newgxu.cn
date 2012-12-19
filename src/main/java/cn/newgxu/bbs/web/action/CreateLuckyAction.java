package cn.newgxu.bbs.web.action;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.LuckyTopicModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.action.CreateLuckyAction.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-4
 * @describe  
 *	发表，编辑，修改 幸运帖！
 */
public class CreateLuckyAction extends AbstractBaseAction{
	private static final long serialVersionUID=32934774634343L;
	private ForumService forumService;
	private LuckyTopicModel model=new LuckyTopicModel();
	
	public String execute() throws Exception {
		model.setUser(getUser());
		try {
			forumService.createLucky(model);
			super.setOnlineStatusForumId(model.getForumId());
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String submit()throws Exception{
		model.setUser(getUser());
		try {
			forumService.createLuckyDo(model);
			super.setOnlineStatusForumId(model.getForumId());
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
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
