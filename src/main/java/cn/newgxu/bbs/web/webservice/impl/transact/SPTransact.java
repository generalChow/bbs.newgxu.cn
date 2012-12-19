package cn.newgxu.bbs.web.webservice.impl.transact;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newgxu.bbs.domain.user.ResetPasswordInfo;
//密码重置使用
@Service(value="sPTransact")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class SPTransact{

	public void saveSP(ResetPasswordInfo r) throws Exception{
		r.save();
	}
}
