package cn.newgxu.others.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class EntityManagerProvider {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void execueUpdate(String hql) {
		entityManager.createQuery(hql).executeUpdate();
	}
	
}
