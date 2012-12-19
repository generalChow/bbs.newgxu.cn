package cn.newgxu.bbs.domain;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "uploadable")
public class Uploadable extends JPAEntity {

	private static final long serialVersionUID = -8938022462581919998L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_uploadable")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "filename_extension", length = 8)
	private String filenameExtension;

	private int maxsize;

	@Column(name = "player_code")
	private String playerCode;

	private int price;

	public String getFilenameExtension() {
		return filenameExtension;
	}

	public void setFilenameExtension(String filenameExtension) {
		this.filenameExtension = filenameExtension;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaxsize() {
		return maxsize;
	}

	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
	}

	public String getPlayerCode() {
		return playerCode;
	}

	public void setPlayerCode(String playerCode) {
		this.playerCode = playerCode;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	// ------------------------------------------------

	public static Uploadable get(int id) throws ObjectNotFoundException {
		return (Uploadable) getById(Uploadable.class, id);
	}

	public static Uploadable getByFilename(String filename)
			throws ObjectNotFoundException {
		return getByFilenameExtension(Util.getFilenameExtension(filename));
	}

	public static Uploadable getByFilenameExtension(String ext)
			throws ObjectNotFoundException {
		return (Uploadable) SQ(
				"from Uploadable u where u.filenameExtension = ?1", P(1, ext));
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "Uploadable" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("filenameExtension", filenameExtension);
				put("maxsize", maxsize);
				put("playerCode", playerCode);
				put("price", price);
			}
		}.toString();
	}

}
