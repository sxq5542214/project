<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>万大商城－授权管理</title>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->

<link href="fonts/font-awesome.min.css" rel="stylesheet">
</head>

<body style="background-color: #f7f7f7" onload="Pow.groupInfoBySupplier();">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">查看授权信息</div>
				</div>
				<div class="widget-body">
					<div class="form-horizontal row-border">
						<div class="form-group">
							<label class="col-md-2 control-label">商户名称</label>
							<div class="col-md-10">
								<input type="text" disabled="disabled" id="name"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">授权列表</label>
							<div class="col-md-10">
								<div id="dt_example" class="example_alt_pagination">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<a type="submit"
									href="javascript:window.location.href='../../admin/power.do';"
									class="btn btn-warning">返回</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->

	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.scrollUp.js"></script>
	<script src="js/jquery.dataTables.js"></script>

	<!-- Custom JS -->
	<script src="js/menu.js"></script>
	<script src="js/pc/power.js"></script>
	<script src="../../js/Util.js"></script>
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