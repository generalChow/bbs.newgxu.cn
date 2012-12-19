package cn.newgxu.bbs.service;

import java.util.List;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.domain.diary.Diary;
import cn.newgxu.bbs.domain.diary.DiaryBooks;
import cn.newgxu.bbs.domain.diary.DiaryComment;
import cn.newgxu.bbs.domain.user.User;
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
public interface DiaryService {
	public boolean isOrnotBuildBook(Authorization auth);

	public void addDiaryBook(DiaryBookModel model) throws BBSException,
			ValidationException;

	public DiaryBooks getDiaryBook(User user) throws BBSException;

	public List<Diary> getDiarys(DiaryBooks diaryBook, Pagination p)
			throws BBSException;

	public void addDiaryDo(DiaryModel model) throws BBSException,
			ValidationException;

	public void editDiaryBook(DiaryBookModel model) throws BBSException;

	public void editDiaryBookDo(DiaryBookModel model) throws BBSException,
			ValidationException;

	public void delDiary(DiaryModel model) throws BBSException;

	public Diary getDiary(int id) throws BBSException;

	public void addHitCount(DiaryBooks diaryBook);

	public List<DiaryComment> getDiaryComment(DiaryModel model)
			throws BBSException;

	public void addComment(DiaryCommentModel model) throws BBSException,
			ValidationException;

	public List<Diary> getDiary(int type, DiaryIndexModel model);

	public List<DiaryBooks> getDiaryBooks(int type, DiaryIndexModel model);

	public void editDiaryDo(DiaryModel model) throws BBSException,
			ValidationException;

	public void search(DiarySearchModel model) throws BBSException,
			ValidationException;

	public List<Diary> getDiarys(DiaryBooks diaryBook, Pagination p, String time)
			throws BBSException;
	
	public void delDiaryByAdmin(DiaryModel model) throws BBSException;
}
