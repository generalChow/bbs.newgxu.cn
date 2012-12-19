/**
* 阿福 2006-3-9最后修改
**/
//取左上点坐标的两个函数
function   getAbsLeft(e){
	var   l=e.offsetLeft; 
	while(e=e.offsetParent)   l   +=   e.offsetLeft;  
	return   l;
}
function   getAbsTop(e) {
	var   t=e.offsetTop;    
	while(e=e.offsetParent)   t   +=   e.offsetTop;   
	return   t;
}
function saveConfig() {
	var url = "saveDiy.jsp?";
	var musicPlayerLeft = getAbsLeft(document.getElementById('musicPlayer'));
	var musicPlayerTop  = getAbsTop(document.getElementById('musicPlayer'));
	url = url+'musicPlayerLeft='+musicPlayerLeft+'&musicPlayerTop='+musicPlayerTop;
	//
	var managerLeft = getAbsLeft(document.getElementById('manager'));
	var managerTop  = getAbsTop(document.getElementById('manager'));
	url = url+'&managerLeft='+managerLeft+'&managerTop='+managerTop;
	//
	var faceLeft = getAbsLeft(document.getElementById('face'));
	var faceTop  = getAbsTop(document.getElementById('face'));
	url = url+'&faceLeft='+faceLeft+'&faceTop='+faceTop;
	//
	var diaryLogoLeft = getAbsLeft(document.getElementById('diaryLogo'));
	var diaryLogoTop  = getAbsTop(document.getElementById('diaryLogo'));
	url = url+'&diaryLogoLeft='+diaryLogoLeft+'&diaryLogoTop='+diaryLogoTop;
	//
	var bookNameLeft = getAbsLeft(document.getElementById('bookName'));
	var bookNameTop  = getAbsTop(document.getElementById('bookName'));
	url = url+'&bookNameLeft='+bookNameLeft+'&bookNameTop='+bookNameTop;
	//
	var userNickLeft = getAbsLeft(document.getElementById('userNick'));
	var userNickTop  = getAbsTop(document.getElementById('userNick'));
	url = url+'&userNickLeft='+userNickLeft+'&userNickTop='+userNickTop;
	//
	var rijishouyuLeft = getAbsLeft(document.getElementById('rijishouyu'));
	var rijishouyuTop  = getAbsTop(document.getElementById('rijishouyu'));
	url = url+'&rijishouyuLeft='+rijishouyuLeft+'&rijishouyuTop='+rijishouyuTop;
	//window.location.href='saveDiy.jsp?musicPlayerLeft='+musicPlayerLeft+'&musicPlayerTop='+musicPlayerTop+'&managerLeft='+managerLeft+'&managerTop='+managerTop;
	window.location.href=url;
}