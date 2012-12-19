package cn.newgxu.bbs.web.action.market;

import cn.newgxu.bbs.domain.market.FreeMarketItem;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.FreeModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class FreeAction extends AbstractBaseAction {

	private static final long serialVersionUID = 7988507098702653959L;

	private FreeModel model = new FreeModel();

	private MarketService marketService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("自由市场迷路中...");
		model.setUser(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		model.getPagination().setPageSize(4);
		model.getPagination().setRecordSize(FreeMarketItem.getNumberOfItems());
		marketService.freeMarket(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

}
