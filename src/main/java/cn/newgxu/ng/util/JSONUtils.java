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
package cn.newgxu.ng.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;

/**
 * 与JSON相关的一些工具类方法。
 * 
 * @author longkai
 * @since 2013-3-9
 * @version 1.0
 */
public class JSONUtils {

	private static final Logger	L	= LoggerFactory.getLogger(JSONUtils.class);

	public static String object2JSON(Object obj) {
		StringBuilder sb = new StringBuilder("{");
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Class<?> type = fields[i].getType();
			sb.append("\"").append(fields[i].getName()).append("\":");
			if (type.equals(String.class) || type.isPrimitive()
					|| WrapperUtils.isWrapper(type)) {
				sb.append("\"");
				try {
					sb.append(clazz.getMethod(
							StringUtils.getter(fields[i].getName()))
							.invoke(obj));
				} catch (Exception e) {
					L.error("对象转换json出错！-> {}, 注入空串!", e);
				}
				sb.append("\"");
				// 没有到结尾，追加逗号！
				if (i != fields.length - 1) {
					sb.append(",");
				}
			} else if (type.isArray()) {
				sb.append("[");
				try {
					Object objs = clazz.getMethod(
							StringUtils.getter(fields[i].getName())).invoke(
							type);
				} catch (Exception e) {
					L.error("对象转换json出错！-> {}, 注入空串!", e);
				}
				sb.append("]");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public static String toJSONString(String name, Object value) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"").append(name).append("\":\"").append(value).append("\"");
		return sb.append("}").toString();
	}

	public static String toJSONString(JSONObject obj) {
		return toJSONString(obj.getName(), obj.getValue());
	}

	public static String toJSONString(JSONArray array) {
		StringBuilder sb = new StringBuilder("{");
		int i = 0;
		Map<String, Object> pairs = array.getPairs();
		for (String name : pairs.keySet()) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("\"").append(name).append("\":").append("\"").append(pairs.get(name)).append("\"");
			i++;
		}
		return sb.append("}").toString();
	}
	
	public static String toJSONString(JSONArray...arrays) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < arrays.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			
			JSONArray array = arrays[i];
			sb.append("{");
			
			Map<String, Object> pairs = array.pairs;
			int j = 0;
			for (String name : pairs.keySet()) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append("\"").append(name).append("\":\"").append(pairs.get(name)).append("\"");
				j++;
			}
			sb.append("}");
		}
		return sb.append("]").toString();
	}

	public static class JSONObject {

		private String	name;
		private Object	value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

	}

	public static class JSONArray {

		private Map<String, Object>	pairs;

		public JSONArray() {
			pairs = new HashMap<String, Object>();
		}

		public void put(String name, Object value) {
			this.pairs.put(name, value);
		}

		public Object get(String name) {
			return this.pairs.get(name);
		}

		public Map<String, Object> getPairs() {
			return pairs;
		}

		public void setPairs(Map<String, Object> pairs) {
			this.pairs = pairs;
		}

	}
}
