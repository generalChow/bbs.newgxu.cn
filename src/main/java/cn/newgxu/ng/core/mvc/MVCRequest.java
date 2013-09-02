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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @author longkai
 * @since 2013-2-27
 * @version 1.0
 */
public class MVCRequest extends HttpServlet {

	private static final long	serialVersionUID	= 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		long t1 = System.currentTimeMillis();
		MVCProcess.mvc(req, resp);
		System.out.println(System.currentTimeMillis() - t1);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		long t1 = System.currentTimeMillis();
		MVCProcess.mvc(req, resp);
		System.out.println(System.currentTimeMillis() - t1);
	}
	
}
