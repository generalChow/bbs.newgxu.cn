package cn.newgxu.bbs.common.util;

import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;

/**
 * <p>
 * Title:字符编码工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: flashman.com.cn Copyright (c) 2005
 * </p>
 * <p>
 * Company: flashman.com.cn
 * </p>
 * 
 * @author: jeffzhu
 * @version 1.0
 */
public class UrlEncodingTools {
	/**
	 * 转换编码 ISO-8859-1到GB2312
	 * 
	 * @param text
	 * @return
	 */
	public String ISO2GB(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("ISO-8859-1"), "GB2312");
		} catch (UnsupportedEncodingException ex) {
			result = ex.toString();
		}
		return result;
	}

	/**
	 * 转换编码 GB2312到ISO-8859-1
	 * 
	 * @param text
	 * @return
	 */
	public String GB2ISO(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("GB2312"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Utf8URL编码
	 * 
	 * @param s
	 * @return
	 */
	public String Utf8URLencode(String text) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {

			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {

				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
				}

				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}

			}
		}

		return result.toString();
	}

	/**
	 * Utf8URL解码
	 * 
	 * @param text
	 * @return
	 */
	public String Utf8URLdecode(String text) {
		String result = "";
		int p = 0;

		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1)
				return text;

			while (p != -1) {
				result += text.substring(0, p);
				text = text.substring(p, text.length());
				if (text == "" || text.length() < 9)
					return result;

				result += CodeToWord(text.substring(0, 9));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}

		}

		return result + text;
	}

	/**
	 * utf8URL编码转字符
	 * 
	 * @param text
	 * @return
	 */
	private String CodeToWord(String text) {
		String result;

		if (Utf8codeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				result = null;
			}
		} else {
			result = text;
		}

		return result;
	}

	/**
	 * 编码是否有效
	 * 
	 * @param text
	 * @return
	 */
	private boolean Utf8codeCheck(String text) {
		String sign = "";
		if (text.startsWith("%e"))
			for (int p = 0; p != -1; ) {
				p = text.indexOf("%", p);
				if (p != -1)
					p++;
				sign += p;
			}
		return sign.equals("147-1");
	}

	/**
	 * 是否Utf8Url编码
	 * 
	 * @param text
	 * @return
	 */
	public boolean isUtf8Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p > 9) {
			text = text.substring(p, p + 9);
		}
		return Utf8codeCheck(text);
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//
//		UrlEncodingTools charTools = new UrlEncodingTools();
//
//		String url;
//
//		url = "http://www.google.com/search?hl=zh-CN&newwindow=1&q=%E8%A1%80%E8%82%89%E6%A8%AA%E9%A3%9E&btnG=%E6%90%9C%E7%B4%A2&lr=";
//		if (charTools.isUtf8Url(url)) {
//			System.out.println(charTools.Utf8URLdecode(url));
//		} else {
//			System.out.println(URLDecoder.decode(url));
//		}
//
//		url = "http://localhost:8080/user/search_do.yws?type=1&keywords=%C1%E8%B3%BF%C8%FD%B5%E3%B5%C4%B7%E7";
//		try {
//			System.out.println(URLEncoder.encode("叨叨雨", "UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		if (charTools.isUtf8Url(url)) {
//			System.out.println(charTools.Utf8URLdecode(url));
//		} else {
//			System.out.println(URLDecoder.decode(url));
//		}
//
//	}
}
