package cn.newgxu.bbs.domain.calendar;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.calendar.FixHoliday.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-19
 * @describe  
 *	固定节日，这个是系统管理员添加的。
 *	可以在论坛首页添加动画公告。
 */
@Entity
@Table(name="ywc_fix_holiday")
public class FixHoliday extends JPAEntity{
	private static final long serialVersionUID=1223727462343894862L;
	
	/*基本属性*/
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="title")
	private String name;
	@Column(name="date_exe")
	private String date;
	@Column(name="date_start")
	private String startDate;	
	@Column(name="imagepath")
	private String imagePath;
	@Column(name="information")
	private String information;
	@Column(name="color")
	private String color="#222222";
	@Column(name="jointime")
	private Date joinTime;

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
	
	/**
	 * 节日的日期（具体到天）<br />
	 * 格式：
	 * <font color="#a50100">yyyy-MM-dd     例如：
	 * 2011-09-18
	 * </font><br />
	 * 如果是固定节日，内是没有前面的yyyy，则是-MM-dd<br /><br />
	 * <font color="#92bf28">由于date是数据库关键字，这里的字段名为 date_exe</font>
	 */
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * 节日的大图，在论坛上  尺寸为   1004*1200 比较好。
	 * @return
	 */
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	/**
	 * 这个官方节日的开始日期，到达这个日期后，就会显示这个节日的图片
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * 节日的说明信息
	 * @return
	 */
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	/**
	 * 节日显示的颜色，默认是#222222
	 * @return
	 */
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public static FixHoliday getById(Integer id) throws ObjectNotFoundException{
		return (FixHoliday)getById(FixHoliday.class, id);
	}
	
	/**
	 * 获取某一天的官方节日列表，<br />
	 *	搜索条件为   带年份参数和不同年份参数两种 <br />
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<FixHoliday> getFixHoliday(int year,int month,int day){
		return (List<FixHoliday>)Q("from FixHoliday where date_exe=?1 or date_exe=?2",P(1, YWSCalendar.getDateInfo(year, month, day)),P(2,YWSCalendar.getDateInfo(0, month, day))).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static List<FixHoliday> getComingHoliday(int monthStep){
		int day[]=YWSCalendar.getToday(YWSCalendar.YEAR_MONTH_DAY);
		System.out.println(day);
		int next[]=YWSCalendar.getDay(0, monthStep, 0, YWSCalendar.YEAR_MONTH_DAY);//获取monthType月后对应的 年月日
		System.out.println(YWSCalendar.getDateInfo(day[0], day[1], day[2])+"          "+YWSCalendar.getDateInfo(next[0], next[1], next[2]));
		System.out.println("from FixHoliday where (date_exe>="+YWSCalendar.getDateInfo(day[0], day[1], day[2])+" and date_exe<"+YWSCalendar.getDateInfo(next[0], next[1], next[2])+") or (date_exe>="+YWSCalendar.getDateInfo(0, day[1], day[2])+" and date_exe<"+YWSCalendar.getDateInfo(0, next[1], next[2]));
		return (List<FixHoliday>)Q("from FixHoliday where (date_exe>=?1 and date_exe<?2) or (date_exe>=?3 and date_exe<?4)",
				P(1,YWSCalendar.getDateInfo(day[0], day[1], day[2])),P(2, YWSCalendar.getDateInfo(next[0], next[1], next[2])),
				P(3,YWSCalendar.getDateInfo(0, day[1], day[2])),P(4,YWSCalendar.getDateInfo(0, next[1], next[2]))).getResultList();
	}
}