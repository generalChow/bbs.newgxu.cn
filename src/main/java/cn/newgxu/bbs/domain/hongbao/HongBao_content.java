package cn.newgxu.bbs.domain.hongbao;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "hongbao_content")
public class HongBao_content extends JPAEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;
	
	private String name;
	
	private int exp;
	
	private int money;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "hongbao_item", joinColumns = @JoinColumn(name = "contentid", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "itemid", referencedColumnName = "id"))
	private List<Item> items;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static HongBao_content getByContentId(int contentType) throws ObjectNotFoundException {
		return (HongBao_content) SQ("from HongBao_content h where h.id = ?1", P(1, contentType));
	}

	@SuppressWarnings("unchecked")
	public static List<HongBao_content> getHongBaoContents() {
		return Q("from HongBao_content").getResultList();
	}

	
}
