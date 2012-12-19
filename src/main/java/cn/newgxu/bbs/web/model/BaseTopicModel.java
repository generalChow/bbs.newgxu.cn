package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.user.User;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.BaseTopicModel.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-4
 * @describe  
 *	基本的帖子编辑model，然后的  CreateTopicModel，CreateVoteMdoel可以直接继承于此类，<br />
 *	那么在forumServiceImpl中这不需要多次出现同一段有关 create  的代码了，看得真不爽。。。
 */
public class BaseTopicModel {
	private String title;
	private int face;
	private String content;
	private int forumId;
	
	private User user;
	private Forum forum;
	
	private List<Area> areas;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getFace() {
		return face;
	}
	public void setFace(int face) {
		this.face = face;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	public List<Area> getAreas() {
		return areas;
	}
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
}
