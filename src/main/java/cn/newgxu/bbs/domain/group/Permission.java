package cn.newgxu.bbs.domain.group;

import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.SmallNews;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface Permission {

	boolean canViewForum(Forum forum, User user);

	boolean canModify(Topic topic, User user);
	
	boolean canModify(Reply reply, User user);
	
	boolean canScreen(Reply reply, User user);

	boolean canDelReply(Reply reply, User user);

	boolean canSetTop(Topic topic, User user);
	
	boolean canSetAreaTop(Topic topic, User user);
	
	boolean canSetAllTop(Topic topic, User user);

	boolean canUnSetTop(Topic topic, User user);

	boolean canLock(Topic topic, User user);
	
	boolean canRepair(Topic topic, User user);

	boolean canSetGood(Topic topic, User user);

	boolean canSetLight(Topic topic, User user);
	
	boolean canPub(Topic topic, User user);

	boolean canDelete(Topic topic, User user);

	boolean canMoveTopic(Topic topic, User user);

	boolean canDelSmallNews(SmallNews smallNews, User user);

	boolean canViewLastLogTime();
	
	boolean canViewTrueName() ;

	boolean canViewUserIp();

	boolean isInVirtualTime();

	boolean canLeavelUp(User user);

	boolean isEditTitleFree();

}
