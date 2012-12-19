package cn.newgxu.others.controller;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.newgxu.others.entity.Client;
import cn.newgxu.others.service.ClientService;

import com.opensymphony.xwork.ActionSupport;

@Controller
@Scope("prototype")
public class ClientController extends ActionSupport {

	private static final long	serialVersionUID	= 1L;
	@Inject
	private ClientService clientService;

	@Override
	public String execute() throws Exception {
		System.out.println("hello!");
		addUser();
		return super.execute();
	}
	
	public void addUser() {
		Client client = new Client();
		client.setAccount("123");
		client.setUsername("ivy");
		client.setPassword("123");
		clientService.create(client);
	}

}
