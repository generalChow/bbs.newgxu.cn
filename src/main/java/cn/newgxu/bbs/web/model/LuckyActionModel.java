package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.lucky.Lucky;
import cn.newgxu.bbs.domain.lucky.LuckyLog;
import cn.newgxu.bbs.domain.user.User;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.LuckyActionModel.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-2
 * @describe  
 *
 */
public class LuckyActionModel {
	private int lid;
	private int logId;//记录id
	private String answer;//用户上传的答案
	
	private int rightCount;//答对题目数
	private int mark;//成绩，以%计算
	
	private int markLevel=0;//获取的礼品等级，为-1就是可以砸蛋，>0就是对应的礼品 id
	
	private boolean choice;
	private String message;
	
	private List<Area> areas;
	private Lucky lucky;
	private User user;
	private LuckyLog luckyLog;
	private List<LuckyLog> luckyLogs;
	
	public Lucky getLucky() {
		return lucky;
	}
	public void setLucky(Lucky lucky) {
		this.lucky = lucky;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getRightCount() {
		return rightCount;
	}
	public void setRightCount(int rightCount) {
		this.rightCount = rightCount;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Area> getAreas() {
		return areas;
	}
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	/**标记是否只是申请砸蛋页面*/
	public boolean isChoice() {
		return choice;
	}
	public void setChoice(boolean choice) {
		this.choice = choice;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LuckyLog getLuckyLog() {
		return luckyLog;
	}
	public void setLuckyLog(LuckyLog luckyLog) {
		this.luckyLog = luckyLog;
	}
	public List<LuckyLog> getLuckyLogs() {
		return luckyLogs;
	}
	public void setLuckyLogs(List<LuckyLog> luckyLogs) {
		this.luckyLogs = luckyLogs;
	}
	public int getMarkLevel() {
		return markLevel;
	}
	public void setMarkLevel(int markLevel) {
		this.markLevel = markLevel;
	}
}
