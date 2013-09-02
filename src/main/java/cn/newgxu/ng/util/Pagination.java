/*
 * Copyright im.longkai@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.newgxu.ng.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个分页模型，在视图上展示实体列表。
 *
 * @author longkai
 * @version 1.0
 * @param <T> 泛型
 * @since 2012-08-25
 */
public class Pagination<T> {
	
	private static final Logger L = LoggerFactory.getLogger(Pagination.class);
	
	/** 包含实体的数组表 */
	private List<T> list;
	/** 当前页码 */
	private int page;
	/** 总页码 */
	private int total;
	/** 总共的记录数 */
	private int totalRecords;
	/** 每页记录数 */
	private int pageSize;
	
	public Pagination(int page, int pageSize, int totalRecords) {
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
		this.total = (totalRecords - 1) / pageSize + 1;
//		this.page = page < 1 ? 1 : page > total ? total : page;
		this.page = page; // 这里，由于我们使用了json动态加载数据，区别于以往的分页，所以，这里也就不计算是否越界了。 
		L.info("当前页码：{}，每页记录：{}，所有记录：{}，总共页码：{}", this.page, this.pageSize, this.totalRecords, this.total);
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
