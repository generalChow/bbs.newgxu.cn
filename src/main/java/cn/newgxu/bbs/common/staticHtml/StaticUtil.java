package cn.newgxu.bbs.common.staticHtml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @path valhalla_hx----cn.newgxu.bbs.common.util.StaticUtil.java 
 * 
 * @author 集成显卡
 * @since 4.0.0
 * @version $Revision 1.1$
 * @date 2011-6-4
 * @describe  页面静态化的功能类
 *
 */
public class StaticUtil {
	
	/**
	 * @方法名称 :creatHtml
	 * @功能描述 :写出html静态文件
	 * @返回值类型 :boolean
	 *	@param templatePath 模版路径
	 *	@param templateName 模版名
	 *	@param saveName 保存的文件名
	 *	@param data 数据
	 *	@return
	 *
	 * @创建日期 :2011-6-1
	 * @修改记录 :
	 */
	public static boolean creatHtml(String templatePath,String templateName,String saveName,Map<?,?> data){
		try{
			Configuration cfg=new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(templatePath));
			//cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template template=cfg.getTemplate(templateName, "UTF-8");
			Writer out=new OutputStreamWriter(new FileOutputStream(saveName));
			template.process(data, out);
			out.flush();
			out.close();
			System.out.println("成功生成文件"+templatePath+"/"+saveName);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @方法名称 :creatHtml
	 * @功能描述 :根据 servletconext 上下文 生成html静态文件
	 * @返回值类型 :boolean
	 *	@param templatePath 模版路径
	 *	@param templateName 模版名
	 *	@param saveName 保存的文件名
	 *	@param data 数据
	 *	@return
	 *
	 * @创建日期 :2011-6-4
	 * @修改记录 :
	 */
	public static boolean creatHtml(ServletContext context,String templateName,String saveName,Map<?,?> data){
		try{
			Configuration cfg=new Configuration();
			cfg.setServletContextForTemplateLoading(context, "/");
			cfg.setEncoding(Locale.getDefault(), "utf-8");
			Template template=cfg.getTemplate(templateName, "UTF-8");
			System.out.println("开始生成文件"+context.getRealPath("/")+saveName);
			Writer out=new OutputStreamWriter(new FileOutputStream(new File(context.getRealPath("/")+saveName)));
			template.process(data, out);
			out.flush();
			out.close();
			System.out.println("成功生成文件"+saveName);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
