package cn.newgxu.bbs.web.action.diary;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.DiaryService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.diary.DiaryCommentModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryCommentAction extends AbstractBaseAction {

	private DiaryCommentModel model = new DiaryCommentModel();

	private DiaryService diaryService;

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7250206664867565190L;

	@Override
	public String execute() throws Exception {

		return SUCCESS;
	}

	public String addComment() throws Exception {
		signOnlineUser("正在发表日记留言...");
		MessageList m = new MessageList();
		String ip = getRequest().getRemoteAddr();
		model.setIp(ip);
		diaryService.addComment(model);
		m.setUrl("/diary/view_Diary.yws?id=" + model.getDid());
		m.addMessage("<b>发表日记留言成功！</b>");
		m.addMessage("<a href='/diary/view_Diary.yws?id=" + model.getDid()
				+ "'>继续查看该日记</a>");
		m.addMessage("<a href='/diary/viewDiaryBook.yws'>到我的日记本看看</a>");
		m.addMessage("<a href='/index.yws'>逛论坛去</a>");
		Util.putMessageList(m, getSession());
		return SUCCESS;
	}

	public Object getModel() {

		return model;
	}

	public void setDiaryService(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

}
