<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
OrderProductLogBean bean = (OrderProductLogBean)request.getAttribute("orderLog");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide{display:none !important;}ng\:form{display:block;}.ng-animate-block-transitions{transition:0s all!important;-webkit-transition:0s all!important;}.ng-hide-add-active,.ng-hide-remove{display:block!important;}</style>
    <title>订购结果</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Cache-Control" content="max-age=300">
    <meta content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
   
	<link rel="stylesheet" type="text/css"	href="css/supplier/stylesd.css">
	<link rel="stylesheet" type="text/css"	href="css/supplier/reset.css">
</head>
<body class="ng-scope">
<div><!-- ngView: undefined --><ng-view class="ng-scope">

<div   class="ng-scope">
<div class="width320">

  <div class="menu">
  
    <div class="downmenu">
      <h2>支付方式：<span  class="ng-binding">库存支付</span></h2>
    </div>
  
    <div class="downmenu">
      <h2>订购号码：<span  class="ng-binding"><%=bean.getOrder_account() %> </span></h2>
    </div>
    
    <div class="downmenu">
      <h2>订购商品：<span  class="ng-binding"><%=bean.getProduct_name() %> </span></h2>
    </div>
    
    <div class="downmenu">
      <h2>订购结果：<span  class="ng-binding"><%=bean.getDictValueByField(OrderProductLogBean.DICT_FIELD_STATUS) %> </span></h2>
    </div>
    
    <div class="downmenu">
      <h2>结果说明：<span  class="ng-binding"><%=bean.getRemark() %></span></h2>
    </div>
    
    <div class="downmenu">
      <h2>订单号：<span  class="ng-binding"><%=bean.getOrder_code() %></span></h2>
    </div>
    
  <div class="clear"></div>
  </div>
  
<div class="clear"></div>
</div>


</div></ng-view></div>
</body></html>
