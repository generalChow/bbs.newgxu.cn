package cn.newgxu.bbs.web.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.Node;

import cn.newgxu.bbs.common.config.XMLUtil;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.activity.Bachelor;
import cn.newgxu.bbs.web.activity.service.ActivityService;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.activity.Activity.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-31
 * @describe  
 *
 */
public class Activity {
	private static final String CONFIG_FILE_NAME="activity_config.xml";
	private static String CONFIG_PATH=null;
	
	/*活动Exception*/
	public static final String NO_LIVE_EXCEPTION="这个活动现在还没有开放或者已经结束了！Sorry！";
	
	/*活动名称*/
	public static final String BACHELOR="Bachelor";
	
	/**
	 * 一个活动是否在进行中<br />
	 * 添加新活动时，请添加相应的 static字段<br />
	 * @param activityName
	 * @return
	 * @throws Exception
	 */
	public static boolean isActivityLive(String activityName) throws Exception{
		String dateInfo=getNodeText("/activity/date/"+activityName);
		String temp[]=dateInfo.split("#");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startD=df.parse(temp[0]);
		Date endD=df.parse(temp[1]);
		Date date=new Date();
		return date.after(startD)&&date.before(endD);
	}
	
	/**
	 * 获取单个 节点 中的字符串信息
	 * @param nodeName
	 * @return
	 * @throws Exception
	 */
	public static String getNodeText(String nodeName) throws Exception{
		Document document=XMLUtil.getXMLDocument(getPath());
		Node node=document.selectSingleNode(nodeName);
		return node.getText();
	}
	
	private static final String getPath(){
		if(CONFIG_PATH==null){
			try{
				CONFIG_PATH=Thread.currentThread().getContextClassLoader().getResource("/config/").toURI().getPath() + CONFIG_FILE_NAME;
			}catch(Exception e){
				CONFIG_PATH="/config/"+CONFIG_FILE_NAME;
			}
		}
		return CONFIG_PATH;
	}
	
	/**
	 * 在topic保存成功后，执行的方法
	 * @param topic
	 * @throws Exception
	 */
	public static void executeActivity(Topic topic,Object... obj) throws Exception{
		getService().afterTopic(topic,obj);
	}
	/**
	 * 在回复完成后，执行的方法
	 * @param reply
	 * @throws Exception
	 */
	public static void executeActivity(Reply reply,Object... obj) throws Exception{
		getService().afterReply(reply);
	}
	
	/**
	 * 获取当前正在进行的活动。管理员可以在配置文件中添加相应的配置
	 * @return
	 */
	public static ActivityService getService() throws Exception{
		String className=getNodeText("/activity/current");
		//return (ActivityService)(Class.forName("cn.newgxu.bbs.web.activity.service.impl."+className).newInstance());
		return (ActivityService)Util.getBean(className);
	}
	
	public static String getActivityInfo(int id){
		try{
			Bachelor b=(Bachelor)Bachelor.getById(Bachelor.class, id);
			StringBuffer sb=new StringBuffer("<div class='BACHELOR_DIV'>");
			sb.append("这是一个光棍节活动帖子。当前状态：<span class='BACHELOR_"+b.getState()+"' ></span>");
			sb.append("<br /><br />表白对象："+b.getLover().getNick());
			sb.append("</div>");
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
