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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.ng.core.mvc.annotation.MVCExceptionpHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
import cn.newgxu.ng.core.mvc.annotation.MVCInterceptor;
import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
import cn.newgxu.ng.core.mvc.annotation.MVCParamMapping;
import cn.newgxu.ng.util.InfoLevel;
import cn.newgxu.ng.util.Pigeon;
import cn.newgxu.ng.util.RegexUtils;
import cn.newgxu.ng.util.StringUtils;
import cn.newgxu.ng.util.WrapperUtils;

/**
 * mvc处理，实际上了spring的bean获得controller。
 * 动态注入参数的时候采用了类似枚举的做法，可以试试下面的那个方法 
 * @see java.lang.Class#isAssignableFrom(Class) 
 * @author longkai
 * @since 2013-2-27
 * @version 1.0
 */
public class MVCProcess {

	private static final Logger				L	= LoggerFactory
														.getLogger(MVCProcess.class);

	/** 处理方法与处理器类名称的映射 */
	private static Map<String, String>		handlers;
	/** 请求路径与处理器方法的映射 */
	private static Map<String, String>		mappedMethods;
	/** 处理方法与该方法参数的映射 */
	private static Map<String, Class<?>[]>	methodParams;
	/** 处理方法的参数    */
	private static Map<String, MVCParamMapping[]> injectedParams;
	/** 处理器类与该类所包含异常处理器的映射 */
	private static Map<String, Method[]> exceptionHandlers;
	/** 路径与拦截器的映射 */
	private static Map<String, Class<?>> interceptors;

	private static WebApplicationContext	context;
	
	/** 默认的视图存放点 */
	private static final String viewPath = "/template/ng/";

	static {
		handlers = new HashMap<String, String>();
		mappedMethods = new HashMap<String, String>();
		methodParams = new HashMap<String, Class<?>[]>();
		injectedParams = new HashMap<String, MVCParamMapping[]>();
		exceptionHandlers = new HashMap<String, Method[]>();
		interceptors = new HashMap<String, Class<?>>();
		getContext();
		init();
		interceptorInit();
	}

	private static WebApplicationContext getContext() {
		if (context == null) {
			context = Util.getWebApplicationContext();
		}
		return context;
	}

	/**
	 * 初始化
	 */
	public static void init() {
		L.info("控制器初始化！");
		Map<String, Object> handlersBeans = getContext().getBeansWithAnnotation(MVCHandler.class);
		for (String handler : handlersBeans.keySet()) {
			Object obj = handlersBeans.get(handler);
			Method[] methods = obj.getClass().getMethods();
			List<Method> exceptionHandleMethods = new ArrayList<Method>();
			MVCHandler mvcHandler = obj.getClass().getAnnotation(MVCHandler.class);
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				MVCMapping mapping = method.getAnnotation(MVCMapping.class);
				if (method.isAnnotationPresent(MVCMapping.class)) {
//					注册响应方法。
					String[] paths = mapping.value();
					for (int j = 0; j < paths.length; j++) {
//						String realPath = mapping.namespace() + paths[j];
						String realPath = mvcHandler.namespace() + paths[j];
//						可能会出现两个斜杠的情况。
						realPath = realPath.replace("//", "/");
						mappedMethods.put(realPath, method.getName());
						handlers.put(realPath, handler);
						L.debug("path:{}, handled by {}' s {} method", realPath, handler, method.getName());
					}
					methodParams.put(handler + "." + method.getName(), method.getParameterTypes());
//					请求参数注解映射
					MVCParamMapping[] paramsMapping = paramsMapping(method);
					injectedParams.put(handler + "." + method.getName(), paramsMapping);
					
					L.debug("请求参数映射  方法：{} 映射：{}", method.getName(),  paramsMapping);
				} else {
//				暂存异常处理器索引
					stageExceptionHandlers(exceptionHandleMethods, method);
				}
			}
//			注册异常处理器
			if (exceptionHandleMethods != null)
				exceptionHandlers.put(handler, exceptionHandleMethods.toArray(new Method[0]));
		}
		L.info("控制器初始化完成！");
	}
	
	/**
	 * 拦截器初始化。
	 */
	private static void interceptorInit() {
		L.info("拦截器初始化！");
		Set<String> urls = mappedMethods.keySet();
		Map<String, Object> interceptorBeans = getContext().getBeansWithAnnotation(MVCInterceptor.class);
//		暂存所有被排除的url
		List<String> excludes = new ArrayList<String>();
		for (String beanName : interceptorBeans.keySet()) {
			Class<?> interceptor = interceptorBeans.get(beanName).getClass();
			MVCInterceptor tag = interceptor.getAnnotation(MVCInterceptor.class);
			if (RegexUtils.contains(tag.url(), "[\\^\\$\\+\\?\\.\\*\\+]+")) {
//				添加符合正则表达式的url。
				for (String s : urls) {
					if (RegexUtils.matches(s, tag.url())) {
						interceptors.put(s, interceptor);
					}
				}
			} else {
				interceptors.put(tag.url(), interceptor);
			}
			excludes.addAll(Arrays.asList(tag.excludes()));
			L.debug("拦截器：{}", interceptors);
		}
//		移除排除的东东
		for (String s : excludes) {
			L.debug("exclude url: {}", s);
			interceptors.remove(s);
		}
		L.info("拦截器初始化完成！");
	}
	
	/**
	 * 在请求响应前进行拦截。
	 * @param path
	 * @param request
	 * @param response
	 * @return 拦截成功
	 * @throws IOException
	 * @throws ServletException
	 */
	private static Interceptor intercept(String path, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Interceptor interceptor = null;
//		interceptors.clear();
//		interceptorInit();
		
		if (interceptors.containsKey(path)) {
			interceptor = (Interceptor) context.getBean(interceptors.get(path));
		}
		
		return interceptor;
	}
	
	/**
	 * 注解在控制器方法上的拦截器实现，为方法执行之前。
	 * @param method
	 * @param request
	 * @param response
	 * @return 放行 true, 拦截 false
	 * @throws ServletException
	 * @throws IOException
	 */
	private static boolean interceptorBefore(Method method, HttpServletRequest request, HttpServletResponse response, ModelAndView mav) throws ServletException, IOException {
//		默认放行。
		boolean allow = true;
		if (method.isAnnotationPresent(MVCInterceptor.class)) {
			MVCInterceptor interceptors = method.getAnnotation(MVCInterceptor.class);
			Class<? extends Interceptor>[] classes = interceptors.interceptors();
			
			for (int i = 0; i < classes.length; i++) {
				Interceptor interceptor  = context.getBean(classes[i]);
				allow = interceptor.before(request, response, mav);
				if (!allow) {
					break;
				}
			}
		}
		return allow;
	}
	
	/**
	 * 注解在控制器方法上的拦截器实现，为方法执行之后。
	 * @param method
	 * @param request
	 * @param response
	 * @param mav
	 * @return 放行 true, 拦截 false
	 * @throws ServletException
	 * @throws IOException
	 */
	private static boolean interceptorAfter(Method method, HttpServletRequest request, HttpServletResponse response, ModelAndView mav) throws ServletException, IOException {
//		默认放行。
		boolean allow = true;
		if (method.isAnnotationPresent(MVCInterceptor.class)) {
			MVCInterceptor interceptors = method.getAnnotation(MVCInterceptor.class);
			Class<? extends Interceptor>[] classes = interceptors.interceptors();
			
			for (int i = 0; i < classes.length; i++) {
				Interceptor interceptor  = context.getBean(classes[i]);
				allow = interceptor.after(request, response, mav);
				if (!allow) {
					break;
				}
			}
		}
		return allow;
	}
	
//	private static Interceptor interceptorBefore(HttpServletRequest request, HttpServletResponse response) {
//		
//	}
	
	/**
	 * 获取方法的参数所包含的注解。
	 * @param m 方法
	 * @return @MVCParamMapping 的注解数组
	 */
	private static MVCParamMapping[] paramsMapping(Method m) {
		Annotation[][] pas = m.getParameterAnnotations();
		MVCParamMapping[] paramMappings = new MVCParamMapping[m.getParameterTypes().length];
		for (int i = 0; i < pas.length; i++) {
			for (int j = 0; j < pas[i].length; j++) {
				if (pas[i][j].annotationType().equals(MVCParamMapping.class)) {
					paramMappings[i] = (MVCParamMapping) pas[i][j];
				}
			}
		}
		return paramMappings;
	}
	
	/**
	 * 暂存异常处理器，如果存在则会对list进行初始化。
	 * @param list
	 * @param method
	 */
	private static void stageExceptionHandlers(List<Method> list, Method method) {
		if (method.isAnnotationPresent(MVCExceptionpHandler.class)) {
			list.add(method);
		}
	}
	
	/**
	 * 处理请求并且处理全局异常（若抛出）。
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void mvc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI();
		L.info("请求映射：{} ", path);
		
//		准备拦截器
		Interceptor interceptor = intercept(path, request, response);
		ModelAndView mav = new ModelAndView();
//		方法执行前进行拦截
		if (interceptor != null) {
			if (!interceptor.before(request, response, mav)) {
				return;
			}
		}
		
		
		try {
			mav = process(request, response, path);
		} catch (Throwable e) {
			globalExceptionHandler(request, response, e);
			return;
		}
		
//		解析试图返回模型前进行拦截
		if (interceptor != null) {
			if (!interceptor.after(request, response, mav)) {
				return; 
			}
		}
		
		injectModel(request, mav.getModel());
		render(request, response, mav.getView());
		
		L.info("请求：{} 处理结束...", path);
	}

	/**
	 * mvc 请求处理过程。
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws MVCException
	 */
	private static ModelAndView process(HttpServletRequest request,
			HttpServletResponse response, String path) throws ServletException, IOException, MVCException {
//		替换url为我们定义的。
		if (mappedMethods.containsKey(path)) {
			// 准备启动mvc
//			Model model = null;
//			View view = null;
			Object controller = null;
			
//			先把模型和视图合并在一起，易于操作控制器返回的结果
			ModelAndView mav = new ModelAndView();

			String methodName = mappedMethods.get(path);
			String beanName = handlers.get(path);
			Class<?>[] paramTypes = methodParams.get(beanName + "." + methodName);
			MVCParamMapping[] paramMappings = injectedParams.get(beanName + "." + methodName);

			L.info("控制器名称: {}", beanName);
			L.info("方法名：{}", methodName);

			controller = context.getBean(beanName);
//			参数注入
			Object[] injectedParams = injectMethodParams(paramTypes, paramMappings, request, response, mav);

			Method method = null;
			Object result = null;
				// invoke it...
			L.debug("准备调用控制器方法：{}，参数类型：{}， 参数：{}", methodName, paramTypes, injectedParams);
			try {
				method = controller.getClass()
						.getMethod(methodName, paramTypes);
//				方法执行之前进行拦截。
				if (!interceptorBefore(method, request, response, mav)) {
					return mav;
				}
				result = method.invoke(controller, injectedParams);
			} catch (InvocationTargetException e) {
//					处理异常-。-，注意，是捕捉底层调用方法抛出的异常！
				L.error("异常处理", e.getCause());
				handleException(beanName, controller, e.getCause(), request, response, mav);
				L.info("异常处理完毕！异常：{}，原因：{}", e.getCause().getClass(), e.getCause().getMessage());
				return mav;
			} catch (Exception e) {
//					其余的索性都一并扔这里了。
				throw new MVCException("控制器方法调用异常", e);
			}
//				解析结果
			resolveResult(method, result, mav);
			
//			解析结果前返回。
			interceptorAfter(method, request, response, mav);
			
			return mav;
//			分离模型和试图
//			model = mav.getModel();
//			view = mav.getView();
				
			// 注入模型
//			injectModel(request, mav.getModel());
//			返回客户端
//			render(request, response, mav.getView());
		} else {
			throw new MVCException("对不起，您所请求的资源不存在！");
		}

	}

	/**
	 * 回应客户端。
	 * @param request
	 * @param response
	 * @param view
	 * @throws IOException
	 * @throws ServletException
	 */
	private static void render(HttpServletRequest request,
			HttpServletResponse response, View view) throws IOException,
			ServletException {
		L.debug("view:{}", view);
		switch (view.getType()) {
//		TODO: enable FREEMARKER!
		// case FREEMARKER:
		// request.getRequestDispatcher(view.getViewName()).forward(request,
		// response);
		// break;
		case JSON:
		case AJAX:
			response.getWriter().write(view.getContent());
			break;
		case HTML:
		case REDIRECT:
			response.sendRedirect(view.getViewName());
			break;
		default:
			request.getRequestDispatcher(viewPath + view.getViewName()).forward(
					request, response);
			break;
		}
	}

	/**
	 * 把模型注入到request中。
	 * @param request
	 * @param model
	 */
	private static void injectModel(HttpServletRequest request, Model model) {
		if (model != null) {
			if (!model.isInjected()) {
				Map<String, Object> map = model.toMap();
				for (String key : map.keySet()) {
					request.setAttribute(key, map.get(key));
				}
			}
		}
	}
	
	
	/**
	 * 控制器方法参数注入
	 * @param paramTypes
	 * @param paramMappings
	 * @param request
	 * @param response
	 * @param model
	 * @param view
	 * @return
	 * @throws MVCException
	 */
	private static Object[] injectMethodParams(Class<?>[] paramTypes, MVCParamMapping[] paramMappings, HttpServletRequest request, HttpServletResponse response,
			ModelAndView mav) throws MVCException {
		Object[] injectedParams = new Object[paramTypes.length];
		Map<String, String[]> requestParams = request.getParameterMap();
		
		for (int i = 0; i < paramTypes.length; i++) {
			// TODO: can we inject super class in the future if needed?
			Class<?> type = paramTypes[i];
			L.debug("正在注入第{}个参数, type: {}", (i + 1), type);
			if (type.equals(HttpServletRequest.class)) {
				injectedParams[i] = request;
			} else if (type.equals(HttpServletResponse.class)) {
				injectedParams[i] = response;
			} else if (type.equals(HttpSession.class)) {
//				注意，这个session，如果不存在，就会新建一个
				injectedParams[i] = request.getSession();
			} else if (type.equals(Model.class)) {
//				注入模型
				mav.setModel(new Model(request));
				injectedParams[i] = mav.getModel();
			} else if (type.equals(View.class)) {
//				注入视图
				mav.setView(new View());
				injectedParams[i] = mav.getView();
			} else if (type.equals(ModelAndView.class)) {
//				注入视图和模型
				injectedParams[i] = mav;
			} else if (type.isPrimitive() || type.equals(String.class)
					|| WrapperUtils.isWrapper(type) || type.equals(Date.class)
					|| type.getSuperclass().equals(Date.class)) {
//				如果是基本类型及其包装器类型，字符串，日期，时间等等，基本类型及其包装器都会有默认值。
				injectedParams[i] = injectParam(type, requestParams, paramMappings[i]);
			} else {
//				注入自定义Javabean
				injectedParams[i] = injectJavaBean(type, requestParams);
			}
		}
		return injectedParams;
	}
	
	/**
	 * 注入一般类型参数（基本类型及其包装类型，日期，字符串本身）。
	 * @param paramType
	 * @param requestParams
	 * @param paramMappings
	 * @param index
	 * @param set 暂存已经注入过的参数。
	 */
	private static Object injectParam(Class<?> paramType, Map<String, String[]> requestParams, MVCParamMapping paramMapping) {
		Object obj = null;
		if (paramMapping != null) {
//			obj = StringUtils.parse(paramType, requestParams.get(paramMapping.value())[0]);
			String param = null;
			try {
				param = requestParams.get(paramMapping.value())[0];
			} catch (Exception e) {}
			
			obj = StringUtils.convert(paramType, param);
			L.debug("请求参数绑定 key:{}, value: {}", paramMapping.value(), obj);
		} else {
//			在参数类型不相同的时候可用, 因为此时是通过类型判断！警告，这个算法不够完善，当出现true，flase，1，0，时间日期并且转换类型是字符串的时候容易出错！慎用！
//			for (String key : requestParams.keySet()) {
//				Object tmp = null;
//				if (!set.contains(key)) {
////					如果暂存set中没有这个的话，也就是说这个key的值还没有被注入过的话
//					tmp = StringUtils.parse(paramType, requestParams.get(key)[0]);
//				}
//				if (tmp != null) {
//					obj = tmp;
//					set.add(key);
//					break;
//				}
//			}
//			L.warn("系统绑定参数 type: {}, vlaue: {}", paramType.getName(), obj);
			throw new RuntimeException("请指明查询参数的名字！");
		}
		return obj;
	}
	
	/**
	 * 注入自定义的JavaBean
	 * @param paramType
	 * @param requestParams
	 * @return
	 * @throws MVCException
	 */
	private static Object injectJavaBean(Class<?> paramType, Map<String, String[]> requestParams) throws MVCException {
		Field[] fields = paramType.getDeclaredFields();
		Object bean = null;
		try {
			bean = paramType.newInstance();
		} catch (Exception e) {
			throw new MVCException("实例化参数对象时异常！", e);
		} 
		
		for (int i = 0; i < fields.length; i++) {
			Object field = null;
			String fieldName = fields[i].getName();
			if (requestParams.containsKey(fieldName)) {
				Class<?> fieldType = fields[i].getType();
				String[] values = requestParams.get(fieldName);
				Class<?> arrayType = fieldType.getComponentType();
//				这里，暂时没有考虑集合类型的注入。so，TODO，collections inject(注意集合类型的初始化）...
				if (arrayType == null) {
//					不是数组类型
					field = StringUtils.convert(fieldType, values[0]);
				} else {
//					数组类型
					Object[] arrayField = (Object[]) Array.newInstance(arrayType, values.length);
					for (int k = 0; k < values.length; k++) {
						arrayField[k] = StringUtils.convert(arrayType, values[k]);
					}
					field = arrayField;
				}
				
//				setter...
				String setter = StringUtils.setter(fieldName);
				L.debug("setter：{}, valuetype: {}, value: {}", setter, fieldType, field);
				try {
					Method method = paramType.getMethod(setter, fieldType);
					method.invoke(bean, field);
				} catch (Exception e) {
					throw new MVCException("setters 参数注入异常！", e);
				} 
			}
		}
		return bean;
	}
	
	/**
	 * 解析响应结果。
	 * @param producer
	 * @param result
	 * @return ModelAndView 响应的视图和模型。
	 * @throws MVCException 无法解析结果
	 */
	private static ModelAndView resolveResult(Method producer, Object result, ModelAndView mav) throws MVCException {
		Class<?> returnType = producer.getReturnType();
		if (returnType.equals(ModelAndView.class)) {
//			如果不是同一个的话
			if (mav != result) {
				ModelAndView tmp = (ModelAndView) result;
				mav.setView(tmp.getView());
				mav.setModel(tmp.getModel());
			}
		} else if (returnType.equals(String.class)) {
			// string, 那视图就是一般的请求（非ajax），返回值代表viewname
			mav.setViewName(result.toString());
		} else if (returnType.equals(View.class)) {
			// 直接返回view
			mav.setView((View) result);
		} else if (returnType.equals(Model.class)) {
//			返回了一个模型
			mav.setModel((Model) result);
		} else {
			L.warn("没有检测到可以解析的模型和视图！返回类型：{}，返回值：{}", returnType, result);
		}
		L.info("视图解析：{}", mav.getView());
		L.debug("模型解析: {}", mav.getModel());
		return mav;
	}
	
	/**
	 * 自定义异常处理。
	 * @param controllerName 控制器名字 
	 * @param handler 控制器对象
	 * @param handleFor 待处理异常
	 * @param request
	 * @param response
	 * @param mav
	 * @throws MVCException 异常处理的时候发生错误-.-
	 * @throws ServletException 
	 * @throws IOException 
	 */
	private static void handleException(String controllerName, Object handler, Throwable handleFor, HttpServletRequest request, HttpServletResponse response, ModelAndView mav) throws MVCException, IOException, ServletException {
		Method[] methods = exceptionHandlers.get(controllerName);
		for (int i = 0; i < methods.length; i++) {
			MVCExceptionpHandler expHandler = methods[i].getAnnotation(MVCExceptionpHandler.class);
			Class<?>[] handledExceptions = expHandler.value();
			for (int j = 0; j < handledExceptions.length; j++) {
				if (handledExceptions[j].isAssignableFrom(handleFor.getClass())) {
					try {
						Class<?>[] cs = methods[i].getParameterTypes();
						Object[] paramst = injectMethodParams(cs, null, request, response, mav);
						paramst[0] = handleFor;
						Object o = methods[i].invoke(handler, paramst);
						resolveResult(methods[i], o, mav);
//						injectModel(request, mav.getModel());
//						render(request, response, mav.getView());
					} catch (Throwable t) {
//							这时再遇上异常，没办法了-/-
						throw new MVCException("返回错误的时候出错！", t);
					}
					return;
				}
			}
		}
//		globalExceptionHandler(request, response, handleFor);
		throw new MVCException(handleFor);
	}
	
	/**
	 * 默认的全局异常处理器，使用了信使作为信息的传递。
	 * @param request
	 * @param response
	 * @param t 异常
	 * @throws IOException
	 * @throws ServletException
	 */
	private static void globalExceptionHandler(HttpServletRequest request, HttpServletResponse response, Throwable t) throws IOException, ServletException {
		L.error("全局异常抛出！{}", t);
		Pigeon msg = new Pigeon().setLevel(InfoLevel.ERROR)
						.setInfo(t.getMessage())
//						.setReason(t.getCause().getMessage())
						.setReason(t.getMessage())
						.setSolutions("请查看异常信息并检查您是否误操作！", "请稍后再试！", "请联系管理员！");
		request.setAttribute("msg", msg);
		request.getRequestDispatcher(viewPath + "mobile/error.jsp").forward(request, response);
	}
	
}
