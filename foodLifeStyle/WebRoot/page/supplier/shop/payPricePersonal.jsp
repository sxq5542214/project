<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	SupplierBean supplier = (SupplierBean) request.getAttribute("supplier");
	
%>			
<!DOCTYPE html>
<html lang="en">

<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">

<title>会员支付</title>

</head>
<body>

<div style="width: 100%;height: 100%;text-align: center;">
  	<img alt="" style="width: 100%;height: 100%;" src="<%=supplier.getPersonal_pay_img()%>">
	<h1>请长按扫码向我付款</h1>
</div>
</body>
</html>