package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.HongbaoService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.hongbao.HongbaoModel;

/**
 * 创建红包action
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateHongbaoAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private HongbaoModel model = new HongbaoModel();

	private HongbaoService hongbaoService;

	public String execute() throws Exception {
		hongbaoService.manageHongbaoType(model);
		return SUCCESS;
	}

	public String doHongbaoCreate() throws Exception {
		hongbaoService.createHongbao(model);
		response("{\"statusCode\":\"200\", \"message\":\"红包创建成功\", \"navTabId\":\"hongbao\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"hongbao.yws\"}");
		return null;
		/*
		MessageList m = new MessageList();
		m.setUrl("/admin/index.yws");
		m.addMessage("<b>创建成功！</b>");
		Util.putMessageList(m, getSession());
		return SUCCESS;
		 */
		
	}

	public Object getModel() {
		return model;
	}

	public void setHongbaoService(HongbaoService hongbaoService) {
		this.hongbaoService = hongbaoService;
	}

	public HongbaoService getHongbaoService() {
		return hongbaoService;
	}

}
