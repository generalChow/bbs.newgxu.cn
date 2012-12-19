package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.HotTopic;
import cn.newgxu.bbs.domain.RemoteContent;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class IndexModel {
	
	private List<Area> areas;

	private List<User> topExpUsers;
	
	private List<User> topMoneyUsers;
	
	private List<User> topTopicUsers;
	
	private List<User> topReplyUsers;
	
	private List<User> topGoodUsers;

	private List<HotTopic> pubHotTopics;

	private List<HotTopic> pubGoodTopics;
	
	//new
	private List<Topic> latestTopics;
//	private List<Diary> diaries;
	private List<SmallNews> smallNews;
	private List<RemoteContent> wishes;
	private List<RemoteContent> notices;
	private List<RemoteContent> losts;
	private List<RemoteContent> finds;
	private List<RemoteContent> twitters;
	
	private List<User> lastWeekMostActiveUsers;

	
	public List<User> getLastWeekMostActiveUsers() {
		return lastWeekMostActiveUsers;
	}

	public void setLastWeekMostActiveUsers(List<User> lastWeekMostActiveUsers) {
		this.lastWeekMostActiveUsers = lastWeekMostActiveUsers;
	}

	public List<RemoteContent> getTwitters() {
		return twitters;
	}

	public void setTwitters(List<RemoteContent> twitters) {
		this.twitters = twitters;
	}

	public List<RemoteContent> getLosts() {
		return losts;
	}

	public void setLosts(List<RemoteContent> losts) {
		this.losts = losts;
	}

	public List<RemoteContent> getFinds() {
		return finds;
	}

	public void setFinds(List<RemoteContent> finds) {
		this.finds = finds;
	}

	public List<RemoteContent> getNotices() {
		return notices;
	}

	public void setNotices(List<RemoteContent> notices) {
		this.notices = notices;
	}

	public List<RemoteContent> getWishes() {
		return wishes;
	}

	public void setWishes(List<RemoteContent> wishes) {
		this.wishes = wishes;
	}

	public List<SmallNews> getSmallNews() {
		return smallNews;
	}

	public void setSmallNews(List<SmallNews> smallNews) {
		this.smallNews = smallNews;
	}

//	public List<Diary> getDiaries() {
//		return diaries;
//	}
//
//	public void setDiaries(List<Diary> diaries) {
//		this.diaries = diaries;
//	}

	public List<Topic> getLatestTopics() {
		return latestTopics;
	}

	public void setLatestTopics(List<Topic> latestTopics) {
		this.latestTopics = latestTopics;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public List<User> getTopExpUsers() {
		return topExpUsers;
	}

	public void setTopExpUsers(List<User> topExpUsers) {
		this.topExpUsers = topExpUsers;
	}

	public List<User> getTopGoodUsers() {
		return topGoodUsers;
	}

	public void setTopGoodUsers(List<User> topGoodUsers) {
		this.topGoodUsers = topGoodUsers;
	}

	public List<User> getTopMoneyUsers() {
		return topMoneyUsers;
	}

	public void setTopMoneyUsers(List<User> topMoneyUsers) {
		this.topMoneyUsers = topMoneyUsers;
	}

	public List<User> getTopReplyUsers() {
		return topReplyUsers;
	}

	public void setTopReplyUsers(List<User> topReplyUsers) {
		this.topReplyUsers = topReplyUsers;
	}

	public List<User> getTopTopicUsers() {
		return topTopicUsers;
	}

	public void setTopTopicUsers(List<User> topTopicUsers) {
		this.topTopicUsers = topTopicUsers;
	}

	public List<HotTopic> getPubGoodTopics() {
		return pubGoodTopics;
	}

	public void setPubGoodTopics(List<HotTopic> pubGoodTopics) {
		this.pubGoodTopics = pubGoodTopics;
	}

	public List<HotTopic> getPubHotTopics() {
		return pubHotTopics;
	}

	public void setPubHotTopics(List<HotTopic> pubHotTopics) {
		this.pubHotTopics = pubHotTopics;
	}

}
