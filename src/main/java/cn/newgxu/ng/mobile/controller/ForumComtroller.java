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
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.ForumModel;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.View;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
import cn.newgxu.ng.core.mvc.annotation.MVCParamMapping;
import cn.newgxu.ng.util.ViewType;

/**
 * 
 * @author longkai
 * @since 2013-3-5
 * @version 1.0
 */
@MVCHandler(value = "mForumComtroller", namespace = "/ng/m/")
@Scope("prototype")
public class ForumComtroller {

	private static final Logger L = LoggerFactory.getLogger(ForumComtroller.class);
	
	@Inject
	private ForumService forumService;
	
	@MVCMapping("forum")
	public String forum(HttpSession session, ForumModel model, ModelAndView mav) throws BBSException {
		model.setViewer((User) session.getAttribute("_user"));
		forumService.forum(model);
		mav.addModel("model", model);
		return "mobile/forum.jsp";
	}
	
	@MVCMapping("/forum/more")
	public View more(@MVCParamMapping("tid") int topicId, @MVCParamMapping("fid") int forumId) throws JSONException {
		View view = new View().setType(ViewType.JSON);
		List<Topic> latestTopics = Forum.getLatestTopis(topicId, forumId, 10);
		L.debug("共有{}条记录！", latestTopics.size());
		JSONArray jsonArray = new JSONArray();
		
		int i = 0;
		for (Topic t : latestTopics) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("tid", t.getId());
			m.put("title", t.getTitle());
			m.put("author", t.getUser().getNick());
			m.put("uid", t.getUser().getId());
			m.put("ctime", t.getCreationTime());
			m.put("clickTimes", t.getClickTimes());
			jsonArray.put(i++, m);
		}
		
		
		return view.setContent(jsonArray.toString());
	}
	
}
