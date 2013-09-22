package cn.newgxu.bbs.service.proxy;

import java.util.List;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.exception.ValidationException;
import cn.newgxu.bbs.common.util.ValidationUtil;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.HotTopic;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.service.ForumService;
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
public class ForumServiceProxy implements ForumService {

	private ForumService forumService;

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public void createArea(AreaManageModel model) throws BBSException {
		forumService.createArea(model);
	}

	public void createForum(ForumManageModel model) throws BBSException {
		forumService.createForum(model);
	}

	public Topic createTopicDo(CreateTopicModel model) throws BBSException, ValidationException {

		ValidationUtil.title(model.getTitle());
		ValidationUtil.content(model.getContent());
		return forumService.createTopicDo(model);
	}

	public List<Area> getAreas() {
		return forumService.getAreas();
	}

	public List<Area> getAllAreas() {
		return forumService.getAllAreas();
	}

	public Topic getTopic(Forum forum, int topicId) throws BBSException {
		return forumService.getTopic(forum, topicId);
	}

	public List<Topic> getTopics_Normal(Forum forum, Pagination p) {
		return forumService.getTopics_Normal(forum, p);
	}

	public List<Reply> getReplies_Normal(Topic topic, Pagination p) {
		return forumService.getReplies_Normal(topic, p);
	}

	public void forum(ForumModel model) throws BBSException {
		forumService.forum(model);
	}

	public void topic(TopicModel model) throws BBSException {
		forumService.topic(model);
	}

	public Topic createReply(ReplyModel model) throws Exception {
		return forumService.createReply(model);
	}

	public void createTopic(CreateTopicModel model) throws BBSException {
		forumService.createTopic(model);
	}

	public void createVote(CreateVoteModel model) throws BBSException {
		forumService.createVote(model);
	}

	public Topic createVoteDo(CreateVoteModel model) throws BBSException, ValidationException {
		ValidationUtil.title(model.getTitle());
		ValidationUtil.content(model.getContent());
		ValidationUtil.options(model.getOptions());

		if (!model.getUser().getUserGroup().isAuthCreateTopic()) {
			throw new BBSException(BBSExceptionMessage.CANNOT_CREATE_TOPIC);
		}

		return forumService.createVoteDo(model);
	}

	public Topic vote(VoteModel model) throws BBSException {
		if (model.getOptions() == null || model.getOptions().length < 1) {
			throw new BBSException(BBSExceptionMessage.SELECT_OPTIONS);
		}
		return forumService.vote(model);
	}

	public void reply(ReplyModel model) throws BBSException {
		forumService.reply(model);
	}

	public void modify(ModifyModel model) throws BBSException {
		forumService.modify(model);
	}

	public void modifyDo(ModifyModel model) throws BBSException, ValidationException {
		try {
			Reply reply = Reply.get(model.getReplyId());
			if (reply.isFirstReply()) {
				ValidationUtil.title(model.getTitle().trim());
			}
		} catch (ObjectNotFoundException e) {
			throw new BBSException(BBSExceptionMessage.PARAMETER_ERROR);
		}

		ValidationUtil.content(model.getContent().trim());
		
		forumService.modifyDo(model);

	}

	public Topic manage(ManageModel model) throws BBSException {
		return forumService.manage(model);
	}

	public void search(SearchModel model) throws BBSException, ValidationException {
		forumService.search(model);
	}

	public void createSmallNews(CreateSmallNewsModel model) throws BBSException {
		forumService.createSmallNews(model);
	}

	public void createSmallNewsDo(CreateSmallNewsModel model) throws BBSException, ValidationException {
		ValidationUtil.smallNews(model.getTitle(), model.getContent());
		forumService.createSmallNewsDo(model);
	}

	public void getSmallNewsList(SmallNewsModel model) throws BBSException {
		forumService.getSmallNewsList(model);
	}

	public SmallNews smallNews(int id) throws BBSException {
		return forumService.smallNews(id);
	}

	public void delSmallNews(SmallNewsModel model) throws BBSException {
		forumService.delSmallNews(model);
	}

	public List<HotTopic> getTopics(int pubType,int num) {
		return forumService.getTopics(pubType,num);
	}

	public Area getArea(int areaId) throws BBSException {
		return forumService.getArea(areaId);
	}

	public void editArea(AreaManageModel model) throws BBSException {
		forumService.editArea(model);
	}

	public Forum getForum(int forumId) throws BBSException {
		return forumService.getForum(forumId);
	}

	public void editForum(ForumManageModel model) throws BBSException {
		forumService.editForum(model);
	}

	public void BoardState(BoardStateModel model) throws BBSException {
		forumService.BoardState(model);
		
	}

	public Area getArea(BoardStateModel model) throws BBSException {
		
		return forumService.getArea(model);
	}

	public List<Topic> getTopics_Good(Forum forum, Pagination p) {
		
		return forumService.getTopics_Good(forum, p);
	}

	public void queryHotTopics() throws Exception {
		forumService.queryHotTopics();
	}

	public void saveDraftBox(CreateTopicModel model) throws BBSException {
		forumService.saveDraftBox(model);	
	}

	public void getDraftBoxes(CreateTopicModel model) throws BBSException {
		forumService.getDraftBoxes(model);
	}

	public CreateTopicModel getDraftBoxModel(int draftid) throws Exception {
		return forumService.getDraftBoxModel(draftid);
	}

	public void saveAgainDraftBox(CreateTopicModel model,int n) throws BBSException {
		forumService.saveAgainDraftBox(model,n);		
	}

	public void delDraftBoxes(CreateTopicModel model) throws BBSException {
		forumService.delDraftBoxes(model);		
	}

	public void checkTopicSafer() throws Exception {
		forumService.checkTopicSafer();
	}

	public void area(AreaModel model) {
		forumService.area(model);
	}

	public void getFourmTime(ForumTimeModel model)
			throws ObjectNotFoundException {
		forumService.getFourmTime(model);
	}

	public void modifyFourmTime(ForumTimeModel model)
	throws Exception {
		forumService.modifyFourmTime(model);
	}

	public void replyFast(ReplyModel model) throws BBSException {
		forumService.replyFast(model);
	}

	public Topic replyFastDo(ReplyModel model) throws Exception {
		return forumService.replyFastDo(model);
	}

	public void answerLucky(LuckyActionModel model) throws BBSException {
		forumService.answerLucky(model);
	}
	public void luckyDo(LuckyActionModel model) throws Exception {
		forumService.luckyDo(model);
	}
	public void giftLucky(LuckyActionModel model) throws Exception {
		forumService.giftLucky(model);
	}
	public void viewLucky(LuckyActionModel model) throws Exception {
		forumService.viewLucky(model);
	}
	public void createLucky(LuckyTopicModel model) throws Exception {
		forumService.createLucky(model);
	}
	public void createLuckyDo(LuckyTopicModel model) throws Exception {
		forumService.createLuckyDo(model);
	}
	public void editLucky(LuckyTopicModel model) throws Exception {
		forumService.editLucky(model);
	}

	@Override
	public Topic getTopicById(int id) throws ObjectNotFoundException {
		// TODO Auto-generated method stu
		Topic topic = Topic.get(id);
		return topic;
	}

	@Override
	public void deleteTopic(int id) throws ObjectNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("到了代理层了");
		Topic topic = Topic.get(id);
		topic.delete();
	}

}
