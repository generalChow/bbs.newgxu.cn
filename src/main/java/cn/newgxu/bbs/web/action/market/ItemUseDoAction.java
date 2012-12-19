package cn.newgxu.bbs.web.action.market;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.ItemUseDoModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ItemUseDoAction extends AbstractBaseAction {

	private static final long serialVersionUID = -4074426202862670389L;

	private static final Log log = LogFactory.getLog(ItemUseDoAction.class);

	private ItemUseDoModel model = new ItemUseDoModel();

	@Override
	public String execute() throws Exception {
		signOnlineUser("使用物品");
		model.setUser(getUser());
		MessageList m = new MessageList();

		try {
			userService.itemUseDo(model);
			m.setUrl("/market/myItems.yws");
			// m.addMessage("<b>成功对用户：${nick} 使用物品！</b>",
			// MessageList.P("${nick}",
			// model.getNick()));
			m.addMessage("<a href='/market/myItems.yws'>去看看我的物品！</a>");
			m.addMessage("<a href='/market/official.yws'>爽！到官方商店去购物！</a>");
			m.addMessage("<a href='/market/free.yws'>嗯...到自由市场去看看！</a>");
			Util.putMessageList(m, getSession());
			log.debug("使用物品成功！");
			return SUCCESS;
		} catch (BBSException e) {
			e.printStackTrace();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			log.debug(e);
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

}
