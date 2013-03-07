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

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.TopicModel;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;

/**
 * 
 * @author longkai
 * @since 2013-3-7
 * @version 1.0
 */
@MVCHandler(value = "mTopicController", namespace = "/ng/m/")
@Scope("prototype")
public class TopicController {

	private static final Logger L = LoggerFactory.getLogger(TopicController.class);
	
	@Inject
	private ForumService forumService;
	
	@MVCMapping("topic")
	public String view(TopicModel model, HttpServletRequest request, ModelAndView mav) throws BBSException {
		User user = (User) request.getSession().getAttribute("_user");
		model.setUser(user);
		forumService.topic(model);
		mav.addModel("model", model);
		return "mobile/topic.jsp";
	}
	
}
