package cn.newgxu.bbs.domain.sys;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "sys_para")
public class Param extends JPAEntity {

	private static final long serialVersionUID = 6274932469936130068L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_param")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "para_code", length = 30)
	private String para_code;

	@Column(name = "para_value", length = 2000)
	private String para_value;

	private boolean sts;
	private String remark;
	private int data_type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPara_code() {
		return para_code;
	}

	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}

	public String getPara_value() {
		return para_value;
	}

	public void setPara_value(String para_value) {
		this.para_value = para_value;
	}

	public int getData_type() {
		return data_type;
	}

	public void setData_type(int data_type) {
		this.data_type = data_type;
	}

	public boolean isSts() {
		return sts;
	}

	public void setSts(boolean sts) {
		this.sts = sts;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// ------------------------------------------------

	public static Param get(int paramId) throws ObjectNotFoundException {
		return (Param) getById(Param.class, paramId);
	}

	public static Param getByCode(String code) throws ObjectNotFoundException {
		return (Param) SQ("from Param a where a.para_code = ?1 ", P(1, code),
				new Pagination(1, 1));
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "param" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("para_code", para_code);
				put("data_type", data_type);
				put("para_value", para_value);
				put("sts", sts);
				put("remark", remark);
			}
		}.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Param) {
			return ((Param) obj).getId() == this.getId();
		}
		return false;
	}

}
