package cn.newgxu.bbs.web.activity.action;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.activity.Activity;
import cn.newgxu.bbs.web.activity.ActivityAction;
import cn.newgxu.bbs.web.activity.model.BachelorModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.activity.action.BachelorAction.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-31
 * @describe  
 *
 */
public class BachelorAction extends ActivityAction{
	private static final long serialVersionUID=8327428425436545L;
	public static final String NAME=Activity.BACHELOR;
	
	private BachelorModel model=new BachelorModel();
	private ForumService forumService;
	
	@Override
	public String execute() throws Exception {
		return null;
	}
	
	public String topic(){
		MessageList m = new MessageList();
		try{
			if(Activity.isActivityLive(NAME)){
				model.setUser(getUser());
				forumService.createTopic(model);
				return ActivityAction.TOPIC;
			}else{
				m.addMessage("【光棍节】"+Activity.NO_LIVE_EXCEPTION);
			}
		}catch(Exception e){
			e.printStackTrace();
			m.addMessage(e.getMessage());
		}
		Util.putMessageList(m, getSession());
		return ERROR;
	}
	
	public String topicDo(){
		MessageList m = new MessageList();
		try{
			if(Activity.isActivityLive(NAME)){
				model.setUser(getUser());
				User lover=checkLover();
				Topic topic=forumService.createTopicDo(model);
				Activity.executeActivity(topic,lover);
				back(m,topic);
				return SUCCESS;
			}else{
				m.addMessage("【光棍节】"+Activity.NO_LIVE_EXCEPTION);
				Util.putMessageList(m, getSession());
				return ERROR;
			}
		}catch(Exception e){
			e.printStackTrace();
			addValidateMsg(e.getMessage());
			String result = topic();
			return ActivityAction.TOPIC.equals(result) ? INPUT : result;
		}
	}
	
	/**
	 * 检查用户是否存在，同时不能是自己
	 * @return
	 * @throws Exception
	 */
	public User checkLover() throws Exception{
		User lover=null;
		try{
			lover=User.getByNick(model.getLoverNick());
			if(lover.getId()==getUser().getId())
				throw new Exception("虽然您很是可爱，但是目前些活动还不支持自己对自己表白哈。");
		}catch(ObjectNotFoundException r){
			throw new Exception("表白对象为空或者昵称不存在，请重新确认");
		}
		return lover;
	}
	
	public void back(MessageList m,Topic topic){
		m.setUrl("/topic.yws?forumId=${forumId}&topicId=${topicId}",
				MessageList.P("${forumId}", model.getForumId()),
				MessageList.P("${topicId}", topic.getId()));
		m.addMessage("<b>新主题发表成功！</b>");
		m.addMessage("<a href='/forum.yws?forumId=${forumId}'>返回主题列表</a>",
				MessageList.P("${forumId}", model.getForumId()));
		m.addMessage("<a href='/topic.yws?forumId=${forumId}&topicId=${topicId}'>查看我刚才发表的主题</a>",
						MessageList.P("${forumId}", model.getForumId()),
						MessageList.P("${topicId}", topic.getId()));
		Util.putMessageList(m, getSession());
	}
	
	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}
	public BachelorModel getModel() {
		return model;
	}
}
