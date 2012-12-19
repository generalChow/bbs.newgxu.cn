package cn.newgxu.bbs.service;

import cn.newgxu.bbs.web.model.user.UpgradeModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface AccountService {
	public void leavelUp(UpgradeModel model);
	public void getNextGroup(UpgradeModel model);
}
