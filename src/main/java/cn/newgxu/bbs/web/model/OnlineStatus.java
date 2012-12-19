package cn.newgxu.bbs.web.model;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class OnlineStatus {
	
	private int total;
	
	private int numberOfForum;
	
	private int forumId;

	public int getNumberOfForum() {
		return numberOfForum;
	}

	public void setNumberOfForum(int numberOfForum) {
		this.numberOfForum = numberOfForum;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

}
