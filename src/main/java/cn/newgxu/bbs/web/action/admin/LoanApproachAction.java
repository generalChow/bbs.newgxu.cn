package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.bank.LoanModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class LoanApproachAction extends AbstractBaseAction {

	private static final long serialVersionUID = 3040850707528477571L;

	private LoanModel model = new LoanModel();

	private BankService bankService;

	public String execute() throws Exception {
		bankService.approachLoan(model);
		response("{\"statusCode\":\"200\", \"message\":\"批准成功\", \"navTabId\":\"bank\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"bankManage.yws\"}");
		return null;
		//return SUCCESS;
	}
	
	public String doLoanDeny() throws Exception {
		bankService.denyLoan(model);
		response("{\"statusCode\":\"200\", \"message\":\"拒绝成功\", \"navTabId\":\"bank\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"bankManage.yws\"}");
		return null;
		//return SUCCESS;
	}
	
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public Object getModel() {
		return model;
	}

}
