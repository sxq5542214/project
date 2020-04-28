function shareFriendSuccess(){
	alert('成功分享，界面即将跳转，请稍等！');
	var openid = $("#openid").val();
	var relation_id = $("#relation_id").val();
	location.href = 'activity/activityShareSuccess.do?openid='+openid+"&relation_id="+relation_id+"&shareType=1";
}

function shareTimelineSuccess(){

	alert('成功分享，界面即将跳转，请稍等！');
	var openid = $("#openid").val();
	var relation_id = $("#relation_id").val();
	location.href = 'activity/activityShareSuccess.do?openid='+openid+"&relation_id="+relation_id+"&shareType=2";
}