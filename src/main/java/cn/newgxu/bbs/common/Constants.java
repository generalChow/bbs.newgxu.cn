package cn.newgxu.bbs.common;

import cn.newgxu.bbs.domain.group.GroupManager;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class Constants {

	public static final String STATIC_DOMAIN = "best.newgxu.cn";

	public static final String USER_SESSION = "cn.newgxu.user.session";

	public static final int DEFAULT_PAGE_SIZE = 30;

	public static final int DEFAULT_MAX_SIZE = 100;

	public static final int DEFAULT_MIN_SIZE = 10;

	public static final int NUMBER_OF_TOPICS = 30;

	public static final int NUMBER_OF_REPLIES = 30;

	public static final int NUMBER_OF_DIARY = 5;

	public static final int INDEX_NUMBER_OF_DIARY = 10;

	public static final int INDEX_NUMBER_OF_DIARYBOOK = 10;

	public static final int NUMBER_OF_DIARYCOMMENT = 5;
	
	/**
	 * 帖子中上传图片的最大合适宽度, 超过这个宽度就会被按照这个宽度等比例压缩
	 */
	public static final int IMAGE_MAX_PROPER_WIDTH = 635;

	public static final String ORIGINAL_URL = "original_url";

	public static final String RE_INPUT_USER = "re_input_user";

	public static final int DEFAULT_GROUP_ID = 1;

	public static final int DEFAULT_GROUP_TYPE = GroupManager.BASIC_GROUP;

	public static final String UPLOADING_STATS = "cn.newgxu.bbs.uploadstats";

	public static final int DEFAULT_MONEY = 50;

	public static final String AUTH_USER_COOKIE = "cn.newgxu.user.cookie";

	public static final String AUTOLOGIN_COOKIE = "cn.newgxu.user.autologin";

	public static final int TOPIC_POWER = 3;

	public static final int REPLY_POWER = 1;

	public static final String MESSAGE_SESSION = "cn.newgxu.message";

	public static final int FREE_MARKET_SELL_DAY = 7;

	public static final int FREE_MARKET_SELL_MIN_PRICE = 100;

	public static final int FREE_MARKET_SELL_MAX_PRICE = 999999;

	public static final int FREE_MARKET_BUSINESS_TAX = 5;


//	public static final String FILE_ROOT = "/newgxu/tomcat/webapps/ROOT"; //166服务器使用
	public static final String FILE_ROOT = "D:/coding4fun/workspaces/sts_2.92@2012-08-26/newgxu-bbs/src/main/webapp";//本地测试用
	public static final String FILE_FACE_ROOT = "/newgxu/tomcat/webapps/ROOT";
	public static final int RANDOM_STRING_LENGTH = 4;

	public static final int RANDOM_STRING_HEIGHT = 17;

	public static final int RANDOM_STRING_WIDTH = 38;

	public static final String VALID_CODE_SESSION = "cn.newgxu.valid.code";

	public static final int TOPIC_GUILD_LENGTH = 3;

	public static final String SCREEN_CONTENT = "＝＝＝＝＝＝＝＝＝＝<br />"
			+ "　 内容已经被屏蔽！<br />" + "＝＝＝＝＝＝＝＝＝＝";

	public static final String DEFAULT_TITLE = "------";

	public static final String DEFAULT_USER_FACE = "<img src=\"/images/de_face/01.gif\" style=\"border:1px;\" />";

	public static final int VOTE_OPTION_LENGTH = 50;

	public static final int VOTE_OPTION_SIZE = 30;

	public static final byte REG_TYPE_STUDENT = 1;
	public static final byte REG_TYPE_TEACHER = 2;
	public static final byte REG_TYPE_DOCTOR = 3;
	public static final byte REG_TYPE_OUTER = 4;
	public static final byte REG_TYPE_FRESHMAN = 100;

	public static final byte ACCOUNT_STATUS_NORMAL = 1;
	public static final byte ACCOUNT_STATUS_FORBID = 2;
	public static final byte ACCOUNT_STATUS_WAIT = 3;
	public static final byte ACCOUNT_STATUS_UNPAST = 4;

	public static final byte LOAN_STATUS_WAIT = 1;
	public static final byte LOAN_STATUS_CANCEL = 2;
	public static final byte LOAN_STATUS_PAST = 3;
	public static final byte LOAN_STATUS_UNPAST = 4;
	public static final byte LOAN_STATUS_REPAY = 5;

	public static final byte BANK_OP_TYPE_OTHER = 0;
	public static final byte BANK_OP_TYPE_CURR = 1;
	public static final byte BANK_OP_TYPE_FIXED = 2;
	public static final byte BANK_OP_TYPE_LOAN = 3;
	public static final byte BANK_OP_TYPE_VIRE_OUT = 4;
	public static final byte BANK_OP_TYPE_VIRE_IN = 5;

	public static final String ONLINE_GD = "在线人数图例";
	public static final String TODAY_TOPIC_GD = "今日帖数图例";
	public static final String TOTAL_TOPIC_GD = "主题数图例";
	public static final String TOTAL_REPLY_GD = "总帖数图例";
	public static final String GROUP_ONLINE_GD = "用户组在线图例";

	public static final String ADMIN_SESSION = "cn.newgxu.admin.session";

	public static final String SORT_BY_CREATETIME = "createTime";

	public static final String SORT_BY_REPLYTIME = "replyTime";
}
