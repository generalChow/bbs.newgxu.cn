//Êä³öFlash
function outputFlash(src,width,height,transparent,idName,listSRC){
	document.writeln("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\""+width+"\" height=\""+height+"\" name=\""+idName+"\" id=\""+idName+"\">");
	document.writeln("<param name=\"movie\" value=\""+src+"?listSRC="+listSRC+"\" />");
	document.writeln("<param name=\"quality\" value=\"high\" />")
	if(transparent==true) document.writeln("<param name=\"wmode\" value=\"transparent\" />")
	document.writeln("<embed src=\""+src+"?listSRC="+listSRC+"\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\""+width+"\" height=\""+height+"\" bgcolor=\"transparent\"  wmode=\"transparent\" allowScriptAccess=\"sameDomain\" swLiveConnect=\"true\" name=\""+idName+"\"></embed>");
	document.writeln("</object>");
}
//outputFlash("mp3player/musicPlayer.swf","200","150",true,"mp3player","mp3player/List.xml")