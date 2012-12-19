package cn.newgxu.bbs.domain.item;

import cn.newgxu.bbs.domain.item.impl.SelfStateChangeable;
import cn.newgxu.bbs.domain.market.ItemType;

/**
 * 使用物品消耗自己的属性
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SelfStateBehaviorFactory {

	public static StateBehavior getInstance(ItemType type) {
		return new SelfStateChangeable();
	}

}
