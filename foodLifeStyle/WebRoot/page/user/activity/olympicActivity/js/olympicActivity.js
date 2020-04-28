function joinOlympicGuessActivity(){
	
	var guess_num = $("#guess_num").val();
	var openid = $("#openid").val();
	var showQRCode = $("#showQRCode").val();
	var shared = $("#shared").val();
	var user_shared = $("#user_shared").val();
	
	if(showQRCode == 'true'){
		alert('请长按上方二维码即可参加活动！');
		return ;
	}
	if(shared == 'false'){
		alert('活动已经结束啦！');
		return ;
	}
	
	if(guess_num == '' ){
		alert('请先填写竞猜的奖牌数量！');
		return ;
	}
	
	$.ajax({
		type: "POST",
		async : false,
		url: 'activity/user/joinOlympicActivity.do',
		data: { guess_num : guess_num, openid : openid ,user_shared:user_shared},
		success: function(result) {
			
			alert(result);
			$("#guess_num").attr("disabled",true);
			$("#guess_num").attr("readonly",true);
		}
	});
	
}
 function shareFriendSuccess(){
  	var openid = $("#openid").val();
  	var share_time_ms = weixinInit.getShare_time_ms();
  	$.ajax({
		type : "POST",
		url : "user/handleUserShare.do",
		data : {"openid": openid,"share_type":1,
		"share_from":"olympic_activity","share_time_ms":share_time_ms},
		success : function(data) {
			$("#shared").val('true');
		}
	});

  }
  function shareTimeFirendSuccess(){
  	var share_time_ms = weixinInit.getShare_time_ms();
  	
  	var openid = $("#openid").val();
  	$.ajax({
		type : "POST",
		url : "user/handleUserShare.do",
		data : {"openid": openid,"share_type":2,
		"share_from":"olympic_activity", "share_time_ms" : share_time_ms},
		success : function(data) {
			$("#shared").val('true');
		}
	});

  }

	




