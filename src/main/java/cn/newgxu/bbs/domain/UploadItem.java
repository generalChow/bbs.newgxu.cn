package cn.newgxu.bbs.domain;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.newgxu.bbs.common.Pagination;
import cn.newgxu.bbs.common.filter.PlayerCodeFilter;
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
@Table(name = "upload_item")
public class UploadItem extends JPAEntity {

	private static final long serialVersionUID = 2690055196813984581L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")    
//	@SequenceGenerator(name="id_seq", sequenceName="seq_upload_item")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	private String filename;

	@Column(name = "file_size")
	private int fileSize;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploader_id")
	private User uploader;

	private int cost;

	@Column(name = "upload_time")
	private Date uploadTime;

	@Column(name = "storage_path")
	private String storagePath;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploadable_id")
	private Uploadable uploadable;

	private String uri;

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		renameTo(new File(storagePath));
		this.storagePath = storagePath;
	}

	public Uploadable getUploadable() {
		return uploadable;
	}

	public void setUploadable(Uploadable uploadable) {
		this.uploadable = uploadable;
	}

	public User getUploader() {
		return uploader;
	}

	public void setUploader(User uploader) {
		this.uploader = uploader;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	// ------------------------------------------------

	public int reckonCostPoint() {
		return (this.fileSize / 100) + 1;
	}

	public int reckonPrice() {
		return this.uploadable.getPrice() * reckonCostPoint();
	}

	public void deleteFile() {
		new File(this.storagePath).delete();
	}

	private void renameTo(File newFile) {
		try {
			File file = new File(this.storagePath);
			if (file.exists()) {
				file.renameTo(newFile);
			}
		} catch (Exception e) {
			return;
		}
	}

	public String getPlayerCode() {
		if (log.isDebugEnabled()) {
			log.debug("uri=" + this.uri);
		}
		return PlayerCodeFilter.doFilter(getUploadable().getPlayerCode(),
				this.uri);
	}

	public boolean acceptableSize() {
		return this.getUploadable().getMaxsize() >= this.fileSize;
	}

	// -----------------------2010-09-25增加资源管理业务by daodaoyu-------------------------
	@SuppressWarnings("unchecked")
	public static List<UploadItem> getMyUploadItem(User user,Pagination p){
		       try {
				p.setRecordSize(((Long) SQ("select count(*) from UploadItem u where u.uploader = ?1 ",P(1, user))).intValue());
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
		return (List<UploadItem>)Q(
				"from UploadItem u where u.uploader = ?1 order by uploadTime desc",
				P(1, user), p).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static List<UploadItem> getUploadItems(Pagination p){
	       try {
				p.setRecordSize(((Long) SQ("select count(*) from UploadItem u ")).intValue());
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
		return (List<UploadItem>)Q(
				"from UploadItem u  order by u.uploadTime desc",
				 p).getResultList();
	}
	public static UploadItem get(int id)throws ObjectNotFoundException{
		return (UploadItem)getById(UploadItem.class,id);
	}
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "UploadItem" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("filename", filename);
				put("fileSize", fileSize);
				put("uploader", uploader);
				put("cost", cost);
				put("uploadTime", uploadTime);
				put("storagePath", storagePath);
				put("uploadable", uploadable);
				put("uri", uri);
			}
		}.toString();
	}
	
	public String getName(){
		int index=filename.lastIndexOf("\\");
		return index>-1?filename.substring(index):filename;
	}
}
