package cn.newgxu.bbs.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegisterWordFilter {

	// 过滤特殊字符
	public static boolean StringFilter(String str)
			throws PatternSyntaxException {
		// 只允许字母和数字,还有汉字
		String regEx = "[^a-zA-Z0-9^\u4E00-\u9FFF]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean LoginStringFilter(String str)
			throws PatternSyntaxException {
		String regEx = "[^a-zA-Z0-9-,-@-#-$-%-^-&-*-^-~\u4E00-\u9FFF]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	// 过滤特殊字符
	public static boolean intFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字,还有汉字
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
		|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c) == true) {
			} else {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {

		String str = "原文!";
		StringFilter(str);
		System.out.println(StringFilter(str));// ture表示有特殊字符包括空�?,false表示没有特殊字符

	}

}
