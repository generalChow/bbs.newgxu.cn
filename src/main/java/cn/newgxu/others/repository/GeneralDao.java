package cn.newgxu.others.repository;

import cn.newgxu.others.util.Pagination;


public interface GeneralDao<T> {

	T persist(T entity);
	
	T merge(T entity);
	
	void remove(T entity);
	
	T find(int id);
	
	Pagination<T> find(int pageNO, int howMany);
	
}
