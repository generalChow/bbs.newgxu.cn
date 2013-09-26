package cn.newgxu.bbs.web.action.accounts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	private static final Log log = LogFactory.getLog(CheckAccoutAction.class);
	private String username;
	private String passWord;
	
	
	public static Log getLog() {
		return log;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		
		String pass = Util.hash(passWord);
		log.info("加密后："+pass);
		User user = User.getByUsername(username);
		if(user.getPassword().equals(pass)){
			JsonUtil.sendJsonStr("{\"result\":\"true\"}");
		}else{
			JsonUtil.sendJsonStr("{\"result\":\"false\"}");
		}
		return null;
	}
	

}
