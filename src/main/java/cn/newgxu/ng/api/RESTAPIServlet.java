/*
 * Copyright (c) 2001-2013 newgxu.cn <the original author or authors>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package cn.newgxu.ng.api;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.HttpRequestHandler;

import com.sun.mail.util.QEncoderStream;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.jpamodel.ObjectNotFoundException;
import cn.newgxu.ng.util.RegexUtils;

/**
 * 
 * @author longkai
 * @email im.longkai@gmail.com
 * @since 2013-5-23
 * @version 0.1
 */
@Controller("RESTAPI")
public class RESTAPIServlet implements HttpRequestHandler {
	
	private static final Logger L = LoggerFactory.getLogger(RESTAPIServlet.class);

	private static final int	GET				= 1;
	private static final int	POST			= 2;
	private static final int	PUT				= 3;
	private static final int	DELETE			= 4;

	private static final int	JSON			= 1;
	private static final int	JSONP			= 2;

	/** 请求列表的最大项目数 */
	private static final int	MAX_LIST_COUNT	= 50;
	
	private static final String MSG				= "msg";
	private static final String STATUS			= "status";

	private static Pattern		pattern			= Pattern.compile("(\\d{1,})(\\..*$)?");
	private static Matcher		matcher;

	@Inject
	private UserService userService;
	
	
	/**
	 * 提取uri中的id
	 * @param uri
	 * @return 解析后的id索引，如果没有（代表操作的是一个列表）返回-1
	 */
	private static final int resolveId(String uri) {
		matcher = pattern.matcher(uri);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		} else {
			return -1;
		}
	}
	
	/**
	 * 解析HTTP method，并且返回相应的method code供switch！若方法不受支持则抛出异常。变长参数为空意味着所有的方法都支持！
	 * @param request
	 * @return 方法的指代整形值
	 */
	private static final int supportedMethod(HttpServletRequest request, int...methods) {
		String method = request.getMethod();
		int code = -1;
		if (method.equalsIgnoreCase("GET")) {
			code = GET;
		} else if (method.equalsIgnoreCase("POST")) {
			code = POST;
		} else if (method.equalsIgnoreCase("PUT")) {
			code = PUT;
		} else if (method.equalsIgnoreCase("DELETE")) {
			code = DELETE;
		} else {
			throw new IllegalArgumentException("不存在[HTTP Method = " + method + "]这个方法！");
		}
		
		int in = 0;
		if (methods.length == 0) {
			in = 4;
		} else {
			for (int i = 0; i < methods.length; i++) {
				if (methods[i] == code) {
					in++;
					break;
				}
			}
		}
		if (in == 0) {
			throw new UnsupportedOperationException("Unsupported HTTP Method: " + method);
		}
		
		return code;
	}
	
	/**
	 * 将请求的参数转换为整形，若为空或者空串以及转型错误会抛出相应异常。
	 * 
	 * @param paramName 参数名
	 * @param request
	 * @return 整形值
	 */
	private static final int convertParam2Int(String paramName, HttpServletRequest request) {
		int result = Integer.MIN_VALUE;
		String str = request.getParameter(paramName);
		Assert.hasLength(str, paramName + " 参数不能为空！");
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			L.error("转换参数：{} 为整形时出现异常！", e);
			throw new IllegalArgumentException(String.format("请求参数[%s]转换异常！", paramName), e);
		}
//		拦截一下请求的列表项目大小
		if (paramName.equalsIgnoreCase("count")) {
			if (result > MAX_LIST_COUNT || result < 0) {
				throw new IllegalArgumentException("请求列表参数count为负或者大于" + MAX_LIST_COUNT);
			}
		}
		return result;
	}
	
	/** 返回何种视图 */
	private static final int whichViewType(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		if (callback != null && callback.length() > 0 && request.getMethod().equalsIgnoreCase("GET")) {
			return JSONP;
		}
		return JSON;
	}
	
	/** 返回视图 */
	private static final void render(HttpServletRequest request, HttpServletResponse response, String json) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (whichViewType(request) == JSONP) {
			String callback = request.getParameter("callback");
			json = "try{" + callback + "(" + json + ")}catch(e){}";
		}
		writer.write(json);
		writer.flush();
		writer.close();
	}
	
	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String result = null;
		try {
			result = dispatcher(request, response);
		} catch (Throwable e) {
			L.error("APIServlet", e);
			Throwable cause = e.getCause();
			result = String.format("{\"status\":0,\"msg\":\"%s\",\"reason\":\"%s\"}", e.getMessage(), cause != null ? cause.getMessage() : "对不起，我们没有能收集足够的错误信息，请您稍后再试！");
		}
		render(request, response, result);
	}
	
	private static String dispatcher(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
		String uri = request.getRequestURI();
		if (RegexUtils.contains(uri, "/topics")) {
			int id = resolveId(uri);
			if (id < 0) {
//				操作集合
				int type = convertParam2Int("type", request);
				int count = convertParam2Int("count", request);
				switch (type) {
				case R.topic.LATEST_TOPICS:
					return latestTopics(request, response, count);
				case R.topic.REFRESH:
					return refreshTopics(request, count);
				case R.topic.FETCH_MORE:
					return fetchMoreTopics(request, count);
				case R.topic.FORUM_LATEST_TOPICS:
					int fid = convertParam2Int("fid", request);
					return latestTopics(request, response, fid, count);
				case R.topic.FORUM_REFRESH:
					return refreshForumTopisc(request, response, count);
				case R.topic.FORUM_FETCH_MORE:
					return fetchMoreTopics(request, response, count);
				case R.topic.CLASSICS:
					return classics(request, response, count);
				default:
					throw new IllegalArgumentException("对不起，没有[type=" + type + "]这个选项！");
				}
			} else {
//				操作单个对象
				int method = supportedMethod(request);
				switch (method) {
				case GET:
					try {
						Topic topic = Topic.get(id);
						return mappingTopic2Json(topic);
					} catch (ObjectNotFoundException e) {
						throw new IllegalArgumentException(String.format("没有找到[id=%d]的帖子！", id), e);
					}
				default:
					break;
				}
			}
		} else if (RegexUtils.contains(uri, "/replies")) {
//			对回复进行操作
			int id = resolveId(uri);
			if (id < 0) {
//				列表项
				int type = convertParam2Int("type", request);
				int count = convertParam2Int("count", request);
				switch (type) {
				case R.reply.LATEST:
					return latestReplies(request, response, count);
				default:
					break;
				}
			}
		} else if (RegexUtils.contains(uri, "/forums")) {
			int id = resolveId(uri);
			if (id < 0) {
//				列表项
				
			} else {
				int method = supportedMethod(request);
				switch (method) {
				case GET:
					return forum2Json(id); 
				default:
					break;
				}
			}
		} else if (RegexUtils.contains(uri, "/users")) {
			if (uri.contains("/users/login")) {
				return user2Json(request);
			}
			int id = resolveId(uri);
			if (id < 0) {
//				集合操作
			} else {
				int method = supportedMethod(request);
				switch (method) {
				case GET:
					return user2Json(id);

				default:
					break;
				}
			}
		}
		return null;
	}
	
	/** 获取全论坛最最新的帖子。 */
	private static String latestTopics(HttpServletRequest request, HttpServletResponse response, int count) throws JSONException, IOException {
		supportedMethod(request, GET);
		List<Topic> topics = Topic.getLatesTopics(count);
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (Topic t : topics) {
			data.add(R.resolveTopic2Map(t));
		}
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("topics", data);
		return json.toString();
	}
	
	private static String mappingTopic2Json(Topic t) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(R.topic.ADDED_TIME, t.getCreationTime().getTime());
		map.put(R.topic.AUTHOR_NICK, t.getUser().getNick());
		map.put(R.topic.CLICK_TIMES, t.getClickTimes());
		map.put(R.ID, t.getId());
//		map.put(R.topic.LAST_REPLIED_TIME, t.getReplyTime().getTime());
//		map.put(R.topic.LAST_REPLIED_USER_NICK, t.getReplyUser().getNick());
		map.put(R.topic.REPLIED_TIMES, t.getReplyTimes());
		map.put(R.topic.TITLE, t.getTitle());
		map.put(R.topic.FORUM, t.getForum().getName());
		map.put(R.topic.CONTENT, t.resolveContent());
		map.put("author", R.resolveUser2Map(t.getUser()));
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("topic", map);
		return json.toString();
	}
	
	/** 刷新最新帖子列表，需要客户端传递一个本地最新的topic id标识 */
	private static String refreshTopics(HttpServletRequest request, int count) throws JSONException {
		supportedMethod(request, GET);
		
		int topTid = convertParam2Int("top_tid", request);
		List<Topic> topics = Topic.refresh(topTid, count);
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (Topic t : topics) {
			data.add(R.resolveTopic2Map(t));
		}
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("topics", data);
		return json.toString();
	}
	
	private static String fetchMoreTopics(HttpServletRequest request, int count) throws JSONException {
		supportedMethod(request, GET);
		
		int lastTid = convertParam2Int("last_tid", request);
		List<Topic> topics = Topic.fetchMore(lastTid, count);
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (Topic t : topics) {
			data.add(R.resolveTopic2Map(t));
		}
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("topics", data);
		return json.toString();
	}
	
	/** 查看某帖子的最新评论 */
	private static String latestReplies(HttpServletRequest request, HttpServletResponse response, int count) throws JSONException {
		supportedMethod(request, GET);
		int lastRid = convertParam2Int("last_rid", request);
		int tid = convertParam2Int("tid", request);
		List<Reply> replies = Reply.fetchReplies(tid, lastRid, count);
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>(replies.size());
		Map<String, Object> m = null;
		for (Reply r : replies) {
			m = R.resolveReply2Map(r);
			data.add(m);
		}
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("replies", data);
		return json.toString();
	}
	
	private static String latestTopics(HttpServletRequest request, HttpServletResponse response, int fid, int count) throws JSONException {
		supportedMethod(request, GET);
		List<Topic> topics = Topic.fetchLatestTopics(fid, count);
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (Topic t : topics) {
			data.add(R.resolveTopic2Map(t));
		}
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("topics", data);
		return json.toString();
	}
	
	private static String refreshForumTopisc(HttpServletRequest request, HttpServletResponse response, int count) throws JSONException {
		supportedMethod(request, GET);
		
		int topTid = convertParam2Int("top_tid", request);
		int fid = convertParam2Int("fid", request);
		
		List<Topic> topics = Topic.refresh(fid, topTid, count);
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (Topic t : topics) {
			data.add(R.resolveTopic2Map(t));
		}
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("topics", data);
		return json.toString();
	}
	
	private static String fetchMoreTopics(HttpServletRequest request, HttpServletResponse response, int count) throws JSONException {
		supportedMethod(request, GET);
		
		int fid = convertParam2Int("fid", request);
		int lastTid = convertParam2Int("last_tid", request);
		
		List<Topic> topics = Topic.fetchMore(fid, lastTid, count);
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (Topic t : topics) {
			data.add(R.resolveTopic2Map(t));
		}
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("topics", data);
		return json.toString();
	}
	
	private static String classics(HttpServletRequest request, HttpServletResponse response, int count) throws JSONException {
		supportedMethod(request, GET);
		int fid = convertParam2Int("fid", request);
		List<Topic> topics = Topic.fetchClassics(fid, count);
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (Topic t : topics) {
			data.add(R.resolveTopic2Map(t));
		}
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("topics", data);
		return json.toString();
	}
	
	private static String forum2Json(int fid) throws JSONException {
		Forum f = null;
		try {
			 f = Forum.get(fid);
		} catch (ObjectNotFoundException e) {
			L.error("forum not found!", e);
			throw new IllegalArgumentException("对不起，您要找的forum不存在啊- -");
		}
		Map<String, Object> m = R.resolveForum2Map(f);
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("forum", m);
		return json.toString();
	}
	
	private static final String user2Json(int uid) throws JSONException {
		User u = null;
		try {
			u = User.get(uid);
		} catch (ObjectNotFoundException e) {
			L.error("user not found @id = " + uid, e);
			throw new IllegalArgumentException("对不起，您要找的用户不存在啊- -");
		}
		Map<String, Object> m = R.resolveUser2Map(u);
		JSONObject json = new JSONObject();
		json.put(STATUS, 1);
		json.put("user", m);
		return json.toString();
	}
	
	private static final String user2Json(HttpServletRequest request) {
		String username = request.getParameter("username");
		User u;
		try {
			u = User.getByUsername(username);
		} catch (ObjectNotFoundException e) {
			return "{\"status\":0}";
		}
		String password = request.getParameter("password");
		if (u == null || !u.getPassword().equals(Util.hash(password))) {
			return "{\"status\":0}";
		} else {
			Map<String, Object> map = R.resolveUser2Map(u);
			map.put("status", 1);
			return new JSONObject(map).toString();
		}
	}
	
}
