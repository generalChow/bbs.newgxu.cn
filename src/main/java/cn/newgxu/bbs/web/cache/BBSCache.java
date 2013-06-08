package cn.newgxu.bbs.web.cache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.config.ForumConfig;
import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.HotTopic;
import cn.newgxu.bbs.domain.RemoteContent;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.activity.Tips;
import cn.newgxu.bbs.domain.user.User;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.cache.BBSCache.java
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-11-4
 * @describe 对BBS中缓存的管理
 */
@SuppressWarnings("unchecked")
public class BBSCache {

	public static Cache areaCache;// 论坛版块缓存
	public static Cache expUserCache;// 最有价值
	public static Cache moneyUserCache;//
	public static Cache topicUserCache;// 主题数
	public static Cache replyUserCache;// 回复数
	public static Cache goodUserCache;// 发表的精华数
	public static Cache hotTopicCache;// 热门主题缓存
	public static Cache goodTopicCache;// 精彩推荐缓存

	// 新添加的 论坛10周年主页改版
	public static Cache diaryCache;// 心情日记
	public static Cache smallNewsCache;// 小字报
	public static Cache wishesCache;// 祝福墙
	public static Cache noticesCache;// 新闻网公告
	public static Cache lostsCache;// 丢失物品信息
	public static Cache findsCache;// 捡到物品信息
	public static Cache twittersCache;// 微博信息

	public static Cache tipCache;// 登录页面中的图片缓存
	
	public static int topicCount1 = Topic.getLatestTopicId();
	public static int topicCount2 = topicCount1;

	public static List<Area> getAreaCache() {
		if (areaCache == null || isLatestTopicsNeedUpate())
			buildAreaCache();
		return areaCache.getList();
	}

	/**
	 * 重新更新缓存后，由于 area 的forums 是设置了 fetch = FetchType.LAZY 这样，如果没有及时获取area 中的
	 * forums 值，那么，下次再想获取时， 会出现：
	 * org.hibernate.LazyInitializationException:failed to lazily initialize a
	 * collection of role:*.Class.property,no session or session was closed
	 * 因为sesssion 已经关闭了，也就不能再 联级加载。
	 * 
	 * 这里，手动调用一下 getForums 中的值。使得一次加载完全部的数据。
	 */
	public static void buildAreaCache() {
		List<Area> list = Area.getAreas();
		
		for (Area a : list) {

			// 获取最新的帖子,用于10周年改版
			int id = a.getId();
			// 坑爹的站务管理...
			if (id == 1) {
				if (TimerUtils.getDay() % 2 == 0)
					a.setLatestTopics(a.getLatestTopicsByForumId(1, 2));
				else
					a.setLatestTopics(a.getLatestTopicsByForumId(14, 2));
			} else if (id == 51 || id == 3 || id == 4 || id ==5 || id == 6 || id == 48) {
				a.setLatestTopics(a.getLatestTopics(2));
			} else if (id == 2 || id == 43 || id == 45 || id == 49) {
				a.setLatestTopics(a.getLatestTopics(4));
			} else {
			//	a.setLatestTopics(a.getLatestTopics(0));
			}
			
			// 后台控制标题过长 用css控制@2012-09-23
//			for (TopicProvider topic : a.getLatestTopics()) {
//				String title = topic.getTitle();
//				if (title.length() > 35) {
//					title = title.substring(0, 33) + "...";
//					topic.setTitle(title);
//				}
//			}
	
			// *************************

			String s = "";
			for (Forum f : a.getForums()) {
				s += f.getName() + "    ";
			}
			System.out.println(a.getName() + "   lazy successed! 版块：" + s);
		}
		areaCache = new Cache();
		areaCache.setDate(new Date());
		areaCache.setList(list);
		areaCache.setName("论坛版块以及新帖缓存");
		System.out.println(areaCache.getName() + "      build success!");
	}
	
	

	/**
	 * 精彩推荐
	 * 
	 * @return
	 */
	public static List<HotTopic> getGoodTopicCache() {
		if (goodTopicCache == null || goodTopicCache.isNeedUpdate())
			buildGoodTopicCache();
		return goodTopicCache.getList();
	}

	public static void buildGoodTopicCache() {
		List<HotTopic> list = HotTopic.getPubTopics(2, 4);
		String s = "";
		for (HotTopic h : list) {
			
			s += h.getForum() + "|" + h.getTopic().getId() + "  ";
		}
		goodTopicCache = new Cache();
		goodTopicCache.setDate(new Date());
		goodTopicCache.setList(list);
		goodTopicCache.setTimeout(getTimeOut());
		goodTopicCache.setName("首页精彩帖子推荐缓存");
		System.out.println(goodTopicCache.getName() + "      build success!  "
				+ s);
	}

	/**
	 * 热门帖子缓存
	 * 
	 * @return
	 */
	public static List<HotTopic> getHotTopicCache() {
		if (hotTopicCache == null || hotTopicCache.isNeedUpdate())
			buildHotTopicCache();
		return hotTopicCache.getList();
	}

	public static void buildHotTopicCache() {
		String s = "";
		List<HotTopic> list = HotTopic.getPubTopics(1, 7);
		for (HotTopic h : list) {
			s += h.getForum() + "|" + h.getTopic().getId() + "  ";
		}
		hotTopicCache = new Cache();
		hotTopicCache.setDate(new Date());
		hotTopicCache.setList(list);
		hotTopicCache.setTimeout(getTimeOut());
		hotTopicCache.setName("首页热门帖子推荐缓存");
		System.out.println(hotTopicCache.getName() + "      build success! "
				+ s);
	}

	public static List<User> getExpUserCache() {
		if (expUserCache == null || expUserCache.isNeedUpdate())
			buildExpUserCache();
		return expUserCache.getList();
	}

	public static void buildExpUserCache() {
		List<User> list = User.getUsersOrderByExp(getPagination());
		expUserCache = new Cache();
		expUserCache.setDate(new Date());
		expUserCache.setList(list);
		expUserCache.setTimeout(getTimeOut());
		expUserCache.setName("最有价值TOP10缓存");
		System.out.println(expUserCache.getName() + "      build success!");
	}

	public static List<User> getMoneyUserCache() {
		if (moneyUserCache == null || moneyUserCache.isNeedUpdate())
			buildMoneyUserCache();
		return moneyUserCache.getList();
	}

	public static void buildMoneyUserCache() {
		List<User> list = User.getUsersOrderByMoney(getPagination());
		moneyUserCache = new Cache();
		moneyUserCache.setDate(new Date());
		moneyUserCache.setList(list);
		moneyUserCache.setTimeout(getTimeOut());
		moneyUserCache.setName("最有钱力TOP10缓存");
		System.out.println(moneyUserCache.getName() + "      build success!");
	}

	public static List<User> getTopicUserCache() {
		if (topicUserCache == null || topicUserCache.isNeedUpdate())
			buildTopicUserCache();
		return topicUserCache.getList();
	}

	public static void buildTopicUserCache() {
		List<User> list = User.getUsersOrderByTopic(getPagination());
		topicUserCache = new Cache();
		topicUserCache.setDate(new Date());
		topicUserCache.setList(list);
		topicUserCache.setTimeout(getTimeOut());
		topicUserCache.setName("最有影响TOP10缓存");
		System.out.println(topicUserCache.getName() + "      build success!");
	}

	public static List<User> getReplyUserCache() {
		if (replyUserCache == null || replyUserCache.isNeedUpdate())
			buildReplyUserCache();
		return replyUserCache.getList();
	}

	public static void buildReplyUserCache() {
		List<User> list = User.getUsersOrderByReply(getPagination());
		replyUserCache = new Cache();
		replyUserCache.setDate(new Date());
		replyUserCache.setList(list);
		replyUserCache.setTimeout(getTimeOut());
		replyUserCache.setName("十大水鬼缓存");
		System.out.println(replyUserCache.getName() + "      build success!");
	}

	public static List<User> getGoodUserCache() {
		if (goodUserCache == null || goodUserCache.isNeedUpdate())
			buildGoodUserCache();
		return goodUserCache.getList();
	}

	public static void buildGoodUserCache() {
		List<User> list = User.getUsersOrderByGood(getPagination());
		goodUserCache = new Cache();
		goodUserCache.setDate(new Date());
		goodUserCache.setList(list);
		goodUserCache.setTimeout(getTimeOut());
		goodUserCache.setName("十大杰出水手缓存");
		System.out.println(goodUserCache.getName() + "      build success!");
	}

	private static Pagination getPagination() {
		Pagination pagination = new Pagination();
		pagination.setPageSize(10);
		return pagination;
	}

	public static List<Tips> getTipsCache() {
		if (tipCache == null || tipCache.isNeedUpdate())
			buildTipCache();
		return tipCache.getList();
	}

	public static void buildTipCache() {
		List<Tips> list = new ArrayList<Tips>();
//		Tips tip = Tips.getCurrent();
//		Calendar deadline = Calendar.getInstance();
//		deadline.set(Calendar.DAY_OF_MONTH, 22);
//		Tips tip = Tips.getRandomHolidayImage(deadline, pattern);
		Tips tip = Tips.getTodayImage();

		if (tip != null)
			list.add(tip);
		tipCache = new Cache();
		tipCache.setDate(new Date());
		tipCache.setList(list);
//		tipCache.setTimeout(getTimeOut() * 4 * 60 * 24);// 默认是4天更新一次
		tipCache.setTimeout(120 * 1000); // 2min 更新一次
		tipCache.setName("登录页面缓存");
		System.out.println(tipCache.getName() + "      build success!");
	}

	/**
	 * 获取当前的全部cache
	 * 
	 * @return
	 */
	public static List<Cache> getCurrentCache() {
		List<Cache> list = new ArrayList<Cache>();
		for (Field field : BBSCache.class.getFields()) {
			if (field.getName().endsWith("Cache")) {
				try {
					list.add((Cache) field.get(null));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static long getTimeOut() {
		long base = 0;
		try {
			base = Long.valueOf(ForumConfig.getConfig("cache_timeout"));
		} catch (Exception e) {
			e.printStackTrace();
			base = 3 * 60;
		}
		return base * 60 * 1000;
	}

	/**
	 * 获取首页心情日记的缓存
	 * @since 2012-04-17
	 */
//	public static void buildDiaryCache() {
//		List<Diary> list = Diary.getDiarys(0, 7);
//		diaryCache = new Cache();
//		diaryCache.setDate(new Date());
//		diaryCache.setList(list);
//		diaryCache.setTimeout(getTimeOut());
//		diaryCache.setName("首页心情日记缓存");
//		System.out.println(diaryCache.getName()
//				+ "      build success!  ");
//	}

	/**
	 * 心情日记缓存
	 * @since 2012-04-17
	 */
//	public static List<TopicProvider> getDiaryCache() {
//		if (diaryCache == null
//				|| diaryCache.isNeedUpdate())
//			buildDiaryCache();
//		return diaryCache.getList();
//	}
	
	/**
	 * 获取首页小字报的缓存
	 * @since 2012-04-17
	 */
	public static void buildSmallNewsCache() {
		List<SmallNews> list = SmallNews.getSmallNews(3);
		smallNewsCache = new Cache();
		smallNewsCache.setDate(new Date());
		smallNewsCache.setList(list);
		smallNewsCache.setTimeout(getTimeOut());
		smallNewsCache.setName("首页小字报缓存");
		System.out.println(smallNewsCache.getName()
				+ "      build success!  ");
	}

	/**
	 * 小字报缓存
	 * @since 2012-04-17
	 */
	public static List<SmallNews> getSmallNewsCache() {
		if (smallNewsCache == null
				|| smallNewsCache.isNeedUpdate())
			buildSmallNewsCache();
		return smallNewsCache.getList();
	}
	
	/**
	 * 获取首页祝福墙的缓存
	 * @since 2012-04-24
	 */
	public static void buildWishesCache() {
		List<RemoteContent> list = new ArrayList<RemoteContent>();
		try {
			list = RemoteContent.getWishesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		wishesCache = new Cache();
		wishesCache.setDate(new Date());
		wishesCache.setList(list);
		wishesCache.setTimeout(getTimeOut());
		wishesCache.setName("首页祝福墙缓存");
		System.out.println(wishesCache.getName()
				+ "      build success!  ");
	}

	/**
	 * 祝福墙缓存
	 * @since 2012-04-24
	 */
	public static List<RemoteContent> getWishesCache() {
		if (wishesCache == null
				|| wishesCache.isNeedUpdate())
			buildWishesCache();
		return wishesCache.getList();
	}
	
	/**
	 * 获取首页新闻网的缓存
	 * @since 2012-04-24
	 */
	@Deprecated
	public static void buildNoticesCache() {
		List<RemoteContent> list = new ArrayList<RemoteContent>();
		try {
			list = RemoteContent.getNoticeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		noticesCache = new Cache();
		noticesCache.setDate(new Date());
		noticesCache.setList(list);
		noticesCache.setTimeout(getTimeOut());
		noticesCache.setName("首页校园通告缓存");
		System.out.println(noticesCache.getName()
				+ "      build success!  ");
	}

	/**
	 * 新闻网公告缓存
	 * @since 2012-04-24
	 */
	@Deprecated
	public static List<RemoteContent> getNoticesCache() {
		if (noticesCache == null
				|| noticesCache.isNeedUpdate())
			buildNoticesCache();
		return noticesCache.getList();
	}
	
	/**
	 * 失物招领丢失物品缓存
	 * @since 2012-04-24
	 */
	@Deprecated
	public static void buildLostsCache() {
		List<RemoteContent> list = new ArrayList<RemoteContent>();
		try {
			list = RemoteContent.getLostList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		lostsCache = new Cache();
		lostsCache.setDate(new Date());
		lostsCache.setList(list);
		lostsCache.setTimeout(getTimeOut());
		lostsCache.setName("首页失物招领丢失物品缓存");
		System.out.println(lostsCache.getName()
				+ "      build success!  ");
	}

	/**
	 * 失物招领丢失物品缓存
	 * @since 2012-04-24
	 */
	@Deprecated
	public static List<RemoteContent> getFindsCache() {
		if (lostsCache == null
				|| lostsCache.isNeedUpdate())
			buildLostsCache();
		return lostsCache.getList();
	}
	
	/**
	 * 失物招领捡到物品缓存
	 * @since 2012-04-24
	 */
	@Deprecated
	public static void buildFindsCache() {
		List<RemoteContent> list = new ArrayList<RemoteContent>();
		try {
			list = RemoteContent.getFindList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		findsCache = new Cache();
		findsCache.setDate(new Date());
		findsCache.setList(list);
		findsCache.setTimeout(getTimeOut());
		findsCache.setName("首页失物招领捡到物品缓存");
		System.out.println(findsCache.getName()
				+ "      build success!  ");
	}

	/**
	 * 失物招领捡到物品缓存
	 * @since 2012-04-24
	 */
	@Deprecated
	public static List<RemoteContent> getLostsCache() {
		if (findsCache == null
				|| findsCache.isNeedUpdate())
			buildFindsCache();
		return findsCache.getList();
	}
	
	/**
	 * 微博缓存
	 * @since 2012-05-09
	 */
	@Deprecated
	public static List<RemoteContent> buildTwittersCache(String userName) {
		List<RemoteContent> list = new ArrayList<RemoteContent>();
		try {
			list = RemoteContent.getTwitter(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		twittersCache = new Cache();
		twittersCache.setDate(new Date());
		twittersCache.setList(list);
		twittersCache.setTimeout(getTimeOut());
		twittersCache.setName("首页微博缓存");
		System.out.println(twittersCache.getName()
				+ "      build success!  ");
		return list;
	}

	/**
	 * 微博缓存
	 * @since 2012-05-09
	 */
	@Deprecated
	public static List<RemoteContent> getTwittersCache(String userName) {
		if (twittersCache == null
				|| twittersCache.isNeedUpdate())
			buildTwittersCache(userName);
		return twittersCache.getList();
	}
	
	public static boolean isLatestTopicsNeedUpate() {
		if (topicCount1 < topicCount2) {
			topicCount1 = topicCount2;
			return true;
		}
		return false;
	}
}