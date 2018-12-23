
function commitData(item){
	originalid=$("input[name=wechatoriginalinfo_originalid]").val();
	original_name=$("input[name=wechatoriginalinfo_original_name]").val();
	method_name=$("input[name=wechatoriginalinfo_method_name]").val();
	appid=$("input[name=wechatoriginalinfo_appid]").val();
	secret=$("input[name=wechatoriginalinfo_secret]").val();
	server_domain=$("input[name=wechatoriginalinfo_server_domain]").val();
	weight=$("input[name=wechatoriginalinfo_weight]").val();
	access_token=$("input[name=wechatoriginalinfo_access_token]").val();
	expires_in=$("input[name=wechatoriginalinfo_expires_in]").val();
	token=$("input[name=wechatoriginalinfo_token]").val();
	mch_name=$("input[name=wechatoriginalinfo_mch_name]").val();
	mch_id=$("input[name=wechatoriginalinfo_mch_id]").val();
	pay_cert_file_path=$("input[name=wechatoriginalinfo_pay_cert_file_path]").val();
	server_url=$("input[name=wechatoriginalinfo_server_url]").val();
	jsapi_ticket=$("input[name=wechatoriginalinfo_jsapi_ticket]").val();
	pay_wechat_sign_key=$("input[name=wechatoriginalinfo_pay_wechat_sign_key]").val();

	//移除原先的编辑框
	$(".wechatoriginalinfo").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(".wechatoriginalinfo").focus(function(){
		console.log($(this).closest(".form-group"));
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
		}
	});
	var id =$("input[name=wechatoriginalinfo_id]").val();
	alert(id);
	$.ajax({
		type : "POST",
		url : "../../admin/wechat/wechatOriginalInfoCrontroller/editAdmimWechatOriginalInfo.do",
		data : {
			"id" : id,
			"originalid":originalid,
			"original_name":original_name,
			"method_name":method_name,
			"appid":appid,
			"secret":secret,
			"server_domain":server_domain,
			"weight":weight,
			"access_token":access_token,
			"expires_in":expires_in,
			"token":token,
			"mch_name":mch_name,
			"mch_id":mch_id,
			"pay_cert_file_path":pay_cert_file_path,
			"server_url":server_url,
			"jsapi_ticket":jsapi_ticket,
			"pay_wechat_sign_key":pay_wechat_sign_key,

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
		var wechatoriginalinfo_name = class_name.substr(25,class_name.length);
		if($(e).children().length > 0){
			$(e).children("input").val(result[wechatoriginalinfo_name]);
		}else{
			$(e).text(result[wechatoriginalinfo_name]);
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
function editDictionary(item){
	var blogList = document.getElementById("myModal");
	console.log($(item).offset().top);  
	blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
	//移除原先的编辑框
	var value = "";
	//获取编辑对象的内容填充编辑框					
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(25,class_name.length);
		var item_name = "wechatoriginalinfo_"+class_name;
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
			url : "../../admin/wechat/wechatOriginalInfoCrontroller/deleteAdmimWechatOriginalInfo.do",
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