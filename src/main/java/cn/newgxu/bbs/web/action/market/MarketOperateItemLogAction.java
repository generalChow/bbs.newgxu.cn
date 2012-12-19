package cn.newgxu.bbs.web.action.market;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.MarketService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.OperateItemLogModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MarketOperateItemLogAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory
			.getLog(MarketOperateItemLogAction.class);

	private OperateItemLogModel model = new OperateItemLogModel();

	private MarketService marketService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			model.getPagination().setPageSize(15);
			model.setUser(getUser());
			marketService.operateItemLog(model);
			return SUCCESS;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public MarketService getMarketService() {
		return marketService;
	}

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	public Object getModel() {
		return model;
	}

}
