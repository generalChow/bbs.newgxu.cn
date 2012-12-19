package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemType;
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
public class EditItemAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2580867848781474L;

	private MarketService marketService;
	private ItemManageModel model = new ItemManageModel();

	@Override
	public String execute() {
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		try {
			Item item = Item.get(model.getId());
			model.setEffect(item.getEffect());
			model.setName(item.getName());
			model.setItem_type_id(item.getType().getId());
			model.setObjectBadboy(item.getObjectBadboy());
			model.setObjectExp(item.getObjectExp());
			model.setObjectPower(item.getObjectPower());
			model.setSelfMoney(item.getSelfMoney());
			model.setSelfPower(item.getSelfPower());
			model.setReturnId(item.getReturnId());
			model.setItemTypes(ItemType.getItemTypes());
			model.setMessage(item.getMessage());
			model.setMessage_title(item.getMessage_title());
			// marketService.getItem(model);
			return SUCCESS;
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String doEditItem() {
		
		try {
			marketService.editItem(model);
			response("{\"statusCode\":\"200\", \"message\":\"物品保存成功\", \"navTabId\":\"item\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"items.yws\"}");
			return null;
			/*
			m.setUrl("#");
			m.addMessage("<b>更改成功！</b>");
			Util.putMessageList(m, getSession());
			return SUCCESS;*/
		} catch (Exception e) {
			e.printStackTrace();
			MessageList m = new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
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
