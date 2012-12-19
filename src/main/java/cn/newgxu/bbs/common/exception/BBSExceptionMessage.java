package cn.newgxu.bbs.common.exception;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BBSExceptionMessage {

	public static final String NOT_LOGIN = "你还没有登录！";

	public static final String OUT_OF_MONEY = "你的西大币不足！";

	public static final String OUT_OF_BALANCE = "你的存款不足！";

	public static final String OUT_OF_ACCEPTABLE_SIZE = "超过允许的最大大小！";

	public static final String USERNAME_EMPTY = "用户名不能为空！";
	
	public static final String TURENAME_ERROR = "您的真实姓名不正确！";
	
	public static final String IDCARD_ERROR = "您的身份证号不正确！";

	public static final String USERNAME_TOO_SHORT = "用户名长度少于指定长度！";

	public static final String USERNAME_TOO_LONG = "用户名长度超过指定长度！";

	public static final String PASSWORD_EMPTY = "密码不能为空！";

	public static final String PASSWORD_TOO_SHORT = "密码过短，请输入不少于6个字符！";

	public static final String PASSWORD_NOT_EQUALS = "两次输入密码不一致！";

	public static final String NICK_EMPTY = "昵称不能为空！";

	public static final String NICKS_EMPTY = "昵称不能为空！";

	public static final String NICK_TOO_SHORT = "昵称少于指定长度！";

	public static final String NICK_TOO_LONG = "昵称超过指定长度！";

	public static final String TRUENAME_EMPTY = "真实姓名不能为空！";

	public static final String TRUENAME_TOO_LONG = "真实姓名超过指定长度！";

	public static final String XUEHAO_NOT_EXIST = "学号有误或您的数据不在数据库中！";

	public static final String IDCARD_INVALID = "您的学号或身份证号有误！";

	public static final String XUEHAO_INVALID = "您的选课系统学号或登录密码有误！";

	public static final String EMAIL_EMPTY = "email不能为空！";

	public static final String EMAIL_TOO_LONG = "email长度超过指定长度！";

	public static final String USERTITLE_TOO_LONG = "头像长度超过指定长度！";

	public static final String IDIOGRAPH_TOO_LONG = "修改签名长度太长！";

	public static final String TITLE_EMPTY = "标题不能为空！";

	public static final String TITLE_TOO_LONG = "标题长度超过指定长度！";

	public static final String CONTENT_EMPTY = "内容不能为空！";

	public static final String CONTENT_TOO_LONG = "内容长度不能大于20000个字！";

	public static final String OUT_OF_POWER = "你的体力不足！";

	public static final String PARAMETER_ERROR = "参数错误！";

	public static final String CANNOT_SETTOP = "该帖子是由管理员或斑竹置顶的";

	public static final String OUT_OF_STORAGE = "物品数量不足！请到道具中心购买";

	public static final String VALID_NUMBER_ONE = "请输入大于0的数字！";

	public static final String USER_NOT_FOUND = "用户没有找到！";

	public static final String CANNOT_TO_SELF = "错误操作：不能对自己进行该操作！";

	public static final String FREE_MARKET_SELL_PRICE_INVALIDATION = "售价不符合规定，请重新输入！";

	public static final String MESSAGE_SESSION_IS_INVALIDATED = "检查到你的cookie有问题，请退出登录，关闭所有浏览器窗口再试。";

	public static final String VALID_CODE_INVALID = "验证码输入错误！";

	public static final String USERNAME_PASSWORD_INVALID = "用户名密码错误！";

	public static final String USER_PROHIBIT = "你应该被禁止登入！";

	public static final String USER_UNPAST = "你应该被禁止登入！";

	public static final String USER_ISNEWCOME_NOT_CREATE = "新注册用户一天过后才能发帖！跟帖！请你先逛逛论坛，熟悉一下论坛！";

	public static final String OLD_PASSWORD_INVALID = "原密码错误！";

	public static final String USERNAME_EXIST = "登录帐号已经存在！";

	public static final String NICK_EXIST = "昵称已经存在！";

	public static final String CANNOT_VIEW_FORUM = "对不起，该论坛属于认证论坛，您还没有取得进入该论坛的认证许可!";

	public static final String CANNOT_CREATE_TOPIC = "你没有创建主题帖的权限！";

	public static final String SAVE_CREATE_TOPIC = "你没有保存主题帖的权限！";

	public static final String OUTOF_SAVE_DRAFTBOX = "你草稿箱已满，请删除一些无用的草稿箱，在使用该功能！";

	public static final String CANNOT_DELETE_TOPIC = "你没有删除该主题帖的权限！";

	public static final String CANNOT_CREATE_REPLY = "你没有回复该主题的权限！";

	public static final String CANNOT_MOVE_TOPIC = "你没有移动该主题帖的权限！";

	public static final String CANNOT_SET_TOPIC_TOP = "你没有置顶该主题的权限！";

	public static final String CANNOT_SET_TOPIC_AREA_TOP = "你没有区置顶该主题的权限！";

	public static final String CANNOT_SET_TOPIC_ALL_TOP = "你没有总置顶该主题的权限！";

	public static final String CANNOT_UNSET_TOPIC_TOP = "你没有取消置顶该主题的权限！";

	public static final String CANNOT_LOCK_TOPIC = "你没有锁定主题的权限！";

	public static final String CANNOT_UNLOCK_TOPIC = "你没有解除锁定该主题的权限！";

	public static final String CANNOT_REPLY_LOCK_TOPIC = "抱歉，该主题已经被锁定，不能再进行回复！";

	public static final String CANNOT_PUB_TOPIC = "你没有推荐该主题的权限！";

	public static final String CANNOT_UNPUB_TOPIC = "你没有推荐该主题的权限！";

	public static final String CANNOT_SET_GOOD_TOPIC = "你没有加精该主题的权限！";

	public static final String CANNOT_RESET_GOOD_TOPIC = "不能重复加精该主题！";

	public static final String CANNOT_UNSET_GOOD_TOPIC = "你没有取消该主题精华的权限！";

	public static final String CANNOT_SET_LIGHT_TOPIC = "你没有加亮该主题的权限！";

	public static final String CANNOT_RESET_LIGHT_TOPIC = "不能重复加亮该主题！";

	public static final String CANNOT_RESET_LOCK_TOPIC = "不能重复锁定该主题！";

	public static final String CANNOT_RESET_TOP_TOPIC = "不能重复置顶该主题！";

	public static final String NOT_TOP_TOPIC = "不是置顶主题！";

	public static final String NOT_LIGHT_TOPIC = "该主题不是加亮主题";

	public static final String NOT_GOOD_TOPIC = "该主题不是精华主题";

	public static final String NOT_UNPUB_TOPIC = "该主题不是推荐主题";

	public static final String NOT_LOCK_TOPIC = "该主题没有被锁定";
	
	public static final String DELETE_TOPIC = "该主题已被删除，不能访问！";

	public static final String CANNOT_UNSET_LIGHT_TOPIC = "你没有取消该主题加亮的权限！";

	public static final String CANNOT_DELETE_REPLY = "你没有删除该回复的权限！";

	public static final String CANNOT_SCREEN_REPLY = "你没有屏蔽该回复的权限！";

	public static final String UPLOAD_FAIL = "上传失败，请检查浏览器设置！";

	public static final String UPLOAD_TYPE_FAIL = "上传失败，请检查上传文件类型是否为允许上传类型！";

	public static final String UPLOAD_NET_FAIL = "由于网络原因上传失败!";

	public static final String TITLE_TOO_SHORT = "标题过短，请输入不少于3个字符！";

	public static final String CONTENT_TOO_SHORT = "内容太少，请输入不少于5个有效字符！";

	public static final String OPERATION_TOO_FAST = "你的操作太快啦";

	public static final String VOTE_OPTIONS_EMPTY = "投票选项不能为空！";

	public static final String OUT_OF_VOTE_OPTIONS_MAX_SIZE = "投票选项不能超过允许的最大数量！";

	public static final String SELECT_OPTIONS = "请选择投票！";

	public static final String UNVOTEABLE = "你不能投票：该主题不允许投票或者你已经投过票了！";

	public static final String UNVOTEABLE_EXP = "你不能投票：你的经验值没达到最低要求哦";

	public static final String CANNOT_MODIFY = "你没有修改该主题/回复的权限！";

	public static final String CANNOT_CREATE_VOTE = "你没有创建投票的权限！";

	public static final String OPEN_ACCOUNTS_MONEY = "怎么都要存点吧,1XDB也好啊！";

	public static final String MONEY_INVALID = "金额数目有误！";

	public static final String DAYS_INVALID = "天数有误！";

	public static final String BANK_NOT_FOUND = "银行倒闭了？";

	public static final String BANK_ACCOUNTS_NOT_FOUND = "您还在银行没开户,请先开户!";

	public static final String BANK_PASSWORD_INVALID = "银行密码错误!";

	public static final String CANNOT_DRAW_FIXED = "你没有权限提取这笔存款！";

	public static final String FIXED_HAS_DRAW = "额哦~~~，这笔存款已被提取！";

	public static final String CANNOT_DEAL_LOAN = "你没有权限处理这笔贷款！";

	public static final String REMIT_NICKS_EMPTY = "收款人昵称不能为空！";

	public static final String SMALLNEWS_TITLE_INVALID = "标题长度要大于1小于20！";

	public static final String SMALLNEWS_CONTENT_TOO_LONG = "内容长度不能大于500个字！！";

	public static final String SMALL_NEWS_NOT_EXIST = "指定小字报不存在或已被删除！";

	public static final String SMALL_NEWS_NOT_TIME = "同一ID，48小时内只能存在一张小字报!";

	public static final String CANNOT_DELETE_SMALL_NEWS = "你没有删除该小字报的权限！";

	public static final String MESSAGE_NICKS_EMPTY = "接收人昵称不能为空！";

	public static final String MESSAGE_TITLE_INVALID = "标题长度要大于1小于20！";

	public static final String MESSAGE_CONTENT_TOO_LONG = "内容长度不能大于500个字！！";

	public static final String BOOK_NAME_EMPTY = "日记本名字不能为空！";

	public static final String BOOK_STYLE_EMPTY = "日记本风格不能为空！";

	public static final String BOOK_VIEWKEY_EMPTY = "阅读密码不应该为空！";

	public static final String DIARY_IS_OWS_ERROR = "该日记不属于你，不可以删除！";

	public static final String MANAGE_ERROR = "您无权管理！";

	public static final String BOOK_DESCRIPTION_EMPTY = "你忘了开篇寄语哦";

	public static final String FACE_SIZE_ERROR = "该图片尺寸不符合要求！";

	public static final String FACE_EXT_ERROR = "头像只允许上传jpg、gif、png类型文件！";

	public static final String FORUM_IS_CLOSE = "现在不是论坛开放时间";

	public static final String NO_HONGBAO = "今天没有红包或者你已经领过了";

	public static final String NEED_EXP_HONGBAO = "经验值要达到200才能领取红包哦，加油灌水吧";

	public static final String TOPICS_PERDAY_LIMIT = "你今天在这个版块中不能发帖了，因为你今天发帖数达到版块上限";

	public static final String NEED_EXP = "你经验值不足";

	public static final String REGISTER_SPECIALS_WORD = "除英文，数字，中文外不能含有特殊字符";

	public static final String ONLY_NUMBER = "学号只能为数字，不能含有非法字符！";

	public static final String CREATE_ITEM_FAIL = "这次你没做出东西来";

	public static final String STUDENTID_EMPTY = "学号不能为空";

	public static final String MIMA_EMPTY = "密码不能为空";

	public static final String USER_UPLOAD_IS_OWS_ERROR = "你没有权限查看其他网友的上传！";
	
}
