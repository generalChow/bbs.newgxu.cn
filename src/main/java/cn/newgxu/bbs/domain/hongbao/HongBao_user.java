package cn.newgxu.bbs.domain.hongbao;

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

import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 用户领取礼物记录
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "hongbao_user")
public class HongBao_user extends JPAEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	/** 上次领取的user */
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user;

	/** 上次领取的礼物 */
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "hongbaoid")
	private HongBao hongbao;

	/** 上次礼物领取时间 */
	private Date lasttime;

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

	public HongBao getHongbao() {
		return hongbao;
	}

	public void setHongbao(HongBao hongbao) {
		this.hongbao = hongbao;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	@SuppressWarnings("unchecked")
	public static List<HongBao_user> getHongbaoByUser(User user)
			throws ObjectNotFoundException {
		List<HongBao_user> users = Q("from HongBao_user h where user.id=?1",
				P(1, user.getId())).getResultList();
		if (users.size() == 0) {
			return null;
		}
		return users;
	}

}
