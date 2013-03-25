package cn.newgxu.bbs.web.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.newgxu.bbs.common.staticHtml.StaticManager;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.HitsCounter;
import cn.newgxu.bbs.domain.HotTopic;
import cn.newgxu.bbs.domain.RemoteContent;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.cache.BBSCache;
import cn.newgxu.bbs.web.model.IndexModel;
import cn.newgxu.bbs.web.model.UserStatus;

import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class IndexAction extends AbstractBaseAction {

	private static final long serialVersionUID = -4853741132558481049L;
	
	private static final Logger l = LoggerFactory.getLogger(IndexAction.class);

	private IndexModel model = new IndexModel();

	private ForumService forumService;
	
	//private OSCacheManage osCacheManage = OSCacheManage.getInstance();
	
	public ForumService getForumService() {
		return forumService;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}
	
	public String execute() throws Exception {
		signOnlineUser("论坛首页");
		userService.updateLastWeekExp();
		
		long p = new Date().getTime();
		UserStatus userStatus = super.getStatus();
		
		  //现在首页已经静态化，不需要加载这些内容了
		 //-----------------old
//			List<Area> areas = this.forumService.getAreas();
		
			List<Area> areas = BBSCache.getAreaCache();
//			List<User> topMoneyUsers = userService.getUsers(2, null);
			List<User> topMoneyUsers = BBSCache.getMoneyUserCache();
//			List<User> topTopicUsers = userService.getUsers(3, null);
			List<User> topTopicUsers = BBSCache.getTopicUserCache();
//			List<User> topReplyUsers = userService.getUsers(4, null);
			List<User> topReplyUsers = BBSCache.getReplyUserCache();
//			List<User> topGoodUsers = userService.getUsers(5, null);
			List<User> topGoodUsers = BBSCache.getGoodUserCache();
//			List<HotTopic> pubGoodTopics = forumService.getTopics(2, 4);
			List<HotTopic> pubGoodTopics = BBSCache.getGoodTopicCache();   // 应网管部要求，在新首页取消之，但是为了考虑旧首页，暂时预留之
//			List<HotTopic> pubHotTopics = forumService.getTopics(1, 7);
			List<HotTopic> pubHotTopics = BBSCache.getHotTopicCache();
//			List<User> topExpUsers = userService.getUsers(1, null);
			List<User> topExpUsers = BBSCache.getExpUserCache();
//			List<Topic> recommendedTopics = Topic.getRecomendedTopic(7, 1, true, true);
//			List<Diary> diaries = Diary.getDiarys(1, 7);
//			List<SmallNews> smallNews = SmallNews.getSmallNews(3);
			List<SmallNews> smallNews = BBSCache.getSmallNewsCache();
//			List<RemoteContent> wishes = RemoteContent.getWishesList(); // 祝福墙
			List<RemoteContent> wishes = BBSCache.getWishesCache();
//			List<RemoteContent> notices = RemoteContent.getNoticeList();
			List<RemoteContent> notices = BBSCache.getNoticesCache();
//			List<RemoteContent> losts = RemoteContent.getLostList();
			List<RemoteContent> losts = BBSCache.getLostsCache();
//			List<RemoteContent> finds = RemoteContent.getFindList();
			List<RemoteContent> finds = BBSCache.getFindsCache();
//			List<RemoteContent> twitters = BBSCache.getTwittersCache(userStatus.getUsername());
			List<RemoteContent> twitters = RemoteContent.getTwitter(userStatus.getUsername());  // 微博更新快，不需要缓存
			
			l.debug("user's name: {}， twitters' length: {}", userStatus.getUsername(), twitters.size());
			
			List<User> lastWeekMostActiveUsers = userService.getUsers(6, null);
			
			model.setAreas(areas);
			model.setPubHotTopics(pubHotTopics);
			model.setTopExpUsers(topExpUsers);
			model.setTopGoodUsers(topGoodUsers);
			model.setTopMoneyUsers(topMoneyUsers);
			model.setTopReplyUsers(topReplyUsers);
			model.setTopTopicUsers(topTopicUsers);
			model.setPubGoodTopics(pubGoodTopics);
//			model.setDiaries(diaries);
			model.setSmallNews(smallNews);
			model.setWishes(wishes);
			model.setNotices(notices);
			model.setLosts(losts);
			model.setFinds(finds);
			model.setTwitters(twitters);
			model.setLastWeekMostActiveUsers(lastWeekMostActiveUsers);
			//------------old
		
//		UserStatus userStatus = super.getStatus();   移动到上面去了，为了获取用户名去获取微博的信息
		userStatus.setTotalHicount(HitsCounter.getTotalHitsCounter());
		userStatus.setTodayHicount(HitsCounter.getTodayHitsCounter());
		super.setStats(userStatus);
		System.out.println(new Date().getTime() - p);
		return SUCCESS;
	}
	
	/**
	 * 生成首页的html静态文件
	 * @author 集成显卡
	 * @return
	 */
	public String html(){
		StaticManager staticManager=(StaticManager)Util.getBean("staticManager");
		staticManager.staticIndex(ServletActionContext.getServletContext());
		return SUCCESS;
	}
	
	/**
	 * 默认地转到了登录页面
	 * @return
	 */
	public String login(){
		HttpServletRequest request = getRequest();
		String header = request.getHeader("User-Agent");
		if (header.contains("MSIE 6.0")) {
			return "login_ie6";
		}
		return LOGIN;
	}

	public Object getModel() {
		return model;
	}
}
