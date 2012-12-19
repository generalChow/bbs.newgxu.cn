package cn.newgxu.bbs.web.action.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.bank.VirementModel;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class VirementAction extends AbstractBaseAction {

	private static final long serialVersionUID = 283689596502077721L;

	private static final Log log = LogFactory.getLog(VirementAction.class);

	private VirementModel model = new VirementModel();

	private BankService bankService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.virement(model);
			return SUCCESS;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String submit() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.virementDo(model);
			m.setUrl("/bank/log.yws?type=4");
			m.addMessage("<b>转帐成功！</b>");
			m.addMessage("<a href='/bank/log.yws?type=4'>查看日志</a>");
			m.addMessage("<a href='/bank/index.yws'>返回银行大厅</a>");
			Util.putMessageList(m, getSession());
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
