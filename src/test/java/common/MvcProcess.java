///*
// * Copyright im.longkai@gmail.com
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package common;
//
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Array;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.jaxen.javabean.JavaBeanXPath;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.context.WebApplicationContext;
//
//import cn.newgxu.bbs.common.util.Util;
//import cn.newgxu.ng.core.mvc.annotation.MVCExceptionpHandler;
//import cn.newgxu.ng.core.mvc.annotation.MVCHandler;
//import cn.newgxu.ng.core.mvc.annotation.MVCMapping;
//import cn.newgxu.ng.core.mvc.annotation.MVCParamMapping;
//import cn.newgxu.ng.util.StringUtils;
//import cn.newgxu.ng.util.WrapperUtils;
//
///**
// * mvc处理，实际上了spring的bean获得controller。
// * 动态注入参数的时候采用了类似枚举的做法，可以试试下面的那个方法 
// * @see java.lang.Class#isAssignableFrom(Class) 
// * @author longkai
// * @since 2013-2-27
// * @version 1.0
// */
//public class MVCProcess {
//
//	private static final Logger				L	= LoggerFactory
//														.getLogger(MVCProcess.class);
//
//	/** 处理方法与处理器类名称的映射 */
//	private static Map<String, String>		handlers;
//	/** 请求路径与处理器方法的映射 */
//	private static Map<String, String>		mappedMethods;
//	/** 处理方法与该方法参数的映射 */
//	private static Map<String, Class<?>[]>	params;
//	/** 处理方法的参数    */
//	private static Map<String, MVCParamMapping[]> mappedParams;
//	/** 处理器类与该类所包含异常处理器的映射 */
//	private static Map<String, Method[]> exceptionHandlers;
//
//	private static WebApplicationContext	context;
//
//	static {
//		handlers = new HashMap<String, String>();
//		mappedMethods = new HashMap<String, String>();
//		params = new HashMap<String, Class<?>[]>();
//		mappedParams = new HashMap<String, MVCParamMapping[]>();
//		exceptionHandlers = new HashMap<String, Method[]>();
//		getContext();
//		init();
//	}
//
//	private static WebApplicationContext getContext() {
//		if (context == null) {
//			context = Util.getWebApplicationContext();
//		}
//		return context;
//	}
//
//	public static void init() {
//		L.info("控制器初始化！");
//		Map<String, Object> ajaxBeans = getContext().getBeansWithAnnotation(MVCHandler.class);
//		for (String handler : ajaxBeans.keySet()) {
//			Object obj = ajaxBeans.get(handler);
//			Method[] methods = obj.getClass().getMethods();
////			用来暂存异常处理器的索引
//			List<Integer> index = null;
//			for (int i = 0; i < methods.length; i++) {
//				if (methods[i].isAnnotationPresent(MVCMapping.class)) {
//					MVCMapping mapping = methods[i].getAnnotation(MVCMapping.class);
//					String[] paths = mapping.value();
//					
//					for (int j = 0; j < paths.length; j++) {
//						mappedMethods.put(paths[j], methods[i].getName());
//						handlers.put(paths[j], handler);
//					}
//					params.put(handler + "." + methods[i].getName(), methods[i].getParameterTypes());
//					
////					请求参数注解映射
//					Annotation[][] parameterAnnotations = methods[i].getParameterAnnotations();
//					MVCParamMapping[] paramMappings = new MVCParamMapping[methods[i].getParameterTypes().length];
//					for (int j = 0; j < parameterAnnotations.length; j++) {
//						for (int k = 0; k < parameterAnnotations[j].length; k++) {
//							if (parameterAnnotations[j][k].annotationType().equals(MVCParamMapping.class)) {
//								paramMappings[j] = (MVCParamMapping) parameterAnnotations[j][k];
//							}
//						}
//					}
//					mappedParams.put(handler + "." + methods[i].getName(), paramMappings);
//					
//					L.info("请求参数映射  方法：{} 映射：{}", methods[i].getName(), paramMappings);
//					L.info("path:{}, handled by {}' s {} method", paths,
//							handler, methods[i].getName());
//				}
////				暂存异常处理器索引
//				if (methods[i].isAnnotationPresent(MVCExceptionpHandler.class)) {
//					if (index == null) {
//						index = new ArrayList<Integer>();
//					}
//					index.add(i);
//				}
//			}
////			注册异常处理器
//			if (index != null) {
//				Method[] exceptionHanlers = new Method[index.size()];
//				for (int i = 0; i < index.size(); i++) {
//					exceptionHanlers[i] = methods[index.get(i)];
//					L.info("控制器：{} 注册异常处理器：{}", handler, methods[i].getName());
//				}
//				exceptionHandlers.put(handler, exceptionHanlers);
//			}
//		}
//		L.info("控制器初始化完成！");
//	}
//
//	public static void process(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		String path = request.getRequestURI();
//
//		L.info("ajax 请求映射：{} ", path);
//
//		if (mappedMethods.containsKey(path)) {
//			// 准备启动mvc
//			Model model = null;
//			View view = null;
//			Object controller = null;
//
//			String methodName = mappedMethods.get(path);
//			String beanName = handlers.get(path);
//			Class<?>[] paramTypes = params.get(beanName + "." + methodName);
//			MVCParamMapping[] paramMappings = mappedParams.get(beanName + "." + methodName);
//
//			L.info("控制器名称: {}", beanName);
//			L.info("方法名：{}", methodName);
//			// L.info("注入方法参数类型{}", paramTypes);
//
//			controller = context.getBean(beanName);
//			Object[] injectedParams = new Object[paramTypes.length];
//			Map<String, String[]> requestParams = request.getParameterMap();
////			用set是因为不想在系统注入参数时出现重复注入。
//			Set<String> tmpParams = null; 
//			if (paramTypes.length > 0) {
//				tmpParams = new HashSet<String>();
//			}
//
//			for (int j = 0; j < paramTypes.length; j++) {
//				// TODO: can we inject super class in the future if needed?
//				L.info("正在注入第{}个参数", j + 1);
//				L.debug("暂存map：{}", tmpParams);
//				Class<?> tmpParamType = paramTypes[j];
//				if (tmpParamType.equals(HttpServletRequest.class)) {
//					injectedParams[j] = request;
//				} else if (tmpParamType.equals(HttpServletResponse.class)) {
//					injectedParams[j] = response;
//				} else if (tmpParamType.equals(HttpSession.class)) {
//					injectedParams[j] = request.getSession();
//				} else if (tmpParamType.equals(Model.class)) {
////					注入模型
//					model = new Model(request);
//					injectedParams[j] = model;
//				} else if (tmpParamType.equals(View.class)) {
////					注入视图
//					view = new View();
//					injectedParams[j] = view;
//				} else if (tmpParamType.isPrimitive() || WrapperUtils.isWrapper(tmpParamType) || tmpParamType.equals(Date.class) || tmpParamType.equals(String.class)) {
////					如果是基本类型极其包装器类型
//					if (paramMappings[j] != null) {
////						if (!paramMappings[j].required()) {
////							throw new RuntimeException("@MvcParaming required 已经被废弃！");
////						}
//						injectedParams[j] = StringUtils.parse(tmpParamType, requestParams.get(paramMappings[j].value())[0]);
//						L.info("请求参数绑定 key:{}, value: {}", paramMappings[j].value(), injectedParams[j]);
//					} else {
////						在参数类型不相同的时候可用
//						for (String key : requestParams.keySet()) {
//							Object tmp = null;
//							if (!tmpParams.contains(key)) {
////								如果暂存set中没有这个的话
//								tmp = StringUtils.parse(tmpParamType, requestParams.get(key)[0]);
//							}
//							if (tmp != null) {
//								injectedParams[j] = tmp;
//								tmpParams.add(key);
//								break;
//							}
//						}
//						L.warn("系统绑定参数 type: {}, vlaue: {}", tmpParamType.getName(), injectedParams[j]);
//					}
//				} else {
////					注入自定义参数
//					Class<?> paramType = tmpParamType;
//					Field[] fields = paramType.getDeclaredFields();
////					Map<String, String[]> requestParams = request.getParameterMap();
//					Object obj = null;
//					try {
//						obj = paramType.newInstance();
//					} catch (InstantiationException e) {
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						e.printStackTrace();
//					}
//					for (int i = 0; i < fields.length; i++) {
//						Object field = null;
//						Object[] arrayField = null;
//						String fieldName = fields[i].getName();
//						if (requestParams.containsKey(fieldName)) {
//							Class<?> fieldType = fields[i].getType();
//							String[] values = requestParams.get(fieldName);
////							paramType.getMethod("set" + fields[i].getName(), fieldType).invoke(obj, requestParams.get(fields[i].getName()));
//							Class<?> arrayType = fieldType.getComponentType();
//							if (arrayType == null) {
////								不是数组类型
//								field = StringUtils.parse(fieldType, values[0]);
//							} else {
////								数组类型
//								L.debug("数组类型：{}，长度：{}", arrayType, values.length);
//								arrayField = (Object[]) Array.newInstance(arrayType, values.length);
//								for (int k = 0; k < values.length; k++) {
//									L.debug("数组参数{}：{}", k, values[k]);
//									arrayField[k] = StringUtils.parse(arrayType, values[k]);
//								}
//								L.debug("array field:{}, field length:{}", arrayField, arrayField.length);
//							}
////							这里，暂时没有考虑集合类型的注入。so，TODO，collections inject(注意集合类型的初始化）...
////							setter...
//							String setter = StringUtils.setter(fieldName);
//							L.debug("setter：{}, valuetype: {}, value: {}，arrayValue：{}", setter, fieldType, field, arrayField);
//							try {
//								Method method = paramType.getMethod(setter, fieldType);
//								if (arrayField != null) {
////									method.invoke(obj, arrayField);
////									不知道为什么，直接调用setter方法对于数组的字段老是抛出type mismatch 或者wrong arg number的异常，不得已用此法。。。
//									fields[i].setAccessible(true);
//									fields[i].set(obj, arrayField);
//									fields[i].setAccessible(false);
//								} else {
//									method.invoke(obj, field);
//								}
//							} catch (IllegalArgumentException e) {
//								e.printStackTrace();
//							} catch (SecurityException e) {
//								e.printStackTrace();
//							} catch (IllegalAccessException e) {
//								e.printStackTrace();
//							} catch (InvocationTargetException e) {
//								e.printStackTrace();
//							} catch (NoSuchMethodException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					injectedParams[j] = obj;
//				}
//			}
//
//			Method method = null;
//			Object result = null;
//				// invoke it...
//				try {
//					method = controller.getClass()
//							.getMethod(methodName, paramTypes);
//					result = method.invoke(controller, injectedParams);
//				} catch (InvocationTargetException e) {
////					处理异常-。-，注意，是捕捉底层调用方法抛出的异常！
//					Method[] exps = exceptionHandlers.get(beanName);
//					for (int k = 0; k < exps.length; k++) {
//						MVCExceptionpHandler mh = exps[k].getAnnotation(MVCExceptionpHandler.class);
//						Class<?>[] exceptions = mh.value();
//						for (int l = 0; l < exceptions.length; l++) {
//							if (exceptions[l].isAssignableFrom(e.getCause().getClass())) {
//								try {
//									exps[k].invoke(controller, e.getCause());
//									response.getWriter().write("error");
////									TODO: 给异常返回视图
//									return;
//								} catch (Throwable t) {
////									这时再遇上异常，没办法了-/-，交给全局异常处理。
//									globalExceptionHandler(response, t);
//								}
//							}
//						}
//					}
//				} catch (Exception e) {
////					其余的索性都一并扔这里了。
//					e.printStackTrace();
//				}
//			Class<?> returnType = method.getReturnType();
//			if (returnType.equals(String.class)) {
//				// string, 那视图就是一般的请求（非ajax），返回值代表viewname
//				if (view == null) {
//					view = new View();
//				}
//				view.setViewName(result.toString());
//			} else if (returnType.equals(View.class)) {
//				// 直接返回view
//				view = (View) result;
//			} else if (returnType.equals(ModelAndView.class)) {
//				// 返回模型和视图
//				ModelAndView mav = (ModelAndView) result;
//				model = mav.getModel();
//				view = mav.getView();
//			} else {
//				L.warn("没有检测到预设的返回类型！");
//				// 结束它。
//				return;
//			}
//
//			// 注入模型
//			if (model != null) {
//				if (!model.isInjected()) {
//					Map<String, Object> map = model.toMap();
//					for (String key : map.keySet()) {
//						request.setAttribute(key, map.get(key));
//					}
//				}
//			}
//
//			L.info("返回视图：{}", view);
//			switch (view.getType()) {
//			// case FREEMARKER:
//			// request.getRequestDispatcher(view.getViewName()).forward(request,
//			// response);
//			// break;
//			case JSON:
//			case AJAX:
//				response.getWriter().write(view.getContent());
//				break;
//			case HTML:
//			case REDIRECT:
//				response.sendRedirect(view.getViewName());
//				break;
//			default:
//				request.getRequestDispatcher(view.getViewName()).forward(
//						request, response);
//				break;
//			}
//
//			// finish it, stop here...
//			L.info("请求：{} 处理结束...", path);
//		} else {
//			throw new RuntimeException("NOT FOUND ACTION!");
//		}
//
//	}
//	
////	TODO: 全局异常处理器
//	
//	private static void globalExceptionHandler(HttpServletResponse response, Throwable t) throws IOException {
//		L.error("系统抛出异常！{}", t);
//		response.sendRedirect("");
//	}
//
//}
