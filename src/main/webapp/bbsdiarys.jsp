<%@ page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*" %>   
<%@page import="cn.newgxu.bbs.home.database.common.GetNewDiary" %>   
<%@page import="cn.newgxu.bbs.home.database.common.DiaryDome" %>  
  <table width='100%' border='0' cellspacing='0' cellpadding='0'>
  <%
  List<DiaryDome> diaryDomes=new LinkedList<DiaryDome>();
  GetNewDiary newdiarys=new GetNewDiary();
  diaryDomes=newdiarys.getNewDiary();
  for(int i=0;i<diaryDomes.size();i++){
  DiaryDome bd=new DiaryDome();
  bd=diaryDomes.get(i);
  String str=bd.getDiaryTitle();
  if(bd.getDiaryTitle().length()>15){
  str=str.substring(0,15);
  str+="...";
  }
     %>
      <tr><td height="20"> <a href="http://bbs.newgxu.cn/diary/view_Diary.yws?id=<%=bd.getDiaryId()%>" title="主题：<%=bd.getDiaryTitle() %>(作者:<%=bd.getNick() %>)"  target="_blank" style="white-space:normal;"><%=str %></a></td></tr>
     <%
  }
  %>
</table>
