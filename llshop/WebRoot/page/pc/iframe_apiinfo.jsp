<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	CustomerBean user = (CustomerBean) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title></title>
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
							<div class="form-horizontal"
								action="../../api/admin/apply.do" method="post"
								name="form" enctype="multipart/form-data">
								<h5>基本信息填写</h5>
								<hr>
								<div class="form-group">
									<label for="emailId" class="col-sm-3 control-label">公司法人</label>
									<div class="col-sm-9">
										<label><%=user.getCompany_legal_name() %></label>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">法人身份证号码</label>
									<div class="col-sm-9">
										<label><%=user.getCompany_legal_idcard()%></label>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword1" class="col-sm-3 control-label">身份证正面</label>
									<div class="col-sm-9">
										<div class="thumbnail">
											<img style="height: 200px" id="showcardfirst"
												src="../../<%=user.getCompany_legal_idcard_positive()%>">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword1" class="col-sm-3 control-label">身份证反面</label>
									<div class="col-sm-9">
										<div class="thumbnail">
											<img style="height: 200px" id="showcardlast"
												src="../../<%=user.getCompany_legal_idcard_back()%>">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">法人联系方式</label>
									<div class="col-sm-9">
										<label><%=user.getCompany_legal_phone()%></label>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">公司注册号</label>
									<div class="col-sm-9">
										<label><%=user.getCompany_registcode()%></label>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">公司有效期</label>
									<div class="col-sm-9">
										<label><%=user.getCompany_dff_date()%></label>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">公司注册地址</label>
									<div class="col-sm-9">
										<label><%=user.getCompany_address()%></label>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">公司经营范围</label>
									<div class="col-sm-9">
										<label><%=user.getCompany_scope()%></label>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword1" class="col-sm-3 control-label">企业营业执照</label>
									<div class="col-sm-9">
										<div class="thumbnail">
											<img style="height: 350px" id="showcompanylic"
												src="../../<%=user.getCompany_business_license()%>">
										</div>
									</div>
								</div>
								<div class="form-actions" style="text-align: right;">
									<button type="button" onclick="Api.audit(<%=CustomerBean.AUDITSTATUS_YES %>,<%=user.getId() %>);" class="btn btn-info">审核通过</button>
									<button type="button" class="btn btn-danger" onclick="Api.audit(<%=CustomerBean.AUDITSTATUS_NO %>,<%=user.getId() %>);">审核不通过</button>
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
	<script src="../../js/ajaxfileupload.js"></script>
	<script src="../../js/Util.js"></script>
	<script src="js/pc/api.js"></script>
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