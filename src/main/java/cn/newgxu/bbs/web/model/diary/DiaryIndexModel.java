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
public class DiaryIndexModel extends PaginationBaseModel {

	private User user;

	private List<Diary> diarys;

	private List<DiaryBooks> diaryBooks;

	private List<Diary> recDiarys;

	private List<Diary> latDiarys;

	private List<DiaryBooks> excDiaryBooks;

	private List<DiaryBooks> newDiaryBooks;

	private List<DiaryBooks> couDiaryBooks;

	public List<DiaryBooks> getCouDiaryBooks() {
		return couDiaryBooks;
	}

	public void setCouDiaryBooks(List<DiaryBooks> couDiaryBooks) {
		this.couDiaryBooks = couDiaryBooks;
	}

	public List<DiaryBooks> getDiaryBooks() {
		return diaryBooks;
	}

	public void setDiaryBooks(List<DiaryBooks> diaryBooks) {
		this.diaryBooks = diaryBooks;
	}

	public List<Diary> getDiarys() {
		return diarys;
	}

	public void setDiarys(List<Diary> diarys) {
		this.diarys = diarys;
	}

	public List<DiaryBooks> getExcDiaryBooks() {
		return excDiaryBooks;
	}

	public void setExcDiaryBooks(List<DiaryBooks> excDiaryBooks) {
		this.excDiaryBooks = excDiaryBooks;
	}

	public List<Diary> getLatDiarys() {
		return latDiarys;
	}

	public void setLatDiarys(List<Diary> latDiarys) {
		this.latDiarys = latDiarys;
	}

	public List<DiaryBooks> getNewDiaryBooks() {
		return newDiaryBooks;
	}

	public void setNewDiaryBooks(List<DiaryBooks> newDiaryBooks) {
		this.newDiaryBooks = newDiaryBooks;
	}

	public List<Diary> getRecDiarys() {
		return recDiarys;
	}

	public void setRecDiarys(List<Diary> recDiarys) {
		this.recDiarys = recDiarys;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
