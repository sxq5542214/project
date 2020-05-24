<%@page import="com.yd.business.supplier.bean.SupplierUserBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierStoreBalanceCardBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
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

List<SupplierStoreBalanceCardRecordBean> list = (List<SupplierStoreBalanceCardRecordBean>)request.getAttribute("recordList");
SupplierUserBean spUser  = (SupplierUserBean)request.getAttribute("spUser");
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
	
    <title>用户储值卡列表</title>
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
  	<form class="form-signup" >
  	
 		<label for="nick_name" class="sr-only" >用户名称</label>
        <input type="text" id="nick_name" name="nick_name" class="form-control" readonly="readonly" style="" placeholder="此处可修改用户的名称"  maxlength="20" value="名称：<%=StringUtil.convertNull(spUser.getNick_name()) %>">
        
        <label for="phone" class="sr-only" >手机号</label>
        <input type="number" id="phone" name="phone" class="form-control" readonly="readonly" style="" placeholder="该用户暂未提供手机号"   maxlength="20" value="号码：<%=StringUtil.convertNull(spUser.getPhone())%>">
    </form>
	
    <div class="container-fluid" style="padding: 0;"><!-- 
      <div class="table-responsive"> -->
      <div class="">
      				<%  int i = 0; String classStr = "";
      					for(SupplierStoreBalanceCardRecordBean record : list){ 
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
      					<div class="panel-heading">【<%=record.getName() %>】  </div>
						  <div class="panel-body">
						    折扣额度：<%=NumberUtil.divideHave100(record.getDiscount()) %>折，剩余金额：<%=NumberUtil.divideHave100(record.getBalance()) %>元<br>
						    到期时间：<%=record.getDff_time() %>
						   <a href="supplier/store/toUserBalanceCardManagerPage.html?openid=<%=record.getOpenid() %>&record_id=<%=record.getId() %>" style="margin-right: 5px;"  role="button" class="btn btn-info pull-right btn-xs"  >修改余额</a>&nbsp;&nbsp;
						  </div>
      				</div>
      				
      				
      				<%}		//如果没有数据
      				if(list.size() == 0 ){
      				 %>
      				 <div class="panel panel-primary ">
      					<div class="panel-heading">【目前无数据】  请先点击下方按钮，为该用户分配储值卡/余额卡
      					</div>
						  <div class="panel-body">
						  	  请点击下方按钮为用户分配储值卡/折扣卡。<br>以理发店为例，可实现如：用户预交100元，获得120元储值卡； 或获得100元的9折卡； 
						<!--   <a href=""  role="button" class="btn btn-success pull-right btn-xs"  >确定</a> -->
						  </div>
      				</div>
      				 <%} %>
      </div>
    </div> <!-- /container -->
    <div class="navbar navbar-fixed-bottom container-fluid">
    	<div class="row">
	    	<div style="text-align: center;">
	    	
				<a href="supplier/store/toAssignBalanceCardToUserPage.html?openid=<%=spUser.getOpenid() %>" style="width: 80%;" role="button" class="btn btn-primary "  >分配新的折扣卡/储值卡</a>
 				<a onclick="history.go(-1);" style="width: 80%;margin-top: 10px;" role="button" class="btn btn-info " >返回</a>
	    	</div>
    	</div>
	</div>
</body>
<script type="text/javascript">
//	$(".cancel-3").attr("class","panel panel-danger");
	window.parent.setTitle(document.title);
</script>
</html>
