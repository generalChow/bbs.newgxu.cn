package cn.newgxu.bbs.domain.user;

import java.util.Date;

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
 * @author xjc
 */
@Entity
@Table(name = "reset_password_info")
public class ResetPasswordInfo extends JPAEntity{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="username")
	private String username;
	@Column(name="code")
	private String code;
	@Column(name="starttime")
	private Date startTime;
	@Column(name="endtime")
	private String endTime;
	@Column(name="complete")
	private int complete;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getComplete() {
		return complete;
	}
	public void setComplete(int complete) {
		this.complete = complete;
	}
	public static ResetPasswordInfo get(int id) throws ObjectNotFoundException {
		return (ResetPasswordInfo) getById(ResetPasswordInfo.class, id);
	}
	
	public static ResetPasswordInfo getByCode(String code)throws ObjectNotFoundException {
		return (ResetPasswordInfo)Q("from ResetPasswordInfo r where r.code=?1",P(1,code)).getSingleResult();
	}
	
	public boolean isValidate(){
		if(this.complete==1){
			return false;
		}
		if(System.currentTimeMillis()>Long.parseLong(this.endTime)){
			return false;
		}
		return true;
	}
}
