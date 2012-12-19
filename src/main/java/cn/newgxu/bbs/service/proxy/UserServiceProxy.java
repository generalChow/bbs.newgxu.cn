package cn.newgxu.bbs.service.proxy;

import java.util.List;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.ValidationUtil;
import cn.newgxu.bbs.domain.TopThree;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.domain.group.UserGroup;
import cn.newgxu.bbs.domain.user.OnlineUser;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.bbs.web.model.OnlineStatus;
import cn.newgxu.bbs.web.model.OnlineUserModel;
import cn.newgxu.bbs.web.model.accounts.LoginModel;
import cn.newgxu.bbs.web.model.accounts.RegisterModel;
import cn.newgxu.bbs.web.model.admin.SelectUserInfoModel;
import cn.newgxu.bbs.web.model.admin.UsersManageModel;
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
public class UserServiceProxy implements UserService {

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User login(LoginModel model) throws BBSException {
		return userService.login(model);
	}

	public void reckonItems(List<UploadItem> uploadItems, Authorization auth)
			throws BBSException {
		userService.reckonItems(uploadItems, auth);
	}

	public User register(RegisterModel model) throws BBSException, ValidationException {
		return userService.register(model);
	}

	public void reInput(RegisterModel model) throws BBSException, ValidationException {
		userService.reInput(model);
	}

	public void itemUseDo(ItemUseDoModel model) throws BBSException {
		this.userService.itemUseDo(model);
	}

	public void myItemsModel(MyItemsModel model) {
		this.userService.myItemsModel(model);
	}

	public void itemComplimentAwayDo(ItemComplimentAwayDoModel model)
			throws BBSException,ObjectNotFoundException{
		this.userService.itemComplimentAwayDo(model);
	}

	public User loginWithoutValidCode(LoginModel model) throws BBSException {
		return userService.loginWithoutValidCode(model);
	}

	public void cleanOnlineUser() {
		userService.cleanOnlineUser();
	}

	public void signOnlineUser(OnlineUserModel model) {
		userService.signOnlineUser(model);
	}

	public void onlineStatus(OnlineStatus model) {
		userService.onlineStatus(model);
	}

	public void deleteOnlineUser(Authorization auth) {
		userService.deleteOnlineUser(auth);
	}

	public void editUserInfo(EidtUserInfoModel model) throws BBSException, ValidationException {
		ValidationUtil.idiograph(model.getIdiograph());
		userService.editUserInfo(model);
	}

	public void editFace(EidtFaceModel model) throws BBSException, ValidationException {
		userService.editFace(model);
	}

	public void editPassword(EidtPasswordModel model) throws BBSException, ValidationException {
		ValidationUtil.password(model.getNewPassword(), model.getConfirmPassword());
		userService.editPassword(model);
	}
	
	public void resetPassword(EidtPasswordModel model){
		userService.resetPassword(model);
	}
	
	public void editQuestion(EidtQuestionModel model) throws BBSException, ValidationException {
		userService.editQuestion(model);
	}

	public void editTitle(EidtTitleModel model) throws BBSException, ValidationException {
		ValidationUtil.userTitle(model.getTitle());
		userService.editTitle(model);
	}

	public List<User> getUsers(int type, Pagination pagination) {
		return userService.getUsers(type, pagination);
	}

	public List<OnlineUser> getOnlineUsers(int type, Pagination pagination) {
		return userService.getOnlineUsers(type, pagination);
	}

	public void getUsers(UsersManageModel model) {
		userService.getUsers(model);
	}

	public List<UserGroup> getGroups(int groupTypeId) {
		return userService.getGroups(groupTypeId);
	}

	public void FaceReckonItems(List<UploadItem> uploadItems, Authorization auth) throws BBSException {
		userService.FaceReckonItems(uploadItems, auth);
		
	}

	public void editUser(UsersManageModel model) throws BBSException, ValidationException {
		userService.editUser(model);
	}

	public User getUser(int id) throws BBSException {
		return userService.getUser(id);
	}

	public User getUser(String nick) throws BBSException {
		return userService.getUser(nick);
	}

	public void searchUsers(GetUsersModel model) throws Exception {
		userService.searchUsers(model);
	}

	public void getWebMasters(WebMastersManageModel model) {
		userService.getWebMasters(model);
	}

	public int getMessageSizeNotRead(User user) {
		return userService.getMessageSizeNotRead(user);
	}

	public void getNormalUsers(UsersManageModel model) {
		userService.getNormalUsers(model);
	}

	public void getWaitForApproachUsers(UsersManageModel model) {
		userService.getWaitForApproachUsers(model);
	}

	public void saveFavoriteTopic(UserFavoriteTopic model) {
		userService.saveFavoriteTopic(model);
	}

	public void getFavoriteTopics(UserFavoriteTopic model) {
		userService.getFavoriteTopics(model);
	}

	public void delFavoriteTopic(UserFavoriteTopic model) {
		userService.delFavoriteTopic(model);
	}

	public void getUser(UsersManageModel model) {
		this.userService.getUser(model);
	}

	public void verifyUser(UsersManageModel model) {
		userService.verifyUser(model);
	}

	public boolean isNickNameInUser(String nick) {
		return userService.isNickNameInUser(nick);
	}

	public boolean isUserNameInUser(String userName) {
		return userService.isUserNameInUser(userName);
	}

	public void updateHonors() {
		userService.updateHonors();
	}

	public void getLastRegisterUsers(UsersManageModel model) {
		userService.getLastRegisterUsers(model);
	}

	public void getRegisterUsersToday(UsersManageModel model) {
		userService.getRegisterUsersToday(model);		
	}

	public void freshWebMasters(WebMastersManageModel model) {
		userService.freshWebMasters(model);
	}

	public void getVoteWebMasters(WebMastersManageModel model) throws Exception {
		userService.getVoteWebMasters(model);
	}

	public void resetVote() {
		userService.resetVote();
	}

	public void deleteVoteWebMaster(WebMastersManageModel model) {
		userService.deleteVoteWebMaster(model);
	}

	public void addVoteWebMaster(WebMastersManageModel model) {
		userService.addVoteWebMaster(model);
	}

	public void VoteWebMasterDo(WebMastersManageModel model) throws Exception {
		userService.VoteWebMasterDo(model);
	}

	public void getCupOfLife(SaveCupOfLifeModel model) throws Exception {
		userService.getCupOfLife(model);		
	}

	public void saveCupOfLife(SaveCupOfLifeModel model) throws BBSException,
			ObjectNotFoundException {
		userService.saveCupOfLife(model);		
	}

	public List<OnlineUser> getOnlineForumUsers(int type, int forum_id,
			Pagination pagination) {
		return userService.getOnlineForumUsers(type, forum_id, pagination);
	}

	public void myUpload(MyUploadModel model)throws BBSException{
		userService.myUpload(model);
	}
	
	public void getUploadItems(MyUploadModel model)throws BBSException{
		userService.getUploadItems(model);
	}
	
	public void searchUserUploadItems(MyUploadModel model)throws BBSException{
		userService.searchUserUploadItems(model);
	}
	
	public boolean deleteUploadItems(MyUploadModel model){
		return userService.deleteUploadItems(model);
	}

	public void selectUserLoginInfo(SelectUserInfoModel model) throws Exception {
		userService.selectUserLoginInfo(model);
	}

	public boolean editReplyMessage(EidtUserInfoModel model, Integer type) {
		return userService.editReplyMessage(model, type);
	}

	public void updateLastWeekExp() {
		userService.updateLastWeekExp();
	}

	public List<TopThree> getTopThree(int type) {
		return userService.getTopThree(type);
	}

	public void addHits(boolean isLogin) {
		userService.addHits(isLogin);
	}
}
