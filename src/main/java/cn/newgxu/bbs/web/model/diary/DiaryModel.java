package cn.newgxu.bbs.web.model.diary;

import java.util.List;

import cn.newgxu.bbs.domain.diary.Diary;
import cn.newgxu.bbs.domain.diary.DiaryBooks;
import cn.newgxu.bbs.domain.diary.DiaryComment;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryModel extends PaginationBaseModel {

	private int id = 0;

	private String title;

	private String content;

	private int weather;

	private int bg_type;

	private int humor;

	private int allowcomment;

	private int lockedType;

	private String ip;

	private String week;

	private Diary diary;

	private User user;

	private DiaryBooks diaryBook;

	private List<Diary> diarys;

	private List<DiaryComment> diaryComments;

	public Diary getDiary() {
		return diary;
	}

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	public List<Diary> getDiarys() {
		return diarys;
	}

	public void setDiarys(List<Diary> diarys) {
		this.diarys = diarys;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DiaryBooks getDiaryBook() {
		return diaryBook;
	}

	public void setDiaryBook(DiaryBooks diaryBook) {
		this.diaryBook = diaryBook;
	}

	public int getAllowcomment() {
		return allowcomment;
	}

	public void setAllowcomment(int allowcomment) {
		this.allowcomment = allowcomment;
	}

	public int getBg_type() {
		return bg_type;
	}

	public void setBg_type(int bg_type) {
		this.bg_type = bg_type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHumor() {
		return humor;
	}

	public void setHumor(int humor) {
		this.humor = humor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWeather() {
		return weather;
	}

	public void setWeather(int weather) {
		this.weather = weather;
	}

	public int getLockedType() {
		return lockedType;
	}

	public void setLockedType(int lockedType) {
		this.lockedType = lockedType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<DiaryComment> getDiaryComments() {
		return diaryComments;
	}

	public void setDiaryComments(List<DiaryComment> diaryComments) {
		this.diaryComments = diaryComments;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

}
