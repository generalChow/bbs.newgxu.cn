package cn.newgxu.bbs.web.model.admin;

import java.util.ArrayList;
import java.util.List;

import cn.newgxu.bbs.domain.activity.Tips;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.admin.HolidayModel.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-11-7
 * @describe  
 *
 */
public class HolidayModel extends PaginationBaseModel{
	private List<Tips> holidays=new ArrayList<Tips>();
	private Tips tips;
	private int id;
	
	public List<Tips> getHolidays() {
		return holidays;
	}
	public void setHolidays(List<Tips> holidays) {
		this.holidays = holidays;
	}
	public Tips getHoliday() {
		return tips;
	}
	public void setHoliday(Tips tips) {
		this.tips = tips;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
