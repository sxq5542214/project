<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	CustomerBean user = (CustomerBean) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>生成卡密</title>
  <meta charset="utf-8">
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
<link href="css/blog.css" rel="stylesheet">
<link href="fonts/font-awesome.min.css" rel="stylesheet">
</head>

<body style="background-color: #f7f7f7">


	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">API申请</div>
				</div>
				<div class="widget-body">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<form class="form-horizontal"
								action="admin/supplier/createCardSecret.do" method="post"
								>
								<h5>卡密信息填写</h5>
								<hr>
								<div class="form-group">
									<label for="emailId" class="col-sm-3 control-label">商户ID(supplier_id)</label>
									<div class="col-sm-9">
										<input type="number" class="form-control"  name="supplier_id"
											placeholder="商户ID">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">产品ID(product_id)</label>
									<div class="col-sm-9">
										<input type="number" class="form-control"  name="product_id"
											placeholder="产品ID">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword1" class="col-sm-3 control-label">商户产品ID(supplier_product_id)</label>
									<div class="col-sm-9">
											<input type="number" class="form-control" name="supplier_product_id"
											placeholder="商户产品ID">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword1" class="col-sm-3 control-label">客户ID(customer_id)</label>
									<div class="col-sm-9">
										<input type="number" class="form-control"
											name="customer_id" placeholder="客户ID">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">商品名称(product_name)</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" name="product_name" placeholder="商品名称">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">生效日期(2016-06-21 12:12:12)</label>
									<div class="col-sm-9">
										<input type="text" class="form-control"  name="eff_time"
											placeholder="生效日期">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">失效日期(2016-06-21 12:12:12)</label>
									<div class="col-sm-9">
										<input type="text" class="form-control"  name="dff_time"
											placeholder="失效日期">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">连续月份</label>
									<div class="col-sm-9">
										<input type="number" class="form-control" name="month_count"
											placeholder="连续月份">
									</div>
								</div>
								<!-- <div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">批次编号</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" name="batch_no"
											 placeholder="批次编号">
									</div>
								</div> -->
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">生成数量</label>
									<div class="col-sm-9">
										<input type="number" class="form-control" name="batch_no_count"
											placeholder="生成数量">
									</div>
								</div>
								
								<div class="form-actions" style="text-align: right;">
									<button type="submit" class="btn btn-info">生成卡密</button>
									
								</div>
							</form>
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
	<script src="../../js/Util.js"></script>
	<!-- Custom JS -->

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