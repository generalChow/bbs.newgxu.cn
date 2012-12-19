package cn.newgxu.bbs.web.model.market;

import java.util.List;

import cn.newgxu.bbs.domain.StoreItem;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OfficialModel extends PaginationBaseModel {

	List<StoreItem> items;

	public List<StoreItem> getItems() {
		return items;
	}

	public void setItems(List<StoreItem> items) {
		this.items = items;
	}

}
