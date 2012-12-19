package cn.newgxu.bbs.domain.group.impl;


/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AuthorizationPermission extends BasicPermission {

	public boolean isInVirtualTime() {
		return true;
	}

	public boolean canLeavelUp() {
		return false;
	}

	public boolean isEditTitleFree() {
		return true;
	}

}
