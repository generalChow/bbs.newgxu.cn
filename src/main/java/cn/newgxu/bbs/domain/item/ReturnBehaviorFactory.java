package cn.newgxu.bbs.domain.item;

import cn.newgxu.bbs.domain.item.impl.HasReturnBehavior;
import cn.newgxu.bbs.domain.item.impl.NoReturnBehavior;
import cn.newgxu.bbs.domain.market.ItemType;

/**
 * 是否是永久物品或者随机性物品
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ReturnBehaviorFactory {

	public static ReturnBehavior getInstance(ItemType itemType) {
		if (itemType.getId() == ItemType.PREREQUISITE_MAKE
				|| itemType.getId() == ItemType.UNCANNILY) {
			return new HasReturnBehavior();
		} else {
			return new NoReturnBehavior();
		}
	}

}
