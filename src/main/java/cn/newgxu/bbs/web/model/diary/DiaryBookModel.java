package cn.newgxu.bbs.web.model.diary;

import java.util.List;

import cn.newgxu.bbs.domain.diary.Diary;
import cn.newgxu.bbs.domain.diary.DiaryBooks;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryBookModel extends PaginationBaseModel {

	private int id = 0;

	private String bookName;

	private String time;

	private int bookStyle;

	private String viewKey;

	private String description;

	private DiaryBooks diaryBook;

	private User user;

	private List<DiaryBooks> diaryBooks;

	private List<Diary> diarys;

	public DiaryBooks getDiaryBook() {
		return diaryBook;
	}

	public void setDiaryBook(DiaryBooks diaryBook) {
		this.diaryBook = diaryBook;
	}

	public List<DiaryBooks> getDiaryBooks() {
		return diaryBooks;
	}

	public void setDiaryBooks(List<DiaryBooks> diaryBooks) {
		this.diaryBooks = diaryBooks;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getBookStyle() {
		return bookStyle;
	}

	public void setBookStyle(int bookStyle) {
		this.bookStyle = bookStyle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getViewKey() {
		return viewKey;
	}

	public void setViewKey(String viewKey) {
		this.viewKey = viewKey;
	}

	public List<Diary> getDiarys() {
		return diarys;
	}

	public void setDiarys(List<Diary> diarys) {
		this.diarys = diarys;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
