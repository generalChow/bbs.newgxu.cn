package cn.newgxu.bbs.domain.market;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

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
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.StoreItem;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "item")
public class Item extends JPAEntity {

	private static final long serialVersionUID = 7581594140505573073L;
	
	/** 一个特殊值，清零用户的体力（因为数据库为无符号整形不允许插入负值），so。。。 */
	public static final int TRUANCATE_POWER = 1000;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_item")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_type_id")
	private ItemType type;

	@Column(name = "item_name")
	private String name;

	@Column(name = "object_power")
	private int objectPower;

	@Column(name = "object_badboy")
	private int objectBadboy;

	@Column(name = "object_exp")
	private int objectExp;

	private int probability;

	@Column(name = "return_id")
	private int returnId;

	@Column(name = "self_power")
	private int selfPower;

	@Column(name = "self_money")
	private int selfMoney;
	// 调用的方法名称
	@Column(name = "work_method", length = 30)
	private String workMethod;

	private String effect;

	@Column(name = "expand1", length = 200)
	private String expand1;

	/**
	 * 添加了一个新的字段，这个字段中，如果对某个用户使用了这个道具，将对用户发一个短信息，<br />
	 * 内容就是这个message了<br />
	 * add by：集成显卡<br />
	 */
	@Column(name = "message", length = 255)
	private String message;
	@Column(name = "message_title", length = 50)
	private String message_title;
	
	/**
	 * 加小图标时，图标配置
	 * ALTER TABLE `item` ADD COLUMN `icon_info`  varchar(255) NULL AFTER `message`;
	 * */
	@Column(name = "icon_info")
	private String icon;
	
	
	public String getExpand1() {
		return expand1;
	}

	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getObjectBadboy() {
		return objectBadboy;
	}

	public void setObjectBadboy(int objectBadboy) {
		this.objectBadboy = objectBadboy;
	}

	public int getObjectExp() {
		return objectExp;
	}

	public void setObjectExp(int objectExp) {
		this.objectExp = objectExp;
	}

	public int getObjectPower() {
		return objectPower;
	}

	public void setObjectPower(int objectPower) {
		this.objectPower = objectPower;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public int getReturnId() {
		return returnId;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}

	public int getSelfMoney() {
		return selfMoney;
	}

	public void setSelfMoney(int selfMoney) {
		this.selfMoney = selfMoney;
	}

	public int getSelfPower() {
		return selfPower;
	}

	public void setSelfPower(int selfPower) {
		this.selfPower = selfPower;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public String getWorkMethod() {
		return workMethod;
	}

	public void setWorkMethod(String workMethod) {
		this.workMethod = workMethod;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage_title() {
		return message_title;
	}

	public void setMessage_title(String messageTitle) {
		message_title = messageTitle;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public StoreItem getStoreItem() {
		try {
			return (StoreItem) SQ("from StoreItem where item.id=?1", P(1,
					this.id));
		} catch (ObjectNotFoundException e) {
			return new StoreItem();
		}
	}

	public static Item get(int id) throws ObjectNotFoundException {
		return (Item) getById(Item.class, id);
	}

	public static Item getRandomItem() {
		List<Item> items = getRandomItems();
		int random = new Random().nextInt(getTotalProbability(items));
		int i = 0;
		Item result = null;
		for (Item item : items) {
			if (random >= i) {
				result = item;
			} else {
				break;
			}
			i += item.getProbability();
		}
		return result;
	}
	
	/**
	 * @方法名称 :getRandomItem
	 * @功能描述 :获取随机物品，base 中指定了可能随机到的物品id集合，形式为  1-2-3
	 * 						因为是指定的id，那么我们可以认为id是一定存在的
	 * 						如果可以随机到 西大币，那么形式是这样的，0xxx  xxx指定的就是西大币的值
	 * 
	 * @返回值类型 :boolean
	 *	@param templatePath 模版路径
	 *	@param templateName 模版名
	 *	@param saveName 保存的文件名
	 *	@param data 数据
	 *	@return
	 *
	 * @创建日期 :2011-6-5 集成显卡
	 * @修改记录 :
	 */
	public static Object getRandomItem(String base){
		String temp[]=base.split("-");
		Random random=new Random();
		int id=random.nextInt(temp.length);
		System.out.println("=====随机id："+id+":"+temp[id]+"=============="+temp[0]+":"+temp[1]+"===================");
		try{
			if(temp[id].startsWith("0"))
				return new Integer(temp[id]);
			return Item.get(Integer.valueOf(temp[id]));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Item> getItems() {
		return (List<Item>) Q("from Item").getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Item> getItems(Pagination p) {
		return (List<Item>) Q("from Item", p).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Item> getRandomItems() {
		return (List<Item>) Q("from Item where probability > 0",
				new Pagination(1, Integer.MAX_VALUE)).getResultList();
	}

	public static int getItemSize() throws ObjectNotFoundException {
		return ((Long) SQ("select count(*) from Item")).intValue();
	}

	public static Item getByName(String name) throws ObjectNotFoundException {
		return (Item) SQ("from Item i where i.name=?1", P(1, name));
	}

	private static int getTotalProbability(List<Item> items) {
		int total = 1;
		for (Item item : items) {
			total += item.getProbability();
		}
		return total;
	}

	public String getPicture() {
		StringBuffer sb = new StringBuffer();
		sb.append("<img src='/images/item/").append(this.id).append(".bmp' />");
		return sb.toString();
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "item" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("name", name);
			}
		}.toString();
	}
	
	/**
	 * 判断是否可以使用，如果一个物品不能使用，那么他的  expand1 会列出可以使用的时间段
	 * 
	 * @return
	 */
	public boolean isCanUse() throws BBSException{
		if(this.expand1==null||expand1.length()==0)
			return true;
		String temp[]=expand1.split("#");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			Date startD=df.parse(temp[0]);
			Date endD=df.parse(temp[1]);
			Date date=new Date();
			return date.after(startD)&&date.before(endD);
		}catch(Exception e){return false;}
	}
}
