package cn.newgxu.bbs.domain.item.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.domain.item.ReturnBehavior;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HasReturnBehavior implements ReturnBehavior {

	private static final Log log = LogFactory.getLog(HasReturnBehavior.class);
	
	public void execute(ItemLine itemLine) throws BBSException {
		if(!itemLine.getItem().isCanUse())
			throw new BBSException("对不起，该物品现在不能使用。使用时间为"+itemLine.getItem().getExpand1());
		Item newItem=null;
		int okRate = 70;//成功的几率。放在前面，下面好控制，比如随机得到物品，是100%的
		if (itemLine.getItem().getReturnId() == -1) {
			/**
			 * 在这里判断一下，如果 typeId=3, retuenId=-1,work_method！=null
			 * 那么就是从 work_method 中获取物品的id集合，
			 * work_method为null时，就从全部物品中获取。
			 * 
			 */
			if(itemLine.getItem().getExpand1()==null||itemLine.getItem().getExpand1().trim().length()==0){
				newItem = Item.getRandomItem();
				if (log.isDebugEnabled()) {
					log.debug("得到随机物品：" + newItem);
				}
			}	
			else{
				System.out.println("随机得到指定的物品，设置成功率为100====================");
				okRate=100;
				Object result=Item.getRandomItem(itemLine.getItem().getExpand1());
				//如果返回的是一个 Integer 对象，那么就是用户西大币加上这个Integer值
				if(result instanceof Integer){
					System.out.println("随机得到西大币=====================");
					User user=itemLine.getUser();
					user.addMoney((Integer)result);
					user.save();
				}
				else if(result instanceof Item){
					newItem=(Item)result;
					System.out.println("随机得到物品:"+newItem.getName()+"=====================");
				}
				else{
					System.out.println("什么也没有");
				}
			}
		} else {
			try {
				int pro=itemLine.getItem().getProbability();
				if(pro==0)
					okRate=100;
				else
					okRate=pro;
				newItem = Item.get(itemLine.getItem().getReturnId());
				if (log.isDebugEnabled()) {
					log.debug("制造物品：" + newItem);
				}
			} catch (ObjectNotFoundException e) {
				log.error(e);
				log.error("严重错误：Item 没有找到！ id="
						+ itemLine.getItem().getReturnId());
				log.error("严重错误：itemLine=" + itemLine);
				return;
			}
		}
		// 生产物品有几率失败，成功率初始值为70%
		// 未来考虑和BB值挂钩
		int rp = (int) (Math.random() * 100);
		if (rp > okRate) {
			throw new BBSException(BBSExceptionMessage.CREATE_ITEM_FAIL);
		}
		
		//只有在newItem存在的情况下才会保存
		if(newItem!=null){
			ItemLine itemLine2 = new ItemLine();
			itemLine2.setItem(newItem);
			// 对谁使用，物品就是谁的，而且是他制造的。
			itemLine2.setUser(itemLine.getObject());
			itemLine2.setMaker(itemLine.getObject());

			itemLine2.save();
		}
	}
}
