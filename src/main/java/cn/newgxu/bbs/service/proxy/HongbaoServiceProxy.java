package cn.newgxu.bbs.service.proxy;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.service.HongbaoService;
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
public class HongbaoServiceProxy implements HongbaoService{
	
	private HongbaoService hongbaoService;

	public void createHongbao(HongbaoModel model) {
		this.hongbaoService.createHongbao(model);
	}

	public void createHongbaoType(HongbaoTypeModel model) {
		this.hongbaoService.createHongbaoType(model);
	}

	public void manageHongbao(HongbaoManageModel model) {
		this.hongbaoService.manageHongbao(model);
	}

	public void setHongbaoService(HongbaoService hongbaoService) {
		this.hongbaoService = hongbaoService;
	}

	public void manageHongbaoType(HongbaoModel model) {
		this.hongbaoService.manageHongbaoType(model);
	}

	public void getHongbao(HongbaoModel model) {
		this.hongbaoService.getHongbao(model);
	}

	public void editHongbao(HongbaoModel model) {
		this.hongbaoService.editHongbao(model);
	}

	public void getHongbaoToday(HongbaoRecieveModel model) {
		hongbaoService.getHongbaoToday(model);
	}

	public void recieveHongbaoToday(HongbaoRecieveModel model) throws BBSException {
		hongbaoService.recieveHongbaoToday(model);
	}

	public void delHongbao(HongbaoModel model) {
		hongbaoService.delHongbao(model);
	}

	public void delHongbaoType(HongbaoModel model) {
		hongbaoService.delHongbaoType(model);		
	}

}
