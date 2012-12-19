package cn.newgxu.bbs.web.model;

import java.util.Random;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BoardView {

	private int id;

	private String name;

	private int shar = 1;

	private int counter;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getShar() {
		Random rd = new Random();
		shar = rd.nextInt(7);
		if (shar == 0) {
			shar = 1;
		}
		return shar;
	}

	public void setShar(int shar) {
		this.shar = shar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
