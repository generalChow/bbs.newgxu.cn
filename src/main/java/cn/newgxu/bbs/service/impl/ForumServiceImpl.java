package cn.newgxu.bbs.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.config.ForumConfig;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.util.ForumControlUtil;
import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.DraftBox;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.HotTopic;
import cn.newgxu.bbs.domain.ManageLog;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.ReplyLine;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.activity.Christmas;
import cn.newgxu.bbs.domain.activity.Doomsday;
import cn.newgxu.bbs.domain.bank.Bank;
import cn.newgxu.bbs.domain.group.BasicGroup;
import cn.newgxu.bbs.domain.group.GroupManager;
import cn.newgxu.bbs.domain.lucky.Lucky;
import cn.newgxu.bbs.domain.lucky.LuckyConfig;
import cn.newgxu.bbs.domain.lucky.LuckyGift;
import cn.newgxu.bbs.domain.lucky.LuckyLog;
import cn.newgxu.bbs.domain.lucky.LuckySubject;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.market.ItemWork;
import cn.newgxu.bbs.domain.message.Message;
import cn.newgxu.bbs.domain.sys.Param;
import cn.newgxu.bbs.domain.user.OnlineUser;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.domain.vote.Vote;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.activity.Activity;
import cn.newgxu.bbs.web.cache.BBSCache;
import cn.newgxu.bbs.web.model.AreaModel;
import cn.newgxu.bbs.web.model.BaseTopicModel;
import cn.newgxu.bbs.web.model.BoardStateModel;
import cn.newgxu.bbs.web.model.BoardView;
import cn.newgxu.bbs.web.model.CreateSmallNewsModel;
import cn.newgxu.bbs.web.model.CreateTopicModel;
import cn.newgxu.bbs.web.model.CreateVoteModel;
import cn.newgxu.bbs.web.model.ForumModel;
import cn.newgxu.bbs.web.model.LuckyActionModel;
import cn.newgxu.bbs.web.model.LuckyTopicModel;
import cn.newgxu.bbs.web.model.ManageModel;
import cn.newgxu.bbs.web.model.ModifyModel;
import cn.newgxu.bbs.web.model.ReplyModel;
import cn.newgxu.bbs.web.model.SearchModel;
import cn.newgxu.bbs.web.model.SmallNewsModel;
import cn.newgxu.bbs.web.model.TopicModel;
import cn.newgxu.bbs.web.model.VoteModel;
import cn.newgxu.bbs.web.model.admin.AreaManageModel;
import cn.newgxu.bbs.web.model.admin.ForumManageModel;
import cn.newgxu.bbs.web.model.admin.ForumTimeModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ForumServiceImpl implements ForumService {

	protected static final int TOPIC_COUNT_PER_PAGE = 30;

	protected static final int REPLY_COUNT_PER_PAGE = 30;

	public void createArea(AreaManageModel model) throws BBSException {
		Area area = new Area();
		area.setName(model.getName());
		area.setTaxis(model.getTaxis());
		area.setHot(model.getHot() == 1);
		area.setHidden(model.getHidden() == 1);
		area.setDescription(model.getDescription());
		if (StringUtils.isEmpty(area.getName())) {
			throw new BBSException("区论坛名称不能为空！");
		}
		try {
			Area.getByName(area.getName());
			throw new BBSException("区论坛名称已经存在！");
		} catch (ObjectNotFoundException e) {

		}
		List<String> nicks = Util.splitNicks(model.getNicks());
		// 区管理员
		List<User> webmasters = new LinkedList<User>();
		// 错误昵称，用户不存在
		String notExistUsers = "";
		String notInAreaWebmasterGroup = "";

		for (int i = 0; i < nicks.size(); i++) {
			String nick = (String) nicks.get(i);
			try {
				User user = User.getByNick(nick);
				if (user.getGroupTypeId() < GroupManager.AREA_WEBMASTER_GROUP) {
					notInAreaWebmasterGroup += nick + "，";
				}
				webmasters.add(user);
			} catch (ObjectNotFoundException e) {
				notExistUsers += nick + "，";
			}
		}
		if (notExistUsers.length() > 0) {
			throw new BBSException("以下用户昵称不存在：" + notExistUsers + "请重新填写！");
		}
		if (notInAreaWebmasterGroup.length() > 0) {
			throw new BBSException("以下用户不属于区管理员组：" + notInAreaWebmasterGroup
					+ "请先调整用户所在组！");
		}
		area.setWebmasters(webmasters);
		area.save();
	}

	public void editArea(AreaManageModel model) throws BBSException {
		Area area = this.getArea(model.getAreaId());
		area.setName(model.getName());
		area.setTaxis(model.getTaxis());
		area.setHot(model.getHot() == 1);
		area.setHidden(model.getHidden() == 1);
		area.setDescription(model.getDescription());
		if (StringUtils.isEmpty(area.getName())) {
			throw new BBSException("区论坛名称不能为空！");
		}
		try {
			if (Area.getByName(area.getName()).getId() != area.getId()) {
				throw new BBSException("区论坛名称已经存在！");
			}
		} catch (ObjectNotFoundException e) {
		}
		List<String> nicks = Util.splitNicks(model.getNicks());
		// 区管理员
		List<User> webmasters = new LinkedList<User>();
		// 错误昵称，用户不存在
		String notExistUsers = "";
		String notInAreaWebmasterGroup = "";

		List<User> oldwebmaster = area.getWebmasters();
		for (int i = 0; i < nicks.size(); i++) {
			String nick = (String) nicks.get(i);
			try {
				User user = User.getByNick(nick);
				if (!oldwebmaster.contains(user)) {
					user.changeAuthority(1, GroupManager.AREA_WEBMASTER_GROUP);
					user.save();
					oldwebmaster.remove(user);
				}
				if (user.getGroupTypeId() < GroupManager.AREA_WEBMASTER_GROUP) {
					notInAreaWebmasterGroup += nick + "，";
				}
				webmasters.add(user);
			} catch (ObjectNotFoundException e) {
				notExistUsers += nick + "，";
			}
		}
		if (notExistUsers.length() > 0) {
			throw new BBSException("以下用户昵称不存在：" + notExistUsers + "请重新填写！");
		}
		if (notInAreaWebmasterGroup.length() > 0) {
			throw new BBSException("以下用户不属于区管理员组：" + notInAreaWebmasterGroup
					+ "请先调整用户所在组！");
		}
		area.setWebmasters(webmasters);
		area.save();
	}

	public void createForum(ForumManageModel model) throws BBSException {
		Forum forum = new Forum();
		forum.setName(model.getName());
		forum.setCompositorId(model.getCompositorId());
		forum.setHot(model.getHot() == 1);
		forum.setSecrecy(model.getSecrecy() == 1);
		forum.setConfrere(model.getConfrere() == 1);
		forum.setTopicMoney(model.getTopicMoney());
		forum.setTopicExp(model.getTopicExp());
		forum.setReplyMoney(model.getReplyMoney());
		forum.setReplyExp(model.getReplyExp());
		forum.setGoodMoney(model.getGoodMoney());
		forum.setGoodExp(model.getGoodExp());
		forum.setLightMoney(model.getLightMoney());
		forum.setLightExp(model.getLightExp());
		forum.setDelMoney(model.getDelMoney());
		forum.setDelExp(model.getDelExp());
		forum.setDescription(model.getDescription());
		forum.setArea(this.getArea(model.getAreaId()));
		List<String> nicks = Util.splitNicks(model.getNicks());
		// 区管理员
		List<User> webmasters = new LinkedList<User>();
		// 错误昵称，用户不存在
		String notExistUsers = "";
		// String notInForumWebmasterGroup = "";

		for (int i = 0; i < nicks.size(); i++) {
			String nick = (String) nicks.get(i);
			try {
				User user = User.getByNick(nick);
				user.changeAuthority(1, GroupManager.FORUM_WEBMASTER_GROUP);
				user.save();
				// if (user.getGroupTypeId() !=
				// GroupManager.FORUM_WEBMASTER_GROUP) {
				// notInForumWebmasterGroup += nick + "，";
				// }
				webmasters.add(user);
			} catch (ObjectNotFoundException e) {
				notExistUsers += nick + "，";
			}
		}
		if (notExistUsers.length() > 0) {
			throw new BBSException("以下用户昵称不存在：" + notExistUsers + "请重新填写！");
		}
		// if (notInForumWebmasterGroup.length() > 0) {
		// throw new BBSException("以下用户不属于区管理员组：" + notInForumWebmasterGroup
		// + "请先调整用户所在组！");
		// }
		forum.setWebmasters(webmasters);
		forum.save();
	}

	public void editForum(ForumManageModel model) throws BBSException {
		Forum forum = this.getForum(model.getForumId());
		forum.setName(model.getName());
		forum.setCompositorId(model.getCompositorId());
		forum.setHot(model.getHot() == 1);
		forum.setSecrecy(model.getSecrecy() == 1);
		forum.setConfrere(model.getConfrere() == 1);
		forum.setLimit_topics_perday(model.getLimit_topics_perday());
		forum.setTopicMoney(model.getTopicMoney());
		forum.setTopicExp(model.getTopicExp());
		forum.setReplyMoney(model.getReplyMoney());
		forum.setReplyExp(model.getReplyExp());
		forum.setGoodMoney(model.getGoodMoney());
		forum.setGoodExp(model.getGoodExp());
		forum.setLightMoney(model.getLightMoney());
		forum.setLightExp(model.getLightExp());
		forum.setDelMoney(model.getDelMoney());
		forum.setDelExp(model.getDelExp());
		forum.setDescription(model.getDescription());
		forum.setArea(this.getArea(model.getAreaId()));
		List<String> nicks = Util.splitNicks(model.getNicks());
		// 区管理员
		List<User> webmasters = new LinkedList<User>();
		// 错误昵称，用户不存在
		String notExistUsers = "";
		// String notInForumWebmasterGroup = "";
		List<User> oldwebmaster = forum.getWebmasters();
		User user;

		for (int i = 0; i < nicks.size(); i++) {
			String nick = (String) nicks.get(i);
			System.out.println("------------>"+nick);
			try {
				user = User.getByNick(nick);
				if (!oldwebmaster.contains(user)) {
					// user.changeAuthority(1,
					// GroupManager.FORUM_WEBMASTER_GROUP);
					GroupManager.changeGroup(user,
							GroupManager.FORUM_WEBMASTER_GROUP, 0);
					user.save();
				} else {
					oldwebmaster.remove(user);
				}
				// if (user.getGroupTypeId() !=
				// GroupManager.FORUM_WEBMASTER_GROUP) {
				// notInForumWebmasterGroup += nick + "，";
				// }
				webmasters.add(user);
			} catch (ObjectNotFoundException e) {
				notExistUsers += nick + "，";
			}
		}
		for (int i = 0; i < oldwebmaster.size(); i++) {
			user = oldwebmaster.get(i);
			// user.changeAuthority(1, GroupManager.BASIC_GROUP);
			GroupManager.changeGroup(user, GroupManager.BASIC_GROUP, 0);
			System.out.println(user.getGroupTypeId() + "++++++++++++++++++"
					+ user.getGroupId());
			user.save();
		}
		if (notExistUsers.length() > 0) {
			throw new BBSException("以下用户昵称不存在：" + notExistUsers + "请重新填写！");
		}
		// if (notInForumWebmasterGroup.length() > 0) {
		// throw new BBSException("以下用户不属于版主组：" + notInForumWebmasterGroup
		// + "请先调整用户所在组！");
		// }
		forum.setWebmasters(webmasters);
		forum.save();
	}

	public Area getArea(int areaId) throws BBSException {
		try {
			return Area.get(areaId);
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
	}

	public Forum getForum(int forumId) throws BBSException {
		try {
			return Forum.get(forumId);
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
	}

	public List<Area> getAreas() {
		//List<Area> areas = Area.getAreas();
		//return areas;
		
		//直接从BBSCache 中获取缓存   集成显卡 2011-11-4
		return BBSCache.getAreaCache();
	}

	public List<Area> getAllAreas() {
		return Area.getAllAreas();
	}

	public Forum getForum(int forumId, User user) throws BBSException {
		Forum forum;
		try {
			forum = Forum.get(forumId);
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

		// 检查用户是否有权限进入
		if (user == null) {
			if (forum.isSecrecy() || forum.isConfrere()) {
				throw new BBSException(BBSExceptionMessage.CANNOT_VIEW_FORUM);
			}
		} else if (!user.getUserGroup().isAuthViewForum(forum, user)) {
			throw new BBSException(BBSExceptionMessage.CANNOT_VIEW_FORUM);
		}

		return forum;
	}

	public List<Topic> getTopics_SortByCreateTime(Forum forum, Pagination p) {
		p.setPageSize(Constants.NUMBER_OF_TOPICS);
		return forum.getTopics(Constants.SORT_BY_CREATETIME, p);
	}

	public List<Topic> getTopics_Normal(Forum forum, Pagination p) {
		p.setPageSize(Constants.NUMBER_OF_TOPICS);
		return forum.getTopics(Constants.SORT_BY_REPLYTIME, p);
	}

	public List<Topic> getTopics_Good(Forum forum, Pagination p) {
		p.setPageSize(Constants.NUMBER_OF_TOPICS);
		return forum.getGoodTopics(p);
	}

	public List<HotTopic> getTopics(int pubType, int num) {
		//return HotTopic.getPubTopics(pubType, num);
		
		/*
		 * 从BBSCache 中获取缓存信息
		 */
		if(pubType==1)
			return BBSCache.getHotTopicCache();
		else if(pubType==2)
			return BBSCache.getGoodTopicCache();
		return null;
	}

	public Topic getTopic(Forum forum, int topicId) throws BBSException {
		Topic topic;
		try {
			topic = Topic.get(topicId);
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

		if (topic.getForum().getId() != forum.getId()) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		
		/*被删除的帖子*/
		if(topic.isInvalid()){
			throw new BBSException(BBSExceptionMessage.DELETE_TOPIC);
		}
		return topic;
	}

	public Reply getReply(Topic topic, int replyId) throws BBSException {
		Reply reply;
		try {
			reply = Reply.get(replyId);
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

		if (reply.getTopic().getId() != topic.getId()) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

		return reply;
	}

	public List<Reply> getReplies_Normal(Topic topic, Pagination p) {
		return topic.getReplies(p, false);
	}

	public List<ReplyLine> getReplieLines_Normal(Topic topic, Pagination p) {
		return topic.getReplieLines(p, false);
	}

	public void forum(ForumModel model) throws BBSException {
		/***
		 * 2011-3-4forumId=0时报错
		 */
		if(model.getForumId()<1){
			throw new BBSException("你所访问的版块不存在！");
		}
		model.setForum(getForum(model.getForumId(), model.getViewer()));
		// 如果是查看精华列表
		if (model.getGood() != null && model.getGood().equals("true")) {
			model.setTopics(getTopics_Good(model.getForum(), model
					.getPagination()));
			// 如果查看帖子列表方式为按创建时间排序
		} else if (model.getSort() != null
				&& model.getSort().equals("createTime")) {
			model.setTopics(getTopics_SortByCreateTime(model.getForum(), model
					.getPagination()));
		} else {
			model.setTopics(getTopics_Normal(model.getForum(), model
					.getPagination()));
		}
		model.setAreas(getAreas());
	}

	public Topic createTopicDo(CreateTopicModel model) throws BBSException {
		User user = model.getUser();
		// 注册两日内不能发帖
		if (user.isNewCome()) {
			throw new BBSException(
					BBSExceptionMessage.USER_ISNEWCOME_NOT_CREATE);
		}
		// 用户组不允许发帖
		if (!user.getUserGroup().isAuthCreateTopic()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_TOPIC);
		}
		// 网站关闭时间不能发帖,对普通用户来说
		if (ForumControlUtil.isForumClose()
				&& model.getUser().getGroupTypeId() == GroupManager.BASIC_GROUP) {
			throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
		}

		Forum forum = getForum(model.getForumId(), user);

		// 达到每天某版块发帖最大限制
		try {
			if (Topic.getTopicsPerTodayByUser(user, forum) >= forum
					.getLimit_topics_perday()) {
				throw new BBSException(BBSExceptionMessage.TOPICS_PERDAY_LIMIT);
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Bank bank = getBank();
		user.newTopic(forum, bank);

		Date postTime = new Date();
		Topic topic = new Topic();
		topic.setTitle(model.getTitle());
		topic.setFace(model.getFace());
		topic.setClickTimes(0);
		topic.setReplyTime(postTime);
		topic.setReplyTimes(0);
		topic.setTopType(0);
		
		
		topic.setCreationTime(postTime);
		
		// 用于添加活动，但是还需要管理员确认
		this.activity(topic, model);

		Reply reply = new Reply();
		reply.setContent(model.getContent());
		reply.setPostTime(postTime);
		reply.setEditTime(postTime);
		reply.setFirstReply(true);

		topic.setTopicUser(user);
		topic.setReplyUser(user);
		topic.setForum(forum);

		reply.setTopic(topic);
		reply.setPostUser(user);

		topic.save();
		reply.save();
//		user.save();
		user.update();
		bank.save();

		return topic;
	}
	
	/**
	 * 添加帖子的活动信息。
	 * @param topic
	 * @param model
	 * @since 2012-12-19
	 */
	private void activity(Topic topic, CreateTopicModel model) {
		if (model.getActivity() == null) {
			return;
		} else if (model.getActivity().equals(Doomsday.getName())) {
			if (Doomsday.rightnow()) {
				topic.setActivityType("activity_2012");
			}
		} else if (model.getActivity().equals(Christmas.getName())) {
			if (Christmas.rightnow()) {
				topic.setActivityType("activity_2012");
			}
		}
	}

	public void topic(TopicModel model) throws BBSException {
		model.setForum(getForum(model.getForumId(), model.getUser()));
		
		Topic topic = getTopic(model.getForum(), model.getTopicId());
		
		model.setTopic(topic);
		// 帖子本身也算一楼，所以要加进去
	//	model.getPagination().setRecordSize(topic.getReplyTimes() + 1);

		/*
		 * 回复被删除后回复的总数，就与实际要显示的不一样
		 */
		model.getPagination().setRecordSize(topic.getRepliesCount(false));
		model.setReplieLines(getReplieLines_Normal(model.getTopic(), model
				.getPagination()));
		model.setAreas(getAreas());
		topic.display();
		topic.save();

		if (model.getUser() == null) {
			model.setVoteable(false);
		} else {
			model.setVoteable(!topic.isVoted(model.getUser()));
		}
	}

	public Topic createReply(ReplyModel model) throws Exception {
		User user = model.getUser();

		/**
		 * 新注册用户一天过后才能发帖！
		 */
//		if (user.isNewCome()) {
//			throw new BBSException(
//					BBSExceptionMessage.USER_ISNEWCOME_NOT_CREATE);
//		}

		if (!user.getUserGroup().isAuthReply()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_REPLY);
		}

		Forum forum = getForum(model.getForumId(), user);
		Topic topic = getTopic(forum, model.getTopicId());

		//通过不显示回帖框控制网站关闭时间普通用户不能回帖
		// 网站关闭时间不能发帖,对普通用户来说
		if (ForumControlUtil.isForumClose()
				&& model.getUser().getGroupTypeId() == GroupManager.BASIC_GROUP) {
			throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
		}

		if (topic.isLock()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_REPLY_LOCK_TOPIC);
		}
		// 限制回复冷却时间防止屠版
		if (topic.getReplyUser().equals(user)
				&& ((System.currentTimeMillis() - topic.getReplyTime()
						.getTime()) <= 3000)) {
			throw new BBSException(BBSExceptionMessage.OPERATION_TOO_FAST);
		}

		Reply r = null;
		if (model.getUserId() != 0) {
			try {
				r = Reply.get(model.getReplyId());
				if (r.getPostUser().getId() != model.getUserId()) {
					throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
				}

			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
			}
		}
		Bank bank = getBank();
		user.reply(forum, bank);

		Date postTime=new Date();
		Reply reply = new Reply();
		if(model.isIfPhone()){
			model.setContent(model.getContent().replaceAll("\r\n", "<br />"));
			model.setContent(model.getContent().replaceAll("\n", "<br />"));
		}
		reply.setContent(model.getContent());
		reply.setPostTime(postTime);
		reply.setEditTime(postTime);
		reply.setTopic(topic);
		reply.setPostUser(user);

		reply.save();

//		user.save();
		user.update();
		bank.save();

		topic.newReply(reply);
		topic.save();
		model.setTopic(topic);
		
		//
		model.setReply(reply);
		model.setPage(topic.getReplyTimes() / 30 + 1);
		//
		
		this.sendReplyMessage(model);
		
		//执行活动
		Activity.executeActivity(reply);
		return topic;
	}

	/**
	 * 检查是否可以发帖子，使用的参数是  BaseTopicModel ，<br />
	 * 可以给发帖的各种情况使用<br />
	 * add-->2011.10.4
	 * @param model
	 * @throws BBSException
	 */
	public void canCreateTopic(BaseTopicModel model)throws BBSException{
		User user = model.getUser();
		// //注册两日内不能发帖
		 if (model.getUser().isNewCome()) {
			 throw new BBSException(BBSExceptionMessage.USER_ISNEWCOME_NOT_CREATE);
		 }
		if (!model.getUser().getUserGroup().isAuthCreateTopic()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_TOPIC);
		}
		// 网站关闭时间不能发帖,对普通用户来说
		if (ForumControlUtil.isForumClose()&& model.getUser().getGroupTypeId() == GroupManager.BASIC_GROUP) {
			throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
		}
		Forum forum = getForum(model.getForumId(), user);
		model.setForum(forum);
		model.setAreas(getAreas());
		// 达到每天某版块发帖最大限制
		try {
			if (Topic.getTopicsPerTodayByUser(user, forum) > forum.getLimit_topics_perday()) {
				throw new BBSException(BBSExceptionMessage.TOPICS_PERDAY_LIMIT);
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void createTopic(CreateTopicModel model) throws BBSException {
		User user = model.getUser();
		// //注册两日内不能发帖
		 if (model.getUser().isNewCome()) {
			 throw new BBSException(BBSExceptionMessage.USER_ISNEWCOME_NOT_CREATE);
		 }

		if (!model.getUser().getUserGroup().isAuthCreateTopic()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_TOPIC);
		}

		// 网站关闭时间不能发帖,对普通用户来说
		if (ForumControlUtil.isForumClose()
				&& model.getUser().getGroupTypeId() == GroupManager.BASIC_GROUP) {
			throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
		}

		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setAreas(getAreas());

		Forum forum = getForum(model.getForumId(), user);
		// 达到每天某版块发帖最大限制
		try {
			// 解决提示发帖已达上限不能发帖的问题，2012-04-16
			if (forum.getLimit_topics_perday() != 0)
				if (Topic.getTopicsPerTodayByUser(user, forum) > forum
						.getLimit_topics_perday()) {
					throw new BBSException(BBSExceptionMessage.TOPICS_PERDAY_LIMIT);
				}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public void createVote(CreateVoteModel model) throws BBSException {
		if (!model.getUser().getUserGroup().isAuthCreateVote()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_VOTE);
		}

		// 网站关闭时间不能发帖,对普通用户来说
		if (ForumControlUtil.isForumClose()
				&& model.getUser().getGroupTypeId() == GroupManager.BASIC_GROUP) {
			throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
		}

		User user = model.getUser();
		Forum forum = getForum(model.getForumId(), user);

		// 达到每天某版块发帖最大限制
		try {
			if (Topic.getTopicsPerTodayByUser(user, forum) > forum
					.getLimit_topics_perday()) {
				throw new BBSException(BBSExceptionMessage.TOPICS_PERDAY_LIMIT);
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setAreas(getAreas());
	}

	public Topic createVoteDo(CreateVoteModel model) throws BBSException {
		if (!model.getUser().getUserGroup().isAuthCreateVote()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_VOTE);
		}

		// 网站关闭时间不能发帖,对普通用户来说
		if (ForumControlUtil.isForumClose()
				&& model.getUser().getGroupTypeId() == GroupManager.BASIC_GROUP) {
			throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
		}

		User user = model.getUser();
		Forum forum = getForum(model.getForumId(), user);

		// 达到每天某版块发帖最大限制
		try {
			if (Topic.getTopicsPerTodayByUser(user, forum) > forum
					.getLimit_topics_perday()) {
				throw new BBSException(BBSExceptionMessage.TOPICS_PERDAY_LIMIT);
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Bank bank = getBank();
		user.newTopic(forum, bank);

		Date postTime = new Date();
		Topic topic = new Topic();
		topic.setTitle(model.getTitle());
		topic.setFace(model.getFace());
		topic.setClickTimes(0);
		topic.setReplyTime(postTime);
		topic.setReplyTimes(0);
		topic.setTopType(0);
		topic.setCreationTime(postTime);

		Reply reply = new Reply();
		reply.setContent(model.getContent());
		reply.setPostTime(postTime);
		reply.setEditTime(postTime);
		reply.setFirstReply(true);

		topic.setTopicUser(user);
		topic.setReplyUser(user);
		topic.setForum(forum);

		reply.setTopic(topic);
		reply.setPostUser(user);

		Vote vote = new Vote();
		vote.setMulti(model.isMulti());
		for (String option : model.getOptions()) {
			vote.addOption(option);
		}

		topic.setVoteTopic(true);
		topic.setVote(vote);

		topic.save();
		reply.save();

		user.save();
		bank.save();

		return topic;
	}

	public Topic vote(VoteModel model) throws BBSException {
		Topic topic = getTopic(getForum(model.getForumId(), model.getUser()),
				model.getTopicId());

		topic.doVote(model.getOptions(), model.getUser());
		topic.save();

		return topic;
	}

	public void reply(ReplyModel model) throws BBSException {
		if (!model.getUser().getUserGroup().isAuthReply()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_REPLY);
		}

		if (model.getUserId() != 0) {
			try {
				Reply r = Reply.get(model.getReplyId());
				if (r.getPostUser().getId() != model.getUserId()) {
					throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
				}
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
			}
		}

		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setTopic(getTopic(model.getForum(), model.getTopicId()));
		model.setAreas(getAreas());

		if (model.getTopic().isLock()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_REPLY_LOCK_TOPIC);
		}
		// 网站关闭时间不能发帖,对普通用户来说
		if (ForumControlUtil.isForumClose()
				&& model.getUser().getGroupTypeId() == GroupManager.BASIC_GROUP) {
			throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
		}

		if (!StringUtils.isEmpty(model.getAction())) {
			try {
				Reply r = Reply.get(model.getReplyId());
				if(!model.isIfPhone()){
					model.setContent(getQuoteInfo(r));
				}
				else{
					model.setContent("[quote][B]以下是引用 [U]"
							+ r.getPostUser().getNick() + "[/U] 在"
							+ Util.formatDateTime(r.getPostTime()) + "的发言：[/B]\r\n"
							+ r.getContent() + "[/quote]\r\n\r\n ");
				}
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
			}
		}
	}
	
	private String getQuoteInfo(Reply r){
		String info="<div class='quote'><b>以下是引用 <u>"
			+ r.getPostUser().getNick() + "</u> 在"
			+ Util.formatDateTime(r.getPostTime()) + "的发言：</b><br />"
			+ r.getContentFilter() + "</div><br /><br />";
		return info;
	}

	public void modify(ModifyModel model) throws BBSException {
		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setAreas(getAreas());
		model.setTopic(getTopic(model.getForum(), model.getTopicId()));
		model.setReply(getReply(model.getTopic(), model.getReplyId()));
		model.setContent(model.getReply().getContentFilter());
		System.out.println("---->"+model.getContent());
		if (!model.getUser().getUserGroup().isAuthModify(model.getReply(),
				model.getUser())) {
			throw new BBSException(BBSExceptionMessage.CANNOT_MODIFY);
		}
	}

	public void modifyDo(ModifyModel model) throws BBSException {
		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setAreas(getAreas());
		Topic topic = getTopic(model.getForum(), model.getTopicId());
		model.setTopic(topic);
		Reply reply = getReply(model.getTopic(), model.getReplyId());
		model.setReply(reply);

		if (!model.getUser().getUserGroup()
				.isAuthModify(reply, model.getUser())) {
			throw new BBSException(BBSExceptionMessage.CANNOT_MODIFY);
		}

		if (reply.isFirstReply()) {
			topic.setTitle(model.getTitle().trim());
			topic.update();
		}
		reply.setEditTime(new Date());
		reply.setContent(model.getContent()+getEditInfo(model.getUser(), reply));
		reply.save();

	}
	
	/**
	 * 根据不同的编辑时间返回修改的记录信息
	 * @param user
	 * @param reply
	 * @return
	 */
	private String getEditInfo(User user,Reply reply){
		String editInfo="";
		if(ForumConfig.isBeforeNewEfitor(reply.getEditTime()))
			editInfo="\r\n\r\n [color=#999999]["+ user.getNick() + " 于 "+ Util.formatDateTime(Util.getCurrentTime())+ " 作最后修改][/color]";
		else
			editInfo="<br /><br /><font color='#999999'>["+ user.getNick() + " 于 "+ Util.formatDateTime(Util.getCurrentTime())+ " 作最后修改]</font>";
		return editInfo;
	}

	private Bank getBank() throws BBSException {
		try {
			return Bank.get();
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.BANK_NOT_FOUND);
		}
	}

	public Topic manage(ManageModel model) throws BBSException {
		String action = model.getAction();
		Forum forum = getForum(model.getForumId(), model.getUser());
		Topic topic = getTopic(forum, model.getTopicId());
		User user = model.getUser();
		ManageLog log = setLog(model);

		if (action.equals("setTop")) { // 版置顶该主题
			// if(topic.isTop()){
			// throw new
			// BBSException(BBSExceptionMessage.CANNOT_RESET_TOP_TOPIC);
			// }
			if (!model.getUser().getUserGroup().isAuthSetTop(topic, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_SET_TOPIC_TOP);
			}
			log.setTypeid(3);
			log.save();
			topic.setTop();
			topic.save();
			// 记录
		} else if (action.equals("setAreaTop")) {// 区置顶该主题
			// if(topic.isAreaTop()){
			// throw new
			// BBSException(BBSExceptionMessage.CANNOT_RESET_TOP_TOPIC);
			// }
			if (!model.getUser().getUserGroup().isAuthSetAreaTop(topic, user)) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_SET_TOPIC_AREA_TOP);
			}
			log.setTypeid(4);
			log.save();
			topic.setAreaTop();
			topic.save();
			// 记录
		} else if (action.equals("setAllTop")) { // 总置顶该主题
			// if(topic.isAllTop()){
			// throw new
			// BBSException(BBSExceptionMessage.CANNOT_RESET_TOP_TOPIC);
			// }
			if (!model.getUser().getUserGroup().isAuthSetAllTop(topic, user)) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_SET_TOPIC_ALL_TOP);
			}
			log.setTypeid(5);
			log.save();
			topic.setAllTop();
			topic.save();
			// 记录
		} else if (action.equals("unSetTop")) { // 取消置顶
			// if(!topic.isTop()){
			// throw new BBSException(BBSExceptionMessage.NOT_TOP_TOPIC);
			// }
			if (!model.getUser().getUserGroup().isAuthUnSetTop(topic, user)) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_UNSET_TOPIC_TOP);
			}
			log.setTypeid(9);
			log.save();
			topic.unSetTop();
			topic.save();
			// 记录
		} else if (action.equals("setGood")) { // 加为精华
			if (topic.isGoodTopic()) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_RESET_GOOD_TOPIC);
			}
			if (!model.getUser().getUserGroup().isAuthSetGood(topic, user)) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_SET_GOOD_TOPIC);
			}
			if (topic.isGoodTopic()) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_RESET_GOOD_TOPIC);
			}
			log.setTypeid(6);
			log.save();
			topic.setGoodTopic(true);
			topic.save();

			// 加经验、XDB……
			User author = topic.getTopicUser();
			Bank bank = getBank();
			author.gainGoodTopic(forum, bank);

			author.save();
			bank.save();
			// 记录
		} else if (action.equals("unSetGood")) { // 取消精华
			if (!topic.isGoodTopic()) {
				throw new BBSException(BBSExceptionMessage.NOT_GOOD_TOPIC);
			}
			if (!model.getUser().getUserGroup().isAuthSetGood(topic, user)) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_UNSET_GOOD_TOPIC);
			}
			log.setTypeid(10);
			log.save();
			if (topic.isGoodTopic()) {// 记录、减经验……
				topic.setGoodTopic(false);
				topic.save();

				User author = topic.getTopicUser();
				Bank bank = getBank();
				author.decreaseGoodTopic(forum, bank);

				author.save();
				bank.save();
				// 记录
			}
		} else if (action.equals("setLight")) { // 加亮主题
			if (topic.isLightTopic()) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_RESET_LIGHT_TOPIC);
			}
			if (!model.getUser().getUserGroup().isAuthSetLight(topic, user)) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_SET_LIGHT_TOPIC);
			}
			if (topic.isLightTopic()) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_RESET_LIGHT_TOPIC);
			}
			log.setTypeid(16);
			log.save();
			topic.setLightTopic(true);
			topic.save();

			// 加经验、XDB……
			// User author = topic.getTopicUser();
			// Bank bank = getBank();
			// author.gainGoodTopic(forum, bank);

			// author.save();
			// bank.save();
			// 记录
		} else if (action.equals("unSetLight")) { // 取消加亮
			if (!topic.isLightTopic()) {
				throw new BBSException(BBSExceptionMessage.NOT_LIGHT_TOPIC);
			}
			if (!model.getUser().getUserGroup().isAuthSetLight(topic, user)) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_UNSET_LIGHT_TOPIC);
			}
			log.setTypeid(17);
			log.save();

			if (topic.isLightTopic()) {// 记录、减经验……
				topic.setLightTopic(false);
				topic.save();

				// User author = topic.getTopicUser();
				// Bank bank = getBank();
				// author.decreaseGoodTopic(forum, bank);
				//
				// author.save();
				// bank.save();
				// 记录
			}
		} else if (action.equals("lock")) { // 锁定该主题
			if (topic.isLock()) {
				throw new BBSException(
						BBSExceptionMessage.CANNOT_RESET_LOCK_TOPIC);
			}
			if (!model.getUser().getUserGroup().isAuthLock(topic, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_LOCK_TOPIC);
			}
			log.setTypeid(7);
			log.save();

			topic.setLock(true);
			topic.save();

			// 从推荐列表中删除帖子记录
			deleteFromHotTopicList(topic);
		} else if (action.equals("unLock")) { // 解除锁定
			if (!topic.isLock()) {
				throw new BBSException(BBSExceptionMessage.NOT_LOCK_TOPIC);
			}
			if (!model.getUser().getUserGroup().isAuthLock(topic, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_UNLOCK_TOPIC);
			}
			log.setTypeid(14);
			log.save();

			topic.setLock(false);
			topic.save();
			// 记录
		} else if (action.equals("pub")) { // 推荐该主题
			if (!model.getUser().getUserGroup().isAuthPub(topic, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_PUB_TOPIC);
			}
			log.setTypeid(8);
			log.save();

			topic.setPubType(model.getPubId());
			topic.save();
			// 增加到推荐列表
			addHotTopicList(topic);
		} else if (action.equals("unPub")) { // 取消推荐
			if (!model.getUser().getUserGroup().isAuthPub(topic, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_UNPUB_TOPIC);
			}
			log.setTypeid(11);
			log.save();

			topic.setPubType(0);
			topic.save();

			// 从推荐列表中删除帖子记录
			deleteFromHotTopicList(topic);
		}/*
		 * else if (action.equals("repair")) { //修复该主题 if
		 * (!model.getUser().getUserGroup().canRepair(topic, user)) { throw new
		 * BBSException(BBSExceptionMessage.CANNOT_CREATE_REPLY); } }
		 */
		else if (action.equals("delete")) { // 删除主题
			if (!model.getUser().getUserGroup().isAuthDelete(topic, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_DELETE_TOPIC);
			}
			if (model.getUser().getGroupTypeId() != 1) {
				log.setTypeid(1);
				log.save();
			}

			topic.setInvalid(true);
			topic.save();

			// 减经验、XDB……
			User author = topic.getTopicUser();
			Bank bank = getBank();
			author.deleteTopic(forum, bank);

			author.save();
			bank.save();

			// 从推荐列表中删除帖子记录
			deleteFromHotTopicList(topic);

		} else if (action.equals("move")) { // 转移主题
			if (!model.getUser().getUserGroup().isAuthMoveTopic(topic, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_MOVE_TOPIC);
			}
			log.setTypeid(15);
			log.save();

			topic.setForum(getForum(model.getToForumId(), user));
			topic.save();
			// 从推荐列表中删除帖子记录
			deleteFromHotTopicList(topic);
		} else if (action.equals("delReply")) { // 删除回复
			Reply reply = getReply(topic, model.getReplyId());
			if (!model.getUser().getUserGroup().isAuthDelReply(reply, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_DELETE_REPLY);
			}
			if (model.getUser().getGroupTypeId() != 1) {
				log.setTypeid(13);
				log.save();
			}

			reply.setInvalid(true);
			reply.save();

			// 减经验、XDB……
			User author = reply.getPostUser();
			Bank bank = getBank();
			author.deleteReply(bank);

			author.save();
			bank.save();

			topic.addReplyTimes(-1);
			topic.save();
		} else if (action.equals("screen")) { // 屏蔽发言
			Reply reply = getReply(topic, model.getReplyId());
			if (!model.getUser().getUserGroup().isAuthScreen(reply, user)) {
				throw new BBSException(BBSExceptionMessage.CANNOT_SCREEN_REPLY);
			}
			log.setTypeid(12);
			log.save();

			reply.setScreen(true);
			reply.save();
		} else if (action.equals("setDecoration")){
			topic.setActivityType("serach_treasuer");
		//	topic.setActivityDecoration("<img border='0' title='雨无声十周年寻宝贴！' src='/images/state/treasure.gif' />");
			topic.save();
		} else if (action.equals("cancel_decoration")) {
			topic.setActivityType("");
			topic.save();
		} else if (action.equals("activity_2012")) {
			topic.setActivityType(topic.getActivityType() + "#!");
		} else if (action.equals("cancel_activity_2012")) {
			topic.setActivityType(topic.getActivityType().split("#")[0]);
		}
		
		//更新首页热门帖子的缓存
		BBSCache.buildGoodTopicCache();
		BBSCache.buildHotTopicCache();
		//
		return topic;
	}

	private void addHotTopicList(Topic topic) {
		HotTopic hotTopic = new HotTopic();
		hotTopic.setPubType(topic.getPubType());
		hotTopic.setTopic(topic);
		hotTopic.setForum(topic.getForum());
		hotTopic.setTitle(topic.getTitle());
		hotTopic.setCreationTime(topic.getCreationTime());
		hotTopic.save();
	}

	private void deleteFromHotTopicList(Topic topic) {
		try {
			HotTopic hotTopic = HotTopic.getByTopicId(topic.getId());
			hotTopic.delete();
		} catch (ObjectNotFoundException e) {
			System.out.println("error! 此主题不在热门话题中");
		}
	}

	private ManageLog setLog(ManageModel model) {
		ManageLog log = new ManageLog();
		log.setDt(new Date());
		try {
			log.setForum(Forum.get(model.getForumId()));
			log.setTopic(Topic.get(model.getTopicId()));
			log.setUser(model.getUser());
			log.setReply(Reply.get(model.getReplyId()));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return log;
	}

	public void search(SearchModel model) throws BBSException {

		switch (model.getType()) {
		case 0: // 按标题
			if (model.getForumId() == 0) {
				model.getPagination().setRecordSize(
						Topic.getTopicsSizeByTitle(model.getKeywords()));
				model.setTopics(Topic.getTopicsByTitle(model.getKeywords(),
						model.getPagination()));
			} else {
				try {
					Forum forum = Forum.get(model.getForumId());
					model.getPagination().setRecordSize(
							Topic.getTopicsSize(forum, model.getKeywords()));
					model.setTopics(Topic.getTopics(forum, model
							.getPagination(), model.getKeywords()));
				} catch (ObjectNotFoundException e) {
					throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
				}
			}
			break;
		case 1: // 按作者
			User user;
			try {
				// user = User.get(11);
				user = User.getByNick(model.getKeywords());
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.USER_NOT_FOUND);
				// return;
			}
			if (model.getForumId() == 0) {
				model.getPagination().setRecordSize(Topic.getTopicsSize(user));
				model.setTopics(Topic.getTopics(user, model.getPagination()));
			} else {
				Forum forum;
				try {
					forum = Forum.get(model.getForumId());
					model.getPagination().setRecordSize(
							Topic.getTopicsSize(forum, user));
					model.setTopics(Topic.getTopics(forum, model
							.getPagination(), user));
				} catch (ObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		case 2: // 按回复人
			User replyUser;
			try {
				// replyUser = User.get(model.getUserId());
				replyUser = User.getByNick(model.getKeywords());
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.USER_NOT_FOUND);
			}
			if (model.getForumId() == 0) {
				model.getPagination().setRecordSize(
						Topic.getTopicsSizeByReplyUser(replyUser));
				List<Topic> topics = Topic.getTopicsByReplyUserAndByReplyTime(
						replyUser, model.getPagination());
				model.setTopics(topics);
			} else {
				Forum forum;
				try {
					forum = Forum.get(model.getForumId());
					model.getPagination().setRecordSize(
							Topic.getTopicsSizeByReplyUser(replyUser, forum));
					List<Topic> topics = Topic
							.getTopicsByReplyUserAndByReplyTime(replyUser,
									forum, model.getPagination());
					model.setTopics(topics);
				} catch (ObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		case 3: // 按回复时间
			model.setKeywords("按回复时间");
			if (model.getForumId() == 0) {
				model.getPagination().setRecordSize(Topic.getTopicsSize());
				model.setTopics(Topic.getTopics(model.getPagination(), 1));
			} else {
				try {
					Forum forum = Forum.get(model.getForumId());
					model.getPagination().setRecordSize(
							Topic.getTopicsSize(forum));
					model.setTopics(Topic.getTopics(forum, model
							.getPagination(), 1));
				} catch (ObjectNotFoundException e) {
					throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
				}
			}
			break;
		case 4: // 按发表时间
			model.setKeywords("按发表时间");
			if (model.getForumId() == 0) {
				model.getPagination().setRecordSize(Topic.getTopicsSize());
				model.setTopics(Topic.getTopics(model.getPagination(), 0));
			} else {
				try {
					Forum forum = Forum.get(model.getForumId());
					model.getPagination().setRecordSize(
							Topic.getTopicsSize(forum));
					model.setTopics(Topic.getTopics(forum, model
							.getPagination(), 0));
				} catch (ObjectNotFoundException e) {
					throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
				}
			}
			break;
		case 5: // 全文检索
			searchByContent(model);
			break;
		case 6: // 按回复人
			User user1;
			try {
				// user1 = User.get(model.getUserId());
				user1 = User.getByNick(model.getKeywords());
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.USER_NOT_FOUND);
			}
			if (model.getForumId() == 0) {
				model.getPagination().setRecordSize(
						Topic.getTopicsSizeByReplyUser(user1));
				List<Topic> topics = Topic
						.getTopicsByReplyUserAndByCreationTime(user1, model
								.getPagination());
				model.setTopics(topics);
				System.out.print(topics.size());
			} else {
				Forum forum;
				try {
					forum = Forum.get(model.getForumId());
					model.getPagination().setRecordSize(
							Topic.getTopicsSizeByReplyUser(user1, forum));
					List<Topic> topics = Topic
							.getTopicsByReplyUserAndByCreationTime(user1,
									forum, model.getPagination());
					model.setTopics(topics);
					System.out.print(topics.size());
				} catch (ObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			throw new BBSException("操作类型错误！");
		}
	}
	
	/**
	 * 全文搜索！！！！！！<br />
	 * <br />
	 * add by:集成显卡<br />
	 * @param model
	 * @throws BBSException
	 */
	public void searchByContent(SearchModel model) throws BBSException{
		if (model.getForumId() == 0) {
			model.getPagination().setRecordSize(Reply.getSizeByContent(model.getKeywords()));
			System.out.println(model.getPagination().getRecordSize()+" ---------------------------------个结果");
			
			model.setTopics(Reply.getTopicsByContent(model.getKeywords(), model.getPagination()));
		} else {
			try {
				Forum forum = Forum.get(model.getForumId());
				model.getPagination().setRecordSize(
						Topic.getTopicsSize(forum, model.getKeywords()));
				model.setTopics(Topic.getTopics(forum, model
						.getPagination(), model.getKeywords()));
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
			}
		}
	}
	
	public void createSmallNews(CreateSmallNewsModel model) throws BBSException {
		// if(model.getUser().getExp()<200){
		// throw new BBSException(BBSExceptionMessage.NEED_EXP);
		// }
		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setAreas(getAreas());
	}

	public void createSmallNewsDo(CreateSmallNewsModel model)
			throws BBSException {
		if (model.getUser().getExp() < 240) {
			throw new BBSException(BBSExceptionMessage.NEED_EXP);
		}

		User user = model.getUser();

		SmallNews small = SmallNews.getSmallNewsListByUser(user.getId());
		if (small != null)
			if (small.getCreateTime().getTime() + 86400000 * 2 > new Date()
					.getTime())
				throw new BBSException(BBSExceptionMessage.SMALL_NEWS_NOT_TIME);

		SmallNews smallNews = new SmallNews();
		smallNews.setTitle(model.getTitle());
		smallNews.setContent(model.getContent());
		smallNews.setCreateTime(new Date());
		smallNews.setUser(user);
		smallNews.setForum(getForum(model.getForumId(), user));

		Bank bank = getBank();
		user.newSmallNews(bank);
		bank.save();
		user.save();
		smallNews.save();
	}

	public void getSmallNewsList(SmallNewsModel model) throws BBSException {
		// 应网管部要求，取消把小字报按版块分类
		// Forum forum = getForum(model.getForumId(), model.getUser());
		// model.getPagination().setRecordSize(SmallNews.getSmallNewsSize(forum));
		// model.setSmallNewsList(SmallNews.getSmallNewsList(forum,model.getPagination()));
		model.getPagination().setRecordSize(SmallNews.getSmallNewsSize());
		model.setSmallNewsList(SmallNews
				.getSmallNewsList(model.getPagination()));
	}
	
	public SmallNews smallNews(int id) throws BBSException {
		try {
			SmallNews smallNews = SmallNews.get(id);
			smallNews.addhit(1);
			smallNews.save();
			return smallNews;
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.SMALL_NEWS_NOT_EXIST);
		}
	}

	public void delSmallNews(SmallNewsModel model) throws BBSException {
		User user = model.getUser();
		SmallNews smallNews;
		try {
			smallNews = SmallNews.get(model.getId());
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.SMALL_NEWS_NOT_EXIST);
		}
		if (!model.getUser().getUserGroup().isAuthDelSmallNews(smallNews, user)) {
			throw new BBSException(BBSExceptionMessage.CANNOT_DELETE_SMALL_NEWS);
		}
		smallNews.setInvalid(true);
		smallNews.save();
	}

	public void BoardState(BoardStateModel model) throws BBSException {
		model.setArea(getArea(model));
		List<BoardView> names = new ArrayList<BoardView>();
		if (model.getAction().equals("nowOnline")) {
			model.setDescription(Constants.ONLINE_GD);
			List<Forum> forums = model.getArea().getForums();
			int totalOnline = 0;
			int userOnline = 0;
			for (Forum forum : forums) {
				BoardView boardView = new BoardView();
				boardView.setId(forum.getId());
				boardView.setName(forum.getName());
				int num = OnlineUser.getNumberOfForum(forum.getId());
				int usernum = OnlineUser.getUserNumberOfForum(forum.getId());
				totalOnline = totalOnline + num;
				userOnline = userOnline + usernum;
				boardView.setCounter(num);
				names.add(boardView);
			}
			model.setTotalNum(totalOnline);
			model.setAreaUserOnline(userOnline);
			model.setAreaVisitOnline(totalOnline - userOnline);
		} else if (model.getAction().equals("todayTopic")) {
			model.setDescription(Constants.TODAY_TOPIC_GD);
			List<Forum> forums = model.getArea().getForums();
			Date date = Util.getUitDate();
			int totalNum = 0;
			for (Forum forum : forums) {
				BoardView boardView = new BoardView();
				boardView.setId(forum.getId());
				boardView.setName(forum.getName());
				int num = Topic.getTopicsSize(forum, date);
				boardView.setCounter(num);
				totalNum = totalNum + num;
				names.add(boardView);
			}

			model.setTotalNum(totalNum);
		} else if (model.getAction().equals("totalTopic")) {
			model.setDescription(Constants.TOTAL_TOPIC_GD);
			List<Forum> forums = model.getArea().getForums();
			int totalNum = 0;
			for (Forum forum : forums) {
				BoardView boardView = new BoardView();
				boardView.setId(forum.getId());
				boardView.setName(forum.getName());
				int num = Topic.getTopicsSize(forum);
				boardView.setCounter(num);
				totalNum = totalNum + num;
				names.add(boardView);
			}
			model.setTotalNum(totalNum);
		} else if (model.getAction().equals("totalReply")) {
			model.setDescription(Constants.TOTAL_REPLY_GD);
			List<Forum> forums = model.getArea().getForums();
			int totalNum = 0;
			for (Forum forum : forums) {
				BoardView boardView = new BoardView();
				boardView.setId(forum.getId());
				boardView.setName(forum.getName());
				int num = Reply.getReplysSize(forum);
				boardView.setCounter(num);
				totalNum = totalNum + num;
				names.add(boardView);
			}
			model.setTotalNum(totalNum);
		} else if (model.getAction().equals("groupOnline")) {
			model.setDescription(Constants.GROUP_ONLINE_GD);
			List<BasicGroup> groups = BasicGroup.getBasicGroups();
			for (BasicGroup group : groups) {
				BoardView boardView = new BoardView();
				boardView.setId(group.getId());
				boardView.setName(group.getGroupName());
				boardView.setCounter(0);
				names.add(boardView);
			}
			List<OnlineUser> onlineUsers = OnlineUser.getOnlineUsers();
			for (OnlineUser onlineUser : onlineUsers) {
				User user = getUser(onlineUser.getUserId());
				for (BoardView boardView : names) {
					if (boardView.getId() == user.getGroupId()) {
						int num = boardView.getCounter() + 1;
						boardView.setCounter(num);
					}
				}
			}
			model.setAreaUserOnline(onlineUsers.size());
			model.setTotalNum(OnlineUser.getTotal());
			model
					.setAreaVisitOnline(OnlineUser.getTotal()
							- onlineUsers.size());
		}
		model.setBoards(names);

	}

	public void queryHotTopics() throws Exception {
		HotTopic.deleteAll();
		List<Topic> list = Topic.getPubTopics(1);
		HotTopic hotTopic;
		Topic topic = null;
		for (int i = 0; i < list.size(); i++) {
			topic = list.get(i);
			hotTopic = new HotTopic();
			hotTopic.setForum(topic.getForum());
			hotTopic.setCreationTime(topic.getCreationTime());
			hotTopic.setPubType(topic.getPubType());
			hotTopic.setTopic(topic);
			hotTopic.setTitle(topic.getTitle());
			hotTopic.save();
		}
		list = Topic.getPubTopics(2);
		for (int i = 0; i < list.size(); i++) {
			topic = list.get(i);
			hotTopic = new HotTopic();
			hotTopic.setForum(topic.getForum());
			hotTopic.setCreationTime(topic.getCreationTime());
			hotTopic.setPubType(topic.getPubType());
			hotTopic.setTopic(topic);
			hotTopic.setTitle(topic.getTitle());
			hotTopic.save();
		}
	}

	public Area getArea(BoardStateModel model) throws BBSException {
		Area area;
		try {
			area = Area.get(model.getAreaID());
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		return area;
	}

	private User getUser(int id) {
		User user = new User();
		try {
			user = User.get(id);
		} catch (ObjectNotFoundException e) {

		}
		return user;
	}

	// -------------add by 叨叨雨 2009-12-02
	public void saveDraftBox(CreateTopicModel model) throws BBSException {

		if (!model.getUser().getUserGroup().isAuthCreateTopic()) {
			throw new BBSException(BBSExceptionMessage.SAVE_CREATE_TOPIC);
		}

		try {
			if (DraftBox.getDraftBoxes(model.getUser(), model.getPagintion())
					.size() > 20) {
				throw new BBSException(BBSExceptionMessage.OUTOF_SAVE_DRAFTBOX);
			}

		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setAreas(getAreas());

		Date savetime = new Date();
		DraftBox db = new DraftBox();
		db.setFace(model.getFace());
		db.setSelect(model.getSelect());
		db.setContent(model.getContent());
		db.setTitle(model.getTitle());
		db.setUser(model.getUser());
		db.setForum(model.getForum());
		db.setIspub(0);
		db.setSavetime(savetime);
		db.setZt(model.getZt());
		db.save();

	}

	public void saveAgainDraftBox(CreateTopicModel model, int n)
			throws BBSException {

		if (!model.getUser().getUserGroup().isAuthCreateTopic()) {

			throw new BBSException(BBSExceptionMessage.SAVE_CREATE_TOPIC);
		}
		if (n == 1) {
			if (ForumControlUtil.isForumClose()) {
				throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
			}
		}
		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setAreas(getAreas());

		Date savetime = new Date();
		DraftBox db = new DraftBox();
		db.setId(model.getId());
		db.setFace(model.getFace());
		db.setSelect(model.getSelect());
		db.setContent(model.getContent());
		db.setTitle(model.getTitle());
		db.setUser(model.getUser());
		db.setForum(model.getForum());
		db.setIspub(n);
		db.setSavetime(savetime);
		db.setZt(model.getZt());
		db.update();
		
		this.createTopicDo(model);
		
	}

	public void getDraftBoxes(CreateTopicModel model) throws BBSException {
		User user = model.getUser();
		try {
			model.setDraftBoxes(DraftBox.getDraftBoxes(user, model
					.getPagintion()));
			model.setSize(DraftBox.getDraftBoxes(user, model.getPagintion())
					.size());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

	}

	public CreateTopicModel getDraftBoxModel(int draftid) throws Exception {
		CreateTopicModel model = new CreateTopicModel();
		DraftBox db = DraftBox.get(draftid);
		//User user = db.getUser();
		Forum forum = db.getForum();
		model.setContent(db.getContent());
		model.setDraftBox(db);
		model.setFace(db.getFace());
		model.setSelect(db.getSelect());
		model.setTitle(db.getTitle());
		model.setForumId(forum.getId());
		model.setForum(forum);
		model.setZt(db.getZt());
		model.setId(db.getId());
		return model;

	}

	public void delDraftBoxes(CreateTopicModel model) throws BBSException {
		//User user = model.getUser();
		int[] dbid = model.getDbid();
		for (int i = 0; i < dbid.length; i++) {
			try {
				DraftBox.delDraftBox(dbid[i]);
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	// 清掉效果过期的物品
	public void checkTopicSafer() throws ObjectNotFoundException {
		List<ItemWork> list = ItemWork.getItemWorksOverdue(2);
		for (int i = 0; i < list.size(); i++) {
			Topic.get(list.get(i).getObId()).unSetTop();
			list.get(i).delete();
		}
	}

	public void area(AreaModel model) {
		try {
			model.setArea(Area.get(model.getArea().getId()));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获取论坛的开关时间
	 */
	public void getFourmTime(ForumTimeModel model) throws ObjectNotFoundException {
		
		model.setTimeParam(Param.getByCode(model.getPara_code()));
	}
	public void modifyFourmTime(ForumTimeModel model)	throws Exception {
		Param p=model.getTimeParam();
		p.update();
	}

	public void replyFast(ReplyModel model) throws BBSException {
		if (!model.getUser().getUserGroup().isAuthReply()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_REPLY);
		}
		
		/*
		if (model.getUserId() != 0) {
			try {
				Reply r = Reply.get(model.getReplyId());
				if (r.getPostUser().getId() != model.getUserId()) {
					throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
				}
			} catch (ObjectNotFoundException e) {
				throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
			}
		}*/
		model.setForum(getForum(model.getForumId(), model.getUser()));
		model.setTopic(getTopic(model.getForum(), model.getTopicId()));
		
		if (model.getTopic().isLock()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_REPLY_LOCK_TOPIC);
		}
		// 网站关闭时间不能发帖,对普通用户来说
		if (ForumControlUtil.isForumClose()
				&& model.getUser().getGroupTypeId() == GroupManager.BASIC_GROUP) {
			throw new BBSException(BBSExceptionMessage.FORUM_IS_CLOSE);
		}

		try {
			Reply r = Reply.get(model.getReplyId());
			model.setContent(getQuoteInfo(r));
			/*4.0 used
			model.setContent("[quote][B] 回复 [U]"
					+ r.getPostUser().getNick() + "[/U] 在"
					+ Util.formatDateTime(r.getPostTime()) + "的发言：[/B]\r\n"
					+ r.getContent() + "[/quote]\r\n\r\n ");
			*/
			
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
	}

	public Topic replyFastDo(ReplyModel model) throws Exception {
		model.setTopic(this.createReply(model));
		//上面的方法已经发送了短信息
		return null;
	}
	
	private void sendReplyMessage(ReplyModel model){
		try{
			//发短信息给被引用的作者
			//使用id=0的论坛系统发送
			List<User> receivers=new ArrayList<User>();
			User receiver=Reply.get(model.getReplyId()).getPostUser();
			if(receiver.getReplyMessage()==0){
				receivers.add(receiver);
				String content=receiver.getNick()+"，您好，[url=/user/user_info.yws?id="+model.getUser().getId()+"]"
				 	+model.getUser().getNick()+"[/url]&nbsp;于"+TimerUtils.getDateByFormat(new Date(), "yyyy-MM-dd hh:mm")
				 	+" &nbsp;在&nbsp;[url=/topic.yws?forumId="+model.getForumId()+"&topicId="+model.getTopicId()+"&page="+model.getPage()+"#floor"+model.getReply().getId()+"]"
				 	+model.getTopic().getTitle()+"[/url]&nbsp;中回复了您。\r\n\r\n\r\n[此提示信息可以在[url=/user/upgrade.yws]个人服务区[/url]中设置是否接收]";
				Message.sendMessage("回复通知", content, 1, receivers, User.get(0), receiver.getNick());
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
	}

	/**
	 *----------------------------------------------------------------
	 *砸蛋相关
	 *by  集成显卡  2011.10.3
	 *----------------------------------------------------------------
	 */
	public void answerLucky(LuckyActionModel model) throws BBSException {
		if(model.getAnswer()==null||model.getAnswer().length()==0)
			throw new BBSException("没有上传任何答案！");
		
		fillLucky(model);
		canSubmitLucky(model);
		String answers[]=model.getAnswer().split("-");
		int shooting=0;
		for(int i=0;i<answers.length;i=i+2){
			try{
				LuckySubject ls=LuckySubject.get(Integer.valueOf(answers[i]));
				if(ls.getAnswer().equalsIgnoreCase(answers[i+1]))
					shooting++;
			}catch(ObjectNotFoundException kk){}
		}
		model.setRightCount(shooting);
		model.setMark(shooting*100/(answers.length/2));
		
		//如果没有达到帖子的最低题目数，就 报错
		if(shooting>=model.getLucky().getMinCount()){
			try{
				switch(shooting){
				case 6:
					model.setMarkLevel(1);
					break;
				case 7:
					model.setMarkLevel(2);
					break;
				case 8:
				case 9:
					model.setMarkLevel(3);
					break;
				case 10:
					model.setMarkLevel(-1);
					break;
				}
				createLuckyLog(model);
				createLuckyMessage(model);
			}catch(Exception e){
				e.printStackTrace();
				throw new BBSException(e.getMessage());
			}
		}else{
			throw new BBSException("很遗憾，您没有及格....<br /><br />"
					+"您答对了 "+shooting+" 题，没有达到要求的 "+model.getLucky().getMinCount()+" 题，请下次再试");
		}
	}
	private void fillLucky(LuckyActionModel model) throws BBSException{
		Lucky lucky=null;
		try{
			lucky=Lucky.get(model.getLid());
		}catch(ObjectNotFoundException e){
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}
		model.setLucky(lucky);
	}
	
	private void createLuckyLog(LuckyActionModel model)throws Exception{
		LuckyLog ll=new LuckyLog();
		ll.setLucky(model.getLucky());
		//为-1则是砸蛋，需要用户转到另外一个页面
		if(model.getMarkLevel()==-1){
			ll.setResult(0);
			ll.setBest(true);
			ll.setUsed(false);
		}else{
			ll.setResult(model.getMarkLevel());
			ll.setUsed(true);
			ll.setCodeNumber(Lucky.getCodeNumber());
			model.setMessage(model.getMessage()+"<br />还有以下礼品："+ll.getGiftInfo()+"[领奖码:"+ll.getCodeNumber()+"]");
		}
		ll.setMark(model.getMark());
		ll.setCreateTime(new Date());
		ll.setUser(model.getUser());
		ll.save();
		model.setLuckyLog(ll);
		//如果是砸蛋机会，那么肯定有一个次高级的礼品
		if(model.getMarkLevel()==-1){
			model.setMessage(model.getMessage()+"<br />获得了砸蛋的机会：<br />[url=/gift_lucky.yws?logId="+model.getLuckyLog().getId()+"]马上去砸蛋[/url]<br />");
			model.setMarkLevel(3);
			createLuckyLog(model);
		}
	}
	
	private void createLuckyMessage(LuckyActionModel model) throws Exception{
		List<User> receivers=new ArrayList<User>();
		User receiver=model.getUser();
		receivers.add(receiver);
		Lucky lucky=model.getLucky();
		String info=model.getMessage().replaceAll("<br />", "\r\n");
		String content=receiver.getNick()+"，您好，您于"+TimerUtils.getDateByFormat(new Date(), "yyyy-MM-dd hh:mm")+" &nbsp;在&nbsp;[url=/topic.yws?forumId="+lucky.getTopic().getForum().getId()+"&topicId="+lucky.getTopic().getId()+"]"
		 	+lucky.getTopic().getTitle()+"[/url]&nbsp;中"+info+"\r\n\r\n"+lucky.getInformation();
		Message.sendMessage("中加学院十周年幸运帖", content, 1, receivers, User.get(0), receiver.getNick());
	}
	
	/**
	 * 是否可以提交答案，如果幸运帖不允许同一个用户重复上传，就会报错
	 * @param model
	 * @throws BBSException
	 */
	public void canSubmitLucky(LuckyActionModel model) throws BBSException{
		/*
		List<LuckyLog> logList=LuckyLog.getLogByUserAndUse(model.getUser(),false);
		if(logList!=null&&logList.size()>0){
			LuckyLog log=logList.get(0);
			throw new BBSException("您已经拥有一次砸蛋的机会了，此幸运帖不允许重复提交，请先去砸蛋吧。<br /><a href='/gift_lucky.yws?logId="+log.getId()+"'>现在去砸蛋</a>");
		}*/
		int size=LuckyLog.sizeOfUserWinGift(model.getUser(), model.getLid());
		if(size>0){
			throw new BBSException("您已经获取过礼品了，这个幸运帖不允许同一个用户重复获取礼品");
		}
	}
	
	//砸蛋页面申请，砸蛋处理都是在这里
	public void luckyDo(LuckyActionModel model) throws Exception {
		checkUser(model);
		fillLucky(model);
		if(!Lucky.canJoin(model.getLid())){
			throw new BBSException("sorry！此幸运帖已经过期或者中奖人数已经超过最大值....<br /><br />目前已有 "+model.getLucky().getCount()+" 人中奖，"+model.getLucky().getCountInfo());
		}
		if(model.isChoice()){
			model.setAreas(getAreas());
		}else{
			LuckyLog lLog=model.getLuckyLog();
			//随机获取礼物
			int result=getLuckyResult(model.getLucky().getProbability(), model.getLucky().getGifts().size());
			if(result>=0){
				LuckyGift lg=LuckyGift.get(6);//获得的信息,中加为的金奖为6
				lLog.setCodeNumber(Lucky.getCodeNumber());
				String message="砸出了幸运彩蛋并获得金奖："+Lucky.singleGiftsInfo(lg)+"[领奖码:"+lLog.getCodeNumber()+"]";
				model.setMessage(message);
				lLog.setResult(lg.getId());
				model.getLucky().addCount(1);
				model.getLucky().save();
				lLog.setResult(6);//中加10周年
				lLog.save();
				createLuckyMessage(model);
				//postGift(model,lg);  //10-10 以后使用的论坛功能
			}else{
				model.setMark(result);//将中奖结果放到 model
				model.setMessage("很遗憾，这次砸到了一个咸鸭蛋.....");
			}
			lLog.setUsed(true);
			lLog.save();
			System.out.println("中奖了！！！！！！！！"+"  结果："+result);
		}
	}
	
	/**检查登录的用户跟中奖记录用户是否一致*/
	private void checkUser(LuckyActionModel model) throws Exception {
		LuckyLog log=LuckyLog.get(model.getLogId());
		if(model.getUser().getId()!=log.getUser().getId())
			throw new BBSException("登录的用户与记录用户不一致！！");
		if(log.isUsed())
			throw new BBSException("对不起，您的砸蛋机会已经无效！可能是已经使用，或者已经过期");
		model.setLid(log.getLucky().getId());
		//当用户已经获取了礼品，同时幸运帖不允许重复得礼品。。。。
		int size=LuckyLog.sizeOfUserWinBestGift(model.getUser(), model.getLid());
		if(size>0&&!log.getLucky().isMultiple())
			throw new BBSException("对不起，您已经获取了此幸运帖的最高级礼品，该幸运帖不允许同一用户重复获取礼品");
		
		model.setLuckyLog(log);
	}
	
	/**
	 * 获取随机抽奖的结果，如果返回-1为没有中奖<br />
	 * 中奖后，返回随机到的奖品编号
	 * @param rate  中奖率
	 * @param max  奖品最大编号
	 * @return
	 */
	private int getLuckyResult(float rate,int max){
		int result=-1;
		Random random=new Random();
		float rate1=random.nextFloat();
		float one=(rate1*System.currentTimeMillis());
		float two=(rate*System.currentTimeMillis());
		System.out.println(one+":"+rate1+"   ----------------------  "+two+":"+rate);
		if(one<two){
			result=random.nextInt(max);
		}
		return result;
	}
	
	/**
	 * 分发礼品
	 * @param lg
	 */
	@SuppressWarnings("unused")
	private void postGift(LuckyActionModel model,LuckyGift lg) throws Exception{
		User postUser=model.getLucky().getTopic().getTopicUser();//
		User receiveUser=model.getUser();//接收的用户
		System.out.println("分发了礼品------"+postUser.getId()+"   -->"+receiveUser.getId());
		switch(lg.getType()){
		case LuckyGift.REALITY_GIFT:
			break;
		case LuckyGift.FORUM_ITEM:
			int itemId=Integer.valueOf(lg.getValue());
			try{
				//Item item=Item.get(itemId);
				//List<ItemLine> il=ItemLine.getItemLinesByUser(postUser, itemId);//获取 发帖用户所拥有的 itemId 的物品
				//ItemLine itemLine=il.get(0);
				ItemLine itemLine=new ItemLine();
				itemLine.setItem(Item.get(itemId));
				itemLine.setUser(receiveUser);
				itemLine.setMakerId(0);
				itemLine.save();
				model.getLuckyLog().setGifted(true);
			}catch(Exception e){
				System.out.println("发帖用户没有相应的物品。。。。");
				//e.printStackTrace();
				//因为发帖的用户没有对应的 物品，那么，将跑出一个异常，用户的 LuckyLog 将不会有变动
				throw new BBSException("创建道具时出错，可能是物品编号["+itemId+"]已经不存在");
			}
			break;
		//如果是西大币，将从 发帖用户中扣除相应的西大币
		case LuckyGift.FORUM_MONEY:
			Integer number=Integer.valueOf(lg.getValue());
			receiveUser.addMoney(number);
			receiveUser.save();
			if(LuckyConfig.getConfig("createMoney").equals("1")){
				postUser.addMoney(0-number);
				postUser.save();
			}
			model.getLuckyLog().setGifted(true);
			break;
		default:
		}
	}
	
	public void giftLucky(LuckyActionModel model) throws Exception {
	}
	public void viewLucky(LuckyActionModel model) throws Exception {
		Lucky lucky=Lucky.get(model.getLid());
		model.setLucky(lucky);
		model.setLuckyLogs(LuckyLog.getLogByLucky(lucky));
	}
	
	public void createLucky(LuckyTopicModel model) throws Exception {
		canCreateTopic(model);
	}
	public void createLuckyDo(LuckyTopicModel model) throws Exception {
	}
	public void editLucky(LuckyTopicModel model) throws Exception {
	}

	/**
	 *----------------------------------------------------------------
	 *end  砸蛋相关
	 *----------------------------------------------------------------
	 */

}
