/**
 * @author 集成显卡
 */
var Calendar=(function(){
	var year=0;
	var month=0;
	var day=0;
	var ID={YEAR_SELECT:"currentYear",MONTH_SELECT:"month_ul",CALENDR:"current_calendar"};
	
	var changeValue=function(y,m){
		year=y;
		month=m;
		$("option:[value='"+year+"']").attr("selected",true);
		$(".month_ul a:eq("+(month-1)+")").addClass("currentMonth");
	}
	var initCalendar=function(){
		$.ajax({
	          type: "get",url: "getMonth.yws?year="+year+"&month="+month,dataType: 'html',
	          success: function(data){
			  	var content="<tr><th>星期天</th><th>星期一</th><th>星期二</th><th>星期三</th><th>星期四</th><th>星期五</th><th>星期六</th></tr>";
			  	$("#"+ID.CALENDR).html(content+data);
				bindDay();
			  }
			});
	}
	var bindDay=function(){
		$("#"+ID.CALENDR+" td").hover(function(){
			if($(this).attr("colspan")==1&&!$(this).hasClass("space")){
				$("#"+ID.CALENDR+" td:[pass=true]").removeClass("hover");
				$(this).addClass("hover").attr("pass",true);
			}
		}).click(function(){
			if($(this).attr("colspan")==1&&!$(this).hasClass("space")){
				day=$(this).attr("day");
				//$("#day_div").html(year+"年"+month+"月"+day+"号");
				$("#day_div").html("加载节日信息中...");
				$("#create_holiday").attr("href","newUserHoliday.yws?method=create&year="+year+"&month="+month+"&day="+day);
				$.get("getDay.yws",{"year":year,"month":month,"day":day},function(data,state){
					if(state=='success'){
						$("#cur_day_div").html(year+"年"+month+"月"+day+"号");
						$("#day_div").html(data);
					}
				});
			}
		});
		$("#"+ID.CALENDR).bind("mouseleave",function(){
			$("#"+ID.CALENDR+" td:[pass=true]").removeClass("hover");
		});
	}
	return{
		/**
		 * 注册点击事件
		 */
		bindClick:function(y,m){
			$("#"+ID.YEAR_SELECT).change(function(){
				year=$(this).val();
				initCalendar();
			});
			$("#"+ID.MONTH_SELECT).find("a").each(function(){
				$(this).click(function(){
					$("."+ID.MONTH_SELECT+" a:eq("+(month-1)+")").removeClass("currentMonth");
					month=$(this).attr("month");
					$("."+ID.MONTH_SELECT+" a:eq("+(month-1)+")").addClass("currentMonth");
					initCalendar();
				});
			});
			changeValue(y,m);
			bindDay();	
		},
		getCalendar:function(year,month){
			changeValue(year,month);
			initCalendar();
		}
	}
})();