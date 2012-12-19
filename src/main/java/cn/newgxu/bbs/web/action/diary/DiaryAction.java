package cn.newgxu.bbs.web.action.diary;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.DiaryService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.diary.DiaryModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryAction extends AbstractBaseAction {

	private DiaryModel model = new DiaryModel();

	private DiaryService diaryService;

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1695442875363238388L;

	@Override
	public String execute() throws Exception {
		signOnlineUser("查看日记本");
		if (diaryService.isOrnotBuildBook(getAuthorization())) {
			addValidateMsg("你还未建立你的日记本，先建立一个吧！");
			return INPUT;
		}
		;
		model.setUser(getUser());
		model.setDiaryBook(diaryService.getDiaryBook(getUser()));
		return SUCCESS;
	}

	public String addDiary() throws Exception {
		signOnlineUser("添加日记");
		model.setUser(getUser());
		model.setDiaryBook(diaryService.getDiaryBook(getUser()));
		String ip = getRequest().getRemoteAddr();
		model.setIp(ip);
		diaryService.addDiaryDo(model);
		return SUCCESS;
	}

	public String delDiary() throws Exception {
		execute();
		MessageList m = new MessageList();
		try {
			diaryService.delDiary(model);
			model.setDiarys(diaryService.getDiarys(model.getDiaryBook(), model
					.getPagination()));
			signOnlineUser("删除日记");
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		return SUCCESS;
	}

	public String editDiaryDo() throws Exception {
		signOnlineUser("修改日记");
		MessageList m = new MessageList();
		model.setUser(getUser());
		model.setDiaryBook(diaryService.getDiaryBook(getUser()));
		String ip = getRequest().getRemoteAddr();
		model.setIp(ip);
		try {
			diaryService.editDiaryDo(model);
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

	public String editDiary() throws Exception {
		model.setDiary(diaryService.getDiary(model.getId()));
		signOnlineUser("编辑日记：" + model.getDiary().getTitle());
		return execute();
	}

	public String viewDiary() throws Exception {

		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		MessageList m = new MessageList();
		try {
			model.setDiary(diaryService.getDiary(model.getId()));
			model.setDiaryComments(diaryService.getDiaryComment(model));
			signOnlineUser("查看日记：" + model.getDiary().getTitle());
		} catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		return SUCCESS;
	}

	public String viewDiaryByTime() throws Exception {
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		MessageList m = new MessageList();
		try {
			model.setDiary(diaryService.getDiary(model.getId()));
			model.setDiaryComments(diaryService.getDiaryComment(model));
			signOnlineUser("查看日记：" + model.getDiary().getTitle());
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
