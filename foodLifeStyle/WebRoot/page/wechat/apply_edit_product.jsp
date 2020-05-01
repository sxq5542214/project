<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/wechat/";
List<SupplierProductBean> list = (List<SupplierProductBean>) request.getAttribute("list");
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
<title>万大商城</title>
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
		<header style="text-align: center;">
			<a class="col-md-12" style="color: white;">申领－选择商品</a>
		</header>
	</div>
	<div id="_contain">
		<div class="todo" id="todo_pro">
			<ul>
				<%
				for(SupplierProductBean bean : list){
					%>
					<li id="<%=bean.getId()%>" name="<%=bean.getProduct_id()%>">
						<div class="todo-content">
							<h4 class="todo-name">
								<%=bean.getProduct_name() %>/¥<%=bean.getProduct_price() %>
								<input class="form-control input-sm" placeholder="请输入申请数量" id="num" style="width: 50%;">
							</h4>
						</div>
					</li>
					<%
				}
				%>
			</ul>
		</div>
		<div class="more">
			<button class="btn btn-primary" onclick="javascript:window.history.go(-1);">上一步</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-primary" onclick="Apy.apply('<%=request.getParameter("parcustid")%>');">确认提交</button>
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
	<script src="js/apply.js"></script>
	<script src="../../js/Util.js"></script>
</body>
</html>