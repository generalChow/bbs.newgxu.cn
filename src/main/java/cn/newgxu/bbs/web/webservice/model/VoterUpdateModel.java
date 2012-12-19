package cn.newgxu.bbs.web.webservice.model;

/**
 * @author 集成显卡
 * 2011.5.1
 * 这个是版主投票系统的model
 * 应该包含版主的nick，还有授权的口令
 * 对应外部程序，想要调用，必需发送一个我们早就设定好的口令
 * 口令经过特定的程序加密
 */
public class VoterUpdateModel extends WebUserModel{
	
	private  String nick;//添加西大币的版主的昵称
	private String yzm;//口令 
	private String helloWorld;//时间
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getYzm() {
		return yzm;
	}
	public void setYzm(String yzm) {
		this.yzm = yzm;
	}
	public String getHelloWorld() {
		return helloWorld;
	}
	public void setHelloWorld(String helloWorld) {
		this.helloWorld = helloWorld;
	}
}
