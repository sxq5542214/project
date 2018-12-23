<%@page import="com.yd.business.customer.bean.CustomerApplyProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/wechat/";
List<CustomerApplyProductBean> list = (List<CustomerApplyProductBean>) request.getAttribute("list");
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>我的申领列表</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/person.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/flat-ui.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
</head>

<body>

	<!-- 用于防止header将下方内容给遮住 -->
	<div style="height: 1.35rem">
		<header>
			<a> <input type="text" class="sch" id="name" /></a>
			<button  onclick="javascript:window.location.href='../../app/apply/myapplylist.do?name='+encodeURI(document.getElementById('name').value)" type="button" class="btn btn-info btn-xs" style="margin-left: 1px">查询</button>
			<a href="../../app/apply/myapply.do" class="btn btn-primary btn-xs">申领</a>
		</header>
	</div>

	<div id="_contain">
		<div class="person">
			<%
			for(CustomerApplyProductBean bean : list){
				String status = bean.getStatus()==0?"审核中":"";
				%>
				<div class="pn-lt">
			    	<i style="font-weight: bold; font-size: 0.6rem;"><%=bean.getProduct_name() %></i>&nbsp;<a><%=status %></a>&nbsp;/&nbsp;<a><%=bean.getApply_num() %></a>&nbsp;/&nbsp;<a><%=bean.getCreate_time() %></a>
			    </div>
				<%
			}
			%>
		</div>
	</div>

	<footer>
		<a href="../../app/apply/myapplylist.do"><div class="foot act">
				<img src="images/foot/ft_3_3.png">
				<p>我的申领</p>
			</div></a> <a href="../../app/operat/list.do" class="foot"> <img
			src="images/foot/ft_4.png">
			<p>操作员管理</p>
		</a> <a href="../../app/apply/auditlist.do"><div class="foot">
				<img src="images/foot/ft_1.png">
				<p>我的审核</p>
			</div></a>
	</footer>
	<script src="js/apply.js"></script>
	<script src="../../js/Util.js"></script>
</body>
</html>