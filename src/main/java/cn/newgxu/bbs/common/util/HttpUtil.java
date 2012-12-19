package cn.newgxu.bbs.common.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author polly
 * @since 4.0.0
 * @version $Revision: 1.1 $
 * 
 */
public class HttpUtil {

	public static int getIntParameter(HttpServletRequest request,
			String paramName, int defaultValue) {
		try {
			return Integer.parseInt(request.getParameter(paramName));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static int getIntParameter(HttpServletRequest request,
			String paramName) {
		return getIntParameter(request, paramName, 0);
	}

	public static Map<?, ?> getParameterMap(HttpServletRequest request) {
		return request.getParameterMap();
	}

	public static String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	public static String buildOriginalURL(HttpServletRequest request) {
		StringBuffer originalURL = new StringBuffer(getRequestURI(request));
		Map<?, ?> parameters = getParameterMap(request);
		if (parameters != null && parameters.size() > 0) {
			originalURL.append("?");
			for (Iterator<?> iter = parameters.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String[] values = (String[]) parameters.get(key);
				for (int i = 0; i < values.length; i++) {
					originalURL.append(key).append("=").append(values[i])
							.append("&");
				}
			}
		}
		return originalURL.toString();
	}

	public static void printCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				System.out.print("cookie name = [" + cookies[i].getName()
						+ "]\t\t");
				System.out.println("cookie value = [" + cookies[i].getValue());
			}
		}
	}

	public static void printSession(Map<?, ?> session) {
		for (Iterator<?> iterator = session.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
			System.out.print("session key = [" + entry.getKey() + "]\t\t");
			System.out.println("session value = [" + entry.getValue() + "]");
		}
	}

	public static void printParameters(HttpServletRequest request) {
		Map<?, ?> parameters = request.getParameterMap();
		for (Iterator<?> iter = parameters.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String[] values = (String[]) parameters.get(key);
			System.out.print("parameter key = [" + key + "]\t\t");
			System.out.println("parameter value = [" + StringUtils.join(values)
					+ "]");
		}
	}

}
