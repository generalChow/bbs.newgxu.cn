package cn.newgxu.bbs.web.model.user;

import java.util.List;

import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class UserFavoriteTopic extends PaginationBaseModel{
	private int topicId;
	
	private User user;
	
	private List<Topic> list;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Topic> getList() {
		return list;
	}

	public void setList(List<Topic> list) {
		this.list = list;
	}
	
	
}
