//优惠卷领取功能
function reveiveCoupon(coupon_id,openid){
	$.ajax({
		url : 'supplier/supplierCouponController/reveiveCoupon.do',
		data : {
			"coupon_id":coupon_id,
			"openid":openid,
		},
		type : 'POST',
		success : function(data) {
			var result = eval("(" + data + ")");
			alert(result.reveiveResult);				//取到领取优惠卷后后台处理结果
			if(result.reveiveResult == "优惠卷领取成功"){
				changeShowCouponNum(result);
			}
		}		
	});
}

//如果优惠卷领取成功,改变展示优惠卷数量》
function  changeShowCouponNum(coupon){
	$.ajax({
		url : 'supplier/supplierCouponController/findSurplusCoupon.do',
		data : {
			"coupon_id":coupon.id,
		},	
		type : 'POST',
		success : function(data) {
			var result = eval("(" + data + ")");
			$("#"+result.id+"").text("目前还剩余:"+result.number+"张");
		}		
	});
}