<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－申领管理</title>
<meta charset="UTF-8" />
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

<body style="background-color: #f7f7f7" onload="Apy.loadMyAuditList();">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						申领管理－<a id="inputs">审核列表</a>
					</div>
				</div>
				<div class="widget-body">
					<div class="form-horizontal row-border">
						<div class="form-group">
							<label class="col-md-1 control-label">状态</label>
							<div class="col-md-2">
								<select class="form-control" id="status">
									<option value="">请选择审核状态</option>
									<option value="0">未审核</option>
									<option value="1">审核通过</option>
									<option value="2">审核不通过</option>
								</select>
							</div>
							<label class="col-md-1 control-label">商品名称</label>
							<div class="col-md-2">
								<input type="text" id="product_name" placeholder="请输入商品名称" class="form-control">
							</div>
							<label class="col-md-1 control-label">商户名称</label>
							<div class="col-md-2">
								<input type="text" id="supplier_name" placeholder="请输入商户名称" class="form-control">
							</div>
							<div class="col-md-1">
								<button class="btn btn-success" onclick="Apy.loadMyAuditList();"><i class="fa fa-search"></i>查询</button>
							</div>
						</div>
					</div>
					
					<div id="dt_example" class="example_alt_pagination">
					</div>

					<div class="modal fade" id="modalMd" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel5" aria-hidden="true"
						style="display: none;">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">×</button>
									<h4 class="modal-title text-danger" id="myModalLabel5">审核</h4>
								</div>
								<div class="modal-footer">
									<div class="form-group">
										<label class="col-md-3" style="text-align:left;">审核通过数量</label>
										<input class="form-control col-md-8" id="apply_num" placeholder="请输入审核通过数量">
									</div>
									<div class="form-group">
										<label class="col-md-4" style="text-align:left;">请选择需要核减的商户</label>
										<div id="dt_supplier" class="col-md-12">
										
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-success" id="auditcommit" data-original-title="" title="">
										<i class="fa fa-check"></i> 提交
									</button>
									<button type="button" class="btn btn-danger"
										data-dismiss="modal" data-original-title="" title="">
										<i class="fa fa-times"></i> Close
									</button>
								</div>
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
	<script src="js/pc/apply.js"></script>
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