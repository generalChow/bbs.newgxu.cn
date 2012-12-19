package cn.newgxu.bbs.web.action;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ValhallaActionSupport extends AbstractBaseAction {

	private static final long serialVersionUID = -5646907009123962008L;

	public String execute() throws Exception {
		signOnlineUser("不便显示的角落");
		return SUCCESS;
	}

	public Object getModel() {
		return new Object();
	}

}
