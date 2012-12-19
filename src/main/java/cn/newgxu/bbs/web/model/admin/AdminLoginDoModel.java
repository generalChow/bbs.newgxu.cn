package cn.newgxu.bbs.web.model.admin;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AdminLoginDoModel {

	private String username;

	private String password;
	
	private String validCode;

	private String rightCode;

	private String ip;
	
	private String originalUrl;

	public String getValidCode() {
		return validCode;
	}

	public String getRightCode() {
		return rightCode;
	}

	public String getIp() {
		return ip;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
