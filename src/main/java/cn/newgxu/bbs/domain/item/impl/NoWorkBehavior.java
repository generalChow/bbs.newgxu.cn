package cn.newgxu.bbs.domain.item.impl;

import cn.newgxu.bbs.domain.item.WorkBehavior;
import cn.newgxu.bbs.domain.market.ItemLine;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class NoWorkBehavior implements WorkBehavior {

	public void execute(ItemLine itemLine) {
		return;
	}

}
