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
package cn.newgxu.nbbs.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cn.newgxu.ng.util.InfoLevel;
import cn.newgxu.ng.util.Pigeon;

/**
 * 
 * @author longkai
 * @since 2013-2-27
 * @version 1.0
 */
public class PigeonTest {

	private Pigeon msg;
	
	@Before
	public void init() {
		msg = new Pigeon().setLevel(InfoLevel.HELP).setReason("session fail!");
	}
	@Test
	public void test() {
		System.out.println(msg.getLevel().name());
		System.out.println(msg.getLevel().toString());
	}

}
