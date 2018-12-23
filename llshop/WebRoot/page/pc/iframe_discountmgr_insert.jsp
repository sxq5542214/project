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

  <title>商户管理-新增折扣规则</title>
<link href="css/new.css" rel="stylesheet">
<link href="css/style.default.css" rel="stylesheet">
</head>

<body onload="Dis.listProType();">
<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						折扣管理－<a id="inputs">新增折扣规则</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
              <!-- BASIC WIZARD -->
              <div id="disabledTabWizard" class="basic-wizard">
                
                <ul class="nav nav-pills nav-justified">
                  <li><a href="#dtab1" data-toggle="tab"><span>第一步:</span> 选择品牌</a></li>
                  <li><a href="#dtab2" data-toggle="tab"><span>第二步:</span> 选择设置规则</a></li>
                  <li><a href="#dtab3" data-toggle="tab"><span>第三步:</span> 确认提交</a></li>
                </ul>
                
                <div class="tab-content">
                  <div class="tab-pane" id="dtab1">
                  </div>
                  <div class="tab-pane" id="dtab2">
                  </div>
                  <div class="tab-pane" id="dtab3">
                    <form class="form-horizontal row-border">
                    	<div class="form-group">
                    		<label class="col-md-2 control-label">折扣规则名称</label>
							<div class="col-md-10">
								<input type="text" name="name" id="name" placeholder="请输入规则名称" class="form-control">
							</div>
                    	</div>
                    	<div class="form-group">
                    		<label class="col-md-2 control-label">规则列表</label>
                    		<div class="col-md-10" id="dis_list">
	                    		
							</div>
                    	</div>
                    	<div class="form-group">
                    		<label class="col-md-2 control-label">备注</label>
							<div class="col-md-10">
								<textarea rows="5" class="form-control" id="remark" placeholder="备注信息"></textarea>
							</div>
                    	</div>
                    	<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="button" name="addDis" id="addDis" onclick="Dis.insertDisGroup();" class="btn btn-info" value="添加折扣规则"/>  <a type="submit" href="javascript:window.location.href='discount.do';" class="btn btn-warning">取消并返回折扣列表</a>
							</div>
						</div>
                    </form>
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
<script src="js/pc/discount.js"></script>
<script src="../../js/Util.js"></script>
<script>
$(document).ready(function(){
  "use strict";
  // Disabled Tab Click Wizard
  $('#disabledTabWizard').bootstrapWizard({
    tabClass: 'nav nav-pills nav-justified',
    onNext: function(tab, navigation, index) {
    	if(index==1){
    		var tab2 = $('#dtab2');
        	tab2.empty();
        	var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table_pro">';
    		content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" checked onclick="Dis.checkAll(this,\'pro_list_set\');" /></th><th style="width: 35%">品牌名称</th>';
    		content += '<th style="width: 60%">设置折扣</th></tr></thead><tbody id="pro_list_set">';
           	var isChecked = false;
    		$('#pro_list input[type=checkbox]').each(function(){
            	var checkbox = $(this);
            	if(checkbox.prop('checked')){
            		isChecked = true;
            		var proid = checkbox.prop('id');
            		var proname = checkbox.parent().parent().find('#proname').text();
    				content += '<tr class="gradeA warn"><td><input type="checkbox" checked class="no-margin" id="'+proid+'"/></td>';
    				content += '<td><label id="proname">'+proname+'</label></td><td>';
    				content += '<table><tbody id="pro_list_dis">';
                    content += '<tr><td><a></a></td><td align="center"><div class="input-group"><input type="number" value="0" placeholder="最小金额" id="minprice" class="form-control"><span class="input-group-addon">元</span></div></td>';
                    content += '<td>至</td><td><div class="input-group"><input type="number" value="0" placeholder="最大金额" id="maxprice" class="form-control"><span class="input-group-addon">元</span></div></td>';
                    content += '<td><div class="input-group"><input type="number" placeholder="折扣额度(0-100)" value="100" id="discount" class="form-control"><span class="input-group-addon">折</span></div></td></tr></tbody></table>';
                    content += '<button class="btn btn-success" onclick="addRow(this);">新增</button>';
                    content += '</td></tr>';
            	}
            });
            content += '</tbody></table>';
            tab2.append(content);
            if(!isChecked) alert("请至少需要选择一个品牌进行折扣规则设置");
            var height = parent.document.getElementById("iframe-content").contentWindow.document.documentElement.scrollHeight;
            parent.document.getElementById("iframe-content").setAttribute("height", tab2.height()+height-200);//重新计算界面高度
			return isChecked;
    	}else{
    		var disList = $('#dis_list');
    		disList.empty();
        	var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table_pro">';
    		content += '<thead><tr><th style="width: 35%">品牌名称</th>';
    		content += '<th style="width: 60%">设置折扣</th></tr></thead><tbody>';
           	var isChecked = false;
           	var prompt = '';
    		$('#pro_list_set input[type=checkbox]').each(function(){
            	var checkbox = $(this);
            	if(checkbox.prop('checked')){
            		isChecked = true;
            		var proid = checkbox.prop('id');
            		var proname = checkbox.parent().parent().find('#proname').text();
            		var proListDis = checkbox.parent().parent().find('#pro_list_dis');
            		var arrays = new Array();
            		var idx = 0;
            		proListDis.find('tr').each(function(){//获取品牌规则信息
            			var obj = $(this);
            			var minprice = obj.find('#minprice').val();
            			var maxprice = obj.find('#maxprice').val();
            			var discount = obj.find('#discount').val();
            			if(Util.empty(prompt)) {
            				if(Util.empty(minprice)) prompt +='['+proname+':最小金额不能为空';
                    		if(Util.empty(maxprice)) prompt +=';最大金额不能为空';
                    		if(Util.empty(discount)) prompt +=';折扣额度不能为空';
                    		if(Util.notEmpty(prompt)) prompt +=']\r\n';
            				if(parseInt(minprice)>parseInt(maxprice)&&Util.empty(prompt)) prompt+='［'+proname+'('+minprice+'-'+maxprice+')］最小金额不能大于最大金额，请检查填写规则！';
            				else{
            					var obj = {};
                				obj.minprice = minprice;
                				obj.maxprice = maxprice;
                				obj.discount = discount;
                				if(idx==0){
                					arrays[idx]=obj;
                					idx++;
                				}else{
                					var o = arrays[idx-1];
            						if(parseInt(o.maxprice)>parseInt(obj.minprice)){
            							prompt+='['+proname+']折扣规则['+o.minprice+'-'+o.maxprice+'与'+obj.minprice+'－'+obj.maxprice+']存在交叉，请检查填写规则!';
            						}else if(parseInt(o.maxprice)!=parseInt(obj.minprice)){
            							if(Util.empty(prompt)) prompt+='\r\n';
            							prompt+='['+proname+']折扣规则['+o.minprice+'-'+o.maxprice+'与'+obj.minprice+'－'+obj.maxprice+']存在空白区域，请检查填写规则!';
            						}
                					if(Util.empty(prompt)){
                						arrays[idx]=obj;
            							idx++;
                					}
                				}
            				}
            			}
            		});
            		content += '<tr class="gradeA warn"><td>'+proname+'</td><td>';
            		for(var i=0;i<arrays.length;i++){
            			var obj = arrays[i];
            			content += '['+obj.minprice+'至'+obj.maxprice+'／'+obj.discount+'折]<br/>';
            		}
            		content += '</td></tr>';
            	}
            });
            content += '</tbody></table>';
            disList.append(content);
            if(!isChecked) alert("请至少需要选择一个品牌进行折扣规则设置");
            else{
            	if(Util.notEmpty(prompt)) {
            		isChecked = false;
            		alert("以下品牌规则设置不完整，请补充：\r\n"+prompt);
            	}
            }
            setIframeHeight(index);
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
function addRow(o){
	$(o).parent().find('#pro_list_dis').append('<tr><td><button class="btn btn-danger" onclick="deleteRow(this);">－</button></td><td align="center"><div class="input-group"><input type="number" value="0" placeholder="最小金额" id="minprice" class="form-control"><span class="input-group-addon">元</span></div></td><td>至</td><td><div class="input-group"><input type="number" placeholder="最大金额" value="0" id="maxprice" class="form-control"><span class="input-group-addon">元</span></div></td><td><div class="input-group"><input type="number" placeholder="折扣额度(0-100)" value="100" id="discount" class="form-control"><span class="input-group-addon">折</span></div></td></tr>');
	setIframeHeight();
}
function deleteRow(o){
	o.parentNode.parentNode.remove();
}
function setIframeHeight(index){
	var height = parent.document.getElementById("iframe-content").contentWindow.document.documentElement.scrollHeight;
	var offsetHeight = document.body.offsetHeight;
	if(offsetHeight>height) height = offsetHeight;
	if(index==2) height = height+150;
	parent.document.getElementById("iframe-content").setAttribute("height", height);//重新计算界面高度
}
</script>
</body>
</html>
