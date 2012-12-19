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
	//点击统计
	CounterManager.log(gloForumID, 1);
} catch (UnauthorizedException e) {
	errorMessage = "论坛程序检测到异常发生，程序终止！!";
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
	//得到基本变量
int forumID = ParamUtils.getIntParameter(request, "forumID", 1);
int pageID = ParamUtils.getIntParameter(request, "page", 1);
int type = ParamUtils.getIntParameter(request, "type", 0);
//得到论坛对象。
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

//论坛信息
//取得该页所有主题ID号的 迭代子
//符合分类主题数
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

//版主
int[] masters = MasterManager.getMasters(Finstance.getForumID());
Client pageUser = null;

//主题信息
int topicID;
Topic pageTopic;

//用户
Client newUser, reUser;

//页面跳转
int[] pageGForums = GForumManager.getGForums();
GForum GFinstance = null;


//下拉菜单显示
StringBuffer gForumSB = new StringBuffer();
//所有区论坛ID号
int[] listGForums = GForumManager.getGForums();
//下拉菜单区论坛
GForum listGForum, thisGForum;
//下拉菜单论坛
Forum listForum, thisForum;
try {
	thisForum = ForumFactory.getForum(forumID);
	thisGForum = GForumFactory.getGForum(thisForum.getMainID());
} catch (ForumNotFoundException fe) {
	return;
}
//加入在线列表。
		OnlineManager.add(String.valueOf(authToken.getUserID()), 
	(Cacheable)OnlineFactory.getOnline(authToken.getUserID(), 
	false, "<a href=forumsList.jsp?gForumID="+thisGForum.getMainID()+">"+thisGForum.getName()+"</a>-><a href=topicsList.jsp?forumID="+forumID+">"+Finstance.getName()+"</a>", request)
		);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>欢迎来到<%=YYGlobals.getYYProperty("forum.info.name")%>-><%=thisGForum.getName()%> -><%=Finstance.getName()%></title>
<meta name="generator" content="newgxu">
<meta name="keywords" content="广西大学 广西大学雨无声网站 雨无声论坛">
<meta name="description" content="广西大学 广西大学雨无声网站 雨无声论坛">
<meta http-equiv="refresh" content="1500">
<link href="list2.css" rel="stylesheet" type="text/css">
<link rel="alternate" type="application/rss+xml" title="雨无声论坛：<%=Finstance.getName()%>" href="Rss.jsp?forumID=<%=forumID%>" />

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
//下拉菜单相关代码
 var h;
 var w;
 var l;
 var t;
 var topMar = 1;
 var leftMar = -2;
 var space = 1;
 var isvisible;
 var isvisible2;
 var MENU_SHADOW_COLOR='#D7E9AD';//定义下拉菜单阴影色
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
//分论坛列表
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
            <td width="67" height="40" align="right" background="images/tit1.jpg">嘿，欢迎您　</td>
            <td width="217" height="40" align="left" background="images/tit.jpg">  <%
  	Client includeUser = null;
  	Group includeGroup = null;
  	String face = "<img src=\"images/bz_06.jpg\" width=\"126\" height=\"72\">";
    	//如果是匿名用户
    	if (PopedomManager.isAnonymous(authToken)) {
  		out.print("，游客");
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
  		out.print("，"+includeUser.getNickname());
  		face = includeUser.getFace();
  	}
  %></td>
            <td width="272" background="images/tit.jpg">
			<%if(includeUser != null) {%>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="20"><a href="controlPanel.jsp" >个人服务区</a>&nbsp;&nbsp;<a href="diary/" target="_blank">心情日记</a>&nbsp;&nbsp;&nbsp;<a href="index.jsp?action=logout">退出</a>&nbsp;&nbsp;<a href="#" onClick="MM_openBrWindow('shortMessagesList.jsp','查看短消息','menubar=yes,scrollbars=yes,width=700,height=500')" >新消息：<%=ShortMessageManager.getNewSMCount(authToken)%>条</a></td>
                  </tr>
                </table>
				<%}%>            </td>
            <td width="18" height="40" background="images/tit3.jpg">&nbsp;</td>
          </tr>
      </table></td>
    <td width="270" class="top_bg_23"><div id="Layer1" style="position:absolute; z-index:1">
      <%
	  //如果是匿名用户
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
          <td align="left" class="xlogo2">免责声明</td>
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
            <td height="36" colspan="3" class="list_title">&nbsp; 免责声明</td>
          </tr>
      </table>
      <table width="95%" height="20"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bian2">
          <tr>
            <td><p class="list_body">		一、本社区是思政教育主题网站所属论坛，服务对象是已完成本论坛注册认证手续的正式用户。现实行实名注册方式，本论坛为不同用户设置了相应的注册通道。 <br>
              1.在校全日制学生通过“在校本科生注册”通道注册； <br>
              2.在校成教生、行健生、硕士博士生通过“在校成教生、行健生、硕士博士研究生注册”注册。 <br>
              3.教师通过“教师注册”通道注册； <br>
              4.校外人员通过“校外人员注册”通道注册； <br>
              在校生在注册时，若系统自动认证失败，可能是没拿到所有在校全日制学生资料的原因，我们会尽快完善系统注册信息。教师和校外人员的注册需要人工认证，请您认真填写，我们将会在48小时之内认证您的注册信息。 <br>
              二、本论坛的开放目的是为了繁荣本校学术气氛、体现学校生活气息；加强学生、学校之间的沟通与联系；本论坛主要限于广西大学校内外信息的发布、教学和科学研究问题的讨论、高新技术的探索、艺术文化体育的共赏、网友的交流等等，以为网友提供一定的知识、信息；方便网友学习、娱乐、联系等一系列交流活动。对于未经确定的消息，特别是敏感的政治问题，本论坛不提供讨论空间。 <br>
              三、本社区网友言论仅代表作者个人意见，与雨无声论坛立场无关；社区内所有内容并不反映雨无声网站以及与论坛相关实体的任何意见和任何观点。本论坛上的任何指向其它第三方网页的链接，皆是出于传播有利于论坛发展和网友交流信息之目的，得到的资讯、产品及服务，本论坛将不负包括法律责任在内的任何责任。用户自行承担一切因您的行为而直接或间接导致的民事或刑事法律责任。<br>
              四、在使用本论坛服务的时候，网友不得违反雨无声论坛网管部一致决意后公布的任何形式的任何规定；不得发布任何诽谤性的、侮辱性的、低俗的、猥亵的、亵渎性的、有色情倾向的、威胁性的、侵犯隐私的、或与任何法律条款相抵触的言论；不得有任何商业广告的链接，不得有攻击雨无声论坛服务器及上传、发布任何带有病毒的文件等恶意行为。鉴于论坛的实时性，我们无法浏览所有帖子，所以本论坛不会对帖子的正确性、完整性和有效性做出任何形式的保证，仅供访问者参照使用。 <br>
              五、论坛网友之间通过本论坛相识、交往、交易、版聚等参与任何形式的网下活动，都属于网友个人自愿行为，此过程中所发生或可能发生的任何心理、生理上的伤害和经济上的损失，以及所产生的其他风险全部由网友个人承担，本论坛不承担任何责任。对于造成的相关风险事故，雨无声网站将不负包括法律责任在内的任何责任。<br>
              六、鉴于网络硬件、互联网的客观原因，网络服务不可避免会不定期遇到某些不可预计或不可抗拒的突发事件，例如新病毒爆发、网络堵塞、黑客攻击、在线人数溢出、服务器硬件故障、系统内部突击维护等等，这些有可能会造成论坛有短暂的无法正常访问。为防止更加严重的后果（如数据丢失）给您带来不便或损失，所以请随时备份好您认为重要的回帖、对自己对网友有价值的帖子、短消息等各种信息，本论坛不会为这些意外造成的损失给予任何物质上的补偿。<br>
              七、除注明之服务条款外，其它因使用雨无声论坛而引致的任何意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其所造成的各种损失（包括因下载而感染电脑病毒），本论坛将不负包括法律责任在内的任何责任。<br>
              八、本论坛认为，凡以任何方式登陆本论坛或直接、间接使用本论坛资料者，视为自愿接受本论坛声明的约束，并开始享受本论坛相关服务和遵守本论坛任何规定。本声明未涉及的问题参见国家有关法律法规，当本声明与国家法律法规冲突时，以国家法律法规为准。<br>
              九、本论坛的最高权利机构为雨无声论坛网管部。本论坛所有权、经营权均属雨无声网站所有，未经协议授权不得转载或镜像，否则依法追究法律责任。被授权转载者务必注明来源“广西大学雨无声论坛”。<br>
            十、本声明解释权归雨无声网管部所有，并有权视情随时更改。</p></td>
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
    <td height="23">位置：<span class="style9"><a href="main.jsp">雨无声社区</a> -&gt; </span>免责声明</td>
    <td width="52" rowspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td width="25" height="23" align="left"><img src="images/home.gif" width="16" height="16"/> </td>
    <td width="883" height="23"><a href="http://www.newgxu.cn" class="t">雨无声网站首页</a></td>
  </tr>
  <tr>
    <td height="23" align="left" valign="top"><img src="images/pp.gif" width="16" height="15"/></td>
    <td height="23">在线人数：<%=OnlineManager.getNumElements()%></td>
  </tr>
  <tr>
    <td height="23" align="left" valign="middle"><img src="images/show2.gif" width="16" height="15"/></td>
    <td height="23">建议使用：<strong>1024*768分辨率</strong>，16位以上颜色、IE6.0以上版本浏览器</td>
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
    <td width="883" class="style62"><a href="./aboutBBS.jsp" target="_blank">关于论坛</a> | <a href="./artSupport.jsp" target="_blank">美术支持</a> | <a href="./allRight.jsp" target="_blank">免责声明</a> | <a href="./reserved.jsp" target="_blank">版权声明</a> | <a href="./stat/stat.jsp" target="_blank">论坛统计</a> | 桂ICP备05001920号 南宁网警备4501200373号 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;雨无声 版权所有◎2005-2010 </td>
  </tr>
</table>
<%@ include file="msg.jsp" %>
</body>
</html>
