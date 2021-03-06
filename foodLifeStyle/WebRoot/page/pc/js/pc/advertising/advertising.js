
//0.初始化fileinput
var oFileInput = new FileInput();
oFileInput.Init("upload-img", $("#config_attribute_value").val(),"advertising_picture",1);

function commitData(item){
	code=$("input[name=advertising_code]").val();
	type=$("input[name=advertising_type]").val();
	picture=$("input[name=advertising_picture]").val();
	picture_link=$("input[name=advertising_picture_link]").val();
	status=$("select[name=advertising_status]").val();
	seq=$("input[name=advertising_seq]").val();
	if(code == null || code == ""){
		alert("编码不能为空,请输入编码");
		return;
	}
	if(type == null || type == ""){
		alert("编码不能为空,请输入编码");
		return;
	}
	if(picture == null || picture == ""){
		alert("图片位置链接不能为空,请输入图片位置链接");
		return;
	}
	if(status == null || status == ""){
		alert("状态不能为空,请选择状态");
		return;
	}
	if(isNaN(seq)){
		alert("排序请输入数字");
		return;
	}
	//移除原先的编辑框
	$(".advertising").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(".advertising").focus(function(){
		console.log($(this).closest(".form-group"));
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
		}
	});
	var id = $("input[name=advertising_id]").val();
	$.ajax({
		type : "POST",
		url : "../../admin/other/advertisingController/editAdvertising.do",
		data : {
			"id" : id,
			"code":code,
			"type":type,
			"picture":picture,
			"picture_link":picture_link,
			"status":status,
			"seq":seq
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
		var advertising_name = class_name.substr(11,class_name.length);
		if($(e).children().length > 0){
			$(e).children("input").val(result[advertising_name]);
		}else{
			$(e).text(result[advertising_name]);
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
		class_name = class_name.substr(18,class_name.length);
		var item_name = "advertising_"+class_name;
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
	$("input[name=advertising_modify_time]").val(getNowDate());
}

function deleteitem(item){
	if(confirm("确定删除数据？")){
		var id = $(item).closest(".gradeA").find("input[type=checkbox]").val();
		$.ajax({
			type : "POST",
			url : "../../admin/other/advertisingController/deleteAdvertising.do",
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