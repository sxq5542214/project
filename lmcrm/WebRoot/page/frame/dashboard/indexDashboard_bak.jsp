<%@page import="com.yd.business.price.bean.PriceBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
  <head>
    <!-- 必须的 meta 标签 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <base href="<%=basePath%>">
    
    <!-- Bootstrap 的 CSS 文件 -->


	<link rel="stylesheet" href="/staticFiles/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="/staticFiles/jquery@3.5.1/dist/jquery.min.js" ></script>
 	<script src="/staticFiles/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
	<script src="/staticFiles/vue/dist/vue.js"></script>
    <script src="/staticFiles/echarts@4.7.0/dist/echarts.min.js" type="text/javascript"></script>
    <title>龙马水厂收费系统</title>
    <style type="text/css">
    .col-xs-8{padding: 1px;}
    .col-xs-4{padding: 1px;}
    .container{padding: 1px; margin-right: 1px; margin-left : 1px;max-width:1940px; }
    </style>
  </head>
 
<body> 

<div class="container" id="dashboardDiv">

	<div class="card bg-light mb-3" style="width: 98%;margin: 0 auto;">
		<div class="card-header">店铺重要信息总览</div>
		<div class="card-body" id="shopYesterdayInfo"> 
			<div class="row" style="margin: 0 auto;">
			  	<div class="card text-white bg-info mb-3" style="max-width: 33%;margin: 3px;">
				  <div class="card-header">近一周增长用户数</div>
				  <div class="card-body">
				    <h5 class="card-title">{{cardTitle1}}</h5>
				    <p class="card-text">{{cardText1}}</p>
				  </div>
				</div>
				<div class="card text-white bg-primary mb-3" style="max-width: 33%;margin: 3px;">
				  <div class="card-header">待完善。。。。</div>
				  <div class="card-body">
				    <h5 class="card-title">{{cardTitle2}}</h5>
				    <p class="card-text">{{cardText2}}</p>
				  </div>
				</div>
				<div class="card text-white bg-success mb-3" style="max-width: 33%;margin: 3px;">
				  <div class="card-header">待完善。。。。</div>
				  <div class="card-body">
				    <h5 class="card-title">{{cardTitle3}}</h5>
				    <p class="card-text">{{cardText3}}</p>
				  </div>
				</div>
		  	</div>
		  	
		</div>
	</div>
	<div class="card bg-light mb-3" style="width: 98%;margin: 0 auto;">
		<div class="card-header">我的近七日收费情况</div>
		<div class="card-body">
		  	<div id="weekChargeInfoChart" style="width: 100%;height:250px;"></div>
		  </div>
	</div>
	<div class="card bg-light mb-3" style="width: 98%;margin: 0 auto;">
		<div class="card-header">店铺重要信息总览（待完善）</div>
		<div class="card-body">
		  	<div id="effOrderChart" style="width: 100%;height:250px;"></div>
		  </div>
	</div>
	    
 	 
</div>

</body>

<script src="js/common/dictionaryData.js" type="text/javascript"></script>
<script src="page/frame/dashboard/js/indexDashboard.js" type="text/javascript"></script>

<script type="text/javascript">

</script>
</html>