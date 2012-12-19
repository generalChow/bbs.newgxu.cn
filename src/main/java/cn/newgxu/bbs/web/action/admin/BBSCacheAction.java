package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.config.ForumConfig;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.cache.BBSCache;
import cn.newgxu.bbs.web.model.admin.BBSCacheModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.action.admin.BBSCacheAction.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-11-5
 * @describe  
 *
 */
public class BBSCacheAction extends AbstractBaseAction{
	private static final long serialVersionUID=21927136123232L;

	private BBSCacheModel model=new BBSCacheModel();

	/**
	 * 读取当前论坛中的缓存信息
	 */
	public String execute() throws Exception {
		model.setCaches(BBSCache.getCurrentCache());
		model.setTimeout(ForumConfig.getConfig("cache_timeout"));
		return SUCCESS;
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	public String update(){
		try{
			switch(model.getCacheId()){
			case 1:
				BBSCache.buildAreaCache();
				break;
			case 2:
				BBSCache.buildExpUserCache();
				break;
			case 3:
				BBSCache.buildMoneyUserCache();
				break;
			case 4:
				BBSCache.buildTopicUserCache();
				break;
			case 5:
				BBSCache.buildReplyUserCache();
				break;
			case 6:
				BBSCache.buildGoodUserCache();
				break;
			case 7:
				BBSCache.buildHotTopicCache();
				break;
			case 8:
				BBSCache.buildGoodTopicCache();
				break;
			case 9:
				BBSCache.buildSmallNewsCache();
				break;
			case 10:
				BBSCache.buildWishesCache();
				break;
			case 11:
				BBSCache.buildNoticesCache();
				break;
			case 12:
				BBSCache.buildFindsCache();
				break;
			case 13:
				BBSCache.buildFindsCache();
				break;
			case 14:
				BBSCache.buildTipCache();
				break;
			}
			response("{\"statusCode\":\"200\", \"message\":\"缓存刷新成功\", \"navTabId\":\"forum_cache\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"cache.yws\"}");
			return null;
		}catch(Exception e){
			MessageList m=new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	/**
	 * 修改缓存刷新时间
	 * @return
	 */
	public String change() {
		try{
			System.out.println("修改缓存时间！");
			ForumConfig.setConfig("cache_timeout", model.getTimeout());
			ForumConfig.updateConfig();
			response("{\"statusCode\":\"200\", \"message\":\"缓存更新间隔修改成功\", \"navTabId\":\"forum_cache\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"cache.yws\"}");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			MessageList m=new MessageList();
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	public Object getModel() {
		return model;
	}
}
