package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.HongbaoService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.hongbao.HongbaoModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class DelHongbaoTypeAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private HongbaoModel model = new HongbaoModel();

	private HongbaoService hongbaoService;

	public String execute() throws Exception {
		hongbaoService.delHongbaoType(model);
		response("{\"statusCode\":\"200\", \"message\":\"红包类型成功删除\", \"navTabId\":\"hongbao_manage\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"manage_hongbao.yws\"}");
		return null;
		/*
		MessageList m = new MessageList();
		m.setUrl("/admin/index.yws");
		m.addMessage("<b>删除成功！</b>");
		Util.putMessageList(m, getSession());
		return SUCCESS;*/
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
