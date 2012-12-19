package cn.newgxu.bbs.web.action.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.bank.LoanModel;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class LoanAction extends AbstractBaseAction {

	private static final long serialVersionUID = -3687991274371972205L;

	private static final Log log = LogFactory.getLog(LoanAction.class);

	private LoanModel model = new LoanModel();

	private BankService bankService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.loan(model);
			return SUCCESS;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String newLoan() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.newLoan(model);
			m.setUrl("/bank/loan.yws");
			m.addMessage("<b>您的申请已经成功提交，请耐心等待！</b>");
			m.addMessage("<a href='/bank/index.yws'>返回银行大厅</a>");
			Util.putMessageList(m, getSession());
			return SUCCESS;
		} catch (ValidationException e) {
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

	public String dealLoan() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.dealLoan(model);
			m.setUrl("/bank/loan.yws");
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
