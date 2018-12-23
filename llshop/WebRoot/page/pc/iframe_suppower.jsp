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

  <title>折扣管理－分配折扣规则</title>
<link href="css/new.css" rel="stylesheet">
  <link href="css/style.default.css" rel="stylesheet">
	
</head>

<body>
<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						折扣管理－<a id="inputs">分配折扣规则</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
              <!-- BASIC WIZARD -->
              <div id="disabledTabWizard" class="basic-wizard">
                
                <ul class="nav nav-pills nav-justified">
                  <li><a href="#dtab1" data-toggle="tab"><span>第一步:</span> 选择商户</a></li>
                  <li><a href="#dtab2" data-toggle="tab"><span>第二步:</span> 选择折扣规则</a></li>
                  <li><a href="#dtab3" data-toggle="tab"><span>第三步:</span> 确认提交</a></li>
                </ul>
                
                <div class="tab-content">
                  <div class="tab-pane" id="dtab1">
                  	<div id="dt_example"></div>
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
<script src="js/pc/power.js"></script>
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
        	if(isChecked)Pow.loadDisGroup();
        	else alert('请选择一个需要授权的商户');
        	return isChecked;
        }else if(index==2){
        	var isChecked = false;
        	$('#dtab2 div table tbody').find('input[type=checkbox]').each(function(){
        		if($(this).prop('checked')) isChecked=true;;
        	});
        	if(isChecked)Pow.commitData();
        	else alert('请至少选择一个授权规则');
        	return isChecked;
        }
    },
    onPrevious: function(tab, navigation, index) {
    },
    onTabClick: function(tab, navigation, index) {
      return false;
    }
  });
  //初始化信息
  Pow.loadCust();
});
</script>
</body>
</html>
