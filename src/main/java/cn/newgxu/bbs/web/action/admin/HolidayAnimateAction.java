package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.activity.Tips;
import cn.newgxu.bbs.service.AdministratorService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.HolidayModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.action.admin.HolidayAnimateAction.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-11-7
 * @describe  
 *	首页动画效果。
 *	这里可以管理全部的节日，添加/删除/编辑节日等。
 *	这样，就不必技术人员管理，管理员可以直接上传相应的图片添加一个动画效果
 */
public class HolidayAnimateAction extends AbstractBaseAction{
	private static final long serialVersionUID=82319372847343L;
	
	private HolidayModel model=new HolidayModel();
	private AdministratorService administratorService;
	
	public String execute() throws Exception {
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		administratorService.holiday(model);
		return SUCCESS;
	}
	
	public String add(){
		try{
			Tips h=new Tips();
			h.setId(0);
			model.setHoliday(h);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	public String addDo(){
		try{
			administratorService.addHoliday(model);
			response("{\"statusCode\":\"200\", \"message\":\"节日添加成功\", \"navTabId\":\"holiday\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"holiday.yws\"}");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			MessageList m = new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	public String edit(){
		try{
			Tips h=administratorService.editHoliday(model);
			//h为null 时是保存编辑，不为null时是申请编辑
			if(h==null){
				response("{\"statusCode\":\"200\", \"message\":\"节日保存成功\", \"navTabId\":\"holiday\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"holiday.yws\"}");
				return null;
			}else{
				return SUCCESS;
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String del()throws Exception{
		try{
			administratorService.delHoliday(model);
			response("{\"statusCode\":\"200\", \"message\":\"节日删除成功\", \"navTabId\":\"holiday\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"holiday.yws\"}");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	public Object getModel() {
		return model;
	}
}
