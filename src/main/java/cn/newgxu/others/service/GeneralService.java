package cn.newgxu.others.service;

import cn.newgxu.others.util.Pagination;


public interface GeneralService<T> {

	void create(T entity);
	
	void delete(T entity);
	
	void update(T entity);
	
	T get(int id);
	
	Pagination<T> get(int pageNO, int count);
	
}
