function changeTips(img){
	
	var dis = $("#tips").css("display");
	
	if(dis == 'block'){
		$("#tips").css("display","none");
		img.src = "images/icon/downjt.png";
	}else{
		$("#tips").css("display","block");
		img.src = "images/icon/upjt.png";
	}
}

function choseProduct(pid){
	
	var oldid = $("#product_id").val();
	
	if(oldid != '' ){ //旧的样式移除
		$("#"+oldid).css("color","#686868");
	}
	
	$("#product_id").val(pid);
	$("#"+pid).css("color","red");
	
	var num = $("#"+pid).attr("title");
//	var price = $("#"+pid).attr("title");
//	$("#text_price").html(price/100);
//	$("#discount_price").html(price/100 * 0.95);
//	$("#product_price").val(price);
	$("#text_store_num").html(num);
	
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
				 	alert('很抱歉，此手机号暂时不支持订购商品！');
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

function addProductInfo(csp){
	
	var html = '';
	var type ;
	for(var i = 0; i < csp.listSupplierBean.length ;i++){
		var sb = csp.listSupplierBean[i];
		type = sb.product_type_name;
		html +='<div class="Block" id="'+sb.id+'" title="'+ sb.store_num +'" onclick="choseProduct('+sb.id+')" >'+ sb.product_name +'</div>';
	}		 
	html +='<div class="clear"></div></div>';
	
	html = '<label class="bderror" style="text-align: center;color: black;">'+type+'&nbsp;&nbsp;&nbsp;&nbsp;  货源：'+csp.supplierBean.parent_name+'</label><div class="box ng-scope" >'
			 + html;
	return html;
}

function payInfo(){
	var phone = $("#phone").val();
	var spid = $("#product_id").val();
	var adminid = $("#adminid").val();
	if(spid == '' || phone =='' || phone.length != 11){ 
		alert('请先填写手机号并选择商品！');
		return;
	}
	
	var num = $("#text_store_num").html();
	if(num == 0 || num == '0'){
		//去往定单确认界面
		location.href = 'supplierProduct/toOrderConfirm.do?phone='+phone +'&spid='+spid+'&adminid='+adminid;
		
	}else{
		//去订购，并返回结果界面
		location.href = 'order/supplierOrderProductByStoreNum.do?phone='+phone +'&spid='+spid+'&adminid='+adminid;
	}
	
	
	
	
	
	
}