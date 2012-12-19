package cn.newgxu.bbs.domain;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ReplyLine {

	private Reply reply;

	// //回应此评论 daodaoyu
	// private Reply replyThis;

	private int floor;

	public ReplyLine(Reply reply, Reply replyThis, int page,
			int numberOfRepliesPerPage, int index) {
		this.reply = reply;
		// this.replyThis=replyThis;
		floor = (page - 1) * numberOfRepliesPerPage + index;
	}

	public String getFloorDisplay() {
		if (floor == 1) {
			return "<b>楼主(LZ)</b>";
		} else if (floor == 2) {
			return "<b>沙发(SF)</b>";
		} else if (floor == 3) {
			return "<b>板凳(BD)</b>";
		} else {
			return "抢占<b>" + floor + "</b>楼";
		}
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getFloor() {
		return floor;
	}

	public Reply getReply() {
		return reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}

}
