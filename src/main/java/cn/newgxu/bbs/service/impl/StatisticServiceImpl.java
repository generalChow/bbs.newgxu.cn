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
	
	private int totalCount = 0;
	private int todayCount = 0;
	
	public int getTotalCount() {
		return totalCount;
	}

	public int getTodayCount() {
		return todayCount;
	}

	public void addHit(int forumId) {
		Date date = new Date();
		HitsManager.addHit(Util.getDate(date), Util.getHour(date), forumId);
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
		return HitsCounter.getTodayHitsCounter();
	}

	public int getTotalHitsCounter() {
		if (this.totalCount == 0) {
			this.totalCount = HitsCounter.getTotalHitsCounter();
		}
		return totalCount;
	}
	
}
