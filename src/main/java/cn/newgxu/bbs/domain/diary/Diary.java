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
//import javax.persistence.Transient;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.filter.FilterUtil;
import cn.newgxu.bbs.common.util.Util;
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
@Table(name = "diary")
public class Diary extends JPAEntity {

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "diary_book_id")
	private DiaryBooks diaryBook;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "userId")
	private User user;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "weather")
	private int weather;

	@Column(name = "bg_type")
	private int bg_type = 1;

	@Column(name = "humor")
	private int humor;

	@Column(name = "hit_count")
	private int hitCount = 0;

	@Column(name = "Locked_type")
	private int lockedType;

	@Column(name = "allowcomment")
	private int allowcomment;

	@Column(name = "add_date")
	private Date addDate;

	@Column(name = "ip")
	private String ip;

	@Column(name = "isDel")
	private int isDel = 0;

//	@Transient
//	private String contentFilter;

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
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

	public DiaryBooks getDiaryBook() {
		return diaryBook;
	}

	public void setDiaryBook(DiaryBooks diaryBook) {
		this.diaryBook = diaryBook;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getHumor() {
		return humor;
	}

	public void setHumor(int humor) {
		this.humor = humor;
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

	public int getLockedType() {
		return lockedType;
	}

	public void setLockedType(int lockedType) {
		this.lockedType = lockedType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getWeather() {
		return weather;
	}

	public void setWeather(int weather) {
		this.weather = weather;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	// ----------------------
	public String getContentFilter() {
		return FilterUtil.diaryContent(getContent());
	}

	public static Diary get(int id) throws ObjectNotFoundException {
		return (Diary) getById(Diary.class, id);
	}

	public static int getDiarysSize(DiaryBooks diaryBook) {
		try {
			return ((Long) SQ(
					"select count(*) from Diary d where d.diaryBook = ?1  and d.isDel = 0 ",
					P(1, diaryBook))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getDiarysSize(DiaryBooks diaryBook, String time) {
		try {
			return ((Long) SQ(
					"select count(*) from Diary d where d.diaryBook = ?1  and d.isDel = 0 and addDate between '"
							+ Util.getDisignDate(time, 0)
							+ "' and '"
							+ Util.getDisignDate(time, 0) + "' ", P(1,
							diaryBook))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public static int getDiarysSize() {
		try {
			return ((Long) SQ("select count(*) from Diary d where d.isDel = 0 "))
					.intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Diary> getDiarys(DiaryBooks diaryBook, Pagination p) {

		return (List<Diary>) Q(
				"from Diary d where d.diaryBook = ?1 and d.isDel = 0 order by d.addDate desc",
				P(1, diaryBook), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Diary> getDiarys(DiaryBooks diaryBook, Pagination p,
			String time) {

		return (List<Diary>) Q(
				"from Diary d where d.diaryBook = ?1 and d.isDel = 0 and addDate between '"
						+ Util.getDisignDate(time, 0) + "' and '"
						+ Util.getDisignDate(time, 0)
						+ "' order by d.addDate desc", P(1, diaryBook), p)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Diary> getDiarys(int type, Pagination p) {
		if (type == 1) {
			return (List<Diary>) Q(
					"from Diary d where d.isDel = 0  order by d.hitCount desc",
					p).getResultList();
		} else {
			return (List<Diary>) Q(
					"from Diary d where d.isDel = 0 order by d.addDate desc", p)
					.getResultList();
		}
	}
	
	/**
	 * 为了方便新首页获取到日记数据而新添加的方法
	 * @param type 类型
	 * @param number 一次获取的数量
	 * @since 2012-04-17
	 */
	@SuppressWarnings("unchecked")
	public static List<Diary> getDiarys(int type, int number) {
		if (type == 1) {
			return (List<Diary>) Q(
					"from Diary d where d.isDel = 0  order by d.hitCount desc").setFirstResult(0).setMaxResults(number).getResultList();
		} else {
			return (List<Diary>) Q(
					"from Diary d where d.isDel = 0 order by d.addDate desc").setFirstResult(0).setMaxResults(number).getResultList();
		}
	}

	// ------------------------------------search
	public static int getDiarysSizeByTitle(String title) {
		try {
			return ((Long) SQ("select count(*) from Diary d where d.title like '%"
					+ title + "%' and d.isDel = 0 order by d.addDate desc"))
					.intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Diary> getDiarysByTitle(String title, Pagination p) {
		return (List<Diary>) Q(
				"from Diary d where d.title like '%" + title
						+ "%' and d.isDel = 0 order by d.addDate desc", p)
				.getResultList();
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "diary" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("user", user);
				put("title", title);
				put("content", content);
				put("weather", weather);
				put("bg_type", bg_type);
				put("humor", humor);
				put("hitCount", hitCount);
				put("lockedType", lockedType);
				put("allowcomment", allowcomment);
				put("addDate", addDate);
				put("ip", ip);
			}
		}.toString();
	}

}
