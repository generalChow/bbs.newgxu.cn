/**
 * 西大Image ,基于 jquery的图像效果的插件 
 *
 	xdImage_cloth 这个是像画布一样可以滚动的图片效果，一般是给定了一个定宽定高的父类窗口(就是当前操作的对象）--parentDiv，还有一个sonDiv
 			---具有很大的width（左右移动） 或者height 值（上下移动），程序会自动设定sonDiv的宽与高值。
 			同时，你可以传递两个用于控制图片显示序号的按钮----lastButton和nextButton ，传的是id值啊。
 			设定 auto 决定是否自动播放。time 指定了 移动的时间间隔。
 			direction 指定了移动的方向。
 			
 			count 指定了一个窗口有多少个图片显示
 			
 * @author 集成显卡
 * @date	2011.5.26
 */
(function($){
	/**
	 * 这个是相对平滑的图片布的滚动，这个是前面 xdImage_cloth的升级版
	 * 可以用于多个图片连贯滑动，图片可以一直沿一个方向滑动，当图片到尽头时，前面的图片会回流到最后，
	 * 这样就让人感觉是一直在滑动，没有回头   嘻嘻
	 * 注意，参数都是以id传递
	 * 
	 * @param {Object} options
	 */
	$.fn.xdImage_smoothCloth=function(options){
		var DEFAULT={
			sonDiv:"",//这个是必须有的参数，指定了历遍的父容器
			kind:"img",//指定对象children的历遍类型，默认是img，可以指定为其他，比如 div
			autoDir:true,//这个怎么理解呢，那就是，如果是false的话
								//因为图片是自动播放的，方向是不改变的，
								//如果为 true，那么当你自己点击时，方向就是你点击时的方向
			auto:true,//是否开启自动播放
			timeout:2000,//动画过渡时间
			time:3000,//自动播放间隔
			direction:"left",//自动播放时默认的动画方向
			lastButton:"",//上一张的控制button
			nextButton:"",//下一张控制button
			count:1//指定了一个窗口显示多少个单元
		}
		
		/*
		 * 系统内置变量
		 * @memberOf {TypeName} 
		 * @return {TypeName} 
		 */
		var SYSTEM={
			max_count:0,index:0,width:'0px',height:'0px',timer:null,direction:1
		}
		var options=$.extend(DEFAULT,options);
		var parent=null;
		var son=null;
		
		$(this).each(function(){
			if(options.sonDiv==''){
				alert("没有指定sonDIv属性，请声明");
				return fasle;
			}
			parent=$(this);
			son=parent.children("#"+options.sonDiv);
			init();
		});
		
		/*
		 * 初始化，包括最大图片数，还有定义事件处理等
		 */
		function init(){
			parent.css("overflow","hidden");
			//获取图片个数
			$("#"+options.sonDiv+" "+options.kind).each(function(){
				SYSTEM.max_count++;
			});
			if(SYSTEM.max_count>0)
				SYSTEM.index=1;
			//判断方向设置，不同的方向，sondiv的样式不一样
			if(options.direction=="left"){
				$("#"+options.sonDiv).css("width",(SYSTEM.max_count*2*parent.width())+"px");
				$("#"+options.sonDiv).css("height",parent.css("height"));
				SYSTEM.width=Math.round(parent.width()/options.count)+"px";
				SYSTEM.height=parent.height()+"px";
			}
			if(options.direction=="top"){
				$("#"+options.sonDiv).css("height",(SYSTEM.max_count*2*parent.height())+"px");
				$("#"+options.sonDiv).css("width",parent.css("width"));
				SYSTEM.width=parent.width()+"px";
				SYSTEM.height=Math.round(parent.height()/options.count)+"px";
			}
			
			//统一子元素的样式，让他们是一样的样式
			$("#"+options.sonDiv).children(options.kind).each(function(){
				$(this).css("width",SYSTEM.width).css("height",SYSTEM.height).css("margin","0").css("float","left").css("display","block");
			});
			/*
			 * 添加两个控制按钮的事件
			 * 当用户点击时，我们需要重置自动播放的计时器。
			 * @return {TypeName} 
			 */
			$("#"+options.lastButton).bind("click",function(){
					changeImage(-1);
					if(options.autoDir)
						SYSTEM.direction=-1; 
					initTimer();
					return false;
				}).css("cursor","pointer");
			$("#"+options.nextButton).bind("click",function(){
					changeImage(1);
					if(options.autoDir)
						SYSTEM.direction=1;
					initTimer();
					return false;
				}).css("cursor","pointer");
			if(options.auto)
				initTimer();
		}
		
		/*
		 * 变换图片，说一下原理
		 * 如果左转，为了看到动画的效果，我们先产生一个动画让整个层左移一定的距离，
		 * 动画结束后，将最左的图片从图片列表中抽出，插到最右边，ok
		 * 同理，右转就是反过来
		 * 
		 * @param {Object} index
		 */
		function changeImage(index){
			//如果已经存在前面的一个动画效果，直接返回
			if(son.is(":animated"))
				return false;
			//animate 中的 margin-left 之类的参数不接受表达式，只能分开一个if实现，，=.= 
			if(options.direction=='left'){
				if(index<0){
					$temp=son.children(":last");
					son.prepend($temp);
					son.css("margin-left",(-1)*parseInt(SYSTEM.width)+"px");
				}
				son.stop().animate({"margin-left":(-1*index)*parseInt(index>0?SYSTEM.width:0)+"px"},options.timeout,function(){
					if(index>0){
						$temp=son.children(":first");
						son.append($temp);
						son.css("margin-left","0px");
					}
					return false;
				});
			}
			else if(options.direction=='top'){
				if(index<0){
					$temp=son.children(":last");
					son.prepend($temp);
					son.css("margin-top",(-1)*parseInt(SYSTEM.height)+"px");
				}
				son.stop().animate({"margin-top":(-1*index)*parseInt(index>0?SYSTEM.height:0)+"px"},options.timeout,function(){
					if(index>0){
						$temp=son.children(":first");
						son.append($temp);
						son.css("margin-top","0px");
					}
					return false;
				});
			}
		}
		
		function initTimer(){
			clearInterval(SYSTEM.timer);
			SYSTEM.timer=setInterval(function(){
				changeImage(SYSTEM.direction);return false;
			},options.time+options.timeout);
		}
	}
})(jQuery);
