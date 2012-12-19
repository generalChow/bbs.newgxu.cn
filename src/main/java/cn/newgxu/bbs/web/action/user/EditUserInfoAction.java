package cn.newgxu.bbs.web.action.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.accounts.RegisterAction;
import cn.newgxu.bbs.web.model.user.EidtUserInfoModel;

import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditUserInfoAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2963413112287145520L;

	private static final Log log = LogFactory.getLog(RegisterAction.class);

	private EidtUserInfoModel model = new EidtUserInfoModel();

	public String execute() throws Exception {
		signOnlineUser("修改个人资料中...");
		model.setUser(getUser());
		model.setYear(Util.getYear(model.getUser().getBirthday()));
		model.setMonth(Util.getMonth(model.getUser().getBirthday()));
		model.setDay(Util.getDay(model.getUser().getBirthday()));
		model.setEmail(model.getUser().getEmail());
		model.setHomepage(model.getUser().getHomepage());
		model.setIdiograph(model.getUser().getIdiograph());
		model.setQq(model.getUser().getQq());
		model.setTel(model.getUser().getTel());
		model.setSex(model.getUser().isSex() ? 1 : 0);
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("修改个人资料中...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			userService.editUserInfo(model);
			m.setUrl("/user/edit_user_info.yws");
			m.addMessage("<b>更新成功！</b>");
			m.addMessage("<a href='/index.yws'>返回论坛首页</a>");
			Util.putMessageList(m, getSession());
			log.debug("修改个人资料成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			addValidateMsg(e.getMessage());
			return INPUT;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	public String replyMessage() throws Exception{
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			if(this.userService.editReplyMessage(model, Integer.valueOf(getRequest().getParameter("type")))){
				ServletActionContext.getResponse().getWriter().write("0");
			}else{
				ServletActionContext.getResponse().getWriter().write("1");
			}
			return null;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

}
