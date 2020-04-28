<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<SupplierProductBean> list = (List<SupplierProductBean>) request.getAttribute("list");
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
<script type="text/javascript" src="js/supplier/orderProduct_new.js"></script>
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

<div class="favorable">
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

<%
	for(SupplierProductBean sp : list){
 %>
	<div class="fb" style="height: 1.99rem" id="<%=sp.getId() %>" onclick="changeProduct(<%=sp.getId() %>)" title="<%=sp.getProduct_price()%>">
    <div class="fb-lt">
    	<!-- <img src="images/scenics/scenic_3.png"> -->
        <p class="fb-name"><%=sp.getProduct_name() %></p>
        <p class="price">
        	<span class="discount">库存<big><%=sp.getStore_num()  %></big>个</span>
            <span class="num">价值<big><%=sp.getProduct_price() /100%></big>元</span>
        </p>
    </div>
</div> 
<%} %>
</div>
<input type="hidden" id="product_id" name="product_id">
<input type="hidden" id="product_price" name="product_price" value="0">

<div class="menu">
	<div>
		<table style="text-align: center;font-size: 0.7rem;width: 100%">
			<tr>
				<td width="70%">
    		<input class="accountinput" id="phone" name="phone" type="number" placeholder="请输入手机号码">
				</td>
				<td>
			<input value="运营商" id="isp" style="color: black;" class="accountinput" readonly="readonly" >
				</td>
			</tr>
		</table>
       	
    </div>
    
    <div >
    	<table style="text-align: center;font-size: 0.7rem;width: 100%" >
			<tr>
				<td width="70%" style="text-align: center;" >
    				<span>全民价：</span>
           			<span style="color: #ff3500;" id="text_price">2889</span>元
				</td>
				<td width="30%">
				<a href="javascript:;" class="hrefbtn" onclick="orderProduct()" > 立即订购  </a>
				</td>
			</tr>
		</table>
    </div>
    
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
