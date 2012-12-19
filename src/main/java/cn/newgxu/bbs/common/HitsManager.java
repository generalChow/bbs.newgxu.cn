package cn.newgxu.bbs.common;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.domain.HitsCounter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HitsManager {

	private static final Log log = LogFactory.getLog(HitsManager.class);

	private static HitsManager instance = new HitsManager();

	private HitsManager() {
	}

	public static HitsManager getInstance() {
		return instance;
	}

	private static Map<HitsCounter, HitsCounter> cache = new HashMap<HitsCounter, HitsCounter>();

	public static void addHit(Date date, int hour, int forumId) {
		HitsCounter entity = get(date, hour, forumId);
		if (entity == null) {
			entity = constructEntity(date, hour, forumId);
		}
		entity.addHit();
		cache.put(entity, entity);

//		if (log.isDebugEnabled()) {
//			log.debug("add Hit!");
//			log.debug(entity);
//		}
	}

	private static HitsCounter get(Date date, int hour, int forumId) {

		if (log.isDebugEnabled()) {
			log.debug("get from cache : "
					+ cache.get(constructEntity(date, hour, forumId)));
		}

		return cache.get(constructEntity(date, hour, forumId));
	}

	private static HitsCounter constructEntity(Date date, int hour, int forumId) {
		HitsCounter entity = new HitsCounter();
		entity.setHitsDate(date);
		entity.setHitsHour(hour);
		entity.setForumId(forumId);

//		log.debug(entity);

		return entity;
	}

	public static List<HitsCounter> getHitsCounters() {
		Map<HitsCounter, HitsCounter> map = cache;
		cache = new HashMap<HitsCounter, HitsCounter>();
		return new LinkedList<HitsCounter>(map.keySet());
	}

	public static void reset() {
		cache = new HashMap<HitsCounter, HitsCounter>();
	}

}
