package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.domain.StoreItem;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.ItemManageModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditStoreItemAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2580867848781474L;

	private MarketService marketService;
	private ItemManageModel model = new ItemManageModel();

	@Override
	public String execute() {
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		try {
			StoreItem storeItem = StoreItem.getByItemId(model.getId());
			model.setId(storeItem.getId());
			model.setName(storeItem.getItem().getName());
			model.setNumber(storeItem.getNumber());
			model.setUnitPrice(storeItem.getUnitPrice());
			// marketService.getItem(model);
			return SUCCESS;
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String doEditStoreItem() {
		try {
			marketService.editStoreItem(model);
			response("{\"statusCode\":\"200\", \"message\":\"物品保存成功\", \"navTabId\":\"item_store\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"store_items.yws\"}");
			return null;
			/*
			MessageList m = new MessageList();
			m.setUrl("#");
			m.addMessage("<b>更改成功！</b>");
			Util.putMessageList(m, getSession());
			return SUCCESS;*/
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

}
