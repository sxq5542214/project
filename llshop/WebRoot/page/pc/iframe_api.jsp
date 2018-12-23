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
		<%
		
		if(user.getAuditstatus()==null){%>
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">API申请</div>
				</div>
				<div class="widget-body">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<form class="form-horizontal"
								action="../../api/admin/apply.do" method="post"
								name="form" enctype="multipart/form-data">
								<h5>基本信息填写</h5>
								<hr>
								<div class="form-group">
									<label for="emailId" class="col-sm-3 control-label">公司法人</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="corporate" name="corporate"
											placeholder="公司法人">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">法人身份证号码</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="cardno" name="cardno"
											placeholder="法人身份证号码">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword1" class="col-sm-3 control-label">身份证正面</label>
									<div class="col-sm-9">
										<div class="thumbnail">
											<img style="height: 200px" id="showcardfirst"
												src="img/profile.png"> <input type="file"
												id="cardfirst" name="cardfirst"
												onchange="onchangeImage(this,'showcardfirst');">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword1" class="col-sm-3 control-label">身份证反面</label>
									<div class="col-sm-9">
										<div class="thumbnail">
											<img style="height: 200px" id="showcardlast"
												src="img/profile.png"> <input type="file"
												id="cardlast" name="cardlast"
												onchange="onchangeImage(this,'showcardlast');">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">法人联系方式</label>
									<div class="col-sm-9">
										<input type="tel" class="form-control"
											id="corporatephone" name="corporatephone" placeholder="法人联系方式">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">公司注册号</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="companyno" name="companyno"
											placeholder="公司注册号">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">公司有效期</label>
									<div class="col-sm-9">
										<input type="datetime" class="form-control" id="companyexpire" name="companyexpire"
											placeholder="公司有效期">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">公司注册地址</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" name="companyaddress"
											id="companyaddress" placeholder="公司注册地址">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">公司经营范围</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="companycover" name="companycover"
											placeholder="公司经营范围">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword1" class="col-sm-3 control-label">企业营业执照</label>
									<div class="col-sm-9">
										<div class="thumbnail">
											<img style="height: 350px" id="showcompanylic"
												src="img/profile.png"> <input type="file"
												id="companylic" name="companylic"
												onchange="onchangeImage(this,'showcompanylic');">
										</div>
									</div>
								</div>
								<div class="form-actions" style="text-align: right;">
									<button type="submit" onclick="return Api.valid();" class="btn btn-info">提交申请</button>
									<button type="button" class="btn btn-danger" onclick="javascript:window.location.href='../../admin/applylist.do'">返回申请列表</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%}else{%>
			<div class="blog-wrapper">
                      <div class="blog-body">
                        <div class="date">
                          申请时间:<%=user.getModify_time() %>
                        </div>
                        <div class="blog-title">
                          <%=user.getAuditstatus()==0?"您好，您的申请平台已接收，正在申请中...请耐心等候！":user.getAuditstatus()==1?"您的资质审核已经通过...恭喜您可以申请商品信息了！":"抱歉，您的资质审核未通过...请核查您的资质信息！" %>
                        </div>
                        <p class="blog-info">
                        </p>
                        <%if(user.getAuditstatus()==1) {%>
                        	<a href="../../admin/applylist.do" class="blog-btn" data-original-title="" title="">申请商品</a>
                        <%}else if(user.getAuditstatus()==2){
                        	%>
                        	<a href="../../admin/aginapi.do" class="blog-btn" data-original-title="" title="">重新提交资质申请</a>
                        	<%
                        } %>
                      </div>
                    </div>
		<%} %>
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