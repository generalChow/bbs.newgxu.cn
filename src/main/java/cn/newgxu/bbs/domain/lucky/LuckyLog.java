package cn.newgxu.bbs.domain.lucky;

import java.util.Calendar;
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
import javax.persistence.Transient;

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.lucky.LuckyLog.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-3
 * @describe  
 *	用户参与幸运帖的记录，记录了 参与时间，用户，lucky_id，得分率，是否砸蛋，砸蛋结果
 */
@Entity
@Table(name="topic_lucky_log")
public class LuckyLog extends JPAEntity {
	private static final long serialVersionUID=19281362434343L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="shooting_mark")
	private int mark;
	@Column(name="used")
	private boolean used;
	@Column(name="date_create")
	private Date createTime;
	@Column(name="gift_id")
	private Integer result;
	@Column(name="gifted")
	private boolean gifted;
	@Column(name="is_best")
	private boolean best;
	@Column(name="code_number")
	private String codeNumber;
	
	@Transient
	private String giftInfo;
	
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="lucky_id")
	private Lucky lucky;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Lucky getLucky() {
		return lucky;
	}
	public void setLucky(Lucky lucky) {
		this.lucky = lucky;
	}
	/**是否已经将礼品分发！*/
	public boolean isGifted() {
		return gifted;
	}
	public void setGifted(boolean gifted) {
		this.gifted = gifted;
	}
	public boolean isBest() {
		return best;
	}
	public void setBest(boolean best) {
		this.best = best;
	}
	public String getCodeNumber() {
		return codeNumber;
	}
	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}
	/**
	 * 获取对应的礼品的描述
	 * @return
	 */
	public String getGiftInfo() {
		if(giftInfo==null){
			try{
				giftInfo=Lucky.singleGiftsInfo(LuckyGift.get(getResult()));
			}catch(Exception e){}
		}
		return giftInfo;
	}
	public void setGiftInfo(String giftInfo) {
		this.giftInfo = giftInfo;
	}
	
	
	public static LuckyLog get(int id) throws ObjectNotFoundException{
		return (LuckyLog)LuckyLog.getById(LuckyLog.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<LuckyLog> getLogByUser(User user){
		return (List<LuckyLog>)Q("from LuckyLog where user_id=?1",P(1, user.getId())).getResultList();
	}
	/**
	 * 根据用户与使用情况获取log列表
	 * @param user
	 * @param isUse
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<LuckyLog> getLogByUserAndUse(User user,Boolean isUse){
		return (List<LuckyLog>)Q("from LuckyLog where user_id=?1 and used=?2",P(1, user.getId()),P(2,isUse)).getResultList();
	}
	/**
	 * 检查用户是否已经获得过礼品
	 * @param user
	 * @return
	 */
	public static int sizeOfUserWinGift(User user,int lid){
		try {
			return ((Long) SQ("select count(*) from LuckyLog  where user_id=?1 and lucky_id=?2 and gift_id>0",P(1,user.getId()) , P(2,lid))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}
	
	/**
	 * 检查用户是否已经获得过金奖礼品
	 * @param user
	 * @return
	 */
	public static int sizeOfUserWinBestGift(User user,int lid){
		try {
			return ((Long) SQ("select count(*) from LuckyLog  where user_id=?1 and lucky_id=?2 and gift_id>0 and is_best>0",P(1,user.getId()) , P(2,lid))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<LuckyLog> getLogByLucky(Lucky lucky){
		return (List<LuckyLog>)Q("from LuckyLog where lucky_id=?1 and gift_id>0",P(1, lucky.getId())).getResultList();
	}
	
	/**
	 * 获取date 值所在的当天及下一天的日期，这个日期的时，分都是置0的
	 * @param date
	 * @return
	 */
	public static Date[] getDateWithinOneDay(Date date){
		Date result[]=new Date[2];
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 1);
		result[0]=c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 1);
		result[1]=c.getTime();
		return result;
	}
	
	public static int getLuckerCount(Date date){
		try {
			Date result[]=getDateWithinOneDay(date);
			return ((Long) SQ("select count(t) from LuckyLog t where t.createTime >= ?1 and t.createTime < ?2 and t.best=?3",P(1, result[0]) , P(2, result[1]),P(3,true))).intValue();
		} catch (ObjectNotFoundException e) {
			return 0;
		}
	}
}
