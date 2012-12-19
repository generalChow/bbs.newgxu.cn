package cn.newgxu.bbs.domain.activity;

import java.util.Calendar;

import cn.newgxu.bbs.common.util.TimerUtils;

/**
 * 2012年末日活动之末日告白
 * 
 * @author longkai
 * @since 2012-12-19
 */
public class Doomsday {

	private static String name = "Doomsday";

	private static Calendar initial;
	private static Calendar deadline;
	
//	private static Doomsday doomsday;

	static {
//		doomsday = new Doomsday();
		init();
	}
	
	private Doomsday() {}
	
//	public static Doomsday getInstance() {
//		if (doomsday == null) {
//			doomsday = new Doomsday();
//		}
//		return doomsday;
//	}
	
	private static void init() {
		initial = Calendar.getInstance();
		initial.set(Calendar.YEAR, 2012);
		initial.set(Calendar.MONTH, Calendar.DECEMBER);
		initial.set(Calendar.DAY_OF_MONTH, 19);
		initial.set(Calendar.HOUR_OF_DAY, 0);
		initial.set(Calendar.MINUTE, 0);
		initial.set(Calendar.SECOND, 0);

		deadline = Calendar.getInstance();
		deadline.set(Calendar.YEAR, 2012);
		deadline.set(Calendar.MONTH, Calendar.DECEMBER);
		deadline.set(Calendar.DAY_OF_MONTH, 21);
		deadline.set(Calendar.HOUR_OF_DAY, 23);
		deadline.set(Calendar.MINUTE, 59);
		deadline.set(Calendar.SECOND, 59);
	}

	public static String getName() {
		return name;
	}

	public static Calendar getInitial() {
		return initial;
	}

	public static Calendar getDeadline() {
		return deadline;
	}
	
	public static boolean rightnow() {
		return TimerUtils.between(initial, deadline);
	}

}
