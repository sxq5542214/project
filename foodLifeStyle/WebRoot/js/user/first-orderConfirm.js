function changePointsBtn(){
	var bt = $('#closeBg');
	var bt2 = $('#btnBg');
	var cs = bt.attr('class');
	var points = new Number($("#points").html());  //这是元为单位的
	var limit_points = new Number($('#limit_points').val()) / 100; //这个是分为单位的,所以要除以100
	var price = new Number($("#price").html());    //这是元为单位的
	var balance = new Number($('#balance').val()) / 100;
	if(balance == '' || balance == 'null' || balance == null){
		balance = 0;
	}
	
	if(points > limit_points){
		points = limit_points;
	}
	
	if(cs == 'pointclose'){
		bt.attr('class','pointopen');
		bt2.attr('class','btnopen');
		
		price = (price - points).toFixed(2);
		if(price < 0){price = 0;}
		
		//保留两位小数
		$("#price").html( price);
		$("#pointsFlag").val("true");
	}else{
		bt.attr('class','pointclose');
		bt2.attr('class','btnclose');
		
		price = (price + points).toFixed(2);
		
		//保留两位小数
		$("#price").html( price );
		$("#pointsFlag").val("false");
	}
}


function pay(){
	var balance = new Number($('#balance').val()) / 100;
	var price = new Number($("#price").html());
	var openid = $("#openid").val();
	var product_id = $("#product_id").val();
	var phone = $("#phone").val();
	var eff_num = $('#eff_num input[name="eff_num"]:checked ').val();
//	var eff_num = $("#eff_num").val();
//	var eff_str = $("#eff_num").find("option:selected").text();
	var eff_str = $('#eff_num input[name="eff_num"]:checked ').parent().find(".eff_num"+eff_num).html();
	var points = $("#points").html() * 100 ; //转换为分为单位
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
					 eff_num : eff_num
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
					 points : points ,
					 eff_num : eff_num
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
										 openid : openid},
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
