package cn.newgxu.bbs.web.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.user.MyUploadModel;

import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@SuppressWarnings("serial")
public class UploadItemsAction extends AbstractBaseAction{
	private static final Log log = LogFactory.getLog(UploadItemsAction.class);
	private MyUploadModel model =new MyUploadModel();

	@Override
	public String execute() throws Exception {
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		userService.getUploadItems(model);
		return SUCCESS;
	}

	public String searchUploadItems()throws Exception {
		MessageList m = new MessageList();
		model.getPagination().setActionName(getActionName());
		model.getPagination().setParamMap(getParameterMap());
		System.out.println(URLDecoder.decode(model.getNick(), "UTF-8"));
		System.out.println(model.getPagination().getRequestString());
		try{
			userService.searchUserUploadItems(model);
			return SUCCESS;
		}catch (BBSException e) {
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}
	
	public String delectUploadItems()throws Exception{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if(userService.deleteUploadItems(model)){
				writer.write("success");
			}else {
				writer.write("error");
			}
			
		} catch (IOException e) {
			log.debug(e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		return null;
	}
	public Object getModel() {
		return model;
	}

	

}
