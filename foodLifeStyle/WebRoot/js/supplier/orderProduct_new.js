function changeProduct(pid){
	
	var oldid = $("#product_id").val();
	
	if(oldid != '' ){ //旧的样式移除
		$("#"+oldid).css("border-color","#e1e1e1");
	}
	
	$("#product_id").val(pid);
	$("#"+pid).css("border-color","red");
	
	var price = $("#"+pid).attr("title");
	$("#text_price").html(price/100);
	$("#product_price").val(price);
	
	
}

function orderProduct(){
	
	var phone = $("#phone").val();
	
	
}