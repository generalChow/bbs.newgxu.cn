package cn.newgxu.bbs.web.model.ycalendar;

import java.util.List;

import cn.newgxu.bbs.domain.calendar.FixHoliday;
import cn.newgxu.bbs.domain.calendar.UserHoliday;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.ycalendar.ListHolidayModel.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-25
 * @describe  
 *	节日列表model
 */
public class ListHolidayModel extends PaginationBaseModel{

	private int uhid;//用户节日的id
	private int fhid;//固定节日的id
	private int userId;//用户id，对应了是查看自己的节日中的user
	private boolean control;//是否是查看自己的节日
	private String actionUrl="listUserHoliday";//action路径,这个决定了是什么url

	private String title;
	
	private UserHoliday userHoliday;
	private FixHoliday fixHoliday;
	private List<UserHoliday> userHolidays;
	private List<FixHoliday> fixHolidays;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isControl() {
		return control;
	}
	public void setControl(boolean control) {
		this.control = control;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public int getUhid() {
		return uhid;
	}
	public void setUhid(int uhid) {
		this.uhid = uhid;
	}
	public int getFhid() {
		return fhid;
	}
	public void setFhid(int fhid) {
		this.fhid = fhid;
	}
	public UserHoliday getUserHoliday() {
		return userHoliday;
	}
	public void setUserHoliday(UserHoliday userHoliday) {
		this.userHoliday = userHoliday;
	}
	public FixHoliday getFixHoliday() {
		return fixHoliday;
	}
	public void setFixHoliday(FixHoliday fixHoliday) {
		this.fixHoliday = fixHoliday;
	}
	public List<UserHoliday> getUserHolidays() {
		return userHolidays;
	}
	public void setUserHolidays(List<UserHoliday> userHolidays) {
		this.userHolidays = userHolidays;
	}
	public List<FixHoliday> getFixHolidays() {
		return fixHolidays;
	}
	public void setFixHolidays(List<FixHoliday> fixHolidays) {
		this.fixHolidays = fixHolidays;
	}
}
