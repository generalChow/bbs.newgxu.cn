package cn.newgxu.bbs.common.config;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;

/**
 * @path valhalla_hx----cn.newgxu.bbs.common.config.ForumConfig.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-6
 * @describe  
 *
 */
public class ForumConfig {
	private static Date NEW_EDITOR_DATE=null;/**新编辑器启用的时间*/
	
	private static final String CONFIG_FILE_NAME="forum_config.xml";
	private static String CONFIG_PATH=null;
	
	public static boolean NEW_5_0=true;//5.0新版本
	
	//配置文件中使用到的命名
	private static final String[] NAMES={"editor_year","editor_month","editor_day","editor_hour","editor_minute","cache_timeout"};
	
	private static Map<String,String> config;//配置的map
	
	/**
	 * 获取论坛的配置<br />
	 * 如果没有配置信息，就从配置文件中读取
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getConfig(String key) throws Exception{
		if(config==null)
			initConfig();
		return config.get(key);
	}
	
	/**
	 * 设置配置信息，直接覆盖
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public static void setConfig(String key,String value)throws Exception{
		if(config==null)
			initConfig();
		config.put(key, value);
	}
	
	/**
	 * 获取config信息
	 */
	private static void initConfig() throws Exception{
		Document document=XMLUtil.getXMLDocument(getPath());
		config=new HashMap<String,String>();
		for(String name:NAMES){
			Node minExpNode=document.selectSingleNode("/forum/"+name);
			config.put(name, minExpNode.getText());
		}
	}
	
	/**
	 * 保存配置，更新到文件中
	 */
	public static void updateConfig() throws Exception {
		Document document=XMLUtil.getXMLDocument(getPath());
		for(String name:NAMES){
			Node node=document.selectSingleNode("/forum/"+name);
			node.setText(config.get(name));
		}
		
		//写数据
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(getPath()), XMLUtil.getOutputFormat());
		xmlWriter.write(document);
		xmlWriter.close();
	}
	
	public static boolean isBeforeNewEfitor(Date replyDate){
		boolean result=getNewEditorDate().after(replyDate);
		return result;
	}
	
	public static Date getNewEditorDate(){
		if(NEW_EDITOR_DATE==null){
			Calendar c=Calendar.getInstance();
			try{
				int year=Integer.valueOf(getConfig("editor_year"));
				int month=Integer.valueOf(getConfig("editor_month"))-1;
				int day=Integer.valueOf(getConfig("editor_day"));
				int hour=Integer.valueOf(getConfig("editor_hour"));
				int minute=Integer.valueOf(getConfig("editor_minute"));
				c.set(year,month, day, hour, minute, 1);
			}catch(Exception e){
				e.printStackTrace();
				//如果异常，则使用默认的设置
				c.set(2011,9, 11, 3, 30, 1);
			}
			NEW_EDITOR_DATE=c.getTime();
		}
		return NEW_EDITOR_DATE;
	}
	
	private static final String getPath() throws Exception{
		if(CONFIG_PATH==null){
			try{
				CONFIG_PATH=Thread.currentThread().getContextClassLoader().getResource("/config/").toURI().getPath() + CONFIG_FILE_NAME;
			}catch(Exception e){
				//window下
				CONFIG_PATH=Thread.currentThread().getContextClassLoader().getResource("\\config\\").toURI().getPath() + CONFIG_FILE_NAME;
			}
		}
		return CONFIG_PATH;
	}
	
	/**测试一下*/
	public static void main(String a[]) throws Exception{
		System.out.println(ForumConfig.getConfig("cache_timeout"));
	}
}
