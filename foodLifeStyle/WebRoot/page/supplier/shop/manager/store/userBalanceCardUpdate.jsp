<%@page import="com.yd.business.supplier.bean.SupplierStoreBalanceCardBean"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean"%>
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

SupplierStoreBalanceCardRecordBean record = (SupplierStoreBalanceCardRecordBean)request.getAttribute("record");
SupplierStoreBalanceCardBean card = (SupplierStoreBalanceCardBean)request.getAttribute("card");

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
	
    <title>用户折扣卡管理</title>
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
      
      				<div class="panel panel-primary ">
      					<div class="panel-heading"> 卡名：【<%=card.getName() %>】  <span style="float: right;">折扣：<%=NumberUtil.divideHave100(card.getDiscount()) %>折</span> </div>
						  <div class="panel-body">
								<input type="hidden" value="<%=record.getId() %>" id="id" name="id" >
								<input type="hidden" value="<%=spUser.getOpenid() %>" id="openid" name="openid" >
      						   <!-- 【】 <span style="float: right;"></span>  -->
						   <div class="input-group" style="width: 100%;">  
						   		<div class="input-group" style="width: 90%;">
						   		<span class="input-group-addon" style="width: 40%;border: 0;background: white;">用户昵称：</span>
							  
							    <input type="text" disabled="disabled" style="text-align: center;" class="form-control" aria-label="请输入数字" value="<%=spUser.getNick_name() %>" >
						   </div>
						   <div class="input-group" style="width: 100%;margin-top: 5px;">  
						   		<div class="input-group" style="width: 90%;">
						   		<span class="input-group-addon" style="width: 40%;border: 0;background: white;">当前余额：</span>
							  
							    <input type="number" step="0.01" id="balance" name="balance" readonly="readonly" style="text-align: center;" class="form-control" aria-label="请输入数字" value="<%=NumberUtil.divideHave100(record.getBalance()) %>" >
						   </div>
						   <div class="input-group" style="width: 100%;margin-top: 5px;">  
						   		<div class="input-group" style="width: 90%;">
						   		<span class="input-group-addon" style="width: 40%;border: 0;background: white;">扣减余额：</span>
							  
							    <input type="number" step="0.01" id="addBalance" name="addBalance" style="text-align: center;" class="form-control" aria-label="请输入数字" value="0" >
						   </div>
						   
						   <div class="input-group" style="width: 100%;margin-top: 5px;">  
						   		<div class="input-group" style="width: 90%;">
						   		<span class="input-group-addon" style="width: 40%;border: 0;background: white;">失效时间：</span>
							  
							    <input type="datetime" id="dff_time" name="dff_time" style="text-align: center;" class="form-control" value="<%=record.getDff_time() %>" >
						   </div>
							<a style="margin-top: 10px;" onclick="submitUpdate()" role="button" class="btn btn-primary pull-right"  >提交修改</a>
							</div>
      					  </div>
      				</div>
      				<%
      					//如果没有数据
      				if(record == null ){
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
    
     <div class="navbar navbar-fixed-bottom container-fluid">
    	<div class="row">
	    	<div style="text-align: center;">
				<a onclick="history.go(-1);" style="width: 80%;" role="button" class="btn btn-info "  >返回</a>
	    	</div>
    	</div>
	</div>
</body>
<script type="text/javascript">


function submitUpdate(){
	var id = $("#id").val();
	var openid = $("#openid").val();
	var addBalance = -$("#addBalance").val();
	var balance = $("#balance").val();
	var dff_time = $("#dff_time").val();
	$.ajax({
       type : "POST",
       //请求地址
       url : "supplier/store/ajax/updateUserStoreCardRecordBalance.html",
       //数据，json字符串
       data : { id : id , openid : openid , addBalance:addBalance, dff_time:dff_time},
       //请求成功
       success : function(resultstr) {
       		if(resultstr == 'success'){
       			alert('修改成功！');
       			location.reload();
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
