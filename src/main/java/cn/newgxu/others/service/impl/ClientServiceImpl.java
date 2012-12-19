package cn.newgxu.others.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newgxu.others.entity.Client;
import cn.newgxu.others.repository.ClientDao;
import cn.newgxu.others.service.ClientService;
import cn.newgxu.others.util.Pagination;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ClientServiceImpl implements ClientService {

	@Inject
	private ClientDao userDao;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void create(Client entity) {
		System.out.println("tx!");
		userDao.persist(entity);
	}

	public void delete(Client entity) {
		// TODO Auto-generated method stub

	}

	public void update(Client entity) {
		// TODO Auto-generated method stub

	}

	public Client get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pagination<Client> get(int pageNO, int count) {
		// TODO Auto-generated method stub
		return null;
	}

}
