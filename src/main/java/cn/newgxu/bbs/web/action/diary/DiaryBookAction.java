package cn.newgxu.bbs.web.action.diary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.DiaryService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.CreateSmallNewsAction;
import cn.newgxu.bbs.web.model.diary.DiaryBookModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryBookAction extends AbstractBaseAction {

	private DiaryBookModel model = new DiaryBookModel();

	private DiaryService diaryService;

	private static final Log log = LogFactory
			.getLog(CreateSmallNewsAction.class);

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		User user = User.get(model.getId());
		if (user.getId() == getAuthorization().getId()) {
			if (diaryService.isOrnotBuildBook(getAuthorization())) {
				addValidateMsg("你还未建立你的日记本，先建立一个吧！");
				return INPUT;
			}
		}
		MessageList m = new MessageList();
		try {
			model.setUser(user);
			model.setDiaryBook(diaryService.getDiaryBook(user));
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			model.setDiarys(diaryService.getDiarys(model.getDiaryBook(), model
					.getPagination()));
			diaryService.addHitCount(model.getDiaryBook());
			signOnlineUser("查看日记本：" + model.getDiaryBook().getBookName());
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		return SUCCESS;
	}

	public String sumbit() throws Exception {
		signOnlineUser("正在建立日记本中...");
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			diaryService.addDiaryBook(model);
			m.setUrl("/diary/index.yws");
			m.addMessage("<b>建立日记成功！</b>");
			m.addMessage("<a href='/diary/index.yws'>到我的日记本看看</a>");
			m.addMessage("<a href='/index.yws'>逛论坛去</a>");
			Util.putMessageList(m, getSession());
			log.debug("建立日记本成功！");
			return SUCCESS;
		} catch (ValidationException e) {
			log.debug(e);
			addValidateMsg(e.getMessage());
			return INPUT;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String editBook() throws Exception {
		signOnlineUser("日记本管理");
		if (diaryService.isOrnotBuildBook(getAuthorization())) {
			addValidateMsg("你还未建立你的日记本，先建立一个吧！");
			return INPUT;
		}
		;
		MessageList m = new MessageList();
		try {
			model.setUser(getUser());
			diaryService.editDiaryBook(model);
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		return SUCCESS;
	}

	public String editBookDo() throws Exception {
		signOnlineUser("日记本管理");
		if (diaryService.isOrnotBuildBook(getAuthorization())) {
			addValidateMsg("你还未建立你的日记本，先建立一个吧！");
			return INPUT;
		}
		;
		MessageList m = new MessageList();
		model.setUser(getUser());
		try {
			diaryService.editDiaryBookDo(model);
			model.setDiaryBook(diaryService.getDiaryBook(getUser()));
			m.setUrl("/diary/viewDiaryBook.yws?id=" + getUser().getId());
			m.addMessage("修改成功");
			m.addMessage("<a href='/diary/viewDiaryBook.yws?id="
					+ getUser().getId() + "'>返回我的日记本</a>");
			m.addMessage("<a href ='/diary/index.yws'>返回日记本主页</a>");
			Util.putMessageList(m, getSession());
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		return SUCCESS;
	}

	public String viewDiaryByTime() throws Exception {
		User user = User.get(model.getId());
		if (user.getId() == getAuthorization().getId()) {
			if (diaryService.isOrnotBuildBook(getAuthorization())) {
				addValidateMsg("你还未建立你的日记本，先建立一个吧！");
				return INPUT;
			}
			;
		}
		MessageList m = new MessageList();
		try {
			model.setUser(user);
			model.setDiaryBook(diaryService.getDiaryBook(user));
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			model.setDiarys(diaryService.getDiarys(model.getDiaryBook(), model
					.getPagination(), model.getTime()));
			diaryService.addHitCount(model.getDiaryBook());
			signOnlineUser("查看日记本：" + model.getDiaryBook().getBookName());
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		return SUCCESS;
	}

	public Object getModel() {

		return model;
	}

	public void setDiaryService(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

}
