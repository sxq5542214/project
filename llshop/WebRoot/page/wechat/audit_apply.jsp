<%@page import="com.yd.business.customer.bean.CustomerApplyProductBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/wechat/";
CustomerApplyProductBean apply = (CustomerApplyProductBean) request.getAttribute("apply");
List<SupplierBean> list = (List<SupplierBean>) request.getAttribute("list");
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
<title>申领审核</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/flat-ui.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/person.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
</head>

<body>
	<!-- 用于防止header将下方内容给遮住 -->
	<div style="height: 1.35rem">
		<header style="text-align: center; color: white;"> 申领审核 </header>
	</div>
	<div id="_contain" class="container">
		<div class="form-horizontal">
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">审核通过数量</label>
				<div class="col-sm-10">
					<input class="form-control" id="applynum" type="text" value="<%=apply.getApply_num() %>" placeholder="请输入审核通过数量">
				</div>
			</div>
			<div class="form-group form-group-sm">
				<label class="col-sm-2 control-label">请选择需要核减的商户</label>
				<div class="col-sm-10">
					<div class="todo">
						<ul>
							<%
								for(SupplierBean bean : list){
									%>
							<li id="<%=bean.getId()%>">
								<div class="todo-icon fui-user"></div>
								<div class="todo-content">
									<h4 class="todo-name"><%=bean.getName() %></h4>
									<%=bean.getContacts_phone() %>
								</div>
							</li>
									<%
								}
							%>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<button class="btn btn-primary btn-block" onclick="Apy.audit(<%=apply.getId()%>,1);">审核通过</button>
	</div>

	<footer>
		<a href="../../app/apply/myapplylist.do" class="foot"> <img src="images/foot/ft_3.png">
			<p>我的申领</p>
		</a> <a href="../../app/operat/list.do" class="foot"> <img
			src="images/foot/ft_4.png">
			<p>操作员管理</p>
		</a> <a href="../../app/apply/auditlist.do"><div class="foot act">
				<img src="images/foot/ft_1_1.png">
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
