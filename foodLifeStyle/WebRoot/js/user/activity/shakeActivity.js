

function joinShakeActivity(){
	
	var openid = $("#openid").val();
	window.location.href= "activity/firstShakeActivity.do?openid="+openid ;
	
//	$.ajax({
//		url : 'activity/firstShakeActivity.do',
//		data : {
//			"openid":$("#openid").val()
//		},
//		async: false,
//		type : 'POST',
//		success : function(data) {
//			 if(data == null || data == ''){
//			 	alert('很抱歉，活动暂时不可参加！请稍后再试!');
//			 	
//			 }else{
//				var result = eval("(" + data + ")");
//			 	
//				var relationid = result.id;
//				var openid = result.openid;
//				
//				window.location.href= "activity/toShakeAddPhonePage.do?openid="+openid+"&relationid="+relationid;
//				
//			 }
//		}
//	});


}