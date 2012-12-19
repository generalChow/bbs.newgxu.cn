package cn.newgxu.bbs.service;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.activity.Tips;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.accounts.LoginModel;
import cn.newgxu.bbs.web.model.admin.HolidayModel;
import cn.newgxu.bbs.web.model.admin.HonorManageModel;
import cn.newgxu.bbs.web.model.admin.LogManageModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.service.AdministratorService
 * 
 * @author 集成显卡
 * @since 5.0
 * @version $Revision 1.1$
 * @date 2011-11-1
 * @describe  
 *	管理员  service 接口。添加新的方法请注意分类
 */
public interface AdministratorService {

	public User login(LoginModel model) throws BBSException;
	
	public void getManageLogs(LogManageModel model) throws BBSException;

	/*-----------------------------------------------------------
	 * 荣誉
	 * -----------------------------------------------------------
	 */
	public void createHonor(HonorManageModel model);
	public void editHonorDo(HonorManageModel model) throws BBSException;
	
	public void manageHonors(HonorManageModel model);
	
	/*----------------------------------------------------------
	 * 节日动画  集成显卡 2011-11-7
	 * ----------------------------------------------------------
	 */
	public void holiday(HolidayModel model)throws BBSException;
	public Tips editHoliday(HolidayModel model)throws BBSException;
	public void delHoliday(HolidayModel model)throws BBSException;
	public Tips addHoliday(HolidayModel model)throws BBSException;
}
