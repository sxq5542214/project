var ids = '';
var selectNode;
var flag_text = "control_role";
var item_class = "."+flag_text;
var table_class = flag_text+"_table";
var action = "";
var editDemo;
var admin_table = $('#admins-table').dataTable({
    //checkbox不排序
     "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
     "aaSorting": [[1, "desc"]],
      "bPaginate":true,
      "iDisplayLength":10,
      "bFilter":true,
      "sPaginationType": "full_numbers"
    });

function getItemListNotExist(){
	if(selectNode == null){
		alert("您还没有选择权限组");
	}else{
		var status = $(selectNode).find(".config_role_table_status").val();
		if(status != 1){
			alert("权限失效，无法关联菜单！");
			return false;
		}
	}
	var roleId = $(selectNode).find(".config_role_table_id").val();
	var menu = $('.example_alt_pagination_menu');
	menu.empty();
	var menu_content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table-menu">';
		menu_content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Sup.checkAll(this);" /></th><th style="width:120px;">菜单名称</th>';
		menu_content += '<th style="width:70px;">编码</th>';
		menu_content += '<th style="width:60px;">状态</th>';
		menu_content += '</tr></thead><tbody id="supplier_list">';
	$.post("admin/system/getItemListNotExist.do?role_id="+roleId,function(data){
		var listObj = eval('('+data+')');
//		var itemList = listObj.itemList;
		if(listObj.length == 0){
			alert("已关联所有菜单！");
			return false;
		}else{
			$('#myModal-menu').modal('show');
		}
		for ( var i = 0; i < listObj.length; i++) {
			var obj = listObj[i];
			menu_content += '<tr class="gradeA "><td><input type="checkbox" class="menu_id" value="'+obj.id+'" /><input class="menu_index_id" type="hidden" value="" /></td><td>'+obj.name+'</td><td>'+Util.toStr(obj.code)+'</td>';
			menu_content += '<td>'+obj.status_value+'</td></tr>';
		}
		menu_content += '</tbody></table><div class="clearfix"></div>';
		menu.append(menu_content);
		$('#data-table-menu').dataTable({
		    //checkbox不排序
		     "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
		     "aaSorting": [[1, "desc"]],
		      "bPaginate":true,
		      "iDisplayLength":10,
		      "bFilter":true,
		      "sPaginationType": "full_numbers"
		    });
	});
}

Sup = function(){
	return {
		udpateSupplierStatus:function(id,status,issale){
			$.post("../../supplier/admin/update.do",{"id":id,"status":status,"issale":issale},function(data){
				if(Util.empty(data)) {
					Sup.listSupplier();
					alert("更新成功");
				}else alert(data);
			});
		},
		listRefresh:function(){
			$('#data-table-role-menu').dataTable({
				"aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
		         "aaSorting": [[1, "desc"]],
				"bPaginate":true,"iDisplayLength":15,"bFilter":true,
		          "sPaginationType": "full_numbers",
		        });
			$('#data-table-role-admin').dataTable({
				"aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
				"aaSorting": [[1, "desc"]],
				"bPaginate":true,"iDisplayLength":15,"bFilter":true,
				"sPaginationType": "full_numbers",
			});
		},
		listSupplier:function(item){
			var role_menu = $('.dt_example_role_menu');
			var role_admin = $('.dt_example_role_admin');
			role_menu.empty();
			role_admin.empty();
			var menu_content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table-role-menu">';
				menu_content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Sup.checkAll(this);" /></th><th style="width:120px;">菜单名称</th>';
				menu_content += '<th style="width:70px;">状态</th>';
				menu_content += '<th style="width:60px;">操作</th>';
				menu_content += '</tr></thead><tbody id="supplier_list">';
			var admin_content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table-role-menu">';
				admin_content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Sup.checkAll(this);" /></th><th>用户名</th>';
				admin_content += '<th>状态</th>';
				admin_content += '<th style="width:60px;">操作</th>';
				admin_content += '</tr></thead><tbody id="supplier_list">';
				$.post("admin/system/getSystemRoleRelaForAjax.do?role_id="+item,function(data){
					var listObj = eval('('+data+')');
					var roleMenuList = listObj.roleMenuList;
					var roleAdminList = listObj.roleAdminList;
					for ( var i = 0; i < roleMenuList.length; i++) {
						var obj = roleMenuList[i];
						var status = obj.status;
						var status_value = "";
						if(status == 0){
							status_value = "eff";
						}else{
							status_value = "loseEff";
						}
						menu_content += '<tr class="gradeA "><td><input type="checkbox" class="table-rela-tr" value="'+obj.id+'" /></td><td>'+obj.menu_name+'</td><td>'+obj.status_value+'</td>';
						menu_content += '<td><div class="btn-group"><button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>';
						menu_content += '</button><ul class="dropdown-menu pull-right"><li><a name="delete-rela" onclick="dealItemRoleRale(this,\'menu\',\'signal\',\'delete\');">删除</a></li>';
						menu_content += '<li><a name="modify-rela-status" onclick="dealItemRoleRale(this,\'menu\',\'signal\',\''+status_value+'\');">'+(status==0?'启用':'暂停')+'</a></li></ul></div></td></tr>';
					}
					for ( var i = 0; i < roleAdminList.length; i++) {
						var obj = roleAdminList[i];
						var status = obj.status;
						var status_value = "";
						if(status == 0){
							status_value = "eff";
						}else{
							status_value = "loseEff";
						}
						
						admin_content += '<tr class="gradeA "><td><input type="checkbox" class="table-rela-tr" value="'+obj.id+'" /></td><td>'+obj.customer_name+'</td><td>'+Util.toYN(obj.status)+'</td>';
						admin_content += '<td><div class="btn-group"><button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>';
						admin_content += '</button><ul class="dropdown-menu pull-right"><li><a name="delete-rela" onclick="dealItemRoleRale(this,\'admin\',\'signal\',\'delete\');">删除</a></li>';
						admin_content += '<li><a name="modify-rela-status" onclick="dealItemRoleRale(this,\'admin\',\'signal\',\''+status_value+'\');">'+(status==0?'启用':'暂停')+'</a></li></ul></div></td></tr>';
					}
					menu_content += '</tbody></table><div class="clearfix"></div>';
					admin_content += '</tbody></table><div class="clearfix"></div>';
					role_menu.append(menu_content);
					role_admin.append(admin_content);
					Sup.listRefresh();//重新刷新一下表格分页
				});
		},
		insertSupplier:function(id){
			var params = {};
			var name = $('#name').val();
			var type = $('#type').val();
			var balance = $('#balance').val();
			var iscreate = $('#iscreate').val();
			var address = $('#address').val();
			var points = $('#points').val();
			var credit = $('#credit').val();
			var deposit_money = $('#deposit_money').val();
			var discount = '';
			var contacts_name = $('#contacts_name').val();
			var contacts_phone = $('#contacts_phone').val();
			var remark = $('#remark').val();
			$('#data-content input[type=checkbox]').each(function(){
				var obj =$(this);
				if(obj.prop('checked')) discount+=obj.prop('id');
			});
			if(Util.empty(name)) alert(' 请输入商户名称');
			else if(Util.empty(contacts_name)) alert('请输入管理员');
			else if(Util.empty(contacts_phone)) alert('请输入管理员号码');
			else if(Util.validPhone(contacts_phone)) alert("请输入正确的手机号码");
			else{
				params.name = name;
				params.type = type;
				params.address = address;
				params.balance = balance;
				params.points = points;
				params.deposit_money = deposit_money;
				params.contacts_name = contacts_name;
				params.contacts_phone = contacts_phone;
				params.remark = remark;
				params.disgroupid = discount;
				params.credit = credit;
				params.iscreate = iscreate;
				var postUrl = "../../supplier/admin/insert.do";
				if(Util.notEmpty(id)) {
					params.id = id;
					postUrl = "../../supplier/admin/update.do";
				}
				$.post(postUrl,params,function(data){
					if(Util.empty(data)) window.location.href = '../../admin/sup.do';
					else alert(data);
				});
			}
		},
		checkAll:function(obj){
			var checked = $(obj).prop('checked');
			$(obj).closest(".table").find('input[type=checkbox]').prop('checked',checked);
		},
		ids:function(){},
		loadDis:function(){},
		delSupplier:function(item,obj,type){
			if(confirm("确定删除权限，权限下的所有关联信息也会同时删除，是否继续？")){
				var postUrl = "admin/system/deleteRoleByIdForAjax.do";
				var params = {};
				params.ids = obj;
				$.post(postUrl,params,function(data){
					if(data == "SUCCESS"){
						alert("删除成功！");
						removeAllIds(item,"config_role_table_id",type);
					}else{
						alert("删除失败！");
					}
				});
			}
		}
	};
}();
$(function(){
	$('body').on('click','.btn-menu-role-open',function(){
		$(this).closest(".table").find("tr").removeClass("success");
		$(this).closest(".role-list").addClass("success");
		selectNode = $(this).closest(".role-list");
	});
	
	
	/**
	 * 菜单与权限的关系，提交
	 */
	$("body").on("click","#commit_role_menu",function(){
		var roleId = $(selectNode).find(".config_role_table_id").val();
		var itemIds = "";
		$("#data-table-menu").find("input[class=menu_id]").each(function(){
			if(this.checked){
				itemIds = itemIds + "," + $(this).val();
			}
		});
		commitRoleRelation(roleId,itemIds,"menu");		
	});
	/**
	 * 客户与权限的关系，提交
	 */
	$("body").on("click","#commit_role_admin",function(){
		var roleId = $(selectNode).find(".config_role_table_id").val();
		var itemIds = "";
		$("#data-table-admin").find("input[class=admin_id]").each(function(){
			if(this.checked){
				itemIds = itemIds + "," + $(this).val();
			}
		});
		commitRoleRelation(roleId,itemIds,"admin");		
	});
	
	//权限下添加菜单
	$("#add-admin-role").click(function(){
		if(selectNode == null){
			alert("您还没有选择权限组");
		}else{
			$('#myModal-admin').modal('show');
		}
	});
});	

function deleteRoles(item){
	 if(confirm("是否批量删除")){
		var valuelist = returnAllIds(item,"config_role_table_id");
		if(!valuelist){
			alert("请至少勾选一条数据！");
			return false;
		}
		Sup.delSupplier(item,valuelist,"all");
	 }
}

/**
 * 提交权限的关联信息
 * @param roleId
 * @param itemIds
 */
function commitRoleRelation(roleId,itemIds,type){
	$.ajax({
		type : "POST",
		url : "admin/system/commitRoleRelation.do",
		data : {"roleId":roleId,"itemIds":itemIds,"type":type},
		success : function(data){
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				if(result.length == 0){
					alert("已关联所有菜单！");
				}else{
					alert("操作成功！");
					Sup.listSupplier(roleId);
				}
				$('#myModal-menu').modal('hide');
				$('#myModal-admin').modal('hide');
			}
		}
	});
}

function showModalBox(act){
	action = act;
	$('#myModal-role').modal('show');
}
function checkAll(obj){
	var checked = $(obj).prop('checked');
	$(obj).closest(".table").find('input[type=checkbox]').prop('checked',checked);
}

//提交权限信息（新增修改）
function commitData(){
	$(item_class).each(function(e,i){
		var attr_name = $(i).attr("name");
		if(attr_name != flag_text+"_id" && ($(i).val() == null || $(i).val() == "")){
			if(!$(i).closest(".form-group").hasClass("has-error")){
				$(i).closest(".form-group").addClass("has-error");
			}
		}
	});
	$(item_class).focus(function(){
		if($(this).closest(".form-group").hasClass("has-error")){
			$(this).closest(".form-group").removeClass("has-error");
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
		url : "admin/system/commitSystemRoleForAjax.do",
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
					if(action == "ADD"){
						addItemToList(result,null);
					}else{
						if(editDemo != null){
							var grade = $(editDemo).closest(".gradeA");
							if(result.status == 0){
								$(grade).css("color"," #E91E63");
							}
							$(grade).html("");
							var item = "<td> <input  class='config_role_table_id' type='checkbox' value='"+result.id+"' /> " +
							"<input  class='config_role_table_status' type='hidden' value='"+result.status+"' /> </td> " +
							"<td class='config_role_table_name'>"+result.name+"</td> " +
							"<td class='config_role_table_code'>"+result.code+"</td> " +
							"<td>"+result.status_value+"</td> " +
							"<td><div class='btn-group' data-toggle='buttons'> " +
							"<label class='btn btn-primary btn-menu-role btn-xs'  title='修改'> <i class='fa fa-pencil'></i> </label> " +
							"<label class='btn btn-danger btn-menu-role btn-xs'  title='删除'> <i class='fa fa-trash-o'></i> </label></div></td> " +
							"<td class='config_role_table_description' style='display:none;'>"+result.description+"</td>";
							$(grade).append(item);
						}
					}
					$('#myModal-role').modal('hide');
					//移除编辑框数据
					$("#form_role").find("input").val(null);
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
	$("#form_role").find("input").val(null);
	//移除原先的编辑框
	//获取编辑对象的内容填充编辑框
	$(item).closest(".gradeA").find("td").each(function(n,e){
		var class_name = $(e)[0].className;
		class_name = class_name.substr(table_class.length,class_name.length);
		var item_name = "."+flag_text+"_"+class_name;
		if($(e).children().length > 0){
			$(e).children().each(function(i,child){
				var childClassName = $(child)[0].className;
				childClassName = "."+flag_text+"_"+ childClassName.substr(table_class.length,childClassName.length);
				$(childClassName).val($(child).val());
			});
		}else{
			$(item_name).val($(e).text());
		}
	});
	action = "EDIT";
	$('#myModal-role').modal('show');
}
//新增完成后  展示新增结果
function addItemToList(result){
	var color = "#333";
	if(result.status == 0){
		color = " #E91E63";
	}
	//获得列表的第一个元素，已这个元素的格式填充数据
	var item = "<tr class='gradeA role-list' style='cursor: pointer; color:"+color+"' onclick='Sup.listSupplier("+result.id+");'>  " +
			"<td> <input  class='config_role_table_id' type='checkbox' value='"+result.id+"' /> " +
			"<input  class='config_role_table_status' type='hidden' value='"+result.status+"' /> </td> " +
			"<td class='config_role_table_name'>"+result.name+"</td> " +
			"<td class='config_role_table_code'>"+result.code+"</td> " +
			"<td>"+result.status_value+"</td> " +
			"<td><div class='btn-group' data-toggle='buttons'> " +
			"<label class='btn btn-primary btn-menu-role btn-xs'  title='修改' onclick='editConfigComment(this);'> <i class='fa fa-pencil'></i> </label> " +
			"<label class='btn btn-danger btn-menu-role btn-xs'  title='删除'> <i class='fa fa-trash-o'></i> </label></div></td> " +
			"<td class='config_role_table_description' style='display:none;'>"+result.description+"</td> </tr>";
	$("#system_role_table_body").prepend(item);
}

/**
 * 变更权限组状态
 * @param item
 * @param status
 * @returns {Boolean}
 */
function changeRoleStatusById(item,status){
	 if(status == 0){
			msg = "确定将关系置为失效?";
		}else if(status == 1){
			msg = "确定生效?";
		}
	 if(confirm(msg)){
	var valuelist = returnAllIds(item,"config_role_table_id");
	if(!valuelist){
		alert("请至少勾选一条数据！");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "admin/system/changeRoleRelationForAjax.do",
		data : {
			"ids":valuelist,"status" : status
		},
		success : function(data) {
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				alert("操作完成！");
				$(item).closest(".column").find("input[class=config_role_table_id]").each(function() {
			  		if (this.checked) {
			            $(this).closest(".gradeA").find(".config_role_table_status_value").text(result[0].status_value);
			        }
			  	});
			}
		}
	});
	 }
}

/**
 * 返回所有勾选的id集合
 */
function returnAllIds(item,className){
	var valuelist = "";
	$(item).closest(".column").find("input[class="+className+"]").each(function() {
  		if (this.checked) {
            valuelist += $(this).val() + ",";
        }
  	});
	if(valuelist == null || valuelist == ""){
//  		alert("请至少勾选一条数据！");
  		return false;
  	}
	return valuelist;
}

/**
 * 移除所有勾选的数据
 */
function removeAllIds(item,className,type){
	if(type == "all"){
		$(item).closest(".column").find("input[class="+className+"]").each(function() {
	  		if (this.checked) {
	  			$(this).closest(".role-list").remove();
	        }
	  	});
	}else{
		$(item).closest(".role-list").remove();
	}
}

/**
 * 提交菜单、用户和权限的关联信息
 * @param menu menu_id
 * @param role_ids
 * @param action "add","modify","delete"
 */
function commitItemRoleRale(item_id,role_ids,rela_id,action,status,type,isall){
	var url = "";
	if(type == "menu"){
		url = "admin/system/commitSystemMenuAndRole.do";
	}else if(type == "admin"){
		url = "admin/system/commitSystemAdminAndRole.do";
	}
	$.ajax({
		type : "POST",
		url : url,
		data : {
			"item_id" : item_id,"role_ids":role_ids,"rela_id":rela_id,"action":action,"status":status
		},
		success : function(data) {
			if (data != null && data != "") {
				var result = eval("(" + data + ")");
				$('#myModal-menu').modal('hide');
				$('#myModal-admin').modal('hide');
				alert("操作成功！");
				var roleId = $(selectNode).find(".config_role_table_id").val();
				Sup.listSupplier(roleId);
			}
		}
	});
}

/**
 * 删除权限组与菜单关联方法
 */
function dealItemRoleRale(item,type,isall,status){
	var msg = "";
	var action = "";
//	var status = "";
	if($(item).attr("name") == "delete-rela"){
		msg = "确定删除该关系？";
		action = "delete";
//		status = "delete";
	}else if(status == "loseEff"){
		msg = "确定将关系置为失效?";
		action = "modify";
//		status = "loseEff";
	}else if(status == "eff"){
		msg = "确定生效?";
		action = "modify";
//		status = "eff";
	}
	
	var valuelist = "";
	if(type == "all"){
		valuelist = returnAllIds(item,"table-rela-tr");
	}else{
		valuelist = $(item).closest(".gradeA").find(".table-rela-tr").val();
	}
		if(confirm(msg)){
			//获取勾选的权限
			if(!valuelist){
				alert("请至少勾选一条数据！");
				return false;
			}
		  	commitItemRoleRale(null,null,valuelist,action,status,type,isall);
		}
}
