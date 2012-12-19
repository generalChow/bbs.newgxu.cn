package cn.newgxu.bbs.web.action.user;

import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.UserFavoriteTopic;

/**
 * 添加收藏Action
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ManageFavoriteTopicAction extends AbstractBaseAction {

	private static final long serialVersionUID = 533446682586715876L;

	private UserFavoriteTopic model = new UserFavoriteTopic();;

	public String execute() throws Exception {
		signOnlineUser("收藏中...");
		model.setUser(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		userService.getFavoriteTopics(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
