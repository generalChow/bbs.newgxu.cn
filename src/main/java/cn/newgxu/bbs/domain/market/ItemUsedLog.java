package cn.newgxu.bbs.domain.market;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "item_used_log")
public class ItemUsedLog extends JPAEntity {

	private static final long serialVersionUID = 1941802093722446784L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_item_used_log")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "object_id")
	private User object;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@Column(name = "work_id")
	private long workId;

	@Column(name = "used_time")
	private Date usedTime = new Date();

	private int type;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getObject() {
		return object;
	}

	public void setObject(User object) {
		this.object = object;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	// ------------------------------------------------

	public static void log(User user, User object, Item item, ItemWork work,
			int type) {
		ItemUsedLog log = new ItemUsedLog();
		log.setUser(user);
		log.setObject(object);
		log.setItem(item);
		log.setWorkId(work.getId());
		log.setType(type);
		log.save();
	}

	/*
	 * 
	 * 取得物品转出转入的记录type 1转出，type 2转入
	 */
	@SuppressWarnings("unchecked")
	public static List<ItemUsedLog> getByType(User user, int type, Pagination p) {

		try {
			if (type == 1) {
				p
						.setRecordSize(((Long) SQ(
								"select count(*) from ItemUsedLog i where user = ?1 and type=1",
								P(1, user))).intValue());

				return (List<ItemUsedLog>) Q(
						"from ItemUsedLog i where user = ?1 and type = 1 order by id desc",
						P(1, user), p).getResultList();
			} else if (type == 2) {
				p
						.setRecordSize(((Long) SQ(
								"select count(*) from ItemUsedLog i where i.object = ?1 and type=1",
								P(1, user))).intValue());
				return (List<ItemUsedLog>) Q(
						"from ItemUsedLog i where i.object = ?1 and type = 1 order by id desc",
						P(1, user), p).getResultList();
			} else if (type == 0) {
				p
						.setRecordSize(((Long) SQ(
								"select count(*) from ItemUsedLog i where (i.object = ?1 or i.user=?2) and type=1",
								P(1, user), P(2, user))).intValue());
				return (List<ItemUsedLog>) Q(
						"from ItemUsedLog i where (i.object = ?1 or user=?2) and type = 1 order by id desc",
						P(1, user), P(2, user), p).getResultList();
			}
		} catch (ObjectNotFoundException e) {
			p.setRecordSize(0);
			return null;
		}
		return null;
	}

	/**
	 * 获取user使用了物品id为itemId的记录。
	 * 返回的是结果集中的第一个对象，为null就没有结果。
	 * 判断条件：object_id=user.id ，type=0（type为1时，只能说明user收到了物品，不是使用）
	 * @param user
	 * @param itemId
	 * @return
	 */
	public static ItemUsedLog getUsedLog(User user,Integer itemId){
		try{
			return (ItemUsedLog)Q("from ItemUsedLog where object_id=?1 and item_id=?2 and type=0",P(1,user.getId()),P(2,itemId)).getResultList().get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	public static int getCountUseItemByUser(User user,Item item) throws ObjectNotFoundException{
		return ((Long) SQ(
				"select count(*) from ItemUsedLog i where user = ?1 and type=0 and item=?2",
				P(1, user),P(2,item))).intValue();
	}
	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "itemUsedLog" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("user", user);
				put("object", object);
				put("item", item);
				put("usedTime", usedTime);
				put("type", type);
			}
		}.toString();
	}

}
