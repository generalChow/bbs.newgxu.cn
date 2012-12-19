package cn.newgxu.bbs.common.staticHtml;

import cn.newgxu.bbs.common.util.Util;


/**
 * @path valhalla_hx----cn.newgxu.bbs.common.staticHtml.StaticService.java 
 * 
 * @author 集成显卡
 * @since 4.0.0
 * @version $Revision 1.1$
 * @date 2011-6-4
 * @describe  静态化处理的service类
 *
 */
public class StaticService {

	//这个是记录了首页的访问次数，包括刷新，一旦次数超过MAX就重新生成首页的  静态文件
	private static int INDEX_COUNT=0;
	private static int MAX_INDEX_COUNT=100;
	
	/**
	 * 每次调用，index_count +1
	 * 生成html 后 置0
	 */
	public static void index(){
		StaticService.INDEX_COUNT++;
		System.out.println(StaticService.class.toString()+"===>"+StaticService.INDEX_COUNT+"/"+StaticService.MAX_INDEX_COUNT);
		if(StaticService.INDEX_COUNT>=StaticService.MAX_INDEX_COUNT){
			StaticManager staticManager=(StaticManager)Util.getBean("staticManager");
			staticManager.staticIndex(null);
			StaticService.INDEX_COUNT=0;
		}
	}
}
