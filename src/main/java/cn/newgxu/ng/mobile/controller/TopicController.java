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

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.RemoteContent;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.CreateTopicModel;
import cn.newgxu.bbs.web.model.TopicModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.View;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCInterceptor;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
import cn.newgxu.ng.core.mvc.annotation.MVCParamMapping;
import cn.newgxu.ng.mobile.interceptor.LoginInterceptor;
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
	
	@MVCMapping("view")
	public String view(TopicModel model, HttpServletRequest request, ModelAndView mav) throws BBSException {
		User user = (User) request.getSession().getAttribute("_user");
		model.setUser(user);
		forumService.topic(model);
		mav.addModel("model", model);
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
	
	@MVCMapping("load_forums")
	public View loadForums(@MVCParamMapping("areaId") int areaId) throws Exception {
		View view = new View().setType(ViewType.JSON);
//		String json = JSONArray.toJSONString();
//		TODO: 使用原有的jsonsimple包无法解析到json。。。。
		String json = RemoteContent.getRemoteResult("http://210.36.16.163/API/GET.php?action=wish&sort=add_time&desc=1&count=4", "gbk");
		view.setContent(json);
		L.debug("返回json数据：{}, array:{}", json, Area.get(areaId).getForums());
		return view;
	}
	
}
