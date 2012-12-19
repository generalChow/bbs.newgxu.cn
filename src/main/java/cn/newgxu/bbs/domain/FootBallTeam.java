package cn.newgxu.bbs.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author daodaoyu
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name="football_team")
@SuppressWarnings("serial")
public class FootBallTeam extends JPAEntity{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String minImg;
	
	private String maxImg;
	
	private String remark;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "myfootball_team", joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> users; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMinImg() {
		return minImg;
	}

	public void setMinImg(String minImg) {
		this.minImg = minImg;
	}

	public String getMaxImg() {
		return maxImg;
	}

	public void setMaxImg(String maxImg) {
		this.maxImg = maxImg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	//----------------------------------------

	public static FootBallTeam getFootBallTeamById(int id) throws ObjectNotFoundException {
		return (FootBallTeam) SQ("from FootBallTeam f where f.id = ?1", P(1, id));
	}

	@SuppressWarnings("unchecked")
	public static List<FootBallTeam> getFootBallTeams() {
		return (List<FootBallTeam>) Q("from FootBallTeam h").getResultList();
	}
	
	public static void deleteFootBallTeam(User user)throws ObjectNotFoundException{
		user.setFootBallTeams(null);
		user.save();
	}

}
