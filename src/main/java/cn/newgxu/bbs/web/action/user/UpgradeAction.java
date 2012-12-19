package cn.newgxu.bbs.web.action.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.service.AccountService;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.UpgradeModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class UpgradeAction extends AbstractBaseAction {

	private static final long serialVersionUID = 533446682586715876L;

	private static final Log log = LogFactory.getLog(UpgradeAction.class);

	private AccountService accountService;
	
	private ForumService forumService;

	private UpgradeModel model = new UpgradeModel();

	public String execute() throws Exception {
		signOnlineUser("升级中心");
		model.setAreas(this.forumService.getAreas());
		model.setUser(getUser());
		accountService.getNextGroup(model);
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("升级中...");
		model.setAreas(this.forumService.getAreas());
		model.setUser(getUser());
		accountService.leavelUp(model);
		execute();
		log.debug("升级成功！");
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

}
