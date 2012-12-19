package cn.newgxu.bbs.domain.activity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

public class DoomsdayTest {

	@Test
	public void testGetName() {
		assertThat(Doomsday.getName(), is("Doomsday"));
	}

	@Test
	public void testGetInitial() {
		Date d = Doomsday.getInitial().getTime();
		System.out.println(d);
	}

	@Test
	public void testGetDeadline() {
		Date d = Doomsday.getDeadline().getTime();
		System.out.println(d);
	}

}
