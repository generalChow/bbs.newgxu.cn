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
package cn.newgxu.ng.core.mvc;

/**
 * 自定义的mvc处理异常，需捕捉！
 * 
 * @author longkai
 * @since 2013-3-1
 * @version 1.0
 */
public class MVCException extends Exception {

	private static final long	serialVersionUID	= 1L;

	public MVCException() {
		super();
	}

	public MVCException(String message, Throwable cause) {
		super(message, cause);
	}

	public MVCException(String message) {
		super(message);
	}

	public MVCException(Throwable cause) {
		super(cause);
	}	

}
