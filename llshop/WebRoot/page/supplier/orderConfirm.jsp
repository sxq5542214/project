<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@page import="com.yd.business.customer.bean.CustomerAdminBean"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SupplierProductBean sp = (SupplierProductBean)request.getAttribute("supplierProduct");
CustomerAdminBean admin = (CustomerAdminBean)request.getAttribute("admin");
String phone = (String)request.getAttribute("phone");
SupplierBean supplier = (SupplierBean)request.getAttribute("supplier");
boolean flag = supplier.getBalance() >= sp.getProduct_price();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide{display:none !important;}ng\:form{display:block;}.ng-animate-block-transitions{transition:0s all!important;-webkit-transition:0s all!important;}.ng-hide-add-active,.ng-hide-remove{display:block!important;}</style>
    <title>支付订单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Cache-Control" content="max-age=300">
    <meta content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
   
	<link rel="stylesheet" type="text/css"	href="css/supplier/stylesd.css">
	<link rel="stylesheet" type="text/css"	href="css/supplier/reset.css">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/supplier/orderConfirm.js"></script>
</head>
<body class="ng-scope">
<div><!-- ngView: undefined --><ng-view class="ng-scope">

<div   class="ng-scope">
<div class="width320">
  <div class="con">
    <h3>支付确认</h3>
    <h2>支付金额：¥<span id="price" class="ng-binding"><%=sp.getProduct_price()/100d  %></span>元</h2>
  </div>

  <div class="menu">
    <div class="downmenu">
      <h2>支付内容：<span  class="ng-binding"><%=phone %></span>&nbsp;购买<span  class="ng-binding"><%=sp.getProduct_name() %></span></h2>
    </div>
    
    <div class="downmenu">
      <h2>操作人：<span  class="ng-binding"><%=admin.getNickname() %></span></h2>
    </div>
    
    <div class="downmenu">
      	<h2>您的余额：<span class="ng-binding" ><%=supplier.getBalance() /100d %></span>元</h2>
       	<!-- <div onclick="changePointsBtn(this)" id="closeBg" class="pointclose">
        	<div id="btnBg"  class="btnclose"></div>
       	</div> -->
    </div>
  <div class="clear"></div>
  </div>
  
<div class="ejqrzf" style="width: 98%" id="show">
  <a href="javascript:;" class="hrefbtn" onclick="pay();" >  确认订购 </a>
 <!-- 
    <a href="#" class="ejqr" >确认</a>
    <a href="#" class="ejqx" >取消</a> -->
</div>
<div id="loader" class="ejqrzf" style="width: 98%;display: none;">
        	<img alt="正在查询" src="images/ajax-loader-7.gif"><br>
        	<!-- 正在使劲抢啊抢啊抢，请耐心等待 -->
</div>
  
<div class="clear"></div>
</div>


</div></ng-view></div>
<form id="supplierOrderProduct" name="supplierOrderProduct" action="order/supplierOrderProduct.do" method="post">
<input id="adminid" name="adminid" type="hidden" value="<%=admin.getId()%>">
<input id="balanceFlag" name="balanceFlag" type="hidden" value="<%=flag %>">
<input id="product_id" type="hidden" value="<%=sp.getId()%>">
<input id="phone" type="hidden" value="<%=phone%>">
</form>
</body></html>
