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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;

import cn.newgxu.ng.core.mvc.Model;
import cn.newgxu.ng.core.mvc.ModelAndView;
import cn.newgxu.ng.core.mvc.MVCProcess;
import cn.newgxu.ng.core.mvc.View;
import cn.newgxu.ng.core.mvc.annotation.MVCExceptionpHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
import cn.newgxu.ng.core.mvc.annotation.MVCParamMapping;
import cn.newgxu.ng.util.StringUtils;
import cn.newgxu.ng.util.ViewType;

/**
 * 
 * @author longkai
 * @since 2013-2-27
 * @version 1.0
 */
@MVCHandler
@Scope("prototype")
public class HelloAjaxHandler {
	
	@MVCExceptionpHandler({Exception.class})
	public String exp(Exception e, HttpServletRequest request) {
		System.out.println(request);
		System.out.println(e.getClass());
		System.out.println(e.getMessage());
		System.out.println("handle now...");
		return "t.jsp";
	}

	@MVCMapping("/hello")
	public View hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setCharacterEncoding("utf-8");
//		response.getWriter().write("hello, world! 我叫龙凯！");
		return new View().setType(ViewType.AJAX).setContent("hello, world!");
	}
	
	@MVCMapping("/nima")
	public View nima(HttpServletResponse response, Model model, int i, String str, Date birthday) throws IOException {
//		response.getWriter().write("nima");
		model.add("name", "longkai").add("i", i).add("s", str).add("day", birthday);
		return new View().setType(ViewType.JSP).setViewName("t.jsp");
	}
	
	@MVCMapping({"/ha", "/longkai"})
	public void haha() {
		MVCProcess.init();
		System.out.println("hi");
	}
	
	@MVCMapping("/inject")
//	@MVCParamMapping([],[])
	public String injectParam(Bean b, @MVCParamMapping("bool") boolean bool) {
		System.out.println(bool);
		System.out.println(b);
		return "t.jsp";
	}
	
	@MVCMapping("/mov")
	public ModelAndView hi(@MVCParamMapping(value = "i") int i) {
		System.out.println(i);
		ModelAndView mov = new ModelAndView();
		mov.addModel("i", i);
		throw new RuntimeException("test");
//		return mov.setViewName("/template/mobile/t.jsp");
	}
	
}
