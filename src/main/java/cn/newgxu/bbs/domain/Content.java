package cn.newgxu.bbs.domain;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.newgxu.bbs.common.config.ForumConfig;
import cn.newgxu.bbs.common.filter.FilterUtil;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
@Entity
@Table(name = "content")
public class Content extends JPAEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_seq")
	// @SequenceGenerator(name="id_seq", sequenceName="seq_content")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = -1;

	@Column(name = "content", length = 100000)
	private String content;

	@Transient
	private String contentFilter;

	public Content() {
	}

	public Content(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		/*
		 * 考虑到 新旧 版本编辑器的兼容问题，这里需要将\r\n全部转换成无字符。
		 */
		if(ForumConfig.NEW_5_0){
			this.content=content.replaceAll("\r\n", "");
			System.out.println("-------------------------------------------------修复了BR");
		}else
			this.content = content;
			
	}

	public String getContentFilter() {
		if (this.contentFilter == null) {
			this.contentFilter = FilterUtil.replyContent(content);
			// this.contentFilter=tranStr(content);
		}
		// while(this.contentFilter.indexOf(" ")!=-1){
		// this.contentFilter =
		// this.contentFilter.substring(0,this.contentFilter.indexOf(" "))
		// +"&nbsp;&nbsp;&nbsp;&nbsp;"+this.contentFilter.substring(this.contentFilter.indexOf(" ")+1);
		// }
		// this.contentFilter=this.contentFilter.replaceAll("&nbsp;","&nbsp;&nbsp;");
		// this.contentFilter=this.contentFilter.replaceAll(" ","&nbsp;");
		// this.contentFilter=this.contentFilter.replaceAll(String.valueOf((char)
		// 13),"<br/>");

		// this.contentFilter = tranStr(this.contentFilter);
		// System.out.println();
		return this.contentFilter;
	}

	public static String tranStr(String oldStr) {
		int k = oldStr.indexOf(String.valueOf((" ")));
		while (k >= 0) {
			oldStr = oldStr.substring(0, k) + "&nbsp;&nbsp;"
					+ oldStr.substring(k + 2, oldStr.length());
			k = oldStr.indexOf(String.valueOf(" "));
		}

		int i = oldStr.indexOf(String.valueOf((char) 13));// 这里就是"/n"
		while (i > 0) {
			oldStr = oldStr.substring(0, i) + "<br>"
					+ oldStr.substring(i + 1, oldStr.length());
			i = oldStr.indexOf(String.valueOf((char) 13));
		}
		int j = oldStr.indexOf(String.valueOf((char) 10));
		while (j > 0) {
			oldStr = oldStr.substring(0, j) + ""
					+ oldStr.substring(j + 1, oldStr.length());
			j = oldStr.indexOf(String.valueOf((char) 10));
		}
		return oldStr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// ------------------------------------------------
	public static Content get(int contentId) throws ObjectNotFoundException {
		return (Content) getById(Content.class, contentId);
	}

	// ------------------------------------------------
	@SuppressWarnings("serial")
	@Override
	public String toString() {
		return "content" + new LinkedHashMap<String, Object>() {
			{
				put("id", id);
				put("content", content);
				put("contentFilter", getContentFilter());
			}
		}.toString();
	}

}