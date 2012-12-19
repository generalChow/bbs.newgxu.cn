package cn.newgxu.bbs.domain.vote;

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

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "vote_webmaster")
public class VoteWebMaster extends JPAEntity {

	private static final long serialVersionUID = 1854231267429071014L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "nick")
	private String nick;
	
	@Column(name = "forum_name")
	private String forum_name;
	
	@Column(name = "vote_score")
	private int score;

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getForum_name() {
		return forum_name;
	}

	public void setForum_name(String forumName) {
		forum_name = forumName;
	}

	@SuppressWarnings("unchecked")
	public static List<VoteWebMaster> getWebMaster() {
		return Q("from VoteWebMaster order by score desc").getResultList();
	}
	
	public static VoteWebMaster getByUserId(int userid) throws ObjectNotFoundException{
		return (VoteWebMaster)SQ("from VoteWebMaster where user.id=?1",P(1,userid));
	}
	
	public static VoteWebMaster getById(int id) throws ObjectNotFoundException{
		return (VoteWebMaster)SQ("from VoteWebMaster where id=?1",P(1,id));
	}

}
