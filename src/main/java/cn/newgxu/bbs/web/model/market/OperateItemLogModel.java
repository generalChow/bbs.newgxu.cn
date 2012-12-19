package cn.newgxu.bbs.web.model.market;

import java.util.List;

import cn.newgxu.bbs.domain.market.ItemUsedLog;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OperateItemLogModel extends PaginationBaseModel {

	private int typeId;

	private User user;

	private List<ItemUsedLog> logs;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ItemUsedLog> getLogs() {
		return logs;
	}

	public void setLogs(List<ItemUsedLog> logs) {
		this.logs = logs;
	}

}
