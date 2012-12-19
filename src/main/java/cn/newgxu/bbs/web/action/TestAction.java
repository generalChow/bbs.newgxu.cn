package cn.newgxu.bbs.web.action;

import java.util.Calendar;
import java.util.List;

import cn.newgxu.bbs.domain.HotTopic;
import cn.newgxu.bbs.domain.activity.Tips;
import cn.newgxu.bbs.web.model.TestModel;

/**
 * 这个类纯粹用来本地测试。
 */
public class TestAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;
	private TestModel model = new TestModel();

	@Override
	public String execute() throws Exception {
//		UpdateLastWeekExpListener.init();
//		User.updateLastWeekExp();
//		userService.updateLastWeekExp();
		
//		model.getPagination().setActionName(getActionName());
//		model.getPagination().setParamMap(getParameterMap());
//		model.setActiveUsers(userService.getUsers(6, model.getPagination()));
//		model.setUser(userService.getUser(1));
//		List<User> users = User.getLastWeekMostActiveUsers(10);
//		Message.sendMessage("恭喜您成为上周最活跃榜单前三名", "恭喜您成为本周新星榜前三位，现给予”卡罗卡斯橙汁”一杯，希望您再接再厉。欲了解“新星”排行榜的排名，<a href='/user/users.yws?type=6'>点我~</a>", 1, users.subList(0, 3), User.get(0), "亲爱的雨无声论坛网友！");
//		Message.sendMessage("恭喜您成为上周最活跃榜单前十名", "恭喜您成为本周新星榜前三位，现给予”加苦王老吉”一杯，希望您再接再厉。欲了解“新星”排行榜的排名，<a href='/user/users.yws?type=6'>点我~</a>", 1, users.subList(3, 10), User.get(0), "亲爱的雨无声论坛网友！");
		
//		userService.updateLastWeekExp();
		Calendar deadline = Calendar.getInstance();
		deadline.set(Calendar.DAY_OF_MONTH, 22);
		String pattern = "cet";
		Tips.getRandomHolidayImage(deadline, pattern);
		
		return SUCCESS;
	}
	
	public String init() {
		return SUCCESS;
	}

	
	// 热门帖子测试! 成功
	public String test() {
		
		List<HotTopic> hotTopics = HotTopic.getPubTopics(1, 10);
		
		for (HotTopic hotTopic : hotTopics) {
			System.out.println(hotTopic.getTitle());
			System.out.println("----" + hotTopic.getCreationTime());
		}
		
		return SUCCESS;
	}


	public Object getModel() {
		return model;
	}

	public void setModel(TestModel model) {
		this.model = model;
	}
}
