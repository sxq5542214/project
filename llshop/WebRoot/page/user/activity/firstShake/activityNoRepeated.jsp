<%@page import="com.yd.business.activity.bean.ActivityUserRelationBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ActivityUserRelationBean relation = (ActivityUserRelationBean)request.getAttribute("relation");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide{display:none !important;}ng\:form{display:block;}.ng-animate-block-transitions{transition:0s all!important;-webkit-transition:0s all!important;}.ng-hide-add-active,.ng-hide-remove{display:block!important;}</style>
    <title>不可重复参与</title>
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
    <h2><%=relation.getPhone()%>已经参与该活动！</h2>
  </div>

  <div class="menu" style="margin: 0;">
    <div class="downmenu" style="height: auto;">
      <h2><span  class="ng-binding" >该号码已经参与过该活动！请勿重复领取！</span><br>
      	<span  class="ng-binding" >不过，下一期免费福利很快就来啦！</span><br>
       </h2>
    </div>
     <div class="downmenu">
    </div>
  <div class="clear"></div>
  </div>
<div class="clear"></div>
</div>
</div></ng-view></div>
</body></html>
