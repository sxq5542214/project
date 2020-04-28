
function commitData(item){
	userid=$("input[name=couponrecord_userid]").val();
	supplier_id=$("input[name=couponrecord_supplier_id]").val();
	supplier_name=$("input[name=couponrecord_supplier_name]").val();
	coupon_id=$("input[name=couponrecord_coupon_id]").val();
	order_id=$("input[name=couponrecord_order_id]").val();
	order_code=$("input[name=couponrecord_order_code]").val();
	product_name=$("input[name=couponrecord_product_name]").val();
	create_time=$("input[name=couponrecord_create_time]").val();
	modify_time=$("input[name=couponrecord_modify_time]").val();
	use_time=$("input[name=couponrecord_use_time]").val();
	expire_time=$("input[name=couponrecord_expire_time]").val();
	status=$("input[name=couponrecord_status]").val();
	status_description=$("input[name=couponrecord_status_description]").val();
	remark=$("input[name=couponrecord_remark]").val();
	if(userid == null || userid == ""){
		alert("userid不能为空,请输入");
		return;
	}
	if(coupon_id == null || coupon_id == ""){
		alert("优惠卷id不能为空,请输入");
		return;
	}
	if(order_id == null || order_id == ""){
		alert("规则id不能为空,请输入");
		return;
	}
	if(status == null || status == ""){
		alert("状态不能为空,请输入");
		return;
	}
	//移除原先的编辑框
	$(".couponrecord").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(".couponrecord").focus(function(){
		console.log($(this).closest(".form-group"));
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
		}
	});
	var id = $("input[name=couponrecord_id]").val();
	$.ajax({
		type : "POST",
		url : "../../admin/supplier/supplierCouponController/editCouponRecord.do",
		data : {
			"id" : id,
			"userid":userid,
			"supplier_id":supplier_id,
			"supplier_name":supplier_name,
			"coupon_id":coupon_id,
			"order_id":order_id,
			"order_code":order_code,
			"product_name":product_name,
			"create_time":create_time,
			"modify_time":modify_time,
			"use_time":use_time,
			"expire_time":expire_time,
			"status":status,
			"status_description":status_description,
			"remark":remark
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
		alert(1);
		var class_name = $(e)[0].className;
		var couponrecord_name = class_name.substr(19,class_name.length);
		if($(e).children().length > 0){
			$(e).children("input").val(result[couponrecord_name]);
		}else{
			$(e).text(result[couponrecord_name]);
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
function editCouponRecord(item){
	var blogList = document.getElementById("myModal");
	console.log($(item).offset().top);  
	blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
	//移除原先的编辑框
	var value = "";
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(19,class_name.length);
		var item_name = "couponrecord_"+class_name;
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
			url : "../../admin/supplier/supplierCouponController/deleteCouponRecord.do",
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