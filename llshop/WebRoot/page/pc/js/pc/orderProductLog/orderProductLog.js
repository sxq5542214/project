
function commitData(item){
	supplier_id=$("input[name=orderproductlog_supplier_id]").val();
	supplier_name=$("input[name=orderproductlog_supplier_name]").val();
	supplier_product_id=$("input[name=orderproductlog_supplier_product_id]").val();
	product_name=$("input[name=orderproductlog_product_name]").val();
	product_price=$("input[name=orderproductlog_product_price]").val();
	order_code=$("input[name=orderproductlog_order_code]").val();
	order_account=$("input[name=orderproductlog_order_account]").val();
	cost_price=$("input[name=orderproductlog_cost_price]").val();
	cost_points=$("input[name=orderproductlog_cost_points]").val();
	cost_money=$("input[name=orderproductlog_cost_money]").val();
	cost_balance=$("input[name=orderproductlog_cost_balance]").val();
	user_id=$("input[name=orderproductlog_user_id]").val();
	status=$("input[name=orderproductlog_status]").val();
	remark=$("input[name=orderproductlog_remark]").val();
	channel_id=$("input[name=orderproductlog_channel_id]").val();
	event_type=$("input[name=orderproductlog_event_type]").val();
	lucky_money=$("input[name=orderproductlog_lucky_money]").val();
	is_sended=$("input[name=orderproductlog_is_sended]").val();
	if(supplier_id == null || supplier_id == ""){
		alert("商户id不能为空,请输入商户id");
		return;
	}
	if(supplier_product_id == null || supplier_product_id == ""){
		alert("产品id不能为空,请输入产品id");
		return;
	}
	if(product_price == null || product_price == ""){
		alert("产品价格不能为空,请输入产品价格");
		return;
	}
	if(cost_price == null || cost_price == ""){
		alert("花费总额不能为空,请输入花费总额");
		return;
	}
	if(cost_points == null || cost_points == ""){
		alert("花费积分不能为空,请输入花费积分");
		return;
	}
	if(cost_money == null || cost_money == ""){
		alert("花费现金不能为空,请输入花费现金");
		return;
	}
	if(cost_balance == null || cost_balance == ""){
		alert("花费余额不能为空,请输入花费余额");
		return;
	}
	if(user_id == null || user_id == ""){
		alert("类名不能为空,请输入类名");
		return;
	}
	if(order_code == null || order_code == ""){
		alert("属性不能为空,请输入属性");
		return;
	}
	if(status == null || status == ""){
		alert("状态不能为空,请输入状态");
		return;
	}
	if(event_type == null || event_type == ""){
		alert("事件类型不能为空,请输入事件类型");
		return;
	}
	//移除原先的编辑框
	$(".orderproductlog").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(".orderproductlog").focus(function(){
		console.log($(this).closest(".form-group"));
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
		}
	});
	var id = $("input[name=orderproductlog_id]").val();
	$.ajax({
		type : "POST",
		url : "../../admin/order/orderProductLogController/editOrderProductLog.do",
		data : {
			"id" : id,
			"supplier_id" : supplier_id,
			"supplier_name" : supplier_name,
			"supplier_product_id" : supplier_product_id,
			"product_name" : product_name,
			"product_price" : product_price,
			"order_code" : order_code,
			"order_account" : order_account,
			"cost_price" : cost_price,
			"cost_points" : cost_points,
			"cost_money" : cost_money,
			"cost_balance" : cost_balance,
			"user_id" : user_id,
			"status" : status,
			"remark" : remark,
			"channel_id" : channel_id,
			"event_type" : event_type,
			"lucky_money" : lucky_money,
			"is_sended" : is_sended
		},
		success : function(data) {
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				alert(result.remark);
				if(id == null || id == ""){
					addItemToList(result);
				}
				$('#myModal').modal('hide');
			}
		}
	});
}

//新增完成后  展示新增结果
function addItemToList(result){
	var itemDemo;
	//获得列表的第一个元素，已这个元素的格式填充数据
	var body_list = $("#supplier_list_head").children()[0];
	//克隆对象
	itemDemo = $.extend(true, {}, body_list);
	$(body_list).children("td").each(function(n,e){
		var class_name = $(e)[0].className;
		var orderproductlog_name = class_name.substr(21,class_name.length);
		if($(e).children().length > 0){
			$(e).children("input").val(result[orderproductlog_name]);
		}else{
			$(e).text(result[orderproductlog_name]);
		}
	});
	$(body_list).removeAttr("style");
	//在此添加隐藏样例
	$("#supplier_list").prepend($(itemDemo).prop("outerHTML"));
}

/**
 * 编辑内容
 * @param item
 */
function editOrderProductLog(item){
	var blogList = document.getElementById("myModal");
	console.log($(item).offset().top);  
	blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
	//移除原先的编辑框
	var value = "";
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(21,class_name.length);
		var item_name = "orderproductlog"+class_name;
		if($(e).children().length > 0){
			$("input[name="+item_name+"]").val($(e).children("input").val());
		}else{
			$("input[name="+item_name+"]").val($(e).text());
		}
		if(class_name == "value"){
			value = $(e).html();
		}
	});
}

function deleteitem(item){
	if(confirm("确定删除数据？")){
		var id = $(item).closest(".gradeA").find("input[type=checkbox]").val();
		$.ajax({
			type : "POST",
			url : "../../admin/order/orderProductLogController/deleteOrderProductLog.do",
			data : {
				"id" : id
			},
			success : function(data) {
				if (data != null && data != "") {
					var result = eval("(" + data + ")");
					alert(result.remark);
					$(item).closest(".gradeA").remove();
				}
			}
		});
	}
}

function toPage(n){
	$('#nowpage').val(n);
	$('#conditionFrom').submit();
}

function getNowDate(){
	var myDate = new Date();
	return myDate.getFullYear()+"-"+(myDate.getMonth()*1+1)+"-"+myDate.getDate()+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
//	myDate.getFullYear();    //获取完整的年份(4位,1970-????)
//	myDate.getMonth();       //获取当前月份(0-11,0代表1月)
//	myDate.getDate();        //获取当前日(1-31)
//	myDate.getDay();         //获取当前星期X(0-6,0代表星期天)
//	myDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
//	myDate.getHours();       //获取当前小时数(0-23)
//	myDate.getMinutes();     //获取当前分钟数(0-59)
//	myDate.getSeconds();     //获取当前秒数(0-59)
}