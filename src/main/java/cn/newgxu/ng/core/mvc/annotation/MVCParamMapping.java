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
package cn.newgxu.ng.core.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于现定参数的名称（不能是自定义的类）
 * @author longkai
 * @since 2013-2-28
 * @version 1.0
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MVCParamMapping {
	
	/**
	 * 客户端传递过来的查询参数名称
	 */
	String value() default "";
	
	/**
	 * 是否要求和value一摸一样，否则根据系统判断注入（有相同类型的参数要小心！）
	 * @deprecated 废弃，意义不大，容易出错。要就系统判断，要就自己做好映射。
	 */
//	@Deprecated
//	boolean required() default true;
	
}
