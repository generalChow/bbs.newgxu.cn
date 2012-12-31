package cn.newgxu.bbs.domain.item.impl;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.item.StateBehavior;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SelfStateChangeable implements StateBehavior {

	public void change(ItemLine itemLine) throws BBSException {
//		User self = itemLine.getUser();
//		self.usePower(itemLine.getItem().getSelfPower());
//		self.payFor(itemLine.getItem().getSelfMoney());
//		self.save();
	/*2011-2-20注释*/	/**由于数据库字段标准为无符号整型，所以不能插入负数*/
		User self = itemLine.getUser();
//		2012-12-31，增加了用户体力清零功能。
		if (itemLine.getItem().getObjectPower() == Item.TRUANCATE_POWER) {
			self.setCurrentPower(0);
		} else {
			self.addPower(itemLine.getItem().getSelfPower());
			if(self.getCurrentPower()>self.getMaxPower()){
				self.setCurrentPower(self.getMaxPower());
			}
		}
		self.addMoney(itemLine.getItem().getSelfMoney());
		self.addExp(itemLine.getItem().getObjectExp());
		self.addBadboy(itemLine.getItem().getObjectBadboy());
		self.save();
	}

}
