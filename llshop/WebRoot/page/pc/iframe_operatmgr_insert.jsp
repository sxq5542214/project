<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－操作员管理－新增操作员</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/wysi/bootstrap-wysihtml5.css" rel="stylesheet">

<link href="css/new.css" rel="stylesheet">
<link rel="stylesheet" href="css/color-picker/jquery.minicolors.css">

<link href="fonts/font-awesome.min.css" rel="stylesheet">

</head>

<body style="background-color: #f7f7f7" onload="Operat.editCustomerAdmin();">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						操作员管理－<a id="inputs">新增操作员</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<div class="form-horizontal row-border">
						<div class="form-group">
							<label class="col-md-2 control-label">操作员名称</label>
							<div class="col-md-10">
								<input type="text" name="nickname" id="nickname" placeholder="请输入操作员名称" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">登录账号</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input type="text" name="username" id="username" class="form-control" placeholder="请输入登录账号">
									</div>
									<div class="col-xs-1">
										<label class="control-label">登录密码</label>
									</div>
									<div class="col-xs-3">
										<input type="password" name="password" id="password" class="form-control" placeholder="请输入登录密码">
									</div>
									<div class="col-xs-3">
										<input type="password" name="password_reset" id="password_reset" class="form-control" placeholder="请再次输入登录密码">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">状态</label>
							<div class="col-md-10">
								<select id="status" name="status" class="form-control">
									<option value="">请选择状态</option>
									<option value="1">可用</option>
									<option value="0">不可用</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="button" name="addCustomerAdmin" onclick="Operat.insertCustomerAdmin();" id="addCustomerAdmin" class="btn btn-info" value="添加操作员"/>  <a type="submit" href="javascript:window.location.href='../../admin/operat.do';" class="btn btn-warning">返回</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->

	<script src="js/wysi/wysihtml5-0.3.0.min.js"></script>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/wysi/bootstrap3-wysihtml5.js"></script>
	<script src="js/jquery.scrollUp.js"></script>
	<!-- Color Picker -->
	<script src="js/color-picker/jquery.minicolors.js"></script>

	<!-- Custom JS -->
	<script src="js/menu.js"></script>
	<script src="../../js/Util.js"></script>
	<script src="js/pc/operat.js"></script>
</body>
</html>