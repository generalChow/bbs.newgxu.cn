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
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */

@Entity
@Table(name = "honor")
public class Honor extends JPAEntity {
	

	private static final long serialVersionUID = 5166233887545932757L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private int type;
	
	private String remark;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_honor", joinColumns = @JoinColumn(name = "honor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static Honor getHonorById(int id) throws ObjectNotFoundException {
		return (Honor) SQ("from Honor h where h.id = ?1", P(1, id));
	}

	@SuppressWarnings("unchecked")
	public static List<Honor> getHonors() {
		return (List<Honor>) Q("from Honor h").getResultList();
	}
	
}
