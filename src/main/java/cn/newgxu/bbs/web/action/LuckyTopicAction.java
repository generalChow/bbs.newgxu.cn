package cn.newgxu.bbs.web.action;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.lucky.Lucky;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.model.LuckyActionModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.action.LuckyTopicAction.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-2
 * @describe  
 *
 */
@SuppressWarnings("serial")
public class LuckyTopicAction extends AbstractBaseAction{
	private static final Log log = LogFactory.getLog(LuckyTopicAction.class);

	private LuckyActionModel model=new LuckyActionModel();
	private ForumService forumService;
	
	public String execute() throws Exception {
		model.setUser(getUser());
		try{
			model.setLucky(Lucky.get(model.getLid()));
			Lucky lucky=model.getLucky();
			Date date=new Date();
			if(lucky.getStartDate().after(date))
				throw new Exception("幸运帖还没有开始！");
			if(lucky.getEndDate().before(date))
				throw new Exception("幸运帖已经结束了！");
			return SUCCESS;
		}catch(Exception e){
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	public String submit() throws Exception{
		model.setUser(getUser());
		try{
			MessageList m = new MessageList();
			System.out.println("上传的答案是："+model.getAnswer());
			model.setMessage("");
			forumService.answerLucky(model);
			m.addMessage("恭喜您！<br /><br />"
					+"您答对了 "+model.getRightCount()+" 题，正确率为："+model.getMark()+"%");
			m.addMessage(urlConvert(model.getMessage()));
			m.addMessage("<br />[请到个人信箱中查看礼品信息]");
			Util.putMessageList(m, getSession());
			return SUCCESS;
		}catch(Exception e){
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	/**
	 * 砸蛋，这只是一个动画，在页面上显示可以得到的礼物
	 * @return
	 * @throws Exception
	 */
	public String getGift() throws Exception{
		model.setUser(getUser());
		try{
			model.setChoice(true);
			forumService.luckyDo(model);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	/**
	 * 获取礼物
	 * @return
	 * @throws Exception
	 */
	public String getGiftDo() throws Exception{
		model.setUser(getUser());
		try{
			forumService.luckyDo(model);
			MessageList m = new MessageList();
			if(model.getMark()==-1){
				m.addMessage("<div style='width:100%;text-align:center;'><img src='/images/lucky_egg2_small.gif' style='border:none;'/></div>");
				m.addMessage(model.getMessage());
				Util.putMessageList(m, getSession());
				return ERROR;
			}else{
				m.addMessage("<div style='width:100%;text-align:center;'><img src='/images/lucky_egg1_small.gif' style='border:none;'/></div>");
				m.addMessage("恭喜！");
				m.addMessage(model.getMessage());
				m.addMessage("<br />[请到个人信箱中查看中奖信息]");
				Util.putMessageList(m, getSession());
				return SUCCESS;
			}
		}catch(Exception e){
			e.printStackTrace();
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	
	/**
	 * 查看中奖信息
	 * @return
	 */
	public String viewResult() throws Exception{
		model.setUser(getUser());
		try{
			model.setChoice(true);
			forumService.viewLucky(model);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	/**
	 * [url/] 这个ubb标签的简单转义到 <a/>
	 * @param base
	 * @return
	 */
	public static String urlConvert(String base){
		try{
			Pattern pattern=Pattern.compile("\\[url=(.+?)\\](.+?)\\[\\/url\\]",Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(base);
			StringBuffer stringbuffer = new StringBuffer();
			for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
				matcher.appendReplacement(stringbuffer, "<a href='"+matcher.group(1)+"' target='_blank'>"+matcher.group(2)+"</a>");
			}
			matcher.appendTail(stringbuffer);
			return stringbuffer.toString();
		}catch(Exception e){
			return base;
		}
	}
	
	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}
	public Object getModel() {
		return model;
	}
}
