package cn.newgxu.bbs.web.model.hongbao;

import java.util.Date;

import cn.newgxu.bbs.domain.hongbao.HongBao;
import cn.newgxu.bbs.domain.user.User;


/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HongbaoRecieveModel {
	
	private String time;
	
	private String today;
	
	private User user;
	
	private int isRecieveToday;
	
	private Date lasttime;
	
	private int isFestivalToday;
	
	private HongBao hongbao;

	private String nextFestival;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getNextFestival() {
		return nextFestival;
	}

	public void setNextFestival(String nextFestival) {
		this.nextFestival = nextFestival;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public int getIsFestivalToday() {
		return isFestivalToday;
	}

	public void setIsFestivalToday(int isFestivalToday) {
		this.isFestivalToday = isFestivalToday;
	}

	public HongBao getHongbao() {
		return hongbao;
	}

	public void setHongbao(HongBao hongbao) {
		this.hongbao = hongbao;
	}

	public int getIsRecieveToday() {
		return isRecieveToday;
	}

	public void setIsRecieveToday(int isRecieveToday) {
		this.isRecieveToday = isRecieveToday;
	}
	
}
