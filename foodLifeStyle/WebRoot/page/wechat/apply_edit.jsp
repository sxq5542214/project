<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/wechat/";
List<CustomerBean> list = (List<CustomerBean>)request.getAttribute("list");
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
<title>申领</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/person.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/flat-ui.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
</head>

<body>

	<div style="height: 1.35rem">
		<header style="text-align: center; color: white;">申领－选择客户</header>
	</div>
	<div id="_contain">
		<div class="todo" id="todo">
			<ul>
				<%
				for(CustomerBean bean : list){
					%>
					<li onclick="javascript:window.location.href='../../app/apply/myapplypro.do?parcustid=<%=bean.getId()%>'">
						<div class="todo-icon fui-user"></div>
						<div class="todo-content">
							<h4 class="todo-name">
								<%=bean.getName() %>
							</h4>
							<%=bean.getContacts_phone()==null?"无":bean.getContacts_phone() %>
						</div>
					</li>
					<%
				}
				%>
			</ul>
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
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery-1.7.1.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/flat-ui.js"></script>

	<script src="js/application.js"></script>
</body>
</html>