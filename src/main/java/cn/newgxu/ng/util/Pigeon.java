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

import java.util.HashMap;
import java.util.Map;

/**
 * 鸽子，顾名思义，信使，用来传递消息之用。 使用了链式注入。
 * 
 * @author longkai
 * @since 2013-2-27
 * @version 0.1
 */
public class Pigeon {

	/** 信息的等级状态 */
	private InfoLevel level;
	/** 要传递的信息 */
	private String info;
	/** 出现此信息的原因 */
	private String reason;
	/** 可能的解决办法 */
	private String[] solutions;
	/** 额外的信息，保留，用到的时候才会初始化map */
	private Map<String, Object> extra; 
	
	public InfoLevel getLevel() {
		return level;
	}

	public Pigeon setLevel(InfoLevel status) {
		this.level = status;
		return this;
	}

	public String getInfo() {
		return info;
	}

	public Pigeon setInfo(String info) {
		this.info = info;
		return this;
	}

	public String getReason() {
		return reason;
	}

	public Pigeon setReason(String reason) {
		this.reason = reason;
		return this;
	}

	public String[] getSolutions() {
		return solutions;
	}

	public Pigeon setSolutions(String...solutions) {
		this.solutions = solutions;
		return this;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public Pigeon setExtra(String key, Object value) {
		extra = new HashMap<String, Object>(1);
		this.extra.put(key, value);
		return this;
	}

}
