package cn.newgxu.bbs.service.impl;

import java.util.Date;
import java.util.List;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.domain.Honor;
import cn.newgxu.bbs.domain.ManageLog;
import cn.newgxu.bbs.domain.activity.Tips;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.AdministratorService;
import cn.newgxu.bbs.web.cache.BBSCache;
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
public class AdministratorServiceImpl extends UserServiceImpl implements
		AdministratorService {

	public User login(LoginModel model) throws BBSException {

		User user = super.login(model);
		if (!(user.getGroupId() == 1 && user.getGroupTypeId() == 4)) {
			throw new BBSException(BBSExceptionMessage.MANAGE_ERROR);
		}
		return user;

	}

	public void getManageLogs(LogManageModel model) throws BBSException {
		List<ManageLog> list = null;
		if (model.getSearchValue() != null&&model.getSearchValue().trim().length()>0) {
			System.out.println(model.getPagination());
			System.out.println("搜索关键字："+model.getSearchValue()+" --> "+model.getPagination());
			if (model.getSearchType().equals("id"))
				list = ManageLog.getLogsByID(model.getPagination(), model
						.getSearchValue());
			if (model.getSearchType().equals("forum"))
				list = ManageLog.getLogsByForum(model.getPagination(), model
						.getSearchValue());
			if (model.getSearchType().equals("username"))
				list = ManageLog.getLogsByUsername(model.getPagination(), model
						.getSearchValue());
			if (model.getSearchType().equals("studentid"))
				list = ManageLog.getLogsByForum(model.getPagination(), model
						.getSearchValue());
		} else {
			list = ManageLog.getLogs(model.getPagination());
		}
		model.setLogs(list);
	}

	public void createHonor(HonorManageModel model) {
		Honor honor = new Honor();
		honor.setName(model.getName());
		honor.setType(model.getType());
		honor.setRemark(model.getRemark());
		honor.save();
	}
	public void editHonorDo(HonorManageModel model )throws BBSException{
		Honor honor=model.getHonor();
		if(honor.getId()>0)
			honor.update();
		else new BBSException("参数错误，在保存Honor时没有发现ID");
	}

	public void manageHonors(HonorManageModel model) {
		model.setHonors(Honor.getHonors());
	}

	/*----------------------------------------------------------
	 * 节日动画  集成显卡 2011-11-7
	 * ----------------------------------------------------------
	 */
	public void delHoliday(HolidayModel model) throws BBSException {
		try{
			Tips h=(Tips)Tips.getById(Tips.class, model.getId());
			h.delete();
		}catch(Exception e){}
	}
	public Tips editHoliday(HolidayModel model) throws BBSException {
		/*
		 * 判断是否有Holiday实例，同时实例id是否>0
		 * 是的话就是update这个Holiday
		 */
		if(model.getHoliday()!=null&&model.getHoliday().getId()>0){
			model.getHoliday().setAddTime(new Date());
			model.getHoliday().update();
			//刷新缓存
			BBSCache.buildTipCache();
		}
		else{
			try{
				Tips h=(Tips)Tips.getById(Tips.class, model.getId());
				model.setHoliday(h);
				return h;
			}catch(Exception e){
				throw new BBSException(e.getMessage());
			}
		}
		return null;
	}
	public Tips addHoliday(HolidayModel model) throws BBSException {
		model.getHoliday().setAddTime(new Date());
		model.getHoliday().save();
		//刷新缓存
		BBSCache.buildTipCache();
		return model.getHoliday();
	}
	public void holiday(HolidayModel model) throws BBSException {
		model.setHolidays(Tips.getHolidays(model.getPagination(), false));
	}
}
