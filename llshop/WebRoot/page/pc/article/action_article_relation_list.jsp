<%@page import="com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleBean"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	List<MsgCenterActionArticleRelationBean> relationList = (List<MsgCenterActionArticleRelationBean>) request.getAttribute("relationList");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－用户动作与消息文章关系管理</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="page/pc/css/bootstrap.min.css" rel="stylesheet">

<link href="page/pc/css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->

<link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
</head>

<body style="background-color: #f7f7f7" onload="">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">用户动作与消息文章关系管理</div>
				</div>
				<div class="widget-body">
					<a class="btn btn-success" href="admin/msgcenter/article/toUpdateActionArticleRelationPage.do">
						<i class="fa fa-plus-circle"></i> 添加
					</a>
					<div id="dt_example" class="example_alt_pagination">
						<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table">
							<thead>
								<tr>
									<th style="width: 5%">
										<input type="checkbox" class="no-margin" onclick="Sup.checkAll(this);" />
									</th>
									<th style="width: 15%">用户动作</th>
									<th style="width: 10%">动作编码</th>
									<th style="width: 10%" class="hidden-phone">对应分类</th>
									<th style="width: 20%" class="hidden-phone">对应文章（为空则在分类中按规则取）</th>
									<th style="width: 5%" class="hidden-phone">状态</th>
									<th style="width: 10%" class="hidden-phone">延迟时间（秒）</th>
									<th style="width: 10%" class="hidden-phone">操作</th>
								</tr>
							</thead>
							<tbody id="supplier_list">
							<% for(MsgCenterActionArticleRelationBean relation : relationList ){ %>
								<tr class="gradeA ">
									<td><input type="checkbox" class="no-margin" id="<%=relation.getId() %>" /></td>
									<td><%=relation.getAction_name() %></td>
									<td><%=relation.getAction_type() %></td>
									<td><%=relation.getArticle_type_name() %></td>
									<td><%=StringUtil.convertNull(relation.getArticle_name()) %></td>
									<td class="hidden-phone"><%=relation.getDictValueByField("status") %></td>
									<td class="hidden-phone"><%=StringUtil.convertNull(relation.getDelay_time()) %></td>
									<td><div class="btn-group">
										<button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="admin/msgcenter/article/toUpdateActionArticleRelationPage.do?id=<%=relation.getId()%>">编辑</a></li>
										</ul></div>
									</td>
								</tr>
							<%} %>
							</tbody>
						</table>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->

	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>

	<!-- Custom JS -->
	<script src="page/pc/js/menu.js"></script>
	
	<script src="js/Util.js"></script>
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
      //Data Tables
      $(document).ready(function () {
        $('#data-table').dataTable({
        	"bPaginate":true,"iDisplayLength":50,"bFilter":true,
         	"sPaginationType": "full_numbers"
        });
      });
    </script>

</body>
</html>