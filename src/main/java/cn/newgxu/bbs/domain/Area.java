package cn.newgxu.bbs.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "area")
public class Area extends JPAEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_area")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "name", length = 255)
	private String name;

	@Column(name = "description", length = 2000)
	private String description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "area", fetch = FetchType.LAZY)
	@OrderBy("compositorId")
	private List<Forum> forums;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "webmaster_area", joinColumns = @JoinColumn(name = "area_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> webmasters;

	private int taxis;
	private boolean hot;
	private boolean hidden;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden; 
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public int getTaxis() {
		return taxis;
	}

	public void setTaxis(int taxis) {
		this.taxis = taxis;
	}

	// ------------------------------------------------

	public static Area get(int areaId) throws ObjectNotFoundException {
		System.out.println(areaId);
		return (Area) getById(Area.class, areaId);
	}

	public static Area getByName(String name) throws ObjectNotFoundException {
		return (Area) SQ("from Area a where a.name = ?1 ", P(1, name),
				new Pagination(1, 1));
	}

	@SuppressWarnings("unchecked")
	public static List<Area> getAreas() {
		return (List<Area>) Q(
				"from Area a where a.hidden = 0 order by taxis asc",
				new Pagination(1, Integer.MAX_VALUE)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Area> getAllAreas() {
		return (List<Area>) Q("from Area a order by taxis asc",
				new Pagination(1, Integer.MAX_VALUE)).getResultList();
	}
	
	@Transient
	private List<Topic> latestTopics;
	
	public List<Topic> getLatestTopics() {
		return latestTopics;
	}

	public void setLatestTopics(List<Topic> latestTopics) {
		this.latestTopics = latestTopics;
	}

	/**
	 * 获取同一个Area中的最新帖子，用于2012年论坛主页改版的“分区新帖”
	 * @param 想要获取帖子的数量
	 * @return 取得的帖子列表
	 * @author ivy
	 * @since 2012-04-01
	 */
	@SuppressWarnings("unchecked")
	public List<Topic> getLatestTopics(int amount) {
		if (amount == 0) {
			return new ArrayList<Topic>();
		}
		
		if (amount < 1) {
			amount = 10;
		}
		
		// from Topic t where t.forum.area.id = ?1 and t.forum.secrecy = f
		return (List<Topic>) Q(
				"from Topic t where t.forum.area = ?1 and t.forum.secrecy = false and t.invalid = 0 order by t.creationTime desc",
				P(1, this)).setMaxResults(amount).getResultList();
	}
	
	
	/**
	 * 只获取特定的forum的最新帖子，比较蛋疼，上面那个方法已经可以了，但是这个是去的特定的forum的
	 * @param id
	 * @param number
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Topic> getLatestTopicsByForumId(int id, int number) {
		return (List<Topic>) Q(
				"from Topic t where t.forum.id = ?1  and t.invalid = 0 order by t.creationTime desc",
				P(1, id)).setMaxResults(number).getResultList();
	}


	public String getHotString() {
		if (isHot()) {
			return "<FONT color=red>是</FONT>";
		} else {
			return "否";
		}
	}

	public String getHiddenString() {
		if (isHidden()) {
			return "<FONT color=red>是</FONT>";
		} else {
			return "否";
		}
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "area" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("name", name);
				put("description", description);
				put("hidden", hidden);
			}
		}.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Area) {
			return ((Area) obj).getId() == this.getId();
		}
		return false;
	}

	public List<User> getWebmasters() {
		return webmasters;
	}

	public void setWebmasters(List<User> webmasters) {
		this.webmasters = webmasters;
	}
}