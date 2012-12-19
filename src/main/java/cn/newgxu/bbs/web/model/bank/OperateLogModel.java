package cn.newgxu.bbs.web.model.bank;

import java.util.List;

import cn.newgxu.bbs.domain.bank.OperateLog;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author xin
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OperateLogModel extends PaginationBaseModel {

	private Byte type = 1;
	private User user;
	private List<OperateLog> logs;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OperateLog> getLogs() {
		return logs;
	}
	public void setLogs(List<OperateLog> logs) {
		this.logs = logs;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
}
