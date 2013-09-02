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

/**
 * java包装器类工具，不知道有什么用。。。
 * 
 * @author longkai
 * @since 2013-2-28
 * @version 1.0
 */
public class WrapperUtils {

	/** 基本的8种包装类型 */
	private static Class<?>[] wrappers;
	
	static {
		wrappers = new Class<?>[] {
				Byte.class,
				Short.class,
				Integer.class,
				Long.class,
				Float.class,
				Double.class,
				Character.class,
				Boolean.class
		};
	}
	
	/**
	 * 判断类型是否是包装类型。
	 * @param type
	 */
	public static boolean isWrapper(Class<?> type) {
		for (int i = 0; i < wrappers.length; i++) {
			if (type.equals(wrappers[i])) {
				return true;
			}
		}
		return false;
	}
	
}
