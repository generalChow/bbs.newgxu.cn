package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.AdministratorService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.LogManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ManageLogManageAction extends AbstractBaseAction {

	private static final long serialVersionUID = -1047332308829561598L;

	private LogManageModel model = new LogManageModel();

	private AdministratorService administratorService;

	@Override
	public String execute() throws Exception {
		try{
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			model.getPagination().setPage(model.getPage());
			administratorService.getManageLogs(model);
			if(model.getSearchType()==null) model.setSearchType("id");
			if(model.getSearchValue()==null) model.setSearchValue("");
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}

	
}
