package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.ItemManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateItemAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private ItemManageModel model = new ItemManageModel();

	private MarketService marketService;

	public String execute() throws Exception {
		marketService.getItemTypes(model);
		return SUCCESS;
	}

	public String doCreateItem() throws Exception {
		try {
			marketService.createItem(model);
			response("{\"statusCode\":\"200\", \"message\":\"物品创建成功\", \"navTabId\":\"item\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"items.yws\"}");
			return null;
			/*
			MessageList m = new MessageList();
			marketService.createItem(model);
			m.setUrl("#");
			m.addMessage("<b>创建成功！</b>");
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
