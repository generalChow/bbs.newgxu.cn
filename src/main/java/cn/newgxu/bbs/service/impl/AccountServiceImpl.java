package cn.newgxu.bbs.service.impl;

import cn.newgxu.bbs.service.AccountService;
import cn.newgxu.bbs.web.model.user.UpgradeModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AccountServiceImpl implements AccountService {

	public void leavelUp(UpgradeModel model) {
		model.setUserGroup(model.getUser().getUserGroup());
		model.setNextUserGroup(model.getUserGroup().getNextGroup());
		model.getUserGroup().leavelUp(model.getUser());
	}

	public void getNextGroup(UpgradeModel model) {
		model.setUserGroup(model.getUser().getUserGroup());
		model.setNextUserGroup(model.getUserGroup().getNextGroup());
	}

}
