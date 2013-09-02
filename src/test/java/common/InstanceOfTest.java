/*
 * Copyright im.longkaii@gmail.com
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

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author longkai
 * @since 2013-2-27
 * @version 1.0
 */
public class InstanceOfTest {

	private Date date;
	
	@Before
	public void init() {
		date = new Date();
	}
	
	@Test
	public void test() {
		assertThat(date instanceof Date, is(true));
	}

}
