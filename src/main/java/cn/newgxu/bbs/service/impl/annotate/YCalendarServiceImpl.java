package cn.newgxu.bbs.service.impl.annotate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.domain.calendar.FixHoliday;
import cn.newgxu.bbs.domain.calendar.MonthUnit;
import cn.newgxu.bbs.domain.calendar.UserHoliday;
import cn.newgxu.bbs.domain.calendar.YWSCalendar;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.YCalendarService;
import cn.newgxu.bbs.web.model.ycalendar.EditCalendarModel;
import cn.newgxu.bbs.web.model.ycalendar.IndexCalendarModel;
import cn.newgxu.bbs.web.model.ycalendar.ListHolidayModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @path valhalla_hx----cn.newgxu.bbs.service.impl.YCalendarServiceImpl.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-18
 * @describe  
 *	YCalendarService的实现类
 */
@Service("yCalendarService")
public class YCalendarServiceImpl implements YCalendarService{
	public Integer getMonthStep(Integer ...monthStep){
		if(monthStep.length>1)
			return monthStep[0]>0?monthStep[0]:YWSCalendar.MONTH_STEP;
		return YWSCalendar.MONTH_STEP;
	}
	
	public void getMonthUnit(IndexCalendarModel model) {
		if(model.getYear()<=0)
			model.setYear(Calendar.getInstance().get(Calendar.YEAR));
		if(model.getMonth()<1||model.getMonth()>12)
			model.setMonth(Calendar.getInstance().get(Calendar.MONTH)+1);
		if(model.getDay()==0)
			model.setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		model.setMonthUnit(new MonthUnit(model.getYear(),model.getMonth()));
	}

	/**
	 * 检查节日是否是合格
	 * @param day
	 */
	public UserHoliday checkUserHoliday(UserHoliday day) throws BBSException{
		if(day==null)
			throw new BBSException("对象为空！不能创建相应的UserHoliday，请联系技术人员");
		if(day.getName()==null||day.getName().trim().length()==0)
			throw new BBSException("节日名称必须填写");
		if(day.getDate()==null||day.getDate().trim().length()==0)
			throw new BBSException("节日日期必须填写，且一定要是一个有效的日期");
		day.setDate(YWSCalendar.resetDateInfo(day.getDate()));
		if(day.getOvert()<0)
			day.setOvert(0);
		return day;
	}
	
	public List<FixHoliday> getComingFixHoliday(Integer... monthStep) {
		return FixHoliday.getComingHoliday(this.getMonthStep(monthStep));
	}

	public List<UserHoliday> getNearbyUserHoliday(Integer... monthStep) {
		return UserHoliday.getNearbyHoliday(this.getMonthStep(monthStep));
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void createUserHolidayDo(EditCalendarModel model) throws BBSException{
		this.checkUserHoliday(model.getHoliday());
		model.getHoliday().setJoinTime(new Date());
		try{
			model.getHoliday().setUser(User.get(70797));
		}catch(ObjectNotFoundException e){
			e.printStackTrace();
		}
		//day.getUser().addMoney(0-1000);
		model.getHoliday().save();
	}

	public void deleteUserHoliday(EditCalendarModel model)throws BBSException {
		
	}

	public void editUserHoliday(EditCalendarModel model) throws BBSException{
	}

	public void editUserHolidayDo(EditCalendarModel model) throws BBSException{
		
	}

	public String viewHoliday(ListHolidayModel model) throws BBSException {
		try{
			if(model.getUhid()!=0){
				model.setUserHoliday(UserHoliday.getById(model.getUhid()));
				model.setTitle("查看用户节日--"+model.getUserHoliday().getName());
				return "userH";
			}else if(model.getFhid()!=0){
				model.setFixHoliday(FixHoliday.getById(model.getFhid()));
				model.setTitle("查看固定节日--"+model.getFixHoliday().getName());
				return "fixH";
			}
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}catch(ObjectNotFoundException e){
			e.printStackTrace();
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
	}

	public void viewMyHoliday(ListHolidayModel model) throws BBSException {
		model.setTitle("用户节日列表");
		model.getPagination().setPageSize(UserHoliday.getUserHolidaySize(model.getUserId()));
		model.setUserHolidays(UserHoliday.getUserHoliday(model.getUserId(), model.getPagination()));
	}
}
