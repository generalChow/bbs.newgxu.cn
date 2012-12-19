package cn.newgxu.bbs.common.util;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SessionUtil {

	private static final Log log = LogFactory.getLog(SessionUtil.class);

	public static void setAttribute(HttpSession session, String key,
			Object value) {
		try {
			session.setAttribute(key, value);
		} catch (IllegalStateException e) {
			log.warn(e);
		}
	}

	public static Object getAttribute(HttpSession session, String key) {
		return getAttribute(session, key, null);
	}

	public static Object getAttribute(HttpSession session, String key,
			Object exceptionObject) {
		try {
			return session.getAttribute(key);
		} catch (IllegalStateException e) {
			log.warn(e);
			return exceptionObject;
		}
	}

}
