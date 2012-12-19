package cn.newgxu.bbs.domain.diary;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "diary_comment")
public class DiaryComment extends JPAEntity {

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "diaryId")
	private Diary diary;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "userId")
	private User user;

	@Column(name = "comtent")
	private String content;

	@Column(name = "dateTime")
	private Date dateTime;

	@Column(name = "ip")
	private String ip;

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

	public Diary getDiary() {
		return diary;
	}

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// -----------------------

	public static DiaryComment get(int id) throws ObjectNotFoundException {

		return (DiaryComment) getById(DiaryComment.class, id);
	}

	public static int CommentSize(Diary diary) {
		try {
			return ((Long) SQ(
					"select count(*) from DiaryComment dc where dc.diary = ?1 ",
					P(1, diary))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<DiaryComment> getDiaryComments(Diary diary, Pagination p) {
		return (List<DiaryComment>) Q(
				"from DiaryComment dc where dc.diary = ?1", P(1, diary), p)
				.getResultList();
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "diaryComment" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("user", user);
				put("diary", diary);
				put("content", content);
				put("dateTime", dateTime);
				put("ip", ip);
			}
		}.toString();
	}
}
