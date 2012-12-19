package cn.newgxu.bbs.domain.market;

import java.util.List;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.sys.impl.ParamManager;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ItemWorkManager {


	/**
	 * 当item 的work 值为color时，就会对用户的ID进行对应的颜色加亮。<br />
	 * add by:集成显卡<br />
	 * @param il
	 */
	public static void color(ItemLine il){
		User user=il.getObject();
		user.setState("color|#FF2222");
		user.save();
	}
	
	/**
	 * 当work 为 icon 时，就会添加一个小图标到昵称后面
	 * @param il
	 */
	public static void icon(ItemLine il){
		User user=il.getObject();
		user.setState("class"+il.getItem().getIcon());
		user.save();
	}
	
	public static void pigFace(ItemLine itemLine) {
		itemLine.getWork().save(ItemWork.OBJECT_TYPE_USER,
				itemLine.getObject().getId(), itemLine.getItem().getId(),
				Util.getCurrentTime(), Util.getDateAfterHour(24), "");
	}

	public static void clearFace(ItemLine itemLine) {
		try {
			itemLine.getWork().delete(ItemWork.OBJECT_TYPE_USER,
					itemLine.getObject().getId(),
					(int[]) ParamManager.getParam("facePropsList"));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static String getPropsFace(User user) {
		try {
			List<ItemWork> itemWork = ItemWork.getItemWorks(
					ItemWork.OBJECT_TYPE_USER, user.getId(),
					(int[]) ParamManager.getParam("facePropsList"), Util
							.getCurrentTime());
			return Item.get(itemWork.get(0).getItemId()).getExpand1();
		} catch (ObjectNotFoundException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static void topicSafer(ItemLine itemLine) {
		try {
			itemLine.getWork().save(ItemWork.OBJECT_TYPE_TOPIC,
					itemLine.getTopic().getId(), itemLine.getItem().getId(),
					Util.getCurrentTime(), Util.getDateAfterHour(24), "");
			Topic.get(itemLine.getTopic().getId()).setTop();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void topicKiller(ItemLine itemLine) {
		try {
			// itemLine.getWork().delete(object_type, object_id, items)
			Topic.get(itemLine.getTopic().getId()).setTop();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void toGirlAndIcon(ItemLine il){
		User user=il.getObject();
		if(user.isSex()){
			icon(il);
		}
	}
}
