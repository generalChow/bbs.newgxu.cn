package cn.newgxu.bbs.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.StoreItem;
import cn.newgxu.bbs.domain.market.FreeMarketBusinessLog;
import cn.newgxu.bbs.domain.market.FreeMarketItem;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.market.ItemType;
import cn.newgxu.bbs.domain.market.ItemUsedLog;
import cn.newgxu.bbs.domain.message.Message;
import cn.newgxu.bbs.domain.user.User;
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
public class MarketServiceImpl implements MarketService {

	private static final Log log = LogFactory.getLog(MarketServiceImpl.class);

	public void getStoreItems(OfficialModel model) {
		model.getPagination().setRecordSize(StoreItem.getNumberOfStoreItems());
		model.setItems(StoreItem.getStoreItems(model.getPagination()));
		if (log.isDebugEnabled()) {
			log.debug(StoreItem.getStoreItems(model.getPagination()));
		}
	}

	public void officialBuyDo(OfficialBuyDoModel model) throws BBSException {
		StoreItem si;
		try {
			si = StoreItem.get(model.getId());
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

		si.sell(model.getNumber());

		payForStoreItem(model.getUser(), model.getNumber(), si.getUnitPrice());

		for (int i = 0; i < model.getNumber(); i++) {
			ItemLine itemLine = new ItemLine();
			itemLine.setUser(model.getUser());
			itemLine.setItem(si.getItem());
			itemLine.save();
		}
	}

	private void payForStoreItem(User user, int number, int unitPrice)
			throws BBSException {
		// int total = (int) ((double) (number * unitPrice) * user.getAgio());
		int total = (int) ((double) (number * unitPrice));
		user.payFor(total);

	}

	public void itemSellDo(ItemSellDoModel model) throws BBSException {
		if (log.isDebugEnabled()) {
			log.debug("itemLine id=" + model.getId());
		}
		try {
			model.getUser().sellItem(ItemLine.get(model.getId()),
					model.getPrice());
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

	}

	public void freeMarket(FreeModel model) {
		model.setCategory(Item.getItems());

		// 查看自己卖的所有物品
		if (model.isViewMyItems()) {
			model.setItems(FreeMarketItem.getItemsByUser(model.getUser(), model
					.getPagination()));
			model.getPagination().setRecordSize(
					FreeMarketItem.getNumberOfItemsByUser(model.getUser()));
		}
		// 查看所有人卖的物品
		else {
			// 查看所有物品
			if (model.getCategoryId() == 0) {
				model.setItems(FreeMarketItem.getItems(model.getPagination()));
				model.getPagination().setRecordSize(
						FreeMarketItem.getNumberOfItems());
			}
			// 查看指定物品
			else {
				try {
					Item item = Item.get(model.getCategoryId());
					model.setItems(FreeMarketItem.getItemsByItem(item, model
							.getPagination()));
					model.getPagination().setRecordSize(
							FreeMarketItem.getNumberOfItemsByItem(item));
				} catch (ObjectNotFoundException e) {
					if (log.isDebugEnabled()) {
						log.error(e);
						log
								.error("严重错误！请检查"
										+ "MarketServiceProxy.freeMarket(FreeModel model)");
					}
					model.setItems(new LinkedList<FreeMarketItem>());
				}
			}
		}
	}

	public void freeMarketItemBuyDo(FreeMarketItemBuyDoModel model)
			throws BBSException {
		FreeMarketItem freeMarketItem = null;
		try {
			freeMarketItem = FreeMarketItem.get(model.getId());
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

		int price = freeMarketItem.getUnitPrice();
		int tax = Util.reckonTax(price, Constants.FREE_MARKET_BUSINESS_TAX);
		int income = Util.reckonAfterTax(price,
				Constants.FREE_MARKET_BUSINESS_TAX);

		User buyer = model.getUser();
		buyer.payFor(price);
		User seller = freeMarketItem.getUser();

		seller.addMoney(income);

		ItemLine itemLine = new ItemLine();
		itemLine.setItem(freeMarketItem.getItem());
		itemLine.setMakerId(freeMarketItem.getMakerId());
		itemLine.setUser(buyer);

		if (log.isDebugEnabled()) {
			log.debug("售价：" + price);
			log.debug("税后：" + income);
			log.debug("税金：" + tax);
		}

		seller.update();

		String str = buyer.getNick() + "购买了你挂在的" + itemLine.getItem().getName()
				+ ",你获得了" + income + "西大币";
		// 发送消息 
		// 最后一个修改为买家的nick 2012-05-15
		Message.sendMessage("物品出售信息", str, 1, seller, buyer, seller.getNick());

		log.debug(seller);
		buyer.update();
		log.debug(buyer);
		itemLine.save();
		log.debug(itemLine);
		freeMarketItem.delete();
		log.debug(freeMarketItem);

		FreeMarketBusinessLog.log(seller, buyer, itemLine.getItem(), price,
				income, tax);
	}

	public void cleanFreeMarketItem() {
		Date currentTime = new Date();
		List<FreeMarketItem> overdueItems = FreeMarketItem
				.getOverdueItems(currentTime);
		for (FreeMarketItem freeMarketItem : overdueItems) {
			ItemLine itemLine = new ItemLine();
			itemLine.setItem(freeMarketItem.getItem());
			itemLine.setMakerId(freeMarketItem.getMakerId());
			itemLine.setUser(freeMarketItem.getUser());
			itemLine.save();
			freeMarketItem.delete();
		}
		if (log.isDebugEnabled()) {
			log.debug("清理过期商品... 当前时刻:" + currentTime);
			log.debug("共有过期商品：" + overdueItems.size() + " 件！");
			log.debug("过期商品列表：" + overdueItems);
		}
	}

	public void createItem(ItemManageModel model)
			throws ObjectNotFoundException {
		Item item = new Item();
		item.setName(model.getName());
		item.setType(ItemType.get(model.getItem_type_id()));
		item.setEffect(model.getEffect());
		item.setObjectBadboy(model.getObjectBadboy());
		item.setObjectExp(model.getObjectExp());
		item.setObjectPower(model.getObjectPower());
		item.setProbability(model.getProbability());
		item.setReturnId(model.getReturnId());
		item.setSelfMoney(model.getSelfMoney());
		item.setSelfPower(model.getSelfPower());
		item.setMessage(model.getMessage());
		item.setMessage_title(model.getMessage_title());
		item.save();

		StoreItem storeItem = new StoreItem();
		storeItem.setItem(item);
		storeItem.setNumber(0);
		storeItem.setUnitPrice(0);
		storeItem.save();
	}

	public void getItemTypes(ItemManageModel model) {
		model.setItemTypes(ItemType.getItemTypes());
	}

	public void getItems(ItemManageModel model) {
		model.setItems(Item.getItems());
	}

	public void editItem(ItemManageModel model) throws ObjectNotFoundException {
		Item item = Item.get(model.getId());
		item.setName(model.getName());
		item.setType(ItemType.get(model.getItem_type_id()));
		item.setEffect(model.getEffect());
		item.setObjectBadboy(model.getObjectBadboy());
		item.setObjectExp(model.getObjectExp());
		item.setObjectPower(model.getObjectPower());
		item.setProbability(model.getProbability());
		item.setReturnId(model.getReturnId());
		item.setSelfMoney(model.getSelfMoney());
		item.setSelfPower(model.getSelfPower());
		item.setMessage(model.getMessage());
		item.setMessage_title(model.getMessage_title());
		item.update();
	}

	public void getItem(ItemManageModel model) throws ObjectNotFoundException {
		Item item = Item.get(model.getId());
		model.setEffect(item.getEffect());
		model.setName(item.getName());
		model.setItem_type_id(item.getType().getId());
		model.setObjectBadboy(item.getObjectBadboy());
		model.setObjectExp(item.getObjectExp());
		model.setObjectPower(item.getObjectPower());
		model.setSelfMoney(item.getSelfMoney());
		model.setSelfPower(item.getSelfPower());
	}

	public void editStoreItem(ItemManageModel model)
			throws ObjectNotFoundException {
		StoreItem storeItem = StoreItem.get(model.getId());
		storeItem.setNumber(model.getNumber());
		storeItem.setUnitPrice(model.getUnitPrice());
		storeItem.save();
	}

	/*
	 * 物品操作记录
	 */

	public void operateItemLog(OperateItemLogModel model) throws BBSException {

		User user = model.getUser();

		switch (model.getTypeId()) {

		case 0:
			model
					.setLogs(ItemUsedLog.getByType(user, 0, model
							.getPagination()));
			break;
		case 1:
			model
					.setLogs(ItemUsedLog.getByType(user, 1, model
							.getPagination()));
			break;
		case 2:
			model
					.setLogs(ItemUsedLog.getByType(user, 2, model
							.getPagination()));
			break;
		default:
			throw new BBSException("操作类型错误！");

		}

	}

}
