
function commitData(item){
	code=$("input[name=cron_code]").val();
	name=$("input[name=cron_name]").val();
	desc=$("input[name=cron_desc]").val();
	class_name=$("input[name=cron_class_name]").val();
	expression=$("input[name=cron_expression]").val();
	status=$("select[name=cron_status]").val();
	description=$("input[name=cron_description]").val();
	enable=$("select[name=cron_enable]").val();


	if(name == null || name == ""){
		alert("定时器名称,请输入定时器名称");
		return;
	}
	if(desc == null || desc == ""){
		alert("定时器描述不能为空,请输入定时器描述");
		return;
	}
	if(class_name == null || class_name == ""){
		alert("定时器方法名不能为空,请输入定时器方法名");
		return;
	}
	if(expression == null || expression == ""){
		alert("定时器执行时间不能为空,请输入定时器执行时间");
		return;
	}
	if(status == null || status == ""){
		alert("定时器执行状态不能为空,请输入定时器执行状态");
		return;
	}else{
		if(isNaN(status)){
			alert("执行状态，请输入数字");
			return;
		}
	}
	if(description == null || description == ""){
		alert("定时器执行结果不能为空,请输入定时器执行结果");
		return;
	}
	if(enable == null || enable == ""){
		alert("定时器开关不能为空,请输入定时器开关");
		return;
	}else{
		if(isNaN(enable)){
			alert("定时器开关,请输入数字");
			return;
		}
	}
	//移除原先的编辑框
	$(".cron").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(".cron").focus(function(){
		console.log($(this).closest(".form-group"));
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
		}
	});
	
	var id = $("input[name=cron_id]").val();
	$.ajax({
		type : "POST",
		url : "../../admin/other/taskSchedulerController/editTaskCron.do",
		data : {
			"id" : id,
			"code":code,
			"name":name,
			"desc":desc,
			"class_name":class_name,
			"expression":expression,
			"status":status,
			"description":description,
			"enable":enable
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
	var body_list = $("#supplier_list").children()[0];
	//克隆对象
	itemDemo = $.extend(true, {}, body_list);
	$(body_list).children("td").each(function(n,e){
		var class_name = $(e)[0].className;
		var cron_name = class_name.substr(11,class_name.length);
		if($(e).children().length > 0){
			$(e).children("input").val(result[cron_name]);
			$("select[name="+cron_name+"]").val($(e).children("input").val());
		}else{
			$(e).text(result[cron_name]);
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
function editAdvertising(item){
	var blogList = document.getElementById("myModal");
	console.log($(item).offset().top);  
	blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
	//移除原先的编辑框
	var value = "";
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(11,class_name.length);
		var item_name = "cron_"+class_name;
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
			url : "../../admin/other/taskSchedulerController/deleteTaskCron.do",
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