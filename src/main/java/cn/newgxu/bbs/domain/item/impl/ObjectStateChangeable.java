package cn.newgxu.bbs.domain.item.impl;

import cn.newgxu.bbs.domain.item.StateBehavior;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ObjectStateChangeable implements StateBehavior {

	public void change(ItemLine itemLine) {
		User object = itemLine.getObject();
		object.addPower(itemLine.getItem().getObjectPower());
		if(object.getCurrentPower()>object.getMaxPower()){
			object.setCurrentPower(object.getMaxPower());
		}
		object.addExp(itemLine.getItem().getObjectExp());
		object.addBadboy(itemLine.getItem().getObjectBadboy());
		object.save();
	}

}
