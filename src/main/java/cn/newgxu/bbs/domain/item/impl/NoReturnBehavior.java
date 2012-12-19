package cn.newgxu.bbs.domain.item.impl;

import cn.newgxu.bbs.domain.item.ReturnBehavior;
import cn.newgxu.bbs.domain.market.ItemLine;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class NoReturnBehavior implements ReturnBehavior {

	public void execute(ItemLine itemLine) {
		return;
	}

}
