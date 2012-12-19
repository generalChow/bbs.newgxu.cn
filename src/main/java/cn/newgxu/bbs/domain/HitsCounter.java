package cn.newgxu.bbs.domain;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "hits_counter")
public class HitsCounter extends JPAEntity {

	private static final long serialVersionUID = -6707611077779314643L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_hits_counter")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "forum_id")
	private int forumId;

	@Column(name = "hits_date")
	private Date hitsDate;

	@Column(name = "hits_hour")
	private int hitsHour;

	@Column(name = "hits_count")
	private int hitsCount;

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getHitsCount() {
		return hitsCount;
	}

	public void setHitsCount(int hitsCount) {
		this.hitsCount = hitsCount;
	}

	public Date getHitsDate() {
		return hitsDate;
	}

	public void setHitsDate(Date hitsDate) {
		this.hitsDate = hitsDate;
	}

	public int getHitsHour() {
		return hitsHour;
	}

	public void setHitsHour(int hitsHour) {
		this.hitsHour = hitsHour;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// ------------------------------------------------
	public static HitsCounter getByForumId_Date_Hour(int forumId, Date date,
			int hour) throws ObjectNotFoundException {
		return (HitsCounter) SQ(
				"from HitsCounter where hitsDate = ?1 and hitsHour = ?2 and forumId = ?3",
				P(1, date), P(2, hour), P(3, forumId));
	}

	public void addHit() {
		this.hitsCount++;
	}

	public void incorporate(HitsCounter o) {
		this.hitsCount += o.getHitsCount();
	}

	public static int getTodayHitsCounter() {
		try {
			return ((Long) SQ(
					"select sum(h.hitsCount) from HitsCounter h where h.hitsDate between ?1 and ?2",
					P(1, TimerUtils.getDate()), P(2, TimerUtils.getTomorrow())))
					.intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public static int getTotalHitsCounter() {
		try {
			return ((Long) SQ("select sum(h.hitsCount) from HitsCounter h"))
					.intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "hitsCounter" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("forumId", forumId);
				put("hitsDate", hitsDate);
				put("hitsHour", hitsHour);
				put("hitsCount", hitsCount);
			}
		}.toString();
	}

	@Override
	public int hashCode() {
		return (int) (this.hitsDate.getTime() / 1000) + forumId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof HitsCounter) {
			return ((HitsCounter) obj).hashCode() == this.hashCode();
		}
		return false;
	}

}
