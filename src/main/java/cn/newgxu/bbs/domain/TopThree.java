package cn.newgxu.bbs.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;

/**
 * 每周最活跃用户历史榜单。
 * 
 * @author longkai
 * @version 1.0
 * @since 2012-09-21
 */
@Entity
@Table(name = "top_three")
public class TopThree extends JPAEntity {

	private static final long	serialVersionUID	= 1L;
	
	public static final int LAST_WEEK_ACTIVE_USER_TOP_THREE = 1;

	@Id
	@GeneratedValue
	private int					id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "first_user_id")
	private User				first;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "second_user_id")
	private User				second;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "third_user_id")
	private User				thrid;

	@Column(name = "weekly")
	private int					weekly;

	@Column(name = "topped_time")
	private Date				toppedTime;

	@Column(name = "type")
	private int					type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getFirst() {
		return first;
	}

	public void setFirst(User first) {
		this.first = first;
	}

	public User getSecond() {
		return second;
	}

	public void setSecond(User second) {
		this.second = second;
	}

	public User getThrid() {
		return thrid;
	}

	public void setThrid(User thrid) {
		this.thrid = thrid;
	}

	public int getWeekly() {
		return weekly;
	}

	public void setWeekly(int weekly) {
		this.weekly = weekly;
	}

	public Date getToppedTime() {
		return toppedTime;
	}

	public void setToppedTime(Date toppedTime) {
		this.toppedTime = toppedTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 获取历史榜单列表。
	 * 
	 * @param pagination
	 * @return 列表
	 */
	@SuppressWarnings("unchecked")
	public static List<TopThree> getTopThrees(Pagination pagination) {
		return (List<TopThree>) Q("from TopThree", pagination).getResultList();
	}

	/**
	 * 保存一个周前三历史榜单。
	 * 
	 * @param champion
	 */
	public static void persist(TopThree topThree) {
		getEntityManager().persist(topThree);
	}
	
	@SuppressWarnings("unchecked")
	public static List<TopThree> getTopThrees(int type, Pagination pagination) {
		return Q("from TopThree t where t.type = " + type, pagination).getResultList();
	}

}
