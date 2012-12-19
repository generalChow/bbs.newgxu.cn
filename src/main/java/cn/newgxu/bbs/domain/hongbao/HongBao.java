package cn.newgxu.bbs.domain.hongbao;

import java.util.List;

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

import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 红包的主体类
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "hongbao")
public class HongBao extends JPAEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	/** 礼物的名字 */
	@Column(name = "name", length = 20)
	private String name;

	/** 礼物的描述 */
	@Column(name = "description", length = 500)
	private String description;

	/** 节日的由来 */
	private String history;

	/** 节日是否开放 */
	private int valid;

	/** 节日 */
	private int time;

	/** 礼物的内容 */
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "content_type")
	private HongBao_content content;

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

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public HongBao_content getContent() {
		return content;
	}

	public void setContent(HongBao_content content) {
		this.content = content;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@SuppressWarnings("unchecked")
	public static List<HongBao> getAllHongBaos() {
		return Q("from HongBao h").getResultList();
	}

	public static HongBao getById(int id) throws ObjectNotFoundException {
		return (HongBao) SQ("from HongBao h where h.id=?1", P(1, id));
	}

	public static HongBao getHongBaoToday(int today)
			throws ObjectNotFoundException {
		return (HongBao) SQ("from HongBao h where h.time=?1", P(1, today));
	}

	@SuppressWarnings("unchecked")
	public static HongBao getNextFestival(int today) {
		List<HongBao> list = Q(
				"from HongBao h where h.time>?1 order by time asc", P(1, today))
				.getResultList();
		if (list.size() == 0) {
			return (HongBao) Q("from HongBao h order by time asc")
					.getResultList().get(0);
		}
		System.out.println(list.size()+" 个红包");
		System.out.println(((HongBao) list.get(0)).getName());
		return (HongBao) list.get(0);
	}
}
