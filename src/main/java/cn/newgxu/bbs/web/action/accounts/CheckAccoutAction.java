package cn.newgxu.bbs.web.action.accounts;

import cn.newgxu.bbs.common.util.JsonUtil;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.ObjectNotFoundException;

import com.opensymphony.xwork.ActionSupport;

public class CheckAccoutAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String userName;
	private String passWord;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	/**
	 * 用来作为检验账户和密码是否正确的接口 正确返回true 错误返回false
	 */
	public String execute() throws ObjectNotFoundException{
		System.out.println(userName);
		String pass = Util.hash(passWord);
		System.out.println("加密后："+pass);
		User user = User.getByUsername(userName);
		if(user.getPassword().equals(pass)){
			JsonUtil.sendJsonStr("{\"result\":\"true\"}");
		}else{
			JsonUtil.sendJsonStr("{\"result\":\"false\"}");
		}
		return null;
	}
	

}
