<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,java.util.*,java.text.*" errorPage="" %>
<style>
<!--
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: underline;
}
a:active {
	text-decoration: none;
}
-->
</style>
<%
String[] days = new String[42];
for(int i=0;i<42;i++) {
	days[i]="";
}
DecimalFormat dc = new DecimalFormat("00");
int year,month,day;
String getYear  = request.getParameter("year");
String getMonth    = request.getParameter("month");
String getDay      = request.getParameter("day");
Calendar thisMonth = Calendar.getInstance();
if(getYear==null ||  getMonth==null || getDay==null) {
	year = thisMonth.get(Calendar.YEAR);
	month = thisMonth.get(Calendar.MONTH);
 	day = thisMonth.get(Calendar.DAY_OF_MONTH);
} else {
	 year  = Integer.parseInt(getYear);
	 month = Integer.parseInt(getMonth)-1;
	 day   = Integer.parseInt(getDay);
}
int thisYear = thisMonth.get(Calendar.YEAR);
thisMonth.set(Calendar.YEAR,year);
thisMonth.set(Calendar.MONTH,month);
//thisMonth.setFirstDayOfWeek(Calendar.SUNDAY);
thisMonth.set(Calendar.DAY_OF_MONTH,1);
int firstIndex = thisMonth.get(Calendar.DAY_OF_WEEK)-1;
//out.println(firstIndex);
int maxIndex = thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
for(int i=0;i<maxIndex;i++) {
	days[firstIndex+i] = String.valueOf(i+1);
}
//month+=1;
int row = (firstIndex+maxIndex+6)/7;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Jsp日历</title>
<style type="text/css">
<!--
body,td,th {
	font-family: 宋体;
	font-size: 9pt;
}
a:link {
	color: #000000;
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000000;
}
a:hover {
	text-decoration: underline;
	color: #FF0000;
}
a:active {
	text-decoration: none;
}
.b2 {
	BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; FONT-SIZE: 9pt; BORDER-LEFT: #000000 1px solid; COLOR: #000000; BORDER-BOTTOM: #000000 1px solid; FONT-FAMILY: "宋体"; BACKGROUND-COLOR: #01a7d8
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.riliInput {
	font-family: "宋体";
	font-size: 9pt;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #999966;
	border-left-color: #FFFFFF;
	width: 25px;
	background-position: center bottom;
}
.riliInput2 {
	font-family: "宋体";
	font-size: 9pt;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #999966;
	border-left-color: #FFFFFF;
	width: 20px;
	background-position: center;
}
.rili-border {
	border: 1px solid #999966;
}
.red {
	border: 1px solid #FF0000;
}
-->
</style>
<SCRIPT language=JavaScript>
function changea(num){
   var num=num;
   var userId="&id="+window.parent.document.all('diaryUserId').value;
   var viewtimelink="viewtimelink"+num;
   var newa=document.getElementById(viewtimelink).href+userId;
   document.getElementById(viewtimelink).href=newa;
}
</SCRIPT>
</head>
<body>
<table border="0" align="center" cellspacing="0">
  <tr>
    <td align="center"><table border="0" align="center" cellspacing="0">
        <tr>
          <td width="52" align="right"><input name="year" type="text" class="riliInput" id="year" value="<%=year%>">
            年</td>
          <td width="46" align="center"><input name="month" type="text" class="riliInput2" id="month" value="<%=month+1%>">
          月</td>
          <td width="46" align="center"><input name="day" type="text" class="riliInput2" id="day" value="<%=day%>">
          日
            <input name="dbid" type="hidden" id="dbid" value=""></td>
          <td width="33" align="center">
            <INPUT name=imageField type=image src="/images/diary/go.gif" align="bottom"  border=0 onClick="this.submit()">			</td>
        </tr>
      
      <tr>
        <td colspan="4" align="center"><table  border="1" align="center" bordercolor="#CCCCCC" style="border-collapse:collapse">
            <tr align="center">
              <td width="20" ><strong>日</strong></td>
              <td width="20" ><strong>一</strong></td>
              <td width="20" ><strong>二</strong></td>
              <td width="20" ><strong>三</strong></td>
              <td width="20" ><strong>四</strong></td>
              <td width="20" ><strong>五</strong></td>
              <td width="20" ><strong>六</strong></td>
            </tr>
            <%for(int i=0;i<row;i++) {%>
            <tr>
              <%for(int j=i*7;j<(i+1)*7;j++) {
			 
			  String m = null;
			  String d = null;
			  String outDate = null;
			   try {
			  	m = dc.format(month+1);
			  } catch(Exception ex) {
			  	m="01";
			  }
			  try {
			  	d = dc.format(Integer.parseInt(days[j]));
			  } catch(Exception ex) {
			  	d="";
			  }
			  
			  String toDay = year+"-"+m+"-"+d;
				outDate = "<font color=blue><u>"+days[j]+"</u></font>";
			  %>
              <td align="center"<% if(days[j]!="" && Integer.parseInt(days[j])==day){out.print("bgcolor=\"#C1E0FF\" ");} else {if(days[j]!=""){out.print("onmouseover=\"this.bgColor='#DEE3EF'\"; onmouseout=\"this.bgColor='#FFFFFF'\";");}}%>>
              <a id="viewtimelink<%=j%>" href="/diary/viewDiaryByTime.yws?time=<%=year%>-<%=month+1%>-<%=days[j]%>" onClick="changea( <%=j%> )" target="content">
                <%if(days[j]!="" && Integer.parseInt(days[j])==day){out.print("<strong><font color=red>"+outDate+"</font></strong>");} 
                else {out.print(outDate);}%>
              </a>
              </td>
              <%}%>
            </tr>
            <%}%>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
