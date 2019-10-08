

//请使用 weixinInit的各类set方法来重写以下方法
var onShareQQSuccess; 	//分享QQ成功  	weixinInit.setOnShareQQSuccess
var onShareQQFail;		//分享QQ失败	weixinInit.setOnShareQQFail
var onShareTimelineSuccess;		//分享朋友圈成功
var onShareTimelineFail;		//分享朋友圈失败
var onShareAppMessageSuccess;	//分享朋友成功
var onShareAppMessageFail;		//分享朋友失败
var servAppName = "";

// 微信统一初始化
var weixinInit = function() {
	// 微信appId
	var appId = "";
	var servIP = location.href.split(servAppName+'/')[0];
	var curUrl = location.href.split('#')[0];
	var shareTitle = null;
	var shareDesc = null;
	var shareLink = location.href;
	var shareImg = null;
	var share_time_ms = new Date().getTime();

	return {
		/* 初始化数据 */
		init : function() {
			// 微信签名
			weixinInit.getWeChatSign();
		},

		// 微信签名
		getWeChatSign : function() {
			var param = "{url:'" + curUrl + "'}";
			$.ajax({
						url : 'wechat/user/checkJsSign.do',
						data : {
							"json" : param
						},
						type : 'POST',
						success : function(data) {
							// alert(data);
							var result = eval("(" + data + ")");
							var timestamp = result[0].timestamp;
							var nonceStr = result[0].nonceStr;
							var signature = result[0].signature;
							appId = result[0].appid;
							weixinInit.setWxConfig(timestamp, nonceStr,
									signature);
						}
					});
		},

		// 设置微信配置
		setWxConfig : function(time, str, sign) {
			wx.config({
				debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				appId : appId, // 必填，公众号的唯一标识
				timestamp : time, // 必填，生成签名的时间戳
				nonceStr : str, // 必填，生成签名的随机串
				signature : sign,// 必填，签名，见附录1
				jsApiList : ['onMenuShareQQ', 'onMenuShareTimeline',
						'onMenuShareAppMessage', 'onMenuShareWeibo','openAddress',
						'onMenuShareQZone', 'chooseImage','uploadImage','downloadImage','scanQRCode']
					// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
		},

		/*
		 * //微信扫一扫 sanQR:function(){ wx.scanQRCode({ needResult: 0, //
		 * 默认为0，扫描结果由微信处理，1则直接返回扫描结果， scanType: ["qrCode","barCode"], //
		 * 可以指定扫二维码还是一维码，默认二者都有 success: function (res) { var result =
		 * res.resultStr; // 当needResult 为 1 时，扫码返回的结果 alert("调用成功"); } });
		 *  },
		 */

		setShareTitle : function(title) {
			shareTitle = title;
		},
		setShareDesc : function(desc) {
			shareDesc = desc;
		},
		setShareImg : function(img) {
			shareImg = img;
		},
		setShareLink : function(link) {
			shareLink = link;
		},
		getShareTitle : function() {
			return shareTitle;
		},
		getShareDesc : function() {
			return shareDesc;
		},
		getShareImg : function() {
			return shareImg;
		},
		getShareLink : function() {
			return shareLink;
		},
		getShare_time_ms : function(){
			return share_time_ms;
		},
		// on function注册
		setOnShareQQSuccess : function(fun) {
			onShareQQSuccess = fun;
		},
		setOnShareQQFail : function(fun) {
			onShareQQFail = fun;
		},
		setOnShareTimelineSuccess : function(fun) {
			onShareTimelineSuccess = fun;
		},
		setOnShareTimelineFail : function(fun) {
			onShareTimelineFail = fun;
		},
		setOnShareAppMessageSuccess : function(fun) {
			onShareAppMessageSuccess = fun;
		},
		setOnShareAppMessageFail : function(fun) {
			onShareAppMessageFail = fun;
		}
	};

}();

wx.ready(function() {
			// wx.showOptionMenu();
			// wx.showMenuItems({
			// menuList: ['menuItem:share:appMessage','menuItem:share:timeline',
			// 'menuItem:share:qq','menuItem:share:weiboApp',
			// 'menuItem:share:QZone'] // 要显示的菜单项，所有menu项见附录3
			// });
			var shareTitle = weixinInit.getShareTitle();
			var shareDesc = weixinInit.getShareDesc();
			var shareLink = weixinInit.getShareLink();
			var shareImg = weixinInit.getShareImg();
			// alert(shareTitle);
			// QQ分享
			wx.onMenuShareQQ({
						title : shareTitle, // 分享标题
						desc : shareDesc, // 分享描述
						link : shareLink, // 分享链接
						imgUrl : shareImg, // 分享图标
						success : function() {
							// 用户确认分享后执行的回调函数
							// alert("QQ分享成功");
							onShareQQSuccess();
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
							// alert("QQ分享失败");
							onShareQQFail();

						}
					});

			// 朋友圈分享
			wx.onMenuShareTimeline({
						title : shareTitle, // 分享标题
						link : shareLink, // 分享链接
						imgUrl : shareImg, // 分享图标
						success : function() {
							// 用户确认分享后执行的回调函数
							onShareTimelineSuccess();
							alert("朋友圈分享成功");
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
							onShareTimelineFail();
							alert("朋友圈分享失败");
						}
					});
			// 朋友分享
			wx.onMenuShareAppMessage({
						title : shareTitle, // 分享标题
						desc : shareDesc, // 分享描述
						link : shareLink, // 分享链接
						imgUrl : shareImg, // 分享图标
						type : '', // 分享类型,music、video或link，不填默认为link
						dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
						success : function() {
							onShareAppMessageSuccess();

							// 用户确认分享后执行的回调函数
//							alert("朋友分享成功");
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
							onShareAppMessageFail();
							alert("朋友分享失败");
						}
					});
			// 腾讯微博分享
			wx.onMenuShareWeibo({
						title : shareTitle, // 分享标题
						desc : shareDesc, // 分享描述
						link : shareLink, // 分享链接
						imgUrl : shareImg, // 分享图标
						success : function() {
							// 用户确认分享后执行的回调函数
							alert("腾讯微博分享成功");
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
							alert("腾讯微博分享失败");
						}
					});
			// QQ空间分享
			wx.onMenuShareQZone({
						title : shareTitle, // 分享标题
						desc : shareDesc, // 分享描述
						link : shareLink, // 分享链接
						imgUrl : shareImg, // 分享图标
						success : function() {
							// 用户确认分享后执行的回调函数
							alert("QQ空间分享成功");
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
							alert("QQ空间分享失败");
						}
					});
		});
wx.error(function(res) {
			alert("微信错误提示：" + res.errMsg);
		});

weixinInit.init();