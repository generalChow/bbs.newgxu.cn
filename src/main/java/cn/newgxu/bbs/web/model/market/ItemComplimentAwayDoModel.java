package cn.newgxu.bbs.web.model.market;

import java.util.List;

import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ItemComplimentAwayDoModel {

	private long id;

	private String nick;

	private User user;

	// 用于生日祝福语
	private String wish;

	// 转物品成功用户
	List<String> receiveUsers;
	// 物品数量不足，转物品失败用户
	List<String> unReceiveUsers;
	// 错误昵称，用户不存在
	List<String> notExistUsers;

	// 用户获取指定的item。 longkai@2012-10-21
	private int itemId;
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getWish() {
		return wish;
	}

	public void setWish(String wish) {
		this.wish = wish;
	}

	public List<String> getReceiveUsers() {
		return receiveUsers;
	}

	public String getStringUsers() {
		String users = "";
		List<String> doUser = getReceiveUsers();
		for (String user : doUser) {
			users = user + ",";
		}
		return users;
	}

	public void setReceiveUsers(List<String> receiveUsers) {
		this.receiveUsers = receiveUsers;
	}

	public List<String> getUnReceiveUsers() {
		return unReceiveUsers;
	}

	public void setUnReceiveUsers(List<String> unReceiveUsers) {
		this.unReceiveUsers = unReceiveUsers;
	}

	public List<String> getNotExistUsers() {
		return notExistUsers;
	}

	public void setNotExistUsers(List<String> notExistUsers) {
		this.notExistUsers = notExistUsers;
	}

}
