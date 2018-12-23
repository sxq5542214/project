var flag_text = "config_prize";
var item_class = "."+flag_text;
var table_class = flag_text+"_table";
var action = "";
var paramAction = "";
var editDemo;
//记录已选择的node信息
var selectNode;
var naviDemo = new Array();
var $expandibleTree;
var tableHeader = {
		property : [],
		width : [],
		title : [],
		type : ""
};
$('#accordion').dataTable({
    //checkbox不排序
     "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
     "aaSorting": [[0, "desc"]],
      "bPaginate":true,
      "iDisplayLength":10,
      "bFilter":true,
      "sPaginationType": "full_numbers"
    });

/**
 * 获取默认数据填充新增框信息
 */
function getDefaultData(){}

function commitData(item){
	$(item_class).each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group1").hasClass("has-error")){
				$(i).closest(".form-group1").addClass("has-error");
			}
		}
	});
	$(item_class).focus(function(){
		if($(this).closest(".form-group1").hasClass("has-error")){
			$(this).closest(".form-group1").removeClass("has-error");
		}
	});
	var flag = true;
	var jsonData ="{";
	//遍历属性值得到需要传递得数据
	$(item_class).each(function(n,item){
		var attr_name = $(item).attr("name");
		if(attr_name != flag_text+"_id" && ($(item).val() == "" || $(item).val() == null)){
			flag = false;
		}
		
		attr_name = attr_name.substr(flag_text.length+1,attr_name.length);
		jsonData = jsonData + "\""+attr_name+"\":\"" + $(item).val() + "\",";
	});
	if(!flag){
		alert("请将信息填写完整！");
		return false;
	}
	//去除末尾逗号
	jsonData = jsonData.substr(0,jsonData.length-1);
	jsonData = jsonData + "}";
	$.ajax({
		type : "POST",
		url : "admin/comment/creatOrUpdateCommentConfigBean.do",
		data : {
			"jsonData" : jsonData
		},
		success : function(data) {
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				if(result.remark != "" && result.remark != null){
					alert(result.remark);
				}else{
					alert("操作成功！");
					var id = result.id;
					if(action == "ADD"){
						addItemToList(result,null);
					}else{
						if(editDemo != null){
							addItemToList(result,editDemo);
						}
					}
					$('#myModal').modal('hide');
				}
			}
		}
	});
}



/**
 * 编辑内容，获取内容形成编辑结构
 * @param item
 */
function editActivityConfig(id){
	$("#form_config_prize").find("input").val(null);
	$("#myModal-editConf").modal("show");
}



$("body").on("change","#form_activity_activity_type",function(){
	//活动类型是红包的，total_money，single_max，single_min，outside_total_money，字段为不可编辑
	if($(this).val() == 1){
		$(".activity_config_info_baseinfo").find(".form-control-money").removeAttr("readonly");
	}else{
		$(".activity_config_info_baseinfo").find(".form-control-money").attr("readonly","true");
	}
});
$("body").on("click","#add-param",function(){
	paramAction = "add";
	$("#form-param").find("input").val(null);
	$("#form-param").find("textarea").val(null);
	$("#myModal-param").find("input[name=activity_id]").val(selectNode.activity_id);
	$("#myModal-param").modal("show");
});
$("body").on("click","#add-rule",function(){
	paramAction = "add";
	$("#form-rule").find("input").val(null);

	$("#myModal-rule").find("input[name=activity_id]").val(selectNode.activity_id);
	$("#myModal-rule").modal("show");
});
$("body").on("click","#add-prize",function(){
	paramAction = "add";
	$("#form-prize").find("input").val(null);

	$("#myModal-prize").find("input[name=activity_id]").val(selectNode.activity_id);
	$("#myModal-prize").modal("show");
});
$("body").on("click","#add-original",function(){
	paramAction = "add";
	$("#form-original").find("input").val(null);
	$("#myModal-original").find("input[name=activity_code]").val(selectNode.activity_code);
	$("#myModal-original").modal("show");
});

/**
 * 展开奖品的弹出框
 */
function showPrize(){
	$("#myModal-prizeList").modal("show");
}
function showOriginal(){
	$("#myModal-originalList").modal("show");
}

function choiceItem(item){
	var prize = $(item).closest(".gradeA").find("input[class=config_prize_table_id]").val();
	var prize_name = $(item).closest(".gradeA").find(".config_prize_table_name").text();
	$("#myModal-prize").find("input[name=prize_id]").val(prize);
	$("#myModal-prize").find("input[name=product_name]").val(prize_name);
	$("#myModal-prizeList").modal("hide");
}
function choiceOriginal(item){
	var original = $(item).closest(".gradeA").find("input[class=config_original_table_id]").val();
	var original_name = $(item).closest(".gradeA").find(".config_original_table_name").text();
	$("#myModal-original").find("input[name=original_id]").val(original);
	$("#myModal-original").find("input[name=original_name]").val(original_name);
	$("#myModal-originalList").modal("hide");
}

function submit_itemRela(url,modalId,formId){
	modalId = "#"+modalId;
	var jsonData = returnJson(formId,"form-control");
	$.ajax({
		type : "POST",
		url : url,
		data : {"jsonData":jsonData},
		success : function(data){
			var result = eval("("+data+")");
			$(modalId).modal("hide");
			 location.reload();
		}
	});
}



/**
 * 编辑规则
 * @param item
 */
function editParam(item,modalId,formId){
	formId = "#"+formId;
	modalId = "#"+modalId;
	paramAction = "edit";
	//移除编辑框数据
	$(formId).find("input").val(null);
	//移除原先的编辑框
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.replace("  sorting_1","");
		class_name = class_name.substr(table_class.length+1,class_name.length);
		var item_name = class_name;
		if($(e).children().length > 0){
			$(e).children().each(function(i,child){
				var childClassName = $(child)[0].className;
				if(childClassName.indexOf(table_class) >= 0){
					childClassName = childClassName.substr(table_class.length+1,childClassName.length);
					$(formId).find("input[name="+childClassName+"]").val($(child).val());
					$(formId).find("select[name="+childClassName+"]").val($(child).val());
				}
			});
		}else{
			$(formId).find("input[name="+item_name+"]").val($(e).text());
			$(formId).find("textarea[name="+item_name+"]").val($(e).text());
		}
	});
	$(modalId).modal("show");
}


/**
 * 表单内容变更记录
 */
$("#form_config_prize").find(".form-control-commit").change(function() {
	if($("#form-commit").hasClass("disabled")){
		$("#form-commit").removeClass("disabled");
	}
});

/**
 * 返回表单的数据成json格式
 * @param formId
 * @param inputClass
 * @returns {String}
 */
function returnJson(formId,inputClass){
	formId = "#"+formId;
	inputClass = "."+inputClass;
	var jsonData ="{";
	//遍历属性值得到需要传递得数据
	$(formId).find(inputClass).each(function(n,item){
		
		var attr_name = $(item).attr("name");

		if(attr_name != "id" && ($(item).val() == "" || $(item).val() == null)){
			flag = false;
		}
		jsonData = jsonData + "\""+attr_name+"\":\"" + $(item).val() + "\",";
	});
	//去除末尾逗号
	jsonData = jsonData.substr(0,jsonData.length-1);
	jsonData = jsonData + "}";
	return jsonData;
}

function deleteitem(item){
	if(confirm("确定删除数据？")){
		var id = $(item).closest(".gradeA").find("input[type=checkbox]").val();
		deleteParam(item,id,"admin/prize/deleteActivityPrize.do");
	}
}

Array.prototype.contains = function ( needle ) {
	  for (i in this) {
	    if (this[i] == needle) return true;
	  }
	  return false;
	};

function deleteParam(item,id,url){
	paramAction = "delete";
	$.ajax({
		type : "POST",
		url : url,
		data : {
			"ids" : id
		},
		success : function(data) {
			if (data != null && data != "") {
				if(data == "SUCCESS"){
					if(item != null){
						$(item).closest(".gradeA").remove();
					}
					$("input[class^='config_prize_table_id']").each(function() {
				  		if (this.checked) {
				           $(this).closest(".gradeA").remove();
				        }
				  	});
				}else{
					alert(data);
				}
			}
		}
	});
}

function deleteAllitem(){
  	var valuelist = "";
  	$("input[class='"+table_class+"_id']").each(function() {
  		if (this.checked) {
            valuelist += $(this).val() + ",";
        }
  	});
  	if(valuelist == null || valuelist == ""){
  		alert("请至少勾选一条数据！");
  		return false;
  	}
  	if(confirm("是否确定全部删除！")){
  		deleteParam(null,valuelist,"admin/prize/deleteActivityPrize.do");
  	}
  }

//新增完成后  展示新增结果
function addItemToList(result,editDemo){
	//获得列表的第一个元素，已这个元素的格式填充数据
	var body_list = $("#supplier_list").children()[0];
	if(editDemo != null){
		body_list = $(editDemo).closest(".gradeA");
	}
	var status_value="";
	var category_value="";
	var dicMap = result.dicMap;
	var dic_List = dicMap["status"];
	if(dic_List != null && dic_List != ""){
		for(var i=0;i<dic_List.length;i++){
			if(dic_List[i]["value"] == result["status"]){
				status_value = dic_List[i]["description"];
			}
		}
	}
	dic_List = dicMap["category"];
	if(dic_List != null && dic_List != ""){
		for(var i=0;i<dic_List.length;i++){
			if(dic_List[i]["value"] == result["category"]){
				category_value = dic_List[i]["description"];
			}
		}
	}
	var tr_body = '<tr class="gradeA"><td class="config_prize_table_id ">'+
	'<input type="checkbox" class="config_prize_table_id" value="'+result.id+'">'+
	'<input type="hidden" class="config_prize_table_status" value="'+result.status+'">'+
	'<input type="hidden" class="config_prize_table_category" value="'+result.category+'">'+
	'<input type="hidden" class="config_prize_table_prize_img_url" value="'+result.prize_img_url+'">'+
	'</td>'+
	'<td class="config_prize_table_prize_name  sorting_1">'+result.prize_name+'</td>'+
	'<td class="config_prize_table_category_value ">'+category_value+'</td>'+
	'<td class="config_prize_table_remain_num ">'+result.remain_num+'</td>'+
	'<td class="config_prize_table_remark ">'+result.remark+'</td>'+
	'<td class="config_prize_table_status_value ">'+status_value+'</td>'+
	'<td class=" "><a class="btn btn-success " style="padding: 0px 4px;margin-left:5px;" onclick="editParam(this,"myModal-editConf","form_config_prize");">'+
	'<i class="fa"></i>修改</a>'+
	'<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">'+
	'<i class="fa"></i>删除</a></td></tr>';
	$("#supplier_list").prepend(tr_body);
}

function toPage(n){
	$('#nowpage').val(n);
	$('#conditionFrom').submit();
}

function checkAll(obj){
	var checked = $(obj).prop('checked');
	$('input[type=checkbox]').prop('checked',checked);
}
$('#myModal').on('hidden.bs.modal', function () {
	$(".config_comment").each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if($(i).closest(".form-group1").hasClass("has-error")){
				$(i).closest(".form-group1").removeClass("has-error");
			}
		}
	});
	});
function getNowDate(){
	var myDate = new Date();
	return myDate.getFullYear()+"-"+(myDate.getMonth()*1+1)+"-"+myDate.getDate()+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
}


function commitActivityInfo(){
	var jsonData = returnJson("form_config_activity","form-control");
	$.ajax({
		type : "POST",
		url : "admin/activity/commitActivityConfig.do",
		data : {"jsonData":jsonData},
		success : function(data){
			var obj = eval("("+data+")");
			editActivityConfig(obj.id);
		}
	});
	
	
}