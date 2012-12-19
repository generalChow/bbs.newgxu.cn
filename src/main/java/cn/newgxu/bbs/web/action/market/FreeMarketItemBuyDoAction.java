package cn.newgxu.bbs.web.action.market;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.FreeMarketItemBuyDoModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class FreeMarketItemBuyDoAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2875809985136178682L;

	private static final Log log = LogFactory
			.getLog(FreeMarketItemBuyDoAction.class);

	private FreeMarketItemBuyDoModel model = new FreeMarketItemBuyDoModel();

	private MarketService marketService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("自由市场购买物品中...");
		MessageList m = new MessageList();
		model.setUser(getUser());
		try {
			marketService.freeMarketItemBuyDo(model);
			m.setUrl("/market/myItems.yws");
			m.addMessage("<b>购买成功！</b>");
			m.addMessage("<a href='/market/myItems.yws'>去看看我的物品！</a>");
			m.addMessage("<a href='/market/official.yws'>到商店去看看！</a>");
			m.addMessage("<a href='/market/free.yws'>再去自由市场看看！</a>");
			Util.putMessageList(m, getSession());
			log.debug("物品购买成功！");
			return SUCCESS;
		} catch (BBSException e) {
			log.debug(e);
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
