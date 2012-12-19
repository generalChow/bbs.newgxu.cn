<#assign location="论坛首页">
<#assign version = "雨无声社区 V4.0">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>雨无声论坛 -- ${version}|广西大学论坛BBS|自考|招生|就业|招聘|考研|学院|图书馆</title>
	<meta name="keywords" content="广西 广西大学 广西大学论坛BBS 西大论坛BBS 雨无声论坛BBS 校园论坛BBS 广西论坛BBS 广西大学自考|招生|就业|招聘|考研|学院|图书馆|学生|交易|买卖|平台|新生|二手|跳楼|交友|平台" />
    <meta name="description" content="广西 广西大学 广西大学论坛BBS 西大论坛BBS 雨无声论坛BBS 校园论坛BBS 广西论坛BBS 广西大学自考|招生|就业|招聘|考研|学院|图书馆|学生|交易|买卖|平台|新生|二手|跳楼|教务处交友|平台" />
	
	<link href="/css/default.css" rel="stylesheet" type="text/css" />
	<link href="/css/index.css" rel="stylesheet" type="text/css" />
	
   <script src="/js/jquery-1.4.2.js" type="text/javascript"></script>
  <!-- <script src="/js/jquery-1.4.2.min.js" type="text/javascript"></script>-->
   <script src="/js/grayscale.js" type="text/javascript"></script>
   <script>   
 
    var $j =jQuery.noConflict();
   </script>
	<script type="text/javascript" src="/js/mootools.v1.00.js"></script>
	<script type="text/javascript" src="/js/newgxu_common.js"></script>
	<script type="text/javascript" src="/js/newgxu_dropdownMenu.js"></script>
  	<script type="text/javascript" src="/js/newgxu_dialog.js"></script>
  	<script type="text/javascript" src="/js/newgxu_ubb.js"></script>
  	<script type="text/javascript" src="/js/newgxu_upload.js"></script>
  	<script type="text/javascript" src="/js/audioplayer.js"></script>
  	<script type="text/javascript" src="/js/google_analytics.js"></script>
	
	<script type="text/JavaScript">
		
	<!--
	function MM_openBrWindow(theURL,winName,features) { //v2.0
		window.open(theURL,winName,features);
	}
	function messageList() {
		var width = 700 ;
		var height = 500;
		var left = eval(screen.Width - width) / 2;
		var top = eval(screen.Height  -height) / 2;
		MM_openBrWindow('/message/message_list.yws?folderId=1','查看短消息','menubar=yes,scrollbars=yes,width='+width+',height='+height+',left='+left+',top='+top+'');
	}
	   function fclose()
   {
    document.getElementById("eMeng").style.visibility='hidden';
   }
	     function fpone()
   {
    var str="<DIV id=eMeng >"
  +" <table width='100%' border='0' cellspacing='0' background='images/msgNotice.jpg' class='msgnotice'>"
   +"  <tr>"
   +"    <td width='15%' height='28'>&nbsp;</td>"
   +"    <td width='75%' align='left'>雨无声社区温馨提示：</td>"
    +"    <td width='10%' align='left'><SPAN title=关闭 "
    +"   style='FONT-WEIGHT: bold; FONT-SIZE: 12px; CURSOR: hand; COLOR: black; MARGIN-RIGHT: 4px' "
  +"     onclick=fclose()>×</SPAN></td>"
   +"  </tr>"
  +"   <tr>"
  +"     <td height='95' colspan='3'><table width='100%' border='0' align='center' cellspacing='0'>"
   +"      <tr>"
   +"        <td align='center'><font "
  +"     color='red'><a href='#' onClick='messageList();' ><font color='#FF0000'>您有${userMessage?default('')}条新的短消息<br />"
   +"            请注意查收</font></a></font></td>"
  +"       </tr>"
   +"    </table></td>"
  +"   </tr>"
 +"  </table>"
+" </DIV>"
document.getElementById("eMeng").innerHTML=str;
setTimeout("fclose()",8000);
   }
	//-->
	</script>
	</head>

<body>
<table border="0" align="center" cellpadding="0" cellspacing="0">
<tr align="center"><td>
<div class="body-container">
 
		<div id="Layer1" style="position:absolute; z-index:1;">
       	  <div id="yu" style="position:absolute; z-index:2; left: -400;">
               <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="1004" height="140" align="middle">
                  <param name="movie" value="/images/flash/yu.swf" />
                  <param name="wmode"   value="transparent" />
                  <param name="quality" value="high" />
                  <embed src="/images/flash/yu.swf" width="1004" height="140" align="middle" quality="high" wmode="transparent"  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"></embed>
              </object>
          </div>
            <div id="hudie" style="position:absolute; z-index:2; left: -270; top: 30;">
             <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="566" height="165">
                  <param name="movie" value="/images/flash/hudie.swf"/>
                  <param name="wmode"   value="transparent"/>
                  <param name="quality" value="high"/>
                  <embed src="/images/flash/yu.swf" quality="high" wmode="transparent"  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="566" height="165"></embed>
              </object>
      		</div>
  		</div>
	 
	 <!--用户模块-->
	 ${userModel?default('')}
	
	<div class="index-container">
	  <span class="left">
			<table width="610" style="margin-left:50x " border="0" cellspacing="4" cellpadding="0">
			  <#list areas as area>
				<tr class="botom_line">
					<td width="11%" height="13" align="center" valign="top" class="shadow area"><a href="#"><!--  <a href="area.yws?areaId=${area.id}">-->${area.name}</a></td>
					  <td width="89%" align="left" valign="top" class="bbslm"><span class="bbslm">
						<#list area.forums?sort_by(["compositorId"])  as forum>
						  <a href="forum.yws?forumId=${forum.id}">${forum.name}</a>
						</#list>
					  </span></td>
				</tr>
			  </#list>
			  <tr class="botom_line">
					<td width="11%" height="13" align="center" valign="top" class="shadow area"></td>
					  <td width="89%" align="left" valign="top" class="bbslm"><span class="bbslm">
					    <a href="/user/cupoflife.yws">世界杯</a>
					  </span></td>
				</tr>
			</table>
			</span>
			
			<span class="right">
				<table width="370" height="28" border="0" style="margin-right: 15px" cellpadding="0" cellspacing="0">
				  <tr>
					<td width="66" align="right"><span class="style3">论坛公告：</span></td>
					<td width="241" valign="middle" class="t">		<MARQUEE  scrollAmount="2" scrollDelay="0" onmouseover="this.stop();" onmouseout="this.start();">
						·欢迎光临雨无声论坛，新版使用的spring+jpa+webwork+模板技术在ie6浏览器下表现不佳，我们将会继续改进，在此向关心和支持过雨无声的各新老会员表示衷心的感谢！！
					  </MARQUEE></td>
				  </tr>
				</table>
				
			<table width="370" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="48" background="images/index_hot2_1.gif"><table width="325" height="30" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td align="left"><table width="325" height="38" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="40"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="38" height="40">
                                <param name="movie" value="/images/flash/tree.swf"/>
                                <param name="quality" value="high"/>
                                <param name="wmode"   value="transparent"/>
                                <embed src="/images/flash/tree.swf" quality="high" wmode="transparent"  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="38" height="40"></embed>
                            </object></td>
                            <td height="43" align="left" valign="bottom"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td  width="3" >&nbsp;</td>
                                  <td align="left"><strong>热门话题</strong></td>
                                </tr>
                                <tr>
                                  <td height="2" colspan="2" align="left"></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td background="images/index_hot2_2.gif"><table width="100%" height="12" border="0" cellpadding="0" cellspacing="0">
                   <#list pubHotTopics as t>
                    <tr>
                      <td width="10" height="12" align="right">&nbsp;</td>
                      <td width="10" align="right"><img src="images/q.gif" width="13" height="12"/></td>
                      <td width="300" height="12" align="left">[<a href="/forum.yws?forumId=${t.forum.id}">${t.forum.name}</a>]<a href="/topic.yws?forumId=${t.forum.id}&amp;topicId=${t.topic.id}">${t.title}</a></td>
                    </tr>
                   </#list>
                </table></td>
              </tr>
              <tr>
                <td height="20" background="images/index_hot2_3.gif">&nbsp;</td>
              </tr>
              <tr>
                <td background="images/index_hot2_3.gif"></td>
              </tr>
            </table>
			<table width="370" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="48" background="images/index_hot2_1.gif"><table width="325" height="30" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td align="left"><table width="325" height="38" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="40"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="38" height="40">
                                <param name="movie" value="/images/flash/tree.swf"/>
                                <param name="quality" value="high"/>
                                <param name="wmode"   value="transparent"/>
                                <embed src="/images/flash/tree.swf" quality="high" wmode="transparent"  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="38" height="40"></embed>
                            </object></td>
                            <td height="43" align="left" valign="bottom"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td  width="3" >&nbsp;</td>
                                  <td align="left"><strong>精彩推荐</strong></td>
                                </tr>
                                <tr>
                                  <td height="2" colspan="2" align="left"></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td background="images/index_hot2_2.gif"><table width="100%" height="12" border="0" cellpadding="0" cellspacing="0">
                   <#list pubGoodTopics as t>
                    <tr>
                      <td width="10" height="12" align="right">&nbsp;</td>
                      <td width="10" align="right"><img src="images/q.gif" width="13" height="12"/></td>
                      <td width="300" height="12" align="left">[<a href="/forum.yws?forumId=${t.forum.id}">${t.forum.name}</a>]<a href="/topic.yws?forumId=${t.forum.id}&amp;topicId=${t.topic.id}">${t.title}</a></td>
                    </tr>
                   </#list>
                </table></td>
              </tr>
              <tr>
                <td height="20" background="images/index_hot2_3.gif">&nbsp;</td>
              </tr>
              <tr>
                <td background="images/index_hot2_3.gif"></td>
              </tr>
            </table>
            </span>
	</div>
	<div class="left">
	 <table width="608" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
					<tr>
					  <td width="38%" align="center" background="images/top_users_1.gif"><strong>用户排行榜</strong></td>
					  <td width="53%" background="images/top_users_2.gif">&nbsp;</td>
					  <td width="9%" background="images/top_users_4.gif">&nbsp;</td>
					</tr>
				  </table></td>
				</tr>
				<tr>
				  <td><table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr align="center" class="botom_line">
					  <td width="20%" height="20"><a href="/user/users.yws?type=1">::最有价值TOP10::</a></td>
					  <td width="20%"><a href="/user/users.yws?type=2">::最有钱力TOP10::</a></td>
					  <td width="20%"><a href="/user/users.yws?type=3">::最有影响TOP10::</a></td>
					  <td width="20%"><a href="/user/users.yws?type=4">::十大水鬼::</a></td>
					  <td width="20%"><a href="/user/users.yws?type=5">::十大杰出水手::</a></td>
					</tr>
					  
					<tr align="center">
					  <td width="20%">
						<#list topExpUsers as u>
						  <a href="/user/user_info.yws?id=${u.id}">${u.nick}</a><br />
						</#list>
					  </td>
					  <td width="20%">
						<#list topMoneyUsers as u>
						  <a href="/user/user_info.yws?id=${u.id}">${u.nick}</a><br />
						</#list>
					  </td>
					  <td width="20%">
						<#list topTopicUsers as u>
						  <a href="/user/user_info.yws?id=${u.id}">${u.nick}</a><br />
						</#list>
					  </td>
					  <td width="20%">
						<#list topReplyUsers as u>
						  <a href="/user/user_info.yws?id=${u.id}">${u.nick}</a><br />
						</#list>
					  </td>
					  <td width="20%">
						<#list topGoodUsers as u>
						  <a href="/user/user_info.yws?id=${u.id}">${u.nick}</a><br />
						</#list>
					  </td>
					</tr>
				  </table></td>
				</tr>
		    </table>
	</div>

	<div class="footer">
		<div class="footer_info">
		  <table width="100%" border="0" >
              <tr>
                <td height="23">	
					<div style="width:800px;">
						<div style="float:left;"><img src="/images/wz.gif" width="16" height="18"/> 位置：&nbsp;</div>
						<div style="float:left;">
							<!--位置 start-->
							<ul id="dropdownMenu_footer" class="dropdownMenu">
								<li><a href="#">雨无声社区</a>
									  <ul>
										<li><a href="/index.yws">雨无声论坛</a></li>
										<li><a href="/user/upgrade.yws">个人服务区</a></li>
										<li><a href="/bank/index.yws">社区银行</a></li>
										<li><a href="/market/official.yws">商业中心</a></li>
									  </ul>
									</li>
									<li>&nbsp;－&gt;&nbsp;</li>
									
									<#if areas?exists>
									<li><a href="/index.yws">雨无声论坛</a>
									  <ul>
										<#list areas as area>
										<li><a href="javascript:void(0);">&nbsp;${area.name}</a>
										  <ul style="margin-left:60px; width:205px;">
										    <#if area.id==2>
										    <#list area.forums?sort_by(["compositorId"]) as forum>
											<#if forum.hot>
											<li><a href="/forum.yws?forumId=${forum.id}" style="color:red" >&nbsp;${forum.name}</a></li>
											<#else>
											<li><a href="/forum.yws?forumId=${forum.id}">&nbsp;${forum.name}</a></li>
											</#if>
											</#list>
										    <#else>
										    <#list area.forums?sort_by(["compositorId"]) as forum>
											<#if forum.hot>
											<li><a href="/forum.yws?forumId=${forum.id}" style="color:red" >&nbsp;${forum.name}</a></li>
											<#else>
											<li><a href="/forum.yws?forumId=${forum.id}">&nbsp;${forum.name}</a></li>
											</#if>
											</#list>
										    </#if>
										  </ul>
										</li>
										</#list>
									  </ul>
									</li>
									<li>&nbsp;－&gt;&nbsp;</li>
									</#if>
									
									<#if forum?exists>
									<li><a href="#">${model.forum.area.name}</a>
									  <ul>
										<#list forum.area.forums as forum>
										<li><a href="/forum.yws?forumId=${forum.id}">&nbsp;${forum.name}</a></li>
										</#list>
									  </ul>
									</li>
									<li>&nbsp;－&gt;&nbsp;</li>
									</#if>
									
									<#if topic?exists>
										<li>
											<a href="/forum.yws?forumId=${model.topic.forum.id}">
											${model.topic.forum.name}</a>
										</li>
										<li>&nbsp;－&gt;&nbsp;</li>
									</#if>
								<!--位置 end-->
								<li>${location?default("")}</li>
							</ul>
							<script language="javascript">new newgxu.DropdownMenu($('dropdownMenu_footer'));</script>
						</div>						
					</div>
				</td>
            </tr>
              <tr>
                <td height="23"><img src="/images/home.gif" width="16" height="16"/> <a href="http://www.newgxu.cn" class="t">雨无声网站首页</a> </td>
              </tr>
              <tr>
                <td height="23"><img src="/images/pp.gif" width="16" height="15"/> 在线人数：${onlineStatus.total} 总访问： ${status.totalHicount}&nbsp;今日访问：${status.todayHicount}</td>
              </tr>
              <tr>
                <td height="23"><img src="/images/show2.gif" width="16" height="15"/> 建议使用：1024*768分辨率，16位以上颜色、IE6.0以上版本浏览器</td>
              </tr>
          </table>
			<br />
		</div>
		<div class="footer_img"></div>
		<div class="footer_logo">
		  <table width="100%" cellpadding="0" cellspacing="0" height="53" border="0">
            <tr>
              <td width="13%" align="center" valign="middle"><img src="/images/logo.gif" width="88" height="31"/></td>
              <td width="87%" align="center" valign="middle"><a href="/aboutBBS.htm ">关于论坛 </a>
			  | <a href="/artSupport.htm">美术支持</a> 
			  | <a href="/reserved.htm">免责声明</a> 
			  | <a href="/allRight.htm">版权申明</a> 
			  | <a href="#">合作伙伴</a> 
			  | <a href="#">友情链接</a> 
			  |	桂ICP备05001920号 南宁网警备4501200373号  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;雨无声 版权所有◎2005-2010</td>
            </tr>
          </table>
		</div>
	</div>
</div>
</td></tr></table>
<#if status.login>
<#if status.messageNotRead gt 0>
<DIV id=eMeng ></DIV>
 <script type="text/javascript">
 setTimeout("fpone( )", 3000);
</script>
</#if>
</#if>
</body>
</html>