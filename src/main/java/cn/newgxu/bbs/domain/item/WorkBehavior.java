package cn.newgxu.bbs.domain.item;

import cn.newgxu.bbs.domain.market.ItemLine;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface WorkBehavior {
	
	public void execute(ItemLine line);

}
