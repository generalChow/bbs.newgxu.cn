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

import javax.servlet.http.HttpServletRequest;

/**
 * 模型和试图，包装了那两个类。注意，这个类主要是用来传递非ajax试图。
 * 
 * @author longkai
 * @since 2013-2-28
 * @version 1.0
 */
public class ModelAndView {

	private View	view;
	private Model	model;

	public ModelAndView() {}
	
	public ModelAndView(HttpServletRequest request) {
		this.model = new Model(request);
	}

	public ModelAndView(View view) {
		this.view = view;
	}
	
	public ModelAndView(Model model) {
		this.model = model;
	}

	public ModelAndView(String viewName) {
		this.view = new View().setViewName(viewName);
	}
	
	public ModelAndView(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	public View getView() {
		return view;
	}

	public ModelAndView setView(View view) {
		this.view = view;
		return this;
	}
	
	public ModelAndView setViewName(String viewName) {
		if (this.view == null) {
			this.view = new View();
		}
		this.view.setViewName(viewName);
		return this;
	}

	public Model getModel() {
		return model;
	}

	public ModelAndView setModel(Model model) {
		this.model = model;
		return this;
	}
	
	public ModelAndView addModel(String key, Object value) {
		if (this.model == null) {
			this.model = new Model();
		}
		this.model.add(key, value);
		return this;
	}
	
	public ModelAndView removeModel(String key) {
		if (this.model == null) {
			model = new Model();
		}
		this.model.remove(key);
		return this;
	}

}
