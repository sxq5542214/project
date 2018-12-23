function changePointsBtn(btn){
	var bt = $('#closeBg');
	var bt2 = $('#btnBg');
	var cs = bt.attr('class');
	var points = new Number($("#points").html());
	var price = new Number($("#price").html());
		
	if(cs == 'pointclose'){
		bt.attr('class','pointopen');
		bt2.attr('class','btnopen');
		
		$("#price").html( price - points );
		$("#pointsFlag").val("true");
	}else{
		bt.attr('class','pointclose');
		bt2.attr('class','btnclose');
		
		$("#price").html( price + points);
		$("#pointsFlag").val("false");
	}
}


function pay(){

	var adminid = $("#adminid").val();
	var product_id = $("#product_id").val();
	var phone = $("#phone").val();
	var balanceFlag = $("#balanceFlag").val();
	
	//余额够
	if(balanceFlag == "true"){
		$.ajax({
			url : "order/createUnifiedOrder.do",
			data : {
					 adminid : adminid,
					 product_id : product_id,
					 phone : phone,
					 type : "SupplierBalance"
			},
			success : function(result) {
				showdiv();
				if(result == '' || result == null){
					alert('调用支付失败');
				}else{
					location.href = 'order/supplierOrderProductByBalance.do?out_trade_code='+result+'&adminid='+adminid;
				}
			}
		});
		hidediv();
		
	}else{
		//余额不够，得要支付
		
		$.ajax({
			url : "alipay/createOrderProductUnifiedOrder.do",
			data : {
					 adminid : adminid,
					 product_id : product_id,
					 phone : phone
			},
			success : function(result) {
				showdiv();
				if(result == '' || result == null){
					alert('调用支付失败');
				}else{
		
					location.href = 'alipay/toAliPayPage.do?out_no='+result;
				}
			}
		});
		hidediv();
	}

}

function showdiv() { 
 document.getElementById("show").style.display ="block";
 document.getElementById("loader").style.display  = "none";
}
function hidediv() {
 document.getElementById("show").style.display ='none';
 document.getElementById("loader").style.display  = "block";
}
