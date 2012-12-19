package cn.newgxu.bbs.domain.item;

import cn.newgxu.bbs.domain.item.impl.ConsumeAwayBehavior;
import cn.newgxu.bbs.domain.item.impl.UnconsumeBehavior;
import cn.newgxu.bbs.domain.market.ItemType;

/**
 * 如果不是永久性物品使用完消失
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ConsumeBehaviorFactory {

	public static ConsumeBehavior getInstance(ItemType itemType) {
		if (itemType.getId() == ItemType.PREREQUISITE_MAKE) {
			return new UnconsumeBehavior();
		} else {
			return new ConsumeAwayBehavior();
		}
	}

}
