package cn.newgxu.bbs.web.model;

import cn.newgxu.bbs.domain.Area;
import cn.newgxu.bbs.domain.Topic;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class SearchModel extends PaginationBaseModel {
	private int type;
	private String keywords;
	private int forumId;
	private List<Area> areas;
	private List<Topic> topics;
	private int userId = 0;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public String getKeywords() {
		try {
			return URLDecoder.decode(this.keywords, "utf8");
		//	return getEncoding(this.keywords);
		} catch (UnsupportedEncodingException e) {
			return this.keywords;
		}
		
	}

//	public String getEncoding(String str)
//	{
//		try {
//			System.out.println(str);
//			str = new String(str.getBytes(),"utf-8");
//			System.out.println(str);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return str;
//	}
//	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getForumId() {
		return this.forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Area> getAreas() {
		return this.areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
}