<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.product.bean.ProductTypeBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	List<ProductTypeBean> list = (List<ProductTypeBean>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="images/favicon.png" type="image/png">

<title>商户管理-商品指派</title>
<link href="css/new.css" rel="stylesheet">
<link href="css/style.default.css" rel="stylesheet">
</head>

<body>
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						商户管理－<a id="inputs">商品指派</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<!-- BASIC WIZARD -->
					<div id="disabledTabWizard" class="basic-wizard">

						<ul class="nav nav-pills nav-justified">
							<li><a href="#dtab1" data-toggle="tab"><span>第一步:</span>
									选择品牌/分类</a></li>
							<li><a href="#dtab2" data-toggle="tab"><span>第二步:</span>
									选择商品</a></li>
							<li><a href="#dtab3" data-toggle="tab"><span>第三步:</span>
									选择商户</a></li>
							<li><a href="#dtab5" data-toggle="tab"><span>第四步:</span>
									确认提交</a></li>
						</ul>

						<div class="tab-content">
							<div class="tab-pane" id="dtab1">
								<div id="dt_example" class="example_alt_pagination">
									<table
										class="table table-condensed table-striped table-hover table-bordered pull-left"
										id="data-table">
										<thead>
											<tr>
												<th style="width: 5%"><input type="checkbox"
													class="no-margin" onclick="checkAll(this)" /></th>
												<th style="width: 20%">品牌名称</th>
												<th style="width: 75%">备注</th>
											</tr>
										</thead>
										<tbody>
											<%
										for(int i=0;i<list.size();i++){
											ProductTypeBean bean = list.get(i);
											%>
											<tr class="gradeA">
												<td><input type="checkbox" id="<%=bean.getId()%>"
													class="no-margin" /></td>
												<td><%=bean.getName() %></td>
												<td><%=NumberUtil.toString(bean.getRemark()) %></td>
											</tr>
											<%
										}
										%>
										</tbody>
									</table>
								</div>
							</div>
							<div class="tab-pane" id="dtab2">
								<div id="dt_example" class="example_alt_pagination">
									
								</div>
							</div>
							<div class="tab-pane" id="dtab3">
								<div id="dt_example" class="example_alt_pagination"></div>
							</div>
							<div class="tab-pane" id="dtab5" style="text-align: center;">
								<label id="result_tag"></label>
							</div>


						</div>
						<!-- tab-content -->

						<ul class="pager wizard">
							<li class="previous"><a href="javascript:void(0)">上一步</a></li>
							<li class="next"><a href="javascript:void(0)">下一步</a></li>
						</ul>

					</div>
					<!-- #disabledTabWizard -->
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modalMd" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel5" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title text-danger" id="myModalLabel5">请选择需要核减的商户</h4>
				</div>
				<div class="modal-footer">
					<div class="form-group">
						<div id="dt_supplier" class="col-md-12"></div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success"
						data-original-title="" id="model_check">
						<i class="fa fa-check"></i> 选择
					</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						data-original-title="" title="">
						<i class="fa fa-times"></i> Close
					</button>
				</div>
			</div>
		</div>
	</div>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-wizard.min.js"></script>
	<script src="js/jquery.dataTables.js"></script>
	<script src="../../js/Util.js"></script>
	<script>
var parcustid = -1;

$(document).ready(function(){
    "use strict";
  // Disabled Tab Click Wizard
  $('#disabledTabWizard').bootstrapWizard({
    tabClass: 'nav nav-pills nav-justified',
    onNext: function(tab, navigation, index) {
    	var i=0;
        if(index==1){
        	var ischecked = false;
        	var ids = "";
        	$('#dtab1 tbody tr').each(function(){
        		var check = $(this).find('input[type=checkbox]');
        		var id = check.attr("id");
        		if(check.prop('checked')) {
        			ischecked = true;
        			if(i>0) ids += ",";
        			ids += id;
        			i = i+1;
        		}
        	});
        	if(ischecked){
        		$.post('../../product/admin/productlist.do',{"ids":ids},function(data){
        			$('#dtab2 div').empty();
        			var list = eval('('+data+')');
        			var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
					content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="checkAll(this)" /></th>';
					content += '<th style="width: 20%">商品品牌</th><th style="width: 20%">商品名称</th><th style="width: 20%">商品价格</th><th style="width: 20%">指派数量</th><th style="width: 15%">选择需要核减商户</th></tr></thead><tbody>';
        			for(var j=0;j<list.length;j++){
        				var obj = list[j];
        				content += '<tr class="gradeA"><td><input type="checkbox" id="'+obj.id+'" class="no-margin" /></td><td>'+Util.toStr(obj.product_brand_name)+'</td><td>'+Util.toStr(obj.name)+'</td><td>'+Util.toStr(obj.product_price)+'</td><td><input type="number" id="num" placeholder="请输入指派数量"/></td><td><a id="select'+obj.id+'" onclick="selectMinusSupplier('+obj.id+',this)" data-toggle="modal">选择</a>&nbsp;<label id="inputSupplier'+obj.id+'"></label></td></tr>';
        			}
        			content += '</tbody></table>';
        			$('#dtab2 div').append(content);
        		});
        	}else alert("请至少选择一个品牌！");
        	return ischecked;
        }else if(index==2){
        	var ischecked = false;
        	var isInputNum = false;
        	var isMinus = false;
        	i=0;
        	$('#dtab2 tbody tr').each(function(){
        		var check = $(this).find('input[type=checkbox]');
        		var num = $(this).find('#num').val();
        		var id = check.attr("id");
        		var checkSupplier = $(this).find('#inputSupplier'+id).attr('name');
        		if(check.prop('checked')&&!isInputNum&&!isMinus) {
        			ischecked = true;
        			if(parseInt(num)>0){
	        			if(!Util.empty(checkSupplier))i = i+1;
	        			else isMinus = true;
        			}else isInputNum = true;
        		}
        	});
        	if(!ischecked){
        		alert("请选择需要指派的商品信息！");
        	}else if(isInputNum){
        		ischecked=false;
        		alert("请输入已选择商品需要指派的数量！");
        	}else if(isMinus){
        		ischecked=false;
        		alert("已选择商品需要指定核减的商户！");
        	}else{
        		$.post("../../supplier/admin/toSupplierByParentCustomerid.do",function(data){
            		$('#dtab3 div').empty();
            		var list = eval('('+data+')');
        			var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
    				content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="checkAll(this)" /></th>';
    				content += '<th style="width: 20%">商户名称</th><th style="width: 20%">商户地址</th><th style="width: 20%">联系人</th><th style="width: 35%">联系方式</th></tr></thead><tbody>';
        			for(var j=0;j<list.length;j++){
        				var obj = list[j];
        				content += '<tr class="gradeA"><td><input type="checkbox" id="'+obj.id+'" class="no-margin" /></td><td>'+Util.toStr(obj.name)+'</td><td>'+Util.toStr(obj.address)+'</td><td>'+Util.toStr(obj.contacts_name)+'</td><td>'+Util.toStr(obj.contacts_phone)+'</td></tr>';
        			}
        			content += '</tbody></table>';
        			$('#dtab3 div').append(content);
            	});
        	}
        	return ischecked;
        }else if(index==3){
        	var ischecked = false;
        	i = 0;
        	var params = "[";
        	var data = "[";
        	$('#dtab2 div tbody tr').each(function(){
        		var check = $(this).find('input[type=checkbox]');
        		if(check.prop('checked')){
        			if(i>0) data+= ",";
        			var id = check.attr('id');
            		data += "{'id':'"+id+"','num':'"+$(this).find('#num').val()+"','minuspid':'"+$('#inputSupplier'+id).attr('name')+"'}";
            		i = i+1;
        		}
        	});
        	data += "]";
        	i = 0;
        	$('#dtab3 div tbody tr').each(function(){
        		var check = $(this).find('input[type=checkbox]');
        		if(check.prop('checked')){
        			ischecked=true;
        			if(i>0) params+= ",";
        			params += "{'supplierid':'"+$(this).find('input[type=checkbox]').attr('id')+"','data':"+data+"}";
            		i = i+1;
        		}
        	});
        	
        	params += "]";
        	if(ischecked){
	        	$.post('../../supplier/admin/toDesignSupplierProduct.do',{'params':params},function(data){
	        		if(Util.empty(data)) $('#result_tag').text("商品指派成功");
	        		else $('#result_tag').text(data);
	        	});
        	}else alert("请选择需要指派的商户！");
        	return ischecked;
        }
    },
    onPrevious: function(tab, navigation, index) {
    	
    },
    onTabClick: function(tab, navigation, index) {
      return false;
    }
  });
  
});
function checked(o){
	var checked = $(o).prop('checked');
	$('#dt_supplier tbody tr input[type=checkbox]').prop('checked',false);
	$(o).prop('checked',checked);
}
function onModelSelect(id){
	$('#dt_supplier tbody tr').each(function(){
		var checked = $(this).find('input[type=checkbox]');
		if(checked.prop('checked')) {
			$('#inputSupplier'+id).text($(this).find('#name').text());
			$('#inputSupplier'+id).attr('name',checked.attr('id'));
		}
	});
	$('#modalMd').modal('hide');
}
function selectMinusSupplier(productid,o){
	var store_num = $(o).parent().parent().find('#num').val();
	if(parseInt(store_num)>0){
		$.post("../../supplier/admin/toQuerySupplierByMinus.do",{"productid":productid,"store_num":store_num},function(data){
    		$('#dt_supplier').empty();
    		var list = eval('('+data+')');
			var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
			content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="checkAll(this)" /></th>';
			content += '<th style="width: 20%">商户名称</th><th style="width: 20%">商户地址</th><th style="width: 20%">联系人</th><th style="width: 35%">联系方式</th></tr></thead><tbody>';
			for(var j=0;j<list.length;j++){
				var obj = list[j];
				content += '<tr class="gradeA"><td><input type="checkbox" id="'+obj.id+'" class="no-margin" /></td><td id="name">'+Util.toStr(obj.name)+'</td><td>'+Util.toStr(obj.address)+'</td><td>'+Util.toStr(obj.contacts_name)+'</td><td>'+Util.toStr(obj.contacts_phone)+'</td></tr>';
			}
			content += '</tbody></table>';
			
			if(list.length==0) content = '无可以指派的商户';
			$('#dt_supplier').append(content);
			$('#model_check').attr('onclick','onModelSelect('+productid+')');
			$('#modalMd').modal();
    	});
	}else alert("请先输入需要指派的数量");
}
function checkAll(o){
	$(o).parent().parent().parent().parent().find('input[type=checkbox]').prop('checked',$(o).prop('checked'));
}
</script>
</body>
</html>
