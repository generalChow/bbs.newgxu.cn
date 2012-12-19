package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Topic;

/**
 * 一个非常简单的在首页上显示更多内容的model。
 *
 * @since 2012-06-12
 * @author longkai
 */
public class MoreResultsModel {
	
	private int id;
	private Area area;

	private List<Topic> moreTopics;

	public Area getArea() {
		return area;
	}

	public int getId() {
		return id;
	}

	public List<Topic> getMoreTopics() {
		return moreTopics;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMoreTopics(List<Topic> moreTopics) {
		this.moreTopics = moreTopics;
	}
	
}
