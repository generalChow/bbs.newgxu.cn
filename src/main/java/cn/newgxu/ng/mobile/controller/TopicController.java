/*
 * Copyright im.longkai@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.newgxu.ng.mobile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;


import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.ReplyLine;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.cache.BBSCache;
import cn.newgxu.bbs.web.model.CreateTopicModel;
import cn.newgxu.bbs.web.model.ModifyModel;
import cn.newgxu.bbs.web.model.ReplyModel;
import cn.newgxu.bbs.web.model.TopicModel;
import cn.newgxu.ng.core.mvc.Model;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.View;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCInterceptor;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
import cn.newgxu.ng.core.mvc.annotation.MVCParamMapping;
import cn.newgxu.ng.mobile.interceptor.LoginInterceptor;
import cn.newgxu.ng.util.InfoLevel;
import cn.newgxu.ng.util.JSONUtils.JSONArray;
import cn.newgxu.ng.util.JSONUtils.JSONObject;
import cn.newgxu.ng.util.JSONUtils;
import cn.newgxu.ng.util.Pigeon;
import cn.newgxu.ng.util.ViewType;

/**
 * 
 * @author longkai
 * @since 2013-3-7
 * @version 1.0
 */
@MVCHandler(value = "mTopicController", namespace = "/ng/m/topic/")
@Scope("prototype")
public class TopicController {

	private static final Logger L = LoggerFactory.getLogger(TopicController.class);
	
	@Inject
	private ForumService forumService;
	
//	蛋碎一地的做法。。。写得太死了。。。
	@MVCMapping("view")
	public String view(TopicModel model, HttpServletRequest request, ModelAndView mav, @MVCParamMapping("page") int page) throws BBSException {
		User user = (User) request.getSession().getAttribute("_user");
		model.setUser(user);
		model.setPage(page);
		model.getPagination().setActionName(request.getRequestURI());
		model.getPagination().setParamMap(request.getParameterMap());
		forumService.topic(model);
		mav.addModel("model", model);
//		int size = model.getReplieLines().size();
//		mav.addModel("count", size);
//		Reply lastReply = model.getReplieLines().get(size - 1).getReply();
//		mav.addModel("last_rid", lastReply.getId());
		return "mobile/topic.jsp";
	}
	
	@MVCMapping("create")
	@MVCInterceptor(interceptors = LoginInterceptor.class)
	public String create(CreateTopicModel model, ModelAndView mav, HttpSession session) throws BBSException {
		model.setUser((User) session.getAttribute("_user"));
		forumService.createTopic(model);
		mav.addModel("model", model);
		return "mobile/new_topic.jsp";
	}
	
	@MVCMapping("submit")
	@MVCInterceptor(interceptors = LoginInterceptor.class)
	public ModelAndView submit(CreateTopicModel model, ModelAndView mav, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("_user");
		L.info("用户：{} 发表新主题, 标题：{}，内容：{}", user.getUsername(), model.getTitle(), model.getContent());

		View view = new View().setType(ViewType.JSON);
		Topic topic = null;
		JSONArray array = new JSONArray();
		model.setUser(user);
		try {
			topic = forumService.createTopicDo(model);
		} catch (Exception e) {
			L.error("发表帖子异常！", e);
			array.put("status", "error");
			array.put("info", e.getMessage());
			mav.setView(view.setContent(JSONUtils.toJSONString(array)));
			return mav;
		}
		
		array.put("status", "success");
		array.put("topicId", topic.getId());
		array.put("forumId", topic.getForum().getId());
		
//		刷新缓存。
		BBSCache.topicCount2++;
		mav.setView(view.setContent(JSONUtils.toJSONString(array)));
		return mav;
	}
	
	@MVCMapping("try_update")
	@MVCInterceptor(interceptors = LoginInterceptor.class)
	public ModelAndView tryUpdate(ModifyModel model, HttpSession session, ModelAndView mav) throws JSONException, BBSException {
		User user = (User) session.getAttribute("_user");
		L.info("用户：{} 尝试修改帖子！", user.getUsername());
		model.setUser(user);
		forumService.modify(model);
		mav.addModel("model", model);
		return mav.setViewName("mobile/update_topic.jsp");
		//		org.json.JSONObject jsonObject = new org.json.JSONObject();
//		model.setUser(user);
//		view.setType(ViewType.JSON);
//		try {
//			forumService.modify(model);
//		} catch (BBSException e) {
//			L.error("用户修改帖子出错！", e);
//			jsonObject.put("status", "no");
//			jsonObject.put("info", e.getMessage());
//			return view.setContent(jsonObject.toString());
//		}
//		jsonObject.put("status", "ok");
//		jsonObject.put("tid", model.getTopicId());
//		jsonObject.put("fid", model.get)
//		jsonObject.put("title", model.getTitle());
//		jsonObject.put("content", model.getContent());
//		return view.setContent(jsonObject.toString());
	}
	
	@MVCMapping("update")
	@MVCInterceptor(interceptors = LoginInterceptor.class)
	public View update(ModifyModel model, HttpSession session, View view) throws Exception {
		User user = (User) session.getAttribute("_user");
		model.setUser(user);
		org.json.JSONObject jsonObject = new org.json.JSONObject();
		view.setType(ViewType.JSON);
		try {
			forumService.modifyDo(model);
		} catch (Throwable e) {
			L.error("修改内容失败", e);
			jsonObject.put("status", "no");
			jsonObject.put("info", e.getMessage());
			return view.setContent(jsonObject.toString());
		}
		jsonObject.put("status", "ok");
		jsonObject.put("tid", model.getTopicId());
		jsonObject.put("fid", model.getTopic().getForum().getId());
		return view.setContent(jsonObject.toString());
	}
	
	@MVCMapping("load_forums")
	public View loadForums(@MVCParamMapping("areaId") int areaId) throws Exception {
		View view = new View().setType(ViewType.JSON);
		List<Forum> forums = Area.get(areaId).getForums();
		JSONArray[] arrays = new JSONArray[forums.size()];
		for (int i = 0; i < arrays.length; i++) {
			arrays[i] = new JSONArray();
			arrays[i].put("areaId", forums.get(i).getId());
			arrays[i].put("name", forums.get(i).getName());
		}
		view.setContent(JSONUtils.toJSONString(arrays));
		return view;
	}
	
	// 回复帖子，并非对于回复而回复。
	@MVCMapping("reply_topic")
	@MVCInterceptor(interceptors = LoginInterceptor.class)
	public ModelAndView replyTopic(ReplyModel model, ModelAndView mav, HttpSession session) throws JSONException {
		User user = (User) session.getAttribute("_user");
		L.info("用户{} 回复主题, 回复内容：", user.getNick(), model.getContent());
		model.setUser(user);
		org.json.JSONObject jsonObject = new org.json.JSONObject();
		View view = new View().setType(ViewType.JSON);
		try {
			forumService.createReply(model);
		} catch (Exception e) {
			L.error("用户回复主题时异常！", e);
			jsonObject.put("status", "no");
			jsonObject.put("info", e.getMessage());
			return mav.setView(view.setContent(jsonObject.toString()));
		}
		jsonObject.put("status", "ok");
		jsonObject.put("info", "回复主题成功！");
		return mav.setView(view.setContent(jsonObject.toString()));
	}
	
//	这个是针对非主题的回复处理！
	@MVCMapping("reply")
	@MVCInterceptor(interceptors = LoginInterceptor.class)
	public View reply(ReplyModel model, View view, HttpSession session) throws JSONException {
		User user = (User) session.getAttribute("_user");
		L.info("用户{} 回复, 回复内容：", user.getNick(), model.getContent());
		model.setUser(user);
		view.setType(ViewType.JSON);
		org.json.JSONObject jsonObject = new org.json.JSONObject();
		
		try {
			forumService.replyFastDo(model);
		} catch (Exception e) {
			L.error("用户回复失败！", e);
			jsonObject.put("status", "no");
			jsonObject.put("info", e.getMessage());
			return view.setContent(jsonObject.toString());
		}
		jsonObject.put("status", "ok");
		return view.setContent(jsonObject.toString());
	}
	
//	这个是请求回复！
	@MVCMapping("request_reply")
	@MVCInterceptor(interceptors = LoginInterceptor.class)
	public View replyRequest(ReplyModel model, View view, HttpSession session) throws JSONException {
		User user = (User) session.getAttribute("_user");
		model.setUser(user);
		L.info("用户{} 请求回复, 回复id：{}", user.getNick(), model.getReplyId());
		org.json.JSONObject jsonObject = new org.json.JSONObject();
		view.setType(ViewType.JSON);
		try {
			forumService.replyFast(model);
		} catch (BBSException e) {
			L.error("请求回复失败！", e);
			jsonObject.put("status", "no");
			jsonObject.put("info", e.getMessage());
			return view.setContent(jsonObject.toString());
		}
		jsonObject.put("status", "ok");
		jsonObject.put("content", model.getContent());
		jsonObject.put("reply_id", model.getReplyId() + "");
		return view.setContent(jsonObject.toString());
	}
	
//	很诡异，在视图上居然解析不了rid和floor。。。fuck
	@MVCMapping("load_replies")
	public View loadReplies(@MVCParamMapping("offset") int offset, @MVCParamMapping("tid") int topicId, View view) throws JSONException {
		List<Reply> replies = Topic.getReples(topicId, offset, 10);
		
		org.json.JSONArray jsonArray = new org.json.JSONArray();
		
		for (int i = 0; i < replies.size(); i++) {
			Reply r = replies.get(i);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("r_id", r.getId() + "");
			m.put("_floor", (i + 1) + "");
			User u = r.getPostUser();
			m.put("face", u.getFace());
			m.put("nick", u.getNick());
			m.put("u_title", u.getTitle());
			m.put("groupName", u.getGroupNameDisplay());
			m.put("exp", u.getExp());
			m.put("totalPost", u.getTotalPostDisplay());
			m.put("content", r.getContentBean().getContent());
			m.put("ctime", r.getPostTime());
			m.put("profile", u.getIdiograph());
			jsonArray.put(i, m);
		}
		return view.setType(ViewType.JSON).setContent(jsonArray.toString());
	}
	
}
