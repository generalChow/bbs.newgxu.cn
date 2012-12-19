package cn.newgxu.bbs.domain.market;

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
import javax.persistence.Transient;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.item.ConsumeBehaviorFactory;
import cn.newgxu.bbs.domain.item.ObjectStateBehaviorFactory;
import cn.newgxu.bbs.domain.item.ReturnBehaviorFactory;
import cn.newgxu.bbs.domain.item.SelfStateBehaviorFactory;
import cn.newgxu.bbs.domain.item.Useable;
import cn.newgxu.bbs.domain.item.WorkBehaviorFactory;
import cn.newgxu.bbs.domain.message.Message;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
/**
 * @author polly
 * @version 0.0.1
 * 
 *          TODO
 */
@Entity
@Table(name = "item_line")
public class ItemLine extends JPAEntity implements Useable {

	private static final long serialVersionUID = -3823171890330319996L;

	/**
	 * 使用目标。
	 */
	@Transient
	private User object;

	@Transient
	private Topic topic;

	@Transient
	private ItemWork work;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_item_line")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@Column(name = "maker_id")
	private int makerId;

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

	public int getMakerId() {
		return makerId;
	}

	public void setMakerId(int makerId) {
		this.makerId = makerId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// ------------------------------------------------

	public static ItemLine get(long id) throws ObjectNotFoundException {
		return (ItemLine) getById(ItemLine.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<ItemLine> getItemLines(User user, Pagination p) {
		return (List<ItemLine>) Q(
				"from ItemLine i where user = ?1 order by i.id desc",
				P(1, user), p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<ItemLine> getItemLinesByUser(User user, int item_id) {
		return (List<ItemLine>) Q(
				"from ItemLine i where user = ?1 and item.id=?2", P(1, user),
				P(2, item_id)).getResultList();
	}

	public void setMaker(User maker) {
		if (maker.getId() < 1) {
			this.makerId = 0;
		} else {
			this.makerId = maker.getId();
		}
	}

	public User getMaker() {
		if (this.makerId < 1) {
			return new User();
		}
		return User.getCertainExist(this.makerId);
	}

	public User getObject() {
		return object;
	}

	public Topic getTopic() {
		return topic;
	}

	public ItemWork getWork() {
		return work;
	}

	public void use(User object) throws BBSException {
		this.object = object;
		this.work = new ItemWork();
		//本身状态的改变
		SelfStateBehaviorFactory.getInstance(getItem().getType()).change(this);
		//效果
		ObjectStateBehaviorFactory.getInstance(getItem().getType()).change(this);
		//有返回物品
		ReturnBehaviorFactory.getInstance(getItem().getType()).execute(this);
		//应用效果
		WorkBehaviorFactory.getInstance(getItem().getWorkMethod())
				.execute(this);
		//消耗性物品
		ConsumeBehaviorFactory.getInstance(getItem().getType()).use(this);
		ItemUsedLog.log(this.getUser(), this.object, this.getItem(), this.work,
				0);
		
		Item i=this.getItem();
		if(i.getMessage()!=null&&i.getMessage().trim().length()>0){
			if(i.getMessage_title()==null)
				i.setMessage_title("道具'"+i.getName()+"'使用通知");
			Message.sendItemLineMessage(this);
		}
	}

	public void use(Topic topic) throws BBSException {
		this.topic = topic;
		this.work = new ItemWork();
		// SelfStateBehaviorFactory.getInstance(getItem().getType()).change(this);
		// ObjectStateBehaviorFactory.getInstance(getItem().getType()).change(this);
		ReturnBehaviorFactory.getInstance(getItem().getType()).execute(this);
		WorkBehaviorFactory.getInstance(getItem().getWorkMethod())
				.execute(this);
		ConsumeBehaviorFactory.getInstance(getItem().getType()).use(this);
		// ItemUsedLog.log(this.getUser(), this.object, this.getItem(),
		// this.work,0);
	}

	public static int getNumberOfItemLines(User user) {
		try {
			return ((Long) SQ(
					"select count(*) from ItemLine i where user = ?1", P(1,
							user))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public void changeOwner(User object, String wish) {
		log.debug("改变物品的拥有者!");
		ItemLine newItemLine = new ItemLine();
		newItemLine.setUser(object);
		newItemLine.setItem(this.getItem());
		newItemLine.setMaker(this.getMaker());
		newItemLine.save();

		this.delete();
		
		this.work = new ItemWork();

		ItemUsedLog.log(this.getUser(), object, this.getItem(), this.work, 1);

		String str = this.getUser().getNick() + "网友成功送你"
				+ this.getItem().getName() + "物品,详情请查看物流日记";
		String strWish = this.getUser().getNick() + "网友送上的祝福： " + wish;

		// 参数说明 最后一个参数为接收人昵称
		Message.sendMessage(wish == null ? "物流消息" : this.getUser().getNick()
				+ "的祝福", wish == null ? str : strWish, 1, object, this
				.getUser(), object.getNick());
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "itemLine" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("user", user);
				put("item", item);
				put("makerId", makerId);
			}
		}.toString();
	}

}
