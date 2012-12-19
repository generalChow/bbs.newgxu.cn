package cn.newgxu.bbs.web.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.util.ForumControlUtil;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.sys.TimeIterm;
import cn.newgxu.bbs.service.ForumService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.admin.ForumTimeModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author tao
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@SuppressWarnings("serial")
public class ControlForumTimeAction extends AbstractBaseAction {

	private ForumService forumService;
	private ForumTimeModel model = new ForumTimeModel();

	@Override
	public String execute() throws Exception {
		forumService.getFourmTime(model);
		String value = model.getTimeParam().getPara_value();

		if(value==null||value.equals("")){
			System.out.println("没有相应的数据！");
			return SUCCESS;
		}
		//用于捕获由于数据错误无法转换的异常
		try{
			Gson gson = new Gson();
			Map<String, List<TimeIterm>> map = gson.fromJson(value, new TypeToken<Map<String, List<TimeIterm>>>(){}.getType());
			for(String s : map.keySet()){
				if(s.equals("day")){
					model.setItermDay(map.get(s));
				}
				if(s.equals("week")){
					model.setItermWeek(map.get(s));
				}
				if(s.equals("date")){
					model.setItermDate(map.get(s));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库暂无控制时间的数据或者数据有错!");
		}
		return SUCCESS;
	}

	public String editOpenClose() {
		try {
			forumService.getFourmTime(model);
	
			Gson gson = new Gson();			
			
			Map<String, List<TimeIterm>> map = new HashMap<String, List<TimeIterm>>();
			map.put("day",  TimeIterm.getTimeItermByString(model.getDay(), 1));
			map.put("week", TimeIterm.getTimeItermByString(model.getWeek(), 2));
			map.put("date", TimeIterm.getTimeItermByString(model.getDate(), 3));
			
			model.getTimeParam().setPara_value(gson.toJson(map));
			
			try {
				forumService.modifyFourmTime(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ForumControlUtil.init();//修改时间后从新加载数据
			
			MessageList m = new MessageList();
			m.setUrl("/admin/controlForumTime.yws");
			m.addMessage("<b>修改成功！</b>");
			Util.putMessageList(m, getSession());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public Object getModel() {
		return model;
	}

	public ForumService getForumService() {
		return forumService;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

}
