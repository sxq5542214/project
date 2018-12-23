<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<OrderProductLogBean> list = (List<OrderProductLogBean>) request.getAttribute("list");
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>全民小商城</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
</head>

<body>

<div id="_contain">

<div class="banner">
	<img src="images/index/banner.png">
    <div class="logo-dbs">
    	<div class="dbs-img">
        	<img src="images/icon/logo.png">
        </div>
        <p>全球低价游，满足你世界梦！</p>
    </div>
</div>

<div class="favorable" style="text-align: center;margin: 0 auto;">
<!-- 
<div class="fb">
    <div class="fb-lt">
    	<img src="images/scenics/scenic_3.png">
        <p class="fb-name">塞班岛5-6天</p>
        <p class="price">
        	<span class="discount"><big>4.7</big>折</span>
            <span class="num"><big>5699</big>元起</span>
        </p>
    </div>
</div> -->

<% for(OrderProductLogBean opl : list){  %>
<div class="fb" style="height: auto;width: 98%;">
    <div class="fb-lt" style="width: 100%;">
    	<!-- <img src="images/scenics/scenic_3.png"> -->
    	
        <p class="price" >
        	<span class="discount" style="font-size: 0.4rem;margin: 0;">订单号：<big><%=opl.getOrder_code() %></big></span>
            <span class="num" style="font-size: 0.4rem;">商品名称：<big><%=opl.getProduct_name() %></big></span>
            <span class="discount" style="font-size: 0.4rem;margin: 0;">订购号码：<big><%=opl.getOrder_account() %></big></span>
            <span class="num" style="font-size: 0.4rem;margin-bottom: 0.2rem;">价格：<big><%=opl.getProduct_price()/100 %></big>元</span>
            <span class="num" style="font-size: 0.4rem;">状态：<big><%=opl.getDictValueByField(OrderProductLogBean.DICT_FIELD_STATUS) %></big></span>
           	<span class="discount" style="font-size: 0.4rem;margin-left:0;">订购时间：<big><%=opl.getCreate_time() %></big></span>
       	</p>
        
        
    </div>
</div> 

<%} %>


</div>
 
</div>

<footer>
	<div class="foot act">
    	<img src="images/foot/ft_1_1.png">
        <p>推荐</p>
    </div>
	<div class="foot">
    	<img src="images/foot/ft_2.png">
        <p>目的地</p>
    </div>
	<div class="foot">
    	<img src="images/foot/ft_3.png">
        <p>帖子</p>
    </div>
	<a href="person/person.html"><div class="foot">
    	<img src="images/foot/ft_4.png">
        <p>我的</p>
    </div></a>
</footer>    
    
</body>
</html>
