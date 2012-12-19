package cn.newgxu.bbs.domain.item;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.market.ItemLine;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface StateBehavior {

	public void change(ItemLine line) throws BBSException;

}
