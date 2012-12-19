suspendcode="<DIV id=lovexin1 style='Z-INDEX: 10; LEFT: 0px; POSITION: absolute; TOP: 105px; width: 100; height: 300px;'><img src='/image/close.gif' onClick='javascript:window.hide()' width='100' height='14' border='0' vspace='3' alt='�رն�j���'><a href='http://www.makewing.com/lanren/' target='_blank'><img src='/image/ad_100x300.jpg' width='100' height='300' border='0'></a></DIV>"
document.write(suspendcode);
//flash��ʽ���÷���
//<EMBED src='flash.swf' quality=high  WIDTH=100 HEIGHT=300 TYPE='application/x-shockwave-flash' id=ad wmode=opaque></EMBED>

lastScrollY=0;
function heartBeat(){
diffY=document.all.body.scrollTop;
percent=.3*(diffY-lastScrollY);
if(percent>0)percent=Math.ceil(percent);
else percent=Math.floor(percent);
document.all.lovexin1.style.pixelTop+=percent;
lastScrollY=lastScrollY+percent;
}
function hide()  
{   
lovexin1.style.visibility="hidden"; 
}
window.setInterval("heartBeat()",1);
