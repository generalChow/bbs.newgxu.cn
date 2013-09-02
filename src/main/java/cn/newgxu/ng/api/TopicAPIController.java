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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.jpamodel.ObjectNotFoundException;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
import cn.newgxu.ng.util.RegexUtils;

/**
 * 
 * @author longkai
 * @email im.longkai@gmail.com
 * @since 2013-5-22
 * @version 0.1
 */
@MVCHandler(value = "mTopicAPIController", namespace = "/ng/api/")
public class TopicAPIController {

	private static final Logger	L	= LoggerFactory.getLogger(TopicAPIController.class);
	
	private static final String	TAG	= TopicAPIController.class.getSimpleName();
	
	public static final String ID = "_ID";
	public static final String TITLE = "title";
	public static final String ADDED_TIME = "added_time";
	public static final String CLICK_TIMES = "click_times";
	public static final String AUTHOR_NICK = "author_nick";
	public static final String REPLIED_TIMES = "replied_times";
	public static final String LAST_REPLIED_TIME = "last_replied_time";
	public static final String LAST_REPLIED_USER_NICK = "last_replied_user_nick";
	
	public static final String CONTENT = "content";

	@MVCMapping("topics")
	public String latestTopics(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String pattern = "\\d{1,}(\\..*$)?";
		
		String result = null;
		if (RegexUtils.contains(pattern, uri)) {
//			请求集合
			int count = Integer.MIN_VALUE;
			try {
				count = Integer.parseInt(request.getParameter("count"));
			} catch (Exception e) {
				throw new IllegalArgumentException("请检查count参数！");
			}
			if (count > 50) {
				count = 50;
			} else if (count < 0) {
				throw new IllegalArgumentException("count参数不能为负！");
			}
			
			String type = request.getParameter("type");
			Assert.hasLength(type, "type参数不能为空！");
			
			List<Topic> topics = null;
			
			int switcher = Integer.parseInt(type);
			switch (switcher) {
			case 1:
				topics = Topic.getLatesTopics(count);
				break;
				
			default:
				topics = Topic.getLatesTopics(count);
			}
			
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < topics.size(); i++) {
				Topic t = topics.get(i);
				JSONObject json = convertTopic2Json(t);
				try {
					jsonArray.put(i, json);
				} catch (JSONException e) {
					L.error(TAG, e);
				}
			}
			result = jsonArray.toString();
			L.debug(TAG, result);
			return "{\"topics\":" + result + "}";
		} else {
//			返回某个帖子
			int id = Integer.parseInt(RegexUtils.fetchFirst(uri, pattern));
			Topic t = null;
			try {
				t = Topic.get(id);
			} catch (ObjectNotFoundException e) {
				L.error(TAG, e);
				return CoreApiController.JSON_NO;
			}
			JSONObject jsonObject = convertTopic2Json(t);
			try {
				jsonObject.put("msg", "ok");
				jsonObject.put(CONTENT, t.getReplieLines(null, false));
			} catch (JSONException e) {
				L.error(TAG, e);
			}
			return "";
		}
	}
	
	public static JSONObject convertTopic2Json(Topic t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ADDED_TIME, t.getCreationTime().getTime());
		map.put(AUTHOR_NICK, t.getUser().getNick());
		map.put(CLICK_TIMES, t.getClickTimes());
		map.put(ID, t.getId());
		map.put(LAST_REPLIED_TIME, t.getReplyTime().getTime());
		map.put(REPLIED_TIMES, t.getReplyTimes());
		map.put(TITLE, t.getTitle());
		map.put(LAST_REPLIED_USER_NICK, t.getReplyUser().getNick());
		return new JSONObject(map);
	}

}
