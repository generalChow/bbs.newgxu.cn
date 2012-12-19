package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Honor;
import cn.newgxu.bbs.service.AdministratorService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.HonorManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateHonorAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private HonorManageModel model = new HonorManageModel();

	private AdministratorService administratorService;

	public String execute() throws Exception {
		return SUCCESS;
	}

	public String doHonorCreate() throws Exception {
		administratorService.createHonor(model);
		response("{\"statusCode\":\"200\", \"message\":\"荣誉创建成功\", \"navTabId\":\"honor\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"honor.yws\"}");
		return null;
		/*
		MessageList m = new MessageList();
		m.setUrl("#");
		m.addMessage("<b>创建成功！</b>");
		Util.putMessageList(m, getSession());
		return SUCCESS;
		*/
	}
	
	public String editHonor()throws Exception{
		try{
			Honor honor=(Honor)Honor.getById(Honor.class,model.getId());
			model.setHonor(honor);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			MessageList m = new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String editHonorDo()throws Exception{
		try{
			administratorService.editHonorDo(model);
			response("{\"statusCode\":\"200\", \"message\":\"荣誉编辑成功\", \"navTabId\":\"honor\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"honor.yws\"}");
			return null;
		}catch(Exception e){
			MessageList m = new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	public Object getModel() {
		return model;
	}

	public void setAdministratorService(
			AdministratorService administratorService) {
		this.administratorService = administratorService;
	}

}
