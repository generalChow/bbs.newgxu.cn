package cn.newgxu.bbs.service;

import java.util.List;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.domain.TopThree;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.domain.group.UserGroup;
import cn.newgxu.bbs.domain.user.OnlineUser;
import cn.newgxu.bbs.domain.user.User;
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
public interface UserService {

	public User register(RegisterModel model) throws BBSException, ValidationException;

	public User login(LoginModel model) throws BBSException;

	public User loginWithoutValidCode(LoginModel model) throws BBSException;

	public void reckonItems(List<UploadItem> uploadItems, Authorization auth)
			throws BBSException;

	public void FaceReckonItems(List<UploadItem> uploadItems, Authorization auth)
            throws BBSException;

	public void itemUseDo(ItemUseDoModel model) throws BBSException;

	public void myItemsModel(MyItemsModel model);

	public void itemComplimentAwayDo(ItemComplimentAwayDoModel model)
			throws BBSException, ObjectNotFoundException;
	
	public void signOnlineUser(OnlineUserModel model);
	
	public void cleanOnlineUser();
	
	public void onlineStatus(OnlineStatus model);
	
	public void deleteOnlineUser(Authorization authorization);

	public void editUserInfo(EidtUserInfoModel model) throws BBSException, ValidationException;

	public void editFace(EidtFaceModel model) throws BBSException, ValidationException;

	public void editPassword(EidtPasswordModel model) throws BBSException, ValidationException;
	
	public void resetPassword(EidtPasswordModel model);

	public void editQuestion(EidtQuestionModel model) throws BBSException, ValidationException;

	public void editTitle(EidtTitleModel model) throws BBSException, ValidationException;

	public List<User> getUsers(int type, Pagination pagination);

	public User getUser(int id) throws BBSException;

	public User getUser(String nick) throws BBSException;

	public List<OnlineUser> getOnlineUsers(int type, Pagination pagination);
	
	public List<OnlineUser> getOnlineForumUsers(int type , int forum_id, Pagination pagination);

	public void reInput(RegisterModel model) throws BBSException, ValidationException;

	public void getUsers(UsersManageModel model);
	
	public void getNormalUsers(UsersManageModel model);
	
	public void getWaitForApproachUsers(UsersManageModel model);

	public List<UserGroup> getGroups(int groupTypeId);

	public void editUser(UsersManageModel model) throws BBSException, ValidationException;

	public void searchUsers(GetUsersModel model) throws Exception;

	public void getWebMasters(WebMastersManageModel model);

	public int getMessageSizeNotRead(User user);

	public void saveFavoriteTopic(UserFavoriteTopic model);
	
	public void getFavoriteTopics(UserFavoriteTopic model);
	
	public void delFavoriteTopic(UserFavoriteTopic model);

	public void getUser(UsersManageModel model);

	public void verifyUser(UsersManageModel model);

	public boolean isUserNameInUser(String userName);

	public boolean isNickNameInUser(String nick);

	public void updateHonors();
	
	public void getLastRegisterUsers(UsersManageModel model);
	
	public void getRegisterUsersToday(UsersManageModel model);

	public void getVoteWebMasters(WebMastersManageModel model) throws Exception;

	public void freshWebMasters(WebMastersManageModel model);

	public void resetVote();

	public void deleteVoteWebMaster(WebMastersManageModel model);
	
	public void addVoteWebMaster(WebMastersManageModel model);

	public void VoteWebMasterDo(WebMastersManageModel model) throws Exception;
	
	public void getCupOfLife(SaveCupOfLifeModel model) throws Exception;
	
	public void saveCupOfLife(SaveCupOfLifeModel model) throws BBSException, ObjectNotFoundException;
	
	public void myUpload(MyUploadModel model)throws BBSException;
	
	public void getUploadItems(MyUploadModel model)throws BBSException;
	
	public void searchUserUploadItems(MyUploadModel model)throws BBSException;

	public boolean deleteUploadItems(MyUploadModel model);
	
	public void selectUserLoginInfo(SelectUserInfoModel model) throws Exception;

	public boolean editReplyMessage(EidtUserInfoModel model,Integer type);
	
	public void updateLastWeekExp();
	
	public List<TopThree> getTopThree(int type);
	
	public void addHits(boolean isLogin);
}
