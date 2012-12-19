package cn.newgxu.bbs.web.model.diary;

import java.util.Date;
import java.util.List;

import cn.newgxu.bbs.domain.diary.Diary;
import cn.newgxu.bbs.domain.diary.DiaryComment;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiaryCommentModel extends PaginationBaseModel {

	private String uid;

	private int did;

	private String content;

	private Date dateTime;

	private String ip;

	private DiaryComment diaryComment;

	private User user;

	private Diary diary;

	private List<DiaryComment> diaryComments;

	public DiaryComment getDiaryComment() {
		return diaryComment;
	}

	public void setDiaryComment(DiaryComment diaryComment) {
		this.diaryComment = diaryComment;
	}

	public List<DiaryComment> getDiaryComments() {
		return diaryComments;
	}

	public void setDiaryComments(List<DiaryComment> diaryComments) {
		this.diaryComments = diaryComments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Diary getDiary() {
		return diary;
	}

	public void setDiary(Diary diary) {
		this.diary = diary;
	}
}
