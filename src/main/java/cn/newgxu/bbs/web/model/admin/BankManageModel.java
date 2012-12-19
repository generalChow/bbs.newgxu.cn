package cn.newgxu.bbs.web.model.admin;

import java.util.List;

import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.bank.Loan;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BankManageModel {
	private Bank bank;

	private int openCost;
    private float currentRate;
    private float fixedRate;
    private float loanRate;
    private float virementRate;
	
	private List<User> bankAdmins;
	private List<Loan> applyLoans;
	
	private byte type;
	
	
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public int getOpenCost() {
		return openCost;
	}
	public void setOpenCost(int openCost) {
		this.openCost = openCost;
	}
	public float getCurrentRate() {
		return currentRate;
	}
	public void setCurrentRate(float currentRate) {
		this.currentRate = currentRate;
	}
	public float getFixedRate() {
		return fixedRate;
	}
	public void setFixedRate(float fixedRate) {
		this.fixedRate = fixedRate;
	}
	public float getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(float loanRate) {
		this.loanRate = loanRate;
	}
	public float getVirementRate() {
		return virementRate;
	}
	public void setVirementRate(float virementRate) {
		this.virementRate = virementRate;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public List<User> getBankAdmins() {
		return bankAdmins;
	}
	public void setBankAdmins(List<User> bankAdmins) {
		this.bankAdmins = bankAdmins;
	}
	public List<Loan> getApplyLoans() {
		return applyLoans;
	}
	public void setApplyLoans(List<Loan> applyLoans) {
		this.applyLoans = applyLoans;
	}
}
