package cn.newgxu.bbs.domain.item;

import cn.newgxu.bbs.domain.item.impl.ObjectStateChangeable;
import cn.newgxu.bbs.domain.market.ItemType;

/**
 * 调用物品增加自己的属性
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ObjectStateBehaviorFactory {

	public static StateBehavior getInstance(ItemType type) {
		return new ObjectStateChangeable();
	}

}
