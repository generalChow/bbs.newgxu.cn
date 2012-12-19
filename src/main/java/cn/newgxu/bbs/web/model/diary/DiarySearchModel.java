package cn.newgxu.bbs.web.model.diary;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import cn.newgxu.bbs.domain.diary.Diary;
import cn.newgxu.bbs.domain.diary.DiaryBooks;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DiarySearchModel extends PaginationBaseModel {

	private int type;

	private String keywords;

	private int diaryId;

	private List<Diary> diarys;

	private List<DiaryBooks> diaryBooks;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getKeywords() {
		try {
			return URLDecoder.decode(keywords, "utf8");
		} catch (UnsupportedEncodingException e) {
			return keywords;
		}
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getDiaryId() {
		return diaryId;
	}

	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}

	public List<Diary> getDiarys() {
		return diarys;
	}

	public void setDiarys(List<Diary> diarys) {
		this.diarys = diarys;
	}

	public List<DiaryBooks> getDiaryBooks() {
		return diaryBooks;
	}

	public void setDiaryBooks(List<DiaryBooks> diaryBooks) {
		this.diaryBooks = diaryBooks;
	}

}
