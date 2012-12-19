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
@Table(name = "vote_webmaster_record")
public class VoteWebMasterRecord extends JPAEntity {

	private static final long serialVersionUID = -7158167170878955275L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

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

	@SuppressWarnings("unchecked")
	public static List<VoteWebMasterRecord> getRecords() {
		return Q("from VoteWebMasterRecord").getResultList();
	}

	public static boolean contain(User user) throws ObjectNotFoundException {
		return (Long) SQ(
				"select count(*) from VoteWebMasterRecord where user.id=?1", P(
						1, user.getId())) > 0 ? true : false;

	}

	public static Long getNumberOfUser() throws ObjectNotFoundException {
		return (Long) SQ("select count(*) from VoteWebMasterRecord");
	}

}
