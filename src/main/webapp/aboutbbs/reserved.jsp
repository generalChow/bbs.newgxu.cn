<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="com.yy.forum.*,
				 com.yy.forum.proxy.*,
				 com.yy.forum.exceptions.*,
				 com.yy.forum.util.*,
				 com.yy.forum.util.upload.*,
				 com.yy.forum.storage.searchDB.*,
				 com.yy.forum.BBSStat.*,
				 java.util.*,
				 java.text.*"
%>
<%
int gloForumID = ParamUtils.getIntParameter(request, "forumID", 0);
%>
<%
long pageLastTime = System.currentTimeMillis();
Authorization authToken = null;
boolean error = false;
String errorMessage = null;
try {
	authToken = SkinUtils.getUserAuthorization(request, response);
	//���ͳ��
	CounterManager.log(gloForumID, 1);
} catch (UnauthorizedException e) {
	errorMessage = "��̳�����⵽�쳣������������ֹ��!";
	error = true;
}

if (error) {
	out.print(ForumMessage.getError(errorMessage));
	return;
} 
SimpleDateFormat sb = new SimpleDateFormat("HH");
int now = Integer.parseInt(sb.format(new Date()));
String topStyle = null;
if(now >=19 || now <=7) {
	topStyle = "top_bg2";
} else {
	topStyle = "top_bg";
}
%>
<%
	//�õ���������
int forumID = ParamUtils.getIntParameter(request, "forumID", 1);
int pageID = ParamUtils.getIntParameter(request, "page", 1);
int type = ParamUtils.getIntParameter(request, "type", 0);
//�õ���̳����
Forum Finstance = null;
try {
	Finstance = ForumProxy.getForum(authToken, forumID);
} catch (ForumNotFoundException fe) {
	out.print(ForumMessage.getError(YYGlobals.getErrorMessage("msg21")));
	return;
} catch (UnauthorizedException ue) {
	out.print(ForumMessage.getError(YYGlobals.getErrorMessage("msg24")));
	return;
}

//��̳��Ϣ
//ȡ�ø�ҳ��������ID�ŵ� ������
//���Ϸ���������
Iterator topics = null;
int topicsCount = 0;
int pagesCount = 0;
try {
	topicsCount = TopicsProxy.getTopicsCount(authToken, forumID, type);
	pagesCount = SkinUtils.ceil(YYGlobals.YY_DIS_TOPICS, topicsCount);
	pageID = SkinUtils.pageFormat(pageID, pagesCount);
	topics = TopicsProxy.getTopics(authToken, forumID, pageID, type);
} catch (UnauthorizedException u) {
	out.print(ForumMessage.getError(YYGlobals.getErrorMessage(u.getMessage())));
	return;
}

//����
int[] masters = MasterManager.getMasters(Finstance.getForumID());
Client pageUser = null;

//������Ϣ
int topicID;
Topic pageTopic;

//�û�
Client newUser, reUser;

//ҳ����ת
int[] pageGForums = GForumManager.getGForums();
GForum GFinstance = null;


//�����˵���ʾ
StringBuffer gForumSB = new StringBuffer();
//��������̳ID��
int[] listGForums = GForumManager.getGForums();
//�����˵�����̳
GForum listGForum, thisGForum;
//�����˵���̳
Forum listForum, thisForum;
try {
	thisForum = ForumFactory.getForum(forumID);
	thisGForum = GForumFactory.getGForum(thisForum.getMainID());
} catch (ForumNotFoundException fe) {
	return;
}
//���������б�
		OnlineManager.add(String.valueOf(authToken.getUserID()), 
	(Cacheable)OnlineFactory.getOnline(authToken.getUserID(), 
	false, "<a href=forumsList.jsp?gForumID="+thisGForum.getMainID()+">"+thisGForum.getName()+"</a>-><a href=topicsList.jsp?forumID="+forumID+">"+Finstance.getName()+"</a>", request)
		);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>��ӭ����<%=YYGlobals.getYYProperty("forum.info.name")%>-><%=thisGForum.getName()%> -><%=Finstance.getName()%></title>
<meta name="generator" content="newgxu">
<meta name="keywords" content="������ѧ ������ѧ��������վ ��������̳">
<meta name="description" content="������ѧ ������ѧ��������վ ��������̳">
<meta http-equiv="refresh" content="1500">
<link href="list2.css" rel="stylesheet" type="text/css">
<link rel="alternate" type="application/rss+xml" title="��������̳��<%=Finstance.getName()%>" href="Rss.jsp?forumID=<%=forumID%>" />

<script type="text/JavaScript">
<!--

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
<style type="text/css">
<!--
body,td,th {
	color: #333333;
}
.STYLE11 {
	color: #666666;
	font-weight: bold;
}
-->
</style>
<div id=menuDiv style='Z-INDEX: 2; VISIBILITY: hidden; WIDTH: 1px; POSITION: absolute; HEIGHT: 1px; BACKGROUND-COLOR: #F2F2F2'></div>
<div id=menuDiv2 style='Z-INDEX: 2; VISIBILITY: hidden; WIDTH: 1px; POSITION: absolute; HEIGHT: 1px; BACKGROUND-COLOR: #F2F2F2'></div>
<script language="JavaScript">
//�����˵���ش���
 var h;
 var w;
 var l;
 var t;
 var topMar = 1;
 var leftMar = -2;
 var space = 1;
 var isvisible;
 var isvisible2;
 var MENU_SHADOW_COLOR='#D7E9AD';//���������˵���Ӱɫ
 var global = window.document
 global.fo_currentMenu = null
 global.fo_shadows = new Array

function YYHideMenu() 
{
 var mX;
 var mY;
 var vDiv;
 var mDiv;
 var mX2;
 var mY2;
 var vDiv2;
 var mDiv2;

	if (isvisible == true)
{
		vDiv = document.all("menuDiv");
		vDiv2 = document.all("menuDiv2");
		mX = window.event.clientX + document.body.scrollLeft;
		mY = window.event.clientY + document.body.scrollTop;
		mX2 = window.event.clientX + document.body.scrollLeft;
		mY2 = window.event.clientY + document.body.scrollTop;
		if (vDiv2.style.visibility=="visible") {
		   if (((mX < parseInt(vDiv.style.left)) || (mX > parseInt(vDiv.style.left)+vDiv.offsetWidth) || (mY < parseInt(vDiv.style.top)-h) || (mY > parseInt(vDiv.style.top)+vDiv.offsetHeight)) && ((mX2 < parseInt(vDiv2.style.left)) || (mX2 > parseInt(vDiv2.style.left)+vDiv2.offsetWidth) || (mY2 < parseInt(vDiv2.style.top)-h) || (mY2 > parseInt(vDiv2.style.top)+vDiv2.offsetHeight))){
			   vDiv.style.visibility = "hidden";
			   vDiv2.style.visibility = "hidden";
			   isvisible = false;
		   }
		}else{
		   if ((mX < parseInt(vDiv.style.left)) || (mX > parseInt(vDiv.style.left)+vDiv.offsetWidth) || (mY < parseInt(vDiv.style.top)-h) || (mY > parseInt(vDiv.style.top)+vDiv.offsetHeight)){
			   vDiv.style.visibility = "hidden";
			   isvisible = false;
		   }
		}
}
}
function YYShowMenu(vMnuCode,tWidth) {
	vSrc = window.event.srcElement;
	vMnuCode = "<table id='submenu' cellspacing=1 cellpadding=3 style='width:"+tWidth+"' onmouseout='YYHideMenu()'><tr height=23 ><td nowrap align=left >" + vMnuCode + "</td></tr></table>";

	h = vSrc.offsetHeight;
	w = vSrc.offsetWidth;
	l = vSrc.offsetLeft + leftMar+4;
	t = vSrc.offsetTop + topMar + h + space-2;
	vParent = vSrc.offsetParent;
	while (vParent.tagName.toUpperCase() != "BODY")
	{
		l += vParent.offsetLeft;
		t += vParent.offsetTop;
		vParent = vParent.offsetParent;
	}

	menuDiv.innerHTML = vMnuCode;
	menuDiv.style.top = t;
	menuDiv.style.left = l;
	menuDiv.style.visibility = "visible";
	isvisible = true;
    makeRectangularDropShadow(submenu, MENU_SHADOW_COLOR, 4)
}
function YYShowMenu2(vMnuCode,tWidth) {
	vSrc = window.event.srcElement;
	vMnuCode = "<table id='submenu2' cellspacing=1 cellpadding=3 style='width:"+tWidth+"' class=tableborder1 onmouseout='YYHideMenu()'><tr height=23 class='trBorder2'><td nowrap align=left class=tablebody2>" + vMnuCode + "</td></tr></table>";

	h = vSrc.offsetHeight;
	w = vSrc.offsetWidth;
	l = vSrc.offsetLeft + leftMar+95;
	t = vSrc.offsetTop + topMar + h + space-20;
	vParent = vSrc.offsetParent;
	while (vParent.tagName.toUpperCase() != "BODY")
	{
		l += vParent.offsetLeft;
		t += vParent.offsetTop;
		vParent = vParent.offsetParent;
	}

	menuDiv2.innerHTML = vMnuCode;
	menuDiv2.style.top = t;
	menuDiv2.style.left = l;
	menuDiv2.style.visibility = "visible";
	menuDiv.style.visibility = "visible";
	isvisible2 = true;
    makeRectangularDropShadow(submenu2, MENU_SHADOW_COLOR, 4)
}

function makeRectangularDropShadow(el, color, size)
{
	var i;
	for (i=size; i>0; i--)
	{
		var rect = document.createElement('div');
		var rs = rect.style
		rs.position = 'absolute';
		rs.left = (el.style.posLeft + i) + 'px';
		rs.top = (el.style.posTop + i) + 'px';
		rs.width = el.offsetWidth + 'px';
		rs.height = el.offsetHeight + 'px';
		rs.zIndex = el.style.zIndex - i;
		rs.backgroundColor = color;
		var opacity = 1 - i / (i + 1);
		rs.filter = 'alpha(opacity=' + (100 * opacity) + ')';
		el.insertAdjacentElement('afterEnd', rect);
		global.fo_shadows[global.fo_shadows.length] = rect;
	}
}
//����̳�б�
<%for (int i = 0; i < listGForums.length-1; i++) {
	StringBuffer gForumsSB = new StringBuffer();
	listGForum = GForumFactory.getGForum(listGForums[i]);
	gForumSB.append("<a style=font-size:9pt;line-height:14pt; href=\\\'#\\\' onMouseOver=\\\'YYShowMenu2(gForum").append(listGForum.getMainID()).append(",100)\\\'>").append(StringUtils.dispGForumName(listGForum)).append("</a><br>");
	int[] listForums = listGForum.getForums(); 
	for (int j = 0; j < listForums.length; j++) {
		listForum = ForumFactory.getForum(listForums[j]);
		gForumsSB.append("<a style=font-size:9pt;line-height:14pt; href=\\\"topicsList.jsp?forumID=").append(listForum.getForumID()).append("\\\">").append(StringUtils.dispForumName(listForum)).append("</a><br>");
	} 
	out.print("var gForum");
	out.print(listGForum.getMainID());
	out.print(" = '");
	out.print(gForumsSB.toString());
	out.print("';\r\n");
}%>
var gForums = '<%=gForumSB.toString()%>'


</script>
</head>

<body onLoad="MM_preloadImages('images/zc2.gif')">
<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
        <td height="184" class="<%=topStyle%>">&nbsp;</td>
  </tr>
</table>
<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="162" height="131" class="top_bg_21">&nbsp;</td>
    <td align="center" valign="top" class="top_bg_22"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="67" height="40" align="right" background="images/tit1.jpg">�٣���ӭ����</td>
            <td width="217" height="40" align="left" background="images/tit.jpg">  <%
  	Client includeUser = null;
  	Group includeGroup = null;
  	String face = "<img src=\"images/bz_06.jpg\" width=\"126\" height=\"72\">";
    	//����������û�
    	if (PopedomManager.isAnonymous(authToken)) {
  		out.print("���ο�");
  	} else {
  		
  		try {
  	includeUser = UserFactory.getUser(authToken.getUserID());
  	includeGroup = GroupFactory.getGroup(includeUser.getGroup());
  		} catch (UserNotFoundException ue) {
  	out.print(ForumMessage.getError(YYGlobals.getErrorMessage("msg19")));
  	return;
  		} catch (GroupNotFoundException ge) {
  	out.print(ForumMessage.getError(YYGlobals.getErrorMessage("msg19")));
  	return;
  		}
  		out.print("��"+includeUser.getNickname());
  		face = includeUser.getFace();
  	}
  %></td>
            <td width="272" background="images/tit.jpg">
			<%if(includeUser != null) {%>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="20"><a href="controlPanel.jsp" >���˷�����</a>&nbsp;&nbsp;<a href="diary/" target="_blank">�����ռ�</a>&nbsp;&nbsp;&nbsp;<a href="index.jsp?action=logout">�˳�</a>&nbsp;&nbsp;<a href="#" onClick="MM_openBrWindow('shortMessagesList.jsp','�鿴����Ϣ','menubar=yes,scrollbars=yes,width=700,height=500')" >����Ϣ��<%=ShortMessageManager.getNewSMCount(authToken)%>��</a></td>
                  </tr>
                </table>
				<%}%>            </td>
            <td width="18" height="40" background="images/tit3.jpg">&nbsp;</td>
          </tr>
      </table></td>
    <td width="270" class="top_bg_23"><div id="Layer1" style="position:absolute; z-index:1">
      <%
	  //����������û�
  		if (PopedomManager.isAnonymous(authToken)) {
		
	  %>
        <div id="login">
          <form id="login2" name="login2" action="index.jsp?action=login" method="post"  onsubmit="return Juge(this)">
            <table width="152" height="152"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="7" colspan="3"></td>
                <td width="6" height="27" rowspan="2">&nbsp;</td>
              </tr>
              <tr>
                <td width="51" height="22">&nbsp;</td>
                <td height="22" colspan="2" valign="bottom"><input name="userCode" type="text" class="input" id="userCode" size="15"/></td>
              </tr>
              <tr>
                <td height="2" colspan="3"></td>
              </tr>
              <tr>
                <td height="26">&nbsp;</td>
                <td height="26" colspan="2" valign="middle"><input name="passwd" type="password" class="input" id="passwd" size="15"/></td>
              </tr>
              <tr>
                <td height="2" colspan="4"></td>
              </tr>
              <tr>
                <td height="12">&nbsp;</td>
                <td width="46" height="16" valign="top"><input name="randCode" type="text" class="input_rand" id="randCode" /></td>
                <td width="49" align="left" valign="top"><img src="getRandCode.jsp" width="38" height="16" class="green-border" /></td>
              </tr>
              <tr>
                <td colspan="4" height="1"></td>
              </tr>
              <tr align="center">
                <td height="31" colspan="4"><a href="?action=login" onClick="sibmit()" target="_self" onMouseOver="MM_swapImage('Image1','','images/dl2.gif',1)" onMouseOut="MM_swapImgRestore()">
                  <input type="image" src="images/dl1.gif" name="Image1" width="37" height="16" border="0"/>
                </a>&nbsp;<a href="register.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2','','images/zc2.gif',1)"><img src="images/zc1.gif" name="Image2" width="37" height="16" border="0" id="Image2"/></a></td>
              </tr>
              <tr>
                <td colspan="4">&nbsp;</td>
              </tr>
            </table>
          </form>
        </div>
     <%}%>
      <%if(topStyle.equals("top_bg2")) {%>
      <div id="yhc" style="position:absolute; width:600px; height:150px; z-index:1; left: -331px; top: -249px;">
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="600" height="150">
          <param name="movie" value="flash/yinghuochong2.swf"/>
          <param name="wmode"   value="transparent"/>
          <param name="quality" value="high"/>
          <embed src="flash/yinghuochong2.swf" quality="high" wmode="transparent"  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="600" height="150"></embed>
        </object>
      </div><%}%>
    </div></td>
  </tr>
</table>
<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
  <tr>
    <td width="45" rowspan="3" bgcolor="#C0DB7E" class="center_bg">&nbsp;</td>
    <td align="right" valign="bottom"><img src="images/art_list_02.jpg" width="21" height="30" alt=""/></td>
    <td height="30" class="list_center_top">&nbsp;</td>
    <td><img src="images/art_list_04.jpg" width="20" height="30" alt=""/></td>
    <td width="45" rowspan="3" bgcolor="#C0DB7E" class="center_bg">&nbsp;</td>
  </tr>
  <tr>
    <td width="21" align="right" valign="top" class="art_list_left"><img src="images/art_list_06_12.jpg" width="21" height="244"/> </td>
    <td align="center" valign="top" bgcolor="#FFFBE0" class="center_c"><table width="100%" height="35"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" class="xlogo2">��Ȩ����</td>
        </tr>
      </table>
        <table width="857" height="62" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
          <tr>
            <td width="13" rowspan="2" align="right" valign="bottom"><img src="images/list_title1_012.gif" width="13" height="62" alt=""/></td>
            <td width="109" align="left" valign="bottom"><img src="images/list_title1_022.gif" width="32" height="26" alt=""/></td>
            <td width="690" height="26" align="right">&nbsp;</td>
            <td width="32" align="right" valign="bottom"><img src="images/list_title1_042.gif" width="32" height="26" alt=""/></td>
            <td width="13" rowspan="2" align="left" valign="bottom"><img src="images/list_title1_052.gif" width="13" height="62" alt=""/></td>
          </tr>
          <tr>
            <td height="36" colspan="3" class="list_title">&nbsp; ��Ȩ����</td>
          </tr>
      </table>
      <table width="95%" height="20"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bian2">
          <tr>
            <td><p class="list_body">��������վ������Ȩ����</p>
<p class="list_body"><br><font color="#ff0000">��һ���ݡ���վ��Ȩ������</font><br> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
�ϸ������л����񹲺͹���Ȩ��,�κ�ת�ػ�ת����Ӧע����ʵ���ߺ���ʵ��������������վ��Ȩ�ڱ���վ��Χ�����á�������ת���û�����������վ�������������ݡ���������վ�����û�����������������İ�Ȩ������Ȩ�����顢���ײ��е��κ����Ρ��κ�ý��ת����������ԭ���߼���������վ��ϵ��δ��Э����Ȩ����ת�ػ��񣬷�������׷���������Σ�����Ȩת�������ע����Դ&ldquo;������ѧ������&rdquo;��<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; �������������಻������ת�أ�<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a.������ָ��ķǱ������ݵ�����������ݣ� 
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b.����������ת�ػ�δ����ɲ���ת�����������ݣ� <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c.δ�ɱ��������������á�ת�ص�������Ʒ�ȷǱ�����Ȩ���ݣ� <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; d.���������е�ͼ�Ρ���־��ҳ���񡢱��ŷ�ʽ������ȣ� <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.�����б�������ر���Ȩ�����ע���û��ʸ񷽿�֪�������ݣ� <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; f.�������ɲ����������Ϊ���ʺ�ת�ص����ݡ�<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ��������վ����ע���û��Լ�����ҳ�ڵ������ṩ��ӵ�д���ҳ���������ϵİ�Ȩ����������վ����֤Ϊ���û��ṩ���������õ��ⲿ���ӵ�׼ȷ�Ժ������ԡ�</p>
<p class="list_body"><font color="#ff0000">�ڶ����ݡ���������̳���������ԣ�</font><br>&nbsp;&nbsp;&nbsp;&nbsp; һ�����Ծ����ع����йط��ɷ��档<br>&nbsp;&nbsp;&nbsp;&nbsp; �����κ��˲�������������̳�з���������������֮һ����Ϣ��<br>&nbsp;&nbsp;&nbsp;&nbsp; (һ)�����ܷ���ȷ���Ļ���ԭ��ģ�<br>&nbsp;&nbsp;&nbsp;&nbsp; (��)Σ�����Ұ�ȫ��й¶�������ܣ��߸�������Ȩ���ƻ�����ͳһ�ģ�<br>&nbsp;&nbsp;&nbsp;&nbsp; (��)�𺦹�������������ģ�<br>&nbsp;&nbsp;&nbsp;&nbsp; (��)ɿ�������ޡ��������ӣ��ƻ������Ž�ģ�<br>&nbsp;&nbsp;&nbsp;&nbsp; (��)�ƻ������ڽ����ߣ�����а�̺ͷ⽨���ŵģ�<br>&nbsp;&nbsp;&nbsp;&nbsp; (��)ɢ��ҥ�ԣ�������������ƻ�����ȶ��ģ�<br>&nbsp;&nbsp;&nbsp;&nbsp; (��)ɢ�����ࡢɫ�顢�Ĳ�����������ɱ���ֲ����߽�������ģ�<br>&nbsp;&nbsp;&nbsp;&nbsp; (��)������߷̰����ˣ��ֺ����˺Ϸ�Ȩ��ģ�<br> &nbsp;&nbsp;&nbsp;&nbsp; (��)���з��ɡ����������ֹ���������ݵ� 
��<br>&nbsp;&nbsp;&nbsp;&nbsp; �����û����ге�һ����������Ϊ��ֱ�ӻ��ӵ��µ����»����·������Ρ�<br>&nbsp;&nbsp;&nbsp;&nbsp; �ġ�����������̳�з�����������������뱾��վ�����޹ء�</p></td>
          </tr>
        </table>
      <table width="857" height="62" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
          <tr>
            <td width="13" rowspan="2" align="right" valign="bottom"><img src="images/list_title2_012.gif" width="13" height="62" alt=""/></td>
            <td height="36" colspan="3" align="left" class="list_title2">&nbsp;</td>
            <td width="13" rowspan="2" align="left" valign="bottom"><img src="images/list_title2_052.gif" width="13" height="62" alt=""/></td>
          </tr>
          <tr>
            <td width="32" height="26" align="left" valign="top"><img src="images/list_title2_022.gif" width="32" height="26" alt=""/></td>
            <td width="767" height="26" align="left">&nbsp;</td>
            <td width="32" height="26" align="right" valign="top"><img src="images/list_title2_042.gif" width="32" height="26" alt=""/></td>
          </tr>
      </table></td>
    <td width="20" align="left" valign="top" class="art_list_right"><img src="images/art_list_08_12.jpg" width="20" height="244"/> </td>
  </tr>
  <tr>
    <td><img src="images/art_list_092.jpg" width="21" height="24" alt=""/></td>
    <td class="list_bb">&nbsp;</td>
    <td><img src="images/art_list_112.jpg" width="20" height="24" alt=""/></td>
  </tr>
</table>
<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#C3DE81">
  <tr>
    <td width="44" rowspan="4">&nbsp;</td>
    <td height="23" align="left" valign="middle"><img src="images/wz.gif" width="16" height="18"/></td>
    <td height="23">λ�ã�<span class="style9"><a href="main.jsp">����������</a> -&gt; </span>��������</td>
    <td width="52" rowspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td width="25" height="23" align="left"><img src="images/home.gif" width="16" height="16"/> </td>
    <td width="883" height="23"><a href="http://www.newgxu.cn" class="t">��������վ��ҳ</a></td>
  </tr>
  <tr>
    <td height="23" align="left" valign="top"><img src="images/pp.gif" width="16" height="15"/></td>
    <td height="23">����������<%=OnlineManager.getNumElements()%></td>
  </tr>
  <tr>
    <td height="23" align="left" valign="middle"><img src="images/show2.gif" width="16" height="15"/></td>
    <td height="23">����ʹ�ã�<strong>1024*768�ֱ���</strong>��16λ������ɫ��IE6.0���ϰ汾�����</td>
  </tr>
</table>
<table width="1004" height="25" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#BFDA7D">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="42"><img src="images/bottom_bg.gif" width="1004" height="42"/></td>
  </tr>
</table>
<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#5FA206">
  <tr>
    <td width="134" height="53" align="center"><img src="images/logo.gif" width="88" height="31"/></td>
    <td width="883" class="style62"><a href="./aboutBBS.jsp" target="_blank">������̳</a> | <a href="./artSupport.jsp" target="_blank">����֧��</a> | <a href="./allRight.jsp" target="_blank">��������</a> | <a href="./reserved.jsp" target="_blank">��Ȩ����</a> | <a href="./stat/stat.jsp" target="_blank">��̳ͳ��</a> | ��ICP��05001920�� ����������4501200373�� &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������ ��Ȩ���С�2005-2010 </td>
  </tr>
</table>
<%@ include file="msg.jsp" %>
</body>
</html>
