package cn.newgxu.bbs.common.util;

import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ConfigParser extends DefaultHandler {
	/*
	 * props:用于存放解析器解析出来的的节点和节点对应的属性，为哈希表 currentName:当前节点的名称
	 * currentValue：用于存放当前节点所对应的属性值
	 */
	private Properties props;
	private String currentName;
	private StringBuffer currentValue = new StringBuffer();

	public ConfigParser() {
		this.props = new Properties();
	}

	public Properties getPrpos() {
		return this.props;
	}

	public String getCurrentName() {
		return currentName;
	}

	/*
	 * 读取<xxx>中的值xxx并将其付给qname，通知解析器解析当前节点对应的值。同时对currentValue缓冲器清空，用来保存当前qname对应属性值
	 * 。
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentValue.delete(0, currentValue.length());
		this.currentName = qName;
	}

	/*
	 * 读取<xxx></xxx>之间的属性值，并将其首先以字符形式保存至字符数组ch中，并记录对应长度，以确保以
	 * length为长度的字符为一个整体，然后讲字符数组中的内容按照length长度为整体加到currentValue缓冲器中
	 * 每次读取xml文件后只会在ch中保存当前所解析到的值，currentValue中也只会有当前的节点所对应的唯一值
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		currentValue.append(ch, start, length);
	}

	/*
	 * 当遇到</xxx>时，将当前的qname，和qname所对应的位于currentValue缓冲器中的值保存到props这个哈希表中去，供外部程序调用
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		props.put(qName.toLowerCase(), currentValue.toString().trim());
	}
}
