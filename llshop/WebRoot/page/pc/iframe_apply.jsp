<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
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

  <title>商户管理申领商品</title>
<link href="css/new.css" rel="stylesheet">
<link href="css/style.default.css" rel="stylesheet">
</head>

<body onload="Apy.loadApplyCustomer();">
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						商户管理－<a id="inputs">申领商品</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
              <!-- BASIC WIZARD -->
              <div id="disabledTabWizard" class="basic-wizard">
                
                <ul class="nav nav-pills nav-justified">
                  <li><a href="#dt_example" data-toggle="tab"><span>第一步:</span> 选择客户</a></li>
                  <li><a href="#dtab2" data-toggle="tab"><span>第二步:</span> 选择商品</a></li>
                  <li><a href="#dtab3" data-toggle="tab"><span>第三步:</span> 确认提交</a></li>
                </ul>
                
                <div class="tab-content">
                  <div class="tab-pane" id="dt_example">
                    
                  </div>
                  <div class="tab-pane" id="dtab2">
                    
                  </div>
                  <div class="tab-pane" id="dtab3" style="text-align: center;">
                    <label id="result_tag"></label>
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
<script src="js/pc/apply.js"></script>
<script src="../../js/Util.js"></script>
<script>
var parcustid = -1;
$(document).ready(function(){
    "use strict";
  // Disabled Tab Click Wizard
  $('#disabledTabWizard').bootstrapWizard({
    tabClass: 'nav nav-pills nav-justified',
    onNext: function(tab, navigation, index) {
        if(index==1){
        	var isChecked = false;
        	$('#dt_example tbody input[type=checkbox]').each(function(){
        		var checked = $(this).prop('checked');
        		if(checked){
        			isChecked = true;
        			parcustid = $(this).attr('id');
        			var dtab2 = $('#dtab2');
        			dtab2.empty();
        			$.post("../../apply/admin/applyCustPro.do",{"parcustid":parcustid},function(data){
        				var dataList = eval('('+data+')');
        				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
						content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Apy.checkAll(this);"/></th><th style="width: 20%">商品名称</th>';
						content += '<th style="width: 15%">商品价格</th><th style="width: 15%">需要申请库存数</th><th style="width: 15%">备注</th></tr></thead><tbody id="apply_list">';
						for(var i=0;i<dataList.length;i++){
							var obj = dataList[i];
							var style = i%2==0?'success':'warn';
							content += '<tr id="'+obj.product_id+'" class="gradeA '+style+'"><td><input type="checkbox" id="'+obj.id+'" class="no-margin" /></td><td>'+Util.toStr(obj.product_name)+'</td>';
							content += '<td>'+obj.product_price+'</td>';
							content += '<td><input type="number" placeholder="请输入申请数量" id="apply_num"></td><td><input type="text" placeholder="请输入备注" id="remark"></td></tr>';
						}
						content += '</tbody></table>';
						dtab2.append(content);
						parent.document.getElementById("iframe-content").setAttribute("height", parent.document.getElementById("iframe-content").contentWindow.document.documentElement.scrollHeight);//重新计算界面高度
        			});
        		}
        	});
        	if(!isChecked) alert("请选择一个客户！");
        	return isChecked;
        }else if(index==2){
        	var result = true;
        	var ischecked = false;
        	$('#apply_list tr').each(function(){
        		var checked = $(this).find('input[type=checkbox]').prop('checked');
        		if(checked){
        			ischecked = true;
        			var id = $(this).find('input[type=checkbox]').attr("id");
        			var proid = $(this).attr('id');
        			var num = $(this).find('#apply_num').val();
        			var remark = $(this).find('#remark').val();
        			if(Util.empty(num)){
        				result = false;
        				alert("请输入需要申请的库存数！");
        			}else{
        				$.post("../../apply/admin/apply.do",{"id":id,"proid":proid,"custid":parcustid,"applynum":num,"remark":remark},function(data){
        					if(Util.empty(data)) $('#result_tag').text("工单申请提交成功!");
        					else $('#result_tag').text(data);
        				});
        			}
        		}
        	});
        	if(!ischecked) {
        		result = false;
        		alert("请至少选择一个需要申领的商品！");
        	}
        	return result;
        }
    },
    onPrevious: function(tab, navigation, index) {
    	
    },
    onTabClick: function(tab, navigation, index) {
      return false;
    }
  });
  
});
</script>
</body>
</html>
