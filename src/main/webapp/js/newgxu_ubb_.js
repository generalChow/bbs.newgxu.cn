var contentObj;
var titleObj;
var formObj;

var replyDiv="reply_div";
/**
 * 快速回复
 * @param {Object} fid
 * @param {Object} tid
 * @param {Object} ridinitUbb
 */
function replyFast(obj,fid,tid,rid,page){
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var width = 870;
	var height = 480;
	var left = eval(screen.Width - width) / 2;
	var top = eval(screen.Height - height) / 2;
	
	var options="dialogWidth:"+width+"px;dialogHeight:"+height+"px"+"center: yes;scroll: yes; status: no;help:no;";
	var result=window.showModalDialog("/replyFast.yws?forumId="+fid+"&topicId="+tid+"&replyId="+rid+"&page="+page,"快速回复",options);
}
function editReplyMessage(origin){
	var type=$j("input[name='replyMessage']:checked").val();
	//没有变化就不需要提交了
	if(type==origin){
		return false;
	}
	$j.get("/user/edit_user_replyMessage.yws?type="+type,{},function(data,state){
		if(state=='success'){
			if(data=='0')
				$j("#replyMessage_info").html("修改成功");
			else
				$j("#replyMessage_info").html("服务器出错....请稍后再试");
		}
	});
}


// function initUbb() {
//     try{
//     $j("#content").select(function(){
//         selectStart=getLocation("content").start;
// 		selectEnd=getLocation("content").end;
// 		//alert(selectStart+"   "+selectEnd);
//     })
//     $j("#content").keypress(function(){
//        selectStart=getLocation("content").start;
// 		selectEnd=getLocation("content").end;
// 		//alert(selectStart+"   "+selectEnd);
//     })
//     $j("#content").click(function(){
//        selectStart=getLocation("content").start;
// 		selectEnd=getLocation("content").end;
// 		//alert(selectStart+"   "+selectEnd);
//     })
//     $j("#content").focus(function(){
//         selectStart=getLocation("content").start;
// 		selectEnd=getLocation("content").end;
// 		//alert(selectStart+"   "+selectEnd);
//     })
//     }catch(e){
//     }
// 	contentObj = $("content");
// 	titleObj = $("title");
// 	formObj = $("post_form");
// }

function selectTopicType(obj) {
	titleObj.value= obj.options[obj.selectedIndex].value+titleObj.value;
	titleObj.focus();
}

function contentChange(begin, end){
	if ((document.selection) && (document.selection.type == "Text")) {
		var range = document.selection.createRange();
		var chText = range.text;
		range.text = begin + chText + end;
	} else {
		contentObj.value = begin + contentObj.value + end;
		contentObj.focus();
	}
}

function contentAdd(str) {
		/*
		 * 重定向到toAddContent
		 */
	
		toAddContent(str);
}

function contentAdd2(text) {
	//contentObj.value += text;
	contentObj.focus();
//	document.selection.createRange().text = text; 
	//以下代码是为了解决不同浏览器兼容问题
	var Sys = {}; 
	var ua = navigator.userAgent.toLowerCase(); 
	var s; 
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 
	(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : 
	(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : 
	(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : 
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0; 
	//以下进行测试 
	if (Sys.ie){
		document.selection.createRange().text = text;
	}else if (Sys.firefox){
		document.getElementById("content").value+=text;
	}else	if (Sys.chrome){
		document.getElementById("content").value+=text;
	}else 	if (Sys.opera){
		
	}else if (Sys.safari){
		
	}
}

/*
 * 添加指定的内容，兼容主流浏览器（这个很保守，因为测试的也是主流浏览器）
 * 集成显卡  2011.5.18
 */
function toAddContent(str){
	var ubbContent=document.getElementById("content");
	var ubbLength=ubbContent.value.length;
	ubbContent.focus();
	if(typeof document.selection !="undefined")
	{
		ubbContent.value=ubbContent.value.substr(0,selectStart)+str+ubbContent.value.substring(selectEnd,ubbLength);
	}
	else
	{
		ubbContent.value=ubbContent.value.substr(0,selectStart)+str+ubbContent.value.substring(selectEnd,ubbLength);
	}
}

function faceAdd(faceId) {
	var text = '[face' + faceId + ']';
	contentAdd2(text);
}

function emAdd(emId) {
	var text = '[em' + emId + ']';
	contentAdd2(text);
}

function selectFont(font) {
	contentChange("[face=" + font + "]", "[/face]");
}

function selectSize(size) {
	contentChange("[size=" + size + "]", "[/size]");
}

//function selectColor(color) {
//	contentChange("[color=" + color + "]", "[/color]");
//}

function selectColor() {
	var color =  getColor();
	if (color == null) { color = "#000000"; }
	contentChange("[color=" + color + "]", "[/color]");
}

function selectBold() {
	contentChange("[B]", "[/B]");
}

function selectItalic() {
	contentChange("[I]", "[/I]");
}

function selectUnder() {
	contentChange("[U]", "[/U]");
}

function selectCenter() {
	contentChange("[center]", "[/center]");
}

function selectGlow() {
	var color =  getColor();
	if (color == null) { color = "RED"; }
	contentChange("[GLOW=255," + color + ",2]", "[/GLOW]");
}

function selectShadow() {
	var color =  getColor();
	if (color == null) { color = "RED"; }
	contentChange("[SHADOW=255," + color + ",2]", "[/SHADOW]");
}

function checkWidthAndHeight(e1, e2) {
	if (e1.value > 600 || e1.value < 1) {
		alert("宽不能超过600或小于1");
		e1.focus();
		return false;
	} else if(e2.value > 1000 || e2.value < 1) {
		alert("高不能超过1000或小于1");
		e2.focus();
		return false;
	}
	return true;
}

var ubbDialog;

function selectUrl() {
	executeUbbDialog('getUrlHtml()');
}

function getUrlHtml() {
	return '<table width="100%" border="0"><tr> <td>链接地址： <input name="Curl" type="text" id="Curl" value="http://" size="25" onMouseOver="this.select()"></td></tr><tr> <td>链接说明：<input name="Ctext" type="text" id="Ctext" size="25"> </td></tr><tr> <td><div align="center"><input type="button" name="Button" value="确定" onClick="urlPub()"></div></td></tr></table>';	
}

function urlPub() {
	var curl = $("Curl");
	var ctext = $("Ctext");
	if (curl.value == "http://") {
		alert("输入链接地址！");
		curl.focus();
	} else if (ctext.value == "") {
		alert("输入链接说明！");
		ctext.focus();
	} else {
		var text = "[url="+curl.value+"]"+ctext.value+"[/url]";
		closeUbbDialog();
		contentAdd(text);
	}
}

function selectEmail() {
	executeUbbDialog('getEmailHtml()');
}

function getEmailHtml() {
	return '<table width="100%" border="0"><tr><td>邮件地址：<input name="email" type="text" id="email" size="25"></td></tr><tr> <td><div align="center"><input type="button" name="Submit2" value="确定" onClick="emailPub()"></div></td></tr></table>';	
}

function emailPub() {
	var email = $("email")
	if (email.value == "") {
		alert("输入邮件地址！");
		email.focus();
	} else {
		var text = "[email]"+email.value+"[/email]";
		closeUbbDialog();
		contentAdd(text);
	}
}

function selectPic() {
	executeUbbDialog('getPicHtml()');
}

function getPicHtml() {
	return '<table width="100%" border="0"><tr><td>图片地址：<input name="pic" type="text" id="pic" value="http://" size="25" onMouseOver="this.select()"></td></tr><tr><td><div align="center"><font color="#FF0000">注意：请不要从您电脑的硬盘中贴图！</font></div></td></tr><tr> <td><div align="center"><input type="button" name="Submit22" value="确定" onClick="picPub()"></div></td></tr></table>';	
}

function picPub() {
	var pic = $("pic")
	if (pic.value == "http://" || pic.value == "") {
		alert("输入图片地址！");
		pic.focus();
	} else {
		var text = "[img]"+pic.value+"[/img]";
		closeUbbDialog();
		contentAdd(text);
	}
}

function selectFlash() {
	executeUbbDialog('getFlashHtml()');
}

function getFlashHtml() {
	return '<table width="100%" border="0"><tr><td colspan="2">flash地址：<input name="flash" type="text" id="flash" value="http://" size="25" onMouseOver="this.select()"></td></tr><tr><td width="50%"><div align="right">宽度：<input id="kuan" type="text" value="500" size="4" maxlength="3"><br><font color="#FF0000"></font></div></td><td width="50%">高度：<input id="gao" type="text" value="350" size="4" maxlength="3"></td></tr><tr><td colspan="2"><div align="center"><font color="#FF0000">注意：若您不能确定flash的宽和高，请保持默认宽高</font></div></td></tr><tr><td colspan="2"><div align="center"><input type="button" value="确定" onClick="flashPub()"></div></td></tr></table>';	
}

function flashPub() {
	var flash = $("flash");
	if (flash.value == "http://" || flash.value == "") {
		alert("输入flash地址！");
		flash.focus();
		return;
	} 
	if (checkWidthAndHeight($("kuan"), $("gao"))) {
		var text = "[flash="+$("kuan").value+","+$("gao").value+"]"+flash.value+"[/flash]";
		closeUbbDialog();
		contentAdd(text);
	}
		
}

function selectReal() {
	executeUbbDialog('getRealHtml()');
}

function getRealHtml() {
	return '<table width="100%" border="0"><tr><td colspan="2">文件地址：<input name="rm" type="text" id="rm" value="http://" size="25" onMouseOver="this.select()"></td></tr><tr> <td width="50%"><div align="right">宽度： <input name="kuan" type="text" id="kuan" value="500" size="4" maxlength="3"></div></td><td width="50%">高度： <input name="gao" type="text" id="gao" value="350" size="4" maxlength="3"></td></tr><tr><td colspan="2"><div align="center"><font color="#FF0000">注意：若您不能确定文件的宽和高，请保持默认宽高</font></div></td></tr><tr><td colspan="2"><div align="center"><input type="button" name="Submit22222" value="确定" onClick="realPub()"></div></td></tr></table>';	
}

function realPub() {
	var real = $("rm");
	if (real.value == "http://" || real.value == "") {
		alert("输入realplayer文件地址！");
		real.focus();
		return;
	} 
	if (checkWidthAndHeight($("kuan"), $("gao"))) {
		var text = "[rm="+$("kuan").value+","+$("gao").value+"]"+real.value+"[/rm]";
		closeUbbDialog();
		contentAdd(text);
	}
}

function selectBGMusic() {
	executeUbbDialog('getBGMusicHtml()');
}

function getBGMusicHtml() {
	return '<table width="100%" border="0"><tr> <td>文件地址：<input name="sound" type="text" id="sound" size="25"></td></tr><tr> <td><div align="center"><input type="button" name="Submit23" value="确定" onClick="BGMusicPub()"></div></td></tr></table>';	
}

function BGMusicPub() {
	var sound = $("sound");
	if (sound.value == "http://" || sound.value == "") {
		alert("输入背景音乐文件地址！");
		sound.focus();
		return;
	} 
	var text = "[sound]"+sound.value+"[/sound]";
	closeUbbDialog();
	contentAdd(text);
}

function selectShockwave() {
	executeUbbDialog('getShockwaveHtml()');
}

function getShockwaveHtml() {
	return '<table width="100%" border="0"><tr><td colspan="2">文件地址：<input name="qt" type="text" id="qt" value="http://" size="25" onMouseOver="this.select()"></td></tr><tr><td width="50%"><div align="right">宽度：<input name="kuan" type="text" id="kuan" value="500" size="4" maxlength="3"><br></div></td><td width="50%">高度：<input name="gao" type="text" id="gao" value="350" size="4" maxlength="3"></td></tr><tr><td colspan="2"><div align="center"><font color="#FF0000">注意：若您不能确定文件的宽和高，请保持默认宽高</font></div></td></tr><tr><td colspan="2"><div align="center"><input type="button" name="Submit2222222" value="确定" onClick="shockwavePub()"></div></td></tr></table>';	
}

function shockwavePub() {
	var qt = $("qt");
	if (qt.value == "http://" || qt.value == "") {
		alert("输入shockwave文件地址！");
		qt.focus();
		return;
	} 
	if (checkWidthAndHeight($("kuan"), $("gao"))) {
		var text = "[qt="+$("kuan").value+","+$("gao").value+"]"+qt.value+"[/qt]";
		closeUbbDialog();
		contentAdd(text);	
	}
}

function selectMedia() {
	executeUbbDialog('getMediaHtml()');
}

function getMediaHtml() {
	return '<table width="100%" border="0"><tr><td colspan="2">文件地址：<input name="mp" type="text" id="mp" value="http://" size="25" onMouseOver="this.select()"></td></tr><tr><td width="50%"><div align="right">宽度：<input name="kuan" type="text" id="kuan" value="500" size="4" maxlength="3"><br><font color="#FF0000"> </font></div></td><td width="50%">高度：<input name="gao" type="text" id="gao" value="350" size="4" maxlength="3"></td></tr><tr><td colspan="2"><div align="center"><font color="#FF0000">注意：若您不能确定文件的宽和高，请保持默认宽高</font></div></td></tr><tr> <td colspan="2"><div align="center"><input type="button" name="Submit222222" value="确定" onClick="mediaPub()"></div></td></tr></table>';	
}

function mediaPub() {
	var mp = $("mp");
	if (mp.value == "http://" || mp.value == "") {
		alert("输入media player文件地址！");
		mp.focus();
		return;
	} 
	checkWidthAndHeight($("kuan"), $("gao"));
	var text = "[mp="+$("kuan").value+","+$("gao").value+"]"+mp.value+"[/mp]";
	closeUbbDialog();
	contentAdd(text);
}

function outsideHtml(html) {
	var result = '<table width="300" height="160" border="0" cellpadding="0" cellspacing="0"><tr>';
	result += '<td height="25"><div align="center">你点中了UBB标签，请输入或';
	result += '<a href="javascript:void(0);" onclick="closeUbbDialog();">关闭</a></div></td></tr><tr>';
    result += '<td valign="top">';
	result += html;
	result += '</td></tr><tr><td height="25"><div align="center">';
	result += '[<a href="javascript:void(0);" onclick="closeUbbDialog();">关闭本窗口</a>]</div></td>';
  	result += '</tr></table>';
	return result;
}

function executeUbbDialog(aMethod) {
	$("ubbDialog").innerHTML = outsideHtml(eval(aMethod));
	ubbDialog = new newgxu.UbbDialog("ubbDialog");
}

function closeUbbDialog() {
	ubbDialog.close();
}

function resumeContent() {
	confirm('你相信奇迹吗？让雨无声和你一起祈祷吧！');
	if(kindEditor){
		kindEditor.html(Cookie.get("lastContent") || '上帝啊，我已经尽力了，但是我实在无法替你找回你丢失的东西...BTW:你是真的丢了吗？');
	}
	contentObj.value = Cookie.get("lastContent") || '上帝啊，我已经尽力了，但是我实在无法替你找回你丢失的东西...BTW:你是真的丢了吗？';
	
}

function getSubmitWaiting() {
	return '<img src="/images/waiting.gif" border="0" />正在发布，请稍等...';
}
function getSaveWaiting() {
	return '<img src="/images/waiting.gif" border="0" />正在保存，请稍等...';
}
function submitTopic() {
		if ($j('#synchronous_twitter').is(':checked')) {
			$j('input[name="synchronousTwitter"]').val(1);
			alert($j('input[name="synchronousTwitter"]').val(1) + " 111");
		}
		try{
			contentObj.value=kindEditor.html();
		}catch(e){alert(e);}
		//return false;
		if (titleObj.value == "") {
			alert("您忘了写文章标题\r\n如果你是想回复，恐怕点错了地方\r\n不妨再回去看看 ^_^");
			titleObj.focus();
			return;
		}
		else {
			var titleLength=titleObj.value.length;
			var patten=/【[\s\S]+?】*/;
			//如果标题中有预定标题前缀,则不算在字数长度中
						
			if(patten.exec(titleObj.value)!=null){
				titleLength=titleLength-4;
			}
			if (titleLength < 4) {
				alert("您输入的标题长度应该大于4个字   (不包含预留的前缀) ^_^");
				contentObj.focus();
				return;
			}
			else 
				if (contentObj.value == "") {
					alert("您忘了写文章正文  ^_^"+contentObj.value);
					contentObj.focus();
					return;
				}
				else 
					if (contentObj.value.length < 6) {
						alert("您输入的字数应该大于 5 个汉字或英文字符  ^_^");
						contentObj.focus();
						return;
					}
					else {
						Cookie.set("lastContent", contentObj.value);
						$("submitWaiting").innerHTML = getSubmitWaiting();
						new newgxu.HtmlDialog("submitWaiting", 260, 60);
						formObj.submit();
					}
		}
		//alert(KE.html('content'));
		//contentObj.value=KE.html('content');
		//formObj.submit();
}

/**
 * 预览帖子
 */
function previewTopic(){
	try{
		//alert($j("#preview_button").html()+"  sas  "+$j("#preview_button").val());
		if($j("#preview_button").val()=="预览"){
			$j("#content_edit").hide();
			$j("#preview_div").fadeIn(500);
			$j("#preview_button").val("返回编辑");
			$j("#preview_title").html(titleObj.value);
			//ajax开始
			$j("#preview_content").html('<div style=" text-align:center;">正在生成预览...</div>');
			$j.post("/webservice/ubb.yws", {input:contentObj.value}, function(data, textStatus) {
				if (textStatus == "success") {
					$j("#preview_content").html(data);
				} else {
					$j("#preview_content").html(textStatus + "\n页面加载失败");
				}
			});
		}
		else{
			$j("#preview_div").hide();
			$j("#content_edit").fadeIn(500);
			$j("#preview_button").val("预览");
		}
	}
	catch(e){alert(e);}
}

function submitReply() {
	
	if ($j('#synchronous_twitter').is(':checked')) {
		$j('input[name="synchronousTwitter"]').val(2);
	}
	
	
	
	try{
			contentObj.value=kindEditor.html();
	}catch(e){}
	if(contentObj.value == "") {
		alert("您忘了写文章正文  ^_^");
		contentObj.focus();
		return;
	}else if(contentObj.value.length<4){
		alert("您输入的字数应该大于3个汉字或者英文字符  ^_^");
		contentObj.focus();
		return;
	}	else {
		Cookie.set("lastContent", contentObj.value);
		$("submitWaiting").innerHTML = getSubmitWaiting();
		new newgxu.HtmlDialog("submitWaiting", 260, 90);
		formObj.submit();
	}
}


function saveDraftBox() {
	try{
		contentObj.value=kindEditor.html();
	}catch(e){}
	if(titleObj.value == "") {
		alert("您忘了写文章标题\r\n如果你是想回复，恐怕点错了地方\r\n不妨再回去看看 ^_^");
		titleObj.focus();
		return;
	} else if(contentObj.value == "") {
		alert("您忘了写文章正文");
		contentObj.focus();
		return;
	} else {
		document.getElementById("post_form").action="/savedraftbox.yws";
		formObj.submit();
	}
}
function saveAgainDraftBox() {
	try{
			contentObj.value=kindEditor.html();
		}catch(e){}
	if(titleObj.value == "") {
		alert("您忘了写文章标题\r\n如果你是想回复，恐怕点错了地方\r\n不妨再回去看看 ^_^");
		titleObj.focus();
		return;
	} else if(contentObj.value == "") {
		alert("您忘了写文章正文");
		contentObj.focus();
		return;
	} else {
		document.getElementById("post_form").action="/save_again_db.yws";
		formObj.submit();
	}
}



function getColor() {
	var color = showModalDialog("/js/selcolor.htm", "", "dialogWidth:18.5em; dialogHeight:15em; status:0; help:0");
	return color ;
}

function previewContent() {
	var width = 640 ;
	var height = 480;
	var left = eval(screen.Width - width) / 2;
	var top = eval(screen.Height  -height) / 2;
	window.open('/js/previewContent.jsp?content='+contentObj.value,'内容预览','menubar=yes,scrollbars=yes,width='+width+',height='+height+',left='+left+',top='+top+'');
}