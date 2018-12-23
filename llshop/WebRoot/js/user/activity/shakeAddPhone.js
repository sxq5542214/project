
function submitFrom(){
	
	var phone = $("#phone").val();
	
	if(phone ==  '' || phone.length != 11){
		alert('请正确的输入手机号');
		return false;
	}
	
	if(confirm('确定号码为：'+phone+'？ 一个号码只能参与该活动一次！')){
	
		$("#loadimg").show();
		$("#fromButton").hide();
		
	//	$("#form").submit();
		return true;
	}
	
	return false;
}

function shareFriendSuccess(){
	var openid = $("#openid").val();
	var relation_id = $("#relation_id").val();
	
	$.ajax({
			url : 'activity/activityShareSuccess.do',
			data : {
				"openid": openid,
				"relation_id" : relation_id,
				"shareType" : 1
			},
			type : 'POST',
			success : function(data) {
			
			}
		});
}

function shareTimelineSuccess(){

	var openid = $("#openid").val();
	var relation_id = $("#relation_id").val();
	
	$.ajax({
			url : 'activity/activityShareSuccess.do',
			data : {
				"openid": openid,
				"relation_id" : relation_id,
				"shareType" : 2
			},
			type : 'POST',
			success : function(data) {
			
			}
		});
}