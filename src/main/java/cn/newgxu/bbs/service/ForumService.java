package cn.newgxu.bbs.service;

import java.util.List;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.HotTopic;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.web.model.AreaModel;
import cn.newgxu.bbs.web.model.BoardStateModel;
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
public interface ForumService {

	public void createArea(AreaManageModel model) throws BBSException;

	public List<Topic> getTopics_Normal(Forum forum, Pagination p);

	public List<Topic> getTopics_Good(Forum forum, Pagination p);

	public List<Area> getAreas();

	public List<Area> getAllAreas();

	public Topic getTopic(Forum forum, int topicId) throws BBSException;

	public void createForum(ForumManageModel model) throws BBSException;

	public List<Reply> getReplies_Normal(Topic topic, Pagination pagination);

	public void forum(ForumModel model) throws BBSException;

	public Topic createTopicDo(CreateTopicModel model) throws BBSException,
			ValidationException;

	public void topic(TopicModel model) throws BBSException;

	public Topic createReply(ReplyModel model) throws Exception;

	public void createTopic(CreateTopicModel model) throws BBSException;

	public void createVote(CreateVoteModel model) throws BBSException;

	public Topic createVoteDo(CreateVoteModel model) throws BBSException,
			ValidationException;

	public Topic vote(VoteModel model) throws BBSException;

	public void reply(ReplyModel model) throws BBSException;
	
	public void modify(ModifyModel model) throws BBSException;

	public void modifyDo(ModifyModel model) throws BBSException,
			ValidationException;

	public Topic manage(ManageModel model) throws BBSException;

	public void search(SearchModel model) throws BBSException,
			ValidationException;

	public void createSmallNews(CreateSmallNewsModel model) throws BBSException;

	public void createSmallNewsDo(CreateSmallNewsModel model)
			throws BBSException, ValidationException;

	public SmallNews smallNews(int id) throws BBSException;

	public void getSmallNewsList(SmallNewsModel model) throws BBSException;

	public void delSmallNews(SmallNewsModel model) throws BBSException;

	public List<HotTopic> getTopics(int pubType, int num);

	public Area getArea(int areaId) throws BBSException;

	public void editArea(AreaManageModel model) throws BBSException;

	public Forum getForum(int forumId) throws BBSException;

	public void editForum(ForumManageModel model) throws BBSException;

	public Area getArea(BoardStateModel model) throws BBSException;

	public void BoardState(BoardStateModel model) throws BBSException;

	public void queryHotTopics() throws Exception;

	public void checkTopicSafer() throws Exception;

	/**
	 * add by 叨叨雨
	 * 
	 */
	public void saveDraftBox(CreateTopicModel model) throws BBSException;

	public void getDraftBoxes(CreateTopicModel model) throws BBSException;

	public CreateTopicModel getDraftBoxModel(int draftid) throws Exception;

	public void saveAgainDraftBox(CreateTopicModel model, int n)
			throws BBSException;

	public void delDraftBoxes(CreateTopicModel model) throws BBSException;

	public void area(AreaModel model);

	/**
	 * add by 集成显卡
	 * 快速回复
	 */
	public void replyFast(ReplyModel model) throws BBSException;
	public Topic replyFastDo(ReplyModel model) throws Exception;
	/**砸蛋相关*/
	/**上交砸蛋的题目答案*/
	public void answerLucky(LuckyActionModel model) throws BBSException;
	/**砸蛋现场，用户已经通过了题目的测试，拥有砸蛋的机会了~.~*/
	public void luckyDo(LuckyActionModel model)throws Exception;
	/**查看幸运帖的中奖情况*/
	public void viewLucky(LuckyActionModel model)throws Exception;
	public void giftLucky(LuckyActionModel model)throws Exception;
	/**申请添加新幸运帖*/
	public void createLucky(LuckyTopicModel model) throws Exception;
	/**提交新幸运帖*/
	public void createLuckyDo(LuckyTopicModel model)throws Exception;
	/**申请修改幸运帖*/
	public void editLucky(LuckyTopicModel model) throws Exception;
	
	/*
	 * 获取论坛的开关时间
	 */
	public void getFourmTime(ForumTimeModel model) throws ObjectNotFoundException;
	
	public void modifyFourmTime(ForumTimeModel model) throws Exception;
	
	public Topic getTopicById(int id) throws ObjectNotFoundException;
	public void deleteTopic(int id) throws ObjectNotFoundException;
}
