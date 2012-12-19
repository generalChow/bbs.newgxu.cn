package cn.newgxu.bbs.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.newgxu.bbs.domain.sys.Param;
import cn.newgxu.bbs.domain.sys.TimeIterm;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 管理论坛开放时间的工具类
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
//public class ForumControlUtil {
////	public static String FORUM_CLOSED_STRING="0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
//	
////	public static int[] ARRAY;
//	public static int time[]=null;
//	private static boolean isClosed = true;
//	static{
//		
//		try {
//			time = (int[]) ParamManager.getParam("Forum_Close_Time");
//		} catch (ObjectNotFoundException e) {
//			e.printStackTrace();
//		}
////		ARRAY=new int[24];
////		int start=time[0];
////		int start_minute = time[1];
////		int end=time[2];
////		int end_minute = time[3];
//		
////		for(int i=0;i<24;i++){
////			ARRAY[i]=(i<=end&&i>=start)?1:0;
////			System.out.print(ARRAY[i]+"=");
////		}
//		
//		
//	}
////	public static int[] getForumIsClosedArray(){
////		return ARRAY;
////	}
//	
//	public static boolean isForumClose(){
//		if(time==null){
//			try {
//				time = (int[]) ParamManager.getParam("Forum_Close_Time");
//			} catch (ObjectNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//		/*
//		 * 获取当前时间
//		 */
//		int hour =TimerUtils.getHour(); 
//		//int hour =6; 
//		int Min =TimerUtils.getMin();
//		/*
//		 * 如果是星期6是1点关
//		 */
//		int week = TimerUtils.getDayOfWeek();
//		
//		int startTime = time[0]*60 + time[1];
//		int endTime = time[2]*60 + time[3];
//		if(week==1){
//			endTime = (time[2]+1)*60 + time[3];
//		}
//		/*0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23
//		 * 如果关闭时间小于打开时间，则反向处理
//		 */
//		if(time[0]>time[2]){
//			if((hour*60+Min) <=startTime&&(hour*60+Min)>=endTime){
//				isClosed = true;
//			}else{
//				isClosed = false;
//			}
//		}else{
//			if((hour*60+Min) >=startTime&&(hour*60+Min)<=endTime){
//				isClosed = false;
//			}else{
//				isClosed = true;
//			}
//		}
//		return isClosed;
//	}
//}


public class ForumControlUtil {
	
	private static boolean isClosed = false;
	private static List<TimeIterm> itermDay = new ArrayList<TimeIterm>();
	private static List<TimeIterm> itermWeek = new ArrayList<TimeIterm>();
	private static List<TimeIterm> itermDate = new ArrayList<TimeIterm>();

	static{
		init();
	}
	
	public static void init(){
		try {
			Param param = Param.getByCode("Forum_Close_Time");
			
			try{
				Gson gson = new Gson();
				Map<String, List<TimeIterm>> map = gson.fromJson(param.getPara_value(), new TypeToken<Map<String, List<TimeIterm>>>(){}.getType());
				for(String s : map.keySet()){
					if(s.equals("day")){
						itermDay = map.get(s);
					}
					if(s.equals("week")){
						itermWeek = map.get(s);
					}
					if(s.equals("date")){
						itermDate = map.get(s);
					}
				}
				}catch (Exception e) {
					System.out.println("数据库暂无控制时间的数据或者数据有错!");
				}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isForumClose(){
		
		for(TimeIterm item : itermDate){
			if(item.isClose()){
				return true;
			}
		}
		
		for(TimeIterm item : itermWeek){
			if(item.isClose()){
				return true;
			}
		}
		for(TimeIterm item : itermDay){
			if(item.isClose()){
				return true;
			}
		}
		
		return isClosed;
	}
}
