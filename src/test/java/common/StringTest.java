package common;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import cn.newgxu.ng.util.RegexUtils;

public class StringTest {

	@Test
	public void test() {
		String s = "hello ? !".replace("?", "longkai");
		assertThat(s, is("hello longkai !"));
	}
	
	@Test
	public void testSpilt() {
		String s = "nice#!";
		assertThat(s.split("#")[0], is("nice"));
	}
	
	@Test
	public void testCase() {
		String s1 = "true";
		String s2 = "TRUE";
		String regex = "(?i)true";
		assertThat(RegexUtils.matches(s1, regex), is(true));
		assertThat(RegexUtils.matches(s2, regex), is(true));
	}
	
	@Test
	public void testRegex() {
		String regex = "[\\^\\$\\+\\?\\.\\*\\+]+";
		String pattern = "/ng/user/.+";
		assertThat(RegexUtils.contains(pattern, regex), is(true));
	}

}
