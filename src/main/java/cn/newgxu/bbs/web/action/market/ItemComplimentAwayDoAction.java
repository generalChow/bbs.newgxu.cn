package cn.newgxu.bbs.web.action.market;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.market.ItemComplimentAwayDoModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ItemComplimentAwayDoAction extends AbstractBaseAction {

	private static final long serialVersionUID = 8485061775292911212L;

	private static final Log log = LogFactory
			.getLog(ItemComplimentAwayDoAction.class);

	private ItemComplimentAwayDoModel model = new ItemComplimentAwayDoModel();

	@Override
	public String execute() throws Exception {
		signOnlineUser("赠送物品给...谁？");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			userService.itemComplimentAwayDo(model);
			m.setUrl("/market/myItems.yws");
			m.addMessage("<b>成功送出物品给用户：${nick} </b>", MessageList.P("${nick}",
					model.getStringUsers()));
			m.addMessage("<a href='/market/myItems.yws'>去看看我的物品！</a>");
			m.addMessage("<a href='/market/official.yws'>爽！到官方商店去购物！</a>");
			m.addMessage("<a href='/market/free.yws'>嗯...到自由市场去看看！</a>");
			Util.putMessageList(m, getSession());
			log.debug("赠送物品成功！");
			return SUCCESS;
		} catch (BBSException e) {
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
