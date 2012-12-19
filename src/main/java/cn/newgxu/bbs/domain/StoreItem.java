package cn.newgxu.bbs.domain;

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
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "store_item")
public class StoreItem extends JPAEntity {

	private static final long serialVersionUID = 4622410741322971053L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_store_item")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "item_id")
	private Item item;

	@Column(name = "unit_price")
	private int unitPrice;

	private int number;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	// ------------------------------------------------

	public static StoreItem get(int id) throws ObjectNotFoundException {
		return (StoreItem) getById(StoreItem.class, id);
	}

	public void save() {
		if (this.number < 0) {
			super.delete();
		} else {
			super.save();
		}
	}

	public void addNumber(int number) {
		this.number += number;
		this.number = this.number < 0 ? 0 : this.number;
	}

	public void sell(int number) throws BBSException {
		if (this.number < number) {
			throw new BBSException(BBSExceptionMessage.OUT_OF_STORAGE);
		}
		addNumber(-number);
	}

	@SuppressWarnings("unchecked")
	public static List<StoreItem> getStoreItems(Pagination p) {
		return (List<StoreItem>) Q("from StoreItem i where number>0  order by i.id desc", p).getResultList();
	}

	public static int getNumberOfStoreItems() {
		try {
			return ((Long) SQ("select count(*) from StoreItem i where number>0")).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "store" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("item", item);
				put("number", number);
			}
		}.toString();
	}

	public static StoreItem getByItemId(int id) throws ObjectNotFoundException{
		return (StoreItem) Q("from StoreItem i where i.item.id = ?1 ", P(1, id)).getSingleResult();
	}

}
