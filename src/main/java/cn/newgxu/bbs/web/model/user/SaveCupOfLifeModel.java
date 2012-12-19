package cn.newgxu.bbs.web.model.user;

import java.util.List;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.FootBallTeam;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author daodaoyu
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SaveCupOfLifeModel {
	
	private int id;
	
	private User user;
	
	private List<FootBallTeam> footBallTeams;
	
	private List<Area> areas;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<FootBallTeam> getFootBallTeams() {
		return footBallTeams;
	}

	public void setFootBallTeams(List<FootBallTeam> footBallTeams) {
		this.footBallTeams = footBallTeams;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
}
