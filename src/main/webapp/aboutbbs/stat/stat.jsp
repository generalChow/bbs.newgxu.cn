<%@ page contentType="text/html;charset=gb2312" errorPage="error.jsp" %>
<%@ include file="../include/globals.jsp" %>
<%
/*
BBSChart.saveTopicStatPicFile(request.getRealPath("/newgxubbs/bbs/stat/topic.jpg"),500,300);
BBSChart.saveRegisterStatPicFile(request.getRealPath("/newgxubbs/bbs/stat/register.jpg"),500,300);
BBSChart.saveHitsStatPicFile(request.getRealPath("/newgxubbs/bbs/stat/hits.jpg"),500,300);
BBSChart.saveLastWeekHitsStatPicFile(request.getRealPath("/newgxubbs/bbs/stat/lastweekhits.jpg"),500,300);
BBSChart.saveYesterdayHitsStatPicFile(request.getRealPath("/newgxu/tomcat/webapps/ROOT/bbs/stat/yesterdayhits.jpg"),600,300);
*/
//如果是匿名用户
if (PopedomManager.isAnonymous(authToken)) {
	out.print("匿名用户无法查看，请先登陆！");
	return;
}
BBSChart.saveTopicStatPicFile(request.getRealPath("/bbs/stat/topic.jpg"),500,300);
BBSChart.saveRegisterStatPicFile(request.getRealPath("/bbs/stat/register.jpg"),500,300);
BBSChart.saveHitsStatPicFile(request.getRealPath("/bbs/stat/hits.jpg"),500,300);
BBSChart.saveLastWeekHitsStatPicFile(request.getRealPath("/bbs/stat/lastweekhits.jpg"),500,300);
BBSChart.saveYesterdayHitsStatPicFile(request.getRealPath("/bbs/stat/yesterdayhits.jpg"),600,300);

//out.println("虚拟路径："+request.getRealPath("/bbs/stat/stat.jpg"));
%>
<html>
<head>
<title><%=YYGlobals.getYYProperty("forum.info.name")%>-论坛统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta name="generator" content="yyForum">
<meta name="keywords" content="jsp,阿福,<%=YYGlobals.getYYProperty("forum.info.name")%>">
<meta name="description" content="欢迎来到<%=YYGlobals.getYYProperty("forum.info.name")%>！论坛统计">
<link href="../css.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
function login() {
	document.loginForm.Login.disabled = true;
	return true;
}
</script>
<style type="text/css">
<!--
body {
	margin-top: 2px;
	margin-bottom: 2px;
}
.ss {
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #666666;
}
-->
</style></head>

<body leftmargin="0">
<br>
<table width="90%" height="204"  border="1" align="center" cellspacing="0" bordercolor="#666666" bgcolor="#ECF2F4" style="border-collapse:collapse ">
  <tr>
    <td width="24%" rowspan="12" valign="top"><table width="100%" height="265"  border="0" align="center" cellspacing="0">
      <tr>
        <td height="25" align="center" bgcolor="#A3B2CC"><strong>论坛数字统计</strong></td>
      </tr>
      <tr>
        <td height="10" align="center" class="ss">今天是:<%=BBSStat.getAbDate(0) %></td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">论坛注册用户总数: <font color=red><%=BBSStat.getTotleUsers() %></font> 人</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">论坛主题帖子总数: <font color=red><%=BBSStat.getTotleTopics() %></font> 篇</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">论坛回复帖子总数: <font color=red><%=BBSStat.getReplyTopics() %></font> 篇</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">论坛今日主题帖总数: <font color=red><%=BBSStat.getTodayTopics() %></font> 篇</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">论坛昨日主题帖总数: <font color=red><%=BBSStat.getYesterdayTopics() %></font> 篇</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">论坛总访问: <font color=red><%=BBSStat.getTotleHits() %></font> 次</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">今日访问: <font color=red><%=BBSStat.getTodayTotleHits() %> </font>次</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">昨日访问: <font color=red><%=BBSStat.getYesterdayTotleHits() %></font> 次</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">本月访问: <font color=red><%=BBSStat.getThisMonthTotleHits() %></font> 次</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">上月访问: <font color=red><%=BBSStat.getLastMonthTotleHits() %></font> 次</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">论坛改版日期: <font color=red><%=BBSStat.getForumOpenDate("2004-07-01") %></font></td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">改版到今天: <font color=red><%=BBSStat.getForumOpenDays() %></font> 天 </td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">平均每日访问:<font color=red> <%=BBSStat.getAverageHits() %></font> 次</td>
      </tr>
    </table></td>
    <td width="76%" height="25" align="center" bgcolor="#A3B2CC"><strong>WEB图表统计</strong></td>
  </tr>
  <tr>
    <td height="13" align="center" bgcolor="#CCCCCC"  onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">论坛总访问量统计</font></td>
  </tr>
  <tr>
    <td align="center"><img src="hits.jpg" width="500" height="300"></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#CCCCCC" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">昨日用户访问数统计（24小时）</font></td>
  </tr>
  <tr>
    <td align="center"><img src="yesterdayhits.jpg" width="600" height="300"></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#CCCCCC" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">上星期访问数统计</font></td>
  </tr>
  <tr>
    <td align="center"><img src="lastweekhits.jpg" width="500" height="300"></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#CCCCCC" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">用户发帖数统计</font></td>
  </tr>
  <tr>
    <td align="center"><img src="topic.jpg" width="500" height="300"></td>
  </tr>
  <tr>
    <td align="center"  bgcolor="#CCCCCC" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">用户注册统计</font></td>
  </tr>
  <tr>
    <td align="center"><img src="register.jpg" width="500" height="300"></td>
  </tr>
  <tr>
    <td align="center">&nbsp;</td>
  </tr>
</table>
</body>
</html>
