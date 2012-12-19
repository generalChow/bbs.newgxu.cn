package cn.newgxu.bbs.web.action.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.bank.CurrentModel;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CurrentAction extends AbstractBaseAction {

	private static final long serialVersionUID = -3362896083099642899L;

	private static final Log log = LogFactory.getLog(CurrentAction.class);

	private CurrentModel model = new CurrentModel();

	private BankService bankService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.current(model);
			return SUCCESS;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String access() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.access(model);
			m.setUrl("/bank/current.yws");
			m.addMessage("<b>操作成功！</b>");
			m.addMessage("<a href='/bank/index.yws'>返回银行大厅</a>");
			Util.putMessageList(m, getSession());
			return SUCCESS;
		} catch (ValidationException e) {
			// super.addActionError(e.getMessage());
			// return execute().equals(SUCCESS)?INPUT:ERROR;
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String manual() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.manual(model);
			m.setUrl("/bank/current.yws");
			m.addMessage("<b>操作成功！</b>");
			m.addMessage("<a href='/bank/index.yws'>返回银行大厅</a>");
			Util.putMessageList(m, getSession());
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

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

}
