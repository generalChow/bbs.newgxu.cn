package cn.newgxu.bbs.web.webservice.model;

/**
 * @author 集成显卡
 * web service的基本model
 * 这个model是给外部程序调用论坛内部命令使用的
 * 首先要验证是否具有权限
 */
public class WebUserModel {

	//对应了论坛的id和密码
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
