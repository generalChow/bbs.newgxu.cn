package cn.newgxu.bbs.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.newgxu.bbs.domain.activity.Tips;
import cn.newgxu.bbs.service.HolidayService;
import cn.newgxu.bbs.web.cache.BBSCache;

/**
 * @path valhalla_hx----cn.newgxu.bbs.service.impl.HolidayServiceImpl.java 
 * 
 * @author 集成显卡
 * @since 4.0.0
 * @version $Revision 1.1$
 * @date 2011-9-11
 * @describe  
 * 节日Service的实现类，这里，没有使用Spring的proxy，直接由indexAction调用
 */
@Service(value="holidayService")
public class HolidayServiceImpl implements HolidayService{
	public static final String HOLIDAY="dting";//这个只是标识而已，没有任何意义的哈

	/**
	 * 获取登录界面中当前需要显示的字符串
	 */
	public String getHoliday(HttpSession session) throws Exception {
		String result=null;
		List<Tips> list=BBSCache.getTipsCache();
		if(list.size()!=0){
			Tips t=list.get(0);
			result="<a href='"+t.getUrl()+"'><img src='"+t.getImgPath()+"'/></a>";
		}else{
			result="<a href=''><img src='/images/holiday/upload/2011CET.jpg'/></a>";
		}
		return result;
	}
}
