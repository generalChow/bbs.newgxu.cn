package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.WebMastersManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class WebmastersManageAction extends AbstractBaseAction {

	private static final long serialVersionUID = -1047332308829561598L;

	private WebMastersManageModel model = new WebMastersManageModel();
	
	@Override
	public String execute() throws Exception {
		userService.getWebMasters(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
