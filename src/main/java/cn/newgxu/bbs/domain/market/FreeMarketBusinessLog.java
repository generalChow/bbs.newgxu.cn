package cn.newgxu.bbs.domain.market;

import java.util.Date;
import java.util.LinkedHashMap;

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

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "free_market_business_log")
public class FreeMarketBusinessLog extends JPAEntity {

	private static final long serialVersionUID = -6854753795608338538L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_free_market_business_log")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private User seller;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private User buyer;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	private int price;

	private int income;

	private int tax;

	@Column(name = "buy_time")
	private Date buyTime;

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	// ------------------------------------------------

	public static void log(User seller, User buyer, Item item, int price,
			int income, int tax) {
		FreeMarketBusinessLog log = new FreeMarketBusinessLog();
		log.setSeller(seller);
		log.setBuyer(buyer);
		log.setItem(item);
		log.setPrice(price);
		log.setIncome(income);
		log.setTax(tax);
		log.setBuyTime(new Date());

		log.save();
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "freeMarketBusinessLog" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("seller", seller);
				put("buyer", buyer);
				put("item", item);
				put("price", price);
				put("income", income);
				put("tax", tax);
				put("buyTime", buyTime);
			}
		}.toString();
	}

}
