package cn.newgxu.bbs.web.action.user;

import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.GetOnlineUsersModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class GetOnlineUsersAction extends AbstractBaseAction {

	private static final long serialVersionUID = 8895141830126528310L;

	private GetOnlineUsersModel model = new GetOnlineUsersModel();

	public String execute() throws Exception {
		signOnlineUser("<a href=\"/user/online_users.yws\">查看在线用户列表</a>");
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		model.setOnlineUsers(userService.getOnlineUsers(model.getType(), model
				.getPagination()));
		return SUCCESS;
	}

	public String forumOnlineUsers() throws Exception {
		signOnlineUser("<a href=\"/user/online_users.yws\">当前板块在线用户列表</a>");
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		model.setOnlineUsers(userService.getOnlineForumUsers(model.getType(),
				model.getForumId(), model.getPagination()));
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
