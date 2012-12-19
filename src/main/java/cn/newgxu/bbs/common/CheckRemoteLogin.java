package cn.newgxu.bbs.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 阿福 2006-9-28 下午09:11:19 edit: 2011-10-26 更新了判断方法， 添加判断身份证
 */
public class CheckRemoteLogin {

	private static final Log log = LogFactory.getLog(CheckRemoteLogin.class);

	/**
	 * 使用POST方式提交数据
	 * 
	 * @return
	 */
	// 这个有验证码，暂时被废弃
//	private static HttpMethod getPostMethod(String username, String password) {
//		PostMethod post = new PostMethod(
//				"/(mm4ois55iqwii2mlcqks3l45)/default2.aspx");
//		NameValuePair p1 = new NameValuePair("__VIEWSTATE",
//				"dDwtMTAzOTYzNjY2ODs7PjegvcbXZuJzTSZGJ7J/g5Ee9Qxh");
//		NameValuePair p2 = new NameValuePair("RadioButtonList1", "%D1%A7%C9%FA");// 学生
//		NameValuePair p3 = new NameValuePair("Button1", "+%B5%C7+%C2%BC+");// 登录
//		NameValuePair p4 = new NameValuePair("TextBox1", username);
//		NameValuePair p5 = new NameValuePair("TextBox2", password);
//		post.setRequestBody(new NameValuePair[] { p1, p2, p3, p4, p5 });
//		return post;
//	}
	
	// 无验证码，现在使用
	private static HttpMethod getPostMethod2(String userName, String password) {
		PostMethod post = new PostMethod(
				"/(1c0jz521sp3sh355ijjkbe45)/default3.aspx");
		NameValuePair p1 = new NameValuePair("__VIEWSTATE",
				"dDwtMTM2MTgxNTk4OTs7PqMpQpG1T7AzprCWAnCQNL6t3Wqt");
//		NameValuePair p2 = new NameValuePair("RadioButtonList1", "%D1%A7%C9%FA");// 学生  默认是学生
		NameValuePair p3 = new NameValuePair("Button1", "+%B5%C7+%C2%BC+");// 登录
		NameValuePair p4 = new NameValuePair("TextBox1", userName);
		NameValuePair p5 = new NameValuePair("TextBox2", password);
		post.setRequestBody(new NameValuePair[] { p1, p3, p4, p5 });
		return post;
	}

	/**
	 * 用学号验证-------- 使用新选课系统
	 */
	public static boolean checkRemoteUser(String username, String password) {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);
		client.getHostConfiguration().setHost("210.36.16.135", 80, "http");
		// HttpState state = client.getState();
		HttpMethod method = getPostMethod2(username, password);
		try {
			client.executeMethod(method);
		} catch (HttpException he) {
			log.error(he.getMessage(), he);
			return false;
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return false;
		}

		// ��ӡ���ҳ��?
		String response;
		try {
			response = new String(method.getResponseBodyAsString().getBytes(
					"utf-8"));
			log.debug(response);
			System.out.println(response);
			if (response.indexOf("密码错误") != -1
					|| response.indexOf("用户名不存在") != -1) {
				return false;
			} else if (response.indexOf("xh=" + username) > -1) {
				return true;
			} else {
				return false;
			}
		} catch (UnsupportedEncodingException uee) {
			log.error(uee.getMessage(), uee);
			return false;
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return false;
		} finally {
			method.releaseConnection(); // 释放Http连接
		}
	}

	/**
	 * 通过读书馆接口进行验证
	 * 
	 * @param name
	 * @param password
	 * @param type
	 *            1表示本科生和研究生，2表示行建
	 * @return
	 * @throws IOException
	 */
	public static boolean checkUser(String name, String password, int type)
			throws IOException {
		switch (type) {
		case 1:
			URL url = new URL(
					"http://210.36.19.205:6666/opac_two/include/login_interface.jsp?login_tye=barcode&barcode="
							+ name + "&password=" + password);
			InputStream inputStream = url.openStream();
			BufferedInputStream bin = new BufferedInputStream(inputStream);
			StringBuffer buffer = new StringBuffer();
			byte b[] = new byte[1024];
			while (bin.read(b) != -1) {
				buffer.append(new String(b));
			}
			System.out.println(buffer);
//			boolean c = buffer.toString().contains("密码不正确");
//			boolean bb = buffer.toString().contains("无此读者");
//			if (!c && !bb) {
//				return true;
//			}
			
			if (buffer.toString().contains("alert")) {
				return false;
			}
			return true;
		case 2:
			url = new URL(
					"http://210.36.24.18:6666/opac_two/include/login_app.jsp?login_tye=barcode&barcode="
							+ name + "&password=" + password);
			InputStream inputStreamX = url.openStream();
			BufferedInputStream stream = new BufferedInputStream(inputStreamX);
			StringBuffer buffer2 = new StringBuffer();
			byte b2[] = new byte[1024];
			while (stream.read(b2) != -1) {
				buffer2.append(new String(b2));

			}
			boolean cX = buffer2.toString().contains("密码不正确");
			boolean bX = buffer2.toString().contains("无此读者");
			if (!cX && !bX) {
				return true;
			}
			break;

		}
		return false;
	}

	/**
	 * 身份证号码验证 如果需要更新网址，请根据目标网址的 contentType 调整编码，否则得到的是乱码
	 * 
	 * @param idCard
	 * @return
	 * @throws Exception
	 */
	public static boolean checkCard(String idCard) throws Exception {
		URL url = new URL(
				"http://qq.ip138.com/idsearch/index.asp?action=idcard&userid="
						+ idCard);
		InputStream inputStream = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream, "gb2312"));
		StringBuffer sbf = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			sbf.append(line);
//			System.out.println(line);
		}
		br.close();
		String result = sbf.toString();
		boolean verify = result.contains("提示")
				&& result.contains("不正确");
		return verify ? false : true;
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// CheckRemoteLogin tp = new CheckRemoteLogin();
		// System.out.print(tp.checkUser("ggg","0601301007","452323198311246124"));
		// System.out.print(CheckRemoteLogin.checkRemoteUser("222220707100320",
		// ""));
		// System.out.println(CheckRemoteLogin.checkRemoteUser("1007300326",
		// "lk505566398"));
		if (CheckRemoteLogin.checkRemoteUser("1007300326", "lk505566398") == true) {
			System.out.println("ok!");
		} else {
			System.out.println("false");
		}

		// System.out.println(checkUser("A08071" , "00000" , 1));
//		System.out.println(checkUser("A1007300354", "00000", 1));
//		System.out.println(checkCard("450302199110132017"));
	}

}