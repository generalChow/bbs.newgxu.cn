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
package cn.newgxu.nbbs.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.text.ParseException;

import org.junit.Ignore;
import org.junit.Test;

import cn.newgxu.ng.util.DateTime;
import cn.newgxu.ng.util.RegexUtils;

/**
 * 
 * @author longkai
 * @since 2013-2-28
 * @version 1.0
 */
public class DateTimeTest {

	String	str	= "00:00:00";
//	String	str	= "2012-12-17";

	@Test
	public void testParse() throws ParseException {
		System.out.println(DateTime.parse(str));
	}

	@Test
	@Ignore
	public void testMatche() {
		boolean matchs = RegexUtils.contains(str, DateTime.ISO_DATE_TIME_REGEX);
		assertThat(matchs, is(true));
	}
}
