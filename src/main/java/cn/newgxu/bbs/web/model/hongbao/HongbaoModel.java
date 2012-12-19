package cn.newgxu.bbs.web.model.hongbao;

import java.util.List;

import cn.newgxu.bbs.domain.hongbao.HongBao_content;


/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HongbaoModel {
	
	private int id;

	private String name;
	
	private String description;
	
	private String history;
	
	private int time;
	
	private int contentType;
	
	private int valid;
	
	private List<HongBao_content> hongbaoTypes;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public List<HongBao_content> getHongbaoTypes() {
		return hongbaoTypes;
	}

	public void setHongbaoTypes(List<HongBao_content> hongbaoTypes) {
		this.hongbaoTypes = hongbaoTypes;
	}

}
