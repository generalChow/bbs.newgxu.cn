package cn.newgxu.bbs.domain.sys.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.sys.Param;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ParamManager {

	private static final Log log = LogFactory.getLog(ParamManager.class);

	private static final int DATA_TYPE_INTEGER = 1;
	private static final int DATA_TYPE_STRING = 2;
	private static final int DATA_TYPE_SPLITE = 3;

	private static ParamManager instance = new ParamManager();

	private ParamManager() {
	}

	public static ParamManager getInstance() {
		return instance;
	}

	private static Map<String, Param> cache = new HashMap<String, Param>();

	public static Object getParam(String code) throws ObjectNotFoundException {
		if (log.isDebugEnabled()) {
			log.debug("get from cache : " + cache.get(code));
		}
		Param param = cache.get(code);
		if (param == null) {
			param = Param.getByCode(code);
			cache.put(code, param);
		}
		switch (param.getData_type()) {
		case DATA_TYPE_INTEGER:
			return Integer.parseInt(param.getPara_value().toString());
		case DATA_TYPE_STRING:
			return param.getPara_value();
		case DATA_TYPE_SPLITE:
			return (int[]) Util.splitIds(param.getPara_value());
		default:
			return param.getPara_value();
		}
	}

	public static void reset() {
		cache = new HashMap<String, Param>();
	}

}
