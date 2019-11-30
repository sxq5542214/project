function pay(){

	$("#payButton").html('加载中...');
	$("#payButton").on('click', '' );
	
	var order_code = $("#order_code").val();
	var cost_money = $("#cost_money").html();
	var cost_balance = $("#cost_balance").val();
	var openid = $("#openid").val();
	var phone = $("#phone").html();
	var points = $("#points").html();
	var coupon_record_id = $("#coupon_record_id").val();
	if( typeof(phone) == 'undefined' || phone == ''){
		alert("请先设置收货地址信息！");
		return;
	}
	$.ajax({
		url : "wechat/createUnifiedOrderByShop.do",
		data : { cost_money : (cost_money - cost_balance).toFixed(2),
				 cost_balance : (cost_balance * 100).toFixed(0),
				 openid : openid,
				 order_code : order_code,
				 phone : phone ,
				 points : points,
				 coupon_record_id : coupon_record_id
		},
		success : function(result) {
			if(result == 'false'){
				alert('调用支付失败');
				$("#payButton").html('立即支付');
				$("#payButton").on('click','pay()');
			}else{
				result = eval('('+result+')');
				
				WeixinJSBridge.invoke(
			       'getBrandWCPayRequest', {
			           "appId" : result.appId,     //公众号名称，由商户传入     
			           "timeStamp": result.timeStamp,         //时间戳，自1970年以来的秒数     
			           "nonceStr" : result.nonceStr, //随机串     
			           "package" : result.packages,     
			           "signType" : result.signType,         //微信签名方式：     
			           "paySign" : result.paySign //微信签名 
			       },
			       function(res){
			           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
			        	delCookie('productInfo'); 
			           	alert('已支付成功！我们将会尽快安排发货！');
			           
			           	$("#payButton").hide();
//			           	location.href = "order/userOrderProduct.do?out_trade_code="+result.outTradeNo;
			         	location.href = "user/toUserShopOrderListPage.do?openid="+openid;			           	
			           }else{
			           	$.ajax({
							url : "wechat/deleteUnifiedOrderByShop.do",
							data : { outTradeNo : result.outTradeNo,
									 transactionId : result.transactionId,
									 openid : openid
									},
							success : function(d) {
								$("#payButton").html('立即支付');
								$("#payButton").show();
								$("#payButton").on('click','pay()');
							}
			           	});
			           }
			       }
			   ); 
			}
		}
	});
	
}