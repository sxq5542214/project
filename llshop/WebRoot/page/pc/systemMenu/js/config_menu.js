$(function() {
	
	//记录已选择的node信息
	var selectNode;
	var naviDemo = new Array();
	var $expandibleTree;
	$.ajax({
		type : "POST",
		url : "admin/system/getSystemMenuToShow.do",
		data : {},
		success : function(data) {
			if (data != null && data != "") {
				data = data.replace(/name/g,"text");
				data = data.replace(/children_menu/g,"nodes");
				defaultData = eval("(" + data + ")");
				$expandibleTree = $('#treeview-expandible').treeview(
						{
							color : "#428bca",
							expandIcon : 'glyphicon glyphicon-chevron-right',
							collapseIcon : 'glyphicon glyphicon-chevron-down',
							emptyIcon : 'glyphicon glyphicon-tag',
							data : "["+data+"]",
							//节点关闭事件
							onNodeCollapsed : function(event, node) {
//								console.log(node.text+"已关闭");
							},
							//节点展开事件
							onNodeExpanded : function(event, node) {
//								console.log(node.text+"已展开");
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
								naviDemo.length = 0;
								naviDemo.push("<li class='active'>"+node.text+"</li>");
								getParentNode(node);
								$(".breadcrumb").html("");
								for(var i=naviDemo.length;i>=0;i--){
									$(".breadcrumb").append(naviDemo[i]);
								}
								getMenuInfo(node);
								selectNode = node;
					        },
					        //节点失去选择事件
					        onNodeUnselected: function (event, node) {
//					        	removeMenuInfo();
//					        	console.log(node.text+"失去选择");
					        }
						});
				var findExpandibleNodess = function() {
					return $expandibleTree.treeview('search', [
							$('#input-expand-node').val(), {
								ignoreCase : false,
								exactMatch : false
							} ]);
				};
				
				var expandibleNodes = findExpandibleNodess();
				// Expand/collapse/toggle nodes
				$('#input-expand-node').on('keyup', function(e) {
					expandibleNodes = findExpandibleNodess();
					$('.expand-node').prop('disabled', !(expandibleNodes.length >= 1));
				});
			}
		}
	});

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
	 * 点击导航链接事件
	 */
	$('body').on('click','.selectNode',function(){
		var clickNode = $expandibleTree.treeview('getNode', $(this).find(".nodeid").val());
		$expandibleTree.treeview('selectNode', [ clickNode, { silent: $('#chk-select-silent').is(':checked') }]);
	});
	
	/**
	 * 添加子节点方法
	 */
	$('body').on('click','#add-children-node',function(){
		if(selectNode == null){
			alert("请选取正确的节点");
		}else{
			if(selectNode != null && selectNode.id == null){
				alert("菜单未保存，请先保存该菜单！");
			}else{
				var add_children_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
				//添加子节点
				var new_node = $expandibleTree.treeview("addNode", [add_children_node, { node: { parentid:add_children_node.id,text: "新加的子菜单" ,nodeId:getRandomNum()} }]); 
				//展开节点
				$expandibleTree.treeview('expandNode', [ add_children_node, { silent: $('#chk-expand-silent').is(':checked') }]);
				//将新建的子节点选中
				var clickNode = $expandibleTree.treeview('getNode', new_node.nodeId);
				$expandibleTree.treeview('selectNode', [ clickNode, { silent: $('#chk-select-silent').is(':checked') }]);
			}
		}
	});
	/**
	 * 添加兄弟节点方法
	 */
	$('body').on('click','#add-siblings-node',function(){
		if(selectNode == null){
			alert("请选取正确的节点");
		}else{
			var add_siblings_node = $expandibleTree.treeview('getParent', selectNode.nodeId);
			var new_node = $expandibleTree.treeview("addNode", [add_siblings_node, {node: {parentid:add_siblings_node.id, text: "新加的同级菜单" ,nodeId:getRandomNum()} }]);  
			//将新建的子节点选中
			var clickNode = $expandibleTree.treeview('getNode', new_node.nodeId);
			$expandibleTree.treeview('selectNode', [ clickNode, { silent: $('#chk-select-silent').is(':checked') }]);
		}
	});
	/**
	 * 删除节点方法
	 */
	$('body').on('click','#delete-node',function(){
		if(selectNode == null){
			alert("请选取正确的节点");
		}else{
			var delete_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
			if(confirm("确定删除节点？")){
				if(delete_node.nodes != null && delete_node.nodes.length > 0){
					alert("包含多个子菜单，不可删除？");
				}else{
					deleteData(delete_node);
				}
			}
		}
	});
	

	/**
	 * 菜单删除方法
	 */
	function deleteData(delete_node){
		$.ajax({
			type : "POST",
			url : "admin/system/deelteSystemMenu.do",
			data : {
				"id" : delete_node.id
			},
			success : function(data) {
				if (data != null && data != "") {
					$expandibleTree.treeview("deleteNode", [delete_node.nodeId, { silent: true } ]);
					alert("删除成功！");
				}
			}
		});
	}
	/**
	 * 将菜单的信息写入表单
	 */
	function getMenuInfo(info){
		//填充菜单的基本信息
		$("#form_menu").find(".form-control").each(function(e,item){
			var idName = $(item).attr("id");
			if(idName != null){
				var attrName = idName.substr(10,idName.length);
				if("icon_path" == attrName){
					var ico_name = "fa "+info[attrName]+"";
					$("#ico-show").removeClass();
					$("#ico-show").addClass(ico_name);
				}
				$(item).val(info[attrName]);
			}
		});
		parentNode = $expandibleTree.treeview('getParent', info);
		$("#form_menu_parent_name").val(parentNode.text);
		//填充菜单的权限信息
		var table_body = "";
		if(info.menu_roles != null){
			for(var i=0;i<info.menu_roles.length;i++){
				if(info.menu_roles[i].status == 1){
					table_body = table_body + "<tr class='gradeA success'>" +
							"<td><input type='checkbox' value='"+info.menu_roles[i]['id']+"' />" +
							"<input class='rela_menu_id' type='hidden' value='"+info.menu_roles[i].menu_id+"' />" +
							"<input class='rela_role_id' type='hidden' value='"+info.menu_roles[i].menu_id+"' />" +
									"</td>" +
							"<td>"+info.menu_roles[i].role_text+"</td>" +
							"<td class='status_value'>"+info.menu_roles[i].status_value+"</td>" +
							"</tr>";
				}else{
					table_body = table_body + "<tr class='gradeA danger'>" +
							"<td><input type='checkbox' value='"+info.menu_roles[i]['id']+"' />" +
							"<input class='rela_menu_id' type='hidden' value='"+info.menu_roles[i].menu_id+"' />" +
							"<input class='rela_role_id' type='hidden' value='"+info.menu_roles[i].menu_id+"' />" +
									"</td>" +
							"<td>"+info.menu_roles[i].role_text+"</td>" +
							"<td class='status_value'>"+info.menu_roles[i].status_value+"</td>" +
							"</tr>";			
					}
			}
		}
		$("#system_menu_role_relation_table_body").html(table_body);
	}
	/**
	 * 将菜单的信息写入表单
	 */
	function removeMenuInfo(){
		$("#form_menu").find(".form-control").each(function(e,item){
			
		});
		
		$("#form_menu_name").val(null);
		$("#form_menu_code").val(null);
	}
	


	// Expand/collapse all
	$('#btn-expand-all').on('click', function(e) {
		var levels = $('#select-expand-all-levels').val();
		$expandibleTree.treeview('expandAll', {
			levels : levels,
			silent : $('#chk-expand-silent').is(':checked')
		});
	});

	$('#btn-collapse-all').on('click', function(e) {
		$expandibleTree.treeview('collapseAll', {
			silent : $('#chk-expand-silent').is(':checked')
		});
	});
	
	$("#form_menu").find(".form-control").on('keyup', function(e) {
		var modify_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
		var name = $(this).attr("name");
		if($(this).attr("name") == "name"){
			name = "text";
		}
		modify_node[name] = $(this).val();
		$expandibleTree.treeview("modifyNode", [modify_node, modify_node]); 
	});
	/**
	 * 提交菜单信息
	 */
	$("#submit-form").on("click",function(){
		var jsonData ="{";
		//遍历属性值得到需要传递得数据
		$("#form_menu").find(".form-control-commit").each(function(n,item){
			var attr_name = $(item).attr("name");
			if(attr_name != "id" && ($(item).val() == "" || $(item).val() == null)){
				flag = false;
			}
			jsonData = jsonData + "\""+attr_name+"\":\"" + $(item).val() + "\",";
		});
		//去除末尾逗号
		jsonData = jsonData.substr(0,jsonData.length-1);
		jsonData = jsonData + "}";
		$.ajax({
			type : "POST",
			url : "admin/system/commitSystemMenu.do",
			data : {
				"jsonData" : jsonData
			},
			success : function(data) {
				if (data != null && data != "") {
					var result = eval("(" + data + ")");
					var modify_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
					modify_node.change = false;
					modify_node.id = result.id;
					modify_node.status = result.status;
					modify_node.type = result.type;
					$expandibleTree.treeview("modifyNode", [modify_node, modify_node]); 
					selectNode = modify_node;
					$("#form_menu_id").val(result.id);
					alert("操作成功！");
				}
			}
		});
	});
	
	/**
	 * 关联权限数据提交
	 */
	$("#commit_role").click(function(){
		var valuelist = "";
		//获取勾选的权限
		$("#roles-table").find("input[class='role_id']").each(function() {
	  		if (this.checked) {
	            valuelist += $(this).val() + ",";
	        }
	  	});
	  	
	  	if(valuelist == null || valuelist == ""){
	  		alert("请至少勾选一条数据！");
	  		return false;
	  	}
	  	commitMenuRoleRale($("#form_menu_id").val(),valuelist,null,"add","add");
	});
	

	
	/**
	 * 表单内容变更记录
	 */
	$("#form_menu :input").change(function() {
		var changeNode = $expandibleTree.treeview('getNode', selectNode.nodeId);
		changeNode.change = true;
		if($("#submit-form").hasClass("btn-default")){
			$("#submit-form").removeClass("btn-default");
			$("#submit-form").addClass("btn-success");
			$("#submit-form").removeClass("disabled");
		}
	});
	
	/**
	 * 删除权限组与菜单关联方法
	 */
	$('body').on('click','.btn-menu-role',function(){
		var msg = "";
		var action = "";
		var status = "";
		if($(this).attr("name") == "delete-menu-role"){
			msg = "确定删除该关系？";
			action = "delete";
			status = "delete";
		}else if($(this).attr("name") == "modify-menu-role-status-un"){
			msg = "确定将关系置为失效?";
			action = "modify";
			status = "loseEff";
		}else if($(this).attr("name") == "modify-menu-role-status"){
			msg = "确定生效?";
			action = "modify";
			status = "eff";
		}
		var valuelist = "";
		if(selectNode == null){
			alert("请选取正确的节点");
		}else{
			if(confirm(msg)){
				//获取勾选的权限
				$("#system_menu_role_relation_table").find("input[type=checkbox]").each(function() {
			  		if (this.checked) {
			            valuelist += $(this).val() + ",";
			        }
			  	});
			  	if(valuelist == null || valuelist == ""){
			  		alert("请至少勾选一条数据！");
			  		return false;
			  	}
			  	commitMenuRoleRale(null,null,valuelist,action,status);
			}
		}
	});
	
	/**
	 * 提交菜单和权限的关联信息
	 * @param menu menu_id
	 * @param role_ids
	 * @param action "add","modify","delete"
	 */
	function commitMenuRoleRale(menu_id,role_ids,rela_id,action,status){
		$.ajax({
			type : "POST",
			url : "admin/system/commitSystemMenuAndRole.do",
			data : {
				"menu_id" : menu_id,"role_ids":role_ids,"rela_id":rela_id,"action":action,"status":status
			},
			success : function(data) {
				if (data != null && data != "") {
					var result = eval("(" + data + ")");
					$('#myModal').modal('hide');
					//更新列表数据
					//更新node数据
					var modify_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
					if(action == "add"){
						$("#system_menu_role_relation_table_body").append(addRolesItem(result));
						addRoleMenuRelaToNode(modify_node,result);
					}else if(action == "modify"){
						modifyRoleMenuRela(modify_node,result,"modify");
					}else if(action == "delete"){
						modifyRoleMenuRela(modify_node,result,"delete");
					}
					alert("操作成功！");
				}
			}
		});
	}
	
	Array.prototype.remove = function(index) {  
		return this.splice(index, 1);
	}; 
	/**
	 * 修改或移除node的权限关系数据
	 */
	function modifyRoleMenuRela(modify_node,result,action){
		if(result != null && result.length > 0){
			for(var i=0;i<result.length;i++){
				var nodes = modify_node.menu_roles;
				for(var n=0;n<nodes.length;n++){
					if(nodes[n].id == result[i].id){
						if(action == "modify"){
							nodes[n].status_value = result[i].status_value;
							nodes[n].status= result[i].status;
						}else if(action == "delete"){
							modify_node.menu_roles.remove(n);
						}
					}
					//获取勾选的权限
					$("#system_menu_role_relation_table").find("input[type=checkbox]").each(function() {
				  		if (this.checked && $(this).val() == result[i].id) {
				  			if(action == "modify"){
				  				$(this).closest(".gradeA").find(".status_value").text(result[i].status_value);
					            if(result[i].status == 1){
					            	$(this).closest(".gradeA").removeClass("danger");
					            	$(this).closest(".gradeA").addClass("success");
					            }else if(result[i].status == 0){
					            	$(this).closest(".gradeA").removeClass("success");
					            	$(this).closest(".gradeA").addClass("danger");
					            }
				  			}else if(action == "delete"){
				  				$(this).closest(".gradeA").remove();
				  			}
				            
				        }
				  	});
				}
			}
		}	
	}
	
	/**
	 * 向node添加权限关系信息
	 */
	function addRoleMenuRelaToNode(modify_node,result){
		if(result != null && result.length > 0){
			for(var i=0;i<result.length;i++){
				var rela = {
						id : result[i].id,
						menu_id : result[i].menu_id,
						menu_text : result[i].menu_name,
						role_id : result[i].role_id,
						role_text : result[i].role_name,
						status : result[i].status,
						status_value : result[i].status_value
				};
				if(modify_node.menu_roles == null){
					modify_node.menu_roles = new Array();
				}
//				console.log(modify_node.menu_roles);
				modify_node.menu_roles.push(rela);
			}
		}
	}
	
	$("#add-menu-role").click(function(){
		if(selectNode == null){
			alert("请选取正确的节点");
		}else{
			if(selectNode != null && selectNode.id == null){
				alert("菜单未保存，请先保存该菜单！");
			}else{
				$('#myModal').modal('show');
			}
		}
	});
	
});



function getRandomNum(){
	return new Date().getTime();
}
$("#form_menu_icon_path").on('keyup', function(e) {
	var ico_name = $(this).val();
	ico_name = "fa "+ico_name+"";
	$("#ico-show").removeClass();
	$("#ico-show").addClass(ico_name);
});

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

var demo_height = $(window).height()-180;
$("#treeview-expandible").css("height",demo_height+"px");



function addRolesItem(result){
	var trItem = "";
	if(result != null && result.length > 0){
		for(var i=0;i<result.length;i++){
			trItem = trItem + "<tr class='gradeA success'>" +
			"<td><input type='checkbox' value='"+result[i].id+"'>" +
			"<input class='rela_menu_id' type='hidden' value='"+result[i].menu_id+"'>" +
			"<input class='rela_role_id' type='hidden' value='"+result[i].role_id+"'></td>" +
			"<td>"+result[i].role_name+"</td>" +
			"<td>"+result[i].status_value+"</td>" +
			"</tr>";
		}
	}
	return trItem;
}

function checkAll(obj){
	var checked = $(obj).prop('checked');
	$('input[type=checkbox]').prop('checked',checked);
}