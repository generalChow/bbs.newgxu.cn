package cn.newgxu.bbs.web.model.user;

import java.util.List;


import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MyUploadModel extends PaginationBaseModel{

	private int id;
	
	private User user;
	
	private String nick;//admin管理下的搜索
	
	private List<UploadItem> uploadItems;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<UploadItem> getUploadItems() {
		return uploadItems;
	}

	public void setUploadItems(List<UploadItem> uploadItems) {
		this.uploadItems = uploadItems;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
}
