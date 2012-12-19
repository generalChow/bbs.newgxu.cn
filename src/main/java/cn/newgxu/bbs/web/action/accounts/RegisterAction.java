package cn.newgxu.bbs.web.action.accounts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.SessionUtil;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.accounts.RegisterModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class RegisterAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2963413112287145520L;

	private static final Log log = LogFactory.getLog(RegisterAction.class);

	private RegisterModel model = new RegisterModel();
	

	public String execute() throws Exception {
		signOnlineUser("正在填写新用户资料");
		
		return SUCCESS;
	}

	public String submit() throws Exception {
		signOnlineUser("正在注册中...");
		MessageList m = new MessageList();
		
		System.out.println(model.getNick());
		
		try {
			model.setRightCode(Util.getValidCode(getSession()));
			userService.register(model);
			switch (model.getRegType()) {
			case 100: // 新生
			case 1: // 本科生
			case 2: // 研究生、博士生
			case 3: // 教师
			case 5: 
				m.setUrl("/accounts/login.yws");
				m.addMessage("<b>注册成功！</b>");
				m.addMessage("<a href='/accounts/login.yws'>现在就登录！</a>");
				break;
			case 4: // 校外
				m.setUrl("#");
				m.addMessage("<b>提交成功！</b>");
				m.addMessage("<b>我们将在48小时内验证您的注册资料！</b>");
				m.addMessage("<a href='/index.yws'>先到论坛逛逛</a>");
				break;
			default:
			}
			Util.putMessageList(m, getSession());
			log.debug("注册成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			addValidateMsg(e.getMessage());
			return INPUT;
		} catch (BBSException e) {
			addValidateMsg(e.getMessage());
			return INPUT;
		}
	}

	public String reInput() throws Exception {
		MessageList m = new MessageList();
		User user = (User) SessionUtil.getAttribute(getSession(),
				Constants.RE_INPUT_USER);
		if (user == null) {
			m.addMessage(BBSExceptionMessage.PARAMETER_ERROR);
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		model.setUser(user);
		model.setRegType(user.getRegisterType());
		model.setYear(Util.getYear(model.getUser().getBirthday()));
		model.setMonth(Util.getMonth(model.getUser().getBirthday()));
		model.setDay(Util.getDay(model.getUser().getBirthday()));
		model.setEmail(model.getUser().getEmail());
		model.setTrueName(user.getTrueName());
		model.setIdcode(user.getIdcode());
		model.setStudentid(user.getStudentid());
		model.setTel(user.getTel());
		model.setUnits(user.getUnits());

		addValidateMsg(user.getRemark());
		signOnlineUser("正在填写新用户资料");
		return SUCCESS;
	}

	public String reInputDo() throws Exception {
		MessageList m = new MessageList();
		User user = (User) SessionUtil.getAttribute(getSession(),
				Constants.RE_INPUT_USER);
		if (user == null) {
			m.addMessage(BBSExceptionMessage.PARAMETER_ERROR);
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		model.setUser(user);
		try {
			model.setRightCode(Util.getValidCode(getSession()));
			userService.reInput(model);
			m.setUrl("#");
			m.addMessage("<b>提交成功！</b>");
			m.addMessage("<b>我们将在48小时内验证您的注册资料！</b>");
			m.addMessage("<a href='/index.yws'>先到论坛逛逛</a>");
			Util.putMessageList(m, getSession());
			SessionUtil.setAttribute(getSession(), Constants.RE_INPUT_USER,
					null);
			log.debug("提交成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			addValidateMsg(e.getMessage());
			return INPUT;
		} catch (BBSException e) {
			addValidateMsg(e.getMessage());
			return INPUT;
		}
	}

	public Object getModel() {
		return model;
	}

}
