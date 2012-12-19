package common;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

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

}
