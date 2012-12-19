/**
 * 论坛节日系统的脚本
 * 需要Juqery的支持【注意：论坛已经重载了jquery的$函数，这里使用的是$j，如果其他地方需要的话，要将$j还原】
 * 
 * 
 * @author 集成显卡   2011.9.11
 */
var HOLIDAY=(function(){
	var setting={
		parent:"#body_table",//添加图片效果的父容器，这里可以直接使用 body_table。
		width:1004,//宽度，论坛宽度默认是1004
		path:"",//图片路径
		type:0,//效果，0：就是开门式，1：淡入淡出式
		timeout:3000, //图片显示多长时间
		movetime:1500 //动画执行时间
	}
	var sonDIv=null;//内容
	var imgHeight=0;
	
	var init=function(){
		if(setting.parent==""){
			alert("ERROR:父容器没有设置");
			return false;
		}
		sonDIv=$j("<div/>").attr("id","holiday_div").css("width","100%").css("position","absolute").css("top","0px").css("z-index",9999).css({overflow: "hidden"});
		if(setting.type==0){
			bulidDoor();
		}else if(setting.type==1){
			setting.parent="body";
			bulidFade();
		}else if(setting.type==2){
			//setting.parent="body";
			buildSide();
		}
		//$j(setting.parent).prepend(sonDIv);
		$j(setting.parent).prepend(sonDIv);
		if(setting.type==2){
			$j("#holiday_side_Div").slideDown(setting.movetime,function(){sideDo();});
		}
		$j("body").mask();
	}
	
	/**
	 * 添加开门式的效果，使用两个div作左右两个门，再运行移动的动画
	 * 两个门的宽度刚好是parent的一半
	 */
	var bulidDoor=function(){
		var width=setting.width/2+"px";
		var lt=$j("<div/>").css("float","left").css("width",width).css("overflow","hidden").attr("id","left_door").append($j("<img/>").attr("src",setting.path));
		var rt=$j("<div/>").css("float","right").css("width",width).css("overflow","hidden").attr("id","right_door").append($j("<img/>").attr("src",setting.path).css("margin-left","-"+width));
		sonDIv.css("width","1004px").append(lt).append(rt).append(addClear());
		
		//开始事件
		setTimeout(function(){
			$j("#left_door").animate({"margin-left":"-"+width},setting.movetime);
			$j("#right_door").animate({"margin-right":"-"+width},setting.movetime,function(){
				clearHolidayEffect();
			});
		},setting.timeout);
	}
	/**
	 * 淡出淡入效果
	 */
	var bulidFade=function(){
		var newDiv=$j("<div/>").attr("id","holiday_fade_Div").css({width:"100%",height:imgHeight+"px",background:"url("+setting.path+")  top center no-repeat"});//append($j("<img/>").attr("src",setting.path));
		sonDIv.append(newDiv);
		setTimeout(function(){
			$j("#holiday_fade_Div").fadeOut(setting.movetime,function(){
				clearHolidayEffect();
			});
		},setting.timeout);
	}
	/**
	 * 下移上移图片效果
	 */
	var buildSide=function(){
		var newDiv=$j("<div/>").attr("id","holiday_side_Div").css({display:"none",width:"1004px",height:imgHeight+"px",background:"url("+setting.path+")  top center no-repeat"});
		sonDIv=$j("<div/>").attr("id","holiday_div").css("position","relative").css("z-index",9999).css({width:"1004px",overflow:"hidden"});
		sonDIv.append(newDiv);
	}
	var sideDo=function(){
		setTimeout(function(){
			$j("#holiday_side_Div").slideUp(setting.movetime,function(){
				clearHolidayEffect();
			});
		},setting.timeout);
	}	
	var clearHolidayEffect=function(){
		$j("body").unmask();
		$j("#holiday_div").remove();
	}
	var addClear=function(){
		return $j("<div/>").css("clear","both");
	}
	
	/**
	 * 幕后加载图片，当大图加载完成后，再初始化
	 */
	var buildCache=function(){
		var $image=$j("<img onload='javascript:HOLIDAY.init();'/>").attr("id","holiday_temp").attr("src",setting.path).css("display","none");
		$j(setting.parent).append($image);
	}
	
	return{
		start:function(options){
			setting=$j.extend(setting,options);
			buildCache();
			//init();
		},
		init:function(){
			var image=new Image();
			image.src=$j("#holiday_temp").attr("src");
			imgHeight=image.height;
			$j("#holiday_temp").remove();
			init();
		}
	}
})();
