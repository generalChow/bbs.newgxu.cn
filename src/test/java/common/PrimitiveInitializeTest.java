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

import static org.junit.Assert.*;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
/**
 * 
 * @author longkai
 * @since 2013-3-3
 * @version 1.0
 */
public class PrimitiveInitializeTest {

	@Test
	public void test() {
		System.out.println(new JavaBean());
		System.out.println('\0');
		System.out.println(int.class.isAssignableFrom(Integer.class));
		System.out.println(Integer.class.isAssignableFrom(int.class));
	}

	static class JavaBean {
		private int i;
		private short s;
		private byte b;
		private long l;
		private float f;
		private double d;
		private char c;
		private boolean bool;
		@Override
		public String toString() {
			return "JavaBean [i=" + i + ", s=" + s + ", b=" + b + ", l=" + l
					+ ", f=" + f + ", d=" + d + ", c=" + c + ", bool=" + bool
					+ "]";
		}
		
	}
}
