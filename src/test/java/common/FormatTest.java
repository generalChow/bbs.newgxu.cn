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
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import cn.newgxu.ng.util.RegexUtils;

/**
 * 
 * @author longkai
 * @since 2013-3-1
 * @version 1.0
 */
public class FormatTest {

	private String integer = "13";
	private String bool = "true";
	private String d = "3.14";
	private String c = "c";
	
	private String regex;
	
	@Test
	public void testParseInt() {
		regex = "(\\+|-)?\\d+";
		assertThat(RegexUtils.matches(integer, regex), is(true));
		assertThat(RegexUtils.matches(d, regex), is(false));
		
		System.out.println(Double.parseDouble("1"));
	}
	
	@Test
	public void testParseDouble() {
		regex = "(\\+|-)?(\\d+)?\\.\\d+";
		assertThat(RegexUtils.matches(d, regex), is(true));
		assertThat(RegexUtils.matches("-0.3", regex), is(true));
		System.out.println(Integer.MAX_VALUE);
		System.out.println((Integer.MAX_VALUE + "").length());
		System.out.println((Long.MAX_VALUE + "").length());
		System.out.println(Double.MAX_VALUE);
	}
	
	@Test
	public void testBool() {
		regex = "(?i)(true|false|1|0)";
		String s1 = "true";
		String s2 = "FALSE";
		String s3 = "1";
		String s4 = "0";
		String s5 = "3";
		String s6 = "fase";
		assertThat(RegexUtils.matches(s1, regex), is(true));
		assertThat(RegexUtils.matches(s2, regex), is(true));
		assertThat(RegexUtils.matches(s3, regex), is(true));
		assertThat(RegexUtils.matches(s4, regex), is(true));
		assertThat(RegexUtils.matches(s5, regex), is(false));
		assertThat(RegexUtils.matches(s6, regex), is(false));
	}
	
	@Test
	public void timeformattest() {
		String time = "20:23:20";
//		System.out.println(new Simpleda);
	}

}
