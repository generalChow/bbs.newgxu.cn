package cn.newgxu.bbs.domain.diary;

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "diary_books")
public class DiaryBooks extends JPAEntity {

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@Column(name = "bookName")
	private String bookName;

	@Column(name = "description")
	private String description;

	@Column(name = "diary_num")
	private int diaryNum = 0;

	@Column(name = "hit_count")
	private int hitCount = 1;

	@Column(name = "diaryStyle")
	private int diaryStyle;

	@Column(name = "viewkey")
	private String viewKey;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDiaryNum() {
		return diaryNum;
	}

	public void setDiaryNum(int diaryNum) {
		this.diaryNum = diaryNum;
	}

	public int getDiaryStyle() {
		return diaryStyle;
	}

	public void setDiaryStyle(int diaryStyle) {
		this.diaryStyle = diaryStyle;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getViewKey() {
		return viewKey;
	}

	public void setViewKey(String viewKey) {
		this.viewKey = viewKey;
	}

	public static DiaryBooks get(int id) throws ObjectNotFoundException {
		return (DiaryBooks) getById(DiaryBooks.class, id);
	}

	public static DiaryBooks getByUserId(User user)
			throws ObjectNotFoundException {
		return (DiaryBooks) SQ("from DiaryBooks db where db.user.id = ?1 ", P(
				1, user.getId()));
	}

	public static int getDiaryBookSize() {
		try {
			return ((Long) SQ("select count(*) from DiaryBooks db")).intValue();
		} catch (ObjectNotFoundException e) {

			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<DiaryBooks> getDiaryBooks(int type, Pagination p) {
		if (type == 1) {
			return (List<DiaryBooks>) Q(
					"from DiaryBooks db order by db.hitCount desc ", p)
					.getResultList();
		} else if (type == 2) {
			return (List<DiaryBooks>) Q(
					"from DiaryBooks db order by db.id desc ", p)
					.getResultList();
		} else {
			return (List<DiaryBooks>) Q(
					"from DiaryBooks db order by db.diaryNum desc ", p)
					.getResultList();
		}
	}

	// -----------------------search
	public static int getDiaryBooksSizeByBookName(String bookName) {
		try {
			return ((Long) SQ("select count(*) from DiaryBooks d where d.bookName like '%"
					+ bookName + "%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<DiaryBooks> getDiaryBooksByBookName(String bookName,
			Pagination p) {
		return (List<DiaryBooks>) Q(
				"from DiaryBooks d where d.bookName like '%" + bookName
						+ "%'  order by d.id desc", p).getResultList();
	}

	public static int getDiaryBooksSizeByAuthor(String author) {
		try {
			return ((Long) SQ("select count(*) from DiaryBooks d where d.user.nick like '%"
					+ author + "%'")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<DiaryBooks> getDiaryBooksByAuthor(String author,
			Pagination p) {
		return (List<DiaryBooks>) Q(
				"from DiaryBooks d where d.user.nick  like '%" + author
						+ "%'  order by d.id desc", p).getResultList();
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "diaryBooks" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("user", user);
				put("bookName", bookName);
				put("description", description);
				put("diaryNum", diaryNum);
				put("hitCount", hitCount);
				put("diaryStyle", diaryStyle);
				put("hitCount", hitCount);
				put("viewKey", viewKey);
			}
		}.toString();
	}
}
