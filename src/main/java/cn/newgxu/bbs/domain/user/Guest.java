package cn.newgxu.bbs.domain.user;

import cn.newgxu.bbs.common.Authorization;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class Guest implements Browser {

	private static int id_counter = 0;

	public Authorization getAuthorization() {
		return new Authorization(--id_counter);
	}

}
