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
package cn.newgxu.ng.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.newgxu.ng.core.mvc.Interceptor;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.annotation.MVCInterceptor;

/**
 * 
 * @author longkai
 * @since 2013-3-3
 * @version 1.0
 */
@MVCInterceptor(/*value = "ishello",*/ url = "/ng/hell.+")
public class HelloInterceptor implements Interceptor {

	@Override
	public boolean before(HttpServletRequest request,
			HttpServletResponse servletResponse) {
		System.out.println("before");
		System.out.println("111");
		return true;
	}

	@Override
	public boolean after(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mav)  {
		System.out.println("after");
		return true;
	}

}
