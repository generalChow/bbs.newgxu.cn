package cn.newgxu.bbs.service;

import java.util.List;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.calendar.FixHoliday;
import cn.newgxu.bbs.domain.calendar.UserHoliday;
import cn.newgxu.bbs.web.model.ycalendar.EditCalendarModel;
import cn.newgxu.bbs.web.model.ycalendar.IndexCalendarModel;
import cn.newgxu.bbs.web.model.ycalendar.ListHolidayModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.service.YCalendarService.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-18
 * @describe  
 *	雨无声日历的service接口
 */public interface YCalendarService {

	/**
	 * 返回指定year和month的monthUnit对象，这个对象中包含了一个月中的天值。
	 * @param year
	 * @param month
	 */
	public void getMonthUnit(IndexCalendarModel model);
	
	/**
	 * 得到在今天之后的一段时间内的fixHoliday，monthStep指定了多少个月的时间差
	 * @param monthStep  这个参数可有可无，没有的话就使用默认的  MONTHSTEP
	 * @return
	 */
	public List<FixHoliday> getComingFixHoliday(Integer ...monthStep);
	
	/**
	 * 得到今天临近的用户节日列表，判断条件是在今天前后的monthStep个月内，同时这个用户节日必须是  overt=0 ，即是全公开的
	 * @param monthStep  这个参数可有可无，没有的话就使用默认的  MONTHSTEP
	 * @return
	 */
	public List<UserHoliday> getNearbyUserHoliday(Integer ...monthStep);
	
	/**
	 * 添加新的holiday
	 * @param model
	 */
	public void createUserHolidayDo(EditCalendarModel model) throws BBSException;
	
	public void editUserHoliday(EditCalendarModel model)throws BBSException;
	
	public void editUserHolidayDo(EditCalendarModel model)throws BBSException;
	
	public void deleteUserHoliday(EditCalendarModel model)throws BBSException;
	
	/*
	********************************************************************
	 * 查看节日，包括单一和列表
	 * *******************************************************************
	 */
	/**
	 * 查看一个holiday，从model中判断是哪种节日
	 */
	public String viewHoliday(ListHolidayModel model) throws BBSException;
	/**
	 * 用户查看自己的节日列表
	 * @param model
	 * @throws BBSException
	 */
	public void viewMyHoliday(ListHolidayModel model) throws BBSException;
}
