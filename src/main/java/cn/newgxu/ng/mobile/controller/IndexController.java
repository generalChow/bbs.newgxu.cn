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
import org.springframework.stereotype.Controller;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.IndexModel;

/**
 * 
 * @author longkai
 * @since 2013-2-27
 * @version 1.0
 */
@Controller("mIndexController")
@Scope("prototype")
public class IndexController extends AbstractBaseAction {

	private static final long	serialVersionUID	= 4572679663192480676L;
	
	private static final Logger L = LoggerFactory.getLogger(IndexController.class);
	
	private IndexModel model = new IndexModel();

	@Override
	public Object getModel() {
		return model;
	}

	@Override
	public String execute() throws Exception {
		L.info("访问移动首页");
		model.setAreas(Area.getAreas());
		model.setLatestTopics(Topic.getLatesTopics(10));
		response("hello, world");
		return SUCCESS;
	}

}
