package cn.newgxu.bbs.common.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.StatisticService;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HitsCounterListener implements InitializingBean {

	private static final Log log = LogFactory.getLog(HitsCounterListener.class);

	// 是否需要自动执行，可被设置为false使本Linstener失效.
	private boolean autoRun = true;

	// 操作线程延时启动的时间，单位为秒
	private int lazyTime = 100;

	private int distanceTime = 10;

	private StatisticService statisticService;

	// 索引线程
	private Thread thread = new Thread() {
		@Override
		public void run() {
			try {
				Thread.sleep(lazyTime * 1000);
				log.info("begin hits counter listener...");
				while (true) {
					statisticService.flushHitsManager();
					try {
						Thread.sleep(Util.ONE_MINUTE * distanceTime);
					} catch (InterruptedException ex) {
						log.error(ex);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e);
			}
		}
	};

	/**
	 * 实现<code>InitializingBean</code>接口，在完成注入后调用启动索引线程.
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if (autoRun) {
			if (statisticService == null) {
				statisticService = (StatisticService) Util
						.getBean("statisticService");
			}
			Assert.notNull(statisticService, "请为statisticService赋值！");
			thread.setDaemon(true);
			thread.setName("Hits counter listener!");
			thread.start();
		}
	}

	public void setStatisticService(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	public void setAutoRun(boolean autoRun) {
		this.autoRun = autoRun;
	}

	public void setDistanceTime(int distanceTime) {
		this.distanceTime = distanceTime;
	}

	public void setLazyTime(int lazyTime) {
		this.lazyTime = lazyTime;
	}

}
