package cn.newgxu.bbs.service.proxy;

import cn.newgxu.bbs.service.AccountService;
import cn.newgxu.bbs.web.model.user.UpgradeModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AccountServiceProxy implements AccountService{
	
	private AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void leavelUp(UpgradeModel model) {
		accountService.leavelUp(model);
	}

	public void getNextGroup(UpgradeModel model) {
		accountService.getNextGroup(model);
	}

}
