var xmlHttp;                        //用于保存XMLHttpRequest对象的全局变量
var uid;                       //用于保存姓名
var content;                        //用于保存内容
var did;                       //用于保存主题编号
//用于创建XMLHttpRequest对象
function createXmlHttp() {
    //根据window.XMLHttpRequest对象是否存在使用不同的创建方式
    if (window.XMLHttpRequest) {
       xmlHttp = new XMLHttpRequest();                  //FireFox、Opera等浏览器支持的创建方式
    } else {
       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//IE浏览器支持的创建方式
    }
}
function submitPost() {
    //获取帖子中姓名、编号、内容三部分信息
    content = document.getElementById("content").value;
    did = document.getElementById("did").value;
    uid = document.getElementById("uid").value;
    //alert(content+did+uid);
    if (checkForm()) {
        displayStatus("正在提交……");                      //显示提示信息
        createXmlHttp();                                    //创建XMLHttpRequest对象
        xmlHttp.onreadystatechange = submitPostCallBack;    //设置回调函数
        xmlHttp.open("POST", "add_Comment.yws", true);         //发送POST请求
        //设置POST请求体类型
        xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlHttp.send("content=" + encodeURI(content) + 
                     "&did=" + encodeURI(did) + 
                     "&uid=" + encodeURI(uid));              //发送包含三个参数的请求体
    }
}

function checkForm() {
    if (content == "") {
        alert("请填写内容");
        return false;
    }
    return true;
}

function displayStatus(info) {
    var statusDiv = document.getElementById("statusDiv");
    statusDiv.innerHTML = info;
    statusDiv.style.display = "";
}

//获取查询选项的回调函数
function submitPostCallBack() {
    if (xmlHttp.readyState == 4) {
        createNewPost(xmlHttp.responseText);
        hiddenStatus();
    }
}

function createNewPost(postId) {
    //清空当前表单中各部分信息
    document.getElementById("content").value = "";
    var now = new Date();
    var strTime = now.toLocaleString();
    var strYear = now.getYear();
    var strMonth = now.getMonth();
    var strDay = now.getDay();
    var hour=now.getHours();
    var minute=now.getMinutes();
    var second=now.getSeconds();
    var displayTime = strYear +"-"+strMonth+"-"+strDay+" "+hour+":"+minute+":"+second;
    var tbody = document.getElementById("diary");
    var newTR = document.createElement("tr");
    var newTR1 = document.createElement("tr");
    var newTR2 = document.createElement("tr");
    
    newTR.id = "diary"+postId;
    current_cell = document.createElement("td");
    current_cell.width = "53%";
    current_cell.height="20";
    current_cell.className = "bg";
    current_cell1 = document.createElement("td");
    current_cell1.width = "25%";
    current_cell1.align="right";
    current_cell1.className = "bg";
    current_cell2 = document.createElement("td");
    current_cell2.width = "22%";
    current_cell2.align="right";
    current_cell2.className="bg";
    current_cell3 = document.createElement("td");
    current_cell3.colSpan=3;
    current_cell3.valign = "top";
    hr=document.createElement("hr");
    hr.size="1";
    current_cell3.appendChild(hr);
    newTR2.appendChild(current_cell3);
    tbody.appendChild(newTR2);
    var span = document.createElement("SPAN");
    span.className="STYLE1";
    spantext = document.createTextNode("网友");
    span.appendChild(spantext);
    var span1 = document.createElement("SPAN");
    span1.className="STYLE1";
    span1text = document.createTextNode("IP");
    span1.appendChild(span1text);
    currenttext = document.createTextNode("："+uid);
    currenttext1 = document.createTextNode("：127.0.0.1");
    currenttext2 = document.createTextNode(displayTime);
    current_cell.appendChild(span);
    current_cell.appendChild(currenttext);
    newTR.appendChild(current_cell);
    current_cell1.appendChild(span1);
    current_cell1.appendChild(currenttext1);
    newTR.appendChild(current_cell1);
    current_cell2.appendChild(currenttext2);
    newTR.appendChild(current_cell2);
    tbody.appendChild(newTR);
    current_cell3 = document.createElement("td");
    current_cell3.height="25";
    current_cell3.colSpan=3
    current_cell3.valign = "bottom";
    currenttext3 = document.createTextNode(content);
    current_cell3.appendChild(currenttext3);
    newTR1.appendChild(current_cell3);
    tbody.appendChild(newTR1);
}

function hiddenStatus() {
    var statusDiv = document.getElementById("statusDiv");
    statusDiv.innerHTML = "";
    statusDiv.style.display = "none";
}

function reset(){
    document.getElementById("content").value = "";
}

function addDiaryPost(){
   var title = document.getElementById("title").value;
   var weather = document.getElementById("weather").value;
   var humor = document.getElementById("humor").value;
   var content = document.getElementById("content").value;
   var lockedType = checkRadio("lockedType");
   var allowcomment = document.getElementById("allowcomment").value;
   if(document.getElementById("allowcomment").checked == false){
      allowcomment = 0;
   }
   if(sumbit()){
      displayStatus("正在提交信息……");                      
        createXmlHttp();                                    
        xmlHttp.onreadystatechange = addDiaryPostCallBack;    
        xmlHttp.open("POST", "add_Diary_do.yws", true);         
        //设置POST请求体类型
        xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlHttp.send("title="+encodeURI(title)+
                     "&weather="+encodeURI(weather)+
                     "&humor="+encodeURI(humor)+
                     "&content=" + encodeURI(content) + 
                     "&lockedType=" + encodeURI(lockedType) + 
                     "&allowcomment=" + encodeURI(allowcomment));             
   
   }
}

function checkRadio(Obj)
   {
    var o=document.getElementsByName(Obj);
    var len=o.length;
    var value="";   
    for (var i=0;i<len ;i++ )
    { if( o[i].checked==true )
     {
      //alert(o[i].value);
      value=o[i].value;
     }
    }
    return value;
   }

function sumbit(){
   if(document.getElementById("title").value.length==0){
       alert("你还没有填写标题哦");
       return false;
   }
   if(document.getElementById("content").value.length==0){
       alert("你还没有填写内容哦");
       return false;
   }
   return true;
}

function addDiaryPostCallBack() {
   if (xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200){
        hiddenStatus();
        window.setInterval(change,10);
        //hiddenDiv("tishi");
        }
    }
}

var i=0;
var n=0;
var t= 150;
function change(){
	 var o = document.getElementById("tishi");
	 o.className="wh";
	 o.innerHTML = "签写日记成功！";
     o.style.filter = "Alpha(Opacity=" + i + ")"; //for IE	
	 o.style.opacity = i/100; //for FF
	 if(i<t && n==0) 
	 i++;
	 else n=1;
}
function hiddenDiv(info){
    var div = document.getElementById(info);
    div.className="";
    div.innerHTML = "";
    div.style.display = "none";
} 