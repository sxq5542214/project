<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－商户注册</title>
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

<body style="background-color: #f7f7f7">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						万大商城－<a id="inputs">商户注册</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form class="form-horizontal row-border" action="#">
						<div class="form-group">
							<label class="col-md-2 control-label">商户名称</label>
							<div class="col-md-10">
								<input type="text" name="name" id="name" placeholder="请输入商户名称" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">商户地址</label>
							<div class="col-md-10">
								<input class="form-control" placeholder="请输入商户地址" type="text" id="address" name="address">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">登录密码</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" type="password" name="password" id="password" placeholder="请输入登录密码">
									</div>
									<div class="col-xs-1">
										<label class="control-label">再次输入登录密码</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" type="password" placeholder="请输入再次输入登录密码" name="passwordreset" id="passwordreset">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">联系人</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" type="text" name="contacts_name" id="contacts_name" placeholder="请输入联系人">
									</div>
									<div class="col-xs-1">
										<label class="control-label">联系方式</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" type="text" placeholder="请输入联系方式" name="contacts_phone" id="contacts_phone">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">备注</label>
							<div class="col-md-10">
								<textarea class="form-control" rows="5" id="remark" placeholder="请输入备注信息"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="button" id="registCustomer" onclick="Reg.regist()" class="btn btn-info" value="注册商户"/>  <a type="submit" href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->

	<script src="js/wysi/wysihtml5-0.3.0.min.js"></script>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/wysi/bootstrap3-wysihtml5.js"></script>
	<script src="js/jquery.dataTables.js"></script>
	<script src="js/jquery.scrollUp.js"></script>
	<script src="js/pc/regist.js"></script>
	<script src="../../js/Util.js"></script>
	<!-- Color Picker -->
	<script src="js/color-picker/jquery.minicolors.js"></script>

	<!-- Custom JS -->
	<script src="js/menu.js"></script>

	<script type="text/javascript">
      //ScrollUp
      $(function () {
        $.scrollUp({
          scrollName: 'scrollUp', // Element ID
          topDistance: '300', // Distance from top before showing element (px)
          topSpeed: 300, // Speed back to top (ms)
          animation: 'fade', // Fade, slide, none
          animationInSpeed: 400, // Animation in speed (ms)
          animationOutSpeed: 400, // Animation out speed (ms)
          scrollText: 'Top', // Text for element
          activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
        });
      });

    </script>

</body>
</html>