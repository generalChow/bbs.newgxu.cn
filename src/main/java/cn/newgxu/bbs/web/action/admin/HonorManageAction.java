package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.AdministratorService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.HonorManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HonorManageAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private HonorManageModel model = new HonorManageModel();

	private AdministratorService administratorService;

	public String execute() throws Exception {
		administratorService.manageHonors(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}


}
