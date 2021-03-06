package cn.newgxu.bbs.web.action.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.bank.EditPasswordModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditBankPasswordAction extends AbstractBaseAction {

	private static final long serialVersionUID = -2624011011845783031L;

	private static final Log log = LogFactory
			.getLog(EditBankPasswordAction.class);

	private EditPasswordModel model = new EditPasswordModel();

	private BankService bankService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("社区银行...");
		model.setUser(getUser());
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("社区银行...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			bankService.editBankPasswordDo(model);
			m.setUrl("/bank/index.yws");
			m.addMessage("<b>更新成功！</b>");
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
