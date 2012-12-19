package cn.newgxu.bbs.web.webservice.util;

import java.util.Vector;

/**
 * @author 集成显卡
 * 这个类可以加密当前时间和解密当前时间，
 * 比如 当前时间为   13564651351
 * 那么加密后 会是 字母形式，解密就是还原
 * 那么别人看到的就是类似乱码的东东
 */
public class WebTimeUtil {
	
	/**
	 * 加密或者解密，其实就是简单的映射一下
	 * type为true时是加密
	 */
	public static String getOriginList(String origin,boolean type){
		Vector<String> vector=WebTimeUtil.getVector();
		origin=WebTimeUtil.toOver(origin);//先将字符串翻转
		System.out.println(origin);
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<origin.length();i++){
			int index=vector.indexOf(String.valueOf(origin.charAt(i)));
			sb.append(type?vector.get(index+10):vector.get(index-10));
		}
		return sb.toString();
	}
	
	//翻转origin
	private static String toOver(String origin){
		StringBuffer sb=new StringBuffer();
		for(int i=origin.length()-1;i>=0;i--)
			sb.append(origin.charAt(i));
		return sb.toString();
	}
	
	private static Vector<String> getVector(){
		String temp[]={"0","1","2","3","4","5","6","7","8","9","z","f","g","a","r","m","h","t","o","c"};
		Vector<String> vector=new Vector<String>();
		for(int i=0;i<temp.length;i++)
			vector.add(temp[i]);
		return vector;
	}
	
	public static boolean isAllow(String origin){
		boolean result=System.currentTimeMillis()-Long.valueOf(WebTimeUtil.getOriginList(origin, false))<=(30*1000);
		if(result){
			System.out.println("时间有允许范围内");
		}
		else{
			System.out.println("时间已经超过允许范围");
		}
		return result;
	}
	
	public static void main(String a[]){
		String temp=String.valueOf(System.currentTimeMillis());
		System.out.println(temp);
		System.out.println(WebTimeUtil.getOriginList("gththfoagrzaf", false));
	}
}
