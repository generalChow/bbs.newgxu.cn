package cn.newgxu.bbs.web.action.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.bank.OperateLogModel;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BankOperateLogAction extends AbstractBaseAction {

	private static final long serialVersionUID = 4069301667738284626L;

	private static final Log log = LogFactory
			.getLog(BankOperateLogAction.class);

	private OperateLogModel model = new OperateLogModel();

	private BankService bankService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			model.setUser(getUser());
			bankService.operateLog(model);
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
