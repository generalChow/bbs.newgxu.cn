package cn.newgxu.bbs.web.webservice.impl.transact;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newgxu.bbs.domain.user.User;

@Service(value="voteUpdateTransact")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class VoteUpdateTransact{

	public void addMoney(String name) throws Exception{
		User user2=User.getByNick(name);
		if(user2!=null){
			//System.out.println("开始添加"+user2.getUsername()+"::"+user2.getNick()+"::"+user2.getMoney());
			user2.addMoney(200);
			user2.save();
			//System.out.println("开始添加"+user2.getUsername()+"::"+user2.getNick()+"::"+user2.getMoney());
		}
	}
}
