package cn.newgxu.bbs.domain.calendar;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.calendar.Holiday.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-9-18
 * @describe  
 *	节日。<br />
 *  节日应该有固定节日（如每年的10.1是国庆节，这个是不关年的）还有一些老历节日（如中秋，每年的日期是不一样的）<br />
 *  还有系统节日和用户自定义节日之分（允许用户自己添加属于自己的节日）<br />
 *  可以设置公开度（对自己，对所有人，对好友----<span style='color:#a50100;'>这个需要以后添加了好友功能</span>）<br />
 *  
 */
@Entity
@Table(name="ywc_holiday")
public class UserHoliday extends JPAEntity{
	private static final long serialVersionUID=123727462343894862L;
	
	/*基本属性*/
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="title")
	private String name;
	@Column(name="date_exe")
	private String date;
	@Column(name="overt")
	private int overt;
	@Column(name="information")
	private String information;
	@Column(name="jointime")
	private Date joinTime;
	
	/*关联对象*/
	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

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
	
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
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
	 * 公开度:
	 * <ul style='color:#92bf28'>
	 * <li>0---对所有人</li>
	 * <li>1---对自己</li>
	 * <li>2---对好友【还不支持】</li>
	 * </ul>
	 */
	public int getOvert() {
		return overt;
	}
	public void setOvert(int overt) {
		this.overt = overt;
	}
	
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	/**
	 * 节日创建者
	 * @return
	 */
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public static UserHoliday getById(Integer id) throws ObjectNotFoundException{
		return (UserHoliday)getById(UserHoliday.class, id);
	}
	
	/**
	 * 获取具体日期的节日
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<UserHoliday> getUserHoliday(int year,int month,int day){
		return (List<UserHoliday>)Q("from UserHoliday where date_exe=?1",P(1, YWSCalendar.getDateInfo(year, month, day))).getResultList();
	}
	
	/**
	 * 获取某个区间内的用户节日<br />
	 * 区间：今天的前monthStep个月----后monthStep个月
	 * 
	 * @param monthStep
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<UserHoliday> getNearbyHoliday(int monthStep){
		int day[]=YWSCalendar.getDay(0, -monthStep, 0, YWSCalendar.YEAR_MONTH_DAY);//获取monthType前后对应的 年月日
		int next[]=YWSCalendar.getDay(0, monthStep, 0, YWSCalendar.YEAR_MONTH_DAY);//获取monthType月后对应的 年月日
		return (List<UserHoliday>)Q("from UserHoliday where date_exe>=?1 and date_exe<?2",
				P(1,YWSCalendar.getDateInfo(day[0], day[1], day[2])),P(2, YWSCalendar.getDateInfo(next[0], next[1], next[2]))).getResultList();
	}
	
	/**
	 * 获取用户的节日列表
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<UserHoliday> getUserHoliday(int uid,Pagination p){
		return (List<UserHoliday>)Q("from UserHoliday where user_id=?1 order by id desc",P(1,uid),p).getResultList();
	}
	public static int getUserHolidaySize(int uid){
		try{
			return ((Long)SQ("select count(*) from UserHoliday where user_id=?1",P(1,uid))).intValue();
		}catch(ObjectNotFoundException r){
			r.printStackTrace();
			return 0;
		}
	}
}
