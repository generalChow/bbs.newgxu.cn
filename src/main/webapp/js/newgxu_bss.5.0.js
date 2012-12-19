/**
 * @author 集成显卡 @2011-10-26
 * 需要 jquery.loadmask.min.js
 */
var BBS=(function(){
	var Mask={
		 base:"",son:"",width:0,height:0
	}
	return{
		mask:function(parent,id){
			Mask.base=parent;
			Mask.son=id;
			$j(Mask.base).mask();
			//显示选择框
			var ddd=$j(Mask.son);
			ddd.show();
		},
		unMask:function(){
			$j(Mask.son).fadeOut(1000,function(){
				$j(Mask.base).unmask();
			});
		},
		/**
		 * 自动调整图片比例，适应父类容器
		 */
		fillImg:function(ImgD,FitWidth,FitHeight,type){
			//实例Image对象，获取他的宽，高值
		  var image=new Image();
		  image.src=ImgD.src;
		  if(image.width>0 && image.height>0){
		      if(image.width/image.height>= FitWidth/FitHeight){
		          if(image.width>FitWidth){ ImgD.width=FitWidth;ImgD.height=(image.height*FitWidth)/image.width;
		          }else{ImgD.width=image.width; ImgD.height=image.height; }
		      } else{
		          if(image.height>FitHeight){ImgD.height=FitHeight;ImgD.width=(image.width*FitHeight)/image.height;
		          }else{ ImgD.width=image.width; ImgD.height=image.height;} 
		     }
		      if(type){//$j(ImgD).css("margin-left",(FitWidth-ImgD.width)/2+"px");
		    	   var t=0;
		    	  if($j.browser.msie){
		    		   t=FitHeight>image.height?(FitHeight-image.height)/2:0;
		    	  }else{
		    		   t=FitHeight>ImgD.height?(FitHeight-ImgD.height)/2:0;
		    	  }
		    	 
		     	 $j(ImgD).css("margin-top",t+"px");
		      }
		  }
		 $j(ImgD).show();
		}
	}
})();
/**用户服务类*/
var SERVICE=(function(){
	/**配置*/
	var options={
		width:500,height:400,//窗口大小
		uploadUrl:'',//资源上传路径
		loadUrl:'/user/myupload!index.yws',//上传页面路径
		sourceUrl:'', //资源URL，加用户id
		loaded:false,//是否加载了上传页面
		viewed:false,//是否加载了管理页面
		div:'#user_source' //弹出窗口的div
	}
	var Class={
		tabOn:'current',tabOff:'normal'
	}
	var page=1;//当前查看资源的页数
	var maxPage=0;
	var pageDiv="#my_resource_div";
	var userId=-1;
	var sessionId="";
	
	var bindClose=function(){
		$j(options.div).find(".CLOSE").click(function(){
			$j(options.div).hide();
			BBS.unMask();
		});
	}
	var bindTab=function(){
		$j(options.div).find(".TAB").click(function(){
			$j(options.div).find(".TAB").removeClass(Class.tabOn).addClass(Class.tabOff);
			$j(this).removeClass(Class.tabOff).addClass(Class.tabOn);
			var id=$j(this).attr("id");
			$j(".content_td>div").hide();
			$j("#"+id+"_div").show();
		});
	}
	var loadUpload=function(type){
		if(page==0||(maxPage>0&&page>maxPage))
			return false;
		$j(pageDiv).html("<span class='LOADING'><br />loading...</span>");
		$j.ajax({
	          type: "get",url:options.sourceUrl+"&page="+page+(type?"&ran="+Math.random():""),dataType: 'text/html',
	          success: function(data){
				$j(pageDiv).html(data);
	          }
	      });
	}
	var showManage=function(){
		try{
			BBS.mask("body",options.div);
			if(!options.loaded){
				bindClose();
				bindTab();
					var settings = {
						flash_url : "/js/swfupload/swfupload.swf",
						upload_url: "/upload_servlet;jsessionid="+sessionId,
						post_params: {"":""},
						file_size_limit : "10240",
						file_types : "*.jpg;*.gif;*.png;*.bmp;*.rar;*.mp3;*.zip",
						file_types_description : "请选择文件",
						file_upload_limit : 10,
						file_queue_limit : 20,
						assumeSuccessTimeout:5000,
						custom_settings : {
							progressTarget : "fsUploadProgress",
							cancelButtonId : "btnCancel"
						},
						debug: false,
						button_image_url: "/css/swfupload/TestImageNoText_65x29.png",
						button_width: "65",
						button_height: "29",
						button_placeholder_id: "spanButtonPlaceHolder",
						button_text: '<span>选择文件</span>',
						button_text_style: ".theFont { font-size: 16; }",
						button_text_left_padding: 6,
						button_text_top_padding: 3,
						
						file_queued_handler : fileQueued,
						file_queue_error_handler : fileQueueError,
						file_dialog_complete_handler : fileDialogComplete,
						upload_start_handler : uploadStart,
						upload_progress_handler : uploadProgress,
						upload_error_handler : uploadError,
						upload_success_handler : uploadSuccess,
						upload_complete_handler : uploadComplete,
						queue_complete_handler : queueComplete
					};
					swfu = new SWFUpload(settings);
					//alert("初始化完成!!\n现在只是测试，如有问题请联系雨无声！");
					loadUpload(false);
					options.loaded=true;
		}
		}catch(e){alert(e);}
	}

	return {
		init:function(id,sid){
			try{
				userId=id;
				sessionId=sid;
				options.sourceUrl='/user/myupload.yws?size=12&id='+id;
				$j("#my_resource_link").click(showManage);
			}catch(e){alert("资源管理器初始化失败，请刷新！"+e);}
		},
		uploadSuccess:function(serverData){
			var d=eval("(" + serverData + ")");
			if(kindEditor){
				var src=d.url.toLowerCase();
				if(src.indexOf("jpg")>-1||src.indexOf("gif")>-1||src.indexOf("png")>-1||src.indexOf("bmp")>-1||src.indexOf("jpg")>-1){
					kindEditor.insertHtml('<img src="'+d.url+'" border="0" alt="" />').focus();
				}else if(src.indexOf("mp3")>-1){
					kindEditor.insertHtml(d.url).focus();
				}else{
					var uri=d.url.split("|")[0];
					var name=d.url.split("|")[1];
					kindEditor.insertHtml("<img src='images/zip_icon.gif'/>[url="+uri+"]点击下载:"+name+"[/url]").focus();
				}
			}
				//kindEditor.insertHtml('<img src="'+d.url+'" border="0" alt="" /><br />');
			else alert('编辑器无效，请刷新页面！');
		},
		nextPage:function(){
			page++;
			if(page>maxPage)
				page=maxPage;
			else
				loadUpload(false);
		},
		prePage:function(){
			page--;
			if(page<=0)
				page=1;
			else
				loadUpload(false);
		},
		loadPage:function(){
			var p=maxPage+1;
			try{p=parseInt($j("#resource_page").val())}catch(e){}
			if(p<=0||p>maxPage){
				alert("请输入正确的页数！！");
				return false;
			}
			page=p;
			loadUpload(false);
		},
		reload:function(){
			loadUpload(true);
		},
		setMaxPage:function(p){
			maxPage=p;	
		},
		selectResource:function(obj){
			var url=$j(obj).attr("rel");
			var type=$j(obj).find(".vote_cell_img").eq(0).attr("rel");
			var result="";
			if(type=='img')
				result='<img src="'+url+'" border="0" alt="" />';
			else if(type=='mp3')
				result="<br />[mp=500,300]"+url+"[/mp]";
			else{
				result="<br /><img src='images/zip_icon.gif'/>[url="+url+"]点击下载[/url]";
			}
			if(kindEditor)
				kindEditor.insertHtml(result);
			else alert('编辑器无效，请刷新页面！');
		}
	}
})();