package cn.newgxu.bbs.web.action.market;

import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.OfficialModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OfficialAction extends AbstractBaseAction {

	private static final long serialVersionUID = 6654340864485423724L;

	private MarketService marketService;

	private OfficialModel model = new OfficialModel();

	@Override
	public String execute() throws Exception {
		signOnlineUser("社区商店闲逛");
		model.getPagination().setActionName(super.getActionName());
		model.getPagination().setParamMap(super.getParameterMap());
		model.getPagination().setPageSize(6);
		marketService.getStoreItems(model);
		return SUCCESS;
	}

	public Object getModel() {
		return model;
	}

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

}
