package cn.newgxu.bbs.common.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.UserService;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OnlineUserListener implements InitializingBean {

	private static final Log log = LogFactory.getLog(OnlineUserListener.class);

	// 是否需要自动执行，可被设置为false使本Linstener失效.
	private boolean autoRun = true;
	

	// 操作线程延时启动的时间，单位为秒
	private int lazyTime = 100;

	private int distanceTime = 10;

	private UserService userService;

	// 索引线程
	private Thread thread = new Thread() {
		@Override
		public void run() {
			try {
				Thread.sleep(lazyTime * 1000);
//				log.info("begin online user listener...");
				while (true) {
					userService.cleanOnlineUser();
					try {
						Thread.sleep(Util.ONE_MINUTE * distanceTime);
					} catch (InterruptedException ex) {
						log.error(ex);
					}
				}
			} catch (InterruptedException e) {
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
			if (userService == null) {
				userService = (UserService) Util.getBean("userService");
			}
			Assert.notNull(userService, "请为userService赋值！");
			thread.setDaemon(true);
			thread.setName("online User listener!");
			thread.start();
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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
