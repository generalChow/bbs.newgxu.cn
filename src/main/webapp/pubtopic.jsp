<%@ page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*" %>   
<%@page import="cn.newgxu.bbs.home.database.common.GetHotTopic" %>   
<%@page import="cn.newgxu.bbs.home.database.common.BaseDome" %>  
<table width='100%' border='0' cellspacing='0' cellpadding='0'>
  <%
  List<BaseDome> baseDomes=new LinkedList<BaseDome>();
  GetHotTopic getHotTopic=new GetHotTopic();
  baseDomes=getHotTopic.getHotTopic();
  for(int i=0;i<baseDomes.size();i++){
  BaseDome bd=new BaseDome();
  bd=baseDomes.get(i);
  String str=bd.getTopicTilte();
  if(bd.getTopicTilte().length()>15){
  str=str.substring(0,15);
  str+="...";
  }
     %>
      <tr>
      <td height="20"> 
      <a href="http://bbs.newgxu.cn/topic.yws?forumId=<%=bd.getForumId() %>&topicId=<%=bd.getTopicId() %>" title="主题：<%=bd.getTopicTilte() %>(作者:<%=bd.getNick() %>)"  target="_blank"><%=str %></a>
      </td>
      </tr>
     <%
  }
  %>
</table>