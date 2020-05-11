<%@page import="com.yd.business.supplier.bean.SupplierUserBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierPackageProductRecordBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierPackageBean"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderProductBean"%>
<%@page import="com.yd.business.order.bean.ShopOrderEffProductBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderEffInfoBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SupplierUserBean spUser = (SupplierUserBean)request.getAttribute("spUser");

List<SupplierPackageProductRecordBean> spprList = (List<SupplierPackageProductRecordBean>)request.getAttribute("spprList");
Map<Integer,SupplierPackageProductRecordBean> map = new HashMap<Integer,SupplierPackageProductRecordBean>();
for(SupplierPackageProductRecordBean bean : spprList){
	map.put(bean.getSupplier_package_id(), bean);
}
%>

<!DOCTYPE HTML>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <base href="<%=basePath%>">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/favicon.ico">
	
    <title>用户套餐管理</title>
	<link rel="stylesheet" href="css/supplier/signup.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
 	<style type="text/css">
 	.panel {
 		margin-bottom:  10px;
 	}
 	</style>
 	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  </head>
 
  <body style="background-color: #eee;padding-top: 0px;">

	
    <div class="container-fluid" style="padding: 0;"><!-- 
      <div class="table-responsive"> -->
      <div class="">
      
      				<%for(Integer key : map.keySet()){
      					SupplierPackageProductRecordBean pack = map.get(key);
      				 %>
      				<div class="panel panel-primary ">
      					<div class="panel-heading"> 套餐名称：【<%=pack.getSupplier_package_name() %>】  <span style="float: right;">生效时间：<%=pack.getEff_time().substring(0, 10) %></span> </div>
						  <div class="panel-body">
				
	      				<%  int i = 0; 
	      					for(SupplierPackageProductRecordBean record : spprList){ 
	      					if(record.getSupplier_package_id() == key.intValue()){
	      				%>
      						   【<%=record.getSupplier_product_name() %>】 <span style="float: right;">失效时间：<%=pack.getDff_time().substring(0, 10) %></span> 
						   <div class="input-group">  
						   		<span class="input-group-addon" style="width: 40%;border: 0;background: white;">剩余数量：</span>
							  <span class="input-group-btn">
						        <button class="btn btn-default" type="button" onclick="changeNum(<%=record.getId()%>,1)">+</button>
						      </span>
							  <input type="number" id="<%=record.getId() %>" style="text-align: center;" class="form-control" aria-label="请输入数字" value="<%=record.getNum() %>" >
							  <span class="input-group-btn">
						        <button class="btn btn-default" type="button" onclick="changeNum(<%=record.getId()%>,-1)">-</button>
						      </span>
							<a style="margin-left: 25px;" onclick="submitNum(<%=record.getId() %>)" role="button" class="btn btn-primary pull-right"  >提交修改</a>
							</div>
      				<%}}	%>
      					  </div>
      				</div>
      				
      				<%}	//如果没有数据
      				if(spprList.size() == 0 ){
      				 %>
      				 <div class="panel panel-primary ">
      					<div class="panel-heading">【目前无数据】
      					</div>
						  <div class="panel-body">
						  	  该用户无套餐数据 
						  </div>
      				</div>
      				 <%} %>
      </div>
    </div> <!-- /container -->
</body>
<script type="text/javascript">

function changeNum(id,num){
	var value = Number($("#"+id).val());
	value += num;
	if(value<0){
		value = 0 ;
	}
	$("#"+id).val(value);
}

function submitNum(id){
	var value = $("#"+id).val();
	
	$.ajax({
       type : "POST",
       //请求地址
       url : "supplier/package/ajax/updatePackageNum.html",
       //数据，json字符串
       data : { recordId : id , num : value },
       //请求成功
       success : function(resultstr) {
       		if(resultstr == 'success'){
       			alert('修改成功！');
       		}else{
       			alert('修改失败：'+resultstr);
       		}
       },
       //请求失败，包含具体的错误信息
       error : function(e){
           alert("数据提交失败！" + e.responseText);
       }
   });
	
	
}
</script>
</html>
