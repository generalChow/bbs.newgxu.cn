package cn.newgxu.bbs.web.action.accounts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.ResetPasswordInfo;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.EidtPasswordModel;
import cn.newgxu.bbs.web.webservice.impl.transact.SPTransact;

/**
 * 
 * @author xjc
 */
public class ResetPassword extends AbstractBaseAction {

	private static final long serialVersionUID = 2718143210042224389L;

	private String username = "";
	private String code = "";
	
	private String schoolId = "";
	private String personalId = "";

	private String errorMessage;
	
	@Resource
	private SPTransact sPTransact;

	private EidtPasswordModel model = new EidtPasswordModel();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String sendMail() throws Exception {
		if (this.username.equals("")) {
			setErrorMessage("用户名无效");
			return INPUT;
		}
		User user;
		try {
			user = User.getByUsername(this.username);
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("用户名无效");
			return INPUT;
		}
		if (user == null) {
			setErrorMessage("用户名无效");
			return INPUT;
		}

		System.out.println(user.getUsername());

		String email = user.getEmail();
		if (email == null || email.equals("") || !validateEmail(email)) {
			setErrorMessage("邮箱无效");
			return INPUT;
		}

		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		//props.setProperty("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props);
		// session.setDebug(true);

		Message msg = new MimeMessage(session);

		// msg.setText("点击连接将密码重置为123456： http://bbs.newgxu.cn/accounts/login_do.yws");
		String theCode = Util.hash(this.username + (new Date()).toString()
				+ Math.random());
		System.out.println(theCode);
		String text = "点击连接将密码重置为123456： http://bbs.newgxu.cn/accounts/resetPassword2.yws?code="
				+ theCode;
		msg.setText(text);
		msg.setSubject("pass");
		msg.setFrom(new InternetAddress("yws_PASSWORD@126.com"));

		Transport transport = session.getTransport();
		transport
				.connect("smtp.126.com", 25, "yws_PASSWORD", "Hsdu&143sm(fI_a");
		transport.sendMessage(msg, new Address[] { new InternetAddress(
				email) });

		ResetPasswordInfo r = new ResetPasswordInfo();

		r.setUsername(this.username);
		r.setComplete(0);
		r.setStartTime(new Date());
		r.setCode(theCode);
		Long endTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
		r.setEndTime(endTime.toString());
		sPTransact.saveSP(r);
		
		transport.close();

		setErrorMessage("邮件已发送,请在24小时内点击邮件中的链接以重置密码.");
		return SUCCESS;
	}
	
	public String sendMailMore() throws Exception {
		if (this.personalId.equals("") && this.schoolId.equals("")) {
			setErrorMessage("输入无效");
			return INPUT;
		}
		List<User> userList=null;
		try {
			userList = User.getUsersByIdcodeAndStudentid(this.schoolId,this.personalId);
		}catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("无对应结果"+e);
			return INPUT;
		}
		if (userList == null || userList.size()==0) {
			setErrorMessage("无对应结果");
			return INPUT;
		}

		String email="";
		for(int i=0;i<userList.size();i++){
			email = userList.get(i).getEmail();
			if (email == null || email.equals("") || !validateEmail(email)){
				continue;
			}else{
				break;
			}
		}
		if (email == null || email.equals("") || !validateEmail(email)) {
			setErrorMessage("邮箱无效");
			return INPUT;
		}

		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		//props.setProperty("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props);
		// session.setDebug(true);

		Message msg = new MimeMessage(session);
		
		List<ResetPasswordInfo> rList = new ArrayList<ResetPasswordInfo>();
		String text = "点击连接将密码重置为123456：\r\n";
		for(int i=0;i<userList.size();i++){
			ResetPasswordInfo r = new ResetPasswordInfo();

			String theCode = Util.hash(this.username + (1000000*Math.random()*Math.random())+" "
					+ Math.random());
			
			r.setUsername(userList.get(i).getUsername());
			r.setComplete(0);
			r.setStartTime(new Date());
			r.setCode(theCode);
			Long endTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
			r.setEndTime(endTime.toString());
			
			rList.add(r);
			
			text+=" "+r.getUsername()+" "+"http://bbs.newgxu.cn/accounts/resetPassword2.yws?code="
				+ theCode+"\r\n";
		}

		msg.setText(text);
		msg.setSubject("pass");
		msg.setFrom(new InternetAddress("yws_PASSWORD@126.com"));

		Transport transport = session.getTransport();
		transport
				.connect("smtp.126.com", 25, "yws_PASSWORD", "Hsdu&143sm(fI_a");
		transport.sendMessage(msg, new Address[] { new InternetAddress(
				email) });

		for(ResetPasswordInfo r:rList){
			sPTransact.saveSP(r);
		}
		
		transport.close();

		setErrorMessage("邮件已发送,请在24小时内点击邮件中的链接以重置密码.");
		return SUCCESS;
	}

	public String resetPass() throws Exception {
		if (code == null || code.equals("")) {
			setErrorMessage("ERROR: invalid code");
			return INPUT;
		}

		ResetPasswordInfo r;
		try{
			 r= ResetPasswordInfo.getByCode(this.code);
		}catch(Exception e){
			e.printStackTrace();
			setErrorMessage("ERROR: invalid code");
			return INPUT;
		}
		if (r == null) {
			setErrorMessage("ERROR: invalid code");
			return INPUT;
		}

		if (r.isValidate() == false) {
			setErrorMessage("ERROR: invalid code");
			return INPUT;
		}

		User u=User.getByUsername(r.getUsername());
		model.setUser(u);
		userService.resetPassword(model);
		
		r.setComplete(1);
		sPTransact.saveSP(r);

		setErrorMessage("密码重置成功，请立即修改初始密码");
		return SUCCESS;
	}

	@Override
	public String execute() throws Exception {
		return null;
	}

	public Object getModel() {
		return null;
	}

	public boolean validateEmail(String input) {
		Pattern p = Pattern.compile("^\\.|^\\@");
		Matcher m = p.matcher(input);
		if (m.find()) {
			System.err.println("不能以'.'或'@'作为起始字符");
			return false;
		}
		// 检测是否以"www."为起始
		p = Pattern.compile("^www\\.");
		m = p.matcher(input);
		if (m.find()) {
			System.out.println("不能以'www.'起始");
			return false;
		}
		// 检测是否包含非法字符
		p = Pattern.compile("[^A-Za-z0-9\\.\\@_\\-~#]+");
		m = p.matcher(input);
		if (m.find()) {
			System.out.println("非法字符");
			return false;
		}
		System.out.println("email : " + input);
		return true;
	}
}
