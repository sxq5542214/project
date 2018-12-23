var flag_text = "item-property";
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

//0.初始化fileinput
var oFileInput = new FileInput();
oFileInput.Init("upload-img", "admin/fileupload/uploadImgToImgServer.do","activity_img_url",1);
oFileInput.Init("upload-img-list", "admin/fileupload/uploadImgToImgServer.do","show_list_img_url",1);
oFileInput.Init("upload-img-description", "admin/fileupload/uploadImgToImgServer.do","description_img_url",1);

$(".activity_config_infos").children().hide();
/**
 * 跳转选项变更处理
 */
$("body").on("change","#form_activity_activity_jump_url_code",function(){
	$("#form_activity_activity_jump_url").val("");
});

/**
 * 自定义跳转的改变事件
 */
$("#form_activity_activity_jump_url").change(function() {
	$("#form_activity_activity_jump_url_code").val("");
});

/**
 * 跳转选项变更处理
 */
$("body").on("change","#form_activity_activity_img_jump_url_code",function(){
	$("#form_activity_activity_img_jump_url").val("");
});

/**
 * 自定义跳转的改变事件
 */
$("#form_activity_activity_img_jump_url").change(function() {
	$("#form_activity_activity_img_jump_url_code").val("");
});

/**
 * 获取默认数据填充新增框信息
 */
function getDefaultData(){
	//移除编辑框数据
	$("#config_comment_form").find("input").val(null);
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

$(item_class).focus(function(){
	if($(this).closest(".form-group1").hasClass("has-error")){
		$(this).closest(".form-group1").removeClass("has-error");
	}
});

/**
 * 编辑内容，获取内容形成编辑结构
 * @param item
 */
function editActivityConfig(id){
	if(id == null){
		action = "ADD";
	}else{
		action = "EDIT";
	}
	//异步获取活动配置信息，以供编辑
	$.ajax({
		type : "POST",
		url : "admin/activity/getDetailActivityConfigInfoForAjax.do",
		data : {"id":id},
		success : function(data){
			//创建结构树
			$expandibleTree = $('#treeview-expandible').treeview(
					{
						color : "#428bca",
						expandIcon : 'glyphicon glyphicon-chevron-right',
						collapseIcon : 'glyphicon glyphicon-chevron-down',
						emptyIcon : 'glyphicon glyphicon-tag',
						data : "["+data+"]",
						//节点关闭事件
						onNodeCollapsed : function(event, node) {
//							console.log(node.text+"已关闭");
						},
						//节点展开事件
						onNodeExpanded : function(event, node) {
//							console.log(node.text+"已展开");
						},
						//节点选择事件
						onNodeSelected: function(event, node) {
							if($("#submit-form").hasClass("btn-success")){
								$("#submit-form").removeClass("btn-success");
								$("#submit-form").addClass("btn-default");
								$("#submit-form").addClass("disabled");
							}
							if(node.change != null && node.change){
								if($("#submit-form").hasClass("btn-default")){
									$("#submit-form").removeClass("btn-default");
									$("#submit-form").addClass("btn-success");
									$("#submit-form").removeClass("disabled");
								}
							}
							getParentNode(node);
							$("#info-title").text(node.text);
//							getMenuInfo(node);
							selectNode = node;
							var nodeClass = ".activity_config_info_"+node.code;
							$(".activity_config_infos").children().hide();
							$(".activity_config_infos").children(nodeClass).show();
							insertDataToForm(node);
							$(item_class).each(function(e,i){
								if($(i).val() == null || $(i).val() == ""){
									if(!$(i).closest(".form-group1").hasClass("has-error")){
										$(i).closest(".form-group1").addClass("has-error");
									}
								}
							});
				        },
				        //节点失去选择事件
				        onNodeUnselected: function (event, node) {
//				        	removeMenuInfo();
//				        	console.log(node.text+"失去选择");
				        }
					});
			$("#myModal-editConf").modal("show");
			var findSelectableNodes = function() {
		          return $expandibleTree.treeview('search', [ '基本信息', { ignoreCase: false, exactMatch: false } ]);
		        };
		        var selectableNodes = findSelectableNodes();
		        $expandibleTree.treeview('selectNode', [ selectableNodes, { silent: $('#chk-select-silent').is(':checked') }]);
		}
	});
}

function insertDataToForm(node){
	//添加基本信息（baseInfo）
	if(node.code == "baseinfo"){
		$(".activity_config_info_baseinfo").find(".form-control-commit").val("");
		//获取基本信息表单，填入基本信息
		$(".activity_config_info_baseinfo").find(".form-control-commit").each(function(e,n){
			if(node.detailInfo[$(n).attr("name")] != null){
				$(n).val(node.detailInfo[$(n).attr("name")]);
			}
			if($(n).attr("name") == "activity_img" || $(n).attr("name") == "description_img" || $(n).attr("name") == "show_list_img"){
				$(n).attr("data-content","<img style='width:160px;height:80px' src='"+node.detailInfo[$(n).attr("name")]+"'>");
			}
		});
	}else if(node.code == "param"){
		var paramDemo = $(".dt_example_param");
		tableHeader.property = ["remark","param_sql","status","type"];
		tableHeader.title = ["描述","条件sql","状态","类别"];
		tableHeader.width = ["100","120","70","70"];
		var url = "admin/activity/deleteActivityParam.do";
		addTableToInfo(paramDemo,tableHeader,"data-table-param",node,"myModal-param","form-param",url);
	}else if(node.code == "rule"){
		var paramDemo = $(".dt_example_rule");
		tableHeader.property = ["description","seq"];
		tableHeader.title = ["规则描述","排序"];
		tableHeader.width = ["200","70"];
		var url = "admin/activity/deleteActivityRule.do";
		addTableToInfo(paramDemo,tableHeader,"data-table-rule",node,"myModal-rule","form-rule",url);
	}
	else if(node.code == "prize"){
		var paramDemo = $(".dt_example_prize");
		tableHeader.property = ["product_name","real_num","show_num","eff_time","dff_time","weight","weight_type"];
		tableHeader.title = ["商品名称","实际数量","展示数量","生效时间","失效时间","权重","权重类型"];
		tableHeader.width = ["200","60","60","80","80","42","60"];
		var url = "admin/activity/deleteActivityPrize.do";
		addTableToInfo(paramDemo,tableHeader,"data-table-prize",node,"myModal-prize","form-prize",url);
	}
	else if(node.code == "original"){
		var paramDemo = $(".dt_example_original");
		tableHeader.property = ["original_name","status","remark"];
		tableHeader.title = ["公众号","状态","备注"];
		tableHeader.width = ["200","70","70"];
		var url = "admin/activity/deleteActivityOringinal.do";
		addTableToInfo(paramDemo,tableHeader,"data-table-original",node,"myModal-original","form-original",url);
	}
}

function addTableToInfo(infoDemo,tableHeader,tableid,node,type,formId,url){
	if(node.activity_id == null){
		alert("请先保存配置信息！");
		var findSelectableNodes = function() {
	          return $expandibleTree.treeview('search', [ '基本信息', { ignoreCase: false, exactMatch: false } ]);
	        };
	        var selectableNodes = findSelectableNodes();
	        $expandibleTree.treeview('selectNode', [ selectableNodes, { silent: $('#chk-select-silent').is(':checked') }]);
	}
	var paramDemo = infoDemo;
	paramDemo.empty();
	var param_content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="'+tableid+'">';
	param_content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="checkAll(this);" /></th>';
	for(var i=0;i<tableHeader.title.length;i++){
		param_content += '<th style="width:'+tableHeader.width[i]+'px;">'+tableHeader.title[i]+'</th>';
	}
	param_content += '<th style="width:60px;">操作</th>';
	param_content += '</tr></thead><tbody id="data-table-param-body">';
	if(node.detailInfo != null && node.detailInfo.length > 0){
		for(var i=0;i<node.detailInfo.length;i++){
			var obj = node.detailInfo[i];
			param_content += '<tr class="gradeA "><td>';
			param_content += '<input type="hidden" class="item-property_table_status" value="'+obj.status+'" />';
			param_content += '<input type="hidden" class="item-property_table_activity_id" value="'+obj.activity_id+'" />';
			param_content += '<input type="hidden" class="item-property_table_type" value="'+obj.type+'" />';
			param_content += '<input type="hidden" class="item-property_table_prize_id" value="'+obj.prize_id+'" />';
			param_content += '<input type="hidden" class="item-property_table_original_id" value="'+obj.original_id+'" />';
			param_content += '<input type="hidden" class="item-property_table_weight_type" value="'+obj.weight_type+'" />';
			param_content += '<input type="checkbox" name="'+flag_text+'" class="item-property_table_id" value="'+obj.id+'" /></td>';
			for(var j=0;j<tableHeader.property.length;j++){
				if(tableHeader.property[j] == "status"){
					param_content += '<td class="item-property_table_'+tableHeader.property[j]+'">'+Util.toStatus(obj[tableHeader.property[j]])+'</td>';
				}else if(tableHeader.property[j] == "type"){
					if(obj[tableHeader.property[j]] == "1"){
						param_content += '<td class="item-property_table_'+tableHeader.property[j]+'">列表过滤</td>';
					}else if(obj[tableHeader.property[j]] == "0"){
						param_content += '<td class="item-property_table_'+tableHeader.property[j]+'">参与过滤</td>';
					}
				}else if(tableHeader.property[j] == "weight_type"){
					if(obj[tableHeader.property[j]] == "1"){
						param_content += '<td class="item-property_table_'+tableHeader.property[j]+'">权重</td>';
					}else if(obj[tableHeader.property[j]] == "0"){
						param_content += '<td class="item-property_table_'+tableHeader.property[j]+'">比例</td>';
					}
				}
				else{
					param_content += '<td class="item-property_table_'+tableHeader.property[j]+'">'+Util.toStr(obj[tableHeader.property[j]])+'</td>';
				}
			}			
			param_content += '<td><div class="btn-group"><button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>';
			param_content += '</button><ul class="dropdown-menu pull-right"><li><a name="delete-param" onclick="deleteParam(this,'+obj.id+',\''+url+'\');">删除</a></li>';
			param_content += '<li><a name="modify-param" onclick="editParam(this,\''+type+'\',\''+formId+'\');">编辑</a></li>';
			param_content += '</ul></div></td></tr>';
		}
	}
	param_content += '</tbody></table><div class="clearfix"></div>';
	paramDemo.append(param_content);
	var tableIdName = "#"+tableid;
	$(tableIdName).dataTable({
		"aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
		"aaSorting": [[1, "desc"]],
		"bPaginate":true,"iDisplayLength":10,"bFilter":true,
		"sPaginationType": "full_numbers",
	});
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
			var obj = eval("("+data+")");
			var modify_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
			if(paramAction == "add"){
				if(formId == "form-param"){
					addParamToNode(modify_node,obj);
				}else if(formId == "form-rule"){
					addRuleToNode(modify_node,obj);
				}else if(formId == "form-prize"){
					addPrizeToNode(modify_node,obj);
				}else if(formId == "form-original"){
					addOriginalToNode(modify_node,obj);
				}
			}else if(paramAction == "edit"){
				modifyRoleMenuRela(modify_node,obj,"edit");
			}else{
				modifyRoleMenuRela(modify_node,obj,"delete");
			}
			insertDataToForm(modify_node);
			$(modalId).modal("hide");
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
 * 向node添加权限关系信息
 */
function addParamToNode(modify_node,result){
	if(result != null){
			var rela = {
					id : result.id,
					activity_id : result.activity_id,
					param_sql : result.param_sql,
					remark : result.remark,
					type : result.type,
					status : result.status
			};
			if(modify_node.detailInfo == null){
				modify_node.detailInfo = new Array();
			}
			modify_node.detailInfo.push(rela);
	}
}
/**
 * 向node添加权限关系信息
 */
function addRuleToNode(modify_node,result){
	if(result != null){
		var rela = {
				id : result.id,
				activity_id : result.activity_id,
				description : result.description,
				seq : result.seq
		};
		if(modify_node.detailInfo == null){
			modify_node.detailInfo = new Array();
		}
		modify_node.detailInfo.push(rela);
	}
}
/**
 * 向node添加权限关系信息
 */
function addPrizeToNode(modify_node,result){
	if(result != null){
		var rela = {
				id : result.id,
				activity_id : result.activity_id,
				product_name : result.product_name,
				eff_time : result.eff_time,
				dff_time : result.dff_time,
				weight : result.weight,
				real_num : result.real_num,
				show_num : result.show_num,
				prize_id : result.prize_id,
				weight_type : result.weight_type
		};
		if(modify_node.detailInfo == null){
			modify_node.detailInfo = new Array();
		}
		modify_node.detailInfo.push(rela);
	}
}
/**
 * 向node添加权限关系信息
 */
function addOriginalToNode(modify_node,result){
	if(result != null){
		var rela = {
				id : result.id,
				activity_code : result.activity_code,
				original_id : result.original_id,
				original_name : result.original_name,
				status : result.status,
				remark : result.remark
		};
		if(modify_node.detailInfo == null){
			modify_node.detailInfo = new Array();
		}
		modify_node.detailInfo.push(rela);
	}
}

/**
 * 修改或移除node的权限关系数据
 */
function modifyRoleMenuRela(modify_node,result,action){
	if(result != null){
		var nodes = modify_node.detailInfo;
		for(var n=0;n<nodes.length;n++){
			if(nodes[n].id == result.id){
				if(action == "edit"){
					for(var p in result){
						nodes[n][p] = result[p];
					}
				}else if(action == "delete"){
					modify_node.detailInfo.remove(n);
				}
			}
		}
	}	
}

//实时变更node信息
$("#form_config_activity").find(".form-control-commit").on('keyup', function(e) {
	var modify_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
	modify_node.detailInfo[$(this).attr("name")] = $(this).val();
	$expandibleTree.treeview("modifyNode", [modify_node, modify_node]); 
});

/**
 * 表单内容变更记录
 */
$("#form_config_activity").find(".form-control-commit").change(function() {
	var changeNode = $expandibleTree.treeview('getNode', selectNode.nodeId);
	changeNode.change = true;
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

function deleteitem(item,type){
	if(confirm("确定删除数据？")){
		var id = $(item).closest(".gradeA").find("input[type=checkbox]").val();
		if(type == "param"){
			deleteParam(item,id,"admin/activity/deleteActivityParam.do");
		}else if(type == "rule"){
			deleteParam(item,id,"admin/activity/deleteActivityRule.do");
		}else if(type == "prize"){
			deleteParam(item,id,"admin/activity/deleteActivitPrize.do");
		}else if(type == "original"){
			deleteParam(item,id,"admin/activity/deleteActivityOriginal.do");
		}
		
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
					var modify_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
					var nodes = modify_node.detailInfo;
					var deleteNode = new Array();
					var arr = id.split(",");
					for(var n=0;n<nodes.length;n++){
						if(arr.contains(nodes[n].id)){
							deleteNode.push(n);
						}
					}
					for(var m in deleteNode){
						nodes.pop(m);
					}
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
		}
	});
}

function deleteAllitem(type){
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
  		deleteParam(null,valuelist,type);
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

var parentNode;
/**
 * 选择节点，添加导航信息
 */
function getParentNode(node){
	parentNode = $expandibleTree.treeview('getParent', node);
	if(parentNode != null && (parentNode.parentid != null && parentNode.parentid != "")){
		naviDemo.push("<li><a href='javascript:return false;' class='selectNode' ><input class='nodeid' type='hidden' value='"+parentNode.nodeId+"' />"+parentNode.text+"</a></li>");
		parentNode = getParentNode(parentNode);
	}else{
		if(node.parentid != null && node.parentid != ""){
			naviDemo.push("<li><a href='javascript:return false;' class='selectNode'><input class='nodeid' type='hidden' value='"+parentNode.nodeId+"' />"+parentNode.text+"</a></li>");
		}
	}
}

/**
 * 提交配置信息
 */
function commitActivityInfo(){
	var flag = true;
	$(item_class).each(function(e,i){
		if($(i).val() == null || $(i).val() == ""){
			if(!$(i).closest(".form-group1").hasClass("has-error")){
				$(i).closest(".form-group1").addClass("has-error");
			}
		}
	});
	formId = "#form_config_activity";
	inputClass = ".form-control";
	var jsonData ="{";
	//遍历属性值得到需要传递得数据
	$(formId).find(inputClass).each(function(n,item){
		var attr_name = $(item).attr("name");
		if(attr_name != "id" && ($(item).val() == "" || $(item).val() == null)){
			if($(item).hasClass(flag_text)){
				flag = false;
			}
		}
		jsonData = jsonData + "\""+attr_name+"\":\"" + $(item).val() + "\",";
	});
	//去除末尾逗号
	jsonData = jsonData.substr(0,jsonData.length-1);
	jsonData = jsonData + "}";
	
	if(!flag){
		alert("请将信息填写完整！");
		return false;
	}
	if(($("#form_activity_activity_jump_url_code").val() == "" || $("#form_activity_activity_jump_url_code").val() == null) && ($("#form_activity_activity_jump_url").val() == "" || $("#form_activity_activity_jump_url").val() == null)){
		alert("跳转链接和自定义选填一个！");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "admin/activity/commitActivityConfig.do",
		data : {"jsonData":jsonData},
		success : function(data){
			var obj = eval("("+data+")");
			if(action == "ADD"){
				editActivityConfig(obj.id);
			}else{
				$("#myModal-editConf").modal("hide");
				location.reload();
			}
		}
	});
}