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

import org.springframework.stereotype.Component;

import cn.newgxu.ng.core.mvc.Interceptor;

/**
 * 拦截器的定义, 可以注解在两个地方，第一个是注解在拦截器类上，第二种是注解在拦截的方法前。
 * 警告：第一种如果存在多个拦截器时，拦截器的启动顺序是不可预知的。第二种则可以自定义拦截器的拦截顺序。
 * 
 * 拦截器使用了spring的容器。
 * 
 * @author longkai
 * @since 2013-3-3
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MVCInterceptor {
	
//	/**
//	 * 拦截器的名字。
//	 * 注解在方法上时可以通过名字来找到相应的拦截器，但是不推荐这样做。
//	 */
//	String value() default "";
	
	/**
	 * 只能定义在方法上，通过指定的拦截器类去获取相应的拦截器。
	 */
	Class<? extends Interceptor>[] interceptors() default {};
	
	/**
	 * 拦截器拦截的路径, 可以使用正则表达式(不是通配符！）。
	 * 只能定义在方法上。
	 */
	String url() default "";
	
	/**
	 * 排除在外的拦截路径。
	 */
	String[] excludes() default {};
	
}
