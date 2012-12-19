package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.HongbaoService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.hongbao.HongbaoManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HongbaoManageAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private HongbaoManageModel model = new HongbaoManageModel();

	private HongbaoService hongbaoService;

	public String execute() throws Exception {
		hongbaoService.manageHongbao(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setHongbaoService(HongbaoService hongbaoService) {
		this.hongbaoService = hongbaoService;
	}

	public HongbaoService getHongbaoService() {
		return hongbaoService;
	}


}
