<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.util.MD5"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	CustomerBean user = (CustomerBean) request.getAttribute("user");
	String password = user.getPassword();
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－个人信息查看</title>
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
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget no-margin">
				<div class="widget-header">
					<div class="title">个人信息查看</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<div class="row">
						<div class="col-lg-12 col-sm-12 col-xs-12">
							<form class="form-horizontal">
								<h5>登录信息</h5>
								<hr>
								<div class="form-group">
									<label for="username" class="col-sm-3 control-label">登录账号</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="username" value="<%=user.getUsername() %>" placeholder="请输入登录账号">
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="col-sm-3 control-label">登录密码</label>
									<div class="col-sm-3">
										<input type="password" class="form-control" id="password" value="<%=password %>" placeholder="请输入登录密码">
									</div>
									<label for="password" class="col-sm-3 control-label">请再次输入登录密码</label>
									<div class="col-sm-3">
										<input type="password" class="form-control" id="password_reset" value="<%=password %>" placeholder="请再次输入登录密码">
									</div>
								</div>
								<br>
								<h5>个人信息</h5>
								<hr>
								<div class="form-group">
									<label for="status" class="col-sm-3 control-label">当前状态</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="status" vallue="<%=user.getDictValueByField("status", user.getStatus()) %>" disabled="disabled" value="可用">
									</div>
								</div>
								<div class="form-group">
									<label for="name" class="col-sm-3 control-label">用户名称</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="name" value="<%=user.getName() %>" placeholder="请输入用户名称">
									</div>
									<label for="credit" class="col-sm-3 control-label">信用度</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="credit" value="<%=user.getCredit() %>" disabled="disabled" value="0">
									</div>
								</div>
								<div class="form-group">
									<label for="balance" class="col-sm-3 control-label">账户余额</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="balance" value="<%=user.getBalance()/100d %>" disabled="disabled" value="0">
									</div>
									<label for="points" class="col-sm-3 control-label">账户积分</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="points" value="<%=NumberUtil.toInt(user.getPoints(), 0) %>" disabled="disabled" value="0">
									</div>
								</div>
								<div class="form-group">
									<label for="getcashmin" class="col-sm-3 control-label">最小提现值</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="getcashmin" value="<%=NumberUtil.toInt(user.getGet_cash_min(), 0) %>" disabled="disabled" value="0">
									</div>
									<label for="paycycle" class="col-sm-3 control-label">结算周期</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="paycycle" value="<%=user.getDictValueByField("pay_cycle", user.getPay_cycle()) %>" disabled="disabled" value="一周">
									</div>
								</div>
								<div class="form-group">
									<label for="contactsname" class="col-sm-3 control-label">联系人</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="contactsname" value="<%=NumberUtil.toString(user.getContacts_name()) %>" placeholder="请输入联系人">
									</div>
									<label for="contactsphone" class="col-sm-3 control-label">联系号码</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="contactsphone" value="<%=NumberUtil.toString(user.getContacts_phone()) %>" placeholder="请输入联系号码">
									</div>
								</div>
								<div class="form-group">
									<label for="address" class="col-sm-3 control-label">所在地址</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="address" value="<%=NumberUtil.toString(user.getAddress()) %>" placeholder="请输入所在地址">
									</div>
								</div>
								<div class="form-group">
									<label for="remark" class="col-sm-3 control-label">备注</label>
									<div class="col-sm-9">
										<textarea class="form-control" rows="5" id="remark" value="<%=NumberUtil.toString(user.getRemark()) %>" placeholder="请输入备注信息"></textarea>
									</div>
								</div>
								<div class="form-actions">
									<button type="submit" class="btn btn-info pull-right" id="save" onclick="Cust.update('<%=user.getId()%>')">保存信息</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="js/wysi/wysihtml5-0.3.0.min.js"></script>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/wysi/bootstrap3-wysihtml5.js"></script>
	<script src="js/jquery.scrollUp.js"></script>

	<!-- Color Picker -->
	<script src="js/color-picker/jquery.minicolors.js"></script>
	<script src="js/pc/customer.js"></script>
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