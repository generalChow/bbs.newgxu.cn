package cn.newgxu.bbs.domain.lucky;

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

import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.lucky.LuckyGift.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-4
 * @describe  
 * 幸运帖子的礼物<br />
 * 包括属性：对应的lucky，奖品类型（虚拟道具，虚拟货币或者真实），相应的值。
 */
@Entity
@Table(name="topic_lucky_gift")
public class LuckyGift extends JPAEntity{
	private static final long serialVersionUID=2182374343903L;
	
	/**真实的礼物*/
	public static final int REALITY_GIFT=0;
	/**论坛道具*/
	public static final int FORUM_ITEM=1;
	/**西大币*/
	public static final int FORUM_MONEY=2;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="gift_type")
	private int type;
	@Column(name="gift_value")
	private String value;
	
	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="lucky_id")
	private Lucky lucky;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Lucky getLucky() {
		return lucky;
	}
	public void setLucky(Lucky lucky) {
		this.lucky = lucky;
	}
	
	public static LuckyGift get(int id) throws ObjectNotFoundException{
		return (LuckyGift)Lucky.getById(LuckyGift.class, id);
	}
}
