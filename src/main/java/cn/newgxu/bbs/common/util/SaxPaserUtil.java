package cn.newgxu.bbs.common.util;

import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SaxPaserUtil {
	/**
	 * 用于存放解析结果的哈希表
	 */
	private static Properties props;

	static {
		try {
			parse("src/resource/config/yy_config.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Properties getProps() {
		return props;
	}

	/**
	 * 用于分析xml文件的方法
	 */
	public static void parse(String filename) throws Exception {
		// 实例化解析器
		ConfigParser handler = new ConfigParser();
		// 实例化用于分析的工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 实例化分析类
		SAXParser parser = factory.newSAXParser();
		try {
			parser.parse(filename, handler);
			props = handler.getPrpos();
		} finally {
			/*
			 * 销毁已过期对象
			 */
			factory = null;
			parser = null;
			handler = null;
		}
	}

	/**
	 * 提供给外部程序调用的用于返回程序所对应需要的xml文件属性的方法
	 */
	public static String getElementValue(String elementName) {
		// elementValue:对应于elementName的节点的属性值
		String elementValue = null;
		elementValue = props.getProperty(elementName);
		// Iterator it=props.keySet().iterator();
		// while(it.hasNext()){
		// String str=(String) it.next();
		// System.out.println(str);
		// System.out.println(props.getProperty(str));
		// }
		return elementValue;
	}
}