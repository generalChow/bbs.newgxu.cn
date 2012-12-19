package cn.newgxu.bbs.web.action;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.BoardStateModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BoardStateAction extends AbstractBaseAction {

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8945389937579623781L;

	private BoardStateModel model = new BoardStateModel();

	private ForumService forumService;

	@Override
	public String execute() throws Exception {
		try {
			model.setAreas(forumService.getAreas());
			forumService.BoardState(model);
			return SUCCESS;
		} catch (BBSException e) {
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
