package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SmallNewsModel extends PaginationBaseModel {

	private int id;

	private int forumId;

	private User user;

	private SmallNews smallNews;

	private List<SmallNews> smallNewsList;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public SmallNews getSmallNews() {
		return smallNews;
	}

	public void setSmallNews(SmallNews smallNews) {
		this.smallNews = smallNews;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<SmallNews> getSmallNewsList() {
		return smallNewsList;
	}

	public void setSmallNewsList(List<SmallNews> smallNewsList) {
		this.smallNewsList = smallNewsList;
	}

}
