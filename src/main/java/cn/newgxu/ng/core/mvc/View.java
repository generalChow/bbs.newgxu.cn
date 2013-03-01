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
package cn.newgxu.ng.core.mvc;

import cn.newgxu.ng.util.ViewType;

/**
 * 自定义的一个试图类。
 * 
 * @author longkai
 * @since 2013-2-28
 * @version 1.0
 */
public class View {

	/** 视图的类型，系统默认是jsp */
	private ViewType	type = ViewType.JSP;
	/** 视图的名称 */
	private String		viewName;
	/** 视图的内容，用在ajax上 */
	private String		content;

	public View() {}
	
	public View(ViewType type, String viewName, String content) {
		this.type = type;
		this.viewName = viewName;
		this.content = content;
	}

	public ViewType getType() {
		return type;
	}

	public View setType(ViewType type) {
		this.type = type;
		return this;
	}

	public String getViewName() {
		return viewName;
	}

	public View setViewName(String viewName) {
		this.viewName = viewName;
		return this;
	}

	public String getContent() {
		return content;
	}

	public View setContent(String content) {
		this.content = content;
		return this;
	}
	
	@Override
	public String toString() {
		return "View [type=" + type + ", viewName=" + viewName + ", content="
				+ content + "]";
	}
	
}
