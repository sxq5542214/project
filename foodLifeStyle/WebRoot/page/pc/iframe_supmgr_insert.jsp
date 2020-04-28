<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
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
<title>万大商城－商户管理－新增商户</title>
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

<body style="background-color: #f7f7f7" onload="Sup.insertUiLoad();">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						商户管理－<a id="inputs">新增商户</a>
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
							<label class="col-md-2 control-label">商户分类</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select id="type" class="form-control">
				                            <option value="">
				                              请选择商户分类
				                            </option>
				                            <option value="0">
				                              按照账户余额扣减
				                            </option>
				                            <option value="1">
				                              按照库存数量扣减
				                            </option>
				                        </select>
									</div>
									<div class="col-xs-1">
										<label class="control-label">账户保证金</label>
									</div>
									<div class="col-xs-6">
										<input type="text" name="deposit_money" id="deposit_money" placeholder="请输入账户保证金" class="form-control">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
						<%
									if(user.getIscreate()==CustomerBean.ISCREATE_YES){
									%>
							<label class="col-md-2 control-label">是否可以创建子商户</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select id="iscreate" class="form-control">
											<option value="1">是</option>
											<option value="0">否</option>
										</select>
									</div>
									<div class="col-xs-1">
										<label class="control-label">信用度</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" type="text" name="credit" id="credit" value="0">
									</div>
								</div>
							</div>
							<%}else{
									%>
									<%
									}
									 %>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">管理员</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" type="text" name="contacts_name" id="contacts_name" placeholder="请输入管理员">
									</div>
									<div class="col-xs-1">
										<label class="control-label">管理员号码</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" type="text" placeholder="请输入管理员号码" name="contacts_phone" id="contacts_phone">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">折扣规则</label>
							<div class="col-md-10">
								<div id="dt_example" class="example_alt_pagination">
									<table
										class="table table-condensed table-striped table-hover table-bordered pull-left"
										id="data-table">
										<thead>
											<tr>
												<th style="width: 5%"><input type="checkbox" onclick="Sup.checkAll(this);"
													class="no-margin" /></th>
												<th style="width: 30%">折扣规则名称</th>
												<th style="width: 35%">规则内容</th>
												<th style="width: 30%">备注</th>
											</tr>
										</thead>
										<tbody id="data-content">
										</tbody>
									</table>
									<div class="clearfix"></div>
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
								<input type="button" id="submitSupplier" class="btn btn-info" value="添加商户"/>  <a type="submit" href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
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
	<script src="js/pc/supplier.js"></script>
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
        var height = parent.document.getElementById("iframe-content").contentWindow.document.documentElement.scrollHeight;
        parent.document.getElementById("iframe-content").attr("height", height+200);//重新计算界面高度
      });

    </script>

</body>
</html>