package cn.newgxu.bbs.domain.vote;

import java.util.LinkedHashMap;

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

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "vote_option")
public class VoteOption extends JPAEntity {

	private static final long serialVersionUID = -8918815303661807179L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_vote_option")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@ManyToOne(cascade = { CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "vote_id")
	private Vote vote;

	@Column(name = "vote_option")
	private String option;

	@Column(name = "vote_score")
	private int score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}
	
	public void addScore(User user) {
		VoteOptionUser vou = new VoteOptionUser();
		vou.setOption(this);
		vou.setUser(user);
		
		vou.save();
		
		this.score++;
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "voteOption" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("vote", vote);
				put("option", option);
				put("score", score);
			}
		}.toString();
	}

}
