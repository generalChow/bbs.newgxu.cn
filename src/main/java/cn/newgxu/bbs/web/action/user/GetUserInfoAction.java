package cn.newgxu.bbs.web.action.user;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.GetUserInfoModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class GetUserInfoAction extends AbstractBaseAction {

	private static final long serialVersionUID = 4167008558855341376L;
	private GetUserInfoModel model = new GetUserInfoModel();

	public String execute() throws Exception {
		signOnlineUser("查看用户信息");
		try {
			if (model.getId() > 0) {
				model.setPageUser(userService.getUser(model.getId()));
			} else {
				model.setPageUser(userService.getUser(model.getNick()));
			}
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

}
