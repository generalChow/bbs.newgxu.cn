package cn.newgxu.bbs.service.proxy;

import java.util.List;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.ValidationUtil;
import cn.newgxu.bbs.domain.diary.Diary;
import cn.newgxu.bbs.domain.diary.DiaryBooks;
import cn.newgxu.bbs.domain.diary.DiaryComment;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.DiaryService;
import cn.newgxu.bbs.web.model.diary.DiaryBookModel;
import cn.newgxu.bbs.web.model.diary.DiaryCommentModel;
import cn.newgxu.bbs.web.model.diary.DiaryIndexModel;
import cn.newgxu.bbs.web.model.diary.DiaryModel;
import cn.newgxu.bbs.web.model.diary.DiarySearchModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryServiceProxy implements DiaryService {

	private DiaryService diaryService;

	public boolean isOrnotBuildBook(Authorization auth) {

		return diaryService.isOrnotBuildBook(auth);
	}

	public void setDiaryService(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

	public void addDiaryBook(DiaryBookModel model) throws BBSException,
			ValidationException {
		ValidationUtil
				.diaryBook(model.getUser().getNick(), model.getBookName(),
						model.getViewKey(), model.getDescription());
		diaryService.addDiaryBook(model);

	}

	public DiaryBooks getDiaryBook(User user) throws BBSException {

		return diaryService.getDiaryBook(user);
	}

	public List<Diary> getDiarys(DiaryBooks diaryBook, Pagination p)
			throws BBSException {

		return diaryService.getDiarys(diaryBook, p);
	}

	public void addDiaryDo(DiaryModel model) throws BBSException,
			ValidationException {
		diaryService.addDiaryDo(model);
	}

	public void editDiaryBook(DiaryBookModel model) throws BBSException {
		diaryService.editDiaryBook(model);

	}

	public void editDiaryBookDo(DiaryBookModel model) throws BBSException,
			ValidationException {
		ValidationUtil
				.diaryBook(model.getUser().getNick(), model.getBookName(),
						model.getViewKey(), model.getDescription());
		diaryService.editDiaryBookDo(model);
	}

	public void delDiary(DiaryModel model) throws BBSException {
		diaryService.delDiary(model);

	}

	public Diary getDiary(int id) throws BBSException {

		return diaryService.getDiary(id);
	}

	public void addHitCount(DiaryBooks diaryBook) {
		diaryService.addHitCount(diaryBook);

	}

	public List<DiaryComment> getDiaryComment(DiaryModel model)
			throws BBSException {

		return diaryService.getDiaryComment(model);
	}

	public void addComment(DiaryCommentModel model) throws BBSException,
			ValidationException {
		diaryService.addComment(model);

	}

	public List<Diary> getDiary(int type, DiaryIndexModel model) {

		return diaryService.getDiary(type, model);
	}

	public List<DiaryBooks> getDiaryBooks(int type, DiaryIndexModel model) {

		return diaryService.getDiaryBooks(type, model);
	}

	public void editDiaryDo(DiaryModel model) throws BBSException,
			ValidationException {
		diaryService.editDiaryDo(model);
	}

	public void search(DiarySearchModel model) throws BBSException,
			ValidationException {
		diaryService.search(model);
	}

	public List<Diary> getDiarys(DiaryBooks diaryBook, Pagination p, String time)
			throws BBSException {
		return diaryService.getDiarys(diaryBook, p, time);
	}

	public void delDiaryByAdmin(DiaryModel model) throws BBSException{
		diaryService.delDiaryByAdmin(model);
	}
}
