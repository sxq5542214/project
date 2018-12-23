var flag_text = "config_comment";
var item_class = "."+flag_text;
var table_class = flag_text+"_table";
var action = "";
var editDemo;
/**
 * 获取默认数据填充新增框信息
 */
function getDefaultData(){
	action = "ADD";
	$.ajax({
		type : "POST",
		url : "admin/comment/getDefaultCommentConfigForAjax.do",
		data : {},
		success : function(data) {
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				$(item_class).each(function(n,item){
					var attr_name = $(item).attr("name");
					attr_name = attr_name.substr(flag_text.length+1,attr_name.length);
					if(attr_name != "id"){
						$(item).val(result[0][attr_name]);
					}
				});
			}
		}
	});
}

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
 * 编辑内容
 * @param item
 */
function editConfigComment(item){
	editDemo = item;
	//移除编辑框数据
	$("#config_comment_form").find("input").val(null);
	//移除原先的编辑框
	var value = "";
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(table_class.length+1,class_name.length);
		var item_name = flag_text+"_"+class_name;
		if($(e).children().length > 0){
			$(e).children().each(function(i,child){
				var childClassName = $(child)[0].className;
				childClassName = flag_text+"_"+ childClassName.substr(table_class.length+1,childClassName.length);
				$("input[name="+childClassName+"]").val($(child).val());
			});
		}else{
			$("input[name="+item_name+"]").val($(e).text());
		}
	});
	$("input[name=config_comment_modify_time]").val(getNowDate());
	action = "EDIT";
}

function deleteitem(item){
	if(confirm("确定删除数据？")){
		var id = $(item).closest(".gradeA").find("input[type=checkbox]").val();
		deleteAjax(item,id);
	}
}

function deleteAjax(item,id){
	$.ajax({
		type : "POST",
		url : "admin/comment/deleteConfigCommentForAjax.do",
		data : {
			"id" : id
		},
		success : function(data) {
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				alert(result.remark);
				if(item != null){
					$(item).closest(".gradeA").remove();
				}
				$("input[name^='"+flag_text+"']").each(function() {
			  		if (this.checked) {
			           $(this).closest(".gradeA").remove();
			        }
			  	});
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
  		deleteAjax(null,valuelist);
  	}
  }

//新增完成后  展示新增结果
function addItemToList(result,editDemo){
	//获得列表的第一个元素，已这个元素的格式填充数据
	var body_list = $("#supplier_list").children()[0];
	if(editDemo != null){
		body_list = $(editDemo).closest(".gradeA");
	}
	var dicMap = result.dicMap;
	var itemDemo;
	//克隆对象
	itemDemo = $.extend(true, {}, body_list);
	$(body_list).children("td").each(function(n,e){
		//用于数据的展示
		if($(e).children("input").length > 0){
			$(e).children("input").each(function(m,d){
				var children_class_name = $(d)[0].className;
				var attribute_name = children_class_name.substr(table_class.length+1,children_class_name.length);
				$(d).val(result[attribute_name]);
				attribute_name = children_class_name.substr(table_class.length+1,children_class_name.length);
				var dic_List = dicMap[attribute_name];
				if(dic_List != null && dic_List != ""){
					for(var i=0;i<dic_List.length;i++){
						if(dic_List[i]["value"] == result[attribute_name]){
							var value_class = "."+children_class_name+"_value";
							$(d).closest(".gradeA").find(value_class).text(dic_List[i]["description"]);
						}
					}
				}
			});
		}else{
			var class_name = $(e)[0].className;
			var attribute_name = class_name.substr(table_class.length+1,class_name.length);
			if(attribute_name.indexOf("value")>0){
			}else{
				$(e).text(result[attribute_name]);
			}
		}
	});
	if(editDemo == null){
		$(body_list).removeAttr("style");
		$(body_list).next().attr("id","coll"+result.id);
		$(body_list).find(".detail-icon").attr("data-target","#coll"+result.id);
		$("#coll"+result.id).html("<td colspan='2'></td><td colspan='11'>" +
				"<p><b>每页显示:</b> <a>"+result.pageSize+"</a></p>" +
				"<p><b>跳转链接:</b> <a>"+result.link_url+"</a></p>" +
				"<p><b>重复留言:</b> <a>"+$(body_list).find(".config_comment_table_is_repeat_value").text()+"</a></p>" +
				"<p><b>投票期限:</b> <a>"+$(body_list).find(".config_comment_table_is_voted_expired_value").text()+"</a></p>" +
				"<p><b>排序规则:</b> <a>"+result.orderby+"</a></p>" +
				"<p><b>创建时间:</b> <a>"+result.create_time+"</a></p>" +
				"<p><b>修改时间:</b> <a>"+result.modify_time+"</a></p></td>");
		//在此添加隐藏样例
		$("#supplier_list").prepend($(itemDemo).prop("outerHTML"));
	}
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

/**
 * 加一
 */
function addNum(item){
	var num = $(item).siblings(".config_comment").val();
	if(!isNaN(num)){
		num = num*1 + 1;
		$(item).siblings(".config_comment").val(num);
	}
}
/**
 * 减一
 */
function minusNum(item){
	var num = $(item).siblings(".config_comment").val();
	if(!isNaN(num)){
		num = num*1 - 1;
		if(num <= 0){
			num = 0;
		}
		$(item).siblings(".config_comment").val(num);
	}
}