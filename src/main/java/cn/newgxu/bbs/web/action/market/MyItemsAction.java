package cn.newgxu.bbs.web.action.market;

import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.MyItemsModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MyItemsAction extends AbstractBaseAction {

	private static final long serialVersionUID = -5800104627746453307L;

	private MyItemsModel model = new MyItemsModel();

	@Override
	public String execute() throws Exception {
		signOnlineUser("望着自己的物品偷笑-_-|||");
		model.setUser(getUser());
		model.getPagination().setActionName(super.getActionName());
		model.getPagination().setParamMap(super.getParameterMap());
		model.getPagination().setPageSize(4);
		userService.myItemsModel(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

}
