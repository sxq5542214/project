<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	List<SupplierBean> list = (List<SupplierBean>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">

  <title>商户管理－商品授权</title>
<link href="css/new.css" rel="stylesheet">
  <link href="css/style.default.css" rel="stylesheet">
	
</head>

<body>
<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						商户管理－<a id="inputs">商品授权</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
              <!-- BASIC WIZARD -->
              <div id="disabledTabWizard" class="basic-wizard">
                
                <ul class="nav nav-pills nav-justified">
                  <li><a href="#dtab1" data-toggle="tab"><span>第一步:</span> 选择商户</a></li>
                  <li><a href="#dtab2" data-toggle="tab"><span>第二步:</span> 选择商品品牌分类</a></li>
                  <li><a href="#dtab3" data-toggle="tab"><span>第三步:</span> 确认提交</a></li>
                </ul>
                
                <div class="tab-content">
							<div class="tab-pane" id="dtab1">
								<div id="dt_example">
									<table
										class="table table-condensed table-striped table-hover table-bordered pull-left"
										id="data-table">
										<thead>
											<tr>
												<th style="width: 5%"><input type="checkbox" class="no-margin" onclick="checkAll(this)" /></th>
												<th style="width: 14%">商户名称</th>
												<th style="width: 14%">当前状态</th>
												<th style="width: 14%">所在地址</th>
												<th style="width: 14%">添加时间</th>
												<th style="width: 14%">修改时间</th>
												
											</tr>
										</thead>
										<tbody>
											<%
											for(int i=0;i<list.size();i++){
												SupplierBean bean = list.get(i);
												%>
											<tr class="gradeX <%=i%2==0?"success":"warning"%>">
												<td><input id="<%=bean.getId() %>" type="checkbox" class="no-margin" /></td>
												<td><%=NumberUtil.toString(bean.getName()) %></td>
												<td><%=NumberUtil.toString(bean.getDictValueByField("status", bean.getStatus())) %></td>
												<td><%=NumberUtil.toString(bean.getAddress()) %></td>
												<td><%=NumberUtil.toString(bean.getCreate_time()) %></td>
												<td><%=NumberUtil.toString(bean.getModify_time()) %></td>
											</tr>
												<%
											}
											%>
										</tbody>
									</table>
								</div>
							</div>
							<div class="tab-pane" id="dtab2">
                  	<div id="dt_example"></div>
                  </div>
                  <div class="tab-pane" id="dtab3">
                    <label id="result"></label>
                  </div>
                </div><!-- tab-content -->
                
                <ul class="pager wizard">
                    <li class="previous"><a href="javascript:void(0)">上一步</a></li>
                    <li class="next"><a href="javascript:void(0)">下一步</a></li>
                  </ul>
                
              </div><!-- #disabledTabWizard -->
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
$(document).ready(function(){
    
    "use strict";
  
  // Disabled Tab Click Wizard
  $('#disabledTabWizard').bootstrapWizard({
    tabClass: 'nav nav-pills nav-justified',
    onNext: function(tab, navigation, index) {
        if(index==1){
        	var isChecked = false;
        	$('#dtab1 div table tbody').find('input[type=checkbox]').each(function(){
        		if($(this).prop('checked')) isChecked=true;;
        	});
        	if(isChecked) {
        		$('#dtab2 div').empty();
        		$.post('../../supplierProduct/admin/queryProductTypeByCustid.do',function(data){
        			var list = eval('('+data+')');
        			var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left"id="data-table"><thead><tr>';
    				content += '<th style="width: 10%"><input type="checkbox" class="no-margin" onclick="checkAll(this)" /></th><th style="width: 20%">品牌名称</th><th style="width: 20%">分类名称</th><th style="width: 50%">备注</th></tr></thead><tbody>';
    				for ( var i = 0; i < list.length; i++) {
    					var obj = list[i];
    					var style = i%2==0?'warn':'success';
    					content += '<tr class="gradeX '+style+'"><td><input type="checkbox" id="'+obj.id+'" protype="'+obj.type+'" class="no-margin" /></td><td id="name">'+Util.toStr(obj.name)+'</td><td>'+Util.toStr(obj.typeName)+'</td><td>'+Util.toStr(obj.remark)+'</td></tr>';
    				}
    				content += '</tbody></table><div class="clearfix"></div>';
    				$('#dtab2 div').append(content);
        		});
        	}else alert('请选择一个需要授权的商户');
        	return isChecked;
        }else if(index==2){
        	var isChecked = false;
        	var types = "[";
        	var params = "[";
        	var index = 0;
        	$('#dtab2 div table tbody tr').each(function(){
        		var checked = $(this).find('input[type=checkbox]');
        		if(checked.prop("checked")) {
        			isChecked=true;
        			if(index>0) types+=",";
        			types += "{'id':'"+checked.attr("id")+"','name':'"+$(this).find('#name').text()+"','type':'"+checked.attr("protype")+"'}";
        			index =index+1;
        		}
        	});
        	types += "]";
        	index = 0;
        	$('#dtab1 div table tbody tr').each(function(){
        		var checked = $(this).find('input[type=checkbox]');
        		if(checked.prop('checked')) {
        			if(index>0) params+=",";
        			params += "{'supplierid':'"+checked.attr("id")+"','data':"+types+"}";
        			index=index+1;
        		}
        	});
        	params+="]";
        	
        	if(isChecked){
        		$.post("../../supplier/admin/powerSupProduct.do",{"params":params},function(data){
        			if(Util.empty(data)) data = "商品授权成功！";
        			$("#result").text(data);
        		});
        	}else alert('请至少选择一个品牌');
        	return isChecked;
        }
    },
    onPrevious: function(tab, navigation, index) {
    },
    onTabClick: function(tab, navigation, index) {
      return false;
    }
  });
  
});
function checkAll(o){
	  $(o).parent().parent().parent().parent().find('input[type=checkbox]').prop('checked',$(o).prop('checked'));
}
</script>
</body>
</html>
