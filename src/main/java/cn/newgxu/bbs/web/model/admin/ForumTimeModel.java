package cn.newgxu.bbs.web.model.admin;

import java.util.ArrayList;
import java.util.List;

import cn.newgxu.bbs.domain.sys.Param;
import cn.newgxu.bbs.domain.sys.TimeIterm;

/**
 * 
 * @author tao
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ForumTimeModel {

	private String para_code ="Forum_Close_Time";

	private String day="";
	private String week="";
	private String date="";
	
	private Param timeParam ;
	
	private List<TimeIterm> itermDay = new ArrayList<TimeIterm>();
	private List<TimeIterm> itermWeek = new ArrayList<TimeIterm>();
	private List<TimeIterm> itermDate = new ArrayList<TimeIterm>();

	public String getPara_code() {
		return para_code;
	}

	public Param getTimeParam() {
		return timeParam;
	}

	public void setTimeParam(Param timeParam) {
		this.timeParam = timeParam;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<TimeIterm> getItermDay() {
		return itermDay;
	}

	public void setItermDay(List<TimeIterm> itermDay) {
		this.itermDay = itermDay;
	}

	public List<TimeIterm> getItermWeek() {
		return itermWeek;
	}

	public void setItermWeek(List<TimeIterm> itermWeek) {
		this.itermWeek = itermWeek;
	}

	public List<TimeIterm> getItermDate() {
		return itermDate;
	}

	public void setItermDate(List<TimeIterm> itermDate) {
		this.itermDate = itermDate;
	}

}
