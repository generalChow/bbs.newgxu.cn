package cn.newgxu.bbs.web.action.user;

import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.UploadFaceModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class UploadFaceAction extends AbstractBaseAction {

	private static final long serialVersionUID = -2213569849255802553L;

	private UploadFaceModel model = new UploadFaceModel();

	@Override
	public String execute() throws Exception {
		signOnlineUser("上传头像中...");
		model.setUser(getUser());
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
