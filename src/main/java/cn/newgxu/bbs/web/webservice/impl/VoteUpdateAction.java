package cn.newgxu.bbs.web.webservice.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.webservice.impl.transact.VoteUpdateTransact;
import cn.newgxu.bbs.web.webservice.model.VoterUpdateModel;
import cn.newgxu.bbs.web.webservice.util.WebTimeUtil;

import com.opensymphony.webwork.ServletActionContext;


/**
 * @author 集成显卡
 * 2011.5.1
 * 进行版主西大币的添加
 * 这里有个  helloworld 的值，如果这个值与当前系统时间之间超过了 30 秒，就不会执行
 * 
 *这里使用了事务注解来实现我们的事务，不然即使  user.save() 一样是没有效果的
 */
public class VoteUpdateAction extends AbstractBaseAction{
	private static final long serialVersionUID=93458497548962L;
	private static final Log log = LogFactory.getLog(VoteUpdateAction.class);
	
	private VoterUpdateModel model=new VoterUpdateModel();
	
	@Resource
	private VoteUpdateTransact voteUpdateTransact;
	
	public String execute() throws Exception {
		//log.info("start:--- 请求添加西大币："+model.getNick());
		String info="start:---<br /> ";
		/*
		 * url中应该记录了username和password两个基本参数
		 * 论坛上面的登录需要validCode，那么这里就添加系统时间作为validCode
		 */
		try{
			boolean isAllow=model.getYzm().equals("jichengxianka2011");
			//同时请求的时间也要在规定的时间内
			if(!isAllow||model.getUsername()==null||model.getUsername().length()==0||model.getPassword()==null||model.getPassword().length()==0||!WebTimeUtil.isAllow(model.getHelloWorld())){
				info+="error";
				ServletActionContext.getResponse().getWriter().print(info);
				return null;
			}
			User user=User.getByUsername(model.getUsername());
			//System.out.println("数据库中的用户密码:"+user.getPassword()+"   加密后："+Util.hash(model.getPassword()));
			//user.getAccountStatus 为1时可以登录, 
			if(user!=null&&user.getPassword().equals(Util.hash(model.getPassword()))&&user.getAccountStatus()==1){
				log.debug(" 请求添加西大币："+model.getNick());
				info+="working<br />";
				this.voteUpdateTransact.addMoney(model.getNick());
				ServletActionContext.getResponse().getWriter().print("success!!success bbs.newgxu.cn");
				//return SUCCESS;
			}
			else{
				log.debug("用户无法通过验证");
				info+="error user<br />";
				ServletActionContext.getResponse().getWriter().print("error info");
			}
		}
		catch(Exception e){
			log.debug(e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
		return null;
	}

	public Object getModel() {
		return model;
	}
}
