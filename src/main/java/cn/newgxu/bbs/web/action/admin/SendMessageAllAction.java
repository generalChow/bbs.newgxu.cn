package cn.newgxu.bbs.web.action.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Authorization;
import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.MessageService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.action.CreateSmallNewsAction;
import cn.newgxu.bbs.web.model.message.SendMessageModel;

/**
 * 
 * @author 集成显卡
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class SendMessageAllAction extends AbstractBaseAction {

	private static final long serialVersionUID = -8538755273711109508L;
	private DataSource dataSource;
	
	private int monthBefore;//多少个月前
	
	private static final Log log = LogFactory
			.getLog(CreateSmallNewsAction.class);

	private SendMessageModel model = new SendMessageModel();

	private MessageService messageService;

	public String execute() throws Exception {
		signOnlineUser("发送短消息");
		model.setUser(getUser());
		//当没有用户信息时，就从session中取得管理员的id，并实例user到model中
		//使用的获得管理员id的方法：((Authorization)this.getSession().getAttribute(Constants.ADMIN_SESSION)).getId()
		if(this.getUser()==null)
			model.setUser(User.get(((Authorization)this.getSession().getAttribute(Constants.ADMIN_SESSION)).getId()));
		System.out.println(model.getUser().getNick());
		try {
			messageService.sendMessage(model);
			return SUCCESS;
		} catch (BBSException e) {
			MessageList m = new MessageList();
			log.debug(e);
			m.addMessage(e.getMessage());
			Util.putMessageList(m, getSession());
			return ERROR;
		}
	}

	/**
	 * 修改了 jpa 添加很慢的问题，
	 * 修正了 内存不足，就是分段执行就好了
	 * @return
	 * @throws Exception
	 */
	public String submit() throws Exception {
		signOnlineUser("发送短消息中...");
		model.setUser(User.get(((Authorization)this.getSession().getAttribute(Constants.ADMIN_SESSION)).getId()));
		model.setUsers("");
		MessageList m = new MessageList();
		Connection conn=null;
		PreparedStatement  ps=null;
		try {
			//System.out.println(model.getUser().getNick()+"   "+this.monthBefore+"月前  说:"+model.getTitle()+"---"+model.getContent());
			Date date=this.getDate(this.monthBefore);
			int userNumber=User.getNumberOfUsersByLogintime(date);
			List<Integer> list=User.getIdsByLasttime(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datetime=dateFormat.format(new Date());
			conn=this.getDataSource().getConnection();
			conn.setAutoCommit(false);
			StringBuilder sb=new StringBuilder();
			sb.append("insert into message (user_id,folder_id,sender_id,receiver_id,receivers,face,title,content,create_time) values");
			ps=conn.prepareStatement("insert into message (user_id,folder_id,sender_id,receiver_id,receivers,face,title,content,create_time) values(?,?,?,?,?,?,?,?,?)");
			long start=System.currentTimeMillis();
			for(int j=0;j<list.size();j++){
				if(j>0)
					sb.append(",");
				sb.append("("+list.get(j)+",1,"+model.getUser().getId()+","+list.get(j)+","+"'all users',"+model.getFace()+",'"+model.getTitle()+"','"+model.getContent()+"','"+datetime+"')");
				if((j+1)%800==0){
					ps.executeUpdate(sb.toString());
					sb.replace(0, sb.length(), "insert into message (user_id,folder_id,sender_id,receiver_id,receivers,face,title,content,create_time) values");
					j++;
					sb.append("("+list.get(j)+",1,"+model.getUser().getId()+","+list.get(j)+","+"'all users',"+model.getFace()+",'"+model.getTitle()+"','"+model.getContent()+"','"+datetime+"')");
				}
			}
			ps.executeUpdate(sb.toString());
			long useTime=(System.currentTimeMillis()-start);
			System.out.println("OK:"+useTime+"ms");
			
			m.setUrl("/admin/send_message_all.yws");
			m.addMessage("<b>操作成功！发送的用户数 "+userNumber+",用时"+useTime+" (毫秒)</b>");
			m.addMessage("<a href='/admin/send_message_all.yws'>继续群发</a>");
			Util.putMessageList(m, getSession());
			log.debug("短消发送成功！");
			
			return SUCCESS;
		}
		catch(Exception e){
			conn.rollback();
			m.addMessage(e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
		finally{
			try{
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e){}
		}
	}

	private Date getDate(int beforeMonth){
		Calendar c=Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.MONTH, -beforeMonth);
		System.out.println("发送信息的登录时间："+c.getTime());
		return c.getTime();
	}
	
	
	public int getMonthBefore() {
		return monthBefore;
	}
	public void setMonthBefore(int monthBefore) {
		this.monthBefore = monthBefore;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Object getModel() {
		return model;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

}
