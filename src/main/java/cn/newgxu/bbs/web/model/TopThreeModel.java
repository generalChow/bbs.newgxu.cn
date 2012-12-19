package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.TopThree;

public class TopThreeModel extends PaginationBaseModel {

	private List<TopThree>	topThrees;

	public List<TopThree> getTopThrees() {
		return topThrees;
	}

	public void setTopThrees(List<TopThree> topThrees) {
		this.topThrees = topThrees;
	}

}
