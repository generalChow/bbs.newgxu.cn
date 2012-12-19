package cn.newgxu.bbs.domain.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.newgxu.bbs.common.util.TimerUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author tao
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class TimeIterm {

	private String normalStart;
	private String normalend;//正常关闭时间
	
	private String week;
	private String weekStart;
	private String weekend;
	
	private String date;
	private String dateStart;
	private String dateend;
	public String getNormalStart() {
		return normalStart;
	}
	public void setNormalStart(String normalStart) {
		this.normalStart = normalStart;
	}
	public String getNormalend() {
		return normalend;
	}
	public void setNormalend(String normalend) {
		this.normalend = normalend;
	}
	
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getWeekStart() {
		return weekStart;
	}
	public void setWeekStart(String weekStart) {
		this.weekStart = weekStart;
	}
	public String getWeekend() {
		return weekend;
	}
	public void setWeekend(String weekend) {
		this.weekend = weekend;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateend() {
		return dateend;
	}
	public void setDateend(String dateend) {
		this.dateend = dateend;
	}
	
	public boolean isClose(long start , long end){
		
		long now = new Date().getTime();
		if(end<start){
			if(now>=end&&now<=start){
				return true;
			}else{
				return false ;
			}
		}
		
		if(now>=start&&now<=end)
		{
			return false;
		}
		
		return true;
	}
	
	public long dateToLong(String str){
		
		return TimerUtils.getTime(TimerUtils.getNowYMD("yyyy-MM-dd") + " " + str,"yyyy-MM-dd HH:mm");
	}
	
	@SuppressWarnings("deprecation")
	public boolean isClose(){
		
		if(date!=null&&date!=""&&dateStart!=null && dateend!=null&&dateStart!="" && dateend!=""){
			if(TimerUtils.getDate(date, "yyyy-MM-dd").getDay()==new Date().getDay()){
				return isClose(dateToLong(dateStart),dateToLong(dateend));
			}
		}
		if(week!=null&&week!=""&&weekStart!=null && weekend!=null&&weekStart!="" && weekend!=""){
			if(Integer.parseInt(week)==TimerUtils.getDayOfWeek()){
				return isClose(dateToLong(weekStart),dateToLong(weekend));
			}
		}
		if(normalStart!=null && normalend!=null&&normalStart!="" && normalend!=""){
	
			return isClose(dateToLong(normalStart),dateToLong(normalend));
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param time
	 * @param format
	 * 说明：day ,week , date 的format值分别是1,2,3
	 * @return
	 */
	public static List<TimeIterm > getTimeItermByString(String time, int format){
		List<TimeIterm > iterm = new ArrayList<TimeIterm >();
		if(time==null||time.equals(""))
			return iterm;
		switch (format) {
		case 1:
			String [] t1 = time.split(", ");
			for(int i = 0;i<t1.length;i++){
				TimeIterm iterm2 = new TimeIterm();
				iterm2.setNormalStart(t1[i]);
				iterm2.setNormalend(t1[++i]);
				iterm.add(iterm2);
			}
			break;
		case 2:
			String [] t2 = time.split(", ");
			for(int i = 0;i<t2.length;i++){
				TimeIterm iterm2 = new TimeIterm();
				iterm2.setWeek(t2[i]);
				iterm2.setWeekStart(t2[++i]);
				iterm2.setWeekend(t2[++i]);
				iterm.add(iterm2);
			}
			break;
		case 3:
			String [] t3 = time.split(", ");
			for(int i = 0;i<t3.length;i++){
				TimeIterm iterm2 = new TimeIterm();
				iterm2.setDate(t3[i]);
				iterm2.setDateStart(t3[++i]);
				iterm2.setDateend(t3[++i]);
				iterm.add(iterm2);
			}
			break;

		default:
			break;
		}
		return iterm;
	}
	
	public static  List<TimeIterm> getTimeItermByJson(String json){
		List<TimeIterm > iterm = new ArrayList<TimeIterm >();
		
		if(json==null)
			return iterm;
		Gson gson = new Gson();
		try{
			iterm = gson.fromJson(json, new TypeToken<List<TimeIterm>>(){}.getType());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return iterm;
	}
	
}
