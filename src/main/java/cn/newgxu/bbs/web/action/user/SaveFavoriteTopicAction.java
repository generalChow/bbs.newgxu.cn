package cn.newgxu.bbs.web.action.user;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.UserFavoriteTopic;

/**
 * 添加收藏Action
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SaveFavoriteTopicAction extends AbstractBaseAction {

	private static final long serialVersionUID = 533446682586715876L;

	private UserFavoriteTopic model = new UserFavoriteTopic();

	public String execute() throws Exception {
		signOnlineUser("收藏中...");
		model.setUser(getUser());
		userService.saveFavoriteTopic(model);
		MessageList m = new MessageList();
		m.addMessage("<b>新回复发表成功！</b>");
		Util.putMessageList(m, getSession());
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
