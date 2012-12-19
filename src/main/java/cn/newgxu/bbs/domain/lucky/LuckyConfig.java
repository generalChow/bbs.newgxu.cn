package cn.newgxu.bbs.domain.lucky;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.lucky.LuckyConfig.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-2
 * @describe  
 * 幸运帖的设置<br />
 * 默认是没有值的，当需要时，会从xml配置文件中读取相应的信息到内存中
 */
public class LuckyConfig {
	private static final String CONFIG_FILE_NAME="lucky_config.xml";//幸运帖配置文件名称
	
	/**
	 * 因为 LuckySubject 中的题目选项是保存为一个 字段值，又不能避免出现多个选项的情况，
	 * 所以，每个选项间以  SPLIT_CHAR 分开。
	 * 使用了一个很特殊的字符串
	 */
	public static final String SPLIT_CHAR="#!#!!%!!!%";
	public static final String DAY_COUNT_CONFIG="day";
	public static final String ALL_COUNT_CONFIG="all";
	
	private static Map<String,String> luckyConfig;
	
	/**
	 * 获取config信息
	 */
	private static void initConfig() throws Exception{
		String path=Thread.currentThread().getContextClassLoader().getResource("\\config\\").toURI().getPath() + CONFIG_FILE_NAME;
		Document document=getXMLDocument(path);
		Node minExpNode=document.selectSingleNode("/lucky/minExperience");
		Node probabilityNode=document.selectSingleNode("/lucky/probability");
		Node createMoneyNode=document.selectSingleNode("/lucky/createMoney");
		luckyConfig=new HashMap<String,String>();
		luckyConfig.put("minExperience", minExpNode.getText());
		luckyConfig.put("probability", probabilityNode.getText());
		luckyConfig.put("createMoney", createMoneyNode.getText());
	}
	
	/**
	 * 保存配置，更新到文件中
	 */
	public static void updateConfig() throws Exception {
		String path=Thread.currentThread().getContextClassLoader().getResource("\\config\\").toURI().getPath() + CONFIG_FILE_NAME;
		Document document=getXMLDocument(path);
		Node minExpNode=document.selectSingleNode("/lucky/minExperience");
		Node probabilityNode=document.selectSingleNode("/lucky/probability");
		Node createMoneyNode=document.selectSingleNode("/lucky/createMoney");
		minExpNode.setText(luckyConfig.get("minExperience"));
		probabilityNode.setText(luckyConfig.get("probability"));
		createMoneyNode.setText(luckyConfig.get("createMoney"));
		
		//写数据
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(path), getOutputFormat());
		xmlWriter.write(document);
		xmlWriter.close();
	}
	
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
	
	/**
	 * 获取幸运帖的配置<br />
	 * 如果没有配置信息，就从配置文件中读取
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getConfig(String key) throws Exception{
		if(LuckyConfig.luckyConfig==null)
			LuckyConfig.initConfig();
		return LuckyConfig.luckyConfig.get(key);
	}
	
	/**
	 * 设置配置信息，直接覆盖
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public static void setConfig(String key,String value)throws Exception{
		if(LuckyConfig.luckyConfig==null)
			LuckyConfig.initConfig();
		LuckyConfig.luckyConfig.put(key, value);
	}
	
	/**测试一下*/
	public static void main(String a[]) throws Exception{
		//System.out.println(LuckyConfig.getConfig("probability"));
		//LuckyConfig.setConfig("probability", "0.2");
		//System.out.println(LuckyConfig.getConfig("probability"));
		//LuckyConfig.updateConfig();
		
		LuckySubject ls=new LuckySubject();
		ls.setOptionList(new String[]{"演讲辩论社","绿叶社","青年志愿者协会","女性商业协会"});
		System.out.println(ls.getOptionString());
		System.out.println(ls.getOptionList());
	}
}