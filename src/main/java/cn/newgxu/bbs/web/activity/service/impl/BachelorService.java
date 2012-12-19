package cn.newgxu.bbs.web.activity.service.impl;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.activity.Bachelor;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.activity.Activity;
import cn.newgxu.bbs.web.activity.service.ActivityService;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.activity.service.impl.BachelorService.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-31
 * @describe  
 * 光棍节在完成topic后，就会创建一个 bachelor记录，判断lover
 */
@Service("BachelorService")
@Scope("prototype")
public class BachelorService implements ActivityService{

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void afterReply(Reply reply,Object... obj) throws Exception {
		System.out.println(reply+"--->回复完成了");
		if(!Activity.isActivityLive(Activity.BACHELOR))
			return ;
		Topic topic=reply.getTopic();
		if(topic.getActivityType()!=null&&topic.getActivityType().equals(Activity.BACHELOR)){
			User user=reply.getPostUser();
			Bachelor b=(Bachelor)Bachelor.getById(Bachelor.class, topic.getLuckyId());
			if(user.getId()==b.getLover().getId()){
				System.out.println("有人回应了哈");
				b.setState(Bachelor.SUCCESS);
				b.update();
			}else{
				System.out.println("===============================没有人回应了哈");
			}
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void afterTopic(Topic topic,Object... obj) throws Exception {
		if(!Activity.isActivityLive(Activity.BACHELOR))
			return ;
		if(obj.length>0){
			if(obj[0] instanceof User){
				Bachelor bachelor=new Bachelor();
				topic.setActivityType(Activity.BACHELOR);
				bachelor.setTopic(topic);
				bachelor.setLover((User)obj[0]);
				bachelor.setAddTime(new Date());
				bachelor.setState(Bachelor.WAITING);
				bachelor.save();
				topic.setLuckyId(bachelor.getId());
				topic.update();
				System.out.println(topic+"-->topic完成了！");
			}
		}
	}
}