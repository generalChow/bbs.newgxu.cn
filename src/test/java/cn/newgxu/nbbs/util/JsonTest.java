/*
 * Copyright (c) 2001-2013 newgxu.cn <the original author or authors>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package cn.newgxu.nbbs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author longkai
 * @email im.longkai@gmail.com
 * @since 2013-5-23
 * @version 0.1
 */
public class JsonTest {

	public class JavaBean {
		private int		id;
		private String	name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return String.format("id: %d, name: %s", id, name);
		}
	}

	private JavaBean	javaBean;
	private JavaBean javaBean2;

	@Before
	public void init() {
		javaBean = new JavaBean();
		javaBean.id = 1;
		javaBean.name = "龙凯";
		
		javaBean2 = new JavaBean();
		javaBean2.id = 2;
		javaBean2.name = "刘玥伽";
	}

	@Test
	public void test1() throws JSONException {
		System.out.println(javaBean);
		JSONObject json = new JSONObject(javaBean);
		json.put("i", 100);
		JSONObject json2 = new JSONObject(javaBean);
		json.put("javabean2", json2);
		
//		JSONArray jsonArray = new JSONArray("beans");
//		for (int i = 0; i < 2; i++) {
//			jsonArray.put(i, javaBean);
//		}
		List<JavaBean> beans = new ArrayList<JsonTest.JavaBean>();
		beans.add(javaBean);
		beans.add(javaBean);
		JSONArray jsonArray = new JSONArray(beans, false);
		System.out.println(jsonArray.toString());
		
		json.putOnce("beans", jsonArray);
		System.out.println(json.toString());
	}
	
	@Test
	public void test2() throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("one", 1);
		map.put("two", "two");
		JSONObject json = new JSONObject(map);
		System.out.println(json.toString());
		
		JSONObject json2 = new JSONObject();
		map.put("json", new JSONObject(javaBean));
		json2.put("map", map);
		json2.put("map2", map);
		System.out.println(json2.toString());
	}
	
	@Test
	public void test3() throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 3; i++) {
			map = new HashMap<String, Object>();
			map.put("key" + i, i);
			map.put("value" + i, -i);
			data.add(map);
//			map.clear();
		}
		json.put("users", data);
		json.put("status", "ok");
		System.out.println(json);
	}
	
	@Test
	public void test4() throws JSONException {
		Map<String, Object> s = new HashMap<String, Object>();
		s.put("a", 1);
		s.put("b", "xxx");
		JSONObject json = new JSONObject();
		json.put("m", s);
		System.out.println(json);
	}

	@Test
	public void test5() throws JSONException {
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("x", "x");
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("y", "y");
		m1.put("z", m2);
		JSONObject json = new JSONObject();
		json.put("xxx", m1);
		System.out.println(json.toString());
	}
	
}
