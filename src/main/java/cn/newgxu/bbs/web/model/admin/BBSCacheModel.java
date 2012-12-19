package cn.newgxu.bbs.web.model.admin;

import java.util.ArrayList;
import java.util.List;

import cn.newgxu.bbs.web.cache.Cache;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.model.admin.BBSCacheModel.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-11-5
 * @describe  
 *
 */
public class BBSCacheModel {
	private List<Cache> caches=new ArrayList<Cache>();
	private String timeout;
	private int cacheId;
	
	public List<Cache> getCaches() {
		return caches;
	}
	public void setCaches(List<Cache> caches) {
		this.caches = caches;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public int getCacheId() {
		return cacheId;
	}
	public void setCacheId(int cacheId) {
		this.cacheId = cacheId;
	}
}
