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
package common;

import cn.newgxu.ng.mobile.Bean;

/**
 * 
 * @author longkai
 * @since 2013-3-1
 * @version 1.0
 */
public class ReferTest {
	
	public static void doSth(Bean b) {
//		b.setAge(10);
//		b = new Bean();
//		System.out.println(b);
//		Bean tmp = b;
//		tmp.setAge(100);
//		System.out.println(tmp);
//		System.out.println(b);
//		System.out.println(tmp == b);
		b.setAge(1000);
	}
	
	public static void main(String[] args) {
//		Bean b = null;
		Bean b = new Bean();
		b.setAge(11);
		System.out.println(b);
		doSth(b);
		System.out.println(b);
//		System.out.println(b);
	}
	
}
