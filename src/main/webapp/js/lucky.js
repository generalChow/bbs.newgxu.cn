/**
 * 2011.10.3
 */
var LUCKY=(function(){
	var i=0; //
	var sld=0;//已经选择了的题目数
	var len=0;//总题目数
	var prog=100;  //$j("#prog").css("width");
	var ht=420;
	var speed=500;//动画速度
	var cssPath="/css/lucky.css";
	var LuckDiv="#lucky_div";
	var answer="";
	var luckyId=0;
	var timeout=10;
	var timer=null;
	
	var warn=function (txt){
	$j("#war").html(txt).fadeIn(500,function(){$j("#war").html("").fadeOut(500)});	
	}
	var checkbtn=function (){
		if(i<=0&&sld<len-1){
			$j("#prev").addClass("noprev");
			}else{
				$j("#prev").removeClass("noprev");
				}
		if(sld<=i&&sld<len){
			$j("#next").addClass("nonext");
			}else{
				$j("#next").removeClass("nonext");
				}	
	}
	var setporogress=function(j){			
		i+=j;
		i=(i<0)? 0:i;
		checkbtn();
		if(!$j("#info").is(":animated")){			
			$j("#tips span").html((i+1>len?len:i+1)+"\/"+len+"题");	
			var wh=$j("#tips").get(0).offsetWidth;		
			var ress=Math.round((i+1)*prog/len);		
			$j("#ress").css({"width": ress+"px"});		
			$j("#tips").css({"left":ress-Math.round(wh/2)+"px"});
			$j("#issue").animate({"top": -i*ht + "px"},500);
			}
		}
	
	var selec=function(ele,type){			
		if(type) sld++;
		//alert(sld);
		ele= $j(ele)? $j(ele):ele;
		ele.parents("div.cnt").addClass("selected");
		ele.parents("div.cnt").find("li.sel").removeClass("sel");
		ele.addClass("sel");
		//alert(ele.find("input[type=radio]").val());
	}
	
	/**
	 * 获取用户选择的信息列表
	 */
	var getSelectInfo=function(){
		answer="";
		$j("#issue").find("li.sel").each(function(){
			answer+=$j(this).attr("sid")+"-"+$j(this).attr("value")+"-";
		});
		return answer.substring(0,answer.length-1);
	}
	
	var toSubmit=function(){
		if(timeout<=0){
			alert("此次作答由于时间过期已经失效了！");
			return false;
		}
		if(sld<len){
				alert("您还有题目没完成！");
				return false;
		}
		var temp=getSelectInfo();
		$j("#lucky_answer").val(temp);
		$j("#lucky_form").submit();
		/*
		 * alert(temp);
		$j.ajax({
          type: "post",url:"submit_lucky.yws?lid="+luckyId+"&answer="+temp,dataType: 'text/html',
          success: function(data){
			$j(LuckDiv).html(data);
          }
     	 });*/
	}
	var countTime=function(){
		if(timeout>0){
				$j("#lucky_time").html(timeout+"&nbsp;秒后此次作答失效");
				timeout--;
				//setTimeout(countTime,1000);
		}else{
			$j("#lucky_time").html("&nbsp;此次作答已经失效");
			clearInterval(timer);
		}
	}
	var runTime=function(){
		//setTimeout(countTime,1000);
		clearInterval(timer);
		timer=setInterval(countTime,1000);
	}
	
	return{
		//加载内容
		loadLucky:function(id){
			luckyId=id;
			$j(LuckDiv).html("加载中....");
			$j.ajax({
	          type: "get",url:"view_lucky.yws?lid="+id+"&ran="+Math.random(),dataType: 'text/html',
	          success: function(data){
				$j(LuckDiv).html(data);
	          }
	     	 });
		},
		start:function(time){
			timeout=time;
			runTime();
			len=$j("#issue").find("div.cnt").length;//读取问题的长度
			$j("#prev").click(function(){
			checkbtn();				
			if($j(this).hasClass("noprev")) return false;
			if(i>0) {
				setporogress(-1);
				}else if(i==0){
				setporogress(len-1);
				}
			});	
			$j("#next").click(function(){
				checkbtn();
				if($j(this).hasClass("nonext")) return false;
				if(sld>i&&i<len-1) {
					setporogress(1);
					}else if(i==len-1){
					setporogress(0-len);
					}
					
				});
			$j("#issue").find("li").click(function(){								  
					if(!$j(this).parents("div.cnt").hasClass("selected")){							
						selec(this,true);				
						}else{
						selec(this,false);	
						}
					//if(sld>=len) return;
					if(sld>i&&i<len-1) {
					setporogress(1);
					}else if(i==len-1){
					setporogress(0-len);
					}
					return false;	
				})
			.hover(
				function(){$j(this).addClass("hover")},
				function(){$j(this).removeClass("hover")}
				);
				setporogress(i);
		},
		clearAll:function(){
			$j("#issue li.sel").removeClass("sel");
			$j("div.cnt").removeClass("selected");
			i=0;
			sld=0;
			setporogress(i);
		},
		submit:function(){
			toSubmit();
		},
		loadCSS:function(){
			var $link=$j("<link type='text/css' href='"+cssPath+"' rel='stylesheet'/>");
			$j("head").append($link);
		},
		bindClik:function(){
				//注册事件
			$j(".btn_lucky1").hover(function(){
				$j(this).addClass("btn_lucky2").css("color","#fff");
			},function(){
				$j(this).removeClass("btn_lucky2").css("color","#999999");
			});
			$j("#btn_lucky").click(function(){
				var id=$j("#btn_lucky").attr("lid");
				LUCKY.loadLucky(id);
			});
		}
	}
})();
$j(document).ready(function(){
		//LUCKY.loadCSS();
		LUCKY.bindClik();
});