
function commitData(item){
	sign_month=$("input[name=usersign_sign_month]").val();
	sign_date=$("input[name=usersign_sign_date]").val();
	user_id=$("input[name=usersign_user_id]").val();
	sence_id=$("input[name=usersign_sence_id]").val();
	sign_count=$("input[name=usersign_sign_count]").val();
	serial_sign_count=$("input[name=usersign_serial_sign_count]").val();
	last_sign_date=$("input[name=usersign_last_sign_date]").val();
	last_points=$("input[name=usersign_last_points]").val();
	points_month=$("input[name=usersign_points_month]").val();

	if(sign_month == null || sign_month == ""){
		alert("签到月份不能为空,请输入签到月份");
		return;
	}
	if(user_id == null || user_id == ""){
		alert("用户id不能为空,请输入用户id");
		return;
	}
	//移除原先的编辑框
	$(".usersign").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(".usersign").focus(function(){
		console.log($(this).closest(".form-group"));
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
		}
	});
	var id = $("input[name=usersign_id]").val();
	$.ajax({
		type : "POST",
		url : "../../admin/user/userController/editUserSign.do",
		data : {
			"id" : id,
			"sign_month":sign_month,
			"sign_date":sign_date,
			"user_id":user_id,
			"sence_id":sence_id,
			"sign_count":sign_count,
			"serial_sign_count":serial_sign_count,
			"last_sign_date":last_sign_date,
			"last_points":last_points,
			"points_month":points_month

		},
		success : function(data) {
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				alert(result.remark);
				$('#myModal').modal('hide');
			}
		}
	});
}


/**
 * 编辑内容
 * @param item
 */
function editUserSign(item){
	var blogList = document.getElementById("myModal");
	console.log($(item).offset().top);  
	blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
	//移除原先的编辑框
	var value = "";
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(15,class_name.length);
		var item_name = "usersign_"+class_name;
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
			url : "../../admin/user/userController/deleteUserSign.do",
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