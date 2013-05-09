package cn.newgxu.bbs.service.impl;

import java.util.Date;
import java.util.List;

import cn.newgxu.bbs.common.HitsManager;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.HitsCounter;
import cn.newgxu.bbs.service.StatisticService;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class StatisticServiceImpl implements StatisticService {

//	这里如果给每日访问设置缓存的话就会造成每次刷新都会++
	//	private static int todayCount = -1;
	private static int totalCount = -1;

	public void addHit(int forumId) {
		Date date = new Date();
		HitsManager.addHit(Util.getDate(date), Util.getHour(date), forumId);
//		if (todayCount != -1) {
//			todayCount++;
//		}
		if (totalCount != -1) {
			totalCount++;
		}
	}

	public void flushHitsManager() {
		List<HitsCounter> entities = HitsManager.getHitsCounters();
		HitsManager.reset();

		for (HitsCounter entity : entities) {
			HitsCounter po;
			try {
				po = HitsCounter.getByForumId_Date_Hour(entity.getForumId(),
						entity.getHitsDate(), entity.getHitsHour());
				po.incorporate(entity);
			} catch (ObjectNotFoundException e) {
				po = entity;
			}
			po.save();
		}
	}

	public int getTodayHitsCounter() {
//		if (todayCount == -1) {
//			todayCount = HitsCounter.getTodayHitsCounter();
//		}
//		return todayCount;
		return HitsCounter.getTodayHitsCounter();
	}

	public int getTotalHitsCounter() {
		if (totalCount == -1) {
			totalCount = HitsCounter.getTotalHitsCounter();
		}
		return totalCount;
	}

}