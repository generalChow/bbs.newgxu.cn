package cn.newgxu.bbs.domain.item;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.market.ItemWork;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface Useable {

	public User getObject();

	public ItemWork getWork();

	public void use(User object) throws BBSException;

}
