package cn.newgxu.bbs.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import cn.newgxu.bbs.common.config.ForumConfig;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.common.util.fileupload.ImageInfo;
import cn.newgxu.bbs.common.util.fileupload.NewgxuFileUpload;
import cn.newgxu.bbs.common.util.fileupload.NewgxuFileUploadStats;
import cn.newgxu.bbs.common.util.fileupload.ScaleImage;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.service.UserService;
import cn.newgxu.jpamodel.ObjectNotFoundException;
import static cn.newgxu.bbs.common.Constants.*;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 * 
 * 5.0版本 更新：
 * 1. 对kindEditer 编辑器上传支持。使用 JSON 传递数据。【需要加入  JSON 包】
 * 2.对方法进行重新整顿，更为清晰
 * 3.还保留4.0 的文件上传支持，需要后台打开
 * 
 * edit by：集成显卡  2011.10.7
 */
public class UploadServlet extends BaseServlet {

	public static final String NAME_LEAVE="\\";//window下的分隔符
	
	private static final long serialVersionUID = -5777924588467591888L;

	private static final Log log = LogFactory.getLog(UploadServlet.class);

	private UserService userService = (UserService) Util.getBean("userService");

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (log.isDebugEnabled()) {
			log.debug("接收到文件上传请求，正在开始处理...");
		}

		NewgxuFileUpload upload = (NewgxuFileUpload) Util.getBean("fileUpload");
		NewgxuFileUploadStats stats = new NewgxuFileUploadStats();

		List<FileItem> items = null;
		try {
			stats.setStat(NewgxuFileUploadStats.UPLOADING);

			//获取上传文件列表
			items = upload.upload(request, stats);
		} catch (FileUploadException e) {
			e=new FileUploadException(BBSExceptionMessage.UPLOAD_FAIL);
			error(response, fail(BBSExceptionMessage.UPLOAD_FAIL),e);
		}
		//文件批量上传到硬盘的业务方法
		List<UploadItem> uploadItems=null;
		try {
			//根据文件类型做上传处理
			uploadItems = upload.convertItems(items);
		} catch (ObjectNotFoundException e1) {
			//抛出异常说明该类型文件不允许上传
			error(response, fail(BBSExceptionMessage.UPLOAD_TYPE_FAIL),new Exception(BBSExceptionMessage.UPLOAD_TYPE_FAIL));
			return;
		} catch (Exception e) {
			e=new Exception(BBSExceptionMessage.UPLOAD_NET_FAIL);
			//上传过程出错
			error(response, fail(BBSExceptionMessage.UPLOAD_NET_FAIL),e);
			return;
		}

		try {
			//验证上传的权限，扣XDB等数据库操作
			userService.reckonItems(uploadItems, getAuth(request));
		} catch (BBSException e) {
			e.printStackTrace();
			error(response, "alert(\"" + e + "\");",e);
			return;
		}
		stats.setStat(NewgxuFileUploadStats.PROCESSING);
		
		if(ForumConfig.NEW_5_0)
			success(response,uploadItems);
		else
			printScript(response, players(uploadItems));
	}
	
	/**
	 * 错误时操作：
	 * @param response
	 * @param info
	 */
	private void error(HttpServletResponse response,String info,Exception e){
		if(ForumConfig.NEW_5_0){
			System.out.println("发生错误了-----》"+e.getMessage());
			json(response,1,e.getMessage());
		}else
			printFailScript(response, info);
	}
	
	private void success(HttpServletResponse response,List<UploadItem> items){
		UploadItem item=items.get(0);
		String result=item.getUri();
		if(result.endsWith("mp3")){
			result=item.getPlayerCode();
		}
		System.out.println("---------------------->"+result.endsWith("rar"));
		if(result.endsWith("rar")||result.endsWith("zip")){
			System.out.println("是一个文件：=================================");
			result+="|"+getFileName(item.getFilename());
		}
		System.out.println("返回数据："+result);
		json(response,0,result);
	}
	
	/**
	 * 根据文件的全路径获取文件真实的名字
	 * @param name
	 * @return
	 */
	public String getFileName(String name){
		int index=name.lastIndexOf(NAME_LEAVE);
		index=(index==-1)?0:index;
		return name.substring(index, name.length());
	}
	
	@SuppressWarnings("unchecked")
	private void json(HttpServletResponse response,int isError,String info){
		initResponse(response);
		PrintWriter writer = null;
		try{
			writer=response.getWriter();
			JSONObject obj = new JSONObject();
			obj.put("error", isError);
			obj.put("url",info);
			writer.write(obj.toJSONString());
			
			System.out.println("info: : : " + info);
			// 压缩图片文件，如果是图片并且宽度超过预设， update by ivy
			if (this.isImageFile(info)) {
				String realIamgePath = FILE_ROOT + info;
				System.out.println("绝对路径： " + realIamgePath);
//				this.modifyImageWidth(realIamgePath);
			}
			
		} catch (IOException e) {
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	private String fail(String msg) {
		return "<script>alert(\"" + msg + "\");</script>";
	}
	
	private void printFailScript(HttpServletResponse response, String s) {
		initResponse(response);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(s);
			writer.write("<script>parent.uploadArea.cancel();</script>");//如果上传文件失败就将对话框关闭
			log.debug(s);
		} catch (IOException e) {
			log.debug(e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String players(List<UploadItem> uploadItems) {
		StringBuffer sb = new StringBuffer("contentAdd(\"");
		for (UploadItem item : uploadItems) {
			sb.append(item.getPlayerCode()).append(" ");
		}
		sb.append("\");");
		return sb.toString();
	}

	private void printScript(HttpServletResponse response, String s) {
		print(response, "<script>parent.uploadArea.showResult('" + s + "');</script>");
	}

	private void print(HttpServletResponse response, String s) {
		initResponse(response);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			log.debug(s);
			writer.write("<script>alert(\"上传成功\");</script>");
			writer.write(s);
		} catch (IOException e) {
			log.debug(e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void initResponse(HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
	}
	
	/**
	 * 修改用户上传图片的宽度，最大宽度默认值为635，超过就进行等比例压缩
	 * @param imagePath 文件的绝对路径，因为用到了java的文件读写，这时要使用绝对路径
	 * @since 2012-03-07
	 * @author ivy
	 */
	public void modifyImageWidth(String imagePath) {
		Map<String, Integer> imageInfo = ImageInfo.getImgInfo(imagePath);
		if (imageInfo.get("width") > IMAGE_MAX_PROPER_WIDTH) {
			ScaleImage scaleImage = new ScaleImage();
			try {
				scaleImage.saveImageAsAnother(imagePath, imagePath, IMAGE_MAX_PROPER_WIDTH, imageInfo.get("height"));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("上传图片出错！");
			}
		}
	}
	
	/**
	 * 判断给定路径的文件是否是图片类型
	 * @param filePath
	 * @return if so, return true, or false
	 * @since 2012-03-07
	 * @author ivy
	 */
	public boolean isImageFile(String filePath) {
		if (filePath.endsWith(".jpg")) 
			return true;
		if (filePath.endsWith(".gif"))
			return true;
		if (filePath.endsWith(".png"))
			return true;
		if (filePath.endsWith(".bmp"))
			return true;
		if (filePath.endsWith(".jpeg"))
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		new UploadServlet().modifyImageWidth("D:/Program Files/tomcat-6.0.35/webapps/ROOT/upload/jpg/2012/3/7/2/@1331057651333_0.jpg");
	}
}