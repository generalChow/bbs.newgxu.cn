package cn.newgxu.bbs.service;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
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
public interface MarketService {

	public void getStoreItems(OfficialModel model);

	public void officialBuyDo(OfficialBuyDoModel model)	throws BBSException, ValidationException;

	public void itemSellDo(ItemSellDoModel model) throws BBSException, ValidationException;

	public void freeMarket(FreeModel model);

	public void freeMarketItemBuyDo(FreeMarketItemBuyDoModel model)	throws BBSException; 

	public void cleanFreeMarketItem();
	
	public void createItem(ItemManageModel model) throws ObjectNotFoundException;
	
	public void getItemTypes(ItemManageModel model);
	
	public void getItems(ItemManageModel model);
	
	public void editItem(ItemManageModel model) throws ObjectNotFoundException;

	public void getItem(ItemManageModel model) throws ObjectNotFoundException;

	public void editStoreItem(ItemManageModel model)throws ObjectNotFoundException;
	
	public void operateItemLog(OperateItemLogModel model) throws BBSException;

}
