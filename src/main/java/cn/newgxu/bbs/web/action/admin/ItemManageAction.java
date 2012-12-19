package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemType;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.ItemManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ItemManageAction extends AbstractBaseAction{
	private static final long serialVersionUID = -1047332308829561598L;

	private MarketService marketService;
	private ItemManageModel model = new ItemManageModel();
	
	@Override
	public String execute() throws Exception {
		marketService.getItems(model);
		return SUCCESS;
	}
	
	public String doGetItemInfo() throws Exception {
		Item item=Item.get(model.getId());
		model.setEffect(item.getEffect());
		model.setName(item.getName());
		model.setItem_type_id(item.getType().getId());
		model.setObjectBadboy(item.getObjectBadboy());
		model.setObjectExp(item.getObjectExp());
		model.setObjectPower(item.getObjectPower());
		model.setSelfMoney(item.getSelfMoney());
		model.setSelfPower(item.getSelfPower());
		model.setItemTypes(ItemType.getItemTypes());
		
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}
}
