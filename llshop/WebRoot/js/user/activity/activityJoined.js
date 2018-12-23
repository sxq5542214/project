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