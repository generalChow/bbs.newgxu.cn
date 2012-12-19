package cn.newgxu.bbs.domain.item;

import cn.newgxu.bbs.domain.item.impl.HasWorkBehavior;
import cn.newgxu.bbs.domain.item.impl.NoWorkBehavior;

/**
 * 特殊的物品调用专门写的功能块
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class WorkBehaviorFactory {

	public static WorkBehavior getInstance(String method) {
		if (method == null || method.equals("")) {
			return new NoWorkBehavior();
		} else {
			return new HasWorkBehavior();
		}
	}

}
