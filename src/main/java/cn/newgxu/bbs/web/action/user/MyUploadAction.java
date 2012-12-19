package cn.newgxu.bbs.web.action.user;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.MyUploadModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@SuppressWarnings("serial")
public class MyUploadAction extends AbstractBaseAction{

	private MyUploadModel model=new MyUploadModel();

	@Override
	public String execute() throws Exception {
		MessageList m = new MessageList();
		try{
			model.setUser(getUser());
			String size=getRequest().getParameter("size");
			if(size!=null){
				Integer s=Integer.valueOf(size);
				model.getPagination().setPageSize(s);
			}
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			userService.myUpload(model);
			return SUCCESS;
		}catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	public String test() throws Exception{
		model.setNick(getSession().getId());
		System.out.println("sesson id:"+model.getNick());
		return "swfupload";
	}
	
	public Object getModel() {
		return model;
	}
	
	
}
