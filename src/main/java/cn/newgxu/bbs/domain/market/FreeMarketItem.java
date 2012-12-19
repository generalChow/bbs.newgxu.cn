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

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.util.Util;
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
@Table(name = "free_market_item")
public class FreeMarketItem extends JPAEntity {

	private static final long serialVersionUID = -5898272782202872555L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_free_market_item")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "unit_price")
	private int unitPrice;

	@Column(name = "overdue_date")
	private Date overdueDate;

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

	public Date getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// ------------------------------------------------

	public static FreeMarketItem get(int id) throws ObjectNotFoundException {
		return (FreeMarketItem) getById(FreeMarketItem.class, id);
	}

	public void setMaker(User maker) {
		this.makerId = maker.getId();
	}

	public User getMaker() {
		if (this.makerId < 1) {
			return new User();
		}
		return User.getCertainExist(this.makerId);
	}

	public static void addItemSell(Item item, User user, int price, int makerId) {
		FreeMarketItem fi = new FreeMarketItem();
		fi.setItem(item);
		fi.setUser(user);
		fi.setUnitPrice(price);
		fi.setOverdueDate(Util.getDateAfterDay(Constants.FREE_MARKET_SELL_DAY));
		fi.setMakerId(makerId);

		fi.save();
	}

	@SuppressWarnings("unchecked")
	public static List<FreeMarketItem> getItemsByUser(User user, Pagination p) {
		return (List<FreeMarketItem>) Q(
				"from FreeMarketItem i where user = ?1 order by i.overdueDate desc",
				P(1, user), p).getResultList();

	}

	public static int getNumberOfItemsByUser(User user) {
		try {
			return ((Long) SQ(
					"select count(*) from FreeMarketItem i where user = ?1", P(
							1, user))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<FreeMarketItem> getItems(Pagination p) {
		return (List<FreeMarketItem>) Q(
				"from FreeMarketItem i order by i.overdueDate desc", p)
				.getResultList();
	}

	public static int getNumberOfItems() {
		try {
			return ((Long) SQ("select count(*) from FreeMarketItem i"))
					.intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<FreeMarketItem> getItemsByItem(Item item, Pagination p) {
		return (List<FreeMarketItem>) Q(
				"from FreeMarketItem i where item = ?1 order by i.overdueDate desc",
				P(1, item), p).getResultList();
	}

	public static int getNumberOfItemsByItem(Item item) {
		try {
			return ((Long) SQ(
					"select count(*) from FreeMarketItem i where item = ?1", P(
							1, item))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	public String getOverDueTimeFormat() {
		return Util.formatSellTime(getOverdueDate());
	}

	@SuppressWarnings("unchecked")
	public static List<FreeMarketItem> getOverdueItems(Date date) {
		return (List<FreeMarketItem>) Q(
				"from FreeMarketItem i where overdueDate <= ?1", P(1, date))
				.getResultList();
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "freeMarketItem" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("item", item);
				put("user", user);
				put("unitPrice", unitPrice);
				put("overdueDate", overdueDate);
			}
		}.toString();
	}
}
