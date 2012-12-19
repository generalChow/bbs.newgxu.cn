package cn.newgxu.bbs.domain.market;

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "item_type")
public class ItemType extends JPAEntity {

	private static final long serialVersionUID = 6411630856535781801L;

	public static final int HELPFULLY = 1;

	public static final int WEAPON = 2;

	public static final int UNCANNILY = 3;

	public static final int PREREQUISITE_MAKE = 4;

	public static final int PREREQUISITE_FAMILY = 5;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_item_type")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "type_name")
	private String name;

	@Column(name = "description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "itemType" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("name", name);
				put("description", description);
			}
		}.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static List<ItemType> getItemTypes() {
		return (List<ItemType>) Q("from ItemType")
				.getResultList();
	}

	public static ItemType get(int id) throws ObjectNotFoundException {
		return (ItemType) getById(ItemType.class, id);
	}
}
