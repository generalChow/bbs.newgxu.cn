package cn.newgxu.bbs.web.model;

import java.util.List;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.DraftBox;
import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class CreateTopicModel {

	private String title;

	private int face;

	private String content;

	private int forumId;

	private User user;

	private Forum forum;

	private List<Area> areas;
	// -------------------add by 叨叨雨
	private int id;

	private int[] dbid;

	private DraftBox draftBox;

	private List<DraftBox> draftBoxes;

	private Pagination pagintion;

	private String select;

	private String zt;

	private int size;

	private String activity;

	/**
	 * 是否同步微雨。
	 * 
	 * @author longkai
	 * @since 2012-09-28
	 */
	private int synchronousTwitter;

	public int getSynchronousTwitter() {
		return synchronousTwitter;
	}

	public void setSynchronousTwitter(int synchronousTwitter) {
		this.synchronousTwitter = synchronousTwitter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getDbid() {
		return dbid;
	}

	public void setDbid(int[] dbid) {
		this.dbid = dbid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public DraftBox getDraftBox() {
		return draftBox;
	}

	public void setDraftBox(DraftBox draftBox) {
		this.draftBox = draftBox;
	}

	public List<DraftBox> getDraftBoxes() {
		return draftBoxes;
	}

	public void setDraftBoxes(List<DraftBox> draftBoxes) {
		this.draftBoxes = draftBoxes;
	}

	public Pagination getPagintion() {
		return pagintion;
	}

	public void setPagintion(Pagination pagintion) {
		this.pagintion = pagintion;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

}
