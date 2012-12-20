package cn.newgxu.bbs.web.webservice.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于论坛和微雨无声交互的实用类
 * 
 * @author longkai
 * @version 1.0
 * @since 2012-09-28
 */
public class Twitter {

	private static final Logger l = LoggerFactory.getLogger(Twitter.class);

	public static String INDEX = "http://bbs.newgxu.cn/index.yws";
	public static String WEBSITE = "广西大学雨无声社区论坛";
	public static String NEW_TOPIC = "刚刚在论坛发表了一篇帖子！ 欢迎大家来灌灌水！ ";
	public static String REPLY = "刚才再在论坛回复了一个帖子！欢迎大家一同来讨论！ ";

	private String url = "";
	private String title = "";
	private String sharedContent = "";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSharedContent() {
		return sharedContent;
	}

	public void setSharedContent(String sharedContent) {
		this.sharedContent = sharedContent;
	}

	/**
	 * 对微博进行同步，注意，这里使用的是get方法。
	 * 
	 * @param username
	 * @param password
	 */
	public void synchronousTwitter(String username, String password, int type) {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		// String url =
		// "http://t.newgxu.cn/index.php?app=home&mod=Public&act=share&username="
		// + username + "&password=" + password;
		String url = "http://t.newgxu.cn/index.php?app=home&mod=Public&act=doQuickPublishPic&from=5";
		HttpClient client = null;
		PostMethod method = null;
		NameValuePair content = null;
		try {
			client = new HttpClient(connectionManager);
			method = new PostMethod(url);
			if (type == 1)
				content = new NameValuePair("content", buildURL(NEW_TOPIC));
			else if (type == 2)
				content = new NameValuePair("content", buildURL(REPLY));
			else
				return;
			NameValuePair _username = new NameValuePair("username", username);
			NameValuePair _password = new NameValuePair("password", password);
			method.setRequestBody(new NameValuePair[] { content, _username,
					_password });
			client.executeMethod(method);

			l.debug(method.getQueryString());
			l.debug(method.getResponseBodyAsString());

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			// method.recycle();
		}
	}

	/**
	 * 构造查询字符串，对参数进行编码。
	 * 
	 * @param username
	 * @param password
	 * @return string
	 * @throws UnsupportedEncodingException
	 * @throws URIException
	 */
	public String buildURL(String indent) throws UnsupportedEncodingException,
			URIException {
		// StringBuilder sb = new StringBuilder();
		// String indent = URLEncoder.encode(" -- ", "UTF-8");
		// sb.append("http://t.newgxu.cn/index.php?app=home&mod=Public&act=doQuickPublishPic&from=5&username=")
		// .append(username).append("&password=").append(password).append("&content=")
		// sb.append(URLEncoder.encode("刚刚在论坛发表了一篇帖子！ 欢迎大家来灌灌水！", "UTF-8"))
		// .append(URLEncoder.encode(title, "UTF-8")).append(indent)
		// .append(URLEncoder.encode(url, "UTF-8")).append(indent)
		// .append(URLEncoder.encode(sharedContent, "UTF-8")).append(indent)
		// .append(URLEncoder.encode(WEBSITE, "UTF-8")).append(indent);
		// System.out.println(sb.toString());
		// return sb.toString();
		// return URIUtil.encodeQuery("刚刚在论坛发表了一篇帖子！ 欢迎大家来灌灌水！ ", "utf-8") + url
		// + " " + WEBSITE;
		return URLEncoder.encode(indent, "UTF-8") + url + " "
				+ URLEncoder.encode(WEBSITE, "UTF-8") + "  " + INDEX;
	}

	public static void main(String[] args) throws Exception {
		Twitter twitter = new Twitter();
		twitter.setUrl("123");
		twitter.setTitle(" 你好");
		twitter.setSharedContent("  要勇敢！");
		// System.out.println(twitter.buildURL("longkai",
		// "e10adc3949ba59abbe56e057f20f883e"));
	}

}
