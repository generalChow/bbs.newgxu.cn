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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.web.cache.BBSCache;
import cn.newgxu.bbs.web.model.IndexModel;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;

/**
 * 
 * @author longkai
 * @since 2013-3-7
 * @version 1.0
 */
@MVCHandler(value = "mHomeConctroller", namespace = "/ng/m/")
@Scope("prototype")
public class HomeConctroller {

	private static final Logger L = LoggerFactory.getLogger(HomeConctroller.class);
	
	@MVCMapping({"index", "home"})
	public ModelAndView index(ModelAndView mav) {
		IndexModel index = new IndexModel();
		index.setAreas(Area.getAreas());
		index.setPubGoodTopics(BBSCache.getGoodTopicCache());
		index.setLatestTopics(Topic.getLatesTopics(10));
		index.setPubHotTopics(BBSCache.getHotTopicCache());
		index.setNotices(BBSCache.getNoticesCache());
		mav.addModel("index", index).setViewName("mobile/index.jsp");
		return mav;
	}
	
}
