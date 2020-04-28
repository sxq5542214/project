function showCoupon(){
	var html = "";
	$.ajax({
		url : 'supplier/queryCouponInfo.do',
		data : {
		},
		type : 'POST',
		success : function(data) {
			var result = eval("(" + data + ")");
			for(var i = 0 ; i < result.length; i++){
				var coupon= result[i];
				if(coupon.coupon_backgroup==null ||coupon.coupon_backgroup==""){
					html +="<div class='stamp stamp03'>";
				}else{
					html +="<div class='stamp stamp03' style='background:"+coupon.coupon_backgroup+"'>";
				}
				html +="<div class='par'><p>"+coupon.merchant_name+"</p>";
				if(coupon.coupon_discount == null || coupon.coupon_discount == ""){
					html +="<span>"+coupon.coupon_offsetmoney+"元</span><sub>抵扣卷</sub>";
				}else{
					html +="<span>"+coupon.coupon_discount+"折</span><sub>优惠券</sub>";
				}
				html +="<p>开始日期:"+coupon.begintime+"<br>结束日期:"+coupon.endtime+"</p></div>";
				html +="<div class='copy'><p>"+coupon.coupon_name+"</p><a href='#'>立即使用</a></div></div>";
			}
			$("#coupon").html(html);
		}
	});
	}



function queryMycoupon(){
	var html = "";
	$.ajax({
		url : 'supplier/queryMycoupon.do',
		data : {
		},
		type : 'POST',
		success : function(data) {
			var result = eval("(" + data + ")");
			for(var i = 0 ; i < result.length; i++){
				var coupon= result[i];
				if(coupon.coupon_backgroup==null ||coupon.coupon_backgroup==""){
					html +="<div class='stamp stamp03'>";
				}else{
					html +="<div class='stamp stamp03' style='background:"+coupon.coupon_backgroup+"'>";
				}
				html +="<div class='par'><p>"+coupon.merchant_name+"</p>";
				if(coupon.coupon_discount == null || coupon.coupon_discount == ""){
					html +="<span>"+coupon.coupon_offsetmoney+"元</span><sub>抵扣卷</sub>";
				}else{
					html +="<span>"+coupon.coupon_discount+"折</span><sub>优惠券</sub>";
				}
				html +="<p>开始日期:"+coupon.begintime+"<br>结束日期:"+coupon.endtime+"</p></div>";
				html +="<div class='copy'><p>"+coupon.coupon_name+"</p><a href='#'>立即使用</a></div></div>";
			}
			$("#mycoupon").html(html);
			alert("查询成功");
		}
	});
	
}

