package cn.newgxu.bbs.web.action.diary;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.service.DiaryService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.diary.DiaryIndexModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DetDiarysAction extends AbstractBaseAction {

	private static final long serialVersionUID = 6059558030520551380L;


	private DiaryIndexModel model = new DiaryIndexModel();

	private DiaryService diaryService;

	public String execute() throws Exception {
		signOnlineUser("从首页查看日记本：查看最新日记");
		// model.setUser(getUser());
		model.getPagination().setActionName(getActionName());
		model.getPagination().setPageSize(Constants.INDEX_NUMBER_OF_DIARY);
		model.setDiarys(diaryService.getDiary(2, model));
		return SUCCESS;
	}

	public Object getModel() {

		return model;
	}

	public void setDiaryService(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

}
