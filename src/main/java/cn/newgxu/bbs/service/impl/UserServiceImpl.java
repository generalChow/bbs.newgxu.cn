package cn.newgxu.bbs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.AuthorizationManager;
import cn.newgxu.bbs.common.CheckRemoteLogin;
import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.RegisterWordFilter;
import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.bbs.common.util.UpdateLastWeekExp;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.common.util.ValidationUtil;
import cn.newgxu.bbs.domain.FootBallTeam;
import cn.newgxu.bbs.domain.Honor;
import cn.newgxu.bbs.domain.ManageLog;
import cn.newgxu.bbs.domain.TopThree;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.domain.UserLogonLog;
import cn.newgxu.bbs.domain.group.GroupManager;
import cn.newgxu.bbs.domain.group.UserGroup;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.market.ItemWork;
import cn.newgxu.bbs.domain.message.Message;
import cn.newgxu.bbs.domain.other.Hits;
import cn.newgxu.bbs.domain.user.OnlineUser;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.domain.vote.VoteWebMaster;
import cn.newgxu.bbs.domain.vote.VoteWebMasterRecord;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.cache.BBSCache;
import cn.newgxu.bbs.web.model.OnlineStatus;
import cn.newgxu.bbs.web.model.OnlineUserModel;
import cn.newgxu.bbs.web.model.accounts.LoginModel;
import cn.newgxu.bbs.web.model.accounts.RegisterModel;
import cn.newgxu.bbs.web.model.admin.SelectUserInfoModel;
import cn.newgxu.bbs.web.model.admin.UsersManageModel;
import cn.newgxu.bbs.web.model.admin.WebMasterModel;
import cn.newgxu.bbs.web.model.admin.WebMastersManageModel;
import cn.newgxu.bbs.web.model.market.ItemComplimentAwayDoModel;
import cn.newgxu.bbs.web.model.market.ItemUseDoModel;
import cn.newgxu.bbs.web.model.market.MyItemsModel;
import cn.newgxu.bbs.web.model.user.EidtFaceModel;
import cn.newgxu.bbs.web.model.user.EidtPasswordModel;
import cn.newgxu.bbs.web.model.user.EidtQuestionModel;
import cn.newgxu.bbs.web.model.user.EidtTitleModel;
import cn.newgxu.bbs.web.model.user.EidtUserInfoModel;
import cn.newgxu.bbs.web.model.user.GetUsersModel;
import cn.newgxu.bbs.web.model.user.MyUploadModel;
import cn.newgxu.bbs.web.model.user.SaveCupOfLifeModel;
import cn.newgxu.bbs.web.model.user.UserFavoriteTopic;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class UserServiceImpl implements UserService {

	protected Log log = LogFactory.getLog(getClass());

	public User loginWithoutValidCode(LoginModel model) throws BBSException {
		if (model.getRightCode() != null
				&& model.getRightCode().equalsIgnoreCase("twitter")) {
			User user = null;
			try {
				user = User.getByUsername(model.getUsername());
			} catch (ObjectNotFoundException e) {
			}
			if (user == null || !user.getPassword().equals(model.getPassword()))
				throw new BBSException(
						BBSExceptionMessage.USERNAME_PASSWORD_INVALID);
		} else {
			if (!isValidUser(model.getUsername(), model.getPassword())) {
				throw new BBSException(
						BBSExceptionMessage.USERNAME_PASSWORD_INVALID);
			}
		}

		User user;
		try {
			user = User.getByUsername(model.getUsername());
		} catch (ObjectNotFoundException e) {
			log.error("不可能出错的地方出错...");
			throw new RuntimeException();
		}

		if (user.getAccountStatus() == Constants.ACCOUNT_STATUS_FORBID) {
			throw new BBSException(BBSExceptionMessage.USER_PROHIBIT);
		}

		if (user.getAccountStatus() == Constants.ACCOUNT_STATUS_UNPAST) {
			throw new BBSException(BBSExceptionMessage.USER_UNPAST);
		}

		loginLogic(user);

		UserLogonLog.log(user, new Date(), model.getIp());

		return user;
	}

	public User login(LoginModel model) throws BBSException {
		if (!isValidCode(model.getValidCode(), model.getRightCode())) {
			throw new BBSException(BBSExceptionMessage.VALID_CODE_INVALID);
		}

		return loginWithoutValidCode(model);
	}

	private boolean isValidCode(String validCode, String rightCode) {
		if (log.isDebugEnabled()) {
			log.debug("input=" + validCode + ", right=" + rightCode);
		}
		return Util.equalsValidCode(validCode, rightCode);
	}

	private void loginLogic(User user) {
		user.addLoginTimes();
		user.addPower();
		user.setLastLoginTime(new Date());
//		user.save();
		user.update();
	}

	/**
	 * 检查用户输入的用户名密码是否有效。
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return true 如果有效；false 如果无效。
	 */
	private boolean isValidUser(String username, String password) {
		User user;
		try {
			user = User.getByUsername(username);
		} catch (ObjectNotFoundException e) {
			return false;
		}
		return user.getPassword().equals(Util.hash(password));
	}

	private void registerCheck(String username, String nick)
			throws BBSException {
		checkUsernameExist(username);
		checkNickExist(nick);
	}

	private void checkUsernameExist(String username) throws BBSException {
		try {
			User.getByUsername(username);
			throw new BBSException(BBSExceptionMessage.USERNAME_EXIST);
		} catch (ObjectNotFoundException e) {
			// ignore
		}
	}

	private void checkNickExist(String nick) throws BBSException {
		try {
			User.getByNick(nick);
			throw new BBSException(BBSExceptionMessage.NICK_EXIST);
		} catch (ObjectNotFoundException e) {
			// ignore
		}
	}

	private void checkStudentid(String studentid, String mima)
			throws BBSException {
		if (!CheckRemoteLogin.checkRemoteUser(studentid, mima)) {
			throw new BBSException(BBSExceptionMessage.XUEHAO_INVALID);
		}
	}

	// 原方法有错误，这里重写一个
	/**
	 * 由于图书馆的验证方式有误，这里重写这个方法，图书馆的初试密码是00000
	 * 
	 * @param studentId
	 */
	private void checkIdcode(String studentId, String password, int type)
			throws BBSException {
		try {
			if (!CheckRemoteLogin.checkUser(studentId, password, type)) {
				throw new BBSException("您输入的学号或者密码不正确！");
			}
		} catch (IOException e) {
			throw new BBSException("您输入的学号或者密码不正确！");
		}
	}

	/*
	 * private void checkIdcode(String trueName, String Studentid, String
	 * IDCard) throws BBSException { try { if
	 * (!CheckRemoteLogin.checkUser(Studentid, IDCard, 1)) { throw new
	 * BBSException(BBSExceptionMessage.XUEHAO_NOT_EXIST); } } catch
	 * (IOException e1) { throw new
	 * BBSException(BBSExceptionMessage.XUEHAO_NOT_EXIST); } // Graduate
	 * graduate; // try { // graduate = Graduate.getByXuehao(Studentid); // }
	 * catch (ObjectNotFoundException e) { // throw new
	 * BBSException(BBSExceptionMessage.XUEHAO_NOT_EXIST); // } // if
	 * (!Util.equalsIDCard(IDCard, graduate.getIDCard())) { // throw new
	 * BBSException(BBSExceptionMessage.IDCARD_INVALID); // } }
	 */

	// 老师的数据库没有表，所以这个方法没有被使用
//	private void checkTeacherIDCard(String IDCard, String name)
//			throws BBSException {
//
//		Teacher teacher;
//		try {
//			teacher = Teacher.getByIDCard(IDCard);
//		} catch (ObjectNotFoundException e) {
//			throw new BBSException(BBSExceptionMessage.IDCARD_INVALID);
//		}
//		if (!name.equals(teacher.getName())) {
//			throw new BBSException(BBSExceptionMessage.IDCARD_INVALID);
//		}
//	}

	public User register(RegisterModel model) throws BBSException,
			ValidationException {
		if (!isValidCode(model.getValidCode(), model.getRightCode())) {
			throw new BBSException(BBSExceptionMessage.VALID_CODE_INVALID);
		}
		// -----------------check
		ValidationUtil.username(model.getUsername());
		ValidationUtil.nick(model.getNick());
		ValidationUtil.password(model.getPassword(), model.getPassword());
		ValidationUtil.truename(model.getTrueName());
		ValidationUtil.email(model.getEmail());
		// ValidationUtil.title(model.getTitle());
		// ValidationUtil.idiograph(model.getIdiograph());

		if (RegisterWordFilter.StringFilter(model.getNick())) {
			throw new BBSException(BBSExceptionMessage.REGISTER_SPECIALS_WORD);
		}

		// username不能带有特殊字符
		// since 2012-06-26
		if (RegisterWordFilter.StringFilter(model.getUsername())) {
			throw new BBSException(BBSExceptionMessage.REGISTER_SPECIALS_WORD);
		}

		// -----------------
		User user = new User();
		user.setUsername(model.getUsername());

		// 注意，这里对密码进行加密。
		user.setPassword(Util.hash(model.getPassword()));
		user.setEmail(model.getEmail());
		user.setSex(model.getSex() == 1);
		user.setIdcode(model.getIdcode());
		user.setBirthday(model.getBirthday());
		user.setNick(model.getNick());
		user.setTrueName(model.getTrueName());

		switch (model.getRegType()) {
		case 1: // 本科生
			if (RegisterWordFilter.LoginStringFilter(model.getStudentid())) {
				throw new BBSException(BBSExceptionMessage.ONLY_NUMBER);
			}
			ValidationUtil.checkStudentIdAndMima(model.getStudentid(),
					model.getMima());
			user.setRegisterType(Constants.REG_TYPE_STUDENT);
			user.setAccountStatus(Constants.ACCOUNT_STATUS_NORMAL);
			user.setStudentid(model.getStudentid());
			System.out.println(user.getStudentid());
			checkStudentid(user.getStudentid(), model.getMima());
			break;
		case 2: // 研究生、博士生
			if (RegisterWordFilter.LoginStringFilter(model.getStudentid())) {
				throw new BBSException(BBSExceptionMessage.ONLY_NUMBER);
			}
			user.setRegisterType(Constants.REG_TYPE_DOCTOR);
			user.setAccountStatus(Constants.ACCOUNT_STATUS_NORMAL);
			user.setStudentid(model.getStudentid());
			// checkIdcode(user.getTrueName(), user.getStudentid(),
			// model.getMima()); 根据新方法改变
			checkIdcode(model.getStudentid(), model.getMima(), 1);
			break;
		case 3: // 教师
			user.setRegisterType(Constants.REG_TYPE_TEACHER);
			user.setAccountStatus(Constants.ACCOUNT_STATUS_NORMAL);
			user.setTel(model.getTel());
			user.setUnits(model.getUnits());
//			checkTeacherIDCard(user.getIdcode(), user.getTrueName());   longkai@2012-08-31
			// 教师注册的问题出在这里，数据库没有教师的表
			
			break;
		case 4: // 校外
			user.setRegisterType(Constants.REG_TYPE_OUTER);
			user.setAccountStatus(Constants.ACCOUNT_STATUS_NORMAL);
			// user.setAccountStatus(Constants.ACCOUNT_STATUS_WAIT);
			/**
			 * 添加校外注册过滤----by 余影
			 */
			System.out.println(model.getTrueName().trim());
			if (!RegisterWordFilter.isChinese(model.getTrueName().trim())) {
				throw new BBSException(BBSExceptionMessage.TURENAME_ERROR);
			}
			try {
				if (!CheckRemoteLogin.checkCard(model.getIdcode())) {
					throw new BBSException(BBSExceptionMessage.IDCARD_ERROR);
				}
			} catch (Exception e) {
				throw new BBSException(BBSExceptionMessage.IDCARD_ERROR);
			}
			break;
		case 5:// 行健
			if (RegisterWordFilter.LoginStringFilter(model.getStudentid())) {
				throw new BBSException(BBSExceptionMessage.ONLY_NUMBER);
			}
			user.setRegisterType(Constants.REG_TYPE_DOCTOR);
			user.setAccountStatus(Constants.ACCOUNT_STATUS_NORMAL);
			user.setStudentid(model.getStudentid());
			try {
				if (!CheckRemoteLogin.checkUser(user.getStudentid(),
						model.getMima(), 2)) {
					throw new BBSException(BBSExceptionMessage.XUEHAO_NOT_EXIST);
				}
			} catch (IOException e) {
				throw new BBSException(BBSExceptionMessage.XUEHAO_NOT_EXIST);
			}
			break;
		case 100: // 新生 longkai @2012-07-14
			user.setRegisterType(Constants.REG_TYPE_FRESHMAN);
			user.setAccountStatus(Constants.ACCOUNT_STATUS_NORMAL);
			if (!RegisterWordFilter.isChinese(model.getTrueName().trim())) {
				throw new BBSException(BBSExceptionMessage.TURENAME_ERROR);
			}
			try {
				if (!CheckRemoteLogin.checkCard(model.getIdcode())) {
					throw new BBSException(BBSExceptionMessage.IDCARD_ERROR);
				}
			} catch (Exception e) {
				throw new BBSException(BBSExceptionMessage.IDCARD_ERROR);
			}
			break;
		default:
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

		user.setRegisterTime(new Date());
		user.setGroupTypeId(Constants.DEFAULT_GROUP_TYPE);
		user.setGroupId(Constants.DEFAULT_GROUP_ID);
		user.setMoney(Constants.DEFAULT_MONEY);
		user.setLastLoginTime(new Date());
		user.setCurrentPower(GroupManager.getUserGroup(
				Constants.DEFAULT_GROUP_TYPE, Constants.DEFAULT_GROUP_ID)
				.getMaxPower());
		// user.setExp(0);
		// user.setNumberOfGood(0);
		// user.setNumberOfReply(0);
		// user.setNumberOfTopic(0);
		// user.setGold(0);
		// user.setBadboy(0);
		// user.setLoginTimes(0);
		user.setLoginmt((long) 0);
		user.setIdiograph("");
		user.setFace("");
		user.setTitle("");

		registerCheck(user.getUsername(), user.getNick());

		user.save();
		return user;
	}

	public void reInput(RegisterModel model) throws BBSException {
		if (!isValidCode(model.getValidCode(), model.getRightCode())) {
			throw new BBSException(BBSExceptionMessage.VALID_CODE_INVALID);
		}

		User user = model.getUser();
		user.setIdcode(model.getIdcode());
		user.setEmail(model.getEmail());
		user.setBirthday(model.getBirthday());
		user.setTrueName(model.getTrueName());

		switch (model.getRegType()) {
		case 3: // 教师
			user.setAccountStatus(Constants.ACCOUNT_STATUS_WAIT);
			user.setTel(model.getTel());
			user.setUnits(model.getUnits());
			break;
		case 4: // 校外
			user.setAccountStatus(Constants.ACCOUNT_STATUS_WAIT);
			break;
		default:
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		user.update();
	}

	public void reckonItems(List<UploadItem> uploadItems, Authorization auth)
			throws BBSException {
		User user = AuthorizationManager.getUser(auth);

		if (!acceptableUploadItemsSize(uploadItems)) {
			deleteUploadItems(uploadItems);
			throw new BBSException(BBSExceptionMessage.OUT_OF_ACCEPTABLE_SIZE);
		}
		if (user.canPayFor(totalCost(user, uploadItems))) {
			saveUploadItems(uploadItems, user);
			user.payFor(totalCost(user, uploadItems));
			user.save();
		} else {
			deleteUploadItems(uploadItems);
			throw new BBSException(BBSExceptionMessage.OUT_OF_MONEY);
		}
	}

	public void FaceReckonItems(List<UploadItem> uploadItems, Authorization auth)
			throws BBSException {
		if (!acceptableUploadItemsSize(uploadItems)) {
			deleteUploadItems(uploadItems);
			throw new BBSException(BBSExceptionMessage.OUT_OF_ACCEPTABLE_SIZE);
		}

		User user = AuthorizationManager.getUser(auth);
		// ---------2010-06-02 网管部要求上传头像不要钱-------------
		saveUploadItems(uploadItems, user);

		StringBuffer face = new StringBuffer();
		for (UploadItem item : uploadItems) {
			face.append("<img src='")
					.append(Util.getFaceUriFromStoragePath(item
							.getStoragePath()))
					.append("' style='border:1px;' />");
			user.setFace(face.toString());
		}
		user.save();
	}

	private boolean acceptableUploadItemsSize(List<UploadItem> uploadItems) {
		for (UploadItem item : uploadItems) {
			if (!item.acceptableSize()) {
				return false;
			}
		}
		return true;
	}

	private int totalCost(User user, List<UploadItem> uploadItems) {
		int totalCost = 0;
		if (user.getGroupTypeId() == GroupManager.BASIC_GROUP) {
			for (UploadItem item : uploadItems) {
				totalCost += user.reckonPriceOfUploadItemPrice(item);
			}
		}
		return totalCost;
	}

	private void deleteUploadItems(List<UploadItem> uploadItems) {
		for (UploadItem item : uploadItems) {
			item.deleteFile();
		}
	}

	private void saveUploadItems(List<UploadItem> uploadItems, User user) {
		for (UploadItem item : uploadItems) {
			item.setCost(user.reckonPriceOfUploadItemPrice(item));
			item.setStoragePath(StringUtils.substringBeforeLast(
					item.getStoragePath(), ".tmp"));
			item.setUploader(user);
			item.setUri(Util.getUriFromStoragePath(item.getStoragePath()));
			item.save();
		}
	}

	public void itemUseDo(ItemUseDoModel model) throws BBSException {
		// 对人用道具
		if (model.getNick() != null) {
			User object = getUser(model.getNick());
			try {
				if (log.isDebugEnabled()) {
					log.debug("itemLine id=" + model.getId());
					log.debug("object id=" + object.getId());
				}
				/*
				 * 在执行这前，需要验证是否可以使用 这个道具， 看一下数据库，我们发现只有
				 * item_type_id=4的物品会消耗体力与金钱 那么就对这两个项进行验证
				 */
				ItemLine itemLine = ItemLine.get(model.getId());
				if (itemLine.getItem().getType().getId() == 4) {
					if (model.getUser().getCurrentPower()
							+ itemLine.getItem().getSelfPower() < 0) {
						throw new BBSException("您使用的是需要体力与金钱支持的物品，可是<br />"
								+ BBSExceptionMessage.OUT_OF_POWER);
					}
					if (model.getUser().getMoney()
							+ itemLine.getItem().getSelfMoney() < 0) {
						throw new BBSException("您使用的是需要体力与金钱支持的物品，可是<br />"
								+ BBSExceptionMessage.OUT_OF_MONEY);
					}
				}

				/*
				 * 检查是否是女性用户（用于2012年女生节）
				 */
				if (!model.getUser().isSex()
						&& itemLine.getItem().getId() == 70) {
					throw new BBSException("节日是她们的，我们什么也没有。。。");
				}
				//
				// if (itemLine.get)
				if (itemLine.getItem().getExpand1() != null)
					if (TimerUtils.isOverdue(itemLine.getItem().getExpand1()))
						throw new BBSException("您所使用的物品已经过期啦！");

				// itemLine.getItem().getExpand1()

				model.getUser().useItem(itemLine, object);
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
			}
		}
		// 对帖子用道具
		if (model.getTopicId() != 0) {
			try {
				Topic topic = Topic.get(model.getTopicId());
				// 如果帖子是由管理员或斑竹置顶的话，即在itemWork表中没有记录，topic.getTopType()!=0说明帖子是置顶了的
				if (!ItemWork.hasItemWork(topic) && topic.getTopType() != 0) {
					throw new BBSException(BBSExceptionMessage.CANNOT_SETTOP);
				}
				List<ItemLine> list = ItemLine.getItemLinesByUser(
						model.getUser(), (int) model.getId());
				if (list.size() == 0)
					throw new BBSException(BBSExceptionMessage.OUT_OF_STORAGE);
				model.getUser().useItem(list.get(0), topic);
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.OUT_OF_STORAGE);
			}

		}

	}

	public void myItemsModel(MyItemsModel model) {
		model.getPagination().setRecordSize(
				ItemLine.getNumberOfItemLines(model.getUser()));
		model.setItems(ItemLine.getItemLines(model.getUser(),
				model.getPagination()));
	}

	public void itemComplimentAwayDo(ItemComplimentAwayDoModel model)
			throws BBSException, ObjectNotFoundException {
		User user = model.getUser();
		// 取得所有转给物品的用户
		List<String> nicks = Util.splitNicks(model.getNick());
		// 转物品成功用户
		
		System.out.println(nicks);
		
		List<String> receiveUsers = new LinkedList<String>();
		// 物品数量不足，转物品失败用户
		List<String> unReceiveUsers = new LinkedList<String>();
		// 错误昵称，用户不存在
		List<String> notExistUsers = new LinkedList<String>();
		List<ItemLine> itemLines = null;
		itemLines = ItemLine.getItemLinesByUser(user,
				ItemLine.get(model.getId()).getItem().getId());
		for (int i = 0; i < nicks.size(); i++) {
			String nick = nicks.get(i);
			try {
				User object = User.getByNick(nick);

				if (log.isDebugEnabled()) {
					log.debug("itemLine id=" + model.getId());
					log.debug("object id=" + object.getId());
				}
				canNotToSelf(user, object);
				if (itemLines.size() > i) {
					model.getUser().complimentAwayItem(itemLines.get(i),
							object, model.getWish());
					receiveUsers.add(nick);
				} else {
					unReceiveUsers.add(nick);
				}
			} catch (ObjectNotFoundException e) {
				notExistUsers.add(nick);
			}
		}
		model.setReceiveUsers(receiveUsers);
		model.setUnReceiveUsers(unReceiveUsers);
		model.setNotExistUsers(notExistUsers);

	}
	
	/**
	 * 这个私有方法和上面那个很像，但是这个可以控制具体送那一个
	 * @param model
	 * @throws BBSException
	 * @throws ObjectNotFoundException	
	 * @author longkai
	 * @since 2012-10-21
	 */
	private void sendSystemItems(ItemComplimentAwayDoModel model) throws BBSException, ObjectNotFoundException {
		User user = model.getUser();
		// 取得所有转给物品的用户
		List<String> nicks = Util.splitNicks(model.getNick());
		// 转物品成功用户
		
		System.out.println(nicks);
		
		List<String> receiveUsers = new LinkedList<String>();
		// 物品数量不足，转物品失败用户
		List<String> unReceiveUsers = new LinkedList<String>();
		// 错误昵称，用户不存在
		List<String> notExistUsers = new LinkedList<String>();
		List<ItemLine> itemLines = null;
		itemLines = ItemLine.getItemLinesByUser(user,
				model.getItemId());
		for (int i = 0; i < nicks.size(); i++) {
			String nick = nicks.get(i);
			try {
				User object = User.getByNick(nick);

				canNotToSelf(user, object);
				if (itemLines.size() > i) {
					model.getUser().complimentAwayItem(itemLines.get(i),
							object, model.getWish());
					receiveUsers.add(nick);
				} else {
					unReceiveUsers.add(nick);
				}
			} catch (ObjectNotFoundException e) {
				notExistUsers.add(nick);
			}
		}
		model.setReceiveUsers(receiveUsers);
		model.setUnReceiveUsers(unReceiveUsers);
		model.setNotExistUsers(notExistUsers);
	}

	private void canNotToSelf(User user, User object) throws BBSException {
		if (user.isSelf(object)) {
			throw new BBSException(BBSExceptionMessage.CANNOT_TO_SELF);
		}
	}

	public void cleanOnlineUser() {
		OnlineUser.clean();
	}

	public void signOnlineUser(OnlineUserModel model) {
		if (OnlineUser.getNumberByIp(model.getIp()) > 300) {
			throw new RuntimeException();
		}
		OnlineUser.sign(model.getUserId(), model.getLastAliveTime(),
				model.getForumId(), model.getOs(), model.getIp(),
				model.getLocation(), model.getSessionid());
	}

	public void onlineStatus(OnlineStatus model) {
		model.setTotal(OnlineUser.getTotal());
		if (model.getForumId() > 0) {
			model.setNumberOfForum(OnlineUser.getNumberOfForum(model
					.getForumId()));
		}
	}

	public void deleteOnlineUser(Authorization auth) {
		if (auth != null) {
			try {
				OnlineUser.getByUserId(auth.getId()).delete();
			} catch (ObjectNotFoundException e) {
				// ignore
			}
		}
	}

	public void editUserInfo(EidtUserInfoModel model) throws BBSException {
		User user = model.getUser();
		user.setSex(model.getSex() == 1);
		user.setBirthday(model.getBirthday());
		user.setEmail(model.getEmail());
		user.setHomepage(model.getHomepage());
		user.setQq(model.getQq());
		user.setTel(model.getTel());
		user.setIdiograph(model.getIdiograph());
		user.save();
	}

	/**
	 * 修改是否接收短信
	 * 
	 * @param model
	 * @param type
	 * @return
	 */
	public boolean editReplyMessage(EidtUserInfoModel model, Integer type) {
		try {
			User user = model.getUser();
			user.setReplyMessage(type);
			user.save();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void editFace(EidtFaceModel model) throws BBSException {
		User user = model.getUser();
		user.setFace(model.getFace());
		user.save();
	}

	public void editPassword(EidtPasswordModel model) throws BBSException {
		User user = model.getUser();
		if (!user.getPassword().equals(Util.hash(model.getOldPassword()))) {
			throw new BBSException(BBSExceptionMessage.OLD_PASSWORD_INVALID);
		}
		user.setPassword(Util.hash(model.getNewPassword()));
		user.save();
	}

	public void resetPassword(EidtPasswordModel model) {
		User user = model.getUser();
		user.setPassword(Util.hash("123456"));
		user.save();
	}

	public void editQuestion(EidtQuestionModel model) throws BBSException {
		User user = model.getUser();
		user.setQuestion(model.getQuestion());
		user.setAnswer(StringUtils.isEmpty(model.getQuestion()) ? "" : Util
				.hash(model.getAnswer()));
		user.save();
	}

	public void editTitle(EidtTitleModel model) throws BBSException {
		User user = model.getUser();
		// 扣XDB
		if (!user.getUserGroup().isEditTitleFree()) {
			if (!user.canPayFor(1000)) {
				throw new BBSException(BBSExceptionMessage.OUT_OF_MONEY);
			}
			user.payFor(1000);// 扣XDB
		}
		user.setTitle((model.getTitle()));
		user.save();
	}

	public User getUser(int id) throws BBSException {
		try {
			return User.get(id);
		} catch (ObjectNotFoundException e) {
			log.debug("没有找到用户，id：" + id);
			throw new BBSException(BBSExceptionMessage.USER_NOT_FOUND);
		}
	}

	// 修改honor add daodaoyu
	public Honor getHonor(int id) throws BBSException {
		try {
			return Honor.getHonorById(id);
		} catch (ObjectNotFoundException e) {
			log.debug("没有找到用户，id：" + id);
			throw new BBSException(BBSExceptionMessage.USER_NOT_FOUND);
		}
	}

	public User getUser(String nick) throws BBSException {
		try {
			return User.getByNick(nick);
		} catch (ObjectNotFoundException e) {
			log.debug("没有找到用户，昵称：" + nick);
			throw new BBSException(BBSExceptionMessage.USER_NOT_FOUND);
		}
	}

	public List<OnlineUser> getOnlineUsers(int type, Pagination pagination) {
		return OnlineUser.getOnlineUsers(pagination);
	}

	public List<OnlineUser> getOnlineForumUsers(int type, int forum_id,
			Pagination pagination) {
		return OnlineUser.getOnlineForumUsers(forum_id, pagination);
	}

	public List<UserGroup> getGroups(int groupTypeId) {
		return GroupManager.getGroups(groupTypeId);
	}

	public void getUsers(GetUsersModel model) {
	}

	public List<User> getUsers(int type, Pagination pagination) {
		boolean index = pagination != null;
		if (pagination == null) {
			// 十大排行榜
			pagination = new Pagination();
			pagination.setPage(1);
			pagination.setPageSize(10);
		} else {
			pagination.setPageSize(20);
			pagination.setRecordSize(User.getNumberOfUsers());
		}
		switch (type) {
		case 1: // 按经验排序
			if (index)
				return User.getUsersOrderByExp(pagination);
			else
				return BBSCache.getExpUserCache();
		case 2: // 按西大币排序
			if (index)
				return User.getUsersOrderByMoney(pagination);
			return BBSCache.getMoneyUserCache();
		case 3: // 按发表主题数排序
			if (index)
				return User.getUsersOrderByTopic(pagination);
			return BBSCache.getTopicUserCache();
		case 4: // 按发表回复数排序
			if (index)
				return User.getUsersOrderByReply(pagination);
			return BBSCache.getReplyUserCache();
		case 5: // 按发表精华数排序
			if (index)
				return User.getUsersOrderByGood(pagination);
			return BBSCache.getGoodUserCache();
		case 6:
			if (index)
				return User.getLastWeekMostActiveUsers(pagination);
			return User.getLastWeekMostActiveUsers(pagination);
//			return BBSCache.getExpUserCache();
		default: // 按加入时间排序
			return User.getUsersOrderByTime(pagination);
		}
	}

	public void getUsers(UsersManageModel model) {
		model.setUsers(User.getUsers(model.getPagination()));
	}

	public void getNormalUsers(UsersManageModel model) {
		model.setUsers(User.getUsersByAccoutStatusType(
				Constants.ACCOUNT_STATUS_NORMAL, model.getPagination()));
	}

	public void getWaitForApproachUsers(UsersManageModel model) {
		model.setUsers(User.getUsersByAccoutStatusType(
				Constants.ACCOUNT_STATUS_WAIT, model.getPagination()));
	}

	public void getLastRegisterUsers(UsersManageModel model) {
		model.setUsers(User.getLastRegisterUsers(model.getStart(),
				model.getEnd(), model.getPagination()));
	}

	public void getRegisterUsersToday(UsersManageModel model) {
		model.setUsers(User.getRegisterUsersToday(model.getPagination()));
	}

	public void editUser(UsersManageModel model) throws BBSException,
			ValidationException {
		User user = getUser(model.getId());
		user.setAccountStatus(model.getAccountStatus());
		// user.setAnswer(model.getAnswer());
		// user.setBadboy(model.getBadboy());
		user.setBirthday(model.getBirthday());
		user.setCurrentPower(model.getCurrentPower());
		user.setEmail(model.getEmail());
		user.setExp(model.getExp());
		user.setFace(model.getFace());
		// user.setGold(model.getGold());
		user.setGroupId(model.getGroupId());
		user.setGroupTypeId(model.getGroupTypeId());
		user.setHomepage(model.getHomepage());
		user.setHonor(model.getHonor());
		user.setIdcode(model.getIdcode());
		user.setIdiograph(model.getIdiograph());
		user.setLastLoginTime(model.getLastLoginTime());
		user.setLoginTimes(model.getLoginTimes());
		user.setLoginmt(model.getLoginmt());
		user.setMoney(model.getMoney());
		user.setNick(model.getNick());
		user.setNumberOfGood(model.getNumberOfGood());
		user.setNumberOfReply(model.getNumberOfReply());
		user.setNumberOfTopic(model.getNumberOfTopic());
		if (!user.getPassword().equals(model.getPassword())) {
			user.setPassword(Util.hash(model.getPassword()));
		}
		user.setQq(model.getQq());
		// user.setQuestion(model.getQuestion());
		user.setRegisterTime(model.getRegisterTime());
		// user.setRegisterType(model.getRegisterType());
		// user.setRemark(model.getRemark());
		// user.setStudentid(model.getStudentid());
		user.setTel(model.getTel());
		user.setTitle(model.getTitle());
		user.setTrueName(model.getTrueName());
		// user.setUnits(model.getUnits());
		// user.setUsername(model.getUsername());
		// user.setConfrere(model.isConfrere());
		user.setSex(model.isSex());
		// 修改honor modify daodaoyu
		List<Honor> honors = new LinkedList<Honor>();
		if (model.getHonor() != null) {
			String[] ids = model.getHonor().split(", ");
			if (ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					System.out.println(ids[i]);
					honors.add(getHonor(Integer.parseInt(ids[i])));
				}
			}
			user.setHonors(honors);
		}
		if (model.getHonor() == null) {
			user.setHonors(null);
		}
		user.save();
	}

	public void searchUsers(GetUsersModel model) throws Exception {
		List<User> users = null;
		if (model.getType() == 0) {
			int[] ids = { Integer.parseInt(model.getKeywords()) };
			users = User.getUsers(ids, model.getPagination());
		} else if (model.getType() == 1) {
			users = User.getUsersByNick(model.getKeywords(),
					model.getPagination());
		} else if (model.getType() == 2) {
			users = User.getUsersByUsername(model.getKeywords(),
					model.getPagination());
		} else if (model.getType() == 3) {
			users = User.getUsersByStudentid(model.getKeywords(),
					model.getPagination());
		} else {
			throw new BBSException();
		}
		model.setUsers(users);
	}

	public void getWebMasters(WebMastersManageModel model) {
		List<User> users = null;
		String searchType = model.getType();
		if (searchType == null)
			users = User
					.getUsersByGroupType(GroupManager.FORUM_WEBMASTER_GROUP);
		else if (searchType.equals("exp"))
			users = User
					.getUsersByGroupTypeSortByExp(GroupManager.FORUM_WEBMASTER_GROUP);
		else if (searchType.equals("money"))
			users = User
					.getUsersByGroupTypeSortByMoney(GroupManager.FORUM_WEBMASTER_GROUP);
		else if (searchType.equals("topics"))
			users = User
					.getUsersByGroupTypeSortByTopics(GroupManager.FORUM_WEBMASTER_GROUP);
		else if (searchType.equals("replys"))
			users = User
					.getUsersByGroupTypeSortByReplys(GroupManager.FORUM_WEBMASTER_GROUP);
		else if (searchType.equals("goods"))
			users = User
					.getUsersByGroupTypeSortBygoods(GroupManager.FORUM_WEBMASTER_GROUP);
		List<WebMasterModel> list = new ArrayList<WebMasterModel>();
		for (int i = 0; i < users.size(); i++) {
			WebMasterModel webmaster = new WebMasterModel();
			webmaster.setUser(users.get(i));
			// webmaster.setNumberOfGood(User.getNumberOfGoods(users.get(i),model.getSpan()));
			// webmaster.setNumberOfTopic(User.getNumberOfTopics(users.get(i),model.getSpan()));
			// webmaster.setNumberOfReply(User.getNumberOfReplys(users.get(i),model.getSpan()));
			webmaster.setNumberOfManage(ManageLog.getLogNumberOfUser(users
					.get(i)));
			list.add(webmaster);
		}
		model.setWebmasters(list);
	}

	public int getMessageSizeNotRead(User user) {
		return Message.getMessageSizeNotRead(user);
	}

	public void saveFavoriteTopic(UserFavoriteTopic model) {
		User user = model.getUser();
		List<Topic> favoriteTopics = user.getFavoriteTopics();
		Topic topic = null;
		try {
			topic = Topic.get(model.getTopicId());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			return;
		}
		if (favoriteTopics.contains(topic)) {
			return;
		}
		favoriteTopics.add(topic);
		user.setFavoriteTopics(favoriteTopics);
		user.save();
	}

	public void delFavoriteTopic(UserFavoriteTopic model) {
		User user = model.getUser();
		List<Topic> favoriteTopics = user.getFavoriteTopics();
		Topic topic = new Topic();
		try {
			topic = Topic.get(model.getTopicId());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		favoriteTopics.remove(topic);
		user.setFavoriteTopics(favoriteTopics);
		user.save();
	}

	public void getFavoriteTopics(UserFavoriteTopic model) {
		int pageSize = 15;
		Pagination p = model.getPagination();
		User user = model.getUser();
		List<Topic> list = user.getFavoriteTopics();
		p.setPageSize(pageSize);
		p.setRecordSize(list.size());
		int start = (p.getPage() - 1) * pageSize;
		int end = p.getPage() * pageSize > list.size() ? list.size() : p
				.getPage() * pageSize;
		model.setList(list.subList(start, end));
	}

	public void getUser(UsersManageModel model) {
		try {
			User user = getUser(model.getId());
			model.setExp(user.getExp());
			model.setEmail(user.getEmail());
			model.setGold(user.getGold());
			model.setMoney(user.getMoney());
			model.setRemark(user.getRemark());
			model.setSex(user.isSex());
			model.setNick(user.getNick());
			model.setUsername(user.getUsername());
			model.setRegisterTime(user.getRegisterTime());
			model.setTrueName(user.getTrueName());
			model.setIdcode(user.getIdcode());
			model.setAccountStatus(user.getAccountStatus());
			model.setRegisterType(user.getRegisterType());
		} catch (BBSException e) {
			e.printStackTrace();
		}
	}

	public void verifyUser(UsersManageModel model) {
		try {
			User user = getUser(model.getId());
			user.setAccountStatus(model.getAccountStatus());
			user.setRemark(model.getRemark());
			user.save();
		} catch (BBSException e) {
			e.printStackTrace();
		}
	}

	public boolean isNickNameInUser(String nick) {
		try {
			System.out.println("====" + nick + "===");
			User.getByNick(nick);
			return true;
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isUserNameInUser(String userName) {
		try {
			System.out.println("====" + userName + "===");
			User.getByUsername(userName);
			return true;
		} catch (ObjectNotFoundException e) {
			return false;
		}
	}

	public void updateHonors() {
		for (int i = 1; i <= 5; i++) {
			updateHonorByType(i);
		}
	}

	private void updateHonorByType(int i) {
		List<User> tops = getUsers(i, null);
		Honor honor = null;
		try {
			honor = Honor.getHonorById(i);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		honor.setUsers(null);
		honor.setUsers(tops);
	}

	public void freshWebMasters(WebMastersManageModel model) {
		// 清除现在的候选列表
		List<VoteWebMaster> voteWebMasters = VoteWebMaster.getWebMaster();
		if (voteWebMasters.size() > 0) {
			for (int i = 0; i < voteWebMasters.size(); i++) {
				voteWebMasters.get(i).delete();
			}
			resetVote();
		}
		// 更换成现任斑竹列表，票数初始化为0
		List<WebMasterModel> list = new ArrayList<WebMasterModel>();
		List<User> webMasters = User
				.getUsersByGroupType(GroupManager.FORUM_WEBMASTER_GROUP);
		for (int i = 0; i < webMasters.size(); i++) {
			User user = webMasters.get(i);
			VoteWebMaster voteWebMaster = new VoteWebMaster();
			voteWebMaster.setScore(0);
			voteWebMaster.setUser(user);
			voteWebMaster.setNick(user.getNick());
			voteWebMaster.setForum_name(user.getManagingForums().get(0)
					.getName());
			voteWebMaster.save();

			// WebMasterModel webMasterModel = new WebMasterModel();
			// webMasterModel.setId(webMasters.get(i).getId());
			// webMasterModel.setUserid(user.getId());
			// webMasterModel.setNick(user.getNick());
			// webMasterModel.setForum_name(user.getManagingForums().get(0).getName());
			// list.add(webMasterModel);
		}
		model.setWebmasters(list);
	}

	public void getVoteWebMasters(WebMastersManageModel model) throws Exception {
		try {
			if (VoteWebMasterRecord.contain(model.getUser())) {
				model.setVoteTarget(true);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			model.setVoteTarget(false);
		}
		List<WebMasterModel> list = new ArrayList<WebMasterModel>();
		List<VoteWebMaster> webMasters = VoteWebMaster.getWebMaster();
		for (int i = 0; i < webMasters.size(); i++) {
			WebMasterModel webMasterModel = new WebMasterModel();
			webMasterModel.setUser(webMasters.get(i).getUser());
			webMasterModel.setId(webMasters.get(i).getId());
			webMasterModel.setUserid(webMasters.get(i).getId());
			webMasterModel.setNick(webMasters.get(i).getNick());
			webMasterModel.setForum_name(webMasters.get(i).getForum_name());
			webMasterModel.setScore(webMasters.get(i).getScore());
			list.add(webMasterModel);
		}
		model.setWebmasters(list);
		try {
			model.setTotalNum(VoteWebMasterRecord.getNumberOfUser());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void resetVote() {
		// 投票记录清零
		List<VoteWebMasterRecord> list = VoteWebMasterRecord.getRecords();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).delete();
			}
			// 票数清零
			List<VoteWebMaster> webMasters = VoteWebMaster.getWebMaster();
			for (int i = 0; i < webMasters.size(); i++) {
				webMasters.get(i).setScore(0);
			}
		}
	}

	public void deleteVoteWebMaster(WebMastersManageModel model) {
		try {
			VoteWebMaster.getById(model.getId()).delete();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void addVoteWebMaster(WebMastersManageModel model) {
		User user = null;
		try {
			user = User.getByNick(model.getUser().getNick());
			VoteWebMaster voteWebMaster = new VoteWebMaster();
			voteWebMaster.setUser(user);
			voteWebMaster.setNick(model.getUser().getNick());
			voteWebMaster.setForum_name(model.getForum_name());
			voteWebMaster.setScore(0);
			voteWebMaster.save();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void VoteWebMasterDo(WebMastersManageModel model) throws Exception {
		if (model.getUser().getExp() < 200) {
			throw new BBSException(BBSExceptionMessage.UNVOTEABLE_EXP);
		}
		if (VoteWebMasterRecord.contain(model.getUser())) {
			throw new BBSException(BBSExceptionMessage.UNVOTEABLE);
		}
		int[] options = model.getOptions();
		for (int i = 0; i < options.length; i++) {
			VoteWebMaster voteWebMaster;
			voteWebMaster = VoteWebMaster.getByUserId(options[i]);
			voteWebMaster.setScore(voteWebMaster.getScore() + 1);
			voteWebMaster.update();
			// 斑竹每得一票奖200xdb
			voteWebMaster.getUser().addMoney(200);
		}
		VoteWebMasterRecord record = new VoteWebMasterRecord();
		record.setUser(model.getUser());
		record.save();
		// 每投一票奖200xdb
		model.getUser().addMoney(200);
		model.setTotalNum(VoteWebMasterRecord.getNumberOfUser());
	}

	public void getCupOfLife(SaveCupOfLifeModel model) throws Exception {
		model.setFootBallTeams(FootBallTeam.getFootBallTeams());
	}

	public void saveCupOfLife(SaveCupOfLifeModel model) throws BBSException,
			ObjectNotFoundException {
		FootBallTeam footballteam = new FootBallTeam();
		List<FootBallTeam> fbts = new LinkedList<FootBallTeam>();
		User user = model.getUser();
		FootBallTeam.deleteFootBallTeam(user);
		if (model.getId() > 0) {
			footballteam = FootBallTeam.getFootBallTeamById(model.getId());
			fbts.add(footballteam);
			user.setFootBallTeams(fbts);
			user.save();
		}
	}

	public void myUpload(MyUploadModel model) throws BBSException {
		System.out.println("aaaaaaaaaaaaaaaaaaaaa111111111111111111");
		User user = getUser(model.getId());
		if (model.getUser() == null) {
			throw new BBSException(BBSExceptionMessage.NOT_LOGIN);
		}
		if (!model.getUser().equals(user)) {
			throw new BBSException(BBSExceptionMessage.USER_UPLOAD_IS_OWS_ERROR);
		}
		model.setUploadItems(UploadItem.getMyUploadItem(model.getUser(),
				model.getPagination()));
	}

	public void getUploadItems(MyUploadModel model) throws BBSException {
		model.setUploadItems(UploadItem.getUploadItems(model.getPagination()));
	}

	public void searchUserUploadItems(MyUploadModel model) throws BBSException {
		User user = getUser(model.getNick());
		model.setUploadItems(UploadItem.getMyUploadItem(user,
				model.getPagination()));
	}

	public boolean deleteUploadItems(MyUploadModel model) {
		try {
			UploadItem uploadItem = UploadItem.get(model.getId());
			uploadItem.deleteFile();
			uploadItem.delete();
			return true;
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void selectUserLoginInfo(SelectUserInfoModel model) throws Exception {

		if (model.getStartTime() == null || model.getStartTime().equals("")) {
			model.setStartTime(TimerUtils.getDateByFormat(
					Util.getDateAfterDay(-30), "yyyy-MM-dd"));
			model.setEndTime(TimerUtils.getNowYMD("yyyy-MM-dd"));
		}
		if (model.getType() == 0) {
			model.setUser(User.getUsersByGroupType(model.getGroupTypeId(),
					model.getPagination()));
		} else if (model.getType() == 1) {
			model.getUser().add(User.getByUsername(model.getKeyWord()));
		} else if (model.getType() == 2) {
			model.getUser().add(User.getByNick(model.getKeyWord()));
		}
		Date start = TimerUtils.getDate(model.getStartTime(), "yyyy-MM-dd");
		Date end = TimerUtils.getDate(model.getEndTime(), "yyyy-MM-dd");
		for (User u : model.getUser()) {
			u.setNumberOfReply(Topic.getTopicsSizeByReplyUser(u, start, end));
			u.setNumberOfTopic(Topic.getTopicsSize(u, start, end));
			u.setLoginTimes(UserLogonLog.getLoginCountByUser(u, start, end));
		}
	}

	/**
	 * 用于每周更新最活跃榜单。
	 * @author longkai
	 * @since 2012-09-22
	 */
	public void updateLastWeekExp() {
		UpdateLastWeekExp.updateLastWeekExp(Calendar.getInstance());
//		应网管部要求，暂时取消榜单的自动信息发送 @2012-12-19
//		String nicks = UpdateLastWeekExp.updateLastWeekExp(Calendar.getInstance());
//		if (nicks != null) {
//			ItemComplimentAwayDoModel model = new ItemComplimentAwayDoModel();
//			model.setItemId(8); // 8是加苦王老吉。。。
//			try {
//				model.setUser(User.get(4980)); // 4980是管理员01的user_id...
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			model.setNick(nicks);
//			model.setWish("恭喜您成为本周新星榜前十位，系统奖励您一份神秘礼物~，希望您再接再厉！^_^");
//			try {
//				sendSystemItems(model);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	public List<TopThree> getTopThree(int type) {
		return TopThree.getTopThrees(type, new Pagination());
	}

	public void addHits(boolean isLogin) {
		Hits.addHits(isLogin);
	}

	/*
	 * public static void main(String[] args) { Pattern pattern =
	 * Pattern.compile("([a-zA-Z0-9_])*"); Matcher matcher =
	 * pattern.matcher("fsdfdsfds_"); if (matcher.matches()) {
	 * System.out.println(1); } else { System.out.println(0); } }
	 */
}
