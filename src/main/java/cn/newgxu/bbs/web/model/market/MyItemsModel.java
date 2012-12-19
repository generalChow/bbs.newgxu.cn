package cn.newgxu.bbs.web.model.market;

import java.util.List;

import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MyItemsModel extends PaginationBaseModel {
	
	private List<ItemLine> items;
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ItemLine> getItems() {
		return items;
	}

	public void setItems(List<ItemLine> items) {
		this.items = items;
	}

}
