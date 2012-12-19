package cn.newgxu.bbs.web.action.admin;

import cn.newgxu.bbs.service.HongbaoService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.hongbao.HongbaoTypeModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateHongbaoTypeAction extends AbstractBaseAction {

	private static final long serialVersionUID = 175663349208217104L;

	private HongbaoTypeModel model = new HongbaoTypeModel();

	private HongbaoService hongbaoService;

	public String execute() throws Exception {
		return SUCCESS;
	}

	public String doHongbaoTypeCreate() throws Exception {
		hongbaoService.createHongbaoType(model);
		response("红包类型添加成功，请刷新列表。当前窗口可以关闭了");
		//response("{\"statusCode\":\"200\", \"message\":\"红包类型创建成功，请刷新红包类型页面以查看最新列表<br />【如不能自动刷新，请手动刷新】\", \"navTabId\":\"\",\"rel\":\"\", \"callbackType\":\"\",\"forwardUrl\":\"\"}");
		return null;
		/*
		MessageList m = new MessageList();
		m.setUrl("#");
		m.addMessage("<b>创建成功！</b>");
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
