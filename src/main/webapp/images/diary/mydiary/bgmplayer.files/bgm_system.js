//////////////////////////////////////////////////////////////////////////////////
//Shinobu BGM Player 1.0a (Full Function Version)				//
//Copyleft (c) 2000 - 2001 Shinobu (shinobu@empal.com),All Rights Unreversed.	//
//Shinobu's Script Support Page : http://javascript.new21.org			//
//////////////////////////////////////////////////////////////////////////////////
//中文化: Cora		                                                        //
//E-Mail: wait4mayday@hotmail.com                                               //
//////////////////////////////////////////////////////////////////////////////////
//程式修改: Sharon                                                              //
//E-Mail:   forsharon@hotmail.com                                               //
//Homepage: http://sharon.has.it                                                //
//請勿刪除以上版權宣告!                                                         //
//----------------------->請記得將此程式連同上傳!<-----------------------       //
//////////////////////////////////////////////////////////////////////////////////

// (不必修改)��
var songtime1 = null;
var doFirstPlay = null;
var songPlaying = false;
var track = 0;
var songNum = null;
var songName = null;
var songTime = null;
var showTitle = null;
var count = 0;
var loop = false;
var playMode = null;
var restLength = null;

function init_bgm() { 
	
	if (showTitle == null) { showTitle = 1; }
	switch (showTitle) {
		case 0 :
			document.form1.stitle.style.visibility = "hidden";
			document.form1.stitle.size = 1;
			break;
		case 1 :
			document.form1.stitle.style.visibility = "visible";
			break;
		case 2 :
			document.form1.stitle.style.visibility = "hidden";
			document.form1.stitle.size = 1;
			break;
		default :
			showTitle = 1;
	}
	if (playMode == null) { playMode = 0; }
	if (restLength == null) { restLength = 5; }
	if (doFirstPlay == null) { doFirstPlay = 1; }
	if (doFirstPlay == 1) { play(); }
}

function addbgm( url, title, time ) { //bgm 蹺陛
	if (songNum == null) { songNum = new Array(); count = 0;} else { count = songNum.length; }
	if (songName == null) { songName = new Array(); }
	if (songTime == null) { songTime = new Array(); }
	songNum[count] = url;
	if (title == null || title == '' ) { title1 = 'Track ' + (count + 1); } else { title1 = title; }
	songName[count] = title1;
	songTime[count] = time;
}

function chgLoop() { 
	if (loop == false) {loop = true;}
	else {loop = false;}
}


function play() {
	if (songPlaying) { 
	alert("秞氈淏婓畦溫笢!");
	return;
	}
	
	switch (playMode) {
		case 0 :
			track = 0;
			break;
		case 1 : 
			track = Math.floor(Math.random() * songNum.length);
			break;
		default : 
			track = 0;
	}
chooseSong(track);
}

function stopTrack() { 
	document.Music.Stop() 
	if (songPlaying) { 
	clearTimeout(songtime1); 
	}
	else { alert("眒冪岆婃礿袨怓!"); return false;}
	songPlaying = false; 
	updateTrack(); 
}

function checkloop( action ) { 
	if ( loop == true ) { chooseSong(track); }
	else {
		switch (action) {
			case "next" : 
				nextTrack();
				break;
			case "pre" : 
				preTrack();
				break;
			default :  
				alert("這不是我們執行的命令範圍內!!\n\n請檢查主要程式是否有錯誤後再試一次!");
				stopTrack();
		}
	}
}

function chooseSong(aaa) { // お楷戲煎 霜蕾 勒傘嗨晦
	if (songPlaying) { //營儅醞檜塭賊..
	clearTimeout(songtime1); //顫歜嬴醒擊 п薯и棻.
	}
	track = aaa; 
	//餌辨濠陛 詣賅腦堅 お楷 廓�ㄧ� 殮溘ж賊...
	if(track > songNum.length - 1 || track < 0) { alert("這首歌曲的編號是不存在的!"); return false;} 
		var nowtrack1 = songNum[track];
		document.Music.Open(nowtrack1); 
		songPlaying = true; 
		updateTrack(); 
		var t_time1 = songTime[track] + restLength;
		var t_time = t_time1 * 1000;
		songtime1 = setTimeout("checkloop('next')",t_time); 
}

function nextTrack() { 
	if (playMode == 1) { var num = Math.floor(Math.random() * songNum.length); } 
	else {
		if(track == songNum.length - 1) { var num = 0; } 
		else { var num = track + 1; }
	}
	chooseSong(num);
}

function preTrack() { 
	if (playMode == 1) { var num = Math.floor(Math.random() * songNum.length); } 
	else {
		if(track == 0) { var num = songNum.length - 1; } 
		else { var num = track - 1; }
	}
	chooseSong(num);
}

function updateTrack() { 
	if (songPlaying) { 
	track_idx = track + 1; 
	switch (showTitle) {
		case 0 :
			window.status = ('沒有使用顯示歌曲名稱的功能!'); return true;
			break;
		case 1 :
			document.form1.stitle.value = "" + track_idx + ". " + songName[track];
			break;
		case 2 :
			window.status = ('' + track_idx + '. ' + songName[track]); return true;
			break;
		default :
			window.status = ('沒有使用顯示歌曲名稱的功能!'); return true;
		}
	}
	else { 
		switch (showTitle) {
		case 0 :
			window.status = ('BGM Player [stopped]'); return true;
			break;
		case 1 :
			document.form1.stitle.value = "BGM Player [stopped]";
			break;
		case 2 :
			window.status = ('BGM Player [stopped]'); return true;
			break;
		default :
			window.status = ('BGM Player [stopped]'); return true;
		}
	}
}

function m_list() { //bgm_sele.html的高度與寬度設定
	window.open('bgm_sele.htm','LinksRemote','width=250,height=330,scrollbars=1,resizable=1');
}