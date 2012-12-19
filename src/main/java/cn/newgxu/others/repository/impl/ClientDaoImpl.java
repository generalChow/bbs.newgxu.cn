package cn.newgxu.others.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cn.newgxu.others.entity.Client;
import cn.newgxu.others.repository.ClientDao;
import cn.newgxu.others.util.Pagination;

@Repository
public class ClientDaoImpl implements ClientDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Client persist(Client entity) {
		entityManager.persist(entity);
		return entity;
	}

	public Client merge(Client entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(Client entity) {
		// TODO Auto-generated method stub

	}

	public Client find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pagination<Client> find(int pageNO, int howMany) {
		// TODO Auto-generated method stub
		return null;
	}

}
