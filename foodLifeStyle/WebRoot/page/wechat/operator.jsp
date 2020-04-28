<%@page import="com.yd.business.customer.bean.CustomerAdminBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/wechat/";
	List<CustomerAdminBean> list = (List<CustomerAdminBean>) request.getAttribute("list");
	
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>操作员管理</title>
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
			<a> <input type="text" class="sch" id="name" />
			</a>
			<button onclick="javascript:window.location.href='../../app/operat/list.do?name='+encodeURI(document.getElementById('name').value)" type="button" class="btn btn-info btn-xs" style="margin-left: 1px">查询</button>
			<a class="btn btn-primary btn-xs" href="../../app/operat/insert.do">添加</a>
		</header>
	</div>
<div id="_contain">
	<div class="person">
		<%
			for(CustomerAdminBean bean : list){
				%>
				<div class="pn-lt">
			    	<a href="operator_edit.html" style="width: 50%"><img class="img-lf" src="images/person/lt_5.png"><%=bean.getNickname() %></a>
			    </div>
				<%
			}
		%>
	</div>
</div>
<footer>
	<a href="../../app/apply/myapplylist.do" class="foot">
    	<img src="images/foot/ft_3.png">
        <p>我的申领</p>
    </a>
	<a href="../../app/operat/list.do" class="foot act">
    	<img src="images/foot/ft_4_4.png">
        <p>操作员管理</p>
    </a>
	<a href="../../app/apply/auditlist.do" class="foot">
    	<img src="images/foot/ft_1.png">
        <p>我的审核</p>
    </a>
</footer>    
    
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
</body>
</html>
