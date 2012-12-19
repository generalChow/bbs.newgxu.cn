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
//����������û�
if (PopedomManager.isAnonymous(authToken)) {
	out.print("�����û��޷��鿴�����ȵ�½��");
	return;
}
BBSChart.saveTopicStatPicFile(request.getRealPath("/bbs/stat/topic.jpg"),500,300);
BBSChart.saveRegisterStatPicFile(request.getRealPath("/bbs/stat/register.jpg"),500,300);
BBSChart.saveHitsStatPicFile(request.getRealPath("/bbs/stat/hits.jpg"),500,300);
BBSChart.saveLastWeekHitsStatPicFile(request.getRealPath("/bbs/stat/lastweekhits.jpg"),500,300);
BBSChart.saveYesterdayHitsStatPicFile(request.getRealPath("/bbs/stat/yesterdayhits.jpg"),600,300);

//out.println("����·����"+request.getRealPath("/bbs/stat/stat.jpg"));
%>
<html>
<head>
<title><%=YYGlobals.getYYProperty("forum.info.name")%>-��̳ͳ��</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta name="generator" content="yyForum">
<meta name="keywords" content="jsp,����,<%=YYGlobals.getYYProperty("forum.info.name")%>">
<meta name="description" content="��ӭ����<%=YYGlobals.getYYProperty("forum.info.name")%>����̳ͳ��">
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
        <td height="25" align="center" bgcolor="#A3B2CC"><strong>��̳����ͳ��</strong></td>
      </tr>
      <tr>
        <td height="10" align="center" class="ss">������:<%=BBSStat.getAbDate(0) %></td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">��̳ע���û�����: <font color=red><%=BBSStat.getTotleUsers() %></font> ��</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">��̳������������: <font color=red><%=BBSStat.getTotleTopics() %></font> ƪ</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">��̳�ظ���������: <font color=red><%=BBSStat.getReplyTopics() %></font> ƪ</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">��̳��������������: <font color=red><%=BBSStat.getTodayTopics() %></font> ƪ</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">��̳��������������: <font color=red><%=BBSStat.getYesterdayTopics() %></font> ƪ</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">��̳�ܷ���: <font color=red><%=BBSStat.getTotleHits() %></font> ��</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">���շ���: <font color=red><%=BBSStat.getTodayTotleHits() %> </font>��</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">���շ���: <font color=red><%=BBSStat.getYesterdayTotleHits() %></font> ��</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">���·���: <font color=red><%=BBSStat.getThisMonthTotleHits() %></font> ��</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">���·���: <font color=red><%=BBSStat.getLastMonthTotleHits() %></font> ��</td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">��̳�İ�����: <font color=red><%=BBSStat.getForumOpenDate("2004-07-01") %></font></td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">�İ浽����: <font color=red><%=BBSStat.getForumOpenDays() %></font> �� </td>
      </tr>
      <tr>
        <td height="20" class="ss" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#ECF2F4'">ƽ��ÿ�շ���:<font color=red> <%=BBSStat.getAverageHits() %></font> ��</td>
      </tr>
    </table></td>
    <td width="76%" height="25" align="center" bgcolor="#A3B2CC"><strong>WEBͼ��ͳ��</strong></td>
  </tr>
  <tr>
    <td height="13" align="center" bgcolor="#CCCCCC"  onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">��̳�ܷ�����ͳ��</font></td>
  </tr>
  <tr>
    <td align="center"><img src="hits.jpg" width="500" height="300"></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#CCCCCC" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">�����û�������ͳ�ƣ�24Сʱ��</font></td>
  </tr>
  <tr>
    <td align="center"><img src="yesterdayhits.jpg" width="600" height="300"></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#CCCCCC" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">�����ڷ�����ͳ��</font></td>
  </tr>
  <tr>
    <td align="center"><img src="lastweekhits.jpg" width="500" height="300"></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#CCCCCC" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">�û�������ͳ��</font></td>
  </tr>
  <tr>
    <td align="center"><img src="topic.jpg" width="500" height="300"></td>
  </tr>
  <tr>
    <td align="center"  bgcolor="#CCCCCC" onMouseOver="this.bgColor='#FDA700'" onMouseOut="this.bgColor='#CCCCCC'"><font color="#0000FF">�û�ע��ͳ��</font></td>
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
