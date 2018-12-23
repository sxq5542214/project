<%@page import="com.yd.business.activity.bean.ActivityOlympicGuessBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ActivityOlympicGuessBean bean = (ActivityOlympicGuessBean)request.getAttribute("bean");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide{display:none !important;}ng\:form{display:block;}.ng-animate-block-transitions{transition:0s all!important;-webkit-transition:0s all!important;}.ng-hide-add-active,.ng-hide-remove{display:block!important;}</style>
    <title>活动结果</title>
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
  <div class="con">
    <h3>活动结果说明</h3>
    <h2>活动结果：<span id="price" class="ng-binding"><%=bean.getDictValueByField(OrderProductLogBean.DICT_FIELD_STATUS) %></span></h2>
  </div>

  <div class="menu">
    <div class="downmenu">
      <h2>竞猜日期：<span  class="ng-binding"><%=bean.getGuess_date() %></span></h2>
    </div>
  
    <div class="downmenu">
      <h2>我竞猜的奖牌数：<span  class="ng-binding"><%=bean.getGuess_num() %>枚 </span></h2>
    </div>
    
    <div class="downmenu">
      <h2>实际获奖奖牌数：<span  class="ng-binding"><%=bean.getWin_num() %>枚 </span></h2>
    </div>
    
    
    <div class="downmenu">
      <h2>结果说明：<span  class="ng-binding"><%=bean.getWin_desc() %></span></h2>
    </div>
    
  <div class="clear"></div>
  </div>
  
  
<div class="clear"></div>
</div>


</div></ng-view></div>
</body></html>
