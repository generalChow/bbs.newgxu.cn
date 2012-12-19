package cn.newgxu.bbs.web.model;

import cn.newgxu.bbs.common.Pagination;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class PaginationBaseModel {
	
	private int page;
	
	private Pagination p = null;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public Pagination getPagination() {
		if (p == null) {
			p = new Pagination(this.page);
		}
		return p;
	}

	public void setP(Pagination p) {
		this.p = p;
	}
}
