package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.domain.Area;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class BoardStateModel {

	private int areaID = 0;

	private String action;

	private String description;

	private Area area;

	private int totalNum;

	private int areaUserOnline;

	private int areaVisitOnline;

	private List<BoardView> boards;

	private List<Area> areas;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getAreaUserOnline() {
		return areaUserOnline;
	}

	public void setAreaUserOnline(int areaUserOnline) {
		this.areaUserOnline = areaUserOnline;
	}

	public int getAreaVisitOnline() {
		return areaVisitOnline;
	}

	public void setAreaVisitOnline(int areaVisitOnline) {
		this.areaVisitOnline = areaVisitOnline;
	}

	public List<BoardView> getBoards() {
		return boards;
	}

	public void setBoards(List<BoardView> boards) {
		this.boards = boards;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
}
