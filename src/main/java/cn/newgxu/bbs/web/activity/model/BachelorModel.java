package cn.newgxu.bbs.web.activity.model;

import cn.newgxu.bbs.domain.activity.Bachelor;
import cn.newgxu.bbs.web.model.CreateTopicModel;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.activity.model.BachelorModel.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-31
 * @describe  
 *	光棍节时的model，包括了发帖，处理时的参数
 */
public class BachelorModel extends CreateTopicModel {
	private String loverNick;//表白对象的 nick，这个
	
	private Bachelor bachelor;

	public String getLoverNick() {
		return loverNick;
	}
	public void setLoverNick(String loverNick) {
		this.loverNick = loverNick;
	}
	public Bachelor getBachelor() {
		return bachelor;
	}
	public void setBachelor(Bachelor bachelor) {
		this.bachelor = bachelor;
	}
}
