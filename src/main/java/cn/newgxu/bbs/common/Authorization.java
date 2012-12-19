package cn.newgxu.bbs.common;


/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class Authorization {

	private int userId = 0;

	public int getId() { 
		return userId;
	}

	public Authorization(int userId) {
		this.userId = userId;
	}

	public boolean isLogin() {
		if (userId > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
//		return "authorization" + new LinkedHashMap<String, Object>() {
//			{
//				put("userId", userId);
//			}
//		}.toString();
		return "authorization " + "{userId: " + this.userId + "}";
	}

}
