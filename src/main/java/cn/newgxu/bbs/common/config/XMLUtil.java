package cn.newgxu.bbs.common.config;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

/**
 * @path valhalla_hx----cn.newgxu.bbs.common.config.XMLUtil.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-10
 * @describe  
 *
 */
public class XMLUtil {

	/**
	 * 获取 xml 文件中的 document
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Document getXMLDocument(String path) throws Exception{
		Document document=null;
		try{
			File configFile=new File(path);
			SAXReader saxReader=new SAXReader();
			document=saxReader.read(configFile);
		}catch(Exception e){
			throw e;
		}
		return document;
	}
	
	/**
	 * 获取一个 OutputFormat
	 * @return
	 */
	public static OutputFormat getOutputFormat(){
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();// 设置XML文档输出格式
		outputFormat.setEncoding("UTF-8");// 设置XML文档的编码类型
		outputFormat.setIndent(true);// 设置是否缩进
		outputFormat.setIndent("	");// 以TAB方式实现缩进
		outputFormat.setNewlines(true);// 设置是否换行
		return outputFormat;
	}
	
}
