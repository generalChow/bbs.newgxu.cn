<?xml version="1.0" encoding="gb2312"?><!DOCTYPE xsl:stylesheet  [
	<!ENTITY nbsp   "&#160;">
	<!ENTITY copy   "&#169;">
	<!ENTITY reg    "&#174;">
	<!ENTITY trade  "&#8482;">
	<!ENTITY mdash  "&#8212;">
	<!ENTITY ldquo  "&#8220;">
	<!ENTITY rdquo  "&#8221;"> 
	<!ENTITY pound  "&#163;">
	<!ENTITY yen    "&#165;">
	<!ENTITY euro   "&#8364;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="gb2312" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
<xsl:template match="/">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<title>��ӭ����<xsl:value-of select="newgxu/bbSVersionInfo/bbsVersion"/></title>
<link href="list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"><xsl:text disable-output-escaping="yes">
<![CDATA[

<!--

//-->
]]>
</xsl:text>
</script>

<script src="bbs.js" type="text/javascript"></script>

</head>

<body>

<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <xsl:choose>
      <xsl:when test="newgxu/count/now&gt;=19">
        <td height="184" class="top_bg2">&nbsp;</td>
      </xsl:when>
      <xsl:when test="newgxu/count/now&lt;=8">
        <td height="184" class="top_bg2">&nbsp;</td>
      </xsl:when>
		<xsl:otherwise>
        <td height="184" class="top_bg">&nbsp;</td>
      </xsl:otherwise>
    </xsl:choose>
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
          <td width="67" height="40" align="center" background="images/tit1.jpg">�٣���ӭ��</td>
          <td width="115" height="40" align="left" background="images/tit.jpg"><span><a href="{newgxu/loginUserInfo/link}"><xsl:value-of select="newgxu/loginUserInfo/nickName" disable-output-escaping="yes"/></a></span></td>
          <td width="372" background="images/tit.jpg"><xsl:if test="newgxu/loginUserInfo/nickName!=''">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"><a href="controlPanel.jsp" class="h">���˷�����</a>&nbsp;&nbsp;<a href="../../diary/viewDiaryBook.jsp?userID={newgxu/loginUserInfo/userID}" target="_blank" class="h">�����ռ�</a>&nbsp;&nbsp;&nbsp;<a href="index.jsp?action=logout" class="h">�˳�</a>&nbsp;&nbsp;<a href="controlPanel.jsp" class="h">����Ϣ��<xsl:value-of select="newgxu/shortMessageinfo/newShortMessageCount"/>��</a></td>
              </tr>
            </table>
          </xsl:if></td>
          <td width="19" height="40" background="images/tit3.jpg">&nbsp;</td>
        </tr>
      </table></td>
    <td width="270" class="top_bg_23"><div id="Layer1" style="position:absolute; z-index:1">
      <div id="yhc" style="position:absolute; width:600px; height:150px; z-index:1; left: -331px; top: -249px;">
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="600" height="150">
          <param name="movie" value="flash/yinghuochong2.swf"/>
		  <param name="wmode"   value="transparent"/>
          <param name="quality" value="high"/>
          <embed src="flash/yinghuochong2.swf" quality="high" wmode="transparent"  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="600" height="150"></embed>
        </object>
      </div></div></td>
  </tr>
</table>
<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
  <tr>
    <td width="45" rowspan="3" class="center_bg">&nbsp; </td>
    <td align="right" valign="bottom"> <img src="images/art_list_02.jpg" width="21" height="30" alt=""/></td>
    <td height="30" class="list_center_top">&nbsp; </td>
    <td> <img src="images/art_list_04.jpg" width="20" height="30" alt=""/></td>
    <td width="45" rowspan="3" class="center_bg">&nbsp;</td>
  </tr>
  <tr>
    <td width="21" align="right" valign="top" class="art_list_left"><img src="images/art_list_06_1.jpg" width="21" height="244"/> </td>
    <td align="center" valign="top" class="center_c"><table width="100%" height="40"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" class="xlogo"><img src="images/home.gif" width="16" height="16"/>&nbsp;<span class="style9"><a href="index.jsp">��������̳ </a>-- �û���Ϣ </span></td>
        </tr>
      </table>
        <table width="100%" height="10"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center"><img src="images/banner-userinfo.gif" width="151" height="45" /></td>
          </tr>
        </table>
      <table width="96%" height="3"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_title_2">
          <tr>
            <td height="1"></td>
          </tr>
        </table>
      <table width="96%" height="25"  border="0" align="center" cellpadding="2" cellspacing="1" class="botom_line">
          <tr>
            <td align="center" bgcolor="#FFFDF6" class="right_line">&nbsp;</td>
          </tr>
        </table>
      <table width="96%" height="20"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bian">
          <tr>
            <td align="center"><table width="70%" border="0" cellpadding="3" cellspacing="1" class="green-border">
                <tr class="trBorder1">
                  <td height="25" colspan="3" align="center" bgcolor="#FFFDF6">&nbsp;</td>
                </tr>
                <tr>
                  <td width="120" height="20"><div align="right">�ǳƣ�</div></td>
                  <td align="left"><xsl:value-of select="string(newgxu/userInfo/nickName)" disable-output-escaping="yes"/></td>
                  <td width="200" rowspan="7" align="center"><xsl:value-of select="string(newgxu/userInfo/face)" disable-output-escaping="yes"/></td>
                </tr>
                <tr>
                  <td height="20" bgcolor="#FFFDF6"><div align="right">QQ��</div></td>
                  <td align="left" bgcolor="#FFFDF6"><xsl:value-of select="newgxu/userInfo/QQ"/></td>
                </tr>
                <tr>
                  <td height="20"><div align="right">ͷ�Σ�</div></td>
                  <td align="left"><xsl:value-of select="string(newgxu/userInfo/title)" disable-output-escaping="yes"/></td>
                </tr>
                <tr>
                  <td height="20" bgcolor="#FFFDF6"><div align="right">��ֹ���룺</div></td>
                  <td align="left" bgcolor="#FFFDF6">��<xsl:value-of select="newgxu/userInfo/loginStatu"/>�����޷�����</td>
                </tr>
                <tr>
                  <td height="20"><div align="right">����/Ǯ��</div></td>
                  <td align="left"><xsl:value-of select="newgxu/userInfo/expence"/> / <xsl:value-of select="newgxu/userInfo/money"/></td>
                </tr>
                <tr>
                  <td height="20" bgcolor="#FFFDF6"><div align="right">�Ա�</div></td>
                  <td align="left" bgcolor="#FFFDF6"><xsl:value-of select="newgxu/userInfo/sex"/></td>
                </tr>
                <tr>
                  <td height="20"><div align="right">���գ�</div></td>
                  <td align="left"><xsl:value-of select="newgxu/userInfo/birthday"/></td>
                </tr>
                <tr>
                  <td height="20" bgcolor="#FFFDF6"><div align="right">E-mail��</div></td>
                  <td colspan="2" align="left" bgcolor="#FFFDF6"><xsl:value-of select="string(newgxu/userInfo/email)"/></td>
                </tr>
                <tr>
                  <td height="20"><div align="right">��ҳ��</div></td>
                  <td colspan="2" align="left"><a href="" target="_blank"><xsl:value-of select="string(newgxu/userInfo/homepage)"/></a></td>
                </tr>
                <tr>
                  <td height="20" bgcolor="#FFFDF6"><div align="right">ע��ʱ�䣺</div></td>
                  <td colspan="2" align="left" bgcolor="#FFFDF6"><xsl:value-of select="newgxu/userInfo/registerTime"/></td>
                </tr>
                <tr>
                  <td height="20"><div align="right">��¼������</div></td>
                  <td colspan="2" align="left"><xsl:value-of select="newgxu/userInfo/loginTimes"/></td>
                </tr>
                <tr>
                  <td height="20" bgcolor="#FFFDF6"><div align="right"><font color="#FF0000">����¼��</font></div></td>
                  <td colspan="2" align="left" bgcolor="#FFFDF6"></td>
                </tr>
                <tr>
                  <td height="20"><div align="right"><font color="#FF0000">��ʵ������</font></div></td>
                  <td colspan="2" align="left"></td>
                </tr>
                <tr>
                  <td height="20" bgcolor="#FFFDF6"><div align="right">����/�ظ�/������</div></td>
                  <td colspan="2" align="left" bgcolor="#FFFDF6"><xsl:value-of select="newgxu/userInfo/postNewCount"/> / <xsl:value-of select="newgxu/userInfo/replyCount"/> / <xsl:value-of select="newgxu/userInfo/postGood"/></td>
                </tr>
                <tr>
                  <td height="20"><div align="right">���/ʣ��������</div></td>
                  <td colspan="2" align="left"><xsl:value-of select="newgxu/userInfo/maxPow"/> / <xsl:value-of select="newgxu/userInfo/lastPow"/></td>
                </tr>
                <tr>
                  <td height="20" bgcolor="#FFFDF6"><div align="right">�ȼ���</div></td>
                  <td colspan="2" align="left" bgcolor="#FFFDF6"><xsl:value-of select="newgxu/userInfo/groupName" disable-output-escaping="yes"/></td>
                </tr>
                <tr>
                  <td height="20"><div align="right">����ǩ����</div></td>
                  <td colspan="2" align="left"><xsl:value-of select="string(newgxu/userInfo/sign)" disable-output-escaping="yes"/></td>
                </tr>
                <tr>
                  <td height="20" colspan="3" bgcolor="#FFFDF6"><div align="center">[<a href="searchResult.jsp?type=1&amp;keywords={string(newgxu/userInfo/nickName)}">���������</a>]&nbsp;&nbsp;[<a href="newShortMessage.jsp?userID={newgxu/userInfo/userID}">���Ͷ���</a>]&nbsp;&nbsp;[<a href="newShortMessage.jsp?isQuery=true&amp;userID={newgxu/userInfo/userID}">��Ϊ����</a>]&nbsp;&nbsp;[<a href="diary/viewDiaryBook.jsp?userID={newgxu/loginUserInfo/userID}">�����ռ�</a>]&nbsp;&nbsp;[<a href="#" onclick="javascript:history.go(-1)">����ǰҳ</a>]</div></td>
                </tr>
            </table></td>
          </tr>
        </table>
      <table width="96%" height="3"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_title_2">
          <tr>
            <td height="1"></td>
          </tr>
        </table>
      <table width="857" height="62" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
          <tr>
            <td width="13" rowspan="2" align="right" valign="bottom"><img src="images/list_title2_01.gif" width="13" height="62" alt=""/></td>
            <td height="36" colspan="3" align="left" class="list_title2"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="4%" class="list_title2_left">&nbsp;</td>
                  <td width="96%" align="right" class="list_title2_right">&nbsp;
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="82%" height="20" align="left">&nbsp;</td>
                          <form id="form1" name="form1" action="?" method="get">
                            <td width="18%" align="right">&nbsp;</td>
                          </form>
                        </tr>
                    </table></td>
                </tr>
            </table></td>
            <td width="13" rowspan="2" align="left" valign="bottom"><img src="images/list_title2_05.gif" width="13" height="62" alt=""/></td>
          </tr>
          <tr>
            <td width="32" height="26" align="left" valign="top"><img src="images/list_title2_02.gif" width="32" height="26" alt=""/></td>
            <td width="767" height="26">&nbsp;</td>
            <td width="32" height="26" align="right" valign="top"><img src="images/list_title2_04.gif" width="32" height="26" alt=""/></td>
          </tr>
      </table></td>
    <td width="20" align="left" valign="top" class="art_list_right"><img src="images/art_list_08_1.jpg" width="20" height="244"/> </td>
  </tr>
  <tr>
    <td> <img src="images/art_list_09.jpg" width="21" height="24" alt=""/></td>
    <td class="list_bb">&nbsp; </td>
    <td> <img src="images/art_list_11.jpg" width="20" height="24" alt=""/></td>
  </tr>
</table>
<table width="1004" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
 <tr>
    <td width="44" rowspan="4">&nbsp;</td>
	 <td height="23" align="left" valign="middle"><img src="images/wz.gif" width="16" height="18"/></td>
    <td height="23">λ�ã���̳��ҳ</td>
     <td width="52" rowspan="4">&nbsp;</td>
 </tr>
  <tr>
    <td width="25" height="23" align="left"><img src="images/home.gif" width="16" height="16"/> </td>
    <td width="883" height="23"><a href="%20" class="t">��������վ��ҳ</a></td>
  </tr>
  <tr>
    <td height="23" align="left" valign="top"><img src="images/pp.gif" width="16" height="15"/></td>
    <td height="23">����������<xsl:value-of select="newgxu/count/online"/> �ܷ��ʣ�<xsl:value-of select="newgxu/count/totleHits"/> ���շ��ʣ�<xsl:value-of select="newgxu/count/todayHits"/></td>
  </tr>
  <tr>
    <td height="23" align="left" valign="middle"><img src="images/show2.gif" width="16" height="15"/></td>
    <td height="23">����ʹ�ã�<strong>1024*768�ֱ���</strong>��16λ������ɫ��IE6.0���ϰ汾�����</td>
  </tr>
</table>
<table width="1004" height="25" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
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
    <td width="883" class="style6">������̳ | ����֧�� | <span class="b">��������</span> | ��Ȩ���� | ������� | �������� | 


    ��ICP��05001920�� ����������4501200373��  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������ ��Ȩ���С�2005-2006 </td>
  </tr>
</table>
</body>
</html>

</xsl:template>
</xsl:stylesheet>