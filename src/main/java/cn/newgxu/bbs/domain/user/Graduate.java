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
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "graduate_res")
public class Graduate extends JPAEntity {

	private static final long serialVersionUID = -2231731211179213181L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_graduate_res")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	private String xuehao;

	private String name;

	private String IDCard;

	// ------------------------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String card) {
		IDCard = card;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXuehao() {
		return xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}

	public static Graduate getByXuehao(String xuehao)
			throws ObjectNotFoundException {
		return (Graduate) SQ("from Graduate u where u.xuehao = ?1",
				P(1, xuehao));
	}

}
