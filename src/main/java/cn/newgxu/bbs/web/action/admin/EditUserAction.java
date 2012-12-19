package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.UsersManageModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class EditUserAction extends AbstractBaseAction {

	private static final long serialVersionUID = 2580867848781474L;

	private UsersManageModel model = new UsersManageModel();

	@Override
	public String execute() throws Exception {
		try {
			User user = userService.getUser(model.getId());
			model.setAccountStatus(user.getAccountStatus());
			model.setAnswer(user.getAnswer());
			model.setBadboy(user.getBadboy());
			model.setBirthday(user.getBirthday());
			model.setCurrentPower(user.getCurrentPower());
			model.setEmail(user.getEmail());
			model.setExp(user.getExp());
			model.setFace(user.getFace());
			model.setGold(user.getGold());
			model.setGroupId(user.getGroupId());
			model.setGroupTypeId(user.getGroupTypeId());
			model.setGroupName(user.getGroupNameDisplay());
			model.setHomepage(user.getHomepage());
			model.setHonor(user.getUserHonor());
			model.setIdcode(user.getIdcode());
			model.setIdiograph(user.getIdiograph());
			
			for (int i = 0; i < 10; i++)
			System.out.println("testsestestsetstst111111111" + user.getIdiograph());
			
			model.setLastLoginTime(user.getLastLoginTime());
			model.setLoginTimes(user.getLoginTimes());
			model.setLoginmt(user.getLoginmt());
			model.setMoney(user.getMoney());
			model.setNick(user.getNick());
			model.setNumberOfGood(user.getNumberOfGood());
			model.setNumberOfReply(user.getNumberOfReply());
			model.setNumberOfTopic(user.getNumberOfTopic());
			model.setPassword(user.getPassword());
			model.setQq(user.getQq());
			model.setQuestion(user.getQuestion());
			model.setRegisterTime(user.getRegisterTime());
			model.setRegisterType(user.getRegisterType());
			model.setRemark(user.getRemark());
			model.setStudentid(user.getStudentid());
			model.setTel(user.getTel());
			model.setTitle(user.getTitle());
			model.setTrueName(user.getTrueName());
			model.setUnits(user.getUnits());
			model.setUsername(user.getUsername());
			model.setConfrere(user.isConfrere());
			model.setSex(user.isSex());
			model.setMaxPower(user.getMaxPower());
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String editUserDo() throws Exception {
		MessageList m = new MessageList();
		try {
			userService.editUser(model);
			response("<div width='100%' style='text-align:center;color:#3333dd; margin-top:40px;'>用户信息保存成功，刷新用户列表可以看到效果！</div>");
			return null;
			//m.setUrl("/admin/edit_user.yws?id=" + model.getId());
			//m.addMessage("<b>更改成功！</b>");
			//Util.putMessageList(m, getSession());
			//return SUCCESS;
		} catch (ValidationException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return INPUT;
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public Object getModel() {
		return model;
	}

}
