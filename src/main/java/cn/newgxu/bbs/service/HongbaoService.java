package cn.newgxu.bbs.service;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.web.model.hongbao.HongbaoManageModel;
import cn.newgxu.bbs.web.model.hongbao.HongbaoModel;
import cn.newgxu.bbs.web.model.hongbao.HongbaoRecieveModel;
import cn.newgxu.bbs.web.model.hongbao.HongbaoTypeModel;


/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface HongbaoService {
	public void createHongbaoType(HongbaoTypeModel model);
	public void createHongbao(HongbaoModel model);
	public void manageHongbao(HongbaoManageModel model);
	public void manageHongbaoType(HongbaoModel model);
	public void getHongbao(HongbaoModel model);
	public void editHongbao(HongbaoModel model);
	public void recieveHongbaoToday(HongbaoRecieveModel model) throws BBSException;
	public void getHongbaoToday(HongbaoRecieveModel model);
	public void delHongbao(HongbaoModel model);
	public void delHongbaoType(HongbaoModel model);
}
