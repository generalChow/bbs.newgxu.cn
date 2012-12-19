package cn.newgxu.bbs.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.Util;
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
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryServiceImpl implements DiaryService {

	public boolean isOrnotBuildBook(Authorization auth) {
		try {
			User user = User.get(auth.getId());
			@SuppressWarnings("unused")
			DiaryBooks diaryBook = DiaryBooks.getByUserId(user);
			return false;
		} catch (ObjectNotFoundException e) {
			return true;
		}
	}

	public void addDiaryBook(DiaryBookModel model) throws BBSException {

		DiaryBooks diaryBook = new DiaryBooks();
		diaryBook.setBookName(model.getBookName());
		diaryBook.setDescription(model.getDescription());
		diaryBook.setDiaryStyle(model.getBookStyle());
		diaryBook.setViewKey(Util.hash(model.getViewKey()));
		diaryBook.setUser(model.getUser());
		diaryBook.save();

	}

	public DiaryBooks getDiaryBook(User user) throws BBSException {

		try {
			DiaryBooks diaryBook = DiaryBooks.getByUserId(user);
			return diaryBook;
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
	}

	public List<Diary> getDiarys(DiaryBooks diaryBook, Pagination p)
			throws BBSException {
		p.setPageSize(Constants.NUMBER_OF_DIARY);
		p.setRecordSize(Diary.getDiarysSize(diaryBook));
		return Diary.getDiarys(diaryBook, p);
	}

	public List<Diary> getDiarys(DiaryBooks diaryBook, Pagination p, String time)
			throws BBSException {
		p.setPageSize(Constants.NUMBER_OF_DIARY);
		p.setRecordSize(Diary.getDiarysSize(diaryBook, time));
		return Diary.getDiarys(diaryBook, p, time);
	}

	// 签写日记
	public void addDiaryDo(DiaryModel model) throws BBSException,
			ValidationException {
		Diary diary = new Diary();
		Date date = new Date();
		diary.setDiaryBook(model.getDiaryBook());
		diary.setUser(model.getUser());
		diary.setTitle(model.getTitle());
		diary.setContent(model.getContent());
		diary.setWeather(model.getWeather());
		diary.setHumor(model.getHumor());
		diary.setLockedType(model.getLockedType());
		diary.setAllowcomment(model.getAllowcomment());
		diary.setAddDate(date);
		// diary.setBg_type(model.getBg_type());
		diary.setIp(model.getIp());
		int diaryNum = model.getDiaryBook().getDiaryNum() + 1;
		model.getDiaryBook().setDiaryNum(diaryNum);
		diary.save();
	}

	public void editDiaryDo(DiaryModel model) throws BBSException,
			ValidationException {
		Diary diary = new Diary();
		try {
			diary = Diary.get(model.getId());
		} catch (ObjectNotFoundException e) {
		}
		if (!diary.getUser().equals(model.getUser())) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		Date date = new Date();
		diary.setTitle(model.getTitle());
		diary.setContent(model.getContent());
		diary.setWeather(model.getWeather());
		diary.setHumor(model.getHumor());
		diary.setLockedType(model.getLockedType());
		diary.setAllowcomment(model.getAllowcomment());
		diary.setAddDate(date);
		// diary.setBg_type(model.getBg_type());
		diary.setIp(model.getIp());
		diary.save();
	}

	public void editDiaryBook(DiaryBookModel model) throws BBSException {
		try {
			if (model.getUser() != DiaryBooks.get(model.getId()).getUser()) {
				throw new BBSException(BBSExceptionMessage.MANAGE_ERROR);
			}
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		model.setDiaryBook(getDiaryBook(model.getUser()));
	}

	public void editDiaryBookDo(DiaryBookModel model) throws BBSException,
			ValidationException {
		try {
			if (model.getUser() != DiaryBooks.get(model.getId()).getUser()) {
				throw new BBSException(BBSExceptionMessage.MANAGE_ERROR);
			}
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		DiaryBooks diaryBook = getDiaryBook(model.getUser());
		diaryBook.setBookName(model.getBookName());
		diaryBook.setDescription(model.getDescription());
		diaryBook.setDiaryStyle(model.getBookStyle());
		if (!diaryBook.getViewKey().equals(model.getViewKey())) {
			diaryBook.setViewKey(Util.hash(model.getViewKey()));
		}
		diaryBook.setUser(model.getUser());
		diaryBook.update();

	}

	public void delDiary(DiaryModel model) throws BBSException {

		Diary diary = getDiary(model.getId());
		if (diary == null) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		if (diary.getDiaryBook().getUser() != model.getUser()) {
			throw new BBSException(BBSExceptionMessage.DIARY_IS_OWS_ERROR);
		}
		diary.setIsDel(1);
		diary.save();
		DiaryBooks diaryBook = model.getDiaryBook();
		int diaryNum = diaryBook.getDiaryNum() - 1;
		diaryBook.setDiaryNum(diaryNum);
		diaryBook.update();
		model.getPagination().setRecordSize(Diary.getDiarysSize(diaryBook));

	}

	public void delDiaryByAdmin(DiaryModel model) throws BBSException{
		Diary diary = getDiary(model.getId());
		if (diary == null) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		if (model.getUser().getGroupTypeId()<3) {
			throw new BBSException(BBSExceptionMessage.MANAGE_ERROR);
		}
		diary.setIsDel(1);
		diary.save();
		DiaryBooks diaryBook = diary.getDiaryBook();
		int diaryNum = diaryBook.getDiaryNum() - 1;
		diaryBook.setDiaryNum(diaryNum);
		diaryBook.update();
	}
	public Diary getDiary(int id) throws BBSException {
		try {
			Diary diary = Diary.get(id);
			return diary;
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
	}

	public void addHitCount(DiaryBooks diaryBook) {
		int hitCount = diaryBook.getHitCount() + 1;
		diaryBook.setHitCount(hitCount);
		diaryBook.update();
	}

	private void addDiaryHitCount(Diary diary) {

		int hitCount = diary.getHitCount() + 1;
		diary.setHitCount(hitCount);
		diary.update();
	}

	public List<DiaryComment> getDiaryComment(DiaryModel model)
			throws BBSException {
		addDiaryHitCount(model.getDiary());
		model.getPagination().setPageSize(Constants.NUMBER_OF_DIARYCOMMENT);
		model.getPagination().setRecordSize(
				DiaryComment.CommentSize(model.getDiary()));
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		String mydate = sp.format(model.getDiary().getAddDate());
		model.setWeek(Util.getDayOfWeek(mydate));
		return DiaryComment.getDiaryComments(model.getDiary(), model
				.getPagination());

	}

	public void addComment(DiaryCommentModel model) throws BBSException,
			ValidationException {
		DiaryComment diaryComment = new DiaryComment();
		Date date = new Date();
		try {
			diaryComment.setUser(User.getByNick(model.getUid()));
		} catch (ObjectNotFoundException e) {

		}
		model.setDiary(getDiary(model.getDid()));
		model.getPagination().setPageSize(Constants.NUMBER_OF_DIARYCOMMENT);
		model.getPagination().setRecordSize(
				DiaryComment.CommentSize(model.getDiary()));
		model.setDiaryComments(DiaryComment.getDiaryComments(model.getDiary(),
				model.getPagination()));
		diaryComment.setDiary(getDiary(model.getDid()));
		diaryComment.setContent(model.getContent());
		diaryComment.setDateTime(date);
		diaryComment.setIp(model.getIp());
		diaryComment.save();

	}

	public List<Diary> getDiary(int type, DiaryIndexModel model) {

		model.getPagination().setRecordSize(Diary.getDiarysSize());
		return Diary.getDiarys(type, model.getPagination());
	}

	public List<DiaryBooks> getDiaryBooks(int type, DiaryIndexModel model) {

		model.getPagination().setRecordSize(DiaryBooks.getDiaryBookSize());
		return DiaryBooks.getDiaryBooks(type, model.getPagination());
	}

	public void search(DiarySearchModel model) throws BBSException,
			ValidationException {
		switch (model.getType()) {
		case 1:
			model.getPagination().setRecordSize(
					Diary.getDiarysSizeByTitle(model.getKeywords()));
			model.setDiarys(Diary.getDiarysByTitle(model.getKeywords(), model
					.getPagination()));
			break;

		case 2:
			model.getPagination()
					.setRecordSize(
							DiaryBooks.getDiaryBooksSizeByBookName(model
									.getKeywords()));
			model.setDiaryBooks(DiaryBooks.getDiaryBooksByBookName(model
					.getKeywords(), model.getPagination()));
			break;

		case 3:
			model.getPagination().setRecordSize(
					DiaryBooks.getDiaryBooksSizeByAuthor(model.getKeywords()));
			model.setDiaryBooks(DiaryBooks.getDiaryBooksByAuthor(model
					.getKeywords(), model.getPagination()));
			break;
		default:
			throw new BBSException("操作类型错误！");
		}
	}
}
