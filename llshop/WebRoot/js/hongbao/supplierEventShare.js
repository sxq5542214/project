$(document).ready(function() {
	function shareSuccess(status) {
		var lotteryid = $("#lottery").val();
		$.ajax({
					url : 'lottery/shareWeixinSuccess.do',
					data : {
						lotteryId : lotteryid,
						status : status
					},
					type : 'POST',
					success : function(data) {
						
					}
		});
	}
	
	
	function shareFriendSuccess(){
		shareSuccessUpdateSenceLog(1);
	}
	function shareTimelineSuccess(){
		shareSuccessUpdateSenceLog(2);
	}

	weixinInit.setOnShareAppMessageSuccess(shareFriendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimelineSuccess);

	weixinInit.setShareTitle($title);
	weixinInit.setShareDesc($desc);
	weixinInit.setShareImg($userimg);

});

function shareSuccessUpdateSenceLog(status) {
	var userSenceLogId = $("#userSenceLogId").val();
	$.ajax({
			url : 'wechat/user/updateUserSenceShareInfo.do',
			data : {
				userSenceLogId : userSenceLogId,
				shareType : status
			},
			type : 'POST',
			success : function(data) {
			}
	});
}
function senceLogFriend(){
	shareSuccessUpdateSenceLog(1);
}
function senceLogTimeline(){
	shareSuccessUpdateSenceLog(2);
}

function showBodyDiv(){
	
	setTimeout("document.getElementById('body_div').style.display = 'block';document.title='邀请好友送豪礼';",2000);
	
}
