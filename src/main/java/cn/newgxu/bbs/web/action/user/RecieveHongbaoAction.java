package cn.newgxu.bbs.web.action.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.hongbao.HongBao_content;
import cn.newgxu.bbs.service.HongbaoService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.accounts.RegisterAction;
import cn.newgxu.bbs.web.model.hongbao.HongbaoRecieveModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class RecieveHongbaoAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2963413112287145520L;

	private static final Log log = LogFactory.getLog(RegisterAction.class);

	private HongbaoRecieveModel model = new HongbaoRecieveModel();

	private HongbaoService hongbaoService;

	public String execute() {
		signOnlineUser("领红包的界面...");
		try {
			model.setUser(getUser());
			hongbaoService.getHongbaoToday(model);
		} catch (BBSException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String doRevicieveHongbao() {
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			hongbaoService.recieveHongbaoToday(model);
			HongBao_content content = model.getHongbao().getContent();
			String itemsDisplay = "";
			for (int i = 0; i < content.getItems().size(); i++) {
				if (i == 0) {
					itemsDisplay += content.getItems().get(i).getName() + "X1";
				} else {
					itemsDisplay += "," + content.getItems().get(i).getName()
							+ "X1";
				}
			}
			m.setUrl("/market/myItems.yws");
			m.addMessage("<b> 红包领取成功！</b>");
			m.addMessage("您获得了" + content.getExp() + "点经验和 "
					+ content.getMoney() + " xdb");
			if (!itemsDisplay.equals(""))
				m.addMessage("还有物品" + itemsDisplay);
			m.addMessage("<a href='/market/myItems.yws'>去物品栏看看</a>");
			m.addMessage("<a href='/index.yws'>返回主页</a>");
			Util.putMessageList(m, getSession());
		} catch (BBSException e) {
			e.printStackTrace();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		log.debug("红包领取成功");
		return SUCCESS;
	}

	public void setHongbaoService(HongbaoService hongbaoService) {
		this.hongbaoService = hongbaoService;
	}

	public Object getModel() {
		return model;
	}

}
