package cn.newgxu.bbs.service.proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.ValidationUtil;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.model.admin.ItemManageModel;
import cn.newgxu.bbs.web.model.market.FreeMarketItemBuyDoModel;
import cn.newgxu.bbs.web.model.market.FreeModel;
import cn.newgxu.bbs.web.model.market.ItemSellDoModel;
import cn.newgxu.bbs.web.model.market.OfficialBuyDoModel;
import cn.newgxu.bbs.web.model.market.OfficialModel;
import cn.newgxu.bbs.web.model.market.OperateItemLogModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MarketServiceProxy implements MarketService {

	private static final Log log = LogFactory.getLog(MarketServiceProxy.class);

	private MarketService marketService;

	public void getStoreItems(OfficialModel model) {
		marketService.getStoreItems(model);

	}

	public void officialBuyDo(OfficialBuyDoModel model) throws BBSException,
			ValidationException {
		ValidationUtil.storeItemNumber(model.getNumber());
		marketService.officialBuyDo(model);
	}

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	public void itemSellDo(ItemSellDoModel model) throws BBSException,
			ValidationException {
		ValidationUtil.freeMarketSellPrice(model.getPrice());

		this.marketService.itemSellDo(model);
	}

	public void freeMarket(FreeModel model) {
		// 如果是查看所有人贩卖的物品 且 itemId!=0 则说明是查看指定某样物品
		// 那么就需要检查这项物品是否真的存在。
		if (model.isViewAllUsersItems() && model.getCategoryId() != 0) {
			try {
				Item.get(model.getCategoryId());
			} catch (ObjectNotFoundException e) {
				if (log.isDebugEnabled()) {
					log.debug("Set item id to 0");
				}
				model.setCategoryId(0);
			}
		}
		this.marketService.freeMarket(model);
	}

	public void freeMarketItemBuyDo(FreeMarketItemBuyDoModel model)
			throws BBSException {
		this.marketService.freeMarketItemBuyDo(model);
	}

	public void cleanFreeMarketItem() {
		marketService.cleanFreeMarketItem();
	}

	public void createItem(ItemManageModel model)
			throws ObjectNotFoundException {
		marketService.createItem(model);
	}

	public void getItemTypes(ItemManageModel model) {
		marketService.getItemTypes(model);
	}

	public void getItems(ItemManageModel model) {
		marketService.getItems(model);
	}

	public void editItem(ItemManageModel model) throws ObjectNotFoundException {
		marketService.editItem(model);
	}

	public void getItem(ItemManageModel model) throws ObjectNotFoundException {
		marketService.getItem(model);
	}

	public void editStoreItem(ItemManageModel model)
			throws ObjectNotFoundException {
		marketService.editStoreItem(model);
	}

	public void operateItemLog(OperateItemLogModel model) throws BBSException {
		marketService.operateItemLog(model);
	}

}
