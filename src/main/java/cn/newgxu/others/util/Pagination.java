package cn.newgxu.others.util;

import java.util.List;

/**
 * 一个分页模型，在视图上展示实体列表。
 *
 * @param <T> 泛型
 * @author longkai
 * @version 1.0
 * @since 2012-08-25
 */
public class Pagination<T> {

	private List<T> list;
	private int page;
	private int total;
	private int totalRecords;
	private int pageSize;
	
	public Pagination(int page, int pageSize, int totalRecords) {
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
		this.total = (totalRecords - 1) / pageSize + 1;
		this.page = page < 1 ? 1 : page > total ? total : page;
	}
	
	public List<T> getList() {
		return list;
	}

	public Pagination<T> setList(List<T> list) {
		this.list = list;
		return this;
	}

	public int getPage() {
		return page;
	}

	public Pagination<T> setPage(int page) {
		this.page = page;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public Pagination<T> setTotal(int total) {
		this.total = total;
		return this;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public Pagination<T> setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
		return this;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public Pagination<T> setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
	/**
	 * 获取上一页的页码（不会越界）。
	 * @return 上一页的页码
	 */
	public int getPrev() {
		return page <= 2 ? 1 : page--;
	}

	/**
	 * 获取下一页的页码（不会越界）。
	 * @return 下一页的页码
	 */
	public int getNext() {
		return page == total ? total : page++;
	}
	
	/**
	 * 获取数据库中抓取列表的起始行数。
	 * @return 起始行
	 */
	public int getBeginRow() {
		return page == 1 ? 0 : (page - 1) * pageSize;
	}
	
}
