package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.BankService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.BankManageModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BankManageAction extends AbstractBaseAction {

	private static final long serialVersionUID = 3040850707528477571L;

	private BankManageModel model = new BankManageModel();

	private BankService bankService;

	public String execute() throws Exception {
		bankService.bankManage(model);
		return SUCCESS;
	}
	
	public String doBankModify() throws BBSException{
		try{
			bankService.bankModify(model);
			response("{\"statusCode\":\"200\", \"message\":\"银行修改成功\", \"navTabId\":\"bank\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"bankManage.yws\"}");
			return null;
			//return SUCCESS;
		}catch(Exception e){
			MessageList m = new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public Object getModel() {
		return model;
	}

}
