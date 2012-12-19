package cn.newgxu.bbs.web.action.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import cn.newgxu.bbs.web.action.AbstractBaseAction;

import com.opensymphony.webwork.ServletActionContext;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.action.ycalendar.UploadImageAction.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-21
 * @describe  
 *	管理员上传文件的action
 */
@SuppressWarnings("serial")
public class AdminUploadImageAction extends AbstractBaseAction{
	//这个是页面上 file选择器的name属性，要相同
	private File imgFile;
	private String imgFileFileName;
	private String imgFileContentType;
	
	private final int  BUFFER_SIZE=16*1024;
	
	
	//文件保存的信息，在struts配置文件中标明
	private String savePath;
	
	public String execute() throws Exception {
		System.out.println("有新文件来了");
		if(imgFile!=null){
			try{
				//重新命名
				String name=this.getSavePath()+"/"+System.currentTimeMillis()+imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
				File target=new File(ServletActionContext.getServletContext().getRealPath(name));
				this.copyFile(imgFile, target);
				//response(name);
				HttpServletResponse response=getResponse();
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("<html><head><script>window.parent.gogo('"+name+"');</script></head><body>success!</body></html>");
				//response("<html><head><script>parent.gogo('"+name+"');</script></head><body>success!</body></html>");
			}
			catch(Exception e){
				e.printStackTrace();
				try{
					response("error!"+e.getMessage());
				}catch(Exception r){}
			}
		}
		return null;
	}
	
	/**
	 * @方法名称 :copyFile
	 * @功能描述 :返回一源文件的大小
	 * @返回值类型 :long
	 *	@param origin
	 *	@param target
	 *	@return
	 *
	 * @创建日期 :2011-4-6
	 * @修改记录 :
	 */
	private long copyFile(File origin,File target){
		InputStream in=null;
		OutputStream out=null;
		long size=0;
		try{
			FileInputStream fis=new FileInputStream(origin);
			size=fis.available();//得到文件的可读字节长度
			in=new BufferedInputStream(fis ,BUFFER_SIZE);
			out=new BufferedOutputStream(new FileOutputStream(target),BUFFER_SIZE);
			byte[] buffer=new byte[BUFFER_SIZE];
			int length=0;
			while((length=in.read(buffer))>0){
				out.write(buffer, 0, length);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(in!=null){
				try{
					in.close();
				}
				catch(IOException e){}
			}
			if(out!=null){
				try{
					out.close();
				}
				catch(IOException e){}
			}
		}
		return size;
	}
	

	public File getImgFile() {
		return imgFile;
	}
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	public String getImgFileFileName() {
		return imgFileFileName;
	}
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	public String getImgFileContentType() {
		return imgFileContentType;
	}
	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public Object getModel() {
		return null;
	}
}