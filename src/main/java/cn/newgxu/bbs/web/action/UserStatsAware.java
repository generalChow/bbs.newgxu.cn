package cn.newgxu.bbs.web.action;

import cn.newgxu.bbs.service.UserService;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface UserStatsAware {
	
	public void setUserService(UserService userService);

}
