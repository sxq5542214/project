function changeShareLink(){
	
	var lotteryid = $("#lottery").val();
	var productId = $("#productId").val();
	var servIP = location.href.split('eca/')[0];
	var userSenceLogId = $("#userSenceLogId").val();
	
	function shareSuccessUpdateSenceLog(status) {
		$.ajax({
				url : 'wechat/updateUserSenceShareInfo.do',
				data : {
					userSenceLogId : userSenceLogId,
					shareType : status
				},
				type : 'POST',
				success : function(data) {
				}
		});
	}
	
	function shareSuccess(status) {
		$.ajax({
					url : 'lottery/shareWeixinSuccess.do',
					data : {
						lotteryId : lotteryid,
						status : status
					},
					type : 'POST',
					success : function(data) {
						document.location =  'https://open.weixin.qq.com/connect/oauth2/authorize?appid='+data+'&redirect_uri='+servIP+'eca/wechat/toBWCUserAddressPage.do%3FproductId%3D'+productId+'&response_type=code&scope=snsapi_base&state=1#wechat_redirect';
					}
				});
	}
	
	function shareFriendSuccess(){
//		shareSuccess(1);
		shareSuccessUpdateSenceLog(1);
		alert('请分享到朋友圈才能设置收货地址哦！');
	}
	function shareTimelineSuccess(){
		shareSuccessUpdateSenceLog(2);
		shareSuccess(2);
	}

	weixinInit.setOnShareAppMessageSuccess(shareFriendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimelineSuccess);

	weixinInit.setShareTitle($title);
	weixinInit.setShareDesc($desc);
	weixinInit.setShareImg($userimg);
	weixinInit.setShareLink(servIP+'eca/lottery/displayLotteryWin.do?lotteryId='+lotteryid+'&userSenceLogId='+userSenceLogId);
	
	setTimeout("document.getElementById('body_div').style.display = 'block';",2000);
}
function showBodyDiv(){
	
	
}
