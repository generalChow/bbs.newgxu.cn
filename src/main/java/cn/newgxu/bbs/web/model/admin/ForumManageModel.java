package cn.newgxu.bbs.web.model.admin;

import java.util.List;

import cn.newgxu.bbs.domain.Area;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ForumManageModel {
	
	private int areaId;
	private Area area;
	private List<Area> areas;

	private int forumId;
	private String name;
	private String description;
	private int compositorId;
	private int hot;
	private int secrecy;
	private int confrere;
	private int limit_topics_perday;
	private int topicMoney;
	private int topicExp;
	private int replyMoney;
	private int replyExp;
	private int goodMoney;
	private int goodExp;
	private int delMoney;
	private int delExp;
	private int lightMoney;
	private int lightExp;
	private String nicks;

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public int getCompositorId() {
		return compositorId;
	}

	public void setCompositorId(int compositorId) {
		this.compositorId = compositorId;
	}

	public int getConfrere() {
		return confrere;
	}

	public void setConfrere(int confrere) {
		this.confrere = confrere;
	}

	public int getDelExp() {
		return delExp;
	}

	public void setDelExp(int delExp) {
		this.delExp = delExp;
	}

	public int getDelMoney() {
		return delMoney;
	}

	public void setDelMoney(int delMoney) {
		this.delMoney = delMoney;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getGoodExp() {
		return goodExp;
	}

	public void setGoodExp(int goodExp) {
		this.goodExp = goodExp;
	}

	public int getGoodMoney() {
		return goodMoney;
	}

	public void setGoodMoney(int goodMoney) {
		this.goodMoney = goodMoney;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public int getLightExp() {
		return lightExp;
	}

	public void setLightExp(int lightExp) {
		this.lightExp = lightExp;
	}

	public int getLightMoney() {
		return lightMoney;
	}

	public void setLightMoney(int lightMoney) {
		this.lightMoney = lightMoney;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReplyExp() {
		return replyExp;
	}

	public void setReplyExp(int replyExp) {
		this.replyExp = replyExp;
	}

	public int getReplyMoney() {
		return replyMoney;
	}

	public void setReplyMoney(int replyMoney) {
		this.replyMoney = replyMoney;
	}

	public int getSecrecy() {
		return secrecy;
	}

	public void setSecrecy(int secrecy) {
		this.secrecy = secrecy;
	}

	public int getTopicExp() {
		return topicExp;
	}

	public void setTopicExp(int topicExp) {
		this.topicExp = topicExp;
	}

	public int getTopicMoney() {
		return topicMoney;
	}

	public void setTopicMoney(int topicMoney) {
		this.topicMoney = topicMoney;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public String getNicks() {
		return nicks;
	}

	public void setNicks(String nicks) {
		this.nicks = nicks;
	}

	public int getLimit_topics_perday() {
		return limit_topics_perday;
	}

	public void setLimit_topics_perday(int limitTopicsPerday) {
		limit_topics_perday = limitTopicsPerday;
	}
	
}
