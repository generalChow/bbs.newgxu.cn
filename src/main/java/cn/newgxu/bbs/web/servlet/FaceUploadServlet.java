package cn.newgxu.bbs.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.common.util.fileupload.NewgxuFaceUpload;
import cn.newgxu.bbs.common.util.fileupload.NewgxuFileUploadStats;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.service.UserService;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class FaceUploadServlet extends BaseServlet {

	private static final long serialVersionUID = -9189092436481567087L;

	private static final Log log = LogFactory.getLog(UploadServlet.class);

	private UserService userService = (UserService) Util.getBean("userService");

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		doFileUpload(session, request, response);
	}

	private void doFileUpload(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("接收到文件上传请求，正在开始处理...");
		}
		List<FileItem> items = null;
		NewgxuFaceUpload upload = (NewgxuFaceUpload) Util.getBean("faceUpload");
		NewgxuFileUploadStats stats = new NewgxuFileUploadStats();
		try {
			stats.setStat(NewgxuFileUploadStats.UPLOADING);
			items = upload.upload(request, stats);
			List<UploadItem> uploadItems = upload.convertItems(items);
			// userService.reckonItems(uploadItems, getAuth(request));
			userService.FaceReckonItems(uploadItems, getAuth(request));
			sendCompleteResponse(response, "头像上传成功,请刷新");
		} catch (FileUploadException e) {
			log.debug(e);
			e.printStackTrace();
			sendErrorRespone(response, e.getMessage());
		} catch (BBSException e) {
			log.debug(e);
			e.printStackTrace();
			sendErrorRespone(response, e.getMessage());
			return;
		}
	}

	private void sendErrorRespone(HttpServletResponse response, String message)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			log.debug(message);
			writer.write("<script>alert(\"头像上传出错\");</script>");
			writer
					.write("<script type='text/javascript'>function killUpdate() { window.parent.killUpdate('"
							+ message
							+ "'); }</script></head><body onload='killUpdate()'>");
		} catch (IOException e) {
			log.debug(e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void sendCompleteResponse(HttpServletResponse response,
			String message) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			log.debug(message);
			writer.write("<script>alert(\"头像上传成功\");</script>");
			writer
					.write("<script type='text/javascript'>function killUpdate() { window.parent.killUpdate('"
							+ message
							+ "'); }</script></head><body onload='killUpdate()'>");
		} catch (IOException e) {
			log.debug(e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
