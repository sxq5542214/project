function changePointsBtn(){
	var bt = $('#closeBg');
	var bt2 = $('#btnBg');
	var cs = bt.attr('class');
	//var points = new Number($("#points").html());  //这是元为单位的
	var points = $("#coupon_points").val();					//接收积分值
	var limit_points = new Number($('#limit_points').val()) / 100; //这个是分为单位的,所以要除以100
	//var price = new Number($("#price").html());    //这是元为单位的`
	var	price =  $("#price_coupon").val();					//取的是原价里面的值
	var offset = new Number($('#offset').val()) / 100; 
	var balance = new Number($('#balance').val()) / 100;
	
	var coupon_discount = new Number($('#coupon_discount').val()) / 10;
	var coupon_offsetmoney = new Number($('#coupon_offsetmoney').val()) / 100;
	
	if(balance == '' || balance == 'null' || balance == null){
		balance = 0;
	}
//	alert("limit_points"+limit_points);
//	if(points > limit_points){
//		points = limit_points;
//	}
//	
	
	if(cs == 'pointclose'){					//用积分的
		bt.attr('class','pointopen');
		bt2.attr('class','btnopen');
		//保留两位小数
		price = price - points;
		if(coupon_discount != 0){									//是否使用了折扣卷
			price = price * coupon_discount/10 ;
		}
		if(coupon_offsetmoney != 0){								//是否使用了抵扣卷
			price = price - coupon_offsetmoney ;
		}
		price = price.toFixed(2);
		if(price < 0){price = (0).toFixed(2);}
		$("#price").html( price);
		$("#pointsFlag").val("true");
	}else{
		bt.attr('class','pointclose');
		bt2.attr('class','btnclose');
		//保留两位小数
		price = price - 0;
		if(coupon_discount != 0){									//是否使用了折扣卷
			price = price * coupon_discount/10 ;
		}
		if(coupon_offsetmoney != 0){								//是否使用了抵扣卷
			price = price - coupon_offsetmoney ;
		}
		price = price.toFixed(2);
		if(price < 0){price = (0).toFixed(2);}
		$("#price").html( price);
		//$("#price").html( (price + points).toFixed(2));
		$("#pointsFlag").val("false");
	}
}





function pay(){
	var coupon_record_id = $("#coupon_record_id").val();		//优惠卷id
	var balance = new Number($('#balance').val()) / 100;
	var price = new Number($("#price").html());
	var openid = $("#openid").val();
	var product_id = $("#product_id").val();
	var phone = $("#phone").val();
	var eff_num = $('#eff_num input[name="eff_num"]:checked ').val();
//	var eff_num = $("#eff_num").val();
//	var eff_str = $("#eff_num").find("option:selected").text();
	var eff_str = $('#eff_num input[name="eff_num"]:checked ').parent().find(".eff_num"+eff_num).html();
	var points = new Number($("#points").html()) * 100 ; //转换为分为单位
	var cost_balance = new Number($("#cost_balance").html());
	if( isNaN(cost_balance)){
		cost_balance = 0 ;
	}
	//让用户确认下
	if(!confirm('确定为：'+phone+'订购？ 生效时间：'+eff_str)){
		return ;
	}
	
	points = points.toFixed(0);
	showdiv();
	
	if(balance >= price){
		$.ajax({
			url : "user/createUnifiedOrder.do",
			data : { price : price,
					 openid : openid,
					 cost_balance : (cost_balance * 100).toFixed(0),
					 product_id : product_id,
					 phone : phone ,
					 points : points,
					 eff_num : eff_num,
					 coupon_record_id: coupon_record_id
			},
			success : function(result) {
				if(result == 'false'){
					alert('调用支付失败');
					hidediv();
				}else{
					location.href = "order/userOrderProduct.do?out_trade_code="+ result;
				}
			}
		});
		
	}else{
		$.ajax({
			url : "wechat/createUnifiedOrder.do",
			data : { price : (price - cost_balance).toFixed(2),
					 cost_balance : (cost_balance * 100).toFixed(0),
					 openid : openid,
					 product_id : product_id,
					 phone : phone ,
					 points : points,
					 eff_num : eff_num ,
					 coupon_record_id : coupon_record_id
			},
			success : function(result) {
				if(result == 'false'){
					alert('调用支付失败');
					hidediv();
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
				           	alert('支付成功！界面即将跳转，请稍等！');
				           
				           	location.href = "order/userOrderProduct.do?out_trade_code="+result.outTradeNo;
				           	
				           }else{
				           	$.ajax({
								url : "wechat/deleteUnifiedOrder.do",
								data : { outTradeNo : result.outTradeNo,
										 openid : openid
										},
								success : function(d) {
									hidediv();
								}
				           	});
				           }
				       }
				   ); 
				}
			}
		});
	}

}

function showdiv() { 
 document.getElementById("show").style.display ="none";
 document.getElementById("loader").style.display  = "block";
}
function hidediv() {
 document.getElementById("show").style.display ='block';
 document.getElementById("loader").style.display  = "none";
}






function popCoupon(userid,supplier_id){
	$("#cognzhagnhul").empty();
	$.ajax({
		url : 'supplier/supplierCouponController/queryMyAllCoupon.do',
		data : {
			"userid":userid,
			"supplier_id" : supplier_id
		},
		type : 'POST',
		success : function(data) {
			var result = eval("(" + data + ")");
			$(".tfrom-mask").css("height",document.body.scrollHeight+"px").css("width",document.body.scrollWidth+"px").css("display","block");
			$(".tfrom").css("display","block");
			$(".tfrom").css("margin-top","-70%");
			var html = mosaicCoupon(result);
			$("#cognzhagnhul").append(html);
			
		}
	});

}




function choseCoupon(coupon_discount,coupon_offsetmoney,coupon_record_id,coupon_id,userid,rule_name){
	$.ajax({
		url : 'supplier/supplierCouponController/useCoupon.do',
		data : {
			"userid":userid,
			"coupon_id":coupon_id
		},
		type : 'POST',
		success : function(data) {
			var result = eval("(" + data + ")");
			if(result.reveiveresult == "优惠卷可以使用"){
				couponPageChange(coupon_discount,coupon_offsetmoney,coupon_record_id);
			}else{
				alert(rule_name);
			}
		}	
	});

}
	
	
function  couponPageChange(coupon_discount,coupon_offsetmoney,coupon_record_id) {
	$(".tfrom").css("display","none");
	$(".tfrom-mask").css("display","none");
	$("#coupon_discount").val(coupon_discount);
	$("#coupon_offsetmoney").val(coupon_offsetmoney);
	$("#coupon_record_id").val(coupon_record_id);			//把记录id存储在隐藏标签中
	var points = $("#coupon_points").val();					//接收积分值
	var	price =  $("#price_coupon").val();					//取的是原价里面的值
 	var cost_balance = $("#cost_balance_coupon").val();			//余额需要支付的钱
 	
	var bt = $('#closeBg');
	var cs = bt.attr('class');								//获取积分中的class的值
	if(cs == 'pointopen'){					//为使用积分的状态
		if(coupon_discount != 0){			//使用的折扣卷
			$("#coupon").text(coupon_discount/10+"折折扣卷"); 	//页面展示折扣卷
			price =(( price - points) * coupon_discount/100 ).toFixed(2) ;	//原价减去积分后乘以优惠卷的的值
			if(price*1 < cost_balance*1){
				$("#cost_balance").text(price);
			}else{
				$("#cost_balance").text(cost_balance);
			}
			$("#price").text(price);
			
		}else{
			$("#coupon").text(coupon_offsetmoney/100+"元抵扣元");//页面展示抵扣卷
			price =(( price - points) - coupon_offsetmoney/100).toFixed(2) ;	//原价减去积分后在减去优惠卷的值
			if(price <= 0){
				price = (0).toFixed(2);
			}
			if(price*1 < cost_balance*1){
				$("#cost_balance").text(price);
			}else{
				$("#cost_balance").text(cost_balance);
			}
			
			$("#price").text(price);
			
		}	
		}else{									//为不使用积分的状态
		if(coupon_discount != 0){
			$("#coupon").text(coupon_discount/10+"折折扣卷"); 			//页面展示折扣卷
			price =( price  * coupon_discount/100 ).toFixed(2) ;	//减去积分算出最终价格
			if(price*1 < cost_balance*1){
				$("#cost_balance").text(price);
			}else{
				$("#cost_balance").text(cost_balance);
			}
			$("#price").text(price);

			
		}else{
			$("#coupon").text(coupon_offsetmoney/10+"元抵扣元");		//页面展示抵扣卷
			price =( price  - coupon_offsetmoney/10).toFixed(2) ;		//减去积分算出最终
			if(price <= 0){
				price = (0).toFixed(2);
			}
			if(price*1 < cost_balance*1){
				$("#cost_balance").text(price);
			}else{
				$("#cost_balance").text(cost_balance);
			}
			$("#price").text(price);
		}
	}
}

//优惠卷没有匹配到产品
function couponNoMatchingProduct(){
	alert("该优惠卷不可用于这个产品,请选择其他优惠卷");
}


//拼接优惠卷展示信息
function mosaicCoupon(result){
	var html = "";
	for(var i = 0 ; i < result.length; i++){
		var coupon= result[i];																																															
		if(coupon.coupon_discount == 0){
			if(coupon.status == 99){
				html += "<li  id='"+result.id+"' ><label id='"+coupon.id+"' style=' width:100%; display:block;'><input type='text' style='display:none' onclick = 'choseCoupon("+coupon.coupon_discount+","+coupon.coupon_offsetmoney+","+coupon.id+","+coupon.coupon_id+","+coupon.userid+",\""+coupon.rule_name+"\")'  \>"+coupon.coupon_offsetmoney/100+"元抵扣卷</br>有效期:"+coupon.use_time+"</br>失效期:"+coupon.expire_time+"</br>使用规则:"+coupon.rule_name+"</label></li>";
			}else{
				html += "<li style='color:#AAAAAA'  id='"+result.id+"'><label id='"+coupon.id+"' style=' width:100%; display:block;'><input type='text' style='display:none' onclick = 'couponNoMatchingProduct()'  \>"+coupon.coupon_offsetmoney/100+"元抵扣卷</br>有效期:"+coupon.use_time+"</br>失效期:"+coupon.expire_time+"</br>"+coupon.rule_name+"</label></li>";
			}
		}else{
		if(coupon.status == 99){
			html += "<li id='"+result.id+"'><label id='"+coupon.id+"' style=' width:100%; display:block;'><input type='text' style='display:none' onclick = 'choseCoupon("+coupon.coupon_discount+","+coupon.coupon_offsetmoney+","+coupon.id+","+coupon.coupon_id+","+coupon.userid+",\""+coupon.rule_name+"\")'  \>"+coupon.coupon_discount/10+"折折扣卷</br>有效期:"+coupon.use_time+"</br>失效期:"+coupon.expire_time+"</br>使用规则:"+coupon.rule_name+"</label></li>";
			}else{
			html += "<li style='color:#AAAAAA'  id='"+result.id+"'><label id='"+coupon.id+"' style=' width:100%; display:block;'><input type='text' style='display:none' onclick = 'couponNoMatchingProduct()'  \>"+coupon.coupon_discount/10+"折折扣卷</br>有效期:"+coupon.use_time+"</br>失效期:"+coupon.expire_time+"</br>"+coupon.rule_name+" </label></li>";
			}
		}
	}
	return html;
	
}



//关闭弹窗
function closePop(){
	$(".tfrom").css("display","none");
	$(".tfrom-mask").css("display","none");
}