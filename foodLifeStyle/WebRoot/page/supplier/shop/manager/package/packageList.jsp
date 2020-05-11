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
String openid = request.getParameter("openid");
String sid = request.getParameter("sid");

List<SupplierPackageBean> listPackage = (List<SupplierPackageBean>)request.getAttribute("listPackage");

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
	
    <title>产品套餐管理</title>
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
      				<%  int i = 0; String classStr = "";
      					for(SupplierPackageBean pack : listPackage){ 
      					switch(i++ % 3){
      						case 0:	classStr = "primary";
      						break;
      						case 1: classStr = "success";
      						break;
      						case 2: classStr = "warning";
      						break;
      					}
      				%>
      				<div class="panel panel-<%=classStr %> ">
      					<div class="panel-heading">【<%=pack.getName() %>】  </div>
						  <div class="panel-body">
						    <%=pack.getTitle() %>
						   <a href="supplier/package/toPackageAssignUserPage.html?packageid=<%=pack.getId()%>"  role="button" class="btn btn-info pull-right btn-xs"  >分配客户</a>&nbsp;&nbsp;
						   <a href="supplier/package/toAddSupplierPackagPage.html?id=<%=pack.getId() %>" style="margin-right: 5px;"  role="button" class="btn btn-info pull-right btn-xs"  >修改套餐</a>&nbsp;&nbsp;
						  </div>
      				</div>
      				
      				
      				<%}		//如果没有数据
      				if(listPackage.size() == 0 ){
      				 %>
      				 <div class="panel panel-primary ">
      					<div class="panel-heading">【目前无数据】  请先创建产品和产品分类，再创建套餐
      					</div>
						  <div class="panel-body">
						  	  请点击下方按钮新增产品套餐。<br>以理发店为例，可实现如：用户预交100元，获得10次精剪+洗头+吹发的产品 
						<!--   <a href=""  role="button" class="btn btn-success pull-right btn-xs"  >确定</a> -->
						  </div>
      				</div>
      				 <%} %>
      </div>
    </div> <!-- /container -->
    <div class="navbar navbar-fixed-bottom container-fluid">
    	<div class="row">
	    	<div style="text-align: center;">
				<a href="supplier/package/toAddSupplierPackagPage.html?sid=<%=sid %>" style="width: 40%;" role="button" class="btn btn-info "  >新增产品套餐</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a  style="width: 40%;" role="button" class="btn btn-primary " >删除产品套餐</a>
	    	</div>
    	</div>
	</div>
</body>
<script type="text/javascript">
//	$(".cancel-3").attr("class","panel panel-danger");
//	window.parent.setTitle('');
</script>
</html>
