package cn.newgxu.bbs.web.activity.service;

import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.Topic;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.activity.service.ActivityService.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-31
 * @describe  
 *	参数是不定项的
 */
public interface ActivityService {
	/**
	 * 在帖子完成后，需要执行的活动操作
	 * @param topic
	 * @throws Exception
	 */
	public void afterTopic(Topic topic,Object ...obj) throws Exception;
	
	/**
	 * 在回复后，需要执行的活动操作
	 * @param reply
	 * @throws Exception
	 */
	public void afterReply(Reply reply,Object ...obj) throws Exception;
}
