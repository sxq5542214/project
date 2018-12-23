<%@page import="com.yd.business.product.bean.ProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

ProductBean bean = (ProductBean) request.getAttribute("bean");
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=bean.getProduct_title() %></title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" type="text/css" href="css/news/style.css">
	</head>
	
	<body style="zoom: 1;">
		<div id="body_div" style="z-index: 8;zoom: 1;">
		<div class="head">
			<h2 class="hd"><%=bean.getProduct_title()%></h2>
		</div>
		
		<div>
			<%=bean.getProduct_desc() %>
		</div>
			
		</div>	
</body>

</html>

