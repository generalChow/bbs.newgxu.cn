package cn.newgxu.bbs.service;

import javax.servlet.http.HttpSession;

/**
 * @path valhalla_hx----cn.newgxu.bbs.service.HolidayService.java 
 * 
 * @author 集成显卡
 * @since 4.0.0
 * @version $Revision 1.1$
 * @date 2011-9-11
 * @describe  
 *	节日系统的service层。
 */
public interface HolidayService {

	/**
	 * 判断今天是不是有需要显示的节日<br />
	 * 从session中读取holiday字段信息，如果没有这个字段或者字段值为true，就会返回加载节日效果的代码，是一段js代码<br />
	 * 如果session中已经存在了这个字段，就将其设置为false<br />
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public String getHoliday(HttpSession session) throws Exception;
}
