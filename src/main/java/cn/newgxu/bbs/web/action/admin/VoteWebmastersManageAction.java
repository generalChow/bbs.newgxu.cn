package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.WebMastersManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class VoteWebmastersManageAction extends AbstractBaseAction {

	private static final long serialVersionUID = -1047332308829561598L;

	private WebMastersManageModel model = new WebMastersManageModel();

	@Override
	public String execute() {
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			userService.getVoteWebMasters(model);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			m.addMessage(e.getMessage()+"<br /><br />这个因为数据库表已经不存在了，这个功能也就没有了哈！");
			Util.putMessageList(m, getSession());
		}
		return ERROR;
	}

	public String freshWebMasters() {
		userService.freshWebMasters(model);
		execute();
		return SUCCESS;
	}

	public String resetVote() throws Exception {
		userService.resetVote();
		userService.getVoteWebMasters(model);
		return SUCCESS;
	}

	public String deleteVoteWebMaster() throws Exception {
		userService.deleteVoteWebMaster(model);
		userService.getVoteWebMasters(model);
		return SUCCESS;
	}

	public String addVoteWebMaster() {
		userService.addVoteWebMaster(model);
		execute();
		return SUCCESS;
	}

	public String VoteDo() {
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			userService.VoteWebMasterDo(model);
			m.setUrl("/webMaster/webMasterVote.yws");
			m.addMessage("<b> 投票成功！</b>");
			m.addMessage("<a href='/webMaster/webMasterVote.yws'>查看票数</a>");
			m.addMessage("<a href='/index.yws'>返回主页</a>");
			Util.putMessageList(m, getSession());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
		}
		return ERROR;
	}

	public Object getModel() {
		return model;
	}

}
