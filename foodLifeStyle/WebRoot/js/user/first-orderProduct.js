var first_discount = 0.4;
var low_discount = 0.6;
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
	var discount = $("#"+pid).attr("discount");
	var limit = $("#"+pid).attr("limit");
	var givePoints = $("#"+pid).attr("givePoints");
	givePoints = (givePoints/100).toFixed(2);
	limit = (limit/100).toFixed(2);
	var sub_points = limit;
	
	if(user_points < limit){
		sub_points = user_points;
	}
	
	$("#text_price").html(price/100);
	//重新计算优惠价
	$("#discount_price").html( (price/100 * discount / 100 - sub_points ).toFixed(2) );
	$("#product_price").val(price);
	var pricenow=(price/100 * discount / 100 - sub_points ).toFixed(2);
	var pricelow = (price/100*low_discount).toFixed(2);
	if(pricenow!=pricelow){
//		alert("亲,您当前积分账户"+user_points+"元,无法足额抵扣该商品价格的40%("+(price/100*first_discount).toFixed(2)+"元),抱歉暂时无法用最低价"+(price/100*low_discount).toFixed(2)+"元购买.\n分享此页面至朋友圈，可立即到账2元积分(可多次重复分享到账).");
		
		if( (pricenow - pricelow) >= 2){
			alert("亲,分享此页面至朋友圈，支付金额立减2元!最低减至6折！");
		}else{
   			alert("亲,分享此页面至朋友圈，支付金额立减"+(pricenow - pricelow).toFixed(2)+"元!");	
		}
		
	}
/*
	if ( user_points < limit ) 
	{
		$("#points").html("本商品可用"+limit+"元,您的积分不足,请点击上图邀请更多好友,获得更多积分享受最低折扣");
		$("#givePoints").html(" ");
	}
	else 
	{
		$("#points").html("本商品可使用"+limit+"元");
		$("#givePoints").html("成功购买后再返"+givePoints+"元积分，可用于下次消费");
	}*/
	
}

function checkPhone(){
 	$("#firstmain").remove();
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
				 	$("#firstmain").html('');
				 }else{
					var result = eval("(" + data + ")");
				 	var html = "";
				 	
					for(var i = 0 ; i < result.length; i++){
						var csp = result[i];
				 		$("#isp").html(csp.areaData.province + csp.areaData.brand);
				 		
				 		html += addProductInfo(csp);
						
					}
					$("#firstmain").html(html);
				 }
			}
		});
	}
}




//在商品展示出来后，直接计算优惠后的价格
function directcalculation(pid,title,discount,limit){
	var user_points =  new Number($("#user_points").val());
	var price = title;
	var discount = discount;
	var limit = limit;
	var givePoints = givePoints;
	givePoints = (givePoints/100).toFixed(2);
	limit = (limit/100).toFixed(2);
	var sub_points = limit;
	if(user_points < limit){
		sub_points = user_points;
	}
	//重新计算优惠价
	var direct = (price/100 * discount / 100 - sub_points ).toFixed(2);
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
		var limit= sb.product_price * first_discount;
		var givePoints=sb.give_points;
	    var direct=	directcalculation(pid,title,discount,limit);
	    var lowprice = (sb.product_price*low_discount/100).toFixed(2);
		html +='<div class="Block" style="height: auto;" id="'+sb.id+'" title="'+ sb.product_price +'" discount="'+sb.discount+'"limit="'+sb.product_price * first_discount +'" givePoints="'+ sb.give_points+'" onclick="choseProduct('+sb.id+')" > ';
        html +='<div  style="float:right;font-size:10px;color:#919191" >月底清零</div><div  style="float:left;font-size:10px;color:#000000" >'+sb.product_type_name+'</div></br>'+ sb.product_name +'</br><div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin-top:auto"></div> ';
        html +='<div style="font-size:10px;text-decoration:line-through;color:#919191;float:right;">原价:'+sb.product_price/100+'元</div></div>';

//        html +='<div  style="float:right;font-size:10px;color:#919191" >月底清零</div><div  style="float:left;font-size:10px;color:#000000" >'+sb.product_type_name+'</div></br>'+ sb.product_name +'</br><span style="font-size:10px;text-decoration:line-through;color:#919191;float:right;">原价:'+sb.product_price/100+'元</span></br><div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin-top:auto"></div> ';
//        html +='<div style="float:right;font-weight: 900;color:red;font-size:10px;"><span style="color:#919191;">最低:</span>'+lowprice+'元</div></div>';
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
	location.href = "user/toFirstOrderConfirm.do?phone="+phone+"&spid="+spid+"&openid="+openid;
	
	
}