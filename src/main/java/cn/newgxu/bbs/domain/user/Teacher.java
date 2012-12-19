package cn.newgxu.bbs.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author tao
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "teacher")
public class Teacher extends JPAEntity {

	private static final long serialVersionUID = -2231731211179213181L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	private String name;

	private String IDCard;

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

	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Teacher getByIDCard(String IDCard)
			throws ObjectNotFoundException {

		return (Teacher) SQ("from Teacher t where t.IDCard = ?1", P(1, IDCard));
	}
}
