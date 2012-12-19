package cn.newgxu.bbs.web.action.market;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.OfficialBuyDoModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OfficialBuyDoAction extends AbstractBaseAction {

	private static final long serialVersionUID = 610871581495315986L;

	private static final Log log = LogFactory.getLog(OfficialBuyDoAction.class);

	private OfficialBuyDoModel model = new OfficialBuyDoModel();

	private MarketService marketService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("社区商店付款中...");
		MessageList m = new MessageList();
		model.setUser(getUser());
		try {
			marketService.officialBuyDo(model);
			m.setUrl("/market/myItems.yws");
			m.addMessage("<b>成功购买到 ${number} 件物品！</b>", MessageList.P(
					"${number}", model.getNumber()));
			m.addMessage("<a href='/market/myItems.yws'>去看看我的物品！</a>");
			m.addMessage("<a href='/market/official.yws'>爽！继续到官方商店购物！</a>");
			m.addMessage("<a href='/market/free.yws'>嗯...到自由市场去看看！</a>");
			Util.putMessageList(m, getSession());
			log.debug("交易成功！");
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
