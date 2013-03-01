package cn.newgxu.bbs.domain.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.util.TimerUtils;
import cn.newgxu.jpamodel.JPAEntity;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.activity.Holiday.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-11-6
 * @describe  
 *	这个是对于在论坛中提示用户相关节日的实体。
 *	比如可以添加一个中秋的记录，那么到了指定的时间，就会在index页面中看到效果。
 *	具体的页面效果实现在   js/holiday.js 中
 *	
 */
@Entity
@Table(name="activity_holiday")
public class Tips extends JPAEntity{
	private static final long serialVersionUID=2137273424343L;
	
	private static final Logger l = LoggerFactory.getLogger(Tips.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="imgpath")
	private String imgPath;
	@Column
	private String url;//链接到的地址
	@Column
	private boolean ifLive;
	@Column(name="date_add")
	private Date addTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isIfLive() {
		return ifLive;
	}
	public void setIfLive(boolean ifLive) {
		this.ifLive = ifLive;
	}
	/**
	 * 获取一个Tips，他是可以显示，也是最新的
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Tips getCurrent(){
		List<Tips> list=(List<Tips>)Q("from Tips as h where ifLive =?1 order by addTime desc",P(1,true)).getResultList();
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	/**
	 * 
	 * @param p
	 * @param all
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Tips> getHolidays(Pagination p,boolean all){
		int size=0;
		try{
			size=((Long) SQ("select count(*) from Tips as h")).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		p.setRecordSize(size);
		return (List<Tips>)Q("from Tips as h order by addTime desc", p).getResultList();
	}
	
	/**
	 * 获取登陆页面上定时活动的图片，比如说cet倒计时什么的，注意遵守命名规定
	 * 比如说是英语等级考试，图片名称是cet1，cet2，这样的话，那么pattern就是cet，数字为截止日期。
	 * @param deadline 活动截止日期
	 * @param pattern 活动名称（这个规定取图片名称）
	 * @return 图片，假如不是活动，返回当前的。
	 * @author longkai
	 * @since 2012-12-18
	 */
	public static Tips getRandomHolidayImage(Calendar deadline, String pattern) {
		Calendar now = Calendar.getInstance();
		int yearOfDeadline = deadline.get(Calendar.YEAR);
		int yearOfNow = now.get(Calendar.YEAR);
		int offset = 0;
		if (yearOfDeadline - yearOfNow == 0) { // 如果是跨年活动
			offset = deadline.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR);
		} else if (yearOfDeadline - yearOfNow > 0) {
			offset += deadline.get(Calendar.DAY_OF_YEAR);
			offset += TimerUtils.isLeap(yearOfDeadline - 1) ? 366: 365;
			offset -= now.get(Calendar.DAY_OF_YEAR);
		} else {
			// 如果是过期了，就返回当前的
			return getCurrent();
		}
		l.info("距离 ”{}“活动还有 {} 天！", pattern, offset);
		String name = pattern + offset;
		String hql = "from Tips t where t.ifLive = true and t.name like '%?%' order by t.addTime desc".replace("?", name);
		return (Tips) Q(hql).setFirstResult(0).setMaxResults(1).getSingleResult();
	}
	
	/**
	 * 抓取当日的登陆页面显示图片，当日的图片名称包含yyyy-M-d这种模式，若没有，则显示默认的图片
	 * @since 2013-01-27
	 */
	public static Tips getTodayImage() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("-").append(month).append("-").append(day);
		String hql = "from Tips t where t.imgPath like '%?%'".replace("?", sb.toString());
		Tips tip = null;
		try {
			tip = (Tips) Q(hql).setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			l.error("查找当日图片失败!", e);
			return fetchDefaultImage();
		}
		return tip;
	}
	
	/**
	 * 抓取默认的登陆显示图片
	 * @since 2013-01-27
	 */
	public static Tips fetchDefaultImage() {
//		把默认的图片名称设置为 _default_
		String hql = "FROM Tips t where t.imgPath like '%_default_%'";
		Tips tip = null;
		try {
			tip = (Tips) Q(hql).setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tip;
	}
	
}
