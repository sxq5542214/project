
$(document).ready(function() {
$("ul[class='tap-list']").find('li').click(function(){
changeTitil($(this));
$("#instanceid").val($(this).attr("id"));
})
$("ul[class='tap-list']").find('li').each(function(){
	var skill = $(this).find(".seckilling").text();
	if(skill == "活动进行中"){
		$(this).addClass("cur");
		$("#staticTxtEnd").text("距结束");
	}
})
$(".get-prize").click(function(){
	$(".show-msg").hide();
	//领取奖品页面
});


//秒杀按钮控制
$(".skill-count,.setRemind").click(function(){
	//activity/user/dealUserJoinActivity.do
	var today=new Date()
	var h=today.getHours()
	var m=today.getMinutes()
	var s=today.getSeconds()
	var startDate = new Date();
	var start_hour = $("#seckillRoundNo").text();
	var life_age = $("#seckillLifeAge").text();
	var seckill_time = $("#seckill_time");
	var startDateTime = $("#in_start_day").val();
	var startDateTimeList = startDateTime.split("-");
	startDate = returnDate($("#in_start_day").val(),startDate,start_hour);
		var activityStartTime = startDate.getTime();
		var todayTime = today.getTime();
		$("#seckillRoundNo").text(start_hour);
		var cost_points = $("#cost_points").val()*1;
		var points = $("#points").val()*1;
		
			if((activityStartTime + life_age*1000) < todayTime){
				alert("活动已结束");
			}else if(activityStartTime < todayTime && (activityStartTime+(life_age*1000)) > todayTime){
				if(points < cost_points){
					alert("积分不足！");
					return;
				}
				//秒杀请求
				location.href = "activity/user/dealUserJoinActivity.do?openid="+$("#openid").val()+"&instanceActivityId="+$("#instanceid").val()+"&code="+$("#code").val();
//				var polData = {openid:$("#openid").val(),instanceActivityId:$("#instanceid").val(),code:$("#code").val()};
//				 $.ajax({});
			}else if(todayTime < activityStartTime){
				var msgAlert = $(this);
				if($(msgAlert).hasClass("setRemind")){
					$(".remind").fadeIn().delay(2000).fadeOut();
				}else{
				
				$.ajax({
		  			type : "POST",
		  			url : "activity/user/dealRemindUserJoinActivity.do",
		  			async : false,
		  			data : {"instanceActivityId":$("#instanceid").val(),"openid" : $("#openid").val()},
		  			success : function(data) {
		  				var result = eval("("+data+")");
		  				var isOk = result.instance_name;
		  				if(isOk != "FAIL"){
		  					$(".remind").fadeIn().delay(2000).fadeOut();
		  					if($(msgAlert).hasClass("setRemind")){
//		  						$(this).removeClass("setRemind");
//		  						$(".alert").fadeIn().delay(2000).fadeOut();
//		  						$(this).css("background-color","#C5C5C5");
//		  						$(this).css("color","#000");
//		  						$(this).text("取消提醒");
		  					}else{
		  						$(msgAlert).addClass("setRemind");
		  						$(msgAlert).css("color","#fff");
		  						$(msgAlert).css("background-color","#589E5A");
		  						$(msgAlert).text("设置提醒");
		  					}
		  				}else if(result.remark != "" && result.remark != null){
		  					alert("您目前无法预约，"+result.remark+"才可参与活动！");
		  				}else{
		  					alert("设置提醒失败！");
		  				}
		  			}
		  		});
				}
			//alert("活动还未开始！");
				
			}
});
})
function changeTitil(clickParam){
var today=new Date()
var h=today.getHours()
var m=today.getMinutes()
var s=today.getSeconds()
var startDate = new Date();
	$(".tap-list").find(".cur").removeClass("cur");
	$(clickParam).addClass("cur");
	$("#in_start_day").val($(".tap-list").find(".cur").find(".start_day").text());
	var start_hour = $(".tap-list").find(".cur").find(".start_hour").text();
	var frequence = $(".tap-list").find(".cur").find(".is_other").text();
	if(frequence != "-1" && frequence != -1){
		startDate = returnDate($(clickParam).find(".start_day").text(),startDate,start_hour);
		var life_age = $("#seckillLifeAge").text();
		var activityStartTime = startDate.getTime();
		var todayTime = today.getTime();
		$("#seckillRoundNo").text(start_hour);
			if((activityStartTime + life_age*1000) < todayTime){
				$("#staticTxtEnd").html("已结束");
				$(".seckill-time").css("background-color","#5A5A5A");
			}else if(activityStartTime < todayTime && (activityStartTime+(life_age*1000)) > todayTime){
				$("#staticTxtEnd").html("距结束");
				$(".seckill-time").css("background-color","#EA4C4C");
			}else if(todayTime < activityStartTime){
				$("#staticTxtEnd").html("距开始");
				$(".seckill-time").css("background-color","#EA4C4C");
			}
	}else{
		$(".time").hide();
	}
	
}
function formatSeconds(value,type) { 
var theTime = parseInt(value);// 秒 
var theTime1 = 0;// 分 
var theTime2 = 0;// 小时 
var theTime3 = 0;// 天
// alert(theTime); 
if(theTime > 60) { 
theTime1 = parseInt(theTime/60); 
theTime = parseInt(theTime%60); 
// alert(theTime1+"-"+theTime); 
if(theTime1 > 60) { 
theTime2 = parseInt(theTime1/60); 
theTime1 = parseInt(theTime1%60);
if(theTime2 > 24){
theTime3 = parseInt(theTime2/24);
theTime2 = parseInt(theTime2%24);
}
} 
} 

if(type == "D"){
	if(parseInt(theTime3) < 10){
		return '0'+parseInt(theTime3);
	}else{
		return parseInt(theTime3);
	}
}else if(type == "H"){
	if(parseInt(theTime2) < 10){
		return '0'+parseInt(theTime2);
	}else{
		return parseInt(theTime2);
	}
}else if(type == "M"){
		if(parseInt(theTime1) < 10){
		return '0'+parseInt(theTime1);
	}else{
		return parseInt(theTime1);
	}
}else if(type == "S"){
		if(parseInt(theTime) < 10){
		return '0'+parseInt(theTime);
	}else{
		return parseInt(theTime);
	}
}
return result; 
} 
function checkTime(i)
{
if (i<10) 
  {i="0" + i}
  return i
}

function startTime()
{
	var frequence = $("#frequence").val();
	if(frequence != "-1" && frequence != -1){
		var today=new Date()
		var h=today.getHours()
		var m=today.getMinutes()
		var s=today.getSeconds()
		var startDate = new Date();
		var start_hour = $("#seckillRoundNo").text();
		var life_age = $("#seckillLifeAge").text();
		// add a zero in front of numbers<10
		m=checkTime(m)
		s=checkTime(s)
		//document.getElementById('seckill_time').innerHTML=h+":"+m+":"+s
		var seckill_time = $("#seckill_time");
		startDate = returnDate($("#in_start_day").val(),startDate,start_hour);
		var activityStartTime = startDate.getTime();
		var todayTime = today.getTime();

				var milliS = activityStartTime -todayTime;
				if(milliS < 0){
					
					milliS = activityStartTime+(life_age*1000) -todayTime;
					if(milliS >=0){
						$("#staticTxtEnd").text("距结束");
						$(".seckill-time").css("background-color","#EA4C4C");
						$($("span[class='seckill-time']")[0]).text(formatSeconds(milliS/1000,'D'));
						$($("span[class='seckill-time']")[1]).text(formatSeconds(milliS/1000,'H'));
						$($("span[class='seckill-time']")[2]).text(formatSeconds(milliS/1000,'M'));
						$($("span[class='seckill-time']")[3]).text(formatSeconds(milliS/1000,'S'));
					}else{
						$("#staticTxtEnd").text("已结束");
						$(".seckill-time").css("background-color","#5A5A5A");
						$($("span[class='seckill-time']")[0]).text("00");
						$($("span[class='seckill-time']")[1]).text("00");
						$($("span[class='seckill-time']")[2]).text("00");
						$($("span[class='seckill-time']")[3]).text("00");
					}
				}else{
					$("#staticTxtEnd").text("距开始");
					$(".seckill-time").css("background-color","#EA4C4C");
					$($("span[class='seckill-time']")[0]).text(formatSeconds(milliS/1000,'D'));
					$($("span[class='seckill-time']")[1]).text(formatSeconds(milliS/1000,'H'));
					$($("span[class='seckill-time']")[2]).text(formatSeconds(milliS/1000,'M'));
					$($("span[class='seckill-time']")[3]).text(formatSeconds(milliS/1000,'S'));
					
				}

		$("ul[class='tap-list']").find('li').each(function(i){
			startDate = returnDate($(this).find(".start_day").text(),startDate,$(this).find(".start_hour").text());
			var todayTime = today.getTime();
			var activityStartTime = startDate.getTime();
			if(activityStartTime < todayTime && todayTime<(activityStartTime + life_age*1000)){
			
				$(this).find(".seckilling").text("活动进行中");
				$("div[id='seckill-body']").find(".skill-hot").each(function(n){
					if(i == n){
					//$(this).find(".reminded").hide();
						$(this).find(".skill-count").each(function(){
						$(this).css("background-color","#f23030");
						$(this).text("立 即 参 与");
						
					});
				}
			})
		}else if((activityStartTime + life_age*1000) < todayTime){

			$(this).find(".seckilling").text("已结束");
			$("div[id='seckill-body']").find(".skill-hot").each(function(n){
				//$(this).find(".reminded").hide();
				if(i == n){
					$(this).find(".skill-count").each(function(){
						$(this).css("background-color","#9A9A9A");
						$(this).text("已结束");
					});
				}
			})
		}else if(todayTime < activityStartTime){

			$(this).find(".seckilling").text("即将开始");
			$("div[id='seckill-body']").find(".skill-hot").each(function(n){
				if(i == n){
					$(this).find(".skill-count").each(function(){
					$(this).css("background-color","#f23030");
						//$(this).text("即将开始");
						if($(this).hasClass("setRemind")){
							$(this).css("background-color","#C5C5C5");
							$(this).css("color","#000");
							$(this).text("已设置提醒");
						}else{
							$(this).css("background-color","#589E5A");
							$(this).css("color","#fff");
							$(this).text("设 置 提 醒");
						}
						
					});
				}
			})
		}
			
			$(this).click(function(){
					$("div[id='seckill-body']").find(".skill-hot").each(function(n){
						if(i == n){
							$(this).css("display","block");
						}else{
							$(this).css("display","none");
						}
					});
				});
			
		});
		t=setTimeout('startTime()',1000)
	}else{
		$(".time").hide();
	}

}