package cn.newgxu.bbs.web.model.admin;

import java.util.List;

import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemType;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ItemManageModel extends PaginationBaseModel {
	private int id;
	private int item_type_id;

	private String name;
	private int objectPower;
	private int objectBadboy;
	private int objectExp;
	private int probability;
	private int returnId;
	private int selfPower;
	private int selfMoney;
	private String effect;
	private String message;
	private String message_title;
	
	private int unitPrice;
	private int number;
	
	private List<ItemType> itemTypes;
	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getItem_type_id() {
		return item_type_id;
	}

	public String getName() {
		return name;
	}

	public int getObjectPower() {
		return objectPower;
	}

	public int getObjectBadboy() {
		return objectBadboy;
	}

	public int getObjectExp() {
		return objectExp;
	}

	public int getProbability() {
		return probability;
	}

	public int getReturnId() {
		return returnId;
	}

	public int getSelfPower() {
		return selfPower;
	}

	public int getSelfMoney() {
		return selfMoney;
	}

	public String getEffect() {
		return effect;
	}

	public List<ItemType> getItemTypes() {
		return itemTypes;
	}

	public void setItem_type_id(int item_type_id) {
		this.item_type_id = item_type_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setObjectPower(int objectPower) {
		this.objectPower = objectPower;
	}

	public void setObjectBadboy(int objectBadboy) {
		this.objectBadboy = objectBadboy;
	}

	public void setObjectExp(int objectExp) {
		this.objectExp = objectExp;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}

	public void setSelfPower(int selfPower) {
		this.selfPower = selfPower;
	}

	public void setSelfMoney(int selfMoney) {
		this.selfMoney = selfMoney;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public void setItemTypes(List<ItemType> itemTypes) {
		this.itemTypes = itemTypes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public int getNumber() {
		return number;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setNumber(int number) {
		this.number = number;
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
}
