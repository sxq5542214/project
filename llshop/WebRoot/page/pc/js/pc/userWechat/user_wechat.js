
function commitData(item){
	openid=$("input[name=wechatuser_openid]").val();
	nick_name=$("input[name=wechatuser_nick_name]").val();
	phone=$("input[name=wechatuser_phone]").val();
	sex=$("select[name=wechatuser_sex]").val();
	province=$("input[name=wechatuser_province]").val();
	city=$("input[name=wechatuser_city]").val();
	status=$("select[name=wechatuser_status]").val();
	parentid=$("input[name=wechatuser_parentid]").val();
	level=$("input[name=wechatuser_level]").val();
	offline_num=$("input[name=wechatuser_offline_num]").val();
	head_img=$("input[name=wechatuser_head_img]").val();
	points=$("input[name=wechatuser_points]").val();
	balance=$("input[name=wechatuser_balance]").val();
	share_type=$("input[name=wechatuser_share_type]").val();
	originalid=$("select[name=wechatuser_originalid]").val();
	type=$("input[name=wechatuser_type]").val();

	if(openid == null || openid == ""){
		alert("openid不能为空,请输入openid");
		return;
	}
	if(phone !=null || phone != "" ){
		if(isNaN(phone)){
			alert("号码请输入数字");
		}
	}
	if(sex == null || sex == ""){
		alert("请输入性别,性别不能为空");
	}
	if(status == null || status == ""){
		alert("请输入状态,状态不能为空");
	}
	if(offline_num == null || offline_num == ""){
		alert("请输入下线数量,下线数量不能为空");
	}
	if(points == null || points == ""){
		alert("请输入积分，积分不能为空");
	}
	if(balance == null || points == ""){
		alert("请输入余额，余额不能为空");
	}
	if(type == null || type == ""){
		alert("请输入用户类别，用户类别不能为空");
	}
	//移除原先的编辑框
	$(".wechatuser").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(".wechatuser").focus(function(){
		console.log($(this).closest(".form-group"));
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
		}
	});
	var id = $("input[name=wechatuser_id]").val();
	$.ajax({
		type : "POST",
		url : "../../admin/user/userWechatController/editWechatUser.do",
		data : {
			"id" : id,
			"openid" : openid,
			"nick_name" : nick_name,
			"phone" : phone,
			"sex" : sex,
			"province" : province,
			"city" : city,
			"status" : status,
			"parentid" : parentid,
			"level" : level,
			"offline_num" : offline_num,
			"head_img" : head_img,
			"points" : points,
			"balance" : balance,
			"share_type" : share_type,
			"originalid" : originalid,
			"type" : type
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
		var userwechat_name = class_name.substr(18,class_name.length);
		if($(e).children().length > 0){
			$(e).children("input").val(result[userwechat_name]);
			$("select[name="+userwechat_name+"]").val($(e).children("input").val());
		}else{
			$(e).text(result[userwechat_name]);
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
function editWechatUser(item){
	var blogList = document.getElementById("myModal");
	console.log($(item).offset().top);  
	blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
	//移除原先的编辑框
	var value = "";
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(17,class_name.length);
		var item_name = "wechatuser_"+class_name;
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
			url : "../../admin/user/userWechatController/deleteWechatUser.do",
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