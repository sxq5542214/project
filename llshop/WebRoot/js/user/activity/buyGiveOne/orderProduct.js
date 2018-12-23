function changeTips(img){
	
	var dis = $("#tips").css("display");
	
	if(dis == 'block'){
		$("#tips").css("display","none");
		$("#tips_img").src = "images/icon/downjt.png";
	}else{
		$("#tips").css("display","block");
		$("#tips_img").src = "images/icon/upjt.png";
	}
}

function choseProduct(pid){
	var oldid = $("#product_id").val();
	if(oldid != '' ){ //旧的样式移除
		$("#"+oldid).css("color","#1eb387");
		$("#"+oldid).css("background-color","#FFFFFF");
	}
	
	$("#product_id").val(pid);
	$("#"+pid).css("color","red");
	$("#"+pid).css("background-color","#ECF2FF");

	var user_points =  new Number($("#user_points").val());
	var price = $("#"+pid).attr("title");
	var real_price = $("#"+pid).attr("realprice");
	var discount = $("#"+pid).attr("discount");
	var limit = $("#"+pid).attr("limit");
//  var givePoints = $("#"+pid).attr("givePoints");
	var offset = $("#"+pid).attr("offset");
	if(offset == null || offset == "" || offset == "undefined" ){
		$("#discount_price").html( (price/100 * discount / 100 ).toFixed(2));
	}else{
		offset = offset/100;
		var sub_points = offset.toFixed(2);
		if(user_points < offset){
			sub_points = user_points.toFixed(2);
		}
		$("#discount_price").html( (price/100 * discount / 100- sub_points).toFixed(2));
	}
	$("#text_price").html(real_price/100);

	//重新计算优惠价
//	$("#discount_price").html( (price/100 * discount / 100 - sub_points ).toFixed(2) );
//	$("#product_price").val(price);
//	if ( user_points < limit ) 
//	{
//		$("#points").html("本商品可用"+limit+"元,您的积分不足,请点击上图邀请更多好友,获得更多积分享受最低折扣！");
//		$("#givePoints").html(" ");
//	}
//	else 
//	{
//		$("#points").html("本商品可使用"+limit+"元");
//		$("#givePoints").html("成功购买后再返"+givePoints+"元积分，可用于下次消费");
//	}
	

	
}

function checkPhone(){
	var phone = $("#phone").val();
	if(phone.length == 11){
		//之前选的商品数据清空
		$("#product_id").val('');
		$.ajax({
			url : 'supplierProduct/queryProductByPhone.do',
			data : {
				"customer_id":$("#customer_id").val(),
				"phone" : phone
			},
			type : 'POST',
			success : function(data) {
				 if(data == null || data == ''){
				 	alert('很抱歉，暂不支持该省手机号充值！我们正在努力接入中，敬请期待！');
				 	$("#main").html('');
				 }else{
					var result = eval("(" + data + ")");
				 	var html = "";
				 	
					for(var i = 0 ; i < result.length; i++){
						var csp = result[i];
				 		$("#isp").html(csp.areaData.province + csp.areaData.brand);
				 		
				 		html += addProductInfo(csp);
						
					}
					$("#main").html(html);
				 }
			}
		});
	}
}
//没输入号码页面展示
function defaultPhone(){
	var phone = "18755171111";
	if(phone.length == 11){
		//之前选的商品数据清空
	$("#product_id").val('');
		$.ajax({
			url : 'supplierProduct/queryProductByPhone.do',
			data : {
				"customer_id":$("#customer_id").val(),
				"phone" : phone
			},
			type : 'POST',
			success : function(data) {
				 if(data == null || data == ''){
				 	alert('很抱歉，暂不支持该省手机号充值！我们正在努力接入中，敬请期待！');
				 	$("#main").html('');
				 }else{
					var result = eval("(" + data + ")");
				 	var html = "";
				 	
					for(var i = 0 ; i < result.length; i++){
						var csp = result[i];
				 		$("#isp").html(csp.areaData.province + csp.areaData.brand);
				 		
				 		html += addProductInfo(csp);
						
					}
					$("#main").html(html);
				 }
			}
		});
	}
}
//Original method
//function addProductInfo(csp){
//	
//	var html = '';
//	var type ;
//	for(var i = 0; i < csp.listSupplierBean.length ;i++){
//		var sb = csp.listSupplierBean[i];
//		type = sb.product_type_name;
//		html +='<div class="Block" style="width:42%;margin-bottom:5px;" id="'+sb.id+'" title="'+ sb.product_price +'" discount="'+sb.discount+'" limit="'+sb.product_limit_points+'" givePoints="'+ sb.give_points +'" onclick="choseProduct('+sb.id+')" >'+ sb.product_name +'</div>';
//	}		 
//	html +='<div class="clear"></div></div>';
//	
////	html = '<label class="bderror" style="text-align: center;color: black;">'+type+'&nbsp;&nbsp;&nbsp;&nbsp;  </label><div class="box ng-scope" >'
////			 + html;
//	return html;
//}

//在商品展示出来后，直接计算优惠后的价格
function directcalculation(pid,title,discount,limit){
	var user_points =  new Number($("#user_points").html());
	var direct = title/100;
	return direct;

	
	
}


function addProductInfo(csp){
	var html = '';
	var type ;
	for(var i = 0; i < csp.listSupplierBean.length ;i++){
		var sb = csp.listSupplierBean[i];
		type = sb.product_type_name;
		var pid =sb.id;
		var title= sb.product_price;
		var discount=sb.discount;
		var limit=sb.product_limit_points;
		var givePoints=sb.give_points;
	    var direct=	directcalculation(pid,title,discount,limit);
		html +='<div class="Block" style="height: auto;width:45%;" id="'+sb.id+'" title="'+ sb.product_price +'" realprice="'+sb.product_real_price +'" discount="'+sb.discount+'" limit="'+sb.product_limit_points+'" givePoints="'+ sb.give_points+'" offset="'+sb.product_offset_points+'"  ';
        html +='onclick="choseProduct('+sb.id+')" ><div  style="float:right;font-size:10px;color:#919191;" >月底清零</div><div  style="float:left;font-size:10px;	color:#000000" >'+sb.product_type_name+'</div><br><div style="font-size: 14px;">'+ sb.product_name +'</div><br> ';
        html +='</div>';
//      html +='onclick="choseProduct('+sb.id+')" ><div  style="float:right;font-size:10px;color:#919191;" >月底清零</div><br><div  style="float:left;font-size:10px;	color:#000000" >'+sb.product_type_name+'</div></br><div style="font-size: 16px;">'+ sb.product_name +'</div><div style="border-top:1px dashed #cccccc;height: 10px;overflow:hidden;margin-top:5% auto "></div> ';
//      html +='<div style="float:left;color:#919191;text-decoration:line-through">原价:'+sb.product_real_price/100+'元</div><div  style="float:right;font-weight: 900;color:red;font-size: 16px;margin-top:auto" >￥'+direct+'元</div></div>';
	}
	html +='<div class="clear"></div></div>';
	
//	html = '<label class="bderror" style="text-align: center;color: black;">'+type+'&nbsp;&nbsp;&nbsp;&nbsp;  </label><div class="box ng-scope" >'
//			 + html;
	return html;
}

function payInfo(){
	var phone = $("#phone").val();
	var spid = $("#product_id").val();
	
	if(phone.length != 11 ){
		alert('请正确填写手机号！');
		return;
	}
	if(spid == '' || phone =='' ){ 
		alert('请先选择商品！');
		return;
	}
	
	var openid = $("#openid").val();
	location.href = "user/toOrderConfirm.do?phone="+phone+"&spid="+spid+"&openid="+openid;
	
	
}

function historyPhone(){
	$("#cognzhagnhul").empty();
	$.ajax({
		url : 'user/UserHistoryPhoneLastTen.do',
		data : {
			"openid":$("#openid").val(),
		},
		type : 'POST',
		success : function(data) {
			if(data == null || data == ''){
			 	alert('很抱歉，您还没有订购过哦!');
			 }else{
					$(".tfrom-mask").css("height",document.body.scrollHeight+"px").css("width",document.body.scrollWidth+"px").css("display","block");
					var result = eval("(" + data + ")");
					if(0<=result.length<=5){
						$(".tfrom").css("margin-top","40%");
					} 
					if(result.length==6){					
						$(".tfrom").css("margin-top","22%");
					}
					if(result.length==7){					
						$(".tfrom").css("margin-top","17%");
					}
					if(result.length==8){					
						$(".tfrom").css("margin-top","12%");
					}
					if(result.length==9){					
						$(".tfrom").css("margin-top","7%");
					}
					if(result.length==10){					
						$(".tfrom").css("margin-top","3%");
					}
					$(".tfrom").css("display","block");
						for(var i = 0 ; i < result.length; i++){
						   var phone =  $("#phone").val();
							var account = result[i];
							$("#cognzhagnhul").append("<li id='"+account.order_account+"'><label style=' width:100%; display:block;'><input type='text' style='display:none' onclick='chosePhone("+account.order_account+","+i+")' \>"+account.order_account+"</label></li>");
							   if(phone==account.order_account ){
									$("#"+account.order_account).css("background","#d8ebf8");
							   }
					}
					
			 }
			
		}
	});
	
}

function chosePhone(phone,number){
	$(".tfrom").css("display","none");
	$(".tfrom-mask").css("display","none");
    $("#phone").val(phone);
	checkPhone();


}


function closePop(){
	$(".tfrom").css("display","none");
	$(".tfrom-mask").css("display","none");
}

