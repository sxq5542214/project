
function commitData(item){
	merchant_id=$("input[name=couponconfig_merchant_id]").val();
	merchant_name=$("input[name=couponconfig_merchant_name]").val();
	code=$("input[name=couponconfig_code]").val();
	type=$("input[name=couponconfig_type]").val();
	coupon_name=$("input[name=couponconfig_coupon_name]").val();
	coupon_discount=$("input[name=couponconfig_coupon_discount]").val();
	coupon_offsetmoney=$("input[name=couponconfig_coupon_offsetmoney]").val();
	status=$("select[name=couponconfig_status]").val();
	number=$("input[name=couponconfig_number]").val();
	receive_limit_num=$("input[name=couponconfig_receive_limit_num]").val();
	coupon_count=$("input[name=couponconfig_coupon_count]").val();
	begin_time=$("input[name=couponconfig_begin_time]").val();
	end_time=$("input[name=couponconfig_end_time]").val();
	remark=$("input[name=couponconfig_remark]").val();
	coupon_backgroup=$("select[name=couponconfig_coupon_backgroup]").val();
	couponshow_product=$("input[name=couponconfig_couponshow_product]").val();
	if(merchant_id == null || merchant_id == ""){
		alert("商户名称不能为空,请输入商户名称");
		return;
	}
	if(coupon_discount == null || coupon_discount == ""){
		alert("优惠卷折扣不能为空,请输入优惠卷折扣");
		return;
	}
	if(coupon_offsetmoney == null || coupon_offsetmoney == ""){
		alert("优惠卷抵扣现金不能为空,请输入优惠卷抵扣现金");
		return;
	}
	if(status == null || status == ""){
		alert("状态不能为空,请输入优惠卷使用状态");
		return;
	}
	if(number == null || number == ""){
		alert("目前优惠卷数量不能为空,请输入目前优惠卷数量");
		return;
	}
	if(receive_limit_num == null || receive_limit_num == ""){
		alert("优惠卷领取限制不能为空,请输入优惠卷领取限制");
		return;
	}
	if(coupon_count == null || coupon_count == ""){
		alert("优惠卷总数不能为空,请输入优惠卷总数");
		return;
	}
	if(begin_time == null || begin_time == ""){
		alert("开始时间能为空,请输入开始时间");
		return;
	}
	if(end_time == null || end_time == ""){
		alert("结束时间不能为空,请输入结束时间");
		return;
	}
	//移除原先的编辑框
	$(".couponconfig").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(".couponconfig").focus(function(){
		console.log($(this).closest(".form-group"));
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
		}
	});
	var id = $("input[name=couponconfig_id_a]").val();
	$.ajax({
		type : "POST",
		url : "../../admin/supplier/supplierCouponController/editCouponConfig.do",
		data : {
			"id" : id,
			"merchant_id":merchant_id,
			"merchant_name":merchant_name,
			"code":code,
			"type":type,
			"coupon_name":coupon_name,
			"coupon_discount":coupon_discount,
			"coupon_offsetmoney":coupon_offsetmoney,
			"status":status,
			"number":number,
			"receive_limit_num":receive_limit_num,
			"coupon_count":coupon_count,
			"begin_time":begin_time,
			"end_time":end_time,
			"remark":remark,
			"coupon_backgroup":coupon_backgroup,
			"couponshow_product":couponshow_product
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
		var couponconfig_name = class_name.substr(19,class_name.length);
		if($(e).children().length > 0){
			$(e).children("input").val(result[couponconfig_name]);
			$("select[name="+couponconfig_name+"]").val($(e).children("input").val());
		}else{
			$(e).text(result[couponconfig_name]);
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
function editCouponConfig(item){
	var blogList = document.getElementById("myModal");
	console.log($(item).offset().top);  
	blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
	ajaxShowProduct($(item).closest(".gradeA").find(".table_couponconfig_id").text(),$(item).closest(".gradeA").find(".table_couponconfig_couponshow_product").text());
	//移除原先的编辑框
	var value = "";
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(19,class_name.length);
		var item_name = "couponconfig_"+class_name;
		if($(e).children().length > 0){
			$("input[name="+item_name+"]").val($(e).children("input").val());
			$("select[name="+item_name+"]").val($(e).children("input").val());
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
			url : "../../admin/supplier/supplierCouponController/deleteCouponConfig.do",
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


function ajaxShowProduct(id,couponshow_product){
	$.ajax({
		type : "POST",
		url : "../../admin/supplier/supplierCouponController/adminGetProductForAjax.do",
		data : {
			couponshow_product:couponshow_product
		},
		success : function(data) {
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				$("#couponproduct").children("#material_div").html(productTable(id,result));
			}else{
				
				$("#couponproduct").children("#material_div").html(productTable(id,null));
			}
		}
	});
}

//产品弹窗
function productTable(id,result){
	 var table_header = "<table class='table table-condensed table-striped table-hover table-bordered pull-left' id='data-table'>" +
		"<thead><tr><th width='10%'>商品id</th><th width='15%'>商品名称</th><th width='10%'>商品价格</th><th>商品品牌</th><th width='20%'>商品分类</th><th width='130px;'>操作</th></tr></thead>";
	  var table_body = "<tbody id='material_list'>";
	  if(result != null && result.length > 0){
		  table_body = table_body + addShowProduct(id,result);
	  }
	  table_body = table_body + "</tbody>";
	  var table = table_header + table_body + "</table>";
	  return table;
}

//增加产品展示
function addShowProduct(id,result){
	var body_item = "";
	if(result != null && result != "" && result.length > 0){
		for(var i = 0;i < result.length;i++){
			body_item += "<tr class='gradeA '><td class='no'>"+result[i].id+"</td>" +
  			"<td style='margin: 0 auto;text-align: left;'>"+result[i].product_name+"</td>" +
  			"<td>"+result[i].product_real_price+"</td>" +
		  	"<td>"+result[i].product_brand_name+"</td>" +
		    "<td>"+result[i].product_type_name+"</td>" +
		  	"<td><div class='btn-group'><input type='hidden' class='beanid' name='id' value='"+result[i].id+"'>" +
		  	"<button data-toggle='button' class='btn btn-danger btn-xs btn-del' onclick='deleteShowProduct("+id+","+result[i].id+",this)'>删除</button></div></td>" +
		  	"</tr>";
		}
	
	}
	return body_item;
	
	
}

function deleteShowProduct(id,show_product,item){
	if(confirm("确定删除数据?")){
		var id = id;
		var show_product = show_product;
		$.ajax({
			type : "POST",
			url : "../../admin/supplier/supplierCouponController/admindeleteShowProduct.do",
			data : {
				"id" : id,
				"show_product":show_product
			},
			success : function(data) {
				if (data != null && data != "") {
					alert(data);
					$(item).closest(".gradeA").remove();
				}
			}
		});
	}
	
}

function addProduct(){
	if(confirm("确定增加数据?")){
		var valuelist = "";
		//获取勾选的权限
		$("#data-table-admin").find("input[type=checkbox]").each(function() {
	  		if (this.checked) {
	            valuelist += $(this).val() + ",";
	        }
	  	});
	  	if(valuelist == null || valuelist == ""){
	  		alert("请至少勾选一条数据！");
	  		return false;
	  	}
	  	commitShowProduct(valuelist);
	}
}

function commitShowProduct(valuelist){
	$.ajax({
		type : "POST",
		url : "../../admin/supplier/supplierCouponController/adminAddShowProduct.do",
		data : {
			"ids" : $("input[name=couponconfig_id]").val(),
			"show_product":valuelist
		},
		success : function(data) {
			if (data != null && data != "") {
				alert(data);
				$("#myModal-product-list").modal("hide");
			}
		}
	});
	
}

function test(){
	$('#myModal-admin').modal('show');

}