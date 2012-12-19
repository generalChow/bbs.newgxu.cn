package cn.newgxu.bbs.domain.activity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.activity.Bachelor.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-31
 * @describe  
 * 光棍节的帖子配置
 */
@Entity
@Table(name="activity_bachelor")
public class Bachelor extends JPAEntity {
	private static final long serialVersionUID=2183323278223L;
	
	public static final int WAITING=0;
	public static final int REFUSE=1;
	public static final int SUCCESS=2;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="has_reply")
	private boolean hasReply;//是否有 lover 的回复
	@Column(name="state")
	private int state;//帖子当前的状态
	@Column(name="addtime")
	private Date addTime;
	
	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="topic_id")
	private Topic topic;
	
	/**表白的对象*/
	@OneToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="lover_id")
	private User lover;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isHasReply() {
		return hasReply;
	}

	public void setHasReply(boolean hasReply) {
		this.hasReply = hasReply;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public User getLover() {
		return lover;
	}

	public void setLover(User lover) {
		this.lover = lover;
	}
	
	public String getStatInfo(){
		switch(getState()){
		case WAITING:{
			return "正在等待回应...";
		}
		case REFUSE:
			return "已经被拒绝了=.=";
		default:
			return "！得到了回应！";
		}
	}
}
