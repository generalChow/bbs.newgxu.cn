package cn.newgxu.bbs.web.action.market;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.ItemSellDoModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ItemSellDoAction extends AbstractBaseAction {

	private static final long serialVersionUID = 5012780996966096781L;

	private static final Log log = LogFactory.getLog(ItemSellDoAction.class);

	private ItemSellDoModel model = new ItemSellDoModel();

	private MarketService marketService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("自由市场卖出物品");
		MessageList m = new MessageList();
		model.setUser(getUser());
		try {
			marketService.itemSellDo(model);
			m.setUrl("/market/myItems.yws");
			m.addMessage("<b>物品成功进入自由市场！销售时间为 ${day} 天！</b>", MessageList.P(
					"${day}", Constants.FREE_MARKET_SELL_DAY));
			m.addMessage("<a href='/market/myItems.yws'>去看看我的物品！</a>");
			m.addMessage("<a href='/market/official.yws'>爽！继续到官方商店购物！</a>");
			m.addMessage("<a href='/market/free.yws'>嗯...到自由市场去看看！</a>");
			Util.putMessageList(m, getSession());
			log.debug("物品成功进入自由市场！");
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
