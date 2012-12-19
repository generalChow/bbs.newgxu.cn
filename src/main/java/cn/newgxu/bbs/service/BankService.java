package cn.newgxu.bbs.service;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.web.model.admin.BankManageModel;
import cn.newgxu.bbs.web.model.bank.BankModel;
import cn.newgxu.bbs.web.model.bank.CurrentModel;
import cn.newgxu.bbs.web.model.bank.EditPasswordModel;
import cn.newgxu.bbs.web.model.bank.FixedModel;
import cn.newgxu.bbs.web.model.bank.LoanModel;
import cn.newgxu.bbs.web.model.bank.OpenAccountsModel;
import cn.newgxu.bbs.web.model.bank.OperateLogModel;
import cn.newgxu.bbs.web.model.bank.VirementModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface BankService {
	
	public void bank(BankModel model) throws BBSException;
	
	public void openAccounts(OpenAccountsModel model) throws BBSException, ValidationException;

	public void current(CurrentModel model) throws BBSException;

	public void access(CurrentModel model) throws ValidationException, BBSException;

	public void manual(CurrentModel model) throws BBSException;

	public void fixed(FixedModel model) throws BBSException;

	public void newFixed(FixedModel model) throws BBSException, ValidationException;

	public void drawFixed(FixedModel model) throws BBSException;

	public void loan(LoanModel model) throws BBSException;

	public void newLoan(LoanModel model) throws BBSException, ValidationException;
	
	public void dealLoan(LoanModel model) throws BBSException;

	public void virement(VirementModel model) throws BBSException;

	public void virementDo(VirementModel model) throws BBSException, ValidationException;

	public void editBankPasswordDo(EditPasswordModel model) throws BBSException, ValidationException;

	public void operateLog(OperateLogModel model) throws BBSException;

	public void bankManage(BankManageModel model) throws BBSException;

	public void bankModify(BankManageModel model) throws BBSException;

	public void approachLoan(LoanModel model) throws ObjectNotFoundException;

	public void denyLoan(LoanModel model) throws ObjectNotFoundException;

}
