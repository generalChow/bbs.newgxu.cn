package cn.newgxu.bbs.web.action.diary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.DiaryService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.diary.DiarySearchModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@SuppressWarnings("serial")
public class DiarySearchAction extends AbstractBaseAction {

	private static final Log log = LogFactory.getLog(DiarySearchAction.class);

	private DiarySearchModel model = new DiarySearchModel();

	private DiaryService diaryService;

	@Override
	public String execute() throws Exception {
		signOnlineUser("日记检索");
		return SUCCESS;
	}

	public String searchDo() throws Exception {
		signOnlineUser("日记检索");
		MessageList m = new MessageList();
		try {
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			diaryService.search(model);
			log.debug("检索完成！");
			return SUCCESS;
		} catch (BBSException e) {
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	public String searchByTime() throws Exception {
		signOnlineUser("那天查看日记!");
		MessageList m = new MessageList();
		try {
			model.getPagination().setActionName(getActionName());
			model.getPagination().setParamMap(getParameterMap());
			diaryService.search(model);
			log.debug("检索完成！");
			return SUCCESS;
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

	public void setDiaryService(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

}
