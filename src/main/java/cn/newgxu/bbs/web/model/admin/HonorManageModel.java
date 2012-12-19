package cn.newgxu.bbs.web.model.admin;

import java.util.List;

import cn.newgxu.bbs.domain.Honor;
import cn.newgxu.bbs.web.model.PaginationBaseModel;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HonorManageModel extends PaginationBaseModel {
	private int id;
	private String name;
	private int type;
	private String remark;
	
	private List<Honor> honors;
	private Honor honor;
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Honor> getHonors() {
		return honors;
	}

	public void setHonors(List<Honor> honors) {
		this.honors = honors;
	}

	public Honor getHonor() {
		return honor;
	}
	public void setHonor(Honor honor) {
		this.honor = honor;
	}
}
