package cn.newgxu.bbs.web.model.market;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.newgxu.bbs.domain.market.FreeMarketItem;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class FreeModel extends PaginationBaseModel {

	public static final String ALL = "all";

	public static final String ME = "me";

	private List<FreeMarketItem> items;

	private List<Item> category;

	private User user;

	private int categoryId;

	private String seller;

	public List<Item> getCategory() {
		return category;
	}

	public void setCategory(List<Item> category) {
		this.category = category;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getSeller() {
		return StringUtils.defaultIfEmpty(this.seller, ALL);
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<FreeMarketItem> getItems() {
		return items;
	}

	public void setItems(List<FreeMarketItem> items) {
		this.items = items;
	}

	public boolean isViewMyItems() {
		return ME.equals(getSeller());
	}

	public boolean isViewAllUsersItems() {
		return !isViewMyItems();
	}

}
