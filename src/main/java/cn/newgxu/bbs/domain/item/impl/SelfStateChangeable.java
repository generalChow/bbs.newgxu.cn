package cn.newgxu.bbs.domain.item.impl;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.item.StateBehavior;
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
		self.addPower(itemLine.getItem().getSelfPower());
		self.addMoney(itemLine.getItem().getSelfMoney());
		if(self.getCurrentPower()>self.getMaxPower()){
			self.setCurrentPower(self.getMaxPower());
		}
		self.addExp(itemLine.getItem().getObjectExp());
		self.addBadboy(itemLine.getItem().getObjectBadboy());
		self.save();
	}

}
