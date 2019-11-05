Array.prototype.remove = function(from, to) {
  var rest = this.slice((to || from) + 1 || this.length);
  this.length = from < 0 ? this.length + from : from;
  return this.push.apply(this, rest);
};
/**
 * 
 * 模仿android里面的Toast效果，主要是用于在不打断程序正常执行的情况下显示提示数据
 * 
 * @param config
 * 
 * @return
 * 
 */
var Toast = function(config) {

	this.context = config.context == null ? $('body') : config.context;// 上下文

	this.message = config.message;// 显示内容

	this.time = config.time == null ? 2000 : config.time;// 持续时间

	this.left = config.left;// 距容器左边的距离

	this.top = config.top;// 距容器上方的距离

	this.init();

};

var msgEntity;

Toast.prototype = {

	// 初始化显示的位置内容等

	init : function() {

		$("#toastMessage").remove();

		// 设置消息体

		var msgDIV = new Array();

		msgDIV.push('<div id="toastMessage">');

		msgDIV.push('<span>' + this.message + '</span>');

		msgDIV.push('</div>');

		msgEntity = $(msgDIV.join('')).appendTo(this.context);

		// 设置消息样式

		var left = this.left == null ? this.context.width() / 2
				- msgEntity.find('span').width() / 2 : this.left;

		var top = this.top == null ? '20px' : this.top;

		msgEntity.css({
					position : 'fixed',
	//				top : top,
					bottom : '60px',
					'z-index' : '99',
					left : left,
					'background-color' : 'black',
					color : 'white',
					'font-size' : '18px',
					margin : '10px'
				});

		msgEntity.hide();

	},

	// 显示动画
	showMsg : function() {
	//	$("#toastMessage").show();
	//	$("#toastMessage").fadeToggle(3000);
	//	msgEntity.show();
	//	msgEntity.fadeTo("slow",0);
		
		//msgEntity.fadeIn(this.time / 2);
	//	msgEntity.fadeOut(this.time / 2);
		msgEntity.fadeToggle(this.time / 2);
		setTimeout( 'msgEntity.fadeToggle(2000);' ,this.time / 2);
		
	}
};


function addProductToCart(spid, num) {

	var successMSGBox = new Toast({context:$('body'),message:'成功加入购物车'});
	var productInfo = getCookie("productInfo");

	if (productInfo != null) {
		var productJson = eval('(' + productInfo + ')');

		var productInfos = productJson.productInfos;
		var notFind = true;
		var len = productInfos.length;

		// 循环，找到相同ID的，然后设置数量
		for (var i = 0; i < len; i++) {
			var spInfo = productInfos[i];
			if (spInfo.spid == spid) {
				productInfos[i].num = num;
				notFind = false;
			}
		}
		if (notFind) {
			productInfos[len] = new Object();
			productInfos[len].spid = spid;
			productInfos[len].num = num;
		}
		setCookie("productInfo", JSON.stringify(productJson));
	} else {
		setCookie("productInfo", "{productInfos:[]}");
		addProductToCart(spid, num);
	}
	
	successMSGBox.showMsg();
}

function deleteProductToCart(spid) {

	var dropMSGBox = new Toast({context:$('body'),message:'成功从购物车删除'});
	var productInfo = getCookie("productInfo");
	if (productInfo != null) {
		var productJson = eval('(' + productInfo + ')');

		var productInfos = productJson.productInfos;
		var len = productInfos.length;

		// 循环，找到相同ID的，然后设置数量
		for (var i = 0; i < len; i++) {
			
			var spInfo = productInfos[i];
			if (typeof(spInfo) != "undefined" &&  spInfo.spid == spid) {
				productInfos[i].spid = -1;
				productInfos[i].num = 0;
				productInfos.remove(i);
			}
		}
		
		setCookie("productInfo", JSON.stringify(productJson));
	}
	
	dropMSGBox.showMsg();
}

