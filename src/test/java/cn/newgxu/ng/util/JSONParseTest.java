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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.newgxu.bbs.domain.RemoteContent;

/**
 * 
 * @author longkai
 * @since 2013-3-10
 * @version 1.0
 */
public class JSONParseTest {

	private static final Logger L = LoggerFactory.getLogger(JSONParseTest.class);
	
	@Test
	@Ignore
	public void testResloveJson() throws ParseException {
		String json = RemoteContent.getRemoteResult(
				"http://localhost:8080/resources/t.json", "utf8");
		System.out.println(json);
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse(json);
		for (int i = 0; i < a.size(); i++) {
			JSONObject o = (JSONObject) a.get(i);
			L.info("tid:{}, title:{}, author:{}, uid:{}, ctime:{}, clickTimes{}", 
					o.get("tid"), o.get("title"), o.get("author"), o.get("uid"), o.get("ctime"), o.get("clickTimes"));
		}
	}

}
