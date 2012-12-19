package cn.newgxu.bbs.domain.item.impl;

import cn.newgxu.bbs.domain.item.WorkBehavior;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.market.ItemWorkManager;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HasWorkBehavior implements WorkBehavior {

	public void execute(ItemLine itemLine) {
		try {
			ItemWorkManager.class.getMethod(itemLine.getItem().getWorkMethod(),
					new Class[] { ItemLine.class }).invoke(null, itemLine);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
