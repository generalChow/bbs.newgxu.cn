package cn.newgxu.bbs.web.action;

import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.IndexModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DetTopicAction extends AbstractBaseAction {

	private static final long serialVersionUID = -4853741132558481049L;

	private IndexModel model = new IndexModel();

	private ForumService forumService;

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public String execute() throws Exception {
		signOnlineUser("从首页查看，论坛精彩推存");
		model.setPubGoodTopics(forumService.getTopics(2, 10));
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}
}
