package cn.newgxu.bbs.service;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface StatisticService {
	
	public void addHit(int forumId);
	
	public void flushHitsManager();

	public int getTodayHitsCounter();

	public int getTotalHitsCounter();

}