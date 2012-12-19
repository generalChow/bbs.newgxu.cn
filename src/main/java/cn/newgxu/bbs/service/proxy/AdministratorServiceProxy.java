package cn.newgxu.bbs.service.proxy;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.activity.Tips;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.AdministratorService;
import cn.newgxu.bbs.web.model.accounts.LoginModel;
import cn.newgxu.bbs.web.model.admin.HolidayModel;
import cn.newgxu.bbs.web.model.admin.HonorManageModel;
import cn.newgxu.bbs.web.model.admin.LogManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */


public class AdministratorServiceProxy implements AdministratorService {
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}

	public User login(LoginModel model) throws BBSException {
		return administratorService.login(model);
	}

	public void getManageLogs(LogManageModel model) throws BBSException {
		this.administratorService.getManageLogs(model);
	}

	public void createHonor(HonorManageModel model) {
		administratorService.createHonor(model);
	}

	public void manageHonors(HonorManageModel model) {
		administratorService.manageHonors(model);
	}

	public void editHonorDo(HonorManageModel model) throws BBSException {
		administratorService.editHonorDo(model);
	}

	public void delHoliday(HolidayModel model) throws BBSException {
		administratorService.delHoliday(model);
	}
	public Tips editHoliday(HolidayModel model) throws BBSException {
		return administratorService.editHoliday(model);
	}
	public void holiday(HolidayModel model) throws BBSException {
		administratorService.holiday(model);
	}
	public Tips addHoliday(HolidayModel model)throws BBSException{
		return administratorService.addHoliday(model);
	}
}
