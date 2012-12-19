package cn.newgxu.bbs.domain.market;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "item_work")
public class ItemWork extends JPAEntity {

	private static final long serialVersionUID = 1L;
	// 使用对象是user
	public static final int OBJECT_TYPE_USER = 1;
	// 使用对象是帖子
	public static final int OBJECT_TYPE_TOPIC = 2;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_item_work")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id = -1;

	@Column(name = "object_type")
	private int obType;

	@Column(name = "object_id")
	private int obId;

	@Column(name = "item_id")
	private int itemId;

	@Column(name = "valid_date")
	private Date validDate;

	@Column(name = "expire_date")
	private Date expireDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getObType() {
		return obType;
	}

	public void setObType(int obType) {
		this.obType = obType;
	}

	public int getObId() {
		return obId;
	}

	public void setObId(int obId) {
		this.obId = obId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String remark;

	// ------------------------------------------------
	public void save(int obType, int obId, int itemId, Date validDate,
			Date expireDate, String remark) {
		this.obType = obType;
		this.obId = obId;
		this.itemId = itemId;
		this.validDate = validDate;
		this.expireDate = expireDate;
		this.remark = remark;
		this.save();
	}

	public void delete(int object_type, int object_id, int[] items) {
		Q(
				"delete from ItemWork t where obType = ?1 and obId = ?2 and itemId in ("
						+ Util.intsToString(items) + ")", P(1, object_type),
				P(2, object_id)).executeUpdate();
	}

	// ------------------------------------------------
	public static ItemWork get(long id) throws ObjectNotFoundException {
		return (ItemWork) getById(ItemWork.class, id);
	}

	public static boolean hasItemWork(Topic topic) {
		try {
			return (Long) SQ(
					"select count(*) from ItemWork i where i.obId = ?1 and i.obType=?2",
					P(1, topic.getId()), P(2, OBJECT_TYPE_TOPIC)) > 0 ? true
					: false;
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<ItemWork> getItemWorks(int object_type, int object_id,
			int[] items, Date date) {
		return (List<ItemWork>) Q(
				"from ItemWork t where obType = ?1 and obId = ?2 and itemId in ("
						+ Util.intsToString(items)
						+ ") and validDate <= ?3 and expireDate > ?3 order by validDate desc",
				P(1, object_type), P(2, object_id), P(3, date)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<ItemWork> getItemWorksOverdue(int object_type) {
		return (List<ItemWork>) Q(
				"from ItemWork t where obType = ?1 and expireDate <= ?2 ",
				P(1, object_type), P(2, Util.getCurrentTime())).getResultList();
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "itemLine" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("ob_type", obType);
				put("ob_id", obId);
				put("item_id", itemId);
				put("valid_date", validDate);
				put("expire_date", expireDate);
				put("remark", remark);
			}
		}.toString();
	}

}
