package cn.newgxu.bbs.common.util;

import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.newgxu.bbs.domain.TopThree;
import cn.newgxu.bbs.domain.user.User;

/**
 * 设置这个类的目的是为了每周定时刷新，可能是因为论坛的历史原因，无法使用Timer或者quartz来自动设置监听（前者entityManager为null，后者有bug，所以自己就写了一个算法来实现。
 * 每周日23:00-23:59分内调用这个类的方法即可实现每周更新一次榜单。
 * 此外，考虑到重启服务器带来的再次刷新数据的影响，给这个类设置了监听器。
 * @author longkai
 * @version 1.0
 * @since 2012-09-23
 */
public class UpdateLastWeekExp implements ServletContextListener {

	private static int week;
	private static boolean hasUpdated;
	private static Calendar setting;
	
	
	static {
		hasUpdated = false;
		setting = Calendar.getInstance();
		week = setting.get(Calendar.DAY_OF_WEEK);
//		setting.set(Calendar.YEAR, 2012);
//		setting.set(Calendar.MONTH, 8);
//		setting.set(Calendar.DAY_OF_MONTH, 19);
		setting.set(Calendar.HOUR_OF_DAY, 23);
//		setting.set(Calendar.MINUTE, 59);
//		setting.set(Calendar.SECOND, 59);
	}
	
	
	public static boolean updatable(Calendar calendar) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(now);
		week = calendar.get(Calendar.DAY_OF_WEEK);
		
		// 如果周一到周六了还没有更新，那么就更新
		if (week != Calendar.SUNDAY && !hasUpdated) {
			hasUpdated = true;
			return true;
		}
		
		// 如果周一到周六已经更新了，那么就不更新
		if (week != Calendar.SUNDAY)
			return false;
		
		// 如果周日，不在23:00 -- 23:59区间内，那么就不更新
		if (calendar.get(Calendar.HOUR_OF_DAY) != setting.get(Calendar.HOUR_OF_DAY)) {
			// 设置本周还没有更新
			hasUpdated = false;
			return false;
		}
		
		// 如果周日，并且在23:00-23:59区间内，检测到本周已经更新了，那么就不更新
		if (hasUpdated)
			return false;
		
		hasUpdated = true;
		return true;
	}
	
	public static String updateLastWeekExp(Calendar now) {
		StringBuffer sb = null;
		if (updatable(now)) {
			// 假如说更新是在周末的23:00-23:59之间进行的，那么就进行保存周前三以及进行颁奖！
			if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				List<User> users = User.getLastWeekMostActiveUsers(10);
			
				TopThree t = new TopThree();
				t.setWeekly(now.get(Calendar.WEEK_OF_YEAR));
				t.setToppedTime(now.getTime());
				t.setFirst(users.get(0));
				t.setSecond(users.get(1));
				t.setThrid(users.get(2));
				t.setType(TopThree.LAST_WEEK_ACTIVE_USER_TOP_THREE);
				TopThree.persist(t);
				
//				应网管部要求，暂时取消榜单的自动信息发送 @2012-12-19
//				try {
//					Message.sendMessage("恭喜您成为上周最活跃榜单前三名", "恭喜您成为“本周最活跃”榜前三位，系统将接下来会给您一份神秘礼物~，希望您再接再厉。欲了解“本周最活跃”排行榜的排名，<a href='/user/users.yws?type=6'>点我~</a>", 1, users.subList(0, 3), User.get(0), "亲爱的雨无声论坛网友！");
//					Message.sendMessage("恭喜您成为上周最活跃榜单前十名", "恭喜您成为“本周最活跃”榜前十位，系统将接下来会给您一份神秘礼物~，希望您再接再厉。欲了解“本周最活跃”排行榜的排名，<a href='/user/users.yws?type=6'>点我~</a>", 1, users.subList(3, 10), User.get(0), "亲爱的雨无声论坛网友！");
//					这个系统发送礼物功能后面再加了。。。
					sb = new StringBuffer();
					for (User user : users.subList(3, 10)) {
						sb.append(user.getNick()).append(", ");
					}
//				} catch (ObjectNotFoundException e) {
//					e.printStackTrace();
//				}
			}
			
			User.updateLastWeekExp();	
			
		}
		
		return sb == null ? null : sb.substring(0, sb.lastIndexOf(", "));
	}

	public void contextInitialized(ServletContextEvent sce) {
		// 重启服务器的时候为了避免又刷新榜单，所以设置启动监听器，假定启动时已经是刷新过了的。（这个已启动的概率非常之大，实在不行也可以自己去刷新它，不过无必要了）
		Calendar initTime = Calendar.getInstance();
		if (initTime.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
			hasUpdated = true;
		// 最好不要在每周日的23:00-23:59重启服务器
		else if (initTime.get(Calendar.HOUR_OF_DAY) == 23)
			hasUpdated = true;	
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed");
	}
	
	public static void main(String[] args) {
		
	}
	
}
