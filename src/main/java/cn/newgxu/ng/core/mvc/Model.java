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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 传递试图数据的模型，分为两种，一种是直接注入到request，另一种先暂存到一个临时map中，最后在由系统注入到request中。
 * 使用链式法则。
 * 
 * @author longkai
 * @since 2013-2-28
 * @version 1.0
 */
public class Model {

	private Map<String, Object> models;
	
	private HttpServletRequest request;
	
	private boolean injected = false;
	
	public Model(HttpServletRequest request) {
		injected = true;
		this.request = request;
	}
	
	public Model() {
		this.models = new HashMap<String, Object>();
	}
	
	public Model setRequest(HttpServletRequest request) {
		injected = true;
		this.request = request;
		return this;
	}
	
	public Model add(String key, Object value) {
		if (injected)
			request.setAttribute(key, value);
		else
			models.put(key, value);
		return this;
	}
	
	public Model remove(String key) {
		if (injected)
			request.removeAttribute(key);
		else
			models.remove(key);
		return this;
	}
	
	public Map<String, Object> toMap() {
		if (injected) {
//			return null;
			throw new RuntimeException("使用request注入！没有map转换！");
		}
		return this.models;
	}
	
	public boolean isInjected() {
		return this.injected;
	}
	
	@Override
	public String toString() {
		return "Model [models=" + models + ", request=" + request
				+ ", injected=" + injected + "]";
	}
	
}
