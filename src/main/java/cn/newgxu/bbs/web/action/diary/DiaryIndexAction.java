package cn.newgxu.bbs.web.action.diary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.DiaryService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.diary.DiaryIndexModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryIndexAction extends AbstractBaseAction {

	private static final long serialVersionUID = 6059558030520551380L;

	private static final Log log = LogFactory.getLog(DiaryIndexAction.class);

	private DiaryIndexModel model = new DiaryIndexModel();

	private DiaryService diaryService;

	public String execute() throws Exception {
		signOnlineUser("日记本首页");
		System.out.println("+++++++++++++++++++++");
		super.setOnlineStatusForumId(1);
		model.setUser(getUser());
		MessageList m = new MessageList();
		System.out.println("+++++++++++++============++++++++");
		try {
			model.getPagination().setPageSize(Constants.INDEX_NUMBER_OF_DIARY);
			model.setRecDiarys(diaryService.getDiary(1, model));
			model.setLatDiarys(diaryService.getDiary(2, model));
			model.setExcDiaryBooks(diaryService.getDiaryBooks(1, model));
			model.setNewDiaryBooks(diaryService.getDiaryBooks(2, model));
			model.setCouDiaryBooks(diaryService.getDiaryBooks(3, model));
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
		return SUCCESS;
	}

	public String getRecDiary() throws Exception {
		signOnlineUser("日记本首页：查看推荐日记");
		model.setUser(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setPageSize(Constants.INDEX_NUMBER_OF_DIARY);
		model.setDiarys(diaryService.getDiary(1, model));
		return SUCCESS;
	}

	public String getLatDiary() throws Exception {
		signOnlineUser("日记本首页：查看最新日记");
		model.setUser(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setPageSize(Constants.INDEX_NUMBER_OF_DIARY);
		model.setDiarys(diaryService.getDiary(2, model));
		return SUCCESS;
	}

	public String getExcDiaryBook() throws Exception {
		signOnlineUser("日记本首页：查看优秀日记本");
		model.setUser(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setPageSize(Constants.INDEX_NUMBER_OF_DIARYBOOK);
		model.setDiaryBooks(diaryService.getDiaryBooks(1, model));
		return SUCCESS;
	}

	public String getNewDiaryBook() throws Exception {
		signOnlineUser("日记本首页：查看最新日记本");
		model.setUser(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setPageSize(Constants.INDEX_NUMBER_OF_DIARYBOOK);
		model.setDiaryBooks(diaryService.getDiaryBooks(2, model));
		return SUCCESS;
	}

	public String getCouDiaryBook() throws Exception {
		signOnlineUser("日记本首页：查看日记本统计");
		model.setUser(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setPageSize(Constants.INDEX_NUMBER_OF_DIARYBOOK);
		model.setDiaryBooks(diaryService.getDiaryBooks(3, model));
		return SUCCESS;
	}

	public Object getModel() {

		return model;
	}

	public void setDiaryService(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

}
