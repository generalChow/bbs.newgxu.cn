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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.newgxu.ng.core.mvc.annotation.MVCParamMapping;

/**
 * 
 * @author longkai
 * @since 2013-2-28
 * @version 1.0
 */
public class Bean {

	private int			age;
	private String		name;
	private String[]	texts;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getTexts() {
		return texts;
	}

	public void setTexts(@MVCParamMapping() String[] texts, int c, @MVCParamMapping("type") boolean b) {
		this.texts = texts;
	}
	
	public void setTexts(String[] texts) {
		this.texts = texts;
	}

	@Override
	public String toString() {
		return "Bean [age=" + age + ", name=" + name + ", texts="
				+ Arrays.toString(texts) + "]";
	}
	
	public static void main(String[] args) {
//		Method[] methods = Bean.class.getDeclaredMethods();
//		for (int i = 0; i < methods.length; i++) {
////			System.out.println(methods[i]
//			Annotation[][] ans = methods[i].getParameterAnnotations();
//			for (int j = 0; j < ans.length; j++) {
////				System.out.println(ans[j]);
//				for (int k = 0; k < ans[j].length; k++) {
//					System.out.println(j + " " + k + ans[j][k]);
//				}
//			}
//		}
//		Object obj = new Object();
//		Date d = new Date();
//		System.out.println(obj.getClass().isAssignableFrom(d.getClass()));
//		System.out.println(d.getClass().isAssignableFrom(obj.getClass()));
//		System.out.println(Date.class.isAssignableFrom(Time.class));
//		System.out.println(Throwable.class.isAssignableFrom(RuntimeException.class));
		List<Integer> is = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			is.add(i);
		}
		Integer[] is2 = is.toArray(new Integer[0]);
//		System.out.println(is2);
		System.out.println(Arrays.toString(is2));
		
//		Method[] methods = Bean.class.getMethods();
//		System.out.println(methods.length);
//		List<Method> methods2 = new ArrayList<Method>();
//		for (int i = 0; i < methods.length; i++) {
//			methods2.add(methods[i]);
//		}
//		
//		methods = null;
//		methods = methods2.toArray(new Method[0]);
//		System.out.println(methods.length);
//		System.out.println(Arrays.toString(methods));
//		
//		System.out.println("".charAt(0));
		Integer i = null;
		String s = "123a";
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			
		}
		System.out.println(i);
	}
	
	
	

}
