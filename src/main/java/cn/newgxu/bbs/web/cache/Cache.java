package cn.newgxu.bbs.web.cache;

import java.util.Date;
import java.util.List;

/**
 * @path valhalla_hx----cn.newgxu.bbs.web.cache.BBSCache.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-11-4
 * @describe  
 *	BBS中的cache 单元，包含了 cache 的更新日期，类型
 */
public class Cache {
	private String name;
	private Date date;
	private long timeout;
	private List<?> list;
	private long hits;//缓存被调用次数
	
	/**
	 * 检查缓存是否需要更新
	 */
	public boolean isNeedUpdate(){
		hits++;//每次都会加1
		if(timeout==0)
			return false;
		long newTime=System.currentTimeMillis();
		boolean result=(newTime-date.getTime())>=timeout;
		return result;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}
	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public long getHits() {
		return hits;
	}
	public void setHits(long hits) {
		this.hits = hits;
	}
}