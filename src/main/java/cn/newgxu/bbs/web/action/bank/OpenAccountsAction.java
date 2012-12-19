package cn.newgxu.bbs.web.action.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.bank.OpenAccountsModel;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OpenAccountsAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2683755332043869747L;

	private static final Log log = LogFactory.getLog(OpenAccountsAction.class);

	private OpenAccountsModel model = new OpenAccountsModel();

	private BankService bankService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("申请开户中...");
		model.setUser(getUser());
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("银行开户中...");
		model.setUser(getUser());
		MessageList m = new MessageList();
		try {
			bankService.openAccounts(model);
			m.setUrl("/bank/index.yws");
			m.addMessage("<b>恭喜您开户成功！</b>");
			m.addMessage("<a href='/bank/index.yws'>进入银行大厅</a>");
			Util.putMessageList(m, getSession());
			log.debug("恭喜您开户成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			log.debug(e);
			super.addActionError(e.getMessage());
			return execute().equals(SUCCESS) ? INPUT : ERROR;
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
