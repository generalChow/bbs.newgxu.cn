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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器的接口，在请求执行之前与执行之后进行某种拦截。
 * 
 * @author longkai
 * @since 2013-3-3
 * @version 1.0
 */
public interface Interceptor {

	/**
	 * 在请求来到之前进行拦截。
	 * @param request
	 * @param servletResponse
	 * @return true，继续执行，false，停止执行
	 */
	boolean before(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) throws ServletException, IOException;
	
	/**
	 * 响应结束之后进行拦截
	 * @param request
	 * @param response
	 * @param mav
	 * @return true， 放行，返回试图，false，不放行，停止执行
	 */
	boolean after(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) throws ServletException, IOException;
	
}
