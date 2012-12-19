package cn.newgxu.bbs.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 这个类是用来在后台（不像ajax前端获取）加载远程获取的数据用的，
 * 包含几个简单的可选的属性（不是每个属性都必须要），
 * 用于在页面显示相关的属性，标题，url，内容，以及时间等等
 * （以后如果需要什么其他的属性自己添加就是了）
 *
 * @since 2012-04-24
 * @author longkai
 */
public class RemoteContent {
	
	private String title;
	private String url;
	private String content;
	private Date time;

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	
	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 这个是回去远程数据的方法。
	 * @param path 从指定的url中获取结果信息。
	 * @param charset 对获取的数据进行转码。
	 * @return 返回转换后获取的数据的字符串。
	 * @version 1.1@2012-04-24
	 * @author longkai
	 */
	public static String getRemoteResult(String path, String charset) {
		StringBuffer sbf = new StringBuffer();
		BufferedReader br = null;
		try {
			URL url = new URL(path);
			sbf = new StringBuffer();
			String line = null;
			// 读取信息
			InputStream inputStream = url.openStream();
			br = new BufferedReader(new InputStreamReader(inputStream, charset));
			while ((line = br.readLine()) != null) {
				sbf.append(line);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (br != null)
					br.close();
				br = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sbf.toString();
	}

	/**
	 * 获取新闻网首页的祝福墙。
	 * @return 祝福墙内容list。
	 * @throws Exception
	 * @version 1.0@2012-04-24
	 * @author longkai
	 */
	public static List<RemoteContent> getWishesList() throws Exception {
		String str = getRemoteResult(
						"http://210.36.16.163/API/GET.php?action=wish&sort=add_time&desc=1&count=4", "gbk");
		List<RemoteContent> remoteContents = null;
		if (!validate(str)) {
			return new ArrayList<RemoteContent>(0);
		}
		// 使用json-simple解析JSON
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse(str);
		RemoteContent remoteContent = null;
		remoteContents = new ArrayList<RemoteContent>();
		for (int i = 0; i < a.size(); i++) {
			JSONObject obj = (JSONObject) a.get(i);
			remoteContent = new RemoteContent();
			remoteContent.setContent(obj.get("content").toString());
			remoteContent.setTitle(obj.get("name").toString());
			remoteContents.add(remoteContent);
		}

		return remoteContents;
	}

	/**
	 * 获取新闻网首页的校园通告栏。
	 * @return 校园通告的list。
	 * @throws Exception
	 * @version 1.0@2012-04-24
	 * @author longkai
	 */
	public static List<RemoteContent> getNoticeList() throws Exception {
		String str = getRemoteResult(
						"http://www.newgxu.cn/API/GET.php?action=notice&sort=noticeid&desc=1&count=7", "gbk");
		List<RemoteContent> remoteContents = null;
		if (!validate(str)) {
			return new ArrayList<RemoteContent>(0);
		}
		// 使用json-simple解析JSON
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse(str);
		remoteContents = new ArrayList<RemoteContent>();
		RemoteContent remoteContent = null;
		for (int i = 0; i < a.size(); i++) {
			JSONObject obj = (JSONObject) a.get(i);
			remoteContent = new RemoteContent();
			remoteContent.setTitle(obj.get("title").toString());
			remoteContent.setUrl("//www.newgxu.cn/html/notices/"
							+ obj.get("noticeid") + ".html");
			remoteContents.add(remoteContent);
		}

		return remoteContents;
	}

	/**
	 * 获取失物招领的捡到物品列表信息。
	 * @return 捡到物品的list。
	 * @throws Exception
	 * @version 1.0@2012-04-04
	 * @author longkai
	 */
	public static List<RemoteContent> getFindList() throws Exception {
		String str = getRemoteResult(
						"http://210.36.16.167/find/GET.php?action=lost_find&desc=1&count=7", "utf8");
		List<RemoteContent> remoteContents = null;
		if (!validate(str)) {
			return new ArrayList<RemoteContent>(0);
		}
		// 使用json-simple解析JSON
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse(str);
		remoteContents = new ArrayList<RemoteContent>();
		RemoteContent remoteContent = null;
		for (int i = 0; i < a.size(); i++) {
			JSONObject obj = (JSONObject) a.get(i);
			remoteContent = new RemoteContent();
			remoteContent.setTitle(obj.get("find_name").toString());
			remoteContent.setUrl("//t.newgxu.cn/find/find_detail.php?find_id="
									+ obj.get("find_id"));
			try {
				remoteContent.setTime(Timestamp.valueOf(obj.get(
						"find_release_time").toString()));
			} catch (Exception e) {
				e.printStackTrace();
				remoteContent.setTime(new Date());
			}
			
			remoteContents.add(remoteContent);
		}

		return remoteContents;
	}

	/**
	 * 获取失物招领的丢失物品列表信息。
	 * @return 丢失物品的list。
	 * @throws Exception
	 * @version 1.0@2012-04-24
	 * @author longkai
	 */
	public static List<RemoteContent> getLostList() throws Exception {
		String str = getRemoteResult(
						"http://210.36.16.167/find/GET.php?action=lost_lost&desc=1&count=7", "utf-8");
		List<RemoteContent> remoteContents = null;
		if (!validate(str)) {
			return new ArrayList<RemoteContent>(0);
		}
		// 使用json-simple解析JSON
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse(str);
		remoteContents = new ArrayList<RemoteContent>();
		RemoteContent remoteContent = null;
		for (int i = 0; i < a.size(); i++) {
			JSONObject obj = (JSONObject) a.get(i);
			remoteContent = new RemoteContent();
			remoteContent.setTitle(obj.get("lost_name").toString());
			remoteContent.setUrl("//t.newgxu.cn/find/lost_detail.php?lost_id="
									+ obj.get("lost_id"));
			try {
				remoteContent.setTime(Timestamp.valueOf(obj.get("lost_release_time").toString()));
			} catch (Exception e) {
				e.printStackTrace();
				remoteContent.setTime(new Date());
			}
			remoteContents.add(remoteContent);
		}

		return remoteContents;
	}

	/**
	 * 通过用户名来获取微博。
	 * <b>注意，从微博获取过来的数据是直接包含链接了的。</b>
	 * @param userName 指定获取的用户。
	 * @return 该用户的最新微博list。
	 * @throws Exception
	 * @version 1.0@2012-05-10
	 * @author longkai
	 */
	public static List<RemoteContent> getTwitter(String userName)
			throws Exception {
		String str = getRemoteResult("http://t.newgxu.cn/get.php?user="
										+ userName + "&count=7", "utf-8");
		List<RemoteContent> remoteContents = null;
		if (!validate(str)) {
			return new ArrayList<RemoteContent>(0);
		}
		// 使用json-simple解析JSON
		JSONParser parser = new JSONParser();
		JSONArray a = null;
		try {
			a = (JSONArray) parser.parse(str);
		} catch (Exception e) {
			return new ArrayList<RemoteContent>(0);
		}
		remoteContents = new ArrayList<RemoteContent>();
		RemoteContent remoteContent = null;
		String temp = "";
		for (int i = 0; i < a.size(); i++) {
			JSONObject obj = (JSONObject) a.get(i);
			remoteContent = new RemoteContent();
			remoteContent.setTitle(obj.get("face").toString());
			remoteContent.setUrl(obj.get("userlink").toString());
			temp = obj.get("content").toString();
//			remoteContent.setContent(
//					temp.length() > 30 ? getContentFilter(temp) : temp);
			remoteContent.setContent(temp);
			remoteContents.add(remoteContent);
		}
		
		return remoteContents;
	}

	/**
	 * 过滤掉微博的一些图片标签（微博内容太长导致页面很难看），非常之麻烦。
	 * @param content 对应过滤的内容。
	 * @return 过滤后的内容字符串。
	 * @version 1.5@2012-05-20
	 * @author longkai
	 */
	public static String getContentFilter(String content) {
		String str = content;
		String regex = "<img.+?>";
		int length = 0;
		int totalLength = content.length();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		if (!matcher.find()) {
			return content.substring(0, 30) + "...";
		}

		matcher.reset();

		while (matcher.find()) {
			length += matcher.group().length();
		}

		if (totalLength - length > 30) {
			content = content.substring(0, length + 30);
			// 匹配任意没有闭合的标签
			if (!content.matches("<([a-z]+)(\\s*\\w*?\\s*=\\s*'.+?')*(\\s*?>[\\s\\S]*?<\\/\\1>|\\s*\\/>)")) {
				content += ">";
			}
			content += "...";
		}

		return content;
	}
	
	private static boolean validate(String result) {
		if (result != null && !result.equals("")) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getTwitter("").size());
//		List<RemoteContent> twitters = getTwitter("longkai");
//		for (RemoteContent content : twitters) {
//			System.out.println(content.getUrl());
//		}
//		System.out.println("hello");
		System.out.println("Hello, " + "World!");
		System.out.println(new ArrayList<RemoteContent>().size());
	}
	
}
