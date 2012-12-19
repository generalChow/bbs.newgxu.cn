package cn.newgxu.bbs.domain.vote;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "vote")
public class Vote extends JPAEntity {

	private static final long serialVersionUID = -5378284626221146520L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_vote")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	private boolean multi;

	@Column(name = "options_count")
	private int count;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="vote_id")
	@OrderBy("id")
	private List<VoteOption> options;

	@Column(name = "number_of_vote_users")
	private int numberOfVoteUsers;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public List<VoteOption> getOptions() {
		return options;
	}

	public void setOptions(List<VoteOption> options) {
		this.options = options;
	}

	public int getNumberOfVoteUsers() {
		return numberOfVoteUsers;
	}

	public void setNumberOfVoteUsers(int numberOfVoteUsers) {
		this.numberOfVoteUsers = numberOfVoteUsers;
	}

	// ------------------------------------------------
	public void addOption(String o) {
		if (options == null) {
			options = new LinkedList<VoteOption>();
		}
		VoteOption option = new VoteOption();
		option.setOption(o);
		option.setScore(0);
		option.setVote(this);
		options.add(option);
		setCount(options.size());
	}

	public boolean isVoted(User user) {
		try {
			SQ("from VoteUser vu where vu.vote = ?1 and vu.user = ?2", P(1,
					this), P(2, user));
			return true;
		} catch (ObjectNotFoundException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> getVotedUsers(Pagination p) {
		return (List<User>) Q(
				"from VoteUser vu where vu.vote = ?1 order by vu.id asc",
				P(1, this), p).getResultList();
	}

	public String getVoteDisplay() {
		StringBuffer result = new StringBuffer(
				"<script language=\"JavaScript\">");
		StringBuffer vote = new StringBuffer();
		StringBuffer votenum = new StringBuffer();
		int voters = 0;
		for (VoteOption option : getOptions()) {
			vote.append(StringUtils.replace(option.getOption(), "'", "\\'"))
					.append("|");
			votenum.append(option.getScore()).append("|");
			voters += option.getScore();
		}
		result.append("var vote='").append(
				StringUtils.removeEnd(vote.toString(), "|"));
		result.append("';var votenum='").append(
				StringUtils.removeEnd(votenum.toString(), "|"));
		result.append("';var votetype='").append(isMulti() ? 1 : 0).append(
				"';var voters='");
		result.append(voters).append("';</script>");
		return result.toString();
	}
	
	public void addVoteUser(User user) {
		VoteUser vu = new VoteUser();
		vu.setUser(user);
		vu.setVote(this);
		
		vu.save();
		
		this.numberOfVoteUsers++;
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "vote" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("multi", multi);
				put("count", count);
				put("options", options);
			}
		}.toString();
	}

}
