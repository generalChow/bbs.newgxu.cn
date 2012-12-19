package cn.newgxu.bbs.web.model.admin;

import java.util.ArrayList;
import java.util.List;

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author tao
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SelectUserInfoModel extends PaginationBaseModel{

	private String keyWord;
	private int type;
	private String startTime;
	private String endTime;
	private int groupTypeId=2;
	private List<User> user = new ArrayList<User>();
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
	public int getGroupTypeId() {
		return groupTypeId;
	}
	public void setGroupTypeId(int groupTypeId) {
		this.groupTypeId = groupTypeId;
	}
	
}