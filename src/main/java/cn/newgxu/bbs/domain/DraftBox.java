package cn.newgxu.bbs.domain;

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

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 叨叨雨
 * @version 0.0.1
 * @since 2009-12-02
 * 
 */
@Entity
@Table(name = "draftbox")
public class DraftBox extends JPAEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	private int face;
	@Column(name = "select_string", length = 20)
	private String select;

	@Column(name = "title", length = 255)
	private String title;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "forum_id")
	private Forum forum;

	@Column(name = "content", length = 20000)
	private String content;

	private int ispub;

	private String zt;

	@Column(name = "save_time")
	private Date savetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIspub() {
		return ispub;
	}

	public void setIspub(int ispub) {
		this.ispub = ispub;
	}

	public Date getSavetime() {
		return savetime;
	}

	public void setSavetime(Date savetime) {
		this.savetime = savetime;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	// -----------------------------------------------------------

	public static DraftBox get(int draftid) throws ObjectNotFoundException {

		return (DraftBox) getById(DraftBox.class, draftid);
	}

	@SuppressWarnings("unchecked")
	public static List<DraftBox> getDraftBoxes(User user, Pagination p)
			throws ObjectNotFoundException {

		return (List<DraftBox>) Q(
				"from DraftBox d where userid= ?1  order by save_time desc",
				P(1, user), p).getResultList();
	}

	public static void delDraftBox(int draftid) throws ObjectNotFoundException {
		get(draftid).delete();
	}

}
