package cn.newgxu.bbs.common.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Calendar;

import org.junit.Test;

public class TimerUtilsTest {

	@Test
	public void testIsLeap() {
		int year1 = 2008;
		assertThat(TimerUtils.isLeap(year1), is(true));
		int year2 = 1900;
		assertThat(TimerUtils.isLeap(year2), is(false));
		int year3 = 2007;
		assertThat(TimerUtils.isLeap(year3), is(false));
	}
	
	@Test
	public void testBefore() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 18);
		assertThat(TimerUtils.before(c), is(false));
		c.set(Calendar.DATE, 20);
		assertThat(TimerUtils.before(c), is(true));
	}

}
