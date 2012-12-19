package cn.newgxu.bbs.common.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.ForumService;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HotTopicListener implements InitializingBean {

	private static final Log log = LogFactory.getLog(HotTopicListener.class);

	// 是否需要自动执行，可被设置为false使本Linstener失效.
	private boolean autoRun = true;

	// 操作线程延时启动的时间，单位为秒
	private int lazyTime = 100;

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	private int distanceTime = 10;

	private ForumService forumService;

	// 索引线程
	private Thread thread = new Thread() {
		@Override
		public void run() {
			try {
				Thread.sleep(lazyTime * 1000);
				log.info("begin hotTopic listener...");
				while (true) {
					// forumService.queryHotTopics();
					// 检查效果过期物品
					forumService.checkTopicSafer();
					try {
						Thread.sleep(Util.ONE_MINUTE * distanceTime);
					} catch (InterruptedException ex) {
						log.error(ex);
					}
				}
			} catch (InterruptedException e) {
				log.error(e);
			} catch (Exception e) {
				e.printStackTrace();
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
			if (forumService == null) {
				forumService = (ForumService) Util.getBean("forumService");
			}
			Assert.notNull(forumService, "请为forumService赋值！");
			thread.setDaemon(true);
			thread.setName("hotTopic listener!");
			thread.start();
		}
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
