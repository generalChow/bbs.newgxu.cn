package cn.newgxu.bbs.common;

import java.util.HashMap;
import java.util.Map;

import cn.newgxu.bbs.common.util.PropertiesUtil;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class Config {

	private static Map<String, String> cache = new HashMap<String, String>();

	private static PropertiesUtil pu;

	static {
		pu = new PropertiesUtil("/valhalla.properties");
	}

	public static String getProperty(String key) {
		String value = cache.get(key);
		if (value == null) {
			value = pu.getProperty(key);
			if (value != null) {
				cache.put(key, value);
			}
		}
		return value;
	}

	public static void setProperty(String key, String value) {
		if (key != null && value != null) {
			if (!value.equals(cache.get(key))) {
				pu.setProperty(key, value);
				cache.put(key, value);
			}
		}
	}

}
