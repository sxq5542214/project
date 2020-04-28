
	//记录已选择的node信息
	var selectNode;
	var naviDemo = new Array();
	var $expandibleTree;


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
				if(selectNode.nodes != null && selectNode.nodes.length >= 5 && selectNode.parentid == null && selectNode.id != 0){
					alert("最多添加5个二级菜单！");
					return false;
				}
				if(selectNode.id == 0 && selectNode.nodes != null && selectNode.nodes.length >= 3){
					alert("最多添加3个一级菜单！");
					return false;
				}
				var add_children_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
				//添加子节点
				var new_node = $expandibleTree.treeview("addNode", [add_children_node, { node: { parent_id:add_children_node.id,original_id:add_children_node.original_id,text: "新加的子菜单" ,nodeId:getRandomNum()} }]); 
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
			if(selectNode.id == 0){
				return false;
			}
			var add_siblings_node = $expandibleTree.treeview('getParent', selectNode.nodeId);
			if(add_siblings_node.id == 0 && add_siblings_node.nodes != null && add_siblings_node.nodes.length >= 3){
				alert("最多添加3个一级菜单！");
				return false;
			}else if(add_siblings_node.nodes != null && add_siblings_node.nodes.length >= 5){
				alert("最多添加5个二级菜单！");
				return false;
			}
			var new_node = $expandibleTree.treeview("addNode", [add_siblings_node, {node: {parent_id:add_siblings_node.id,original_id:add_siblings_node.original_id, text: "新加的同级菜单" ,nodeId:getRandomNum()} }]);  
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
		if(delete_node.id != null){
			$.ajax({
				type : "POST",
				url : "admin/wechatmenu/deleteWechatMenu.do",
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
		}else{
			$expandibleTree.treeview("deleteNode", [delete_node.nodeId, { silent: true } ]);
		}
		
	}
	/**
	 * 菜单删除方法
	 */
	function syncData(item){
		var original_id = $(item).closest(".gradeA").find(".table_wechatoriginalinfo_originalid").text();
		$.ajax({
			type : "POST",
			url : "admin/wechatmenu/syncNewWechatMenu.do",
			data : {
				"original_id" : original_id
			},
			success : function(data) {
				if (data != null && data != "" && data == "SUCCESS") {
					alert("同步成功！");
				}else{
					alert("同步失败！");
				}
			}
		});
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
		if(selectNode == null){
			alert("请选择菜单节点！");
			return false;
		}
		var type = $("#form_menu_type").val();
		if(type == "click"){
			var menu_key = $("#form_menu_menu_key").val();
			if(menu_key == null || menu_key == ""){
				alert("关键字必填！");
				return false;
			}
		}else if(type == "view"){
			var url = $("#form_menu_url").val();
			var url_code = $("#form_menu_url_code").val();
			if((url == null || url == "") && (url_code == null || url_code == "")){
				alert("跳转地址和自定义跳转地址需填写一个！");
				return false;
			}
		}else if(type == "media_id" || type == "view_limited"){
			var media_id = $("#form_menu_media_id").val();
			if(media_id == null || media_id == ""){
				alert("素材ID必填！");
				return false;
			}
		}
		var jsonData ="{";
		//遍历属性值得到需要传递得数据
		$("#form_menu").find(".form-control-commit").each(function(n,item){
			var attr_name = $(item).attr("name");
			if(attr_name != "id" && ($(item).val() == "" || $(item).val() == null)){
				flag = false;
			}
			if(attr_name == "parent_id" && $(item).val() == 0){
				$(item).val("");
			}
			jsonData = jsonData + "\""+attr_name+"\":\"" + $(item).val() + "\",";
		});
		//去除末尾逗号
		jsonData = jsonData.substr(0,jsonData.length-1);
		jsonData = jsonData + "}";
		$.ajax({
			type : "POST",
			url : "admin/wechatmenu/commitWechatMenu.do",
			data : {
				"jsonData" : jsonData
			},
			success : function(data) {
				if (data != null && data != "") {
					var result = eval("(" + data + ")");
					var modify_node = $expandibleTree.treeview('getNode', selectNode.nodeId);
					modify_node.change = false;
					modify_node.id = result.id;
					modify_node.status = result.name;
					modify_node.name = result.menu_key;
					modify_node.url = result.url;
					modify_node.media_id = result.media_id;
					modify_node.parent_id = result.parent_id;
					modify_node.original_id = result.original_id;
					$expandibleTree.treeview("modifyNode", [modify_node, modify_node]); 
					selectNode = modify_node;
					$("#form_menu_id").val(result.id);
					alert("操作成功！");
				}
			}
		});
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
	
	Array.prototype.remove = function(index) {  
		return this.splice(index, 1);
	}; 
	
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

function editOriginalMenu(item){
	var original_id = $(item).closest(".gradeA").find(".table_wechatoriginalinfo_originalid").text();
	$("#form_menu_original_id").val(original_id);
	$.ajax({
		type : "POST",
		url : "admin/wechatmenu/getWechatMenuForShow.do",
		data : {"original_id":original_id},
		success : function(data) {
			if (data != null && data != "") {
				data = data.replace(/name/g,"text");
				data = data.replace(/children_button/g,"nodes");
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
								getMenuInfo(node);
								selectNode = node;
					        },
					        //节点失去选择事件
					        onNodeUnselected: function (event, node) {
					        	selectNode = null;
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
}

/**
 * 提交菜单到微信
 */
function commitToWeiXin(){
	if(confirm("同步数据需要从本地获取，确定已将菜单数据保存至本地？")){
		var original_id = $("#form_menu_original_id").val();
		$.ajax({
			type : "POST",
			url : "admin/wechatmenu/sendWechatMenuToWeixin.do",
			data : {
				"original_id" : original_id
			},
			success : function(data) {
				alert(data);
				$('#myModal-menu').modal('hide');
			}
		});
	}
	
}

/**
 * 将菜单的信息写入表单
 */
function getMenuInfo(info){
	$("#form_menu_media_code").attr("readonly","true");
	$("#form_menu_menu_key").attr("readonly","true");
	$("#form_menu_url").attr("readonly","true");
	$("#form_menu_url_code").attr("readonly","true");
	if(info != null && info.id != 0){
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
				if(idName != "form_menu_original_id"){
					$(item).val(info[attrName]);
				}
				if(idName == "form_menu_type"){
					if(info[attrName] != null && info[attrName] == "view"){
						$("#form_menu_url").removeAttr("readonly");
						$("#form_menu_url_code").removeAttr("readonly");
					}else if(info[attrName] != null && info[attrName] == "click"){
						$("#form_menu_menu_key").removeAttr("readonly");
					}else if(info[attrName] != null && ( info[attrName] == "media_id" || info[attrName] == "view_limited" )){
						$("#form_menu_media_code").removeAttr("readonly");
					}else{
						$("#form_menu_media_code").removeAttr("readonly");
						$("#form_menu_menu_key").removeAttr("readonly");
						$("#form_menu_url").removeAttr("readonly");
						$("#form_menu_url_code").removeAttr("readonly");
					}
				}
			}
		});
	}
}

/**
 * 跳转选项变更处理
 */
$("body").on("change","#form_menu_url_code",function(){
	$("#form_menu_url").val("");
});

/**
 * 自定义跳转的改变事件
 */
$("#form_menu_url").change(function() {
	$("#form_menu_url_code").val("");
});

$("body").on("change","#form_menu_type",function(){
	//类型是view url必填；click  key必填；media_id和view_limited media_id必填 字段为不可编辑
	if($(this).val() == "view"){
		$("#form_menu_media_code").attr("readonly","true");
		$("#form_menu_menu_key").attr("readonly","true");
		$("#form_menu_url").removeAttr("readonly");
		$("#form_menu_url_code").removeAttr("readonly");
	}else if($(this).val() == "click"){
		$("#form_menu_media_code").attr("readonly","true");
		$("#form_menu_menu_key").removeAttr("readonly");
		$("#form_menu_url").attr("readonly","true");
		$("#form_menu_url_code").attr("readonly","true");
	}else if($(this).val() == "media_id" || $(this).val() == "view_limited"){
		$("#form_menu_media_code").removeAttr("readonly");
		$("#form_menu_menu_key").attr("readonly","true");
		$("#form_menu_url").attr("readonly","true");
		$("#form_menu_url_code").attr("readonly","true");
	}else{
		$("#form_menu_media_id").removeAttr("readonly");
		$("#form_menu_menu_key").removeAttr("readonly");
		$("#form_menu_url").removeAttr("readonly");
		$("#form_menu_url_code").removeAttr("readonly");
	}
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

function checkAll(obj){
	var checked = $(obj).prop('checked');
	$('input[type=checkbox]').prop('checked',checked);
}

/**
 * 异步获取素材信息
 */
function showMaterialList(nowpage){
	/**
	 * 图文素材
	 */
	showNewsMaterialList(nowpage);
	/**
	 * 图片素材
	 */
	showotherMaterialList("image");
	/**
	 * 音频素材
	 */
	showotherMaterialList("voice");
	/**
	 * 视频素材
	 */
	showotherMaterialList("video");
	$("#myModal-material").modal("show");
}

function showotherMaterialList(type){
	var original_id = $("#form_menu_original_id").val();
	$.ajax({
		type : "POST",
		url : "wechat/delivery/getOriginalWecahtMaterialList.do",
		data : {"original_id" : original_id,"type" : type ,"nowpage" : 1},
		success : function(data){
			var material = eval("("+data+")");
			dealOtherTableInfo(material,type);
			$('#table_'+type).dataTable({
			    //checkbox不排序
			     "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
			     "aaSorting": [[1, "desc"]],
			      "bPaginate":true,
			      "iDisplayLength":10,
			      "bFilter":true,
			      "sPaginationType": "full_numbers"
			    });
		}
	});
}

function showNewsMaterialList(nowpage){
	var original_id = $("#form_menu_original_id").val();
	$.ajax({
		type : "POST",
		url : "wechat/delivery/getOriginalWecahtMaterialList.do",
		data : {"original_id" : original_id,"type" : "news","nowpage" : nowpage},
		success : function(data){
			var material = eval("("+data+")");
			dealTableInfo(material,"news");
		}
	});
}

function dealOtherTableInfo(material,type){
	var table_header = '<div id="dt_example" class="example_alt_pagination">'+
	'<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="table_'+type+'">'+
		'<thead>'+
			'<tr>'+
				'<th style="width: 90%">素材名称</th>'+
				'<th style="width: 10%" class="hidden-phone">操作</th>'+
			'</tr>'+
		'</thead>';
	var table_body = '<tbody id="supplier_list">';
	var tr = "";
	if(material != null && material.length > 0){
		for(var i=0;i<material.length;i++){
			tr += '<tr class="gradeA ">'+
			'<th>';
			if(type == "image"){
				tr += '<div class="hotlinking">'+
				 '<img id="img" style="width:160px;height:80px" src="http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl='+material[i].sucai_url+'">'+
				'</div><h5><a style="margin-left: 8px;" href="'+material[i].sucai_url+'" target="_blank">'+material[i].sucai_name+'</a></h5></th>';
			}else{
				tr += material[i].sucai_name;
			}
			tr +='<th >'+
			'<a class="btn btn-info" href="javascript:return false;"  style="padding: 6px 12px;margin-left:5px;" onclick="choiceMaterial(\''+material[i].sucai_media_id+'\',\''+material[i].sucai_name+'\');">'+
					'<i class="fa fa-bars" style="padding-right: 5px;"></i>选择此素材</a>'+
			'</th>'+
		'</tr>';
		}
	}
	var tablHtml = table_header + table_body + tr + '</tbody></table></div>';
	$("#"+type).children().html(tablHtml);
}

/**
 * 生成table结构（素材）
 */
function dealTableInfo(material,type){
	var table_header = '<div id="dt_example" class="example_alt_pagination">'+
		'<table class="table table-condensed table-striped table-hover table-bordered pull-left">'+
			'<thead>'+
				'<tr>'+
					'<th style="width: 20%">主素材名称</th>'+
					'<th style="width: 31%" class="hidden-phone">明细</th>'+
					'<th style="width: 5%" class="hidden-phone">操作</th>'+
				'</tr>'+
			'</thead>';
	var table_body = '<tbody id="supplier_list">';
	var tr = "";
	var list = material.dataList;
	if(list != null && list.length > 0){
		for(var i=0;i<list.length;i++){
			var materialList = list[i].materialList;
			var rowSpan = materialList.length*1+1;
			tr += '<tr class="gradeA ">'+
			'<th rowspan="'+rowSpan+'">'+
			'<div class="hotlinking">'+
			//简易方法，如有更好的还请更新（此处用的是qq阅读跳转的） 
			'<img id="img" style="width:160px;height:80px" src="http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl='+list[i].sucai_thumb_url+'">'+
			'</div><h5><a style="margin-left: 8px;" href="'+list[i].sucai_url+'" target="_blank">'+list[i].sucai_title+'</a></h5></th>'+
			'<th rowspan="1" class="mater"></th>'+
			'<th rowspan="'+rowSpan+'">'+
			'<a class="btn btn-info" href="javascript:return false;"  style="padding: 6px 12px;margin-left:5px;" onclick="choiceMaterial(\''+list[i].sucai_media_id+'\',\''+list[i].sucai_title+'\');">'+
					'<i class="fa fa-bars" style="padding-right: 5px;"></i>选择此素材</a>'+
			'</th>'+
		'</tr>';
			if(materialList != null && materialList.length > 0){
				for(var j=0;j<materialList.length;j++){
					tr += '<tr class="gradeA ">'+
    				'<th class="childrenmater"><a href="'+materialList[j].sucai_url+'" target="_blank">'+materialList[j].sucai_title+'</a></th>'+
    				'</tr>';
				}
			}
		}
	}
	
	var footer = '<div class="dataTables_info">当前第'+material.nowpage+' 页/共 '+material.totalpage+' 页['+material.totalcount+' 条数据]</div>'+
    '<div class="dataTables_paginate paging_full_numbers">'+
    	'<span>'+
    	'<a tabindex="0" class="paginate_button" onclick="showNewsMaterialList(1)" >首页</a>';
    	if(material.nowpage>1){
    		footer +='<a tabindex="0" class="paginate_button" >.....</a>';
    	} 
    		for(var n = 1 ; n <= material.totalpage; n ++) {
    			var classStr = "paginate_button";
    			if(material.nowpage == n)
    				classStr = "paginate_active";
    			if(n < material.nowpage+5 && n > material.nowpage-5){
    				footer +='<a tabindex="0" class="'+classStr+'" onclick="showNewsMaterialList('+n+')" >'+n+'</a>';
    		}}
    		if(material.nowpage<material.totalpage){
    			footer +='<a tabindex="0" class="paginate_button" >.....</a>';
    	}                 	
    		footer = footer + '<a tabindex="0" class="paginate_button" onclick="showNewsMaterialList('+material.totalpage+')" >尾页</a>'+
    	'</span>'+
    '</div>'+
		'<div class="clearfix"></div>'+
	'</div>';
    var tablHtml = table_header + table_body + tr + '</tbody></table>' + footer;
	$("#news").children().html(tablHtml);
}

function choiceMaterial(media_id,media_name){
	$("#form_menu_media_code").val(media_name);
	$("#form_menu_media_id").val(media_id);
	var changeNode = $expandibleTree.treeview('getNode', selectNode.nodeId);
	changeNode.change = true;
	if($("#submit-form").hasClass("btn-default")){
		$("#submit-form").removeClass("btn-default");
		$("#submit-form").addClass("btn-success");
		$("#submit-form").removeClass("disabled");
	}
	$("#myModal-material").modal("hide");
}

function showImg(url) {
    var frameid = 'frameimg' + Math.random();
    window.img = '<img id="img" style="width:160px;height:80px" src=\'' + url + '?' + Math.random() + '\' /><script>window.onload = function() { parent.document.getElementById(\'' + frameid + '\').height = document.getElementById(\'img\').height+\'px\'; }<' + '/script>';
    document.write('<iframe id="' + frameid + '" src="javascript:parent.img;" frameBorder="0" scrolling="no" width="160px;height:80px"></iframe>');
}