<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleBean"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	List<MsgCenterArticleBean> articleList = (List<MsgCenterArticleBean>) request.getAttribute("articleList");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－消息文章管理</title>
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
					<div class="title">消息文章管理</div>
				</div>
				<div class="widget-body">
					<a class="btn btn-success" href="admin/msgcenter/article/toUpdateArticlePage.do">
						<i class="fa fa-plus-circle"></i> 添加
					</a>
					<a class="btn btn-success" href="javascript:return false;" onclick="syncWechatMaterial();">
						<i class="fa fa-plus-circle"></i> 同步微信文章
					</a>
					
					<div id="dt_example" class="example_alt_pagination">
						<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table">
							<thead>
								<tr>
									<th style="width: 5%">
										<input type="checkbox" class="no-margin" onclick="Sup.checkAll(this);" />
									</th>
									<th style="width: 12%">文章名称</th>
									<th style="width: 5%">状态</th>
									<th style="width: 8%" class="hidden-phone">分类名称</th>
									<th style="width: 5%" class="hidden-phone">性别分类</th>
									<th style="width: 8%" class="hidden-phone">区域</th>
									<th style="width: 4%" class="hidden-phone">顺序</th>
									<th style="width: 7%" class="hidden-phone">发送类型</th>
									<th style="width: 8%" class="hidden-phone">开始时间</th>
									<th style="width: 8%" class="hidden-phone">结束时间</th>
									<th style="width: 8%" class="hidden-phone">指定时间</th>
									<th style="width: 8%" class="hidden-phone">指定状态</th>
									<th style="width: 5%" class="hidden-phone">操作</th>
								</tr>
							</thead>
							<tbody id="supplier_list">
							<% for(MsgCenterArticleBean article : articleList ){ %>
								<tr class="gradeA ">
									<td><input type="checkbox" class="no-margin" id="<%=article.getId() %>" /></td>
									<td><%=article.getName() %></td>
									<td><%=article.getDictValueByField("status") %></td>
									<td><%=article.getType_name() %></td>
									<td><%=article.getDictValueByField("sex_type") %></td>
									<td><%=StringUtil.convertNull(article.getArea()) %></td>
									<td class="hidden-phone"><%=article.getSeq() %></td>
									<td class="hidden-phone"><%=article.getDictValueByField("send_type") %></td>
									<td class="hidden-phone"><%=article.getStart_time() %></td>
									<td class="hidden-phone"><%=article.getEnd_time() %></td>
									<td class="hidden-phone"><%=StringUtil.convertNull(article.getAssign_send_time()) %></td>
									<td class="hidden-phone"><%=article.getDictValueByField("assign_send_status") %></td>
									<td><div class="btn-group">
										<button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="admin/msgcenter/article/toUpdateArticlePage.do?id=<%=article.getId()%>">编辑</a></li>
											<li><a href="admin/msgcenter/article/toArticleAssignSendTimePage.do?id=<%=article.getId()%>">指定发送时间</a></li>
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
      
      function syncWechatMaterial(){
      	
		$.post("wechat/ajax/syncWechatMaterial.do", {
					
				}, function(data) {
					alert("同步完成");
				});
		alert("开始同步微信服务器上的素材，请稍后直接查看结果");
      }
      
    </script>

</body>
</html>