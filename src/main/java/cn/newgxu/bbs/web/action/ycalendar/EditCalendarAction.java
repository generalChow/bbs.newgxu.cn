package cn.newgxu.bbs.web.action.ycalendar;

import javax.annotation.Resource;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.calendar.UserHoliday;
import cn.newgxu.bbs.service.YCalendarService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.ycalendar.EditCalendarModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.action.ycalendar.EditCalendarAction.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-20
 * @describe  
 *
 */
@SuppressWarnings("serial")
public class EditCalendarAction extends AbstractBaseAction{
	
	@Resource(name="yCalendarService")
	private YCalendarService yCalendarService;
	private EditCalendarModel model=new EditCalendarModel();
	
	public String execute() {
		MessageList m = new MessageList();
		try{
			if(model.getMethod().equals("create")){
				model.setHoliday(new UserHoliday());
				System.out.println("saasas");
				return "create";
			}
			//确定提交
			else if(model.getMethod().equals("createDo")){
				try{
					yCalendarService.createUserHolidayDo(model);
					m.addMessage("<b>新节日发表成功！</b>");
					Util.putMessageList(m, getSession());
				}catch(BBSException be){
					be.printStackTrace();
					return INPUT;
				}
				return SUCCESS;
			}
			return ERROR;
		}catch(Exception e){
			e.printStackTrace();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	public String edit(){
		try{
			if(model.getMethod().equals("edit")){
				System.out.println("saasas");
				return "edit";
			}
			//确定提交
			else if(model.getMethod().equals("editDo")){
				
				return SUCCESS;
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			model.setMessage(e.getMessage());
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

}
